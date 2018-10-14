package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import clemnico.FC.Vecteur;

public class Player extends Entity {

	//// Attributs////

	private boolean upKey=false;
	private boolean leftKey=false;
	private boolean rightKey=false;
	
	private int directionX = 0;
	private int airControl = 1; // En pourcentage
	
	private int keyPressed = 0;
	private boolean dead = false;
	private int ax = 2;
	private int currentVx=0;
	private int vxMax = 15;
	private int vyMax = 50;
	private int vJump = 40;
	Sound sound=new Sound("saut.wav");
	Sound sound1=new Sound("pan.wav");
	
	private boolean shooting=false;
	private int reloadShoot=10;	//fréquence de tir (0=max)
	private int timeShoot=0;	//durée depuis le dernier tir
	private int bulletSize=30;
	
	private int xMouse;
	private int yMouse;

	public ArrayList<Projectile> projectiles=new ArrayList<>();
	
	////Constructeur////
	public Player(int x, int y, int width, int height, String name, int direction, int vxOnGround) {
		super(name, x, y, width, height);
		setDirectionX(direction);
	}

	
	//// Methodes////

	// Actions clavier
	public void actionKeyboardPressed(int key) {
		//Déplacement
		if (key == KeyEvent.VK_Z) {setUpKey(true);}
		if (key == KeyEvent.VK_Q) {setLeftKey(true);setKeyPressed(-1);} 
		if (key == KeyEvent.VK_D) {setRightKey(true);setKeyPressed(1);}
		
		//Jet de projectiles
		if (key == KeyEvent.VK_SPACE) {setShooting(true);}
	}
	
	public void actionKeyboardReleased(int key) {
		//Déplacement
		if (key == KeyEvent.VK_Z) {setUpKey(false);}
		if (key == KeyEvent.VK_Q) {setLeftKey(false);} 
		if (key == KeyEvent.VK_D) {setRightKey(false);}
		
		//Jet de projectiles
		if (key == KeyEvent.VK_SPACE) {setShooting(false);}
	}
	
	public void movement() {
		//Tombe si est en l'air
		if (inTheAir) {
			double g = -1.5;
			setVy((int) (vy - g * 1.0));
		}
		
		//Saut
		if (upKey && !inTheAir) {
			setVy(-vJump);
			setY(y - 1);
			sound.play();
		}
		if (leftKey) {setDirectionX(-1);}
		if (rightKey) {setDirectionX(1);}
		
		//Si un déplacement latérale est demandé par le joueur
		//Dernière touche appuyée valide si conflit entre droite et gauche
		if (leftKey && rightKey) {
			if (inTheAir) {
				setVx(currentVx+keyPressed*airControl);
			}
			else {
				setVx(currentVx+keyPressed*ax);
				
			}
		}
		else if (leftKey || rightKey) {
			if (inTheAir) {
				setVx(currentVx+directionX*airControl);
			}
			else {
				setVx(currentVx+directionX*ax);
				
			}
		}
		setCurrentVx(vx);
		setX(x + vx);
		setY(y + vy);
	}
	
	public void projectileOperation(Panel panel,Portal portal1, Portal portal2,ArrayList<GeneralEnemy> enemies, int width,int height,ArrayList<Obstacle> obstacles) {
		
		setTimeShoot(timeShoot+1);
		
		//Creation des projectiles
		if (shooting && timeShoot>=reloadShoot) {
			Projectile projectile=new Projectile(x+this.width/2-bulletSize/2,y+this.height/2-bulletSize/2,10,20,0, bulletSize);
			projectile.useDefaultAnimations();
			projectile.setCurrentAnimation(NameAnimation.DEFAULT);
			panel.addToMainLayer(projectile);
			projectile.directionThrow(this, xMouse, yMouse);
			projectiles.add(projectile);
			sound1.play();
	
			setTimeShoot(0);
			
		}
		
		//Refresh des projectiles et enregistrement de ceux à détruire
		ArrayList<Projectile> toRemove = new ArrayList<>();
		for (Projectile projectile : projectiles) {
			projectile.step(portal1,portal2,enemies);
			if(projectile.isOut(width, height,panel.getxOffset(),panel.getyOffset(), obstacles)) {
				toRemove.add(projectile);
			}
		}
		
		//Destruction des projectiles
		for (Projectile projectile : toRemove) {
			projectiles.remove(projectile);
			panel.deleteEntity(projectile);
		}
	}
	
	
	// Action de joueur pour un pas de la boucle
	public void step(Panel panel,Portal portal1, Portal portal2,ArrayList<GeneralEnemy> enemies, int width,int height,ArrayList<Obstacle> obstacles) {
		
		
		// Déplacement du joueur
		movement();
		// Gestion portails teleportations
		fc.portalInteractionRect(this, portal1, portal2);
		// Gestion obstacle
		
		fc.obstacleInteraction(this, obstacles);
		//fc.obstacleInteraction(this, obstaclesMoving); 
		// Tir du joueur
		projectileOperation(panel, portal1, portal2, enemies, width, height, obstacles);
		
		getCurrentAnimation().update();	
		chooseAnimation();
	}

	
	@Override
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		if (inTheAir) {
			if (vy<=0) {if (vx>=0) {name=NameAnimation.JUMPR;}else {name=NameAnimation.JUMPL;}}
			else  {if (vx>=0) {name=NameAnimation.FALLR;}else {name=NameAnimation.FALLL;}}}
		
		else {if (vx>0) {name=NameAnimation.WALKR;}else if (vx<0) {name=NameAnimation.WALKL;}}
		setCurrentAnimation(name);
	}
	
	
	
	@Override
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.WALKL,ACreator.createAnimation(Animations.AnimationPlayerWalkL,width,height));
		addAnimation(NameAnimation.WALKR,ACreator.createAnimation(Animations.AnimationPlayerWalkR,width,height));
		addAnimation(NameAnimation.JUMPL,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.JUMPR,ACreator.createAnimation(Animations.AnimationPlayerAirKick,width,height));
		addAnimation(NameAnimation.FALLL,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.FALLR,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));

		
	}
	
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	

	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}


	public int getDirectionX() {
		return directionX;
	}

	public void setDirectionX(int i) {
		this.directionX = i;
	}

	public int getKeyPressed() {
		return keyPressed;
	}
	public void setKeyPressed(int keyPressed) {
		this.keyPressed=keyPressed;
	}

	public boolean isInTheAir() {
		return inTheAir;
	}


	public void setInTheAir(boolean inTheAir) {
		if (this.inTheAir != inTheAir) {
			 setTimeInAir(0);
			this.inTheAir = inTheAir;}
	}


	public int getVx() {
		return vx;
	}


	public void setVx(int vx) {
		if (vx>0) {vx=Math.min(vx , vxMax );}
		else {vx=- Math.min(-vx, vxMax);}
		this.vx = vx;
	}


	public int getVy() {
		return vy;
	}


	public void setVy(int vy) {
		if (vy>0) {vy=Math.min(vy , vyMax );}
		else {vy=- Math.min(-vy, vyMax);}
		this.vy = vy;
	}


	public int getTimeInAir() {
		return timeInAir;
	}


	public void setTimeInAir(int timeInAir) {
		this.timeInAir = timeInAir;
	}


	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}




	public boolean isShooting() {
		return shooting;
	}




	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}




	public int getxMouse() {
		return xMouse;
	}




	public void setxMouse(int xMouse) {
		this.xMouse = xMouse;
	}




	public int getyMouse() {
		return yMouse;
	}




	public void setyMouse(int yMouse) {
		this.yMouse = yMouse;
	}

	public int getReloadShoot() {
		return reloadShoot;
	}

	public void setReloadShoot(int reloadShoot) {
		this.reloadShoot = reloadShoot;
	}

	public int getTimeShoot() {
		return timeShoot;
	}

	public void setTimeShoot(int timeShoot) {
		this.timeShoot = timeShoot;
	}




	public boolean isUpKey() {
		return upKey;
	}




	public void setUpKey(boolean upKey) {
		this.upKey = upKey;
	}




	public boolean isLeftKey() {
		return leftKey;
	}




	public void setLeftKey(boolean leftKey) {
		this.leftKey = leftKey;
	}




	public boolean isRightKey() {
		return rightKey;
	}




	public void setRightKey(boolean rightKey) {
		this.rightKey = rightKey;
	}




	public int getvJump() {
		return vJump;
	}




	public void setvJump(int vJump) {
		this.vJump = vJump;
	}




	public int getAirControl() {
		return airControl;
	}




	public void setAirControl(int airControl) {
		this.airControl = airControl;
	}


	public int getAx() {
		return ax;
	}


	public void setAx(int ax) {
		this.ax = ax;
	}


	public int getCurrentVx() {
		return currentVx;
	}


	public void setCurrentVx(int currentVx) {
		this.currentVx = currentVx;
	}


	public int getBulletSize() {
		return bulletSize;
	}


	public void setBulletSize(int bulletSize) {
		this.bulletSize = bulletSize;
	}



}


package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import clemnico.FC.Vecteur;

public abstract class Player extends Entity {

	//// Attributs////

	protected boolean upKey=false;
	protected boolean leftKey=false;
	protected boolean rightKey=false;
	protected boolean mouse1=false;
	protected boolean mouse2=false;
	protected boolean mouse3=false;
	
	protected int directionX = 0;
	protected int airControl = 1; // En pourcentage
	
	protected int keyPressed = 0;
	protected boolean dead = false;
	protected int ax = 2;
	protected int currentVx=0;
	protected int vxMax = 15;
	protected int vyMax = 30;
	protected int vJump = 30;
	
	
	protected boolean shooting=false;
	protected int reloadShoot=10;	//fréquence de tir (0=max)
	protected int timeShoot=0;	//durée depuis le dernier tir
	protected int bulletSize=30;
	protected int bulletSpeed=20;
	
	protected Portal portal1=new Portal(-500, -500, 100, 20,"Portal1",Color.BLUE);
	protected Portal portal2=new Portal(-500, -500, 100, 20,"Portal2",Color.ORANGE);
	
	
	protected int xMouse;
	protected int yMouse;
	
	public ArrayList<Projectile> projectiles=new ArrayList<>();
	
	//Son
	Sound sound=new Sound("saut.wav");
	Sound sound1=new Sound("pan.wav");
	Sound sound2=new Sound("hitplayer.wav");
	
	////Constructeur////
	public Player(int x, int y, int width, int height, String name, int direction, int vxOnGround) {
		super(name, x, y, width, height);
		setDirectionX(direction);
		portal1.setOwner(this);
		portal2.setOwner(this);
		
		
		
		listeNoCollisonWith= new Class[] {ObstacleFixSurfaceNoPortal.class,ObstacleFixGateNoPortal.class};
		
	}

	
	//// Methodes////

	// Actions clavier
	public void actionKeyboardPressed(int key) {
		switch (key) {
		//Déplacement
		case KeyEvent.VK_Z: setUpKey(true); 					break;
		case KeyEvent.VK_Q: setLeftKey(true);setKeyPressed(-1); break;
		case KeyEvent.VK_D: setRightKey(true);setKeyPressed(1); break;
		//Jet de projectiles
		case KeyEvent.VK_SPACE: setShooting(true);				break;
		}
	}
	
	public void actionKeyboardReleased(int key) {
		switch (key) {
		//Déplacement
		case KeyEvent.VK_Z: setUpKey(false);		break;
		case KeyEvent.VK_Q: setLeftKey(false);		break; 
		case KeyEvent.VK_D: setRightKey(false);		break;
		//Jet de projectiles
		case KeyEvent.VK_SPACE: setShooting(false); break;
		}
	}
	
	
	// Actions souris
	public void actionMouse(int nClic, int xClic, int yClic) {
		switch(nClic) {
			case 1:	this.portal1.movePortal(xClic,yClic); break;
			case 3: this.portal2.movePortal(xClic,yClic); break;
		}
	}
	
	
	public void movement() {
		//Tombe si est en l'air
		if (inTheAir) {
			double g = -1;
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
		if (!((isInCollisionLeft() && vx<0)|| (isInCollisionRight() && vx>0))) {setX(x + vx);}
		setY(y + vy);
		
//		if (!inTheAir && !(leftKey || rightKey)) {setVx((int)(vx*0.9));}
		if (!inTheAir && !(leftKey || rightKey)) {setVx(0);}
	}
	

	
	public void touched(int vx2, int vy2) {
		// TODO Auto-generated method stub
		//sound2.play();
		
	}
	
	
	public void projectileOperation(Panel panel,ArrayList<Player> listPlayer,ArrayList<Entity> entities, int width,int height) {
		
		setTimeShoot(timeShoot+1);
		
		//Creation des projectiles
		if (shooting && timeShoot>=reloadShoot) {
			
			Projectile projectile=new Projectile(x+this.width/2-bulletSize/2,y+this.height/2-bulletSize/2,10,20,0, bulletSize, this, bulletSpeed);
			projectile.useDefaultAnimations();
			System.out.println("toto");
			this.levelIn.addToMainLayer(projectile);
			System.out.println("toto2");
			projectile.directionThrow(xMouse, yMouse);
			projectiles.add(projectile);
			sound1.play();
	
			setTimeShoot(0);
			
		}
		
		//Refresh des projectiles et enregistrement de ceux à détruire
		ArrayList<Projectile> toRemove = new ArrayList<>();
		for (Projectile projectile : projectiles) {
			projectile.step(listPlayer);
			if(projectile.isOut(width, height,panel.getxOffset(),panel.getyOffset(), entities)) {
				toRemove.add(projectile);
			}
		}
		
		//Destruction des projectiles
		for (Projectile projectile : toRemove) {
			projectiles.remove(projectile);
			this.levelIn.deleteEntity(projectile);
		}
	}
	
		
		
	// Action de joueur pour un pas de la boucle
	public void step(Window window,ArrayList<Player> listPlayer,ArrayList<Entity> entities,ArrayList<Obstacle> obstacles) {
		
		
		// Déplacement du joueur
		movement();
		// Gestion portails teleportations
		fc.portalInteractionRect(this, portal1, portal2);
		// Gestion obstacle
		
		fc.obstacleInteraction(this, obstacles);
		//fc.obstacleInteraction(this, obstaclesMoving); 
		// Tir du joueur
		projectileOperation(window.panel, listPlayer, entities, window.getWidth(), window.getHeight());
		
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

		setCurrentAnimation(NameAnimation.DEFAULT);
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


	public Portal getPortal1() {
		return portal1;
	}


	public void setPortal1(Portal portal1) {
		this.portal1 = portal1;
	}


	public Portal getPortal2() {
		return portal2;
	}


	public void setPortal2(Portal portal2) {
		this.portal2 = portal2;
	}


	public int getBulletSpeed() {
		return bulletSpeed;
	}


	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}


}


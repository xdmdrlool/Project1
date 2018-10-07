package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import clemnico.FC.Vecteur;

public class Player extends Entity {

	//// Attributs////

	private int directionX = 0;

	private boolean moveX = false;
	private int keyPressed = 0;
	private boolean dead = false;
	private int vxMax = 100;
	private int vyMax = 30;
	private int vxOnGround=6;
	Sound sound=new Sound("saut.wav");
	
	private boolean shooting=false;
	private int reloadShoot=10;	//fréquence de tir (0=max)
	private int timeShoot=0;	//durée depuis le dernier tir
	
	private int xMouse;
	private int yMouse;

	public ArrayList<Projectile> projectiles=new ArrayList<>();
	
	////Constructeur////
	public Player(int x, int y, int width, int height, String name, int direction, int vxOnGround) {
		super(name, x, y, width, height);
		setDirectionX(direction);
		setVxOnGround(vxOnGround);
	}


	
	
	//// Methodes////

	public void moveIn(int x, int y) {
		setX(x);
		setY(y);
	}

	public void distanceStep(int dx, int dy) {
		moveIn(this.x + dx, this.y + dy);
	}

	// Action clavier
	public void actionKeyboard(int key) {

		if (key == KeyEvent.VK_Z && !inTheAir) {
			setInTheAir(false);
			setVy(-20);
			setY(y - 1);
			setVx(directionX * vx);
			sound.play();

		}
		
		if (key == KeyEvent.VK_Q && keyPressed != key) {
			setDirectionX(-1);
			setKeyPressed(key);
			setMoveX(true);
		} else if (key == KeyEvent.VK_D && keyPressed != key) {
			setDirectionX(1);
			setKeyPressed(key);
			setMoveX(true);
		}
		//Jet de projectiles
		if (key == KeyEvent.VK_SPACE) {
			setShooting(true);
		}
	}
	
	public void shoot() {
		Projectile projectile=new Projectile(x+width/2,y+height/2,10,20,0);
		projectile.directionThrow(this, xMouse, yMouse);
		projectiles.add(projectile);
	}
	
	
	// Action de joueur pour un pas de la boucle
	public void step(int period) {

		// Mouvement vertical du joueur
		if (inTheAir) {
			fall();
		}

		//Mouvement horizontal du joueur
		double AirControl = 1.0; // En pourcentage
		if (moveX) {
			if (isInTheAir()) {
				setX(x + (int) (this.directionX * AirControl * vxOnGround));
			} else {
				setVx(this.directionX * vxOnGround);
				setX(this.x + this.vx);
			}
		}
		
		//Le joueur tire
		setTimeShoot(timeShoot+1);
		if (shooting && timeShoot>=reloadShoot) {
			shoot();
			setTimeShoot(0);
		}
		
		
		chooseAnimation();
	}
	
	// Mouvement physique du joueur dans les airs sans entrée clavier
	public void fall() {
		double g = -5;
		double t = timeInAir / 60.0;

		setVy((int) (vy - g * t));
		setX(x + vx);
		setY(y + vy);
	}
	

	
	//Gestion intéraction entre joueur et les obstacles

	
public void obstacleInteraction(FC fc, Obstacle[] obstacles) {
		Vecteur vecteurCorrection=null;
		Vecteur directionCollision=null;
		boolean varInTheAir=true;
//		System.out.println("");
		for (Obstacle obstacle: obstacles) {
			//S'il y a collision avec un obstacle
			FormRect rect0=new FormRect(Color.RED, xBefore, yBefore, width, height, 0);
			FormRect rect=(FormRect) getHitbox().getForm();
			FormRect obs=(FormRect) obstacle.getHitbox().getForm();
			
			rect0.setX(rect0.getX()+obstacle.getX()-obstacle.getxBefore());
			rect0.setY(rect0.getY()+obstacle.getY()-obstacle.getyBefore());
			
			Vecteur[] tab = fc.calculVecteurCollisionRectDroitObstacleDroit(rect0,rect,obs);
			
			if (tab !=null) {
				vecteurCorrection=tab[0];
				directionCollision=tab[1];
				
//				System.out.println("xB :"+rect0.getX()+"   yB : "+rect0.getY());
//				System.out.println("x :"+x+"   y : "+y);
//				System.out.println(vecteurCorrection.x+" "+vecteurCorrection.y);
//				System.out.println(directionCollision.x+" "+directionCollision.y);
				if (vecteurCorrection.y<0||directionCollision.y>0) {varInTheAir=false;}
				if (directionCollision.x!=0 ||directionCollision.y>0) {setVx(0);}
				if (directionCollision.y!=0) {setVy(0);}
				

				int newX=(int) (getX()+vecteurCorrection.x);
				int newY=(int) (getY()+vecteurCorrection.y);
				
//				System.out.println("x: "+newX+"  y: "+newY);
//				System.out.println("air : "+isInTheAir());	

				setX(newX);
				setY(newY);
//				System.out.println("x :"+x+"   y : "+y+"     vy : "+vy+"     "+varInTheAir);
			}
			

		}
//		System.out.println("x :"+x+"   y : "+y+"     vy : "+vy+"     "+varInTheAir);	
		setTimeInAir(getTimeInAir()+1);
		setInTheAir(varInTheAir);
		setxBefore(x);setyBefore(y);

		
				
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

	public boolean isMoveX() {
		return moveX;
	}
	public void setMoveX(boolean moveX) {
		this.moveX=moveX;
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


	public int getVxOnGround() {
		return vxOnGround;
	}


	public void setVxOnGround(int vxOnGround) {
		this.vxOnGround = vxOnGround;
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

	

}


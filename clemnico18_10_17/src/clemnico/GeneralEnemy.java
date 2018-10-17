package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import clemnico.FC.Vecteur;

public class GeneralEnemy extends Entity {
////Attributs////
	
	boolean fallFromPlatform= false;
	private int 	directionX = 0;
	private boolean moveX = false;
	private boolean dead =false;

	private int vxMax=100;
	private int vyMax=30;
	private int vxOnGround=5;


	////Constructeur////
	public GeneralEnemy(int x,int y,int width, int height,String name, int direction, int vxOnGround, boolean fallFromPlatform) {
		super(name, x,y, width, height);
		setxBefore(x);
		setyBefore(y);
		setDirectionX(direction);
		setVxOnGround(vxOnGround);
		setFallFromPlatform(fallFromPlatform);
	}


	////Methodes////

	
	public void moveIn(int x ,int y) {
		setX(x);
		setY(y);
	}
	
	public void distanceStep(int dx,int dy) {
		moveIn(this.x+dx, this.y+dy);
	}
	
	
	//Action de joueur pour un pas de la boucle
	public void step(Portal portal1, Portal portal2, ArrayList<Obstacle> obstacles) {
		
		//Mouvement vertical du joueur
		if (inTheAir) {fall();}
		
		setX(x+vx);
		setY(y+vy);
		
		fc.portalInteractionRect(this, portal1, portal2);	// Gestion portails teleportations
		obstacleInteractionEnemy(fc, obstacles);		// Gestion obstacle
		
		chooseAnimation();
	}

	
	//Mouvement physique du joueur dans les airs sans entrée clavier
	public void fall() {
		double g=-2;
		double t=timeInAir/60.0;
		setVy((int)(vy-g));
		setY(y+vy);
	}
	
	public void touched(int vxProjectile, int vyProjectile) {
		setX(x+vxProjectile*5);
		setY(y+vyProjectile*5);
	}
	
	
	public void obstacleInteractionEnemy(FC fc, ArrayList<Obstacle> obstacles) {
		Vecteur vecteurCorrection=null;
		Vecteur directionCollision=null;
		boolean varInTheAir=true;
		boolean inverseVx=false;
		for (Obstacle obstacle: obstacles) {
			//S'il y a collision avec un obstacle
			FormRect rect0=new FormRect(Color.RED, xBefore, yBefore, width, height, 0);
			FormRect rect=(FormRect) getHitbox().getForm();
			FormRect obs=(FormRect) obstacle.getHitbox().getForm();
			Vecteur[] tab = fc.calculVecteurCollisionRectDroitObstacleDroit(rect0,rect,obs);
			
			if (tab !=null) {
				vecteurCorrection=tab[0];
				directionCollision=tab[1];
//				System.out.println(directionCollision.x+" "+directionCollision.y);
//				System.out.println("xB :"+xBefore+"   yB : "+yBefore);
//				System.out.println(vecteurCorrection.x+" "+vecteurCorrection.y);
				if (vecteurCorrection.y<0||directionCollision.y>0) {varInTheAir=false;}
				if (directionCollision.x!=0) {inverseVx=true;}
				if (!fallFromPlatform && directionCollision.y>0) {if(x<obstacle.getX() && vx<0) {inverseVx=true;} if (x+width>obstacle.getX()+obstacle.getWidth() &&vx>=0) {inverseVx=true;};}
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
		setTimeInAir(getTimeInAir()+1);
		setInTheAir(varInTheAir);
		setxBefore(x);setyBefore(y);		
		if (inverseVx) {setVx(-vx);}
	}
	
	
	
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
		addAnimation(NameAnimation.WALKL,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.WALKR,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.JUMPL,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.JUMPR,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
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






	public void setInTheAir(boolean inTheAir) {
		if (this.inTheAir != inTheAir) {
			 setTimeInAir(0);
			this.inTheAir = inTheAir;}
		if (!inTheAir) {if (vx>=0) {setVx(vxOnGround);} else {setVx(-vxOnGround);}}
	}





	public void setVx(int vx) {
		if (vx>0) {vx=Math.min(vx , vxMax );}
		else {vx=- Math.min(-vx, vxMax);}
		this.vx = vx;
	}





	public void setVy(int vy) {
		if (vy>0) {vy=Math.min(vy , vyMax );}
		else {vy=- Math.min(-vy, vyMax);}
		this.vy = vy;
	}




	public int getVxOnGround() {
		return vxOnGround;
	}


	public void setVxOnGround(int vxOnGround) {
		this.vxOnGround = vxOnGround;
	}



	public boolean isFallFromPlatform() {
		return fallFromPlatform;
	}


	public void setFallFromPlatform(boolean fallFromPlatform) {
		this.fallFromPlatform = fallFromPlatform;
	}
}



package clemnico;

import java.util.ArrayList;

public class EnemyDefault extends Enemy {
////Attributs////
	
	boolean fallFromPlatform= false;
	private boolean dead =false;

	private int vxMax=100;
	private int vyMax=30;
	private int vxOnGround=5;


	////Constructeur////
	public EnemyDefault(int x,int y,int width, int height,String name, boolean fallFromPlatform) {
		super(name, x,y, width, height);
		setVxOnGround(vxOnGround);
		setFallFromPlatform(fallFromPlatform);
		setAx(1);
	}


	////Methodes////
	
	public void touched(int vxProjectile, int vyProjectile) {
		setVx(0);
		setVy(0);
	}
	
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		if (inTheAir) {
			if (vy<=0) {if (vx>=0) {name=NameAnimation.JUMPR;}else {name=NameAnimation.JUMPL;}}
			else  {if (vx>=0) {name=NameAnimation.FALLR;}else {name=NameAnimation.FALLL;}}}
		
		else {if (vx>0) {name=NameAnimation.WALKR;}else if (vx<0) {name=NameAnimation.WALKL;}}
		setCurrentAnimation(name);
	}
	
	public void step(Portal portal1, Portal portal2, ArrayList<Obstacle> obstacles) {
		//Mouvement vertical du joueur
		if (inTheAir) {fall();}
		
		setVx(vx+ax);
		setX(x+vx);
				
		fc.portalInteractionRect(this, portal1, portal2);	// Gestion portails teleportations
		fc.obstacleInteractionEnemy(this, obstacles);		// Gestion obstacle
		getCurrentAnimation().update();	
		chooseAnimation();
		
	}
	

	
	@Override
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.WALKL,ACreator.createAnimation(Animations.AnimationEnemyWalkL,width,height));
		addAnimation(NameAnimation.WALKR,ACreator.createAnimation(Animations.AnimationEnemyWalkR,width,height));
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

	public void setInTheAir(boolean inTheAir) {
		if (this.inTheAir != inTheAir) {
			 setTimeInAir(0);
			this.inTheAir = inTheAir;}
		if (!inTheAir) {if (vx>=0) {setVx(vxOnGround);} else {setVx(-vxOnGround);}}
	}

	public void setVx(int vx) {
		if (vx>0) {vx=Math.min(vx , vxMax );}
		else if (vx<0){vx=- Math.min(-vx, vxMax);}
		this.vx = vx;
	}

	public void setVy(int vy) {
		if (vy>0) {vy=Math.min(vy , vyMax );}
		else if (vy<0){vy=- Math.min(-vy, vyMax);}
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



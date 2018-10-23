package clemnico;

import java.awt.Color;
import java.util.ArrayList;

import clemnico.FC.Vecteur;

public abstract class Enemy extends Entity{

	////Attributs////
	protected boolean ableToShoot;
	protected boolean ableToJump;
	protected boolean fallFromPlatform= false;
	protected int vxOnGround=5;
	protected int ax=1;
	protected boolean dead=false;
	
	//Paramètres de recul
	protected boolean recoil=false;
	protected int vRecoil=1;
	protected int timeRecoil=0;
	protected int timeEndRecoil=10;
	
	
	////Constructeur////
	public Enemy(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		
	}
	
	////Méthodes////
	
	public abstract void touched(int vxProjectile, int vyProjectile);
	
	//Mouvement physique du joueur dans les airs sans entrée clavier
	public void fall() {
		double g=-2;
		setVy((int)(vy-g));
		setY(y+vy);
	}
	
	//Action de joueur pour un pas de la boucle
	public abstract void step(Portal portal1, Portal portal2, ArrayList<Obstacle> obstacles);
	
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));
		addAnimation(NameAnimation.WALKL,ACreator.createAnimation(Animations.AnimationEnemyWalkL,width,height));
		addAnimation(NameAnimation.WALKR,ACreator.createAnimation(Animations.AnimationEnemyWalkR,width,height));
		addAnimation(NameAnimation.JUMPL,ACreator.createAnimation(Animations.AnimationEnemyWalkL,width,height));
		addAnimation(NameAnimation.JUMPR,ACreator.createAnimation(Animations.AnimationEnemyWalkR,width,height));
		addAnimation(NameAnimation.FALLL,ACreator.createAnimation(Animations.AnimationEnemyWalkL,width,height));
		addAnimation(NameAnimation.FALLR,ACreator.createAnimation(Animations.AnimationEnemyWalkR,width,height));		
	}
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		if (inTheAir) {
			if (vy<=0) {if (vx>=0) {name=NameAnimation.JUMPR;}else {name=NameAnimation.JUMPL;}}
			else  {if (vx>=0) {name=NameAnimation.FALLR;}else {name=NameAnimation.FALLL;}}}
		
		else {if (vx>0) {name=NameAnimation.WALKR;}else if (vx<0) {name=NameAnimation.WALKL;}}
		setCurrentAnimation(name);
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public boolean isAbleToShoot() {
		return ableToShoot;
	}
	public void setAbleToShoot(boolean ableToShoot) {
		this.ableToShoot = ableToShoot;
	}
	public boolean isAbleToJump() {
		return ableToJump;
	}
	public void setAbleToJump(boolean ableToJump) {
		this.ableToJump = ableToJump;
	}

	
	public boolean isFallFromPlatform() {
		return fallFromPlatform;
	}


	public void setFallFromPlatform(boolean fallFromPlatform) {
		this.fallFromPlatform = fallFromPlatform;
	}
	
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getVxOnGround() {
		return vxOnGround;
	}
	public void setVxOnGround(int vxOnGround) {
		this.vxOnGround = vxOnGround;
	}
	public int getAx() {
		return ax;
	}
	public void setAx(int ax) {
		this.ax = ax;
	}

	public int getVyMax() {
		return vyMax;
	}

	public void setVyMax(int vyMax) {
		this.vyMax = vyMax;
	}

	public int getVxMax() {
		return vxMax;
	}

	public void setVxMax(int vxMax) {
		this.vxMax = vxMax;
	}

	public boolean isRecoil() {
		return recoil;
	}

	public void setRecoil(boolean recoil) {
		this.recoil = recoil;
	}

	public int getvRecoil() {
		return vRecoil;
	}

	public void setvRecoil(int vRecoil) {
		this.vRecoil = vRecoil;
	}

	public int getTimeRecoil() {
		return timeRecoil;
	}

	public void setTimeRecoil(int timeRecoil) {
		this.timeRecoil = timeRecoil;
	}

	public int getTimeEndRecoil() {
		return timeEndRecoil;
	}

	public void setTimeEndRecoil(int timeEndRecoil) {
		this.timeEndRecoil = timeEndRecoil;
	}
}

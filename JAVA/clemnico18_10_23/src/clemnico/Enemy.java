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
	
	public abstract void chooseAnimation();
	
	public abstract void useDefaultAnimations();
	
	
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
}

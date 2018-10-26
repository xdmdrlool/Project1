package clemnico;

import java.util.ArrayList;

public class EnemyShoot extends Enemy {
	
	////Attributs////
	private int reloadShoot=40;	//fréquence de tir (0=max)
	private int timeShoot=0;	//durée depuis le dernier tir
	private int bulletSize=30;
	private int bulletSpeed=15;
	
	public ArrayList<Projectile> projectiles=new ArrayList<>();
	
	//Son
	Sound sound=new Sound("pouletHit.wav");
	
	
	////Constructeur////
	public EnemyShoot(int x,int y,int width, int height,String name, boolean fallFromPlatform) {
		super(name, x,y, width, height);
		setVxOnGround(5);
		setVxMax(vxOnGround);
		setVx(vxOnGround);
		setAx(1);
		
		setvRecoil(10);
		setTimeEndRecoil(10);
		
		setFallFromPlatform(false);
	}


	////Methodes////
	
	
	
	public void projectileOperation(Panel panel,Portal portal1, Portal portal2,ArrayList<Entity> entities, int width,int height, Player player) {
		
		setTimeShoot(timeShoot+1);
		
		//Creation des projectiles
		if (timeShoot>=reloadShoot) {
			Projectile projectile=new Projectile(x+this.width/2-bulletSize/2,y+this.height/2-bulletSize/2,10,20,0, bulletSize, this, bulletSpeed);
			projectile.useDefaultAnimations();
			projectile.setCurrentAnimation(NameAnimation.DEFAULT);
			panel.addToMainLayer(projectile);
			projectile.directionThrow(player.getX(), player.getY());
			projectiles.add(projectile);
	
			setTimeShoot(0);
			
		}
		
		//Refresh des projectiles et enregistrement de ceux à détruire
		ArrayList<Projectile> toRemove = new ArrayList<>();
		for (Projectile projectile : projectiles) {
			projectile.step(portal1,portal2);
			if(projectile.isOut(width, height,panel.getxOffset(),panel.getyOffset(), entities)) {
				toRemove.add(projectile);
			}
		}
		
		//Destruction des projectiles
		for (Projectile projectile : toRemove) {
			projectiles.remove(projectile);
			panel.deleteEntity(projectile);
		}
	}
	
	
	
	public void movement() {
//		setvRecoil(10);
		
		//Mouvement vertical du joueur
		if (inTheAir) {fall();}
		//S'il se fait toucher
		if (isRecoil()) {
			setX(x+getvRecoil());
			setTimeRecoil(getTimeRecoil() + 1);
		}
		//Sinon mouvement normal
		else{
			setVx((int)(vx+Math.signum(vx)*ax));
			setX(x+vx);
		}
		//Temps de recul
		if (timeRecoil==timeEndRecoil) {
			setRecoil(false);
		}
	}
	
	public void touched(int vxProjectile, int vyProjectile) {
		sound.play();
		setRecoil(true);
		setTimeRecoil(0);
		setvRecoil((int) Math.signum(vxProjectile)*Math.abs(getvRecoil()));
	}
	
	public void step(Window window,Portal portal1, Portal portal2,ArrayList<Obstacle> obstacles,ArrayList<Entity> entities, Player player) {
		
		movement();
				
		fc.portalInteractionRect(this, portal1, portal2);	// Gestion portails teleportations
		fc.obstacleInteractionEnemy(this, obstacles);		// Gestion obstacle
		
		projectileOperation(window.panel, portal1, portal2, entities, window.getWidth(), window.getHeight(), player);
		
		getCurrentAnimation().update();	
		chooseAnimation();
		
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	
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
	
	public int getBulletSize() {
	return bulletSize;
	}
	
	public void setBulletSize(int bulletSize) {
	this.bulletSize = bulletSize;
	}


	public int getBulletSpeed() {
		return bulletSpeed;
	}


	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

}
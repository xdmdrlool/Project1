package clemnico;

import java.util.ArrayList;

public class EnemyShoot extends Enemy{
	
	private boolean shooting=false;
	private int reloadShoot=10;	//fréquence de tir (0=max)
	private int timeShoot=0;	//durée depuis le dernier tir
	private int bulletSize=30;
	
	public ArrayList<Projectile> projectiles=new ArrayList<>();

	public EnemyShoot(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public void touched(int vxProjectile, int vyProjectile) {
		// TODO Auto-generated method stub
		
	}

	public void step(Portal portal1, Portal portal2, ArrayList<Obstacle> obstacles) {
		
	}
	
	
public void projectileOperation(Panel panel,Portal portal1, Portal portal2,ArrayList<Entity> entities, int width,int height,ArrayList<Obstacle> obstacles) {
		
		setTimeShoot(timeShoot+1);
		
		//Creation des projectiles
		if (shooting && timeShoot>=reloadShoot) {
			Projectile projectile=new Projectile(x+this.width/2-bulletSize/2,y+this.height/2-bulletSize/2,10,20,0, bulletSize);
			projectile.useDefaultAnimations();
			projectile.setCurrentAnimation(NameAnimation.DEFAULT);
			panel.addToMainLayer(projectile);
			projectile.directionThrow(this, 1, 0);
			projectiles.add(projectile);
	
			setTimeShoot(0);
			
		}
		
		//Refresh des projectiles et enregistrement de ceux à détruire
		ArrayList<Projectile> toRemove = new ArrayList<>();
		for (Projectile projectile : projectiles) {
			projectile.step(portal1,portal2);
			if(projectile.isOut(width, height,panel.getxOffset(),panel.getyOffset(), entities, this)) {
				toRemove.add(projectile);
			}
		}
		
		//Destruction des projectiles
		for (Projectile projectile : toRemove) {
			projectiles.remove(projectile);
			panel.deleteEntity(projectile);
		}
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

	public int getBulletSize() {
		return bulletSize;
	}

	public void setBulletSize(int bulletSize) {
		this.bulletSize = bulletSize;
	}

}

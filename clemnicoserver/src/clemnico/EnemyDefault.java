package clemnico;

import java.util.ArrayList;

public class EnemyDefault extends Enemy {
	
	////Attributs////
	
	//Son
	Sound sound=new Sound("pouletHit.wav");
	
	
	////Constructeur////
	public EnemyDefault(int x,int y,int width, int height,String name, boolean fallFromPlatform) {
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
	public void movement() {
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
	
	public void step(Window window,ArrayList<Player> listPlayer,ArrayList<Obstacle> obstacles,ArrayList<Entity> entities) {
		
		movement();
		// Gestion portails teleportations
		for(Player player : listPlayer)	{
			fc.portalInteractionRect(this, player.portal1, player.portal2);	
		}
		fc.obstacleInteractionEnemy(this, obstacles);		// Gestion obstacle
		getCurrentAnimation().update();	
		chooseAnimation();
		
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////

}



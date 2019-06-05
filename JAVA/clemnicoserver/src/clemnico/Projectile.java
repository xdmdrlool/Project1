package clemnico;

import java.awt.Color;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Projectile extends Entity{
	
	
	////Attributs////
	private int vNorm=15;
	private double angle=0;
	private int xLimit=1000;
	private int yLimit=1000;
	private double vxReal;
	private double vyReal;
	private int flyTime=0;


	
	////Constructeur////
	public Projectile(int x, int y, int width, int height, double angle, int size, Entity owner, int vNorm) {
		super("nom a la con",x,y,width,height);
		setvNorm(vNorm);
		setAngle(angle);
		setVx(1);
		setVy(1);
		setVxMax(vNorm);
		setVyMax(vNorm);
		setHeight(size);
		setWidth(size);
		setOwner(owner);
		listeNoCollisonWith= new Class[] {ObstacleFixSurfaceNoPortal.class,ObstacleFixGateNoPlayer.class};
	}
	
	////Méthodes////
	public void display(Graphics2D gg) {
		Sprite sprite =currentAnimation.getSprite();
		sprite.render(gg, x+width/2, y+height/2);
	}
	
	public void directionThrow(int xClic, int yClic) {
		int xp=owner.getX()+owner.getWidth()/2;
		int yp=owner.getY()+owner.getHeight()/2;
		int xc=xClic-width/2;
		int yc=yClic-height/2;
		
		double norm=Math.sqrt((xp-xc)*(xp-xc)+(yp-yc)*(yp-yc));
		if ((int)(norm)==0) {
			setVx(vNorm);
			setVy(0);
		}
		double[] vector= {(xc-xp),(yc-yp)};
		setVx((int)(vNorm*vector[0]*1./norm));
		setVy((int)(vNorm*vector[1]*1./norm));
	}
	
	
	public void moveIn(int x, int y) {
		setX(x);
		setY(y);
	}
	
	
	//Détermine si le projectile doit être détruit ou non
	public boolean isOut(int w, int h,int xoff,int yoff,ArrayList<Entity> entities) {
		//Hors des limites du terrain
		if (x+xoff+width+xLimit<0 || y+yoff+height+yLimit<0 || x+xoff-xLimit>w || y+yoff-yLimit>h) {
			return true;
		}
		
		//Percute quelque chose
		for(Entity entity : entities) {
			
			//Variable rassemblant les éléments non touchables
			boolean untouchable = (owner.hashCode()!=entity.getOwner().hashCode() && !(entity instanceof Portal) && entity.getClass().getSuperclass()!=owner.getClass().getSuperclass());
			for (@SuppressWarnings("rawtypes") Class cla :this.listeNoCollisonWith) {if(cla.isInstance(entity)){untouchable=false;}}
			if (this.isInCollisionWith(entity) && untouchable) {
//				System.out.println(this.toString());
//				System.out.println(entity.toString());
				entity.touched(vx,vy);
				return true;
			}
		}
		
		return false;
	}
	
	public void touched(int vx2, int vy2) {
		// TODO Auto-generated method stub
		
	}
	
	public void step(ArrayList<Player> listPlayer) {
		for(Player player : listPlayer) {
			fc.portalInteractionRect(this, player.portal1, player.portal2);
		}
		setFlyTime(flyTime+1);
		setxBefore(x);
		setyBefore(y);
		setX(x+vx);
		setY(y+vy);
		
		getCurrentAnimation().update();
	}
	
	
	
	@Override
	public void useDefaultAnimations() {
		if (owner instanceof Player) {
			addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationProjectileDefault,width,height));
		}else {
			addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationProjectileEnemy,width,height));
		}
		setCurrentAnimation(NameAnimation.DEFAULT);
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	

	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
		this.form.setAngle(angle);
		this.hitbox.setAngle(angle);
		if (this.currentAnimation != null) {this.currentAnimation.setAngle(angle);}		
	}


	public void setCurrentAnimation(NameAnimation name) {
		Animation anime = ListAnimation.get(name);
		if (this.currentAnimation!=anime) {
			this.currentAnimation=anime;
			this.currentAnimation.reset();}
		this.currentAnimation.setAngle(this.getAngle());
	}
		
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		setCurrentAnimation(name);

	}

	public int getvNorm() {
		return vNorm;
	}

	public void setvNorm(int vNorm) {
		this.vNorm = vNorm;
	}

	public double getVxReal() {
		return vxReal;
	}

	public void setVxReal(double vxReal) {
		this.vxReal = vxReal;
	}

	public double getVyReal() {
		return vyReal;
	}

	public void setVyReal(double vyReal) {
		this.vyReal = vyReal;
	}

	public int getFlyTime() {
		return flyTime;
	}

	public void setFlyTime(int flyTime) {
		this.flyTime = flyTime;
	}

}

package clemnico;

import java.awt.Color;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Projectile extends Entity{
	
	
	////Attributs////
	private int vNorm=10;
	private double angle=0;


	
	////Constructeur////
	public Projectile(int x, int y, int width, int height, double angle) {
		super("nom a la con",x,y,width,height);
		setAngle(angle);
		setVx(1);
		setVy(1);
		setHeight(30);
		setWidth(30);
	}
	
	////M�thodes////
	public void display(Graphics2D gg) {
		Sprite sprite =currentAnimation.getSprite();
		sprite.render(gg, x+width/2, y+height/2);
	}
	
	public void directionThrow(Player player, int xClic, int yClic) {
		int xp=player.getX()+player.getWidth()/2;
		int yp=player.getY()+player.getHeight()/2;
		
		double norm=Math.sqrt((xp-xClic)*(xp-xClic)+(yp-yClic)*(yp-yClic));
		if (norm==0) {
			setVx(vNorm);
			setVy(0);
		}
		double[] vector= {(xClic-xp)*1./norm,(yClic-yp)*1./norm};
		setVx((int)(vNorm*vector[0]));
		setVy((int)(vNorm*vector[1]));
	}
	
	
	public void moveIn(int x, int y) {
		setX(x);
		setY(y);
	}
	
	
	//Ajoute le projectile � array pour l'enlever ensuite
	public ArrayList<Projectile> isOut(ArrayList<Projectile> array, int w, int h,int xoff,int yoff, boolean obstacleCollision) {
		if (x+xoff+width<0 || y+yoff+height<0 || x+xoff>w || y+yoff>h || obstacleCollision) {
			setX(-100);
			setY(-100);
			array.add(this);
			return array;
		}
		return array;
	}
	
	public void step(int period, Player player, int xMouse, int yMouse) {
		setX(x+vx);
		setY(y+vy);
	}
	
	
	public void entityInteraction(Entity entity) {
		entity.setX(entity.getX()-10);
	}
	
	
	@Override
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationProjectileDefault,width,height));		
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

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(NameAnimation name) {
		Animation anime = ListAnimation.get(name);
		if (this.currentAnimation!=anime) {
			this.currentAnimation=anime;
			this.currentAnimation.reset();}
		this.currentAnimation.setAngle(this.getAngle());
	}
		
	public Map<NameAnimation,Animation> getListAnimation() {
		return ListAnimation;
	}


	public void setListAnimation(Map<NameAnimation,Animation> listAnimation) {
		ListAnimation = listAnimation;
	}



	public void addAnimation(NameAnimation name,Animation animation) {
		this.ListAnimation.put(name,animation);
	}
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		setCurrentAnimation(name);

	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getvNorm() {
		return vNorm;
	}

	public void setvNorm(int vNorm) {
		this.vNorm = vNorm;
	}

	public int getxBefore() {
		return xBefore;
	}

	public void setxBefore(int xBefore) {
		this.xBefore = xBefore;
	}

	public int getyBefore() {
		return yBefore;
	}

	public void setyBefore(int yBefore) {
		this.yBefore = yBefore;
	}

	public int getTimeInAir() {
		return timeInAir;
	}

	public void setTimeInAir(int timeInAir) {
		this.timeInAir = timeInAir;
	}




}

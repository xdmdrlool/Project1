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
	private int xLimit=1000;
	private int yLimit=1000;


	
	////Constructeur////
	public Projectile(int x, int y, int width, int height, double angle, int size) {
		super("nom a la con",x,y,width,height);
		setAngle(angle);
		setVx(1);
		setVy(1);
		setHeight(size);
		setWidth(size);
	}
	
	////Méthodes////
	public void display(Graphics2D gg) {
		Sprite sprite =currentAnimation.getSprite();
		sprite.render(gg, x+width/2, y+height/2);
	}
	
	public void directionThrow(Player player, int xClic, int yClic) {
		int xp=player.getX()+player.getWidth()/2;
		int yp=player.getY()+player.getHeight()/2;
		int xc=xClic-width/2;
		int yc=yClic-height/2;
		
		double norm=Math.sqrt((xp-xc)*(xp-xc)+(yp-yc)*(yp-yc));
		if ((int)(norm)==0) {
			setVx(vNorm);
			setVy(0);
		}
		double[] vector= {(xc-xp)*1./norm,(yc-yp)*1./norm};
		setVx((int)(vNorm*vector[0]));
		setVy((int)(vNorm*vector[1]));
	}
	
	
	public void moveIn(int x, int y) {
		setX(x);
		setY(y);
	}
	
	
	//Ajoute le projectile à array pour l'enlever ensuite
	public void isOut(ArrayList<Projectile> array, int w, int h,int xoff,int yoff, ArrayList<Obstacle> obstacles) {
		if (x+xoff+width+xLimit<0 || y+yoff+height+yLimit<0 || x+xoff-xLimit>w || y+yoff-yLimit>h) {
			array.add(this);
		}
		else {
			for(Obstacle obstacle : obstacles) {
				if(this.isInCollisionWith(obstacle)) {
					array.add(this);
					break;
				}
			}
		}
	}
	
	public void step(int period, Player player, int xMouse, int yMouse) {
		setxBefore(x);
		setyBefore(y);
		setX(x+vx);
		setY(y+vy);
	}
	
	
	public void enemyInteraction(GeneralEnemy enemy, ArrayList<Projectile> toRemove) {
		if (this.isInCollisionWith(enemy)) {
			enemy.touched(vx,vy);
			toRemove.add(this);
		}
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

}

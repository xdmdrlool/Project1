package clemnico;

import java.awt.Graphics2D;
import java.util.Map;

public abstract class Entity {

	public Entity(int x, int y) {
		
	}
	public abstract int getX();
	public abstract void setX(int x);
	public abstract int getxBefore();
	public abstract void setxBefore(int xBefore);
	public abstract int getY();
	public abstract void setY(int y);
	public abstract int getyBefore();
	public abstract void setyBefore(int yBefore);
	public abstract int getVx();
	public abstract void setVx(int vx);
	public abstract int getVy();
	public abstract void setVy(int vy);
	public abstract int getWidth();
	public abstract void setWidth(int width);
	public abstract int getHeight();
	public abstract void setHeight(int height);
	public abstract Hitbox getHitbox();
	public abstract void setHitbox(Hitbox hitbox);
	public abstract int getTimeInAir();
	public abstract void setTimeInAir(int timeInAir);
	public abstract boolean isInTheAir();
	public abstract void setInTheAir(boolean inTheAir);
	public abstract Map<NameAnimation,Animation> getListAnimation();
	public abstract void setListAnimation(Map<NameAnimation,Animation> listAnimation);
	public abstract Animation getCurrentAnimation();
	public abstract void setCurrentAnimation(NameAnimation name);
	public abstract void addAnimation(NameAnimation name,Animation animation);
	public abstract void display(Graphics2D g);
	public abstract void chooseAnimation();
}
package clemnico;

import java.awt.Graphics2D;
import java.util.Map;

public abstract class Entity {

	public Entity(int x, int y) {
		
	}
	public abstract int getX();
	public abstract void setX(int x);
	public abstract int getY();
	public abstract void setY(int y);
	public abstract Hitbox getHitbox();
	public abstract void setHitbox(Hitbox hitbox);
	public abstract Map<NameAnimation,Animation> getListAnimation();
	public abstract void setListAnimation(Map<NameAnimation,Animation> listAnimation);
	public abstract Animation getCurrentAnimation();
	public abstract void setCurrentAnimation(NameAnimation name);
	public abstract void addAnimation(NameAnimation name,Animation animation);
	public abstract void display(Graphics2D g);
	public abstract void chooseAnimation();
}
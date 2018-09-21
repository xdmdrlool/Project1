package clemnico;

import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Entity {

	public Entity(int x, int y) {
		
	}
	public abstract int getX();
	public abstract void setX(int x);
	public abstract int getY();
	public abstract void setY(int y);
	public abstract Hitbox getHitbox();
	public abstract void setHitbox(Hitbox hitbox);
	public abstract Animation getAnimation();
	public abstract void setAnimation(Animation animation);
	public abstract void display(Graphics2D g);
}

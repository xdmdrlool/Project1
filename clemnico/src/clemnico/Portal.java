package clemnico;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Portal extends Entity{
	
	private int x=0;
	private int y=0;
	private int height=10;
	private int width=10;
	private double angle=0;
	private boolean moved;
	private FormRect form= new FormRect(Color.BLUE,x,y,width, height, angle);
	private Hitbox hitbox=new Hitbox("RECT",x,y+height/4,10,height/2,width,angle);
	private Animation currentAnimation;
	public Map<NameAnimation,Animation> ListAnimation=new  HashMap<>();
	
	////Constructeur////
	public Portal(int x, int y, int width, int height){
		super(x,y);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	////Méthodes////
	public void setRotation(int xPlayer, int yPlayer, int xClic, int yClic) {
		if (xClic==xPlayer) {
			
			this.setAngle(0);
		}
		else if (xClic>xPlayer){
			this.setAngle((int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) - Math.PI/2.0));
		}
		else {
			this.setAngle((int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) + Math.PI/2.0));
		}
	}
	
	public void display(Graphics2D gg) {
		Sprite sprite =currentAnimation.getSprite();
		sprite.render(gg, x+width/2, y+height/2);
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		this.form.setX(x);
		this.hitbox.setX(x);
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		this.form.setY(y);;
		this.hitbox.setY(y+height/4);
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
		this.form.setAngle(angle);;
		this.hitbox.setAngle(angle);
		this.currentAnimation.setAngle(angle);
	}
	public FormRect getForm() {
		return form;
	}
	public void setForm(FormRect form) {
		this.form = form;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		this.form.setHeight(height);;
		this.hitbox.setHeight(height/2);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.form.setWidth(width);;
		this.hitbox.setWidth(width);
	}
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(NameAnimation name) {
		this.currentAnimation=ListAnimation.get(name);
		this.currentAnimation.reset();
		
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
}

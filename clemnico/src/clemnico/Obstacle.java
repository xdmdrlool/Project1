package clemnico;

import java.awt.Color;

import java.awt.Graphics2D;

public class Obstacle extends Entity {
	
	private int x=0;
	private int y=0;
	private int height=10;
	private int width=10;
	private double angle=0;
	private FormRect form= new FormRect(Color.darkGray,x,y,width, height, angle);
	private Hitbox hitbox=new Hitbox("RECT",x,y+height/4,10,height/2,width,angle);
	private Animation animation;
	
	public Obstacle(int x, int y, int width,int height,double angle) {
		super(x,y);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setAngle(angle);
	}
	
	public void display(Graphics2D gg) {
		Sprite sprite =animation.getSprite();
		sprite.render(gg, x, y);
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
		this.form.setAngle(angle);
		this.hitbox.setAngle(angle);
		if (this.animation != null) {this.animation.setAngle(angle);}		
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

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}



	public Animation getAnimation() {
		return animation;
	}



	public void setAnimation(Animation animation) {
		this.animation = animation;
		this.animation.setAngle(this.getAngle());
	}


}



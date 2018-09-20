package clemnico;

import java.awt.Color;

public class Portal {
	
	private int x=0;
	private int y=0;
	private int height=10;
	private int width=10;
	private double angle=0;
	private boolean moved;
	private FormRect form= new FormRect(Color.BLUE,x,y,width, height, angle);
	private Hitbox hitbox=new Hitbox("RECT",x,y,0,width,height,angle);
	
	public Portal(int x, int y, int height, int width){
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public void setRotation(int xPlayer, int yPlayer, int xClic, int yClic) {
		if (xClic==xPlayer) {
			
			this.setAngle(0);
		}
		else {
			this.setAngle((int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) + Math.PI/2.0));
		}
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
		this.hitbox.setY(y);
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
		this.form.setAngle(angle);;
		this.hitbox.setAngle(angle);
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
		this.hitbox.setHeight(height);
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
}

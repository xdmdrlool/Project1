package clemnico;

import java.awt.Color;

public class Portal {
	
	private int x;
	private int y;
	private int height;
	private int width;
	private int angle;
	private boolean moved;
	private Form form;
	private Hitbox hitbox;
	
	public Portal(int x, int y, int height, int width){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.hitbox=new Hitbox(y,y,height,width);
		
		int[] array = {x,y,width, height, 0};
		FormRect rect= new FormRect(Color.BLUE,array);
		
		this.setForm(rect);
	}
	
	public void setRotation(int xPlayer, int yPlayer, int xClic, int yClic) {
		if (xClic==xPlayer) {
			this.form.getArg()[4]=0;
		}
		else {
			this.form.getArg()[4]=(int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) + Math.PI/2.0);
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
		this.form.getArg()[0]=x;
		this.hitbox.setX(x);
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		this.form.getArg()[1]=y;
		this.hitbox.setY(y);
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
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

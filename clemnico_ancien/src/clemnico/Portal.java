package clemnico;

import java.awt.Color;

public class Portal {
	
	private int x;
	private int y;
	private int height;
	private int width;
	private double angle;
	private Form form;
	
	public Portal(int x, int y, int height, int width, double angle){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.angle=angle;
		
		int[] array = {x,y,width, height};
		FormRect rect= new FormRect(Color.BLUE,array);
		
		this.setForm(rect);
	}

	public void moveIn(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void actionMouse(int xClic,int yClic) {
		this.moveIn(xClic, yClic);
	}
	

	public void step() {
		this.moveIn(this.x,this.y);
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
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
}

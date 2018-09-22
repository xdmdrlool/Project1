package clemnico;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class FormCircle extends Form {

	private String type;
	private Color color;
	private int x=100;
	private int y=100;
	private int radius=20;


	public FormCircle(Color color, int x, int y,int radius) {
		super(color);
		this.type="CIRCLE";
		this.setColor(color);
		this.setX(x);
		this.setY(y);
		this.setRadius(radius);
		// TODO Auto-generated constructor stub
	}


	public void draw(Graphics2D graphic) {

		graphic.setColor(this.color);
		graphic.fillOval(x-radius, y-radius, 2*radius, 2*radius);
	}
	
	
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public String getType() {
		return type;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}




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


	public int getRadius() {
		return radius;
	}


	public void setRadius(int rayon) {
		this.radius = rayon;
	}


	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}





	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setAngle(double angle) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		
	}





}
package clemnico;


import java.awt.Color;
import java.awt.Graphics2D;

public class FormCircle extends Form {

	private String type;
	private Color color;
	private int x=100;
	private int y=100;
	private int rayon=20;


	public FormCircle(Color color, int x, int y,int rayon) {
		super(color);
		this.type="CIRCLE";
		this.setColor(color);
		this.setX(x);
		this.setY(y);
		this.setRayon(rayon);
		// TODO Auto-generated constructor stub
	}


	public void draw(Graphics2D graphic) {

		graphic.setColor(this.color);
		graphic.fillOval(x-rayon, y-rayon, 2*rayon, 2*rayon);
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


	public int getRayon() {
		return rayon;
	}


	public void setRayon(int rayon) {
		this.rayon = rayon;
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
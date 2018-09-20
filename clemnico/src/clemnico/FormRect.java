package clemnico;


import java.awt.Color;
import java.awt.Graphics2D;


public class FormRect extends Form {

	private String type;
	private Color color;
	private int x=100;
	private int y=100;
	private int width=20;
	private int height =20;
	private double angle=0;
	private Graphics2D graphic;


	public FormRect(Color color,int x, int y,int w,int h,double angle ) {
		super(color);
		this.type="RECT";
		this.setColor(color);
		this.setX(x);
		this.setY(y);
		this.setWidth(w);
		this.setHeight(h);
		this.setAngle(angle);

		// TODO Auto-generated constructor stub
	}


	public void draw(Graphics2D graphic) {
		int x =this.x;
		int y =this.y;
		int width =this.width;
		int height =this.height;
		double angle = Math.toRadians(this.angle);
		
		this.setGraphic(graphic);
		
		graphic.setColor(this.color);
		graphic.rotate(angle,x+width/2,y+height/2);
		graphic.fillRect(x, y, width, height);
		graphic.rotate(-angle,x+width/2,y+height/2);
		
		
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




	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}


	public Graphics2D getGraphic() {
		return graphic;
	}


	public void setGraphic(Graphics2D graphic) {
		this.graphic = graphic;
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


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	@Override
	public int getRayon() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setRayon(int rayon) {
		// TODO Auto-generated method stub
		
	}












}
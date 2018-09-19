package clemnico;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class FormRect extends Form {

	private String type;
	private Color color;
	private int[] arg;
	private int angle=0;
	private Graphics2D graphic;


	public FormRect(Color color, int[] arg) {
		super(color, arg);
		this.type="RECT";
		this.setColor(color);
		this.setArg(arg);
		// TODO Auto-generated constructor stub
	}


	public void draw(Graphics2D graphic) {
		int x =this.arg[0];
		int y =this.arg[1];
		int width =this.arg[2];
		int height =this.arg[3];
		double angle = Math.toRadians(this.arg[4]);
		
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


	public int[] getArg() {
		return arg;
	}


	public void setArg(int[] arg) {
		this.arg = arg;
	}


	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}


	public Graphics2D getGraphic() {
		return graphic;
	}


	public void setGraphic(Graphics2D graphic) {
		this.graphic = graphic;
	}







}
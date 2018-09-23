package clemnico;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Form {
	
	public Form(Color color ) {
		
	}
	public abstract void draw(Graphics2D g);
	
	public abstract String getType();
	public abstract Color getColor();
	public abstract void setColor(Color color0);
	public abstract int getX();
	public abstract void setX(int x);
	public abstract int getY();
	public abstract void setY(int y);
	public abstract int getRadius();
	public abstract void setRadius(int radius);
	public abstract int getWidth();
	public abstract void setWidth(int width);
	public abstract int getHeight();
	public abstract void setHeight(int height);
	public abstract double getAngle();
	public abstract void setAngle(double angle);
	
	
}	

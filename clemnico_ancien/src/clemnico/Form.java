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
	
	
	
}	

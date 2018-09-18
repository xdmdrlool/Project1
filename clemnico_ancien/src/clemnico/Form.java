package clemnico;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Form {
	
	public Form(Color color, int[] arg ) {
		
	}
	public abstract void draw(Graphics graphic);
	
	public abstract String getType();
	public abstract Color getColor();
	public abstract void setColor(Color color0);
	public abstract int[] getArg();
	public abstract void setArg(int[] arg0);
	
	
}	

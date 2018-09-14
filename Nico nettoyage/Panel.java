package fr.nicolas.snakex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
 
public class Panel extends JPanel { 
	
	////Attributs////
	protected int xPlayer =100 ; 
	protected int yPlayer =100;
	protected int rPlayer =50;

	
	public void paintComponent(Graphics g){  
		super.paintComponent(g);
		g.fillOval(xPlayer-rPlayer, yPlayer-rPlayer, 2*rPlayer, 2*rPlayer);
		
		
//	    int x1 = this.getWidth()/4;
//
//	    int y1 = this.getHeight()/4;                      
//
//	    g.drawOval(x1, y1, this.getWidth()/2, this.getHeight()/2);
//	    
//	  //x1, y1, width, height
//
//	    g.fillRect(65, 65, 30, 40);
//	    
//	  //x1, y1, width, height, arcWidth, arcHeight
//
//	    g.drawRoundRect(10, 10, 30, 50, 10, 10);
//	    
//	  //x1, y1, x2, y2
//
//	    g.drawLine(0, 0, this.getWidth(), this.getHeight());
//	    
//	    int x[] = {20, 30, 50, 60, 60, 50, 30, 20};
//
//	    int y[] = {30, 20, 20, 30, 50, 60, 60, 50};
//
//	    g.drawPolygon(x, y, 8);
//	    g.drawPolyline(x, y, 8);
//	    
//	    
//	    
//	    Font font = new Font("Courier", Font.BOLD, 20);
//	    g.setFont(font);
//	    g.setColor(Color.red);          
//	    g.drawString("Tiens ! je suis la !", 10, 20); 
  }

	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public int getX0() {
		return xPlayer;
	}
	public void setX0(int xPlayer) {
		this.xPlayer = xPlayer;
	}
	public int getY0() {
		return yPlayer;
	}
	public void setY0(int yPlayer) {
		this.yPlayer = yPlayer;
	}
	public int getR0() {
		return rPlayer;
	}
	public void setR0(int rPlayer) {
		this.rPlayer = rPlayer;
	}        

	
}
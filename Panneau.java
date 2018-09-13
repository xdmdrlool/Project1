package fr.clement.InterfaceGraphique;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
 
public class Panneau extends JPanel { 
	private int x0 =100 ; 
	private int y0 =100;
	private int r0 =10;

	public void paintComponent(Graphics g){  
		super.paintComponent(g);
		g.fillOval(x0-r0, y0-r0, 2*r0, 2*r0);
		
		
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

	public int getX0() {
		return x0;
	}
	public void setX0(int x0) {
		this.x0 = x0;
	}
	public int getY0() {
		return y0;
	}
	public void setY0(int y0) {
		this.y0 = y0;
	}
	public int getR0() {
		return r0;
	}
	public void setR0(int r0) {
		this.r0 = r0;
	}        

	
}
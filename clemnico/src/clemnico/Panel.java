package clemnico;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import clemnico.FC.Vecteur;
 
public class Panel extends JPanel { 
	
	////Attributs////
	protected ArrayList<Entity> formList= new ArrayList<Entity>();
	private int xOffset=0;
	private int yOffset=0;
	
	////Constructeur////
	public Panel() {

	}
	
	
	
	public synchronized void paintComponent(Graphics g){  
		super.paintComponent(g);
		Graphics2D gg= (Graphics2D) g;		
		for (Entity entity :formList) {
			entity.display(gg,xOffset,yOffset);
		}
		
	
//		g.fillOval(xPlayer-rPlayer, yPlayer-rPlayer, 2*rPlayer, 2*rPlayer);
		
//		ArrayList<Integer> array =new ArrayList<Integer>();
//		array.add(100);
//		array.add(100);
//		array.add(200);
//		array.add(100);
//		FormRect rect = new FormRect(Color.RED,array );
//		rect.draw(g);
		
		
		
		
		
		
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
	

	public ArrayList<Entity> getEntityList() {
		return formList;
	}
	public void setEntityList(ArrayList<Entity> formList) {
		this.formList = formList;
	}



	public int getxOffset() {
		return xOffset;
	}



	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}



	public int getyOffset() {
		return yOffset;
	}



	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}        

	
}
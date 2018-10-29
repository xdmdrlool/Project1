package clemnico;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import clemnico.FC.Vecteur;
 
public class Panel extends JPanel { 
	
	////Attributs////
	private int xOffset=0;
	private int yOffset=0;
	
	
	private ArrayList<Layer> listLayer=new ArrayList<Layer>();

	
	////Constructeur////
	public Panel() {

	}
	
	//// Methodes////
	
	public synchronized void paintComponent(Graphics g){  
		super.paintComponent(g);
		Graphics2D gg= (Graphics2D) g;				
		for (Layer layer :listLayer) {
			layer.display(gg,xOffset,yOffset);
		}
	

  }

	
	

	public void triLayer() {
		Layer a;
		int j;
		for (int i=0;i<listLayer.size();i++) {
			j=i;
			while (j>0 && (listLayer.get(j).getDistance()>listLayer.get(j-1).getDistance())) {
				a=listLayer.get(j-1);
				listLayer.set(j-1, listLayer.get(j));
				listLayer.set(j, a);
				j-=1;
			}
		}
	}
	
	

	

	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	




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



	public ArrayList<Layer> getListLayer() {
		return listLayer;
	}



	public void setListLayer(ArrayList<Layer> listLayer) {
		this.listLayer = listLayer;
		triLayer();
	}

       

	
}
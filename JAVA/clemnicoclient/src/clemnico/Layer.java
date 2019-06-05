package clemnico;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class Layer {
	
	////Attrbiuts////
	float distance =0;
	public ArrayList<Entity> listEntity=new ArrayList<Entity>();
	
	
	////Constructeur////
	public Layer(float distance) {
		setDistance(distance);
		
	}

	
	
	////Méthodes////
	
	public synchronized void display(Graphics2D gg,int xoff, int yoff,int w,int h) {
		double calibrage=0.1;
		for(Entity e:listEntity) {
			e.display(gg,((int)( xoff/(1+Math.abs(distance)*calibrage))),((int)( yoff/(1+Math.abs(distance)*calibrage))),w,h);
		}
	}
	
	
	public void add(Entity e) {
		e.setLayerIn(this);
		listEntity.add(e);
	}

	
	
	public boolean remove(Entity e) {
		return listEntity.remove(e);
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public float getDistance() {
		return distance;
	}

	public ArrayList<Entity> getListEntity() {
		return listEntity;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public void setListEntity(ArrayList<Entity> listEntity) {
		this.listEntity = listEntity;
	}
	


}

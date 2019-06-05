package clemnico;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Layer {
	
	
	////Attrbiuts////
	int distance =0;
	ArrayList<Entity> listEntity=new ArrayList<Entity>();
	
	
	
	
	////Constructeur////
	public Layer(int distance) {
		setDistance(distance);
		
	}

	
	
	////Méthodes////
	
	public void display(Graphics2D gg,int xoff, int yoff) {
		double calibrage=0.1;
		for(Entity e:listEntity) {
			e.display(gg,((int)( xoff/(1+Math.abs(distance)*calibrage))),((int)( yoff/(1+Math.abs(distance)*calibrage))));
		}
	}
	
	
	public void add(Entity e) {
		listEntity.add(e);
	}
	
	public boolean remove(Entity e) {
		return listEntity.remove(e);
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public int getDistance() {
		return distance;
	}

	public ArrayList<Entity> getListEntity() {
		return listEntity;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setListEntity(ArrayList<Entity> listEntity) {
		this.listEntity = listEntity;
	}
	


}

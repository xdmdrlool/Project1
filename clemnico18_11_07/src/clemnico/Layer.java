package clemnico;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class Layer {
	
	////Attrbiuts////
	int distance =0;
	public ArrayList<Entity> listEntity=new ArrayList<Entity>();
	public ArrayList<Player> listPlayer=new ArrayList<Player>();
	public ArrayList<Portal> listPortal=new ArrayList<Portal>();
	public ArrayList<Obstacle> listObstacle=new ArrayList<Obstacle>();
	public ArrayList<Projectile> listProjectile=new ArrayList<Projectile>();
	public ArrayList<Enemy> listEnemy=new ArrayList<Enemy>();
	
	
	////Constructeur////
	public Layer(int distance) {
		synchronized(listEntity) {};
		synchronized(listPlayer) {};
		synchronized(listPortal) {};
		synchronized(listObstacle) {};
		synchronized(listProjectile) {};
		synchronized(listEnemy) {};
		setDistance(distance);
		
	}

	
	
	////Méthodes////
	
	public synchronized void display(Graphics2D gg,int xoff, int yoff) {
		double calibrage=0.1;
		for(Entity e:listEntity) {
			e.display(gg,((int)( xoff/(1+Math.abs(distance)*calibrage))),((int)( yoff/(1+Math.abs(distance)*calibrage))));
		}
	}
	
	
	public void add(Entity e) {
		e.setLayerIn(this);
		listEntity.add(e);
		if (e instanceof Player) {listPlayer.add((Player)e);}
		else if (e instanceof Portal) {listPortal.add((Portal)e);}
		else if (e instanceof Obstacle) {listObstacle.add((Obstacle)e);}
		else if (e instanceof Projectile) {listProjectile.add((Projectile)e);}
		else if (e instanceof Enemy) {listEnemy.add((Enemy)e);}
	}

	
	
	public boolean remove(Entity e) {
		if (e instanceof Player) {listPlayer.remove(e);}
		else if (e instanceof Portal) {listPortal.remove(e);}
		else if (e instanceof Obstacle) {listObstacle.remove(e);}
		else if (e instanceof Projectile) {listProjectile.remove(e);}
		else if (e instanceof Enemy) {listEnemy.remove(e);}
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

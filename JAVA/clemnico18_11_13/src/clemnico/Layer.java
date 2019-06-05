package clemnico;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class Layer {
	
	////Attrbiuts////
	float distance =0;
	
	// Listes des diffenrents types d'entity dans le layer
	public ArrayList<Entity> listEntity=new ArrayList<Entity>();
	public ArrayList<Player> listPlayer=new ArrayList<Player>();
	public ArrayList<Portal> listPortal=new ArrayList<Portal>();
	public ArrayList<Obstacle> listObstacle=new ArrayList<Obstacle>();
	public ArrayList<Projectile> listProjectile=new ArrayList<Projectile>();
	public ArrayList<Enemy> listEnemy=new ArrayList<Enemy>();
	
	
	////Constructeur////
	public Layer(float distance) {

		setDistance(distance);
		
	}

	
	
	////Méthodes////
	
	// Affichage des layers (compte-tenu de la distance)
	public synchronized void display(Graphics2D gg,int xoff, int yoff,int w,int h) {
		double calibrage=0.1;
//		synchronized (listEntity) {
			for(Entity e:listEntity) {
				e.display(gg,((int)( xoff/(1+Math.abs(distance)*calibrage))),((int)( yoff/(1+Math.abs(distance)*calibrage))),w,h);
			}
//		}
	}
	
	// Ajouter une entity au layer
	public void add(Entity e) {
		e.setLayerIn(this);
		listEntity.add(e);
		if (e instanceof Player) {listPlayer.add((Player)e);}
		else if (e instanceof Portal) {listPortal.add((Portal)e);}
		else if (e instanceof Obstacle) {listObstacle.add((Obstacle)e);}
		else if (e instanceof Projectile) {listProjectile.add((Projectile)e);}
		else if (e instanceof Enemy) {listEnemy.add((Enemy)e);}
	}

	
	// Retirer une entity du layer
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

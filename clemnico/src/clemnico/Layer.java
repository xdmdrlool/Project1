package clemnico;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Layer {
	
	
	////Attrbiuts////
	int distance =0;
	private ArrayList<Entity> listEntity=new ArrayList<Entity>();
	private ArrayList<Player> listPlayer=new ArrayList<Player>();
	private ArrayList<Portal> listPortal=new ArrayList<Portal>();
	private ArrayList<Obstacle> listObstacle=new ArrayList<Obstacle>();
	private ArrayList<Projectile> listProjectile=new ArrayList<Projectile>();
	private ArrayList<EnemyDefault> listGeneralEnemy=new ArrayList<EnemyDefault>();
	
	
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
		e.setLayerIn(this);
		listEntity.add(e);
		if (e instanceof Player) {listPlayer.add((Player)e);}
		else if (e instanceof Portal) {listPortal.add((Portal)e);}
		else if (e instanceof Obstacle) {listObstacle.add((Obstacle)e);}
		else if (e instanceof Projectile) {listProjectile.add((Projectile)e);}
		else if (e instanceof EnemyDefault) {listGeneralEnemy.add((EnemyDefault)e);}
	}

	
	
	public boolean remove(Entity e) {
		if (e instanceof Player) {listPlayer.remove(e);}
		else if (e instanceof Portal) {listPortal.remove(e);}
		else if (e instanceof Obstacle) {listObstacle.remove(e);}
		else if (e instanceof Projectile) {listProjectile.remove(e);}
		else if (e instanceof EnemyDefault) {listGeneralEnemy.remove(e);}
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

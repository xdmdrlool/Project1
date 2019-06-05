package clemnico;

import java.io.IOException;
import java.util.ArrayList;

public class Level extends Thread{
	FC fc = new FC();
	
	private static LevelLoader LLoader= new LevelLoader();
	private Window window;
	
	public ArrayList<Player> listPlayer=new ArrayList<>();
	public ArrayList<PlayerLocal> listPlayerLocal=new ArrayList<>();
	public ArrayList<PlayerWeb> listPlayerWeb=new ArrayList<>();
	
	public ArrayList<Layer> listLayer=new ArrayList<>();
	public int indexMainLayer=0;
	public ArrayList<Entity> listEntity=new ArrayList<Entity>();
//	private ArrayList<Player> listPlayer=new ArrayList<Player>();
	public ArrayList<Obstacle> listObstacle=new ArrayList<Obstacle>();
	public ArrayList<Projectile> listProjectile=new ArrayList<Projectile>();
	public ArrayList<Portal> listPortal=new ArrayList<Portal>();
	public ArrayList<Enemy> listEnemy=new ArrayList<Enemy>();
	public Discussion discussion = new Discussion(null, listPlayerWeb, listEntity);
	/*public ArrayList<Entity> enemy2Entity(ArrayList<Enemy> enemies){
		ArrayList<Entity> entities = new ArrayList<>();
		for (Enemy enemy : enemies) {
			entities.add(enemy);
		}
		return entities;
	}
	public ArrayList<Entity> player2Entity(ArrayList<Enemy> enemies){
		ArrayList<Entity> entities = new ArrayList<>();
		for (Enemy enemy : enemies) {
			entities.add(enemy);
		}
		return entities;
	}*/
	
	public Level(String name, Window window) throws Exception {
		
		setWindow(window);
		
		ArrayList<Layer> list= LLoader.load(name);
		for(Layer layer :list) {
			addLayer(layer);
		}
		
		discussion = new Discussion(listPlayerLocal.get(0), listPlayerWeb, listEntity);
		discussion.start();
		
	}
	
	public void run(){
		
		for (Player player : listPlayer) {
			player.step(window,listPlayer,player.getLayerIn().listEntity,player.getLayerIn().listObstacle);
		}
		window.calculCameraOffset(window.panel, listPlayerLocal.get(0));	//La caméra s'adapte au player 1
				
		for (Obstacle obstacle : listObstacle) {
			obstacle.update();
		}
		for (Enemy enemy : listEnemy) {
			enemy.step(window, listPlayer, enemy.getLayerIn().listObstacle, enemy.getLayerIn().listEntity);
		}
		//Pour l'instant, les ennemis tirent sur le player 1
		//discussion.update(listPlayerLocal.get(0), listPlayerWeb, listEntity);
		
	}
	
	
	
	//// Methodes////
	
	
	

	
	
	public void addLayer(Layer l) {
		for (Entity e :l.getListEntity()) {addEntityToList(e);e.setLevelIn(this);}
		listLayer.add(l);
		triLayer();
	}
	
	public void addToLayer(int i,Entity e) {
		addEntityToList(e);
		e.setLevelIn(this);
		listLayer.get(i).add(e);
	}
	
	public void addToMainLayer(Entity e) {
		addToLayer(indexMainLayer, e);
		e.setLevelIn(this);
	}
	
	public boolean deleteEntity(Entity e) {
		removeEntityToList(e);
		boolean bool=false;
		for (Layer layer :listLayer) {
			bool=layer.remove(e) || bool;
		}
		return bool;
	}
	
	public void updateIndexMainLayer() {
		for (int i=0;i<listLayer.size();i++) {
			if (listLayer.get(i).getDistance()==0) {setIndexMainLayer(i);break;};
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
		updateIndexMainLayer();
	}	
	
	private void addEntityToList(Entity e){
		synchronized(listEntity){
			listEntity.add(e);
			listEntity.notify();
		}
		
		if 		(e instanceof PlayerLocal) 	{listPlayerLocal.add((PlayerLocal)e);listPlayer.add((Player) e);}
		else if (e instanceof PlayerWeb) 	{listPlayerWeb.add((PlayerWeb) e);listPlayer.add((Player) e);}
		else if (e instanceof Obstacle) 	{listObstacle.add((Obstacle)e);}
		else if (e instanceof Projectile) 	{listProjectile.add((Projectile)e);}
		else if (e instanceof Portal) 		{listPortal.add((Portal)e);}
		else if (e instanceof Enemy) 		{listEnemy.add((Enemy)e);}
	}
	
	private boolean removeEntityToList(Entity e){
		boolean removed=false;

		if 		(e instanceof PlayerLocal) 	{listPlayerLocal.remove(e);}
		else if (e instanceof PlayerWeb) 	{listPlayerWeb.remove(e);}
		else if (e instanceof Obstacle) 	{listObstacle.remove(e);}
		else if (e instanceof Projectile) 	{listProjectile.remove(e);}
		else if (e instanceof Portal) 		{listPortal.remove(e);}
		else if (e instanceof Enemy) 		{listEnemy.remove(e);}
		synchronized(listEntity){
			discussion.updating=true;
			listEntity.remove(e);
			removed=true;
			listEntity.notify();
			
		}
		return removed;
	}
	
	
	
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	
	
	
	public ArrayList<Layer> getListLayer() {
		return listLayer;
	}

	public void setListLayer(ArrayList<Layer> listLayer) {
		this.listLayer = listLayer;
	}



	public int getIndexMainLayer() {
		return indexMainLayer;
	}



	public void setIndexMainLayer(int indexMainLayer) {
		this.indexMainLayer = indexMainLayer;
	}







	public ArrayList<Entity> getListEntity() {
		return listEntity;
	}






	public ArrayList<Obstacle> getListObstacle() {
		return listObstacle;
	}







	public ArrayList<Projectile> getListProjectile() {
		return listProjectile;
	}







	public ArrayList<Enemy> getListGeneralEnemy() {
		return listEnemy;
	}







	public void setListEntity(ArrayList<Entity> listEntity) {
		this.listEntity = listEntity;
	}





	public void setListObstacle(ArrayList<Obstacle> listObstacle) {
		this.listObstacle = listObstacle;
	}







	public void setListProjectile(ArrayList<Projectile> listProjectile) {
		this.listProjectile = listProjectile;
	}







	public void setListGeneralEnemy(ArrayList<Enemy> listGeneralEnemy) {
		this.listEnemy = listGeneralEnemy;
	}



	public Window getWindow() {
		return window;
	}



	public void setWindow(Window window) {
		this.window = window;
	}

}

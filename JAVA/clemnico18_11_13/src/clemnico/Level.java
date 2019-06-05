package clemnico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Level {
	
	private static  LevelLoader LLoader= new  LevelLoader(0,0);
	private Window window;
	public Player player=null;
	
	// Liste des layers
	public ArrayList<Layer> listLayer=new ArrayList<>();
	private int indexMainLayer=0;
	
	// Liste des differents types d'entity
	public ArrayList<Entity> listEntity=new ArrayList<Entity>();
//	private ArrayList<Player> listPlayer=new ArrayList<Player>();
	public ArrayList<Portal> listPortal=new ArrayList<Portal>();
	public ArrayList<Obstacle> listObstacle=new ArrayList<Obstacle>();
	public ArrayList<Projectile> listProjectile=new ArrayList<Projectile>();
	public ArrayList<Enemy> listEnemy=new ArrayList<Enemy>();

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
	
	public Level(String name, Window window) throws IOException {
		setWindow(window);
		LLoader.setWidthWindow(window.getWidth());
		LLoader.setHeightWindow(window.getHeight());
		synchronized(listEntity) {};
		synchronized(listPortal) {};
		synchronized(listObstacle) {};
		synchronized(listProjectile) {};
		synchronized(listEnemy) {};
		ArrayList<Layer> list= LLoader.load(name);
		for(Layer layer :list) {
			addLayer(layer);
		}
	}
	
	
	// La gameloop
	public void gameloop() {
		


		// Update du player
		player.step(window,listPortal.get(0),listPortal.get(1),player.getLayerIn().listEntity,player.getLayerIn().listObstacle);
		
		// Update de l'offset de la camera
		window.calculCameraOffset(window.panel, player);
		for (Obstacle obstacle : listObstacle) {
			obstacle.update();
		}
		// Update des enemy
		for (Enemy enemy : listEnemy) {
			enemy.step(window, listPortal.get(0), listPortal.get(1), enemy.getLayerIn().listObstacle, enemy.getLayerIn().listEntity,player);
		
		}
		
	}
	
	
	
	//// Methodes////
	
	
	

	
	// Rajouter un layer au level
	public void addLayer(Layer l) {
		for (Entity e :l.getListEntity()) {addEntityToList(e);e.setLevelIn(this);}
		listLayer.add(l);
		triLayer();
	}
	
	// Rajouter une entity au layer d'index i
	public void addToLayer(int i,Entity e) {
		addEntityToList(e);
		e.setLevelIn(this);
		listLayer.get(i).add(e);
	}
	
	// Rajouter une entity au layer principal
	public void addToMainLayer(Entity e) {
		addToLayer(indexMainLayer, e);
		e.setLevelIn(this);
	}
	
	// Enlever une entity au level
	public boolean deleteEntity(Entity e) {
		removeEntityToList(e);
		boolean bool=false;
		for (Layer layer :listLayer) {
			bool=layer.remove(e) || bool;
		}
		return bool;
	}
	
	
	
	
	
	/// Methodes privees ///
	
	
	
	
	// Reorganise les layers dans listLayer en fonction de leur distance
	private void triLayer() {
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
	
	// Ajouter une entity a la bonne liste d'entity
	private void addEntityToList(Entity e){
		listEntity.add(e);
		if (e instanceof Player) {player=(Player) e;}
		else if (e instanceof Portal) {listPortal.add((Portal)e);}
		else if (e instanceof Obstacle) {listObstacle.add((Obstacle)e);}
		else if (e instanceof Projectile) {listProjectile.add((Projectile)e);}
		else if (e instanceof Enemy) {listEnemy.add((Enemy)e);}
	}
	
	// Enleve une entity des liste d'entity
	private boolean removeEntityToList(Entity e){
		if (e instanceof Player) {;}
		else if (e instanceof Portal) {listPortal.remove(e);}
		else if (e instanceof Obstacle) {listObstacle.remove(e);}
		else if (e instanceof Projectile) {listProjectile.remove(e);}
		else if (e instanceof Enemy) {listEnemy.remove(e);}
		return listEntity.remove(e);
	}
	
	
	private void updateIndexMainLayer() {
		for (int i=0;i<listLayer.size();i++) {
			if (listLayer.get(i).getDistance()==0) {setIndexMainLayer(i);break;};
		}
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








	public ArrayList<Portal> getListPortal() {
		return listPortal;
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









	public void setListPortal(ArrayList<Portal> listPortal) {
		this.listPortal = listPortal;
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

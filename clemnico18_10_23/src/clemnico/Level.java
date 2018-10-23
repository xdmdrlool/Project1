//package clemnico;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class Level {
//	
//	private static LayerLoader LLoader= new LayerLoader();
//
//	private ArrayList<Layer> listLayer=new ArrayList<>();
//	private int indexMainLayer=0;
//	private ArrayList<Entity> listEntity=new ArrayList<Entity>();
//	private ArrayList<Player> listPlayer=new ArrayList<Player>();
//	private ArrayList<Portal> listPortal=new ArrayList<Portal>();
//	private ArrayList<Obstacle> listObstacle=new ArrayList<Obstacle>();
//	private ArrayList<Projectile> listProjectile=new ArrayList<Projectile>();
//	private ArrayList<GeneralEnemy> listGeneralEnemy=new ArrayList<GeneralEnemy>();
//	
//	
//	
//	public Level(String name) throws IOException {
//		ArrayList<Layer> list= LLoader.load(name);
//		for(Layer layer :list) {
//			addLayer(layer);
//		}
//	}
//	
//	
//	
//	//// Methodes////
//	
//	
//	
//
//	
//	
//	public void addLayer(Layer l) {
//		for (Entity e :l.getListEntity()) {addEntityToList(e);}
//		listLayer.add(l);
//		triLayer();
//	}
//	
//	public void addToLayer(int i,Entity e) {
//		addEntityToList(e);
//		listLayer.get(i).add(e);
//	}
//	
//	public void addToMainLayer(Entity e) {
//		addEntityToList(e);
//		addToLayer(indexMainLayer, e);
//	}
//	
//	public boolean deleteEntity(Entity e) {
//		removeEntityToList(e);
//		boolean bool=false;
//		for (Layer layer :listLayer) {
//			bool=layer.remove(e) || bool;
//		}
//		return bool;
//	}
//	
//	public void updateIndexMainLayer() {
//		for (int i=0;i<listLayer.size();i++) {
//			if (listLayer.get(i).getDistance()==0) {setIndexMainLayer(i);break;};
//		}
//	}
//	
//	
//	public void triLayer() {
//		Layer a;
//		int j;
//		for (int i=0;i<listLayer.size();i++) {
//			j=i;
//			while (j>0 && (listLayer.get(j).getDistance()>listLayer.get(j-1).getDistance())) {
//				a=listLayer.get(j-1);
//				listLayer.set(j-1, listLayer.get(j));
//				listLayer.set(j, a);
//				j-=1;
//			}
//		}
//		updateIndexMainLayer();
//	}	
//	
//	private void addEntityToList(Entity e){
//		listEntity.add(e);
//		if (e instanceof Player) {listPlayer.add((Player)e);}
//		else if (e instanceof Portal) {listPortal.add((Portal)e);}
//		else if (e instanceof Obstacle) {listObstacle.add((Obstacle)e);}
//		else if (e instanceof Projectile) {listProjectile.add((Projectile)e);}
//		else if (e instanceof GeneralEnemy) {listGeneralEnemy.add((GeneralEnemy)e);}
//	}
//	
//	private boolean removeEntityToList(Entity e){
//		if (e instanceof Player) {listPlayer.remove(e);}
//		else if (e instanceof Portal) {listPortal.remove(e);}
//		else if (e instanceof Obstacle) {listObstacle.remove(e);}
//		else if (e instanceof Projectile) {listProjectile.remove(e);}
//		else if (e instanceof GeneralEnemy) {listGeneralEnemy.remove(e);}
//		return listEntity.remove(e);
//	}
//	
//	
//	
//	
//	
//	////////////////////////////////
//	/////// GETTER AND SETTER //////
//	////////////////////////////////
//	
//	
//	
//	public ArrayList<Layer> getListLayer() {
//		return listLayer;
//	}
//
//	public void setListLayer(ArrayList<Layer> listLayer) {
//		this.listLayer = listLayer;
//	}
//
//
//
//	public int getIndexMainLayer() {
//		return indexMainLayer;
//	}
//
//
//
//	public void setIndexMainLayer(int indexMainLayer) {
//		this.indexMainLayer = indexMainLayer;
//	}
//
//
//
//
//
//
//
//	public ArrayList<Entity> getListEntity() {
//		return listEntity;
//	}
//
//
//
//
//
//
//
//	public ArrayList<Player> getListPlayer() {
//		return listPlayer;
//	}
//
//
//
//
//
//
//
//	public ArrayList<Portal> getListPortal() {
//		return listPortal;
//	}
//
//
//
//
//
//
//
//	public ArrayList<Obstacle> getListObstacle() {
//		return listObstacle;
//	}
//
//
//
//
//
//
//
//	public ArrayList<Projectile> getListProjectile() {
//		return listProjectile;
//	}
//
//
//
//
//
//
//
//	public ArrayList<GeneralEnemy> getListGeneralEnemy() {
//		return listGeneralEnemy;
//	}
//
//
//
//
//
//
//
//	public void setListEntity(ArrayList<Entity> listEntity) {
//		this.listEntity = listEntity;
//	}
//
//
//
//
//
//
//
//	public void setListPlayer(ArrayList<Player> listPlayer) {
//		this.listPlayer = listPlayer;
//	}
//
//
//
//
//
//
//
//	public void setListPortal(ArrayList<Portal> listPortal) {
//		this.listPortal = listPortal;
//	}
//
//
//
//
//
//
//
//	public void setListObstacle(ArrayList<Obstacle> listObstacle) {
//		this.listObstacle = listObstacle;
//	}
//
//
//
//
//
//
//
//	public void setListProjectile(ArrayList<Projectile> listProjectile) {
//		this.listProjectile = listProjectile;
//	}
//
//
//
//
//
//
//
//	public void setListGeneralEnemy(ArrayList<GeneralEnemy> listGeneralEnemy) {
//		this.listGeneralEnemy = listGeneralEnemy;
//	}
//
//}

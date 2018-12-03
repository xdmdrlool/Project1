package clemnico;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	
	private BufferedImage img;
	private int blocSize=100;
	private int width;
	private int height;
	
	
	@SuppressWarnings("rawtypes")
	
	// Liste des entity à fusionner horizontalement et verticalement
	Class [] listClassFusion=new Class[] {ObstacleFix.class};
	
	//Triplet d'un meme nombre pour les obstacles
	int[] obstacleFixPix= {0,0,0};	//Noir
	int[] obstacleMovingPix= {100,100,100};	//Gris foncé
	int[] obstacleMovingParcoursPix= {200,200,200};	//Gris clair
	int[] enemyDefaultPix= {255,0,0};  	//Rouge
	int[] enemyJumpPix= {255,100,0};	//Orange
	int[] enemyShootPix= {255,0,200};	//Rose
	int[] enemyFollowPix= {255,200,0};	//jaune
	int[] enemyLoopPix= {255,200,100};	//Beige
	int[] obstacleFixSurfaceNoPortalPix= {0,0,255};	//Bleu
	int[] obstacleFixGateNoPlayer= {0,200,255};	//Bleu clair
	int[] obstacleFixGateNoPortal= {100,200,255};	//Bleu tres clair
	int[] player1Pix= {0,100,0};		//vert foncé
	int[] player2Pix= {0,200,0};		//vert clair
	
	
	public Map(String name) {
		File file = new File("resources/"+name+".bmp");
		
		try {
			img = ImageIO.read(file);
			width=img.getWidth();
			height=img.getHeight();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Entity> load() {
		ArrayList<Entity> mapObject = new ArrayList<>();

		
		int obstacleType=0; // = 1 pour ObstacleFix et = 2 pour ObstacleMoving
		int i1Connect=0;
		int j1Connect=0;
		int i2Connect=0;	//Uniquement pour les obstacles mouvant
		int j2Connect=0;	//Uniquement pour les obstacles mouvant
		int widthConnect=0;
		int heightConnect=0;
		
		
		int[] pixel= {0,0,0};
		for (int j=0;j<height; j++) {
			for(int i=0;i<width;i++) {
				pixel=img.getRaster().getPixel(i, j, new int[3]);

				//Affichage des obstacles
				if (pixel[0]==pixel[1] && pixel[1]==pixel[2]) {
					//Obstacles fixes
					if (pixel[0]==obstacleFixPix[0]) {
						if (obstacleType==1 && i<width-1) {
							widthConnect+=1;
						}
						else {
							obstacleType=1;
							i1Connect=i;
							j1Connect=j;
							widthConnect=1;
							heightConnect=1;
						}
					}
					else if(obstacleType==1) {
						obstacleType=0;
						ObstacleFix obstacle = new ObstacleFix(i1Connect*blocSize, j1Connect*blocSize, widthConnect*blocSize, heightConnect*blocSize,"Obs1", 0);
						mapObject.add(obstacle);
					}
					//Corps obstacles mouvant
					if (pixel[0]==obstacleMovingPix[0]) {
						if (obstacleType==2) {
							widthConnect+=1;
						}
						else {
							obstacleType=2;
							i1Connect=i;
							j1Connect=j;
							i2Connect=0;
							j2Connect=0;
							widthConnect=1;
							heightConnect=1;
						}
					}
					//Pattern obstacles mouvant
					else if (pixel[0]==obstacleMovingParcoursPix[0] && obstacleType==2 && i<width-1) {
						i2Connect+=1;
						j2Connect=0;
					}
					else if(obstacleType==2) {
						obstacleType=0;
						ObstacleMoving obstacleMoving= new ObstacleMoving("Plate",i1Connect*blocSize,j1Connect*blocSize,
																				 (i1Connect+i2Connect)*blocSize,(j1Connect+j2Connect)*blocSize,
																				 widthConnect*blocSize,heightConnect*blocSize, 50*(i2Connect));
						mapObject.add(obstacleMoving);
						
					}
					
				}
				
				if (equal(pixel,obstacleFixSurfaceNoPortalPix)) {
					ObstacleFixSurfaceNoPortal obstacleFixSurfaceNoPortal =new ObstacleFixSurfaceNoPortal(i*blocSize, j*blocSize, blocSize, blocSize,"Obs1", 0);
					mapObject.add(obstacleFixSurfaceNoPortal);
				}
				
				if (equal(pixel,obstacleFixGateNoPlayer)) {
					ObstacleFixGateNoPlayer obstacleFixGateNoPlayer =new ObstacleFixGateNoPlayer(i*blocSize, j*blocSize, blocSize, blocSize,"Obs1", 0);
					mapObject.add(obstacleFixGateNoPlayer);
				}
				
				if (equal(pixel,obstacleFixGateNoPortal)) {
					ObstacleFixGateNoPortal obstacleFixGateNoPortal =new ObstacleFixGateNoPortal(i*blocSize, j*blocSize, blocSize, blocSize,"Obs1", 0);
					mapObject.add(obstacleFixGateNoPortal);
				}
				
				//Affichage des joueurs
				if (equal(pixel,player1Pix)) {
					PlayerLocal playerLocal = new PlayerLocal(i*blocSize, j*blocSize, 50, 50, "Player1", 1, 6);
					mapObject.add(playerLocal);
				}
				if (equal(pixel,player2Pix)) {
					PlayerWeb player = new PlayerWeb(i*blocSize, j*blocSize, 50, 50, "Player2", 1, 6);
					mapObject.add(player);
				}
				
				
				//Affichage des ennemis
				if (equal(pixel,enemyDefaultPix)) {
					EnemyDefault enemy = new EnemyDefault(i*blocSize, j*blocSize, 50, 50, "Enemy1",false);
					mapObject.add(enemy);
				}
				if (equal(pixel,enemyJumpPix)) {
					EnemyJump enemy = new EnemyJump(i*blocSize, j*blocSize, 50, 50, "Enemy1",false);
					mapObject.add(enemy);
				}
				if (equal(pixel,enemyShootPix)) {
					EnemyShoot enemy = new EnemyShoot(i*blocSize, j*blocSize, 50, 50, "Enemy1",false);
					mapObject.add(enemy);
				}
				if (equal(pixel,enemyFollowPix)) {
					EnemyFollow enemy = new EnemyFollow(i*blocSize, j*blocSize, 50, 50, "Enemy1",false);
					mapObject.add(enemy);
				}
				if (equal(pixel,enemyLoopPix)) {
					EnemyLoop enemy = new EnemyLoop(i*blocSize, j*blocSize, 50, 50, "Enemy1",false,"path1");
					mapObject.add(enemy);
				}
				
			}
			
			
			
		}
		
		fusionX(mapObject);
		fusionY(mapObject);
		for (Entity e :mapObject) {e.useDefaultAnimations();}
		
		
		return mapObject;
	}
	
	private boolean equal(int[] l1,int[] l2) {
		for (int k=0;k<l1.length;k++) {
			if(l1[k]!=l2[k]) {return false;}
		}
		return true;
	}
	
	
	
	// Fusionne les obstacle selon X
	private void fusionX(ArrayList<Entity> list) { 
		int n=1000000;
		Entity e1;
		Entity e2;
		while (n>1 && n!=list.size()) {
			n=list.size();
			for (int i=0;i<list.size();i++) {
				e1=list.get(i);
				for(int j=0;j<list.size();j++) {
					e2=list.get(j);
					if (j!=i && tryfusionX(e1, e2)) {list.remove(e2);}
				}
				
			}
			
		}
	}
	
	// Fusionne les obstacle selon Y
	private void fusionY(ArrayList<Entity> list) {
		int n=1000000;
		Entity e1;
		Entity e2;
		while (n>1 && n!=list.size()) {
			n=list.size();
			for (int i=0;i<list.size();i++) {
				e1=list.get(i);
				
				for(int j=0;j<list.size();j++) {
					e2=list.get(j);
					if (j!=i) {
						if(tryfusionY(e1, e2)) {list.remove(e2);}}
					}
				}
				
			}
			
	}
	
	
	
	
	// Verifie que la class de e est dans la liste
	@SuppressWarnings("rawtypes")
	private boolean classIn(Entity e,Class[] listClass) { 
		for (Class cla :listClass) {
			if (cla==e.getClass()) {return true;}
		}
		return false;
	}
	
	// Essai de fusionner les entite si entity2 est a droite de entity1
	private boolean tryfusionX(Entity entity1, Entity entity2) { 
		if(!(classIn(entity1, listClassFusion))){return false;}
		if (entity1.getClass()!=entity2.getClass()) {return false;}
		
		if (entity1.y==entity2.y && entity1.height==entity2.height && entity1.x+entity1.width==entity2.x) {
			entity1.setWidth(entity1.width+entity2.width);
			return true;
		}
		return false;
	}
	
	// Essai de fusionner les entite si entity2 est en dessous de entity1
	private boolean tryfusionY(Entity entity1, Entity entity2) { 
		if(!(classIn(entity1, listClassFusion))){return false;}
		if (entity1.getClass()!=entity2.getClass()) {return false;}

		if (entity1.x==entity2.x && entity1.width==entity2.width && entity1.y+entity1.height==entity2.y) {
			entity1.setHeight(entity1.height+entity2.height);
			return true;
			
		}
		return false;
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////

	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}

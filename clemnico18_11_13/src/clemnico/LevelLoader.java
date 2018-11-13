package clemnico;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LevelLoader {

	static ImageLoader IL=new ImageLoader();
	
	private int widthWindow;
	private int heightWindow;
	
	public LevelLoader(int width,int height) {
		widthWindow=width;
		heightWindow=height;

	}
	
	
	
	public ArrayList<Layer> load(String name) throws IOException  {
		ArrayList<Layer> listLayer= new ArrayList<Layer>();
		
		
	    File f = new File ("resources/" + name + ".txt");
	    
	    
	    
	    FileReader fr = new FileReader (f);

	    BufferedReader br = new BufferedReader (fr);
	    String[] parts;

	    String line ;
	    String layerPath;
	    float distance;
	    Map map;
	    ArrayList<Entity> list;
	    Layer layer;

		/////////////////////////////////
		/////// LECTURE DU FICHIER //////
		/////////////////////////////////

	    ////////// Background
	    
	    //  Lecture d'une ligne
	    line =br.readLine(); 
	    // Lecture du path
	    parts=line.split("'"); 
	    
	    
	    String backgroundPath =parts[1];
	    // Creation de l'image
	    BufferedImage image =IL.loadImage(backgroundPath);
	    int w = image.getWidth();int h=image.getHeight();
	    // Creation de l'animation de fond
	    SpriteSheet ss= new SpriteSheet(image, 1);
	    Sprite sprite=new Sprite(ss, 0, 0, w, h, widthWindow, heightWindow, 0);
	    Animation anim=new Animation(new Sprite[]{sprite},new int[] {1} );
	    // Creation de l'obstacle
	    ObstacleFix obsBack =new ObstacleFix(0, 0, widthWindow, heightWindow, "", 0);
	    obsBack.addAnimation(NameAnimation.DEFAULT, anim);
	    obsBack.setCurrentAnimation(NameAnimation.DEFAULT);
	    // Creation du layer de background
	    Layer layerBack=new Layer(1000000);
	    layerBack.add(obsBack);
	    listLayer.add(layerBack);
	    
	    
	    ////////// Chargement des layers
	    
	    //  Lecture d'une ligne
	    line =br.readLine();
	    // Lecture du nombre de layer
	    parts=line.split(":");
	    int nbLayer=Integer.parseInt(parts[1]);

	    // Lecture et Creation des layers
	    for (int i=0;i<nbLayer;i++) {
	    	line =br.readLine();
	    	// Lecture path/distance
	    	parts=line.split(",");
	    	layerPath=parts[0];
	    	distance=Float.parseFloat((parts[1]));
	    	// Chargement des entity
	    	map = new Map(layerPath);
	    	list = map.load();
	    	// Creation layer
	    	layer =new Layer(distance);
	    	for (Entity e : list) {layer.add(e);}
	    	
	    	// Ajout du layer a la liste de tous les layers
	    	listLayer.add(layer);
	    }
				
	    //////////
			
		    
	    return listLayer;
	    
	}



	public int getWidthWindow() {
		return widthWindow;
	}



	public int getHeightWindow() {
		return heightWindow;
	}



	public void setWidthWindow(int widthWindow) {
		this.widthWindow = widthWindow;
	}



	public void setHeightWindow(int heightWindow) {
		this.heightWindow = heightWindow;
	}
	
	
	
	
	
}

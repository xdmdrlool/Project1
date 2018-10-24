package clemnico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LevelLoader {

	public LevelLoader() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ArrayList<Layer> load(String name) throws IOException  {
		ArrayList<Layer> listLayer= new ArrayList<Layer>();
		
		
	    File f = new File ("resources/" + name + ".txt");
	    
	    
	    
	    FileReader fr = new FileReader (f);

	    BufferedReader br = new BufferedReader (fr);
	    String[] parts;

	    String line ;
	    String layerPath;
	    int distance;
	    Map map;
	    ArrayList<Entity> list;
	    Layer layer;



	    ////////// Background
	    
	    line =br.readLine();
	    parts=line.split("'");
	    String backgroundPath =parts[1];

	    ////////// Chargement des layers
	    
	    line =br.readLine();
	    parts=line.split(":");
	    int nbLayer=Integer.parseInt(parts[1]);

	    for (int i=0;i<nbLayer;i++) {
	    	line =br.readLine();
	    	parts=line.split(",");
	    	layerPath=parts[0];
	    	distance=Integer.parseInt(parts[1]);
	    	map = new Map(layerPath);
	    	list = map.load();

	    	layer =new Layer(distance);
	    	for (Entity e : list) {layer.add(e);}
	    	listLayer.add(layer);
	    }
				
	    //////////
			
		    
	    return listLayer;
	    
	}
	
	
	
	
	
}

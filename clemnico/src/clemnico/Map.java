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
	
	int[] obstaclePix= {0,0,0};	//Noir
	int[] enemyPix= {255,0,0};  //Rouge
	
	
	public Map(String name) {
		File file = new File("C:\\Users\\Nicolas\\Desktop\\Logiciels\\Programmation\\Java\\eclipse-workspace\\clemnico\\resources\\"+name+".bmp");
		
		try {
			img = ImageIO.read(file);
			width=img.getWidth();
			height=img.getHeight();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Obstacle> load() {
		ArrayList<Obstacle> obstacles = new ArrayList<>();
		int[] pixel= {0,0,0};
		for(int i=0;i<width;i++) {
			for (int j=0;j<height; j++) {
				pixel=img.getRaster().getPixel(i, j, new int[3]);
				//System.out.println(pixel[0]+" "+pixel[1]+" "+pixel[2]);
				
				//System.out.println(obstaclePix[0]+" "+obstaclePix[1]+" "+obstaclePix[2]);
				//System.out.println(i+" "+j);
				if (pixel[0]==obstaclePix[0] && pixel[1]==obstaclePix[1] && pixel[2]==obstaclePix[2]) {
					Obstacle obstacle = new FixObstacle(i*blocSize, j*blocSize, blocSize, blocSize,"Obs1", 0);
					obstacles.add(obstacle);
				}
			}
		}
		
		return obstacles;
		//System.out.println(pixel[0]+" "+pixel[1]+" "+pixel[2]);
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

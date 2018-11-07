package clemnico;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


//Permet de créer un chemin prédéfini pour l'ennemi
//Le parcours doit être tracé au centre de l'écran
public class EnemyPath {
	
	////Attributs////
	private BufferedImage img;
	private int width;
	private int height;
	
	
	int[] pathPix= {0,0,0};		//Noir
	int[] startPix= {0,255,0};	//Vert
	
	
	////Constructeur////
	public EnemyPath(String name) {
		File file = new File("resources/"+name+".bmp");
		
		try {
			img = ImageIO.read(file);
			width=img.getWidth();
			height=img.getHeight();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	////Methodes////
	public ArrayList<Point> load(){
		ArrayList<Point> path = new ArrayList<>();
		
		Point firstPos = new Point(firstPos());
		Point lastPos=firstPos;
		Point lastPos2;
		Point actualPos=firstPos;
		do {
			path.add(actualPos);
			lastPos2=actualPos;
			actualPos=nextStep(firstPos, lastPos, actualPos);
			lastPos=lastPos2;
			
		} while(!actualPos.equals(firstPos));
		
		return path;		
	}
	
	public Point firstPos() {
		Point pos=new Point();
		int[] pixel= {0,0,0};
		for (int j=0;j<height; j++) {
			for(int i=0;i<width;i++) {
				pixel=img.getRaster().getPixel(i, j, new int[3]);
				if (equal(pixel,startPix)) {
					pos.x=i;
					pos.y=j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	public Point nextStep(Point startPos, Point lastPos, Point actualPos) {
		
		Point nextPos=new Point(-1,-1);
		
		int il=lastPos.x;
		int jl=lastPos.y;
		int i=actualPos.x;
		int j=actualPos.y;
		
		
		//Cherche la suite du chemin, en commencant par les pixels latéraux, puis ceux en diagonal
		//On vérifie d'abord si le prochain pixel est le pixel du début
		if	    (equal(img.getRaster().getPixel(i  , j-1, new int[3]),startPix) && (i  !=il || j-1!=jl)) {nextPos.x=i  ;nextPos.y=j-1;}
		else if (equal(img.getRaster().getPixel(i-1, j  , new int[3]),startPix) && (i-1!=il || j  !=jl)) {nextPos.x=i-1;nextPos.y=j  ;}
		else if (equal(img.getRaster().getPixel(i+1, j  , new int[3]),startPix) && (i+1!=il || j  !=jl)) {nextPos.x=i+1;nextPos.y=j  ;}
		else if (equal(img.getRaster().getPixel(i  , j+1, new int[3]),startPix) && (i  !=il || j+1!=jl)) {nextPos.x=i  ;nextPos.y=j+1;}
		
		else if	(equal(img.getRaster().getPixel(i-1, j-1, new int[3]),startPix) && (i-1!=il || j-1!=jl)) {nextPos.x=i-1;nextPos.y=j-1;}
		else if	(equal(img.getRaster().getPixel(i+1, j-1, new int[3]),startPix) && (i+1!=il || j-1!=jl)) {nextPos.x=i+1;nextPos.y=j-1;}
		else if (equal(img.getRaster().getPixel(i-1, j+1, new int[3]),startPix) && (i-1!=il || j+1!=jl)) {nextPos.x=i-1;nextPos.y=j+1;}
		else if (equal(img.getRaster().getPixel(i+1, j+1, new int[3]),startPix) && (i+1!=il || j+1!=jl)) {nextPos.x=i+1;nextPos.y=j+1;}
		
		else if	(equal(img.getRaster().getPixel(i  , j-1, new int[3]),pathPix) && (i  !=il || j-1!=jl)) {nextPos.x=i  ;nextPos.y=j-1;}
		else if (equal(img.getRaster().getPixel(i-1, j  , new int[3]),pathPix) && (i-1!=il || j  !=jl)) {nextPos.x=i-1;nextPos.y=j  ;}
		else if (equal(img.getRaster().getPixel(i+1, j  , new int[3]),pathPix) && (i+1!=il || j  !=jl)) {nextPos.x=i+1;nextPos.y=j  ;}
		else if (equal(img.getRaster().getPixel(i  , j+1, new int[3]),pathPix) && (i  !=il || j+1!=jl)) {nextPos.x=i  ;nextPos.y=j+1;}
		
		else if	(equal(img.getRaster().getPixel(i-1, j-1, new int[3]),pathPix) && (i-1!=il || j-1!=jl)) {nextPos.x=i-1;nextPos.y=j-1;}
		else if	(equal(img.getRaster().getPixel(i+1, j-1, new int[3]),pathPix) && (i+1!=il || j-1!=jl)) {nextPos.x=i+1;nextPos.y=j-1;}
		else if (equal(img.getRaster().getPixel(i-1, j+1, new int[3]),pathPix) && (i-1!=il || j+1!=jl)) {nextPos.x=i-1;nextPos.y=j+1;}
		else if (equal(img.getRaster().getPixel(i+1, j+1, new int[3]),pathPix) && (i+1!=il || j+1!=jl)) {nextPos.x=i+1;nextPos.y=j+1;}
	
		else    {return startPos;}
		
		return nextPos;
	}
	
	
	public boolean equal(int[] l1,int[] l2) {
		for (int k=0;k<l1.length;k++) {
			if(l1[k]!=l2[k]) {return false;}
		}
		return true;
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
}

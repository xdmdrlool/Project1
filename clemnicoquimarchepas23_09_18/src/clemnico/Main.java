package clemnico;

import java.awt.Color;

import clemnico.FC.Vecteur;

public class Main {
 
	public static void main(String[] args) {
		
		Window window =new Window(60);
		
		
//		FC fc=new FC();
//		FormRect obs= new FormRect(Color.BLACK, 0, 0, 100, 100, 0);
//		
//		
//		FormRect rect0= new FormRect(Color.BLACK, 105, 95, 100, 100, 0);
//		FormRect rect= new FormRect(Color.BLACK, 90, 90, 100, 100, 0);
//		
//		Vecteur vec = fc.calculVecteurCollisionRectDroitObstacleDroitSansFrottement(rect0, rect, obs);
//		if (vec==null) {System.out.println("pas collision");}
//		else {System.out.println(vec.x+" "+vec.y);}
		
		
		
		
		FC fc=new FC();
		FormRect obs= new FormRect(Color.BLACK, 0, 402, 600, 100, 0);
		FormCircle cir0= new FormCircle(Color.BLACK, 150, 50, 30);
		FormCircle cir = new FormCircle(Color.BLACK, 100, 342, 60);

		Vecteur vec = fc.calculVecteurCollisionCircleObstacleDroitSansFrottement( cir, obs);
		if (vec==null) {System.out.println("pas collision");}
		else {System.out.println(vec.x+" "+vec.y);}


	}
}
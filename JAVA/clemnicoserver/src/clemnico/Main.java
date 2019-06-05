package clemnico;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;






/*class SendMessage extends Thread {
	
	Discussionte discussion;
	
	public SendMessage(Discussionte discussion) {
		this.discussion=discussion;
	}
	
	public void run(){
		do {
			System.out.println(discussion.msg);
		}while(true);
		
	}
}

class Discussionte{
	String msg = new String();
}*/




class Ajout extends Thread{
	ArrayList<Integer> listInt;
	boolean jajoute =false;
	int n=0;
	public Ajout(ArrayList<Integer> listInt) {
		this.listInt=listInt;
	}
	
	public void run() {
		do {
			synchronized(listInt) {
				listInt.add(1);
				n+=1;
				System.out.println(n);
				listInt.notify();
			}
			
		} while(true);
		
	}
	
	
}




public class Main {
	
	
	 /*public static byte booleans2byte(boolean[] booleans) {
	        byte B = 0;
	        for (int i = 0; i < 8; i++) {
	            int bToi = booleans[i] ? 1 : 0;
	            B += (byte) (Math.abs(bToi * Math.pow(2, 8 - i)));
	        }
	        return B;
	    }*/
	
 
	public static void main(String[] args) throws Exception {
		
		Window window =new Window(60);
		/*Thread mainThread = Thread.currentThread();
		ArrayList<Integer> listInt = new ArrayList<>();
		listInt.add(1);
		listInt.add(2);
		listInt.add(3);
		
		Ajout ajout = new Ajout(listInt);
		ajout.setPriority(1);
		ajout.start();
		synchronized(listInt) {
			listInt.wait();
		}
		
		do {
			synchronized(listInt) {
					for(int i : listInt) {
						//System.out.print(i+" ");
					}
					//System.out.println();
				}
					listInt.wait();
				
			}
		}while(true);
			
		*/
		
		
		
		//TEST THREADS
		/*BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
		
		Discussionte discussion = new Discussionte();
		SendMessage sm=new SendMessage(discussion);
		sm.start();
		
		do {
			discussion.msg=entree.readLine();
		}while(true);
		*/
		
		
		/*boolean[] booleans = {false,false,true,false,false,true,true,false};
		
		byte b = booleans2byte(booleans);

		//System.out.println(b);
		
		*/
		
		
//		LayerLoader t=new LayerLoader();
//		try {
//			t.load("test");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		


		
	   
		
		
		
		
		
		
		
		
//		FormRect obs= new FormRect(Color.BLACK, 900, 500, 200, 250,0);
//		
//		
//		FormRect rect0= new FormRect(Color.BLACK, 170, 964, 30, 30, 0);
//		FormRect rect= new FormRect(Color.BLACK, 0, 1100, 200, 100, 0);
//		System.out.println(fc.Collision(rect0, rect));
//		Vecteur[] tab = fc.calculVecteurCollisionRectDroitObstacleDroit(rect0, rect, obs);
//		Vecteur vec = tab[0];
//		Vecteur dir = tab[1];
//		if (vec==null) {System.out.println("pas collision");}
//		else {System.out.println(vec.x+" "+vec.y);}
	
//		Point A=new Point(900,500);
//		Point B=new Point(910,500);
//		Point C=new Point(903,500);
//		Point D= fc.projectionPointSeg(A, B, C);
//		System.out.println(D.x+" "+D.y);
		
		
//		FC fc=new FC();
//		FormRect obs= new FormRect(Color.BLACK, 0, 402, 600, 100, 0);
//		FormCircle cir0= new FormCircle(Color.BLACK, 150, 50, 30);
//		FormCircle cir = new FormCircle(Color.BLACK, 100, 342, 60);
//
//		Vecteur vec = fc.calculVecteurCollisionCircleObstacleDroitSansFrottement( cir, obs);
//		if (vec==null) {System.out.println("pas collision");}
//		else {System.out.println(vec.x+" "+vec.y);}
		

	}
}
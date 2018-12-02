package clemnico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

////////////////////////////////
/////// CLASSE RECEVOIR ////////
////////////////////////////////

class Recevoir extends Thread {
	
	Discussion d;
	
	int timeWait = 0;
	int timeWaitLimit = 10000;
	
	public Recevoir(Discussion d) {
		this.d=d;
		this.setName("Thread Recevoir");
	}

	public void run() {
		try {
			DatagramSocket socketReception  = new DatagramSocket(d.port);
			String message = null;
			byte[] tampon;
			DatagramPacket paquet;
			do {
				if (timeWait == timeWaitLimit) {
					//On traite les messages un par un pour éviter les pb de synchro
					for (PlayerWeb playerWeb : d.listPlayerWeb) {
						//message reçu par le serveur (20 octets ok car on ne reçoit qu'un message par tour)
						tampon = new byte[20];
						paquet = new DatagramPacket(tampon, tampon.length);
						socketReception.receive(paquet);
						message = new String(tampon);
						//System.out.println(message);
						//Traitement du message converti en action pour chaque client
						playerWeb.translationAction(message);
					}
					timeWait=0;
				}
				else {
					timeWait+=1;
				}
				
				
			} 
			while(true);
		}
		catch(IOException exc) {}
	}
}

////////////////////////////////
/////// CLASSE ENVOYER /////////
////////////////////////////////

class Envoyer extends Thread {
	
	Discussion d;
	ArrayList<Entity> listEntityLocal = new ArrayList<>();
	
	int k =0;
	int timeWait = 0;
	int timeWaitLimit = 10000000;
	
	boolean isSending = false;
	boolean elementSent = false;
	
	public Envoyer(Discussion d) {
		this.d=d;
		this.setName("Thread Envoyer");
	}

	public void run() {
		try {
			DatagramSocket socketEnvoi = new DatagramSocket();
			DatagramPacket paquet;
			byte[] tampon; 
			
			/*BufferedReader entree = new BufferedReader
			(new InputStreamReader(System.in));*/
			do {
				if (timeWait==timeWaitLimit) {
					String message=new String("");
					synchronized(d.listEntity) {
						listEntityLocal = new ArrayList<>(d.listEntity);
						d.listEntity.notify();
					}
					
						
					//Au debut du message on indique le nombre d'entités codé sur 3 charactères
					String listEntitySize=new String(Integer.toString(listEntityLocal.size()));
					if (listEntitySize.length()<2) {listEntitySize=listEntitySize+"  ";}
					else if (listEntitySize.length()<3) {listEntitySize=listEntitySize+" ";}
					
					message = message + listEntitySize;
					
					//Envoie un message pour tous les Entities, les uns a la suite des autres:
					//une animation, le numero de frame, sa taille(w,h) puis ses coordonnées(x,y)
					for(Entity entity : listEntityLocal) {
						
						String animationNumber=new String(Integer.toString(entity.getCurrentAnimation().getSprite().getNumberAnimation()));
						String frameNumber=new String(Integer.toString(entity.getCurrentAnimation().getNumeroFrame()));
						String wEntity = new String(Integer.toString(entity.getWidth()));
						String hEntity = new String(Integer.toString(entity.getHeight()));
						String xEntity = new String(Integer.toString(entity.getX()));
						String yEntity = new String(Integer.toString(entity.getY()));
						
						//On code la le numéro d'animation et le numéro de frame sur un String de taille 2
						if (animationNumber.length()<2) {animationNumber=animationNumber+" ";}
						if (frameNumber.length()<2) {frameNumber=frameNumber+" ";}
						
						//Création du message:
						message = message + animationNumber+frameNumber+wEntity.length()+wEntity+hEntity.length()+hEntity
														   +xEntity.length()+xEntity+yEntity.length()+yEntity;
					}
					System.out.println(message);
					k=k+1;
					System.out.println(k);
					//Conversion du message en octets
					tampon = message.getBytes();
						
					//Envoie du message à tous les clients
					for(PlayerWeb playerWeb : d.listPlayerWeb) {
						InetAddress address = InetAddress.getByAddress(playerWeb.getAddress());
						paquet = new DatagramPacket(tampon, tampon.length, address, d.port);
						socketEnvoi.send(paquet);
					}
					
					//System.out.println(message);
					
					/*elementSent=true;
					d.listEntity.notify();
					d.listEntity.wait(15);*/
					
					timeWait=0;
				}
				else {
					timeWait+=1;
				}
				
				
				
				
			} while(true);
			
		}
		catch(IOException exc) {
			exc.printStackTrace();
		} 
	}
}

////////////////////////////////
/////// CLASSE DISCUSSION //////
////////////////////////////////

public class Discussion{
	
	protected PlayerLocal playerLocal;
	protected ArrayList<PlayerWeb> listPlayerWeb;
	protected ArrayList<Entity> listEntity;
	protected boolean updating = false;	//Indique si le pgm modifie la listEntity
	Envoyer envoyer;
	Recevoir recevoir;
	
	boolean fini = false;
	int port = 1201;
	InetAddress adresse ;
	public Discussion(PlayerLocal playerLocal, ArrayList<PlayerWeb> listPlayerWeb, ArrayList<Entity> listEntity) throws Exception {
		
		this.playerLocal=playerLocal;
		this.listPlayerWeb=listPlayerWeb;
		this.listEntity=listEntity;
		envoyer = new Envoyer(this);
		recevoir = new Recevoir(this);
	}
	
	public void start() {
		envoyer.start();
		recevoir.start();
	}
	
}






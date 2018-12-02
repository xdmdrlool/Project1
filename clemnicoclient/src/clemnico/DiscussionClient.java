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
	
	DiscussionClient d;
	
	int k=0;
	int timeWait = 0;
	int timeWaitLimit = 10000;
	
	public Recevoir(DiscussionClient d) {
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
				if (timeWait==timeWaitLimit) {
					//Actualisation constante de la position des entités
					//message reçu par le client
					
					tampon = new byte[1000];
					paquet = new DatagramPacket(tampon, tampon.length);
					socketReception.receive(paquet);
					message = new String(tampon);
					
					System.out.println(paquet.getLength()+" "+socketReception.getReceiveBufferSize()+socketReception.getSendBufferSize());
					//Traduction du message décrivant l'entité
					//Numéro animation, numéro frame, w, h, x, y
					d.playerServer.translationAction(message);
					//System.out.println(message);
					k+=1;
					//System.out.println(k);
					timeWait=0;
				}
				else {
					timeWait += 1;
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
	
	DiscussionClient d;
	
	int timeWait = 0;
	int timeWaitLimit = 10000000;
	
	public Envoyer(DiscussionClient d) {
		this.d=d;
		this.setName("Thread Envoyer");
	}

	public void run() {
		try {
			
			InetAddress address = InetAddress.getByAddress(d.playerServer.getAddress());
			
			DatagramSocket socketEnvoi = new DatagramSocket();
			DatagramPacket paquet;
			byte[] tampon; 
			String message;
			
			//Le joueur local envoie ses informations de clavier et souris au serveur
			do {
				if (timeWait == timeWaitLimit) {
					message=d.playerLocal.messageCreation();
					tampon = message.getBytes();
					paquet = new DatagramPacket(tampon, tampon.length, address, d.port);
					socketEnvoi.send(paquet);
						
					//System.out.println(message);
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

public class DiscussionClient{
	
	protected PlayerLocal playerLocal;
	protected PlayerServer playerServer;
	Envoyer envoyer;
	Recevoir recevoir;
	
	protected boolean dataActualized=false;	//Le serveur doit envoyer un message d'actualisation des données
	
	int port = 1201;
	
	public DiscussionClient(PlayerLocal playerLocal, PlayerServer playerServer) throws Exception {
		
		this.playerLocal=playerLocal;
		this.playerServer=playerServer;
		envoyer = new Envoyer(this);
		recevoir = new Recevoir(this);
	}
	
	public void start() {
		envoyer.start();
		recevoir.start();
	}
	
}






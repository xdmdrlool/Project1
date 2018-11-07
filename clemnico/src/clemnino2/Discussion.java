package clemnino2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


class Recevoir extends Thread {
	
	Discussion discussion;
	
	Recevoir(Discussion discussion) {
		this.discussion = discussion;
		setDaemon(true);
	}

	public void run() {
		try {
			DatagramSocket socketReception  = new DatagramSocket(discussion.port);
			String message = null;
			byte[] tampon;
			DatagramPacket paquet;
			do {
				tampon = new byte[1000];
				paquet = new DatagramPacket(tampon, tampon.length);
				socketReception.receive(paquet);
				message = new String(tampon);
				System.out.println(message);
			} 
			while(!message.startsWith("fin"));
		}
		catch(IOException exc) {}
		discussion.fini = true;
		synchronized(discussion) {
			discussion.notify();
		}
	}
}

class Envoyer extends Thread {
	Discussion discussion;
	Envoyer(Discussion discussion) {
		this.discussion = discussion;
		setDaemon(true);
	}

	public void run() {
		try {
			InetAddress adresse = discussion.adresse;
			int port = discussion.port;
			DatagramSocket socketEnvoi = new DatagramSocket();
			DatagramPacket paquet;
			byte[] tampon; 
			String message;
			BufferedReader entree = new BufferedReader
			(new InputStreamReader(System.in));
			do
			{
				message = entree.readLine();
				tampon = message.getBytes();
				paquet = new DatagramPacket(tampon, tampon.length, adresse, port);
				socketEnvoi.send(paquet);
			}
			while(!message.equals("fin"));
		}
		catch(IOException exc) {
			exc.printStackTrace();
		}
		discussion.fini = true;
		synchronized(discussion) {
			discussion.notify();
		}
	}
}

public class Discussion {
	boolean fini = false;
	int port = 1201;
	InetAddress adresse ;
	public Discussion() throws Exception {
		try
		{
			adresse = InetAddress.getByAddress(new byte[]{(byte)25, (byte)57, 89, 96});
		}
		catch(UnknownHostException exc)
		{
			System.out.println("destinataire inconnu");
			System.exit(1);
		}
		new Envoyer(this).start();
		new Recevoir(this).start();
	}
	public static void main(String argv[])  throws Exception
	{
		
		Discussion discussion = new Discussion();
		synchronized(discussion) {
			while(!discussion.fini) discussion.wait();
		}
	}
}
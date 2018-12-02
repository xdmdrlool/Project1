package clemnico;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PlayerServer extends JPanel{
	
	private byte[] address = new byte[] {(byte)25, (byte)57, 89, 96};
	
	public Window window;
	
	AnimationCreator ACreator = new AnimationCreator();
	Animations[] listAnimations = Animations.values();
	boolean isSending = false;

	private int xOffset;
	private int yOffset;
	
	ArrayList<Entity> listEntity = new ArrayList<>();
	
	public PlayerServer(Window window) {
		this.window=window;
	}
	//Traduction du message envoyé par le serveur correspondant à une entité
	public void translationAction(String message){
		
		if(isSending) {
			int posMsg = 0; //Position de lecture dans le message
			
			//Nombre d'entités en début de message, codé sur 3 charactères
			int numberEntitySize = message.charAt(1)==' ' ? 1 : message.charAt(2)==' ' ? 2 : 3;
			int numberEntity = Integer.parseInt(message.substring(posMsg, posMsg + numberEntitySize));
			posMsg+=3;

			listEntity = new ArrayList<>();
			//Message des entités successifs avec les paramètres suivants: numéro d'animation, numéro d'image, w, h, x, y
			
			for(int k = 0; k<numberEntity;k++) {
	
				int numberAnimation = 	Integer.parseInt(message.substring(posMsg, posMsg + (message.charAt(1+posMsg)==' ' ? 1 : 2)));
				posMsg+=2;
				//System.out.println(message.substring(posMsg, posMsg + message.charAt(posMsg+1)==' ' ? 1 : 2));
				//int numberFrame = 		Integer.parseInt(message.substring(posMsg, posMsg + message.charAt(posMsg+1)==' ' ? 1 : 2));
				posMsg+=2;
				int wSize = Integer.parseInt(message.substring(posMsg,posMsg+1));
				posMsg+=1;
				int w = Integer.parseInt(message.substring(posMsg,posMsg + wSize));
				posMsg+=wSize;
				int hSize = Integer.parseInt(message.substring(posMsg,posMsg+1));
				posMsg+=1;
				int h = Integer.parseInt(message.substring(posMsg,posMsg + hSize));
				posMsg+=hSize;
				int xSize = Integer.parseInt(message.substring(posMsg,posMsg+1));
				posMsg+=1;
				int x = Integer.parseInt(message.substring(posMsg,posMsg + xSize));
				posMsg+=xSize;
				int ySize = Integer.parseInt(message.substring(posMsg,posMsg+1));
				posMsg+=1;
				int y = Integer.parseInt(message.substring(posMsg,posMsg + ySize));
				posMsg+=ySize;
				
				//Creation et affichage de l'entité dans la fenêtre
				
				Entity entity = new Entity(null, x, y, w, h);
				
				entity.addAnimation(NameAnimation.DEFAULT, ACreator.createAnimation(listAnimations[numberAnimation], w, h));
				entity.setCurrentAnimation(NameAnimation.DEFAULT);
				//System.out.println(entity.getCurrentAnimation().getSprite());
				listEntity.add(entity);
			}
			isSending=false;
		}
				
	}
	
	public synchronized void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D gg= (Graphics2D) g;
		//System.out.println(listEntity.size());
		for(Entity entity : listEntity) {
			entity.display(gg, xOffset, yOffset, window.getWidth(), window.getHeight());
		}
		
		
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}
	
	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}
	
}

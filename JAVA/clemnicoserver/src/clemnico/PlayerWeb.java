package clemnico;


import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import clemnico.FC.Vecteur;

public class PlayerWeb extends Player {
	
	////Attributs////
	//private byte[] address = new byte[]{(byte)25, (byte)58, (byte) 163, 90};	//PC portable
//	private byte[] address = new byte[]{(byte)25, (byte)78, (byte) 152, (byte)219};	//PC Clement
	private byte[] address = new byte[]{(byte)25, (byte)57, (byte) 89, (byte)96};	//PC Nico
	////Constructeur////
	public PlayerWeb(int x, int y, int width, int height, String name, int direction, int vxOnGround) {
		super(x, y, width, height, name, direction, vxOnGround);
		// TODO Auto-generated constructor stub
		
	}
	
	////Méthodes////

	//Traduit le message texte envoyé par le player réseau
	public void translationAction(String message) {
		
		//Envoie des entrées du joueur réseua dans l'ordre suivant:
		//Booléens (0,1): upKey, leftKey, rightKey, shooting, mouse1, mouse2, mouse3
		//Puis coordonnées: xMouse, yMouse
		int posMsg=7;
		int xMouseLength=Integer.parseInt(message.substring(posMsg, posMsg+1));
		posMsg+=1;
		xMouse=Integer.parseInt(message.substring(posMsg, posMsg+xMouseLength));
		posMsg+=xMouseLength;
		int yMouseLength=Integer.parseInt(message.substring(posMsg, posMsg+1));
		posMsg+=1;
		yMouse=Integer.parseInt(message.substring(posMsg, posMsg+yMouseLength));
		
		upKey = message.charAt(0)=='1';
		leftKey = message.charAt(1)=='1';
		rightKey = message.charAt(2)=='1';
		shooting = message.charAt(3)=='1';
		if (mouse1 = message.charAt(4)=='1') {actionMouse(1, xMouse, yMouse);}
		if (mouse2 = message.charAt(5)=='1') {actionMouse(2, xMouse, yMouse);}
		if (mouse3 = message.charAt(6)=='1') {actionMouse(3, xMouse, yMouse);}
	}

	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}

}
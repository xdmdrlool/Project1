package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class PlayerLocal extends Player{

	//// Attributs////

	//Entrées clavier
	private boolean upKey=false;
	private boolean leftKey=false;
	private boolean rightKey=false;
	private boolean shooting=false;
	
	//Entrées souris
	private boolean mouse1=false;
	private boolean mouse2=false;
	private boolean mouse3=false;	
	
	private int xMouse;
	private int yMouse;
	
	//Tableau rassemblant les entrées dans l'ordre ci-dessus, à convertir en String pour le message
	private char[] input = {'0','0','0','0','0','0','0'};
	
	//Son
	Sound sound=new Sound("saut.wav");
	Sound sound1=new Sound("pan.wav");
	Sound sound2=new Sound("hitplayer.wav");
	
	
	////Constructeur////
	public PlayerLocal(int x, int y, int width, int height, String name, int direction, int vxOnGround) {
		super(x, y, width, height, name, direction, vxOnGround);
		// TODO Auto-generated constructor stub
	}
	
	//// Methodes////

	// Actions clavier
	public void actionKeyboardPressed(int key) {
		switch (key) {
		//Déplacement
		case KeyEvent.VK_Z: setUpKey(true); 		input[0]='1';	break;
		case KeyEvent.VK_Q: setLeftKey(true);		input[1]='1';	break;
		case KeyEvent.VK_D: setRightKey(true);		input[2]='1';	break;
		//Jet de projectiles
		case KeyEvent.VK_SPACE: setShooting(true);	input[3]='1';	break;
		}
	}
	
	public void actionKeyboardReleased(int key) {
		switch (key) {
		//Déplacement
		case KeyEvent.VK_Z: setUpKey(false);		input[0]='0';	break;
		case KeyEvent.VK_Q: setLeftKey(false);		input[1]='0';	break; 
		case KeyEvent.VK_D: setRightKey(false);		input[2]='0';	break;
		//Jet de projectiles
		case KeyEvent.VK_SPACE: setShooting(false);	input[3]='0'; 	break;
		}
	}
	
	
	// Actions souris
	public void actionMousePressed(int nClic, int xClic, int yClic) {
		switch(nClic) {
			case 1:	setMouse1(true); setxMouse(xClic); setyMouse(yClic); input[4]='0';	break;
			case 2:	setMouse2(true); setxMouse(xClic); setyMouse(yClic); input[5]='0';	break;
			case 3:	setMouse3(true); setxMouse(xClic); setyMouse(yClic); input[6]='0';	break;
		}
	}
	
	public void actionMouseReleased(int nClic, int xClic, int yClic) {
		switch(nClic) {
			case 1:	setMouse1(false); setxMouse(xClic); setyMouse(yClic); input[4]='0';	break;
			case 2:	setMouse2(false); setxMouse(xClic); setyMouse(yClic); input[5]='0';	break;
			case 3:	setMouse3(false); setxMouse(xClic); setyMouse(yClic); input[6]='0';	break;
		}
	}
	
	//Renvoie le message pour le serveur sous la forme de 7 booléens (0,1)
	//puis des coordonnées de la souris
	public String messageCreation() {
		
		String message = new String("");
		//On ajoute les entrées booléennes
		message = message + String.valueOf(input);
		
		//On ajoute les coordonnées de la souris
		String xMouseS = Integer.toString(xMouse);
		String yMouseS = Integer.toString(yMouse);
		
		message = message + xMouseS.length() + xMouseS + yMouseS.length() + yMouseS;
		
		return message;
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	

	public int getxMouse() {
		return xMouse;
	}

	public void setxMouse(int xMouse) {
		this.xMouse = xMouse;
	}

	public int getyMouse() {
		return yMouse;
	}

	public void setyMouse(int yMouse) {
		this.yMouse = yMouse;
	}


	public boolean isUpKey() {
		return upKey;
	}


	public void setUpKey(boolean upKey) {
		this.upKey = upKey;
	}
	
	public boolean isLeftKey() {
		return leftKey;
	}

	public void setLeftKey(boolean leftKey) {
		this.leftKey = leftKey;
	}

	public boolean isRightKey() {
		return rightKey;
	}

	public void setRightKey(boolean rightKey) {
		this.rightKey = rightKey;
	}

	public boolean isMouse1() {
		return mouse1;
	}

	public void setMouse1(boolean mouse1) {
		this.mouse1 = mouse1;
	}

	public boolean isMouse2() {
		return mouse2;
	}

	public void setMouse2(boolean mouse2) {
		this.mouse2 = mouse2;
	}

	public boolean isMouse3() {
		return mouse3;
	}

	public void setMouse3(boolean mouse3) {
		this.mouse3 = mouse3;
	}

	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	public char[] getInput() {
		return input;
	}

	public void setInput(char[] input) {
		this.input = input;
	}


}


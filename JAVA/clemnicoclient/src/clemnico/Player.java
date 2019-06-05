package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public abstract class Player extends Entity {

	//// Attributs////

	protected boolean upKey=false;
	protected boolean leftKey=false;
	protected boolean rightKey=false;
	
	
	
	protected int directionX = 0;
	protected int airControl = 1; // En pourcentage
	
	protected int keyPressed = 0;
	protected boolean dead = false;
	protected int ax = 2;
	protected int currentVx=0;
	protected int vxMax = 15;
	protected int vyMax = 30;
	protected int vJump = 30;
	
	
	protected boolean shooting=false;
	protected int reloadShoot=10;	//fréquence de tir (0=max)
	protected int timeShoot=0;	//durée depuis le dernier tir
	protected int bulletSize=30;
	protected int bulletSpeed=20;
	
	protected int xMouse;
	protected int yMouse;
	
	//Son
	Sound sound=new Sound("saut.wav");
	Sound sound1=new Sound("pan.wav");
	Sound sound2=new Sound("hitplayer.wav");
	
	////Constructeur////
	public Player(int x, int y, int width, int height, String name, int direction, int vxOnGround) {
		super(name, x, y, width, height);
		setDirectionX(direction);
		
		
	}

	
	//// Methodes////



	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	

	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}


	public int getDirectionX() {
		return directionX;
	}

	public void setDirectionX(int i) {
		this.directionX = i;
	}


}


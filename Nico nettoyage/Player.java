package fr.nicolas.snakex;

import java.awt.event.KeyEvent;

public class Player {

	////Attributs////
	private String 	name = "" ;
	private double 	health = 20 ;
	private double 	attack = 0.5;
	private int 	direction =0;
	private int 	speed = 300;
	private boolean move = false;
	private boolean dead =false;
	
	////Constructeur////
	public Player(String name, double health, double attack, int direction, int speed, boolean move) {
		this.name=name;
		this.health=health;
		this.attack=attack;
		this.direction=direction;
		this.speed=speed;
		this.move=move;
		
		
	}
	
	////Methodes////
	public void damage(double d) {
		this.health-=d;
	}
	
	public void actionKeyboard(int key) {
		
		switch (key) {
		
			case KeyEvent.VK_UP :
				setDirection(0);
				setMove(true);
				break;
				
			case KeyEvent.VK_LEFT :
				setDirection(1);
				setMove(true);
				break;
				
			case KeyEvent.VK_RIGHT :
				setDirection(2);
				setMove(true);
				break;
				
			case KeyEvent.VK_DOWN :
				setDirection(3);
				setMove(true);
				break;
				
			default :
				//statusBar.setText("rien");
				break;
			
		}
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	public double getAttack() {
		return attack;
	}
	public void setAttack(double attack) {
		this.attack = attack;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public boolean isMove() {
		return move;
	}
	public void setMove(boolean move) {
		this.move = move;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}

}

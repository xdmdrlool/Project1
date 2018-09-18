package clemnico;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

	////Attributs////
	private String 	name = "" ;
	private double 	health = 20 ;
	private double 	attack = 0.5;
	private int 	direction =0;
	private int 	speed = 300;
	private boolean move = false;
	private boolean dead =false;
	private Form form;
	private int xPlayer=100;
	private int yPlayer=100;
	
	////Constructeur////
	public Player(String name, double health, double attack, int direction, int speed, boolean move) {
		this.name=name;
		this.health=health;
		this.attack=attack;
		this.direction=direction;
		this.speed=speed;
		this.move=move;
		
		
		int[] array = {xPlayer,yPlayer,50};
		FormCircle circle = new FormCircle(Color.RED,array );

		
		this.setForm(circle);
		
		
	}
	
	////Methodes////
	public void damage(double d) {
		this.health-=d;
	}
	
	public void moveIn(int x ,int y) {
		this.setxPlayer(x);
		this.setyPlayer(y);
	}
	
	public void distanceStep(int dx,int dy) {
		this.moveIn(this.xPlayer+dx, this.yPlayer+dy);
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
	
	public void step(int period) {
		int dxPlayer= this.getSpeed()*period/1000;
		if (this.isMove()) {
			switch (this.getDirection()) {
		
				case 0 :
					this.distanceStep(0,-dxPlayer);
					break;
				case 1 :
					this.distanceStep(-dxPlayer,0);
					break;
				case 2 :
					this.distanceStep(dxPlayer,0);
					break;
				case 3 :
					this.distanceStep(0,dxPlayer);
					break;
					
			}
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

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public int getxPlayer() {
		return xPlayer;
	}

	public void setxPlayer(int xPlayer) {
		this.xPlayer = xPlayer;
		this.form.getArg()[0]=xPlayer;
		
	}

	public int getyPlayer() {
		return yPlayer;
	}

	public void setyPlayer(int yPlayer) {
		this.yPlayer = yPlayer;
		this.form.getArg()[1]=yPlayer;
	}

}
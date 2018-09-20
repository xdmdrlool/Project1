package clemnico;


import java.awt.Color;
import java.awt.event.KeyEvent;


public class Player {

	////Attributs////
	private String 	name = "" ;
	private double 	health = 20 ;
	private double 	attack = 0.5;
	private int 	direction = 0;
	private int 	speed = 300;
	private boolean move = false;
	private int keyPressed=0;
	private boolean dead =false;
	private FormCircle form;
	private int x=100;
	private int y=100;
	private int width=20;
	private int height =20;
	private Hitbox hitbox = new Hitbox(x,y,width,height,0);
	



	////Constructeur////
	public Player(String name, double health, double attack, int direction, int speed, boolean move) {
		this.name=name;
		this.health=health;
		this.attack=attack;
		this.setDirection(direction);
		this.speed=speed;
		this.setMove(move);

		FormCircle circle = new FormCircle(Color.RED,x,y,10 );

		
		this.setForm(circle);
		
		
	}


	////Methodes////
	public void damage(double d) {
		this.health-=d;
	}
	
	public void moveIn(int x ,int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void distanceStep(int dx,int dy) {
		this.moveIn(this.x+dx, this.y+dy);
	}
	
	public void actionKeyboard(int key) {
		
		if (key==KeyEvent.VK_Z && keyPressed!=key) {
			this.setDirection(0);
			this.setKeyPressed(key);
			this.setMove(true);
		}
		else if (key==KeyEvent.VK_Q && keyPressed!=key) {
			setDirection(1);
			this.setKeyPressed(key);
			this.setMove(true);
		}
		else if (key==KeyEvent.VK_D && keyPressed!=key) {
			setDirection(2);
			this.setKeyPressed(key);
			this.setMove(true);
		}
		else if (key==KeyEvent.VK_S && keyPressed!=key) {
			setDirection(3);
			this.setKeyPressed(key);
			this.setMove(true);
		}
		else {
			
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

	public FormCircle getForm() {
		return form;
	}

	public void setForm(FormCircle form) {
		this.form = form;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.form.setX(x);;
		this.hitbox.setX(x);		
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.form.setY(y);;
		this.hitbox.setY(y);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int i) {
		this.direction = i;
	}

	public boolean isMove() {
		return move;
	}
	public void setMove(boolean move) {
		this.move=move;
	}
	public int getKeyPressed() {
		return keyPressed;
	}
	public void setKeyPressed(int keyPressed) {
		this.keyPressed=keyPressed;
	}
	public Hitbox getHitbox() {
		return hitbox;
	}


	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
}

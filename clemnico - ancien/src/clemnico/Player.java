package clemnico;


import java.awt.Color;
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
	private Form form;
	private int x=100;
	private int y=100;
	private int width=50;
	private int height =50;
	private Hitbox hitbox = new Hitbox(x,y,width,height);
	
	////Constructeur////
	public Player(String name, double health, double attack, int direction, int speed, boolean move) {
		this.name=name;
		this.health=health;
		this.attack=attack;
		this.direction=direction;
		this.speed=speed;
		this.move=move;
		
		
		int[] array = {x,y,width,height};
		FormRect rect = new FormRect(Color.RED,array );

		
		this.setForm(rect);
		
		
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
		int dx= this.getSpeed()*period/1000;
		if (this.isMove()) {
			switch (this.getDirection()) {
		
				case 0 :
					this.distanceStep(0,-dx);
					break;
				case 1 :
					this.distanceStep(-dx,0);
					break;
				case 2 :
					this.distanceStep(dx,0);
					break;
				case 3 :
					this.distanceStep(0,dx);
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.form.getArg()[0]=x;
		this.hitbox.setX(x);
		
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.form.getArg()[1]=y;
		this.hitbox.setY(y);
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.form.getArg()[2]=width;
		this.hitbox.setWidth(width);
	}

	public int getHeight() {
		return height;
		
	}

	public void setHeight(int height) {
		this.height = height;
		this.form.getArg()[3]=height;
		this.hitbox.setHeight(height);
	}

}
package clemnico;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class Player extends Entity {

	////Attributs////
	private String 	name = "" ;

	private int 	direction = 0;
	private int 	speed = 300;
	private boolean move = false;
	private int keyPressed=0;
	private boolean dead =false;
	private FormCircle form;
	private int x=0;
	private int y=0;
	private int width=20;
	private int height =20;
	private int rayon=10;
	private Hitbox hitbox = new Hitbox("CIRCLE",x,y,rayon,width,height,0);
	private Animation animation;



	////Constructeur////
	public Player(int x,int y,int width, int height, int rayon,String name, int direction, int speed, boolean move) {
		super(x,y);
		FormCircle circle = new FormCircle(Color.RED,x,y,rayon );
		setForm(circle);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setRayon(rayon);
		this.name=name;
		setDirection(direction);
		this.speed=speed;
		setMove(move);

		
		
		
	}


	////Methodes////
	
	public void display(Graphics2D gg) {
		Sprite sprite =animation.getSprite();
		sprite.render(gg, x, y);
	}
	
	
	public void moveIn(int x ,int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void distanceStep(int dx,int dy) {
		this.moveIn(this.x+dx, this.y+dy);
	}
	
	public void actionKeyboard(int key) {
		
		if (key==KeyEvent.VK_UP && keyPressed!=key) {
			this.setDirection(0);
			this.setKeyPressed(key);
			this.setMove(true);
		}
		else if (key==KeyEvent.VK_LEFT && keyPressed!=key) {
			setDirection(1);
			this.setKeyPressed(key);
			this.setMove(true);
		}
		else if (key==KeyEvent.VK_RIGHT && keyPressed!=key) {
			setDirection(2);
			this.setKeyPressed(key);
			this.setMove(true);
		}
		else if (key==KeyEvent.VK_DOWN && keyPressed!=key) {
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
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.form.setX(x);;
		getHitbox().setX(x);		
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.form.setY(y);;
		getHitbox().setY(y);
	}
	public void setRayon(int rayon) {
		this.rayon = rayon;
		getHitbox().setRayon(rayon);
	}
	public int getRayon() {
		return rayon;
	}

	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
		getHitbox().setWidth(width);
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
		getHitbox().setHeight(height);
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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


	public Animation getAnimation() {
		return animation;
	}


	public void setAnimation(Animation animation) {
		this.animation = animation;
	}






}

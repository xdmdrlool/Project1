package clemnico;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Portal extends Entity{
	
	private int x=0;
	private int y=0;
	private int height=10;
	private int width=10;
	private double angle=0;
	private boolean moved;
	private FormRect form= new FormRect(Color.BLUE,x,y,width, height, angle);
	private Hitbox hitbox=new Hitbox("RECT",x,y+height/4,10,height/2,width,angle);
	private Animation currentAnimation;
	public Map<NameAnimation,Animation> ListAnimation=new  HashMap<>();
	FC fc=new FC();
	
	////Constructeur////
	public Portal(int x, int y, int width, int height){
		super(x,y);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	////Méthodes////
	
	//Renvoie l'angle du portail enfonction de la position du joueur et du clic
	public int angleRotation(int xPlayer, int yPlayer, int xClic, int yClic) {
		if (xClic==xPlayer) {
			return 0;
		}
		else if (xClic>xPlayer){
			return ((int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) - Math.PI/2.0));
		}
		else {
			return ((int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) + Math.PI/2.0));
		}
	}
	
	public void display(Graphics2D gg) {
		Sprite sprite =currentAnimation.getSprite();
		sprite.render(gg, x+width/2, y+height/2);
	}
	

	public boolean obstacleInteraction(Obstacle[] obstacles,Player player,int xClic,int yClic) {
		Point A=new Point(player.getX(),player.getY());
		Point B=new Point(xClic, yClic);
		for (Obstacle obstacle: obstacles) {
			//S'il y a interaction avec un obstacle
			if (this.getHitbox().collision(obstacle.getHitbox()) || fc.CollisionLineRect(A,B,obstacle.getForm())) {
				return true;
			}
		}
		return false;
	}
	
	
	//Déplace le portail que s'il n'est pas en contact avec un obstacle
	public void movePortal(Obstacle[] obstacles,Player player, int xClic, int yClic) {
		int xBefore=x;
		int yBefore=y;
		double angleBefore=angle;
		
		setX(xClic-width/2);
		setY(yClic-height/2);
		setAngle(angleRotation(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2,xClic,yClic));
		
		setAngle(angle);
		if(obstacleInteraction(obstacles,player,xClic,yClic)) {
			setX(xBefore);
			setY(yBefore);
			setAngle(angleBefore);
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
		this.form.setX(x);
		this.hitbox.setX(x);
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		this.form.setY(y);;
		this.hitbox.setY(y+height/4);
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
		this.form.setAngle(angle);;
		this.hitbox.setAngle(angle);
		this.currentAnimation.setAngle(angle);
	}
	public FormRect getForm() {
		return form;
	}
	public void setForm(FormRect form) {
		this.form = form;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		this.form.setHeight(height);;
		this.hitbox.setHeight(height/2);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.form.setWidth(width);;
		this.hitbox.setWidth(width);
	}
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(NameAnimation name) {
		Animation anime = ListAnimation.get(name);
		if (this.currentAnimation!=anime) {
			this.currentAnimation=anime;
			this.currentAnimation.reset();}
	}
	public Map<NameAnimation,Animation> getListAnimation() {
		return ListAnimation;
	}


	public void setListAnimation(Map<NameAnimation,Animation> listAnimation) {
		ListAnimation = listAnimation;
	}



	public void addAnimation(NameAnimation name,Animation animation) {
		this.ListAnimation.put(name,animation);
	}
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		setCurrentAnimation(name);
	}

	public FC getFc() {
		return fc;
	}

	public void setFc(FC fc) {
		this.fc = fc;
	}
}

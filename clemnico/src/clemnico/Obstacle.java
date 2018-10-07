package clemnico;

import java.awt.Color;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class Obstacle  extends Entity{
	
	private int x=0;
	private int y=0;
	private int xBefore=0;
	private int yBefore=0;
	private int vx=0;
	private int vy=0;
	private int height=10;
	private int width=10;
	private double angle=0;
	private int timeInAir=0;
	private boolean inTheAir=false;
	private FormRect form= new FormRect(Color.darkGray,x,y,width, height, angle);
	private Hitbox hitbox=new Hitbox("RECT",x,y,10,height,width,angle);
	private Animation currentAnimation;
	public Map<NameAnimation,Animation> ListAnimation=new  HashMap<>();
	
	public Obstacle(int x, int y, int width,int height,double angle) {
		super(x,y);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setAngle(angle);
	}
	
	public void display(Graphics2D gg) {
		Sprite sprite =currentAnimation.getSprite();
		sprite.render(gg, x+width/2, y+height/2);
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
		this.hitbox.setY(y);
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
		this.form.setAngle(angle);
		this.hitbox.setAngle(angle);
		if (this.currentAnimation != null) {this.currentAnimation.setAngle(angle);}		
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
		this.hitbox.setHeight(height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.form.setWidth(width);;
		this.hitbox.setWidth(width);
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
		this.currentAnimation.setAngle(this.getAngle());
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

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getxBefore() {
		return xBefore;
	}

	public void setxBefore(int xBefore) {
		this.xBefore = xBefore;
	}

	public int getyBefore() {
		return yBefore;
	}

	public void setyBefore(int yBefore) {
		this.yBefore = yBefore;
	}

	public int getTimeInAir() {
		return timeInAir;
	}

	public void setTimeInAir(int timeInAir) {
		this.timeInAir = timeInAir;
	}

	public boolean isInTheAir() {
		return inTheAir;
	}

	public void setInTheAir(boolean inTheAir) {
		this.inTheAir = inTheAir;
	}


}



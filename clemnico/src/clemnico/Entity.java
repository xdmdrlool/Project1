package clemnico;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
	
	
	////Attrbiuts////
	protected static AnimationCreator ACreator =new AnimationCreator();
	protected static  FC fc=new FC();
	protected int x;
	protected int y;
	protected int vx=0;
	protected int vy=0;
	protected int xBefore;
	protected int yBefore;
	protected int width;
	protected int height;
	protected int timeInAir=0;
	protected boolean inTheAir=false;
	protected String name;
	protected FormRect form =new FormRect(Color.RED, 0, 0, 0, 0, 0);
	protected Hitbox hitbox=new Hitbox("RECT", 0, 0, 10,0, 0, 0);
	protected Animation currentAnimation;
	protected Map<NameAnimation, Animation> ListAnimation = new HashMap<>();
	


	////Constructeur////
	public Entity(String name,int x, int y, int width,int height) {
		setX(x);
		setY(y);
		setxBefore(x);
		setyBefore(y);
		setWidth(width);
		setHeight(height);
		setName(name);
	}
	
	
	////Méthodes////
	public abstract void chooseAnimation();
	
	public abstract void useDefaultAnimations();
	
	public void display(Graphics2D gg,int xOffset,int yOffset) {
		Sprite sprite = getCurrentAnimation().getSprite();
		sprite.render(gg,xOffset+ x + width / 2,yOffset+ y + height / 2);
	}
	
	
	public boolean isInCollisionWith(Entity entity) {
		return hitbox.collision(entity.getHitbox());	
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////

	public FormRect getForm() {
		return form;
	}

	public void setForm(FormRect form) {
		this.form = form;
	}

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
		this.form.setY(y);
		this.hitbox.setY(y);
	}


	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getxBefore() {
		return xBefore;
	}

	public int getyBefore() {
		return yBefore;
	}

	public void setxBefore(int xBefore) {
		this.xBefore = xBefore;
	}

	public void setyBefore(int yBefore) {
		this.yBefore = yBefore;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
		this.form.setWidth(width);
		this.hitbox.setWidth(width);
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
		this.form.setHeight(height);
		this.hitbox.setHeight(height);
	}


	public int getTimeInAir() {
		return timeInAir;
	}

	public boolean isInTheAir() {
		return inTheAir;
	}

	public void setTimeInAir(int timeInAir) {
		this.timeInAir = timeInAir;
	}

	public void setInTheAir(boolean inTheAir) {
		this.inTheAir = inTheAir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}


	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
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

	public void addAnimation(NameAnimation string,Animation animation) {
		this.ListAnimation.put(string,animation);
	}

	public Animation getCurrentAnimation() {
		return this.currentAnimation;
	}

	@Override
	public String toString() {
		return "Entity [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}


	
	

	
	
}
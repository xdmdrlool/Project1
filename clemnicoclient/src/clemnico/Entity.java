package clemnico;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entity {
	
	
	////Attrbiuts////
	protected static AnimationCreator ACreator =new AnimationCreator();
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Layer layerIn =null;
	protected String name;
	protected FormRect form =new FormRect(Color.RED, 0, 0, 0, 0, 0);
	protected Animation currentAnimation;
	protected Map<NameAnimation, Animation> ListAnimation = new HashMap<>();
	


	////Constructeur////
	public Entity(String name,int x, int y, int width,int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	
	////Méthodes////
	
	public void display(Graphics2D gg,int xOffset,int yOffset,int w,int h) {
		if (xOffset+ x + width*2 / 2<0 || xOffset+ x >w ||yOffset+ y + height*2 / 2<0 ||yOffset+ y >h) {}
		else {Sprite sprite = getCurrentAnimation().getSprite();
		sprite.render(gg,xOffset+ x + width / 2,yOffset+ y + height / 2);}
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
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.form.setY(y);
	}




	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
		this.form.setWidth(width);
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
		this.form.setHeight(height);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String toString() {
		return this.getClass()+" [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}



	public Layer getLayerIn() {
		return layerIn;
	}


	public void setLayerIn(Layer layerIn) {
		this.layerIn = layerIn;
	}
	
	
}
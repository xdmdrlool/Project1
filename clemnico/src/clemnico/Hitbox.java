package clemnico;
import java.awt.Rectangle;
public class Hitbox {
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle form= new Rectangle(0,0,1,1);
	public Hitbox(int x, int y, int w, int h) {
		this.setX(x);
		this.setY(y);
		this.setWidth(w);
		this.setHeight(h);
		this.form=new Rectangle(x,y,w,h);
	}

	public boolean colision(Hitbox hb){
		int x2=hb.getX();
		int y2=hb.getY();
		int w2=hb.getWidth();
		int h2=hb.getHeight();
		
		if((x2 >= this.x + this.width)      // trop à droite
				|| (x2 + w2 <= this.x) // trop à gauche
				|| (y2 >= this.y + this.height) // trop en bas
				|| (y2 + h2 <= this.y))  // trop en haut
			          return false; 
			   else
			          return true;	
	}
	
	public void moveIn(int x,int y) {
		
	}

	public Rectangle getForm() {
		return form;
	}
	public void setForm(Rectangle form) {
		this.form = form;
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.form.x=x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.form.y=y;
	}



}

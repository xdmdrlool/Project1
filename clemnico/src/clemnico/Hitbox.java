package clemnico;
import java.awt.Color;


public class Hitbox {
	private String type;
	private int x=0;
	private int y=0;
	private int rayon=10;
	private int width=10;
	private int height=10;
	private double angle=0;
	private Form form;
	private Color color=Color.BLACK;
	public Hitbox(String type,int x, int y,int rayon, int w, int h,double angle) {
		switch (type) {
			case "RECT" :
				form=new FormRect(color,x,y,w,h,angle);
				this.setX(x);
				this.setY(y);
				this.setWidth(w);
				this.setHeight(h);
				this.setAngle(angle);
				break;
			case "CIRCLE" :
				form=new FormCircle(color,x,y,rayon);
				this.setX(x);
				this.setY(y);
				this.setRayon(rayon);
		}
		

	}

	
	public boolean collision(Hitbox hb){

		Form form1=this.form;
		Form form2=hb.getForm();
		FC fC= new FC();
		return fC.Collision(form1, form2);
	}
	
	public void moveIn(int x,int y) {
		
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	

	public Form getForm() {
		return form;
	}
	public void setForm(FormRect form) {
		this.form = form;
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

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
		this.form.setAngle((int) angle);
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getRayon() {
		return rayon;
	}


	public void setRayon(int rayon) {
		this.rayon = rayon;
		this.form.setRadius(rayon);
	}



}

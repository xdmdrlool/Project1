package clemnico;
import java.awt.Color;


public class Hitbox {
	private int x=0;
	private int y=0;
	private int width=10;
	private int height=10;
	private double angle;
	private FormRect form=new FormRect(Color.BLACK,x,y,width,height, 0);
	public Hitbox(int x, int y, int w, int h,double angle) {
		this.setX(x);
		this.setY(y);
		this.setWidth(w);
		this.setHeight(h);
		this.setAngle(angle);

	}

	
	public boolean colision(Hitbox hb){
		FormRect form1=this.form;
		FormRect form2=hb.getForm();
		FC fC= new FC();
		return fC.Collision(form1, form2);
	}
	
	public void moveIn(int x,int y) {
		
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



}

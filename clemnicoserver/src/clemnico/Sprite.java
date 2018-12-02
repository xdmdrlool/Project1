package clemnico;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {
	
	
	
	private BufferedImage texture;
	private int widthSizeDisplay; // taille dans la fenetre
	private int heightSizeDisplay;//
	private  double angle=0.;
	private int numberAnimation;
	
	
	
	public Sprite(SpriteSheet ss, int x,int y, int width,int height,int widthSizeDisplay, int heightSizeDisplay, double angle, int numberAnimation) {
		setWidthSizeDisplay(widthSizeDisplay);
		setHeightSizeDisplay(heightSizeDisplay);
		setAngle(angle);
		setTexture(ss.grabImage(x, y, width, height));
		setNumberAnimation(numberAnimation);
	}
	
	
	public void render(Graphics2D gg ,int x,int y) {
		
		double angleRad=Math.toRadians(angle);
		int w=widthSizeDisplay;
		int h=heightSizeDisplay;
		int x0=x;
		int y0=y;
		gg.rotate(angleRad,x0,y0);
		gg.drawImage(texture,x-w/2,y-h/2,w,h,null);
		gg.rotate(-angleRad,x0,y0);

	}
	
	
	
	
	
	
	
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public BufferedImage getTexture() {
		return texture;
	}
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}


	public int getWidthSizeDisplay() {
		return widthSizeDisplay;
	}


	public void setWidthSizeDisplay(int widthSizeDisplay) {
		this.widthSizeDisplay = widthSizeDisplay;
	}


	public int getHeightSizeDisplay() {
		return heightSizeDisplay;
	}


	public void setHeightSizeDisplay(int heightSizeDisplay) {
		this.heightSizeDisplay = heightSizeDisplay;
	}


	public double getAngle() {
		return angle;
	}


	public void setAngle(double angle) {
		this.angle = angle;
	}


	public int getNumberAnimation() {
		return numberAnimation;
	}


	public void setNumberAnimation(int numberAnimation) {
		this.numberAnimation = numberAnimation;
	}



}

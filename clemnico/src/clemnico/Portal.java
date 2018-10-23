package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Portal extends Entity{
	

	private double angle=0;
	private boolean moved;
	private Color color;
	
	////Constructeur////
	public Portal(int x, int y, int width, int height,String name,Color color){
		super(name, x,y, width, height);
		this.setColor(color);

	}
	
	////Méthodes////
	
	//Renvoie l'angle du portail enfonction de la position du joueur et du clic
	public int angleRotation(int xPlayer, int yPlayer, int xClic, int yClic) {
		if (xClic==xPlayer) {
			return 180;
		}
		else if (xClic>xPlayer){
			return ((int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) - Math.PI/2.0));
		}
		else {
			return ((int) Math.toDegrees(Math.atan((yPlayer-yClic)*1.0/(xPlayer-xClic)*1.0) + Math.PI/2.0));
		}
	}
	

	

	public boolean obstacleInteraction(ArrayList<Obstacle> obstacles,Player player,int xClic,int yClic) {
		Point A=new Point(player.getX()+player.getWidth()/2,player.getY()+player.getHeight()/2);
		Point B=new Point(xClic, yClic);
		for (Obstacle obstacle: obstacles) {
			//S'il y a interaction avec un obstacle
			if (isInCollisionWith(obstacle) || fc.CollisionLineRect(A,B,obstacle.getForm())) {
				return true;
			}
		}
		return false;
	}
	
	
	//Déplace le portail que s'il n'est pas en contact avec un obstacle
	public void movePortal(ArrayList<Obstacle> obstacles,Player player, int xClic, int yClic) {
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
	
	
	
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		setCurrentAnimation(name);
	}
	
	
	@Override
	public void useDefaultAnimations() {
		if (color==Color.BLUE) {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPortal1Default,width,height));}
		else {addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPortal2Default,width,height));}
	}
	

	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	


	public void setY(int y) {
		this.y = y;
		this.form.setY(y);
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



	public void setHeight(int height) {
		this.height = height;
		this.form.setHeight(height);;
		this.hitbox.setHeight(height/2);
	}


	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}





	

}

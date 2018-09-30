package clemnico;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import clemnico.FC.Vecteur;

public class GeneralEnemy extends Enemy {
////Attributs////
	
	boolean fallFromPlatform= false;
	
	private String 	name = "" ;
	private int 	directionX = 0;
	private int 	speed = 300;
	private boolean moveX = false;
	private boolean inTheAir=false;
	private int timeInAir=0;
	private int keyPressed=0;
	private boolean dead =false;
	private FormRect form;
	private int x=100;
	private int y=100;
	private int xBefore=x;
	private int yBefore=y;
	private int vx=5;
	private int vy=0;
	private int vxMax=100;
	private int vyMax=30;
	private int width=20;
	private int height =20;
	private int radius=60;
	private Hitbox hitbox = new Hitbox("RECT",x,y,radius,width,height,0);
	private FC fc=new FC();
	private Animation currentAnimation;
	public Map<NameAnimation,Animation> ListAnimation = new  HashMap<>();


	////Constructeur////
	public GeneralEnemy(int x,int y,int width, int height,String name, int direction, int speed, boolean move) {
		super(x,y);
		FormRect rect = new FormRect(Color.RED,x,y,width,height,0 );
		setForm(rect);
		setX(x);
		setY(y);
		setxBefore(x);
		setyBefore(y);
		setWidth(width);
		setHeight(height);
		setRadius(radius);
		this.name=name;
		setDirectionX(direction);
		this.speed=speed;
		setMoveX(move);	
	}


	////Methodes////
	public void display(Graphics2D gg) {
		Sprite sprite =currentAnimation.getSprite();
		sprite.render(gg, x+width/2, y+height/2);
	}
	
	public void moveIn(int x ,int y) {
		setX(x);
		setY(y);
	}
	
	public void distanceStep(int dx,int dy) {
		moveIn(this.x+dx, this.y+dy);
	}
	
	
	//Action de joueur pour un pas de la boucle
	public void step(int period) {
		
		//Mouvement vertical du joueur
		if (inTheAir) {fall();}
		
		setX(this.x+this.vx);
		chooseAnimation();
	}

	
	//Mouvement physique du joueur dans les airs sans entrée clavier
	public void fall() {
		double g=-5;
		double t=timeInAir/60.0;
		setVy((int)(vy-g*t));
		setX(x+vx);
		setY(y+vy);
	}
	
	
	public void obstacleInteraction2(FC fc, Obstacle[] obstacles) {
		Vecteur vecteurCorrection=null;
		Vecteur directionCollision=null;
		boolean varInTheAir=true;
		boolean inverseVx=false;
		for (Obstacle obstacle: obstacles) {
			//S'il y a collision avec un obstacle
			FormRect rect0=new FormRect(Color.RED, xBefore, yBefore, width, height, 0);
			FormRect rect=(FormRect) getHitbox().getForm();
			FormRect obs=(FormRect) obstacle.getHitbox().getForm();
			Vecteur[] tab = fc.calculVecteurCollisionRectDroitObstacleDroit(rect0,rect,obs);
			
			if (tab !=null) {
				vecteurCorrection=tab[0];
				directionCollision=tab[1];
//				System.out.println(directionCollision.x+" "+directionCollision.y);
//				System.out.println("xB :"+xBefore+"   yB : "+yBefore);
//				System.out.println(vecteurCorrection.x+" "+vecteurCorrection.y);
				if (vecteurCorrection.y<0||directionCollision.y>0) {varInTheAir=false;}
				if (directionCollision.x!=0) {inverseVx=true;}
				if (!fallFromPlatform) {if (  directionCollision.y>0 && (x<obstacle.getX() || x+width>obstacle.getX()+obstacle.getWidth())) {inverseVx=true;};}
				if (directionCollision.y!=0) {setVy(0);}

				int newX=(int) (getX()+vecteurCorrection.x);
				int newY=(int) (getY()+vecteurCorrection.y);
				
				
//				System.out.println("x: "+newX+"  y: "+newY);
//				System.out.println("air : "+isInTheAir());	

				setX(newX);
				setY(newY);
				
//				System.out.println("x :"+x+"   y : "+y+"     vy : "+vy+"     "+varInTheAir);
			}
			

		}
		setTimeInAir(getTimeInAir()+1);
		setInTheAir(varInTheAir);
		setxBefore(x);setyBefore(y);		
		if (inverseVx) {setVx(-vx);}
	}
	
	
	
	

	

	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
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
		this.form.setX(x);;
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

	public int getDirectionX() {
		return directionX;
	}

	public void setDirectionX(int i) {
		this.directionX = i;
	}

	public boolean isMoveX() {
		return moveX;
	}
	public void setMoveX(boolean moveX) {
		this.moveX=moveX;
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


	public boolean isInTheAir() {
		return inTheAir;
	}


	public void setInTheAir(boolean inTheAir) {
		if (this.inTheAir != inTheAir) {
			 setTimeInAir(0);
			this.inTheAir = inTheAir;}
	}


	public int getVx() {
		return vx;
	}


	public void setVx(int vx) {
		if (vx>0) {vx=Math.min(vx , vxMax );}
		else {vx=- Math.min(-vx, vxMax);}
		this.vx = vx;
	}


	public int getVy() {
		return vy;
	}


	public void setVy(int vy) {
		if (vy>0) {vy=Math.min(vy , vyMax );}
		else {vy=- Math.min(-vy, vyMax);}
		this.vy = vy;
	}


	public int getTimeInAir() {
		return timeInAir;
	}


	public void setTimeInAir(int timeInAir) {
		this.timeInAir = timeInAir;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		this.hitbox.setWidth(width);
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
		this.hitbox.setHeight(height);
	}

	


	public int getRadius() {
		return radius;
	}


	public void setRadius(int radius) {
		this.radius = radius;
		this.form.setRadius(radius);
		this.hitbox.setRayon(radius);
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


	@Override
	public Animation getCurrentAnimation() {
		return this.currentAnimation;
	}
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		if (inTheAir) {
			if (vy<=0) {if (vx>=0) {name=NameAnimation.JUMPR;}else {name=NameAnimation.JUMPL;}}
			else  {if (vx>=0) {name=NameAnimation.FALLR;}else {name=NameAnimation.FALLL;}}}
		
		else {if (vx>0) {name=NameAnimation.WALKR;}else if (vx<0) {name=NameAnimation.WALKL;}}
		setCurrentAnimation(name);
	}
}



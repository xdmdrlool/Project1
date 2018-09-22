package clemnico;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class Player extends Entity{

	////Attributs////
	private String 	name = "" ;
	private int 	directionX = 0;
	private int 	speed = 300;
	private boolean moveX = false;
	private boolean inTheAir=false;
	private int timeInAir=0;
	private int keyPressed=0;
	private boolean dead =false;
	private FormCircle form;
	private int x=100;
	private int y=100;
	private int vx=0;
	private int vy=0;
	private int width=20;
	private int height =20;
	private int rayon=10;
	private Hitbox hitbox = new Hitbox("CIRCLE",x,y,rayon,width,height,0);
	private Animation animation;



	////Constructeur////
	public Player(int x,int y,int width, int height, int rayon,String name, int direction, int speed, boolean move) {
		super(x,y);
		FormCircle circle = new FormCircle(Color.RED,x,y,rayon );
		setForm(circle);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setRayon(rayon);
		this.name=name;
		setDirectionX(direction);
		this.speed=speed;
		setMoveX(move);

		
		
		
	}


	////Methodes////
	public void display(Graphics2D gg) {
		Sprite sprite =animation.getSprite();
		sprite.render(gg, x, y);
	}
	
	public void moveIn(int x ,int y) {
		setX(x);
		setY(y);
	}
	
	public void distanceStep(int dx,int dy) {
		moveIn(this.x+dx, this.y+dy);
	}
	
	//Action clavier
	public void actionKeyboard(int key) {
		
		
		if (key==KeyEvent.VK_Z && !inTheAir) {
			setInTheAir(false);
			setVy(-50);
			setY(y-10);
			setVx(directionX*vx);
			
		}
		if (key==KeyEvent.VK_Q && keyPressed!=key) {
			setDirectionX(-1);
			setKeyPressed(key);
			setMoveX(true);
		}
		else if (key==KeyEvent.VK_D && keyPressed!=key) {
			setDirectionX(1);
			setKeyPressed(key);
			setMoveX(true);
		}
	}
	
	//Mouvement physique du joueur dans les airs sans entrée clavier
	public void fall() {
		double g=-9.81;
		double t=timeInAir/100.0;
		
		setY((int)(y-0.5*g*t*t+vy*t));
		setVy((int)(vy-g*t));
		setX(x+vx);
	}
	
	//Gestion de l'interaction joueur/portail
	public void portalInteraction(FC fc,Portal portal1, Portal portal2) {
		//Obtenir les points A et B des deux portails (axe, point, portail)
		int xA1=(int) fc.Rect2Array(portal1.getForm())[0].x;
		int yA1=(int) fc.Rect2Array(portal1.getForm())[0].y;
		int xB1=(int) fc.Rect2Array(portal1.getForm())[1].x;
		int yB1=(int) fc.Rect2Array(portal1.getForm())[1].y;
		int xD1=(int) fc.Rect2Array(portal1.getForm())[3].x;
		int yD1=(int) fc.Rect2Array(portal1.getForm())[3].y;
		int xA2=(int) fc.Rect2Array(portal2.getForm())[0].x;
		int yA2=(int) fc.Rect2Array(portal2.getForm())[0].y;
		int xB2=(int) fc.Rect2Array(portal2.getForm())[1].x;
		int yB2=(int) fc.Rect2Array(portal2.getForm())[1].y;
		int xD2=(int) fc.Rect2Array(portal2.getForm())[3].x;
		int yD2=(int) fc.Rect2Array(portal2.getForm())[3].y;
		
		//Détermine la vitesse min et la distance entre le joueur et le portail de sortie
		int distancePortal=10;
		int vMinOut=10;
		
		//Norme de la vitesse
		double vOut=Math.min(Math.sqrt(vx*vx+vy*vy), vMinOut);
		
		//Téléportation portail 1 (bleu) vers portail 2 (orange)
		if (hitbox.colision(portal1.getHitbox())) {
			double distancePlayerA1=Math.sqrt((x-xA1)*(x-xA1)+(y-yA1)*(y-yA1));
			double distancePlayerD1=Math.sqrt((x-xD1)*(x-xD1)+(y-yD1)*(y-yD1));
			double distancePlayerA2=portal2.getWidth()-distancePlayerA1;
			
			//Vecteurs normalisés AB et DA du portail 2
			double[] vectorAB2= {(xB2-xA2)*1./portal2.getWidth()*1.,(yB2-yA2)*1./portal2.getWidth()*1.};
			double[] vectorDA2= {(xA2-xD2)*1./portal2.getHeight()*1.,(yA2-yD2)*1./portal2.getHeight()*1.};
			//Détermine le côté du portail où passe le joueur
			if (distancePlayerA1<distancePlayerD1) {
				setX((int) (xA2+vectorAB2[0]*distancePlayerA2+distancePortal*vectorDA2[0]));
				setY((int) (yA2+vectorAB2[1]*distancePlayerA2+distancePortal*vectorDA2[1]));
				setVx((int)(vectorDA2[0]*vOut));
				setVy((int)(vectorDA2[1]*vOut));
			}
			else {
				setX((int) (xA2+vectorAB2[0]*distancePlayerA2-(distancePortal+portal2.getHeight())*vectorDA2[0]));
				setY((int) (yA2+vectorAB2[1]*distancePlayerA2-(distancePortal+portal2.getHeight())*vectorDA2[1]));
				setVx((int)(-vectorDA2[0]*vOut));
				setVy((int)(-vectorDA2[1]*vOut));
			}
		}
		//Téléportation portail 2 (orange) vers portail 1 (bleu)
		else if (hitbox.colision(portal2.getHitbox())){
			double distancePlayerA2=Math.sqrt((x-xA2)*(x-xA2)+(y-yA2)*(y-yA2));
			double distancePlayerD2=Math.sqrt((x-xD2)*(x-xD2)+(y-yD2)*(y-yD2));
			double distancePlayerA1=portal1.getWidth()-distancePlayerA2;
			
			//Vecteurs normalisés AB et DA du portail 2
			double[] vectorAB1= {(xB1-xA1)*1./portal1.getWidth()*1.,(yB1-yA1)*1./portal1.getWidth()*1.};
			double[] vectorDA1= {(xA1-xD1)*1./portal1.getHeight()*1.,(yA1-yD1)*1./portal1.getHeight()*1.};
			//Détermine le côté du portail où passe le joueur
			if (distancePlayerA2<distancePlayerD2) {
				setX((int) (xA1+vectorAB1[0]*distancePlayerA1+distancePortal*vectorDA1[0]));
				setY((int) (yA1+vectorAB1[1]*distancePlayerA1+distancePortal*vectorDA1[1]));
				setVx((int)(vectorDA1[0]*vOut));
				setVy((int)(vectorDA1[1]*vOut));
			}
			else {
				setX((int) (xA1+vectorAB1[0]*distancePlayerA1-(distancePortal+portal1.getHeight())*vectorDA1[0]));
				setY((int) (yA1+vectorAB1[1]*distancePlayerA1-(distancePortal+portal1.getHeight())*vectorDA1[1]));
				setVx((int)(-vectorDA1[0]*vOut));
				setVy((int)(-vectorDA1[1]*vOut));
			}
		}
	}
	
	//Gestion intéraction entre joueur et les obstacles
	public void obstacleInteraction(Obstacle obstacle) {
		if (this.getHitbox().colision(obstacle.getHitbox())) {
			this.setInTheAir(false);
			this.setTimeInAir(0);
			this.setVy(0);
			this.setVx(0);
		}
		else {
			this.setInTheAir(true);
			this.setTimeInAir(this.getTimeInAir()+1);
			
		}
	}
	
	
	
	
	//Action de joueur pour un pas de la boucle
		public void step(int period) {
			
			//Mouvement vertical du joueur
			if (inTheAir) {fall();}
			
			
			int vxOnGround=3;       //Mouvement latéral du joueur
			double AirControl=0.5;  //En pourcentage
			if (moveX) {
				if (isInTheAir()) {
					setX(x+(int)(this.directionX*AirControl*vxOnGround));
				}
				else {
					setVx(this.directionX*vxOnGround);
					setX(this.x+this.vx);
				}
			}
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

	public FormCircle getForm() {
		return form;
	}

	public void setForm(FormCircle form) {
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
		this.inTheAir = inTheAir;
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


	public Animation getAnimation() {
		return animation;
	}


	public void setAnimation(Animation animation) {
		this.animation = animation;
	}


	public int getRayon() {
		return rayon;
	}


	public void setRayon(int rayon) {
		this.rayon = rayon;
		this.hitbox.setRayon(rayon);
	}
}

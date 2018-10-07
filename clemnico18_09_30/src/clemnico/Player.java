package clemnico;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import clemnico.FC.Vecteur;


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
	private FormRect form;
	private int x=100;
	private int y=100;
	private int xBefore=x;
	private int yBefore=y;
	private int vx=0;
	private int vy=0;
	private int vxMax=100;
	private int vyMax=30;
	private int width=20;
	private int height =20;
	private int radius=60;
	private Hitbox hitbox = new Hitbox("RECT",x,y,radius,width,height,0);
	private FC fc=new FC();
	private Animation currentAnimation;
	public Map<NameAnimation,Animation> ListAnimation=new  HashMap<>();


	////Constructeur////
	public Player(int x,int y,int width, int height, int radius,String name, int direction, int speed, boolean move) {
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
	
	//Action clavier
	public void actionKeyboard(int key) {
		
		
		if (key==KeyEvent.VK_Z && !inTheAir) {
			setInTheAir(false);
			setVy(-20);
			setY(y-20);
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
		double g=-3;
		double t=timeInAir/10.0;
		
		setVy((int)(vy-g*t));
		setX(x+vx);
		setY(y+vy);
	}
	
	//Gestion de l'interaction joueur/portail
	public void portalInteraction(FC fc,Portal portal1, Portal portal2) {
				
		//S'il y a interaction avec l'un des deux portails
		if (hitbox.colision(portal1.getHitbox()) || hitbox.colision(portal2.getHitbox())) {
			
			//Détermine le portail d'entrée et de sortie
			Portal portalIn,portalOut;
			if (hitbox.colision(portal1.getHitbox())) 	{portalIn=portal1;	portalOut=portal2;}
			else 										{portalIn=portal2;	portalOut=portal1;}
			
			//Obtenir les points A et B des deux portails (axe, point, portail)
			//portalIn = 1 et portalOut = 2 
			int xA1=fc.Rect2Array(portalIn.getForm())[0].x;
			int yA1=fc.Rect2Array(portalIn.getForm())[0].y;
			int xB1=fc.Rect2Array(portalIn.getForm())[1].x;
			int yB1=fc.Rect2Array(portalIn.getForm())[1].y;
			int xD1=fc.Rect2Array(portalIn.getForm())[3].x;
			int yD1=fc.Rect2Array(portalIn.getForm())[3].y;
			int xA2=fc.Rect2Array(portalOut.getForm())[0].x;
			int yA2=fc.Rect2Array(portalOut.getForm())[0].y;
			int xB2=fc.Rect2Array(portalOut.getForm())[1].x;
			int yB2=fc.Rect2Array(portalOut.getForm())[1].y;
			int xD2=fc.Rect2Array(portalOut.getForm())[3].x;
			int yD2=fc.Rect2Array(portalOut.getForm())[3].y;
			
			//Détermine la vitesse min et la distance entre le joueur et le portail de sortie
			int distancePortal=10;
			int vMinOut=10;
			
			//Détermine la position du joueur sur la tangente du portail
			double distancePlayerA1=Math.sqrt((x-xA1)*(x-xA1)+(y-yA1)*(y-yA1));
			double distancePlayerD1=Math.sqrt((x-xD1)*(x-xD1)+(y-yD1)*(y-yD1));
			double distancePlayerA2=portalOut.getWidth()-distancePlayerA1;
			
			//Détermine la norme de la vitesse en sortie
			double vNormIn=Math.sqrt(vx*vx+vy*vy); 						//Norme de la vitesse d'entrée
			double[] vectorVIn= {vx*1./vNormIn*1.,vy*1./vNormIn*1.};	//Vecteur vitesse normalisé
			double vNormOut=Math.max(vNormIn, vMinOut);					//Vitesse minorée en sortie de portail
			
			//Vecteurs normalisés AB et DA du portail In et Out
			double[] vectorAB1= {(xB1-xA1)*1./portalIn.getWidth()*1.,(yB1-yA1)*1./portalIn.getWidth()*1.};
			double[] vectorAB2= {(xB2-xA2)*1./portalOut.getWidth()*1.,(yB2-yA2)*1./portalOut.getWidth()*1.};
			double[] vectorDA2= {(xA2-xD2)*1./portalOut.getHeight()*1.,(yA2-yD2)*1./portalOut.getHeight()*1.};
			
			//Détermine l'orientation de la vitesse en sortie en fonction de celle en entrée
			double thetaIn=Math.acos(vectorAB1[0]*vectorVIn[0]+vectorAB1[1]*vectorVIn[1]);
			double thetaOut=(thetaIn-Math.PI);
			double[] vectorVOut= {(vectorAB2[0]*Math.cos(thetaOut)-vectorAB2[1]*Math.sin(thetaOut)),
							      (vectorAB2[0]*Math.sin(thetaOut)+vectorAB2[1]*Math.cos(thetaOut))};
			int[] vOut= {(int) (vectorVOut[0]*vNormOut), (int) (vectorVOut[1]*vNormOut)};
			
			//Détermine le côté du portail où passe le joueur
			correctionInteractionRect(fc, portalIn.getForm());
			if (distancePlayerA1<distancePlayerD1) {
				setX((int) (xA2+vectorAB2[0]*distancePlayerA2+distancePortal*vectorDA2[0]));
				setY((int) (yA2+vectorAB2[1]*distancePlayerA2+distancePortal*vectorDA2[1]));
				setVx(vOut[0]);
				setVy(vOut[1]);
			}
			else {
				setX((int) (xA2+vectorAB2[0]*distancePlayerA2-(distancePortal+portalOut.getHeight())*vectorDA2[0]));
				setY((int) (yA2+vectorAB2[1]*distancePlayerA2-(distancePortal+portalOut.getHeight())*vectorDA2[1]));
				setVx(-vOut[0]);
				setVy(-vOut[1]);
			}
		}
	}
	
	//Gestion intéraction entre joueur et les obstacles
	public void obstacleInteraction(FC fc, Obstacle[] obstacles) {
		
		for (Obstacle obstacle: obstacles) {
			//S'il y a collision avec un obstacle
			if (this.getHitbox().colision(obstacle.getHitbox())) {
				this.setVy(0);
				this.setVx(0);
				
				//Si l'obstacle est un sol ou un plafond
				if(yBefore<=y) {
					this.setTimeInAir(0);
					this.setInTheAir(false);
				}
				correctionInteractionRect(fc, obstacle.getForm());
				break;
			}
			else {
				this.setInTheAir(true);
				this.setTimeInAir(this.getTimeInAir()+1);
			}
		}
		setxBefore(x);
		setyBefore(y);
	}
	
	public void correctionInteractionRect(FC fc, FormRect rect) {
		Point pointPlayer1=new Point(xBefore,yBefore);
		Point pointPlayer2=new Point(x,y);
		
		Point A=new Point(fc.Rect2Array(rect)[0].x,fc.Rect2Array(rect)[0].y);
		Point B=new Point(fc.Rect2Array(rect)[1].x,fc.Rect2Array(rect)[1].y);
		Point C=new Point(fc.Rect2Array(rect)[2].x,fc.Rect2Array(rect)[2].y);
		Point D=new Point(fc.Rect2Array(rect)[3].x,fc.Rect2Array(rect)[3].y);
		
		double dmin=10000;
		Point correctedPosition=new Point(-1,-1);
		
		if (fc.calculIntersectionSeg(A,B,pointPlayer1,pointPlayer2)!=null && fc.distance(A, B)<dmin)
			{correctedPosition=fc.calculIntersectionSeg(A,B,pointPlayer1,pointPlayer2);}
		if (fc.calculIntersectionSeg(B,C,pointPlayer1,pointPlayer2)!=null && fc.distance(A, B)<dmin)
			{correctedPosition=fc.calculIntersectionSeg(A,B,pointPlayer1,pointPlayer2);}
		if (fc.calculIntersectionSeg(C,D,pointPlayer1,pointPlayer2)!=null && fc.distance(A, B)<dmin)
			{correctedPosition=fc.calculIntersectionSeg(A,B,pointPlayer1,pointPlayer2);}
		if (fc.calculIntersectionSeg(D,A,pointPlayer1,pointPlayer2)!=null && fc.distance(A, B)<dmin)
			{correctedPosition=fc.calculIntersectionSeg(A,B,pointPlayer1,pointPlayer2);}
		if (correctedPosition.x!=-1 && correctedPosition.y!=-1) {
			setX(correctedPosition.x-radius);
			setY(correctedPosition.y-radius);
		}
	}
	
	
public void obstacleInteraction2(FC fc, Obstacle[] obstacles) {
		Vecteur vecteurCorrection=null;
		Vecteur directionCollision=null;
		boolean varInTheAir=true;
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
				if (directionCollision.x!=0) {setVx(0);}
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

		
				
	}
	
	
	
	

	
	//Action de joueur pour un pas de la boucle
		public void step(int period) {
			
			//Mouvement vertical du joueur
			if (inTheAir) {fall();}
			
			
			int vxOnGround=3;       //Mouvement latéral du joueur
			double AirControl=1.0;  //En pourcentage
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
		if (!this.inTheAir && inTheAir) {setTimeInAir(0);}
		this.inTheAir = inTheAir;
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
		this.currentAnimation=ListAnimation.get(name);
		this.currentAnimation.reset();
		
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
}


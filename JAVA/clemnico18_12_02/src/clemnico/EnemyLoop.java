package clemnico;

import java.awt.Point;
import java.util.ArrayList;

public class EnemyLoop extends Enemy {
	
	////Attributs////
	private ArrayList<Point> path = new ArrayList<>();
	private int posPath=0;
	
	private Point startPos;
	private int amplitude=10;
	
	//Son
	Sound sound=new Sound("pouletHit.wav");
	
	
	////Constructeur////
	public EnemyLoop(int x,int y,int width, int height,String name, boolean fallFromPlatform,String namePath) {
		super(name, x,y, width, height);
		setVxOnGround(5);
		setVxMax(vxOnGround);
		setVx(vxOnGround);
		setAx(1);
		setStartPos(new Point(x,y));
		
		setPath(new EnemyPath(namePath).load());
		
		setvRecoil(10);
		setTimeEndRecoil(10);
		
		setFallFromPlatform(false);
	}


	////Methodes////
	public void movement() {
		setX(path.get(posPath).x*amplitude+startPos.x);
		setY(path.get(posPath).y*amplitude+startPos.y);
		
		if (posPath==path.size()-1) {
			setPosPath(0);
		}
		else {
			setPosPath(posPath+1);
		}
	}
	
	public void touched(int vxProjectile, int vyProjectile) {
		sound.play();
		setRecoil(true);
		setTimeRecoil(0);
		setvRecoil((int) Math.signum(vxProjectile)*Math.abs(getvRecoil()));
	}
	
	public void step(Window window,Portal portal1, Portal portal2,ArrayList<Obstacle> obstacles,ArrayList<Entity> entities, Player player) {
		
		movement();
				
		fc.portalInteractionRect(this, portal1, portal2);	// Gestion portails teleportations
		//fc.obstacleInteractionEnemy(this, obstacles);		// Gestion obstacle
		getCurrentAnimation().update();	
		chooseAnimation();
		
	}


	public ArrayList<Point> getPath() {
		return path;
	}


	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}


	public int getPosPath() {
		return posPath;
	}


	public void setPosPath(int posPath) {
		this.posPath = posPath;
	}


	public Point getStartPos() {
		return startPos;
	}


	public void setStartPos(Point startPos) {
		this.startPos = startPos;
	}
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////

}

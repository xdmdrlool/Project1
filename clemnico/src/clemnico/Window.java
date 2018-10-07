package clemnico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window extends JFrame {

	//// Attributs////
	public Panel panel;
	private JLabel statusBar;
	private int fps;
	protected ImageLoader loader = new ImageLoader();
	private Handlerclass handler;
	private int width=1500;
	private int height=1000;
	
	public static AnimationCreator AC =new AnimationCreator();
	
	// Objets de la fenêtre
	Player player = new Player(400, 200, 100, 100,"Player1", 0, 6);
	Portal portal1 = new Portal(-500, -500, 100, 20,"Portal1",Color.BLUE);
	Portal portal2 = new Portal(-500, -500, 100, 20,"Portal2",Color.ORANGE);
	Obstacle obstacle = new FixObstacle(300, 600, 400, 200,"Obs1", 0);
	Obstacle obstacle2 = new FixObstacle(900, 550, 200, 247,"Obs2", 0);
	Obstacle obstacle3 = new FixObstacle(150, 500, 200, 200,"Obs3", 0);
	

	GeneralEnemy enemy = new GeneralEnemy(400, 400, 50, 50, "Enemy1", 0, 5,false);
	
	MovingPlatform movingPlatform= new MovingPlatform("Plate",400,501,700,501,500,50, 1000);
	
	
	Obstacle[] obstacles = { obstacle, obstacle2, obstacle3,movingPlatform };
	
	
	
	ArrayList<Projectile> projectiles=player.getProjectiles();
	int projectileCount=0;
	FC fc = new FC();
	
	

	//// Constructeur////
	public Window(int fps) {
		this.fps = fps;

		this.setTitle("Ma fenetre");
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);

		portal1.getForm().setColor(Color.BLUE);
		portal2.getForm().setColor(Color.ORANGE);

		initPanel();
		stepGame(player, enemy);

	}

	//// Methodes////

	private void initPanel() {
		panel = new Panel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);

		statusBar = new JLabel("default");
		add(statusBar, BorderLayout.SOUTH);

		Handlerclass handler = new Handlerclass(panel, statusBar, player, portal1, portal2, obstacles);
		panel.addMouseListener(handler);
		panel.addMouseMotionListener(handler);
		addKeyListener(handler);
		
		setHandler(handler);
		
		
		
		// Animations
		player.useDefaultAnimations();		
		portal1.useDefaultAnimations();
		portal2.useDefaultAnimations();
		obstacle.useDefaultAnimations();
		obstacle.useDefaultAnimations();
		obstacle2.useDefaultAnimations();
		obstacle3.useDefaultAnimations();
		enemy.useDefaultAnimations();
		movingPlatform.useDefaultAnimations();
		
		
		player.setCurrentAnimation(NameAnimation.DEFAULT);
		portal1.setCurrentAnimation(NameAnimation.DEFAULT);
		portal2.setCurrentAnimation(NameAnimation.DEFAULT);
		obstacle.setCurrentAnimation(NameAnimation.DEFAULT);
		obstacle2.setCurrentAnimation(NameAnimation.DEFAULT);
		obstacle3.setCurrentAnimation(NameAnimation.DEFAULT);
		enemy.setCurrentAnimation(NameAnimation.DEFAULT);
		movingPlatform.setCurrentAnimation(NameAnimation.DEFAULT);
		ArrayList<Entity> array = new ArrayList<Entity>();
		array.add(obstacle);
		array.add(obstacle2);
		array.add(obstacle3);
		array.add(player);
		array.add(portal1);
		array.add(portal2);
		
		array.add(enemy);
				
		array.add(movingPlatform);
		
		panel.setEntityList(array);
		
		
	}

	
	private void refreshPanel() {
		
		projectiles=player.getProjectiles();
		ArrayList<Entity> array = panel.getEntityList();
		
		//Si un projectile a été rajouté par le joueur en faisant espace
		if (projectileCount<projectiles.size()) {			
			projectiles.get(projectileCount).addAnimation(NameAnimation.DEFAULT,AC.createAnimation(Animations.AnimationObsatcleDefault2,projectiles.get(projectileCount).getWidth(),projectiles.get(projectileCount).getHeight()));
			projectiles.get(projectileCount).setCurrentAnimation(NameAnimation.DEFAULT);
			array.add(projectiles.get(projectileCount));
			projectileCount+=1;
			panel.setEntityList(array);
		}
	}
	
	private void stepGame(Player player, GeneralEnemy enemy) {

		Timer chrono = new Timer();
		int delay = 100;
		int period = 1000 / this.fps;

		chrono.schedule(new TimerTask() {
			long time = 0;

			@Override
			public void run() {
				time = time + 1;
				player.step(period);
				enemy.step(period);
				
				refreshPanel();
				ArrayList<Projectile> toRemove= new ArrayList<>();
				for (Projectile projectile : projectiles) {
					fc.portalInteractionRect(projectile, portal1, portal2);
					projectile.step(period,player,1,1);
					toRemove=projectile.isOut(toRemove, width, height,panel.getxOffset(),panel.getyOffset());
				}
				for (Projectile projectile : toRemove) {
					projectiles.remove(projectile);
					projectileCount-=1;
				}
				
				// Gestion portails teleportations
				fc.portalInteractionRect(player, portal1, portal2);
				enemy.portalInteraction(fc, portal1, portal2);
//				System.out.println(player.getX()+" "+player.isInTheAir());

				// Gestion obstacle
				player.obstacleInteraction(fc, obstacles);
				enemy.obstacleInteraction2(fc, obstacles);
//				System.out.println(player.getForm().getX()+"   "+player.getForm().getY()+"   "+player.getTimeInAir());

				player.getCurrentAnimation().update();					
				caculCameraOffset(panel, player);
				movingPlatform.update();
				panel.repaint();
			}
				
			}, delay, period);
	}

	
	public void caculCameraOffset(Panel panel,Player player) {
		int w0=this.getWidth();int h0=this.getHeight();
		int xOff=panel.getxOffset();int yOff=panel.getyOffset();
		int x=player.getX();int y=player.getY();int w=player.getWidth();int h=player.getHeight();
		int a;
		if (x+xOff<1*w0/4) {a=w0/4-x;panel.setxOffset(a);handler.setxOffset(a);}
		else if (x+w+xOff>3*w0/4) {a=3*w0/4-x-w;panel.setxOffset(a);handler.setxOffset(a);}
		if (y+yOff<1*h0/4) {a=h0/4-y;panel.setyOffset(a);handler.setyOffset(a);}
		else if (y+h+yOff>3*h0/4) {a=3*h0/4-y-h;panel.setyOffset(a);handler.setyOffset(a);}
	}
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public Handlerclass getHandler() {
		return handler;
	}

	public void setHandler(Handlerclass handler) {
		this.handler = handler;
	}
	

}

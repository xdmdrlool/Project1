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
	Player player = new Player(100, 200, 100, 100,"Player1", 0, 6);
	Portal portal1 = new Portal(-500, -500, 100, 20,"Portal1",Color.BLUE);
	Portal portal2 = new Portal(-500, -500, 100, 20,"Portal2",Color.ORANGE);
	Map map=new Map("carte");

	GeneralEnemy enemy = new GeneralEnemy(400, 400, 50, 50, "Enemy1", 0, 5,false);
	
	//MovingPlatform movingPlatform= new MovingPlatform("Plate",400,501,700,501,500,50, 1000);
	
	
	ArrayList<Obstacle> obstacles= map.load();
	

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
		for (Obstacle obstacle : obstacles) {
			obstacle.useDefaultAnimations();
		}
		enemy.useDefaultAnimations();
		//movingPlatform.useDefaultAnimations();
		
		
		player.setCurrentAnimation(NameAnimation.DEFAULT);
		portal1.setCurrentAnimation(NameAnimation.DEFAULT);
		portal2.setCurrentAnimation(NameAnimation.DEFAULT);
		for (Obstacle obstacle : obstacles) {
			obstacle.setCurrentAnimation(NameAnimation.DEFAULT);
		}
		enemy.setCurrentAnimation(NameAnimation.DEFAULT);
		//movingPlatform.setCurrentAnimation(NameAnimation.DEFAULT);
		
		
		ArrayList<Layer> arrayLayer= new ArrayList<Layer>();
		Layer layer1=new Layer(0);
		layer1.add(player);
		layer1.add(portal1);
		layer1.add(portal2);
		for (Obstacle obstacle : obstacles) {
			layer1.add(obstacle);
		}
		
		layer1.add(enemy);
		//layer1.add(movingPlatform);
		
		Layer layer2=new Layer(200);
		//layer2.add(obstacle3);
		
		
		arrayLayer.add(layer1);
		arrayLayer.add(layer2);
		panel.setListLayer(arrayLayer);
		
	}

	
	private void refreshPanel() {
		
		projectiles=player.getProjectiles();
		
		//Si un projectile a été rajouté par le joueur en faisant espace
		if (projectileCount<projectiles.size()) {			
			projectiles.get(projectileCount).useDefaultAnimations();
			projectiles.get(projectileCount).setCurrentAnimation(NameAnimation.DEFAULT);
			panel.addToMainLayer( projectiles.get(projectileCount));
			projectileCount+=1;
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
				
				//Gestion des projectiles
				ArrayList<Projectile> toRemove= new ArrayList<>();
				for (Projectile projectile : projectiles) {
					fc.portalInteractionRect(projectile, portal1, portal2);
					projectile.step(period,player,1,1);
					projectile.getCurrentAnimation().update();	
					toRemove=projectile.isOut(toRemove, width, height,panel.getxOffset(),panel.getyOffset(), fc.obstacleInteraction(projectile, obstacles));
				}
				for (Projectile projectile : toRemove) {
					projectiles.remove(projectile);
					panel.deleteEntity(projectile);
					projectileCount-=1;
				}
				
				// Gestion portails teleportations
				fc.portalInteractionRect(player, portal1, portal2);
				fc.portalInteractionRect(enemy, portal1, portal2);

				// Gestion obstacle
				fc.obstacleInteraction(player, obstacles);
				enemy.obstacleInteractionEnemy(fc, obstacles);

				player.getCurrentAnimation().update();					
				calculCameraOffset(panel, player);
				//movingPlatform.update();
				panel.repaint();
			}
				
			}, delay, period);
	}

	
	public void calculCameraOffset(Panel panel,Player player) {
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

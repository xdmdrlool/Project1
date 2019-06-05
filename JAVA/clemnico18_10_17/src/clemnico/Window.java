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
	public static FC fc = new FC();
	
	// Objets de la fenêtre
	Player player = new Player(100, 200, 100, 100,"Player1", 0, 6);
	Portal portal1 = new Portal(-500, -500, 100, 20,"Portal1",Color.BLUE);
	Portal portal2 = new Portal(-500, -500, 100, 20,"Portal2",Color.ORANGE);
	Map map=new Map("carte");

	GeneralEnemy enemy = new GeneralEnemy(500, 400, 50, 50, "Enemy1", 0, 5,false);
	
	//MovingPlatform movingPlatform= new MovingPlatform("Plate",400,501,700,501,500,50, 1000);
	
	
	ArrayList[] objectsMap=map.load();
	ArrayList<Obstacle> obstacles=fc.concatenate(objectsMap[0],objectsMap[1]);
	ArrayList<GeneralEnemy> enemies= objectsMap[2];
	

	
	
	

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
		
		stepGame(player, enemies);

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
		for (GeneralEnemy enemy : enemies) {
			enemy.useDefaultAnimations();
		}
		
		player.setCurrentAnimation(NameAnimation.DEFAULT);
		portal1.setCurrentAnimation(NameAnimation.DEFAULT);
		portal2.setCurrentAnimation(NameAnimation.DEFAULT);
		for (Obstacle obstacle : obstacles) {
			obstacle.setCurrentAnimation(NameAnimation.DEFAULT);
		}
		for (GeneralEnemy enemy : enemies) {
			enemy.setCurrentAnimation(NameAnimation.DEFAULT);
		}
		
		ArrayList<Layer> arrayLayer= new ArrayList<Layer>();
		Layer layer1=new Layer(0);
		Layer layer2=new Layer(200);
		
		layer1.add(player);
		layer1.add(portal1);
		layer1.add(portal2);
		for (Obstacle obstacle : obstacles) {
			layer1.add(obstacle);
		}
		for (GeneralEnemy enemy : enemies) {
			layer1.add(enemy);
		}
		
		arrayLayer.add(layer1);
		arrayLayer.add(layer2);
		panel.setListLayer(arrayLayer);
		
	}

	
	
	private void stepGame(Player player, ArrayList<GeneralEnemy> enemies) {
		
		Timer chrono = new Timer();
		int delay = 100;
		int period = 1000 / this.fps;

		chrono.schedule(new TimerTask() {
			long time = 0;

			@Override
			public void run() {
				time = time + 1;
				player.step(panel,portal1, portal2, enemies, width, height, obstacles);
					
				calculCameraOffset(panel, player);
				
				for (Obstacle obstacle : obstacles) {
					obstacle.update();
				}
				for (GeneralEnemy enemy : enemies) {
					enemy.step(portal1, portal2, obstacles);
				}
				
				panel.repaint();
			}
				
			}, delay, period);
	}

	
	
	public void calculCameraOffset(Panel panel,Player player) {
		int w0=this.getWidth();int h0=this.getHeight();
		int xOff=panel.getxOffset();int yOff=panel.getyOffset();
		int x=player.getX();int y=player.getY();int w=player.getWidth();int h=player.getHeight();
		int p;
		
		// la camera bouge quand le joueur à a/b du bord
		int a=2;
		int b=7;
		
		// c/d : vitesse de la camera ; c/d=0 : mvt instantané   ;c/d=1 : pas de mvt
		int c=17;
		int d=20;
		
		if (x+xOff<a*w0/b) {p=a*w0/b-x;panel.setxOffset(p+(c*(xOff-p))/d);handler.setxOffset(p+(c*(xOff-p))/d);}
		else if (x+w+xOff>(b-a)*w0/b) {p=(b-a)*w0/b-x-w;panel.setxOffset(p+(c*(xOff-p))/d);handler.setxOffset(p+(c/(xOff-p))/d);}
		if (y+yOff<a*h0/b) {p=a*h0/b-y;panel.setyOffset(p+(c*(yOff-p))/d);handler.setyOffset(p+(c*(yOff-p))/d);}
		else if (y+h+yOff>(b-a)*h0/b) {p=(b-a)*h0/b-y-h;panel.setyOffset(p+(c*(yOff-p))/d);handler.setyOffset(p+(c*(yOff-p))/d);}
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

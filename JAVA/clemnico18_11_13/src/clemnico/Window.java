package clemnico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window extends JFrame {

	//// Attributs////
	
	public Panel panel;
	public JLabel statusBar;
	private int fps;
	protected ImageLoader loader = new ImageLoader();
	private Handlerclass handler;
	private int width=1500;
	private int height=1000;
	
	public static AnimationCreator AC =new AnimationCreator();
	public static FC fc = new FC();
	
	public Level level;
	
	Portal portal1 = new Portal(-500, -500, 100, 20,"Portal1",Color.BLUE);
	Portal portal2 = new Portal(-500, -500, 100, 20,"Portal2",Color.ORANGE);

	

	
	

	//// Constructeur////
	public Window(int fps) {

		try {
			setLevel(new Level("level1", this));} catch (IOException e) {e.printStackTrace();}
		
		Player p=new Player(100, 100, 50, 50, "Player1", 1, 6);
		p.useDefaultAnimations();
		level.addToMainLayer(p);
		level.player=p;
		
		this.fps = fps;

		this.setTitle("Ma fenetre");
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		
		
		portal1.getForm().setColor(Color.BLUE);
		portal2.getForm().setColor(Color.ORANGE);
		portal1.useDefaultAnimations();
		portal2.useDefaultAnimations();
		level.addToMainLayer(portal1);
		level.addToMainLayer(portal2);
		
		initPanel();
		
		stepGame();

	}

	//// Methodes////

	private void initPanel() {
		
		panel = new Panel();
		panel.setW(width);
		panel.setH(height);
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);
		
		statusBar = new JLabel("default");
		add(statusBar, BorderLayout.SOUTH);
		Handlerclass handler = new Handlerclass(panel, statusBar, level.player, portal1, portal2);
		panel.addMouseListener(handler);
		panel.addMouseMotionListener(handler);
		addKeyListener(handler);
		
		setHandler(handler);
		
		panel.setListLayer(level.getListLayer());
		
		// Animations
		
		
	}

	
	
	private void stepGame() {
		
		Timer chrono = new Timer();
		int delay = 0;
		int period =1000 / this.fps;

		chrono.schedule(new TimerTask() {
			long time = 0;
			int t;
			int d=20;
			@Override
			public void run() {
				// Calcul du FPS reel 
				d=Calendar.getInstance().get(Calendar.MILLISECOND)-t;
				int fps=(int) (1000*1.0/(d*1.0));
//				statusBar.setText(Integer.toString(fps)+ " "+ Integer.toString(d));
				t =Calendar.getInstance().get(Calendar.MILLISECOND);
				
				time = time + 1;
				
				level.gameloop();
				
				panel.repaint();
				
			}
				
			}, delay, period);
	}

	
	
	public void calculCameraOffset(Panel panel,Player player) {
		Boolean fixe=true;
		Boolean milieu=true;
		
		int w0=this.getWidth();int h0=this.getHeight();
		int xOff=panel.getxOffset();int yOff=panel.getyOffset();
		int x=player.getX();int y=player.getY();int w=player.getWidth();int h=player.getHeight();
		
		
		// la camera bouge quand le joueur � a/b du bord
		int a=5;
		int b=12;
		// c/d : vitesse de la camera ; c/d=0 : mvt instantan�   ;c/d=1 : pas de mvt
		int c=93;
		int d=100;
		int p=yOff;
		if (y+yOff<a*h0*1./b) {p=a*h0/b-y;}
		else if (y+h+yOff>(b-a)*h0*1./b) {p=(b-a)*h0/b-y-h;}
		
		panel.setyOffset(p+(c*(yOff-p))/d);handler.setyOffset(p+(c*(yOff-p))/d);
		
		p=xOff;
		if (fixe) {
			if (milieu) {p=w0/2-x;c=0;}
			else if(level.player.getDirectionX()==1){p=w0/3-x;} 
			else {p=(2*w0)/3-x;}
			}
		
		else if (x+xOff<a*w0*1./b) {p=a*w0/b-x;}
		else if (x+w+xOff>(b-a)*w0*1./b) {p=(b-a)*w0/b-x-w;}
		
		panel.setxOffset(p+(c*(xOff-p))/d);handler.setxOffset(p+(c*(xOff-p))/d);
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	

}

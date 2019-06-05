package clemnico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window extends JFrame {

	//// Attributs////
	
	private JLabel statusBar;
	private int fps;
	protected ImageLoader loader = new ImageLoader();
	private Handlerclass handler;
	private int width=1500;
	private int height=1000;
	
	public static AnimationCreator AC = new AnimationCreator();
	
	public PlayerLocal playerLocal = new PlayerLocal(0, 0, 0, 0, "", 0, 0);
	public PlayerServer playerServer = new PlayerServer(this);
	
	public DiscussionClient discussion = new DiscussionClient(playerLocal, playerServer);
	
	//public Level level;
	

	//// Constructeur////
	public Window(int fps) throws Exception{
				
		this.fps = fps;

		this.setTitle("Ma fenetre");
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		
		
		discussion = new DiscussionClient(playerLocal, playerServer);
		discussion.start();
		
		initPanel();
		
		stepGame();

	}

	//// Methodes////

	private void initPanel() {
		
		/*panel = new Panel();
		panel.setW(width);
		panel.setH(height);
		panel.setBackground(Color.WHITE);*/
		add(playerServer, BorderLayout.CENTER);
		
		statusBar = new JLabel("default");
		add(statusBar, BorderLayout.SOUTH);
		Handlerclass handler = new Handlerclass(statusBar, playerLocal);
		playerServer.addMouseListener(handler);
		playerServer.addMouseMotionListener(handler);
		addKeyListener(handler);
		
		setHandler(handler);
		
		//panel.setListLayer(level.getListLayer());
		
		// Animations
		
		
	}

	
	
	private void stepGame() {
		
		Timer chrono = new Timer();
		int delay = 100;
		int period = 1000 / this.fps;

		chrono.schedule(new TimerTask() {
			long time = 0;
			
			@Override
			public void run() {
				time = time + 1;
				if (!playerServer.isSending) {
					playerServer.repaint();
					playerServer.isSending=true;
				}
				
				
			}
				
			}, delay, period);
	}

	
	
	public void calculCameraOffset(PlayerServer playerServer,PlayerLocal player) {
		Boolean fixe=true;
		Boolean milieu=true;
		
		int w0=this.getWidth();int h0=this.getHeight();
		int xOff=playerServer.getxOffset();int yOff=playerServer.getyOffset();
		int x=player.getX();int y=player.getY();int w=player.getWidth();int h=player.getHeight();
		
		
		// la camera bouge quand le joueur à a/b du bord
		int a=5;
		int b=12;
		// c/d : vitesse de la camera ; c/d=0 : mvt instantané   ;c/d=1 : pas de mvt
		int c=93;
		int d=100;
		int p=yOff;
		if (y+yOff<a*h0*1./b) {p=a*h0/b-y;}
		else if (y+h+yOff>(b-a)*h0*1./b) {p=(b-a)*h0/b-y-h;}
		
		playerServer.setyOffset(p+(c*(yOff-p))/d);handler.setyOffset(p+(c*(yOff-p))/d);
		
		p=xOff;
		if (fixe) {
			if (milieu) {p=w0/2-x;c=0;}
			else if(player.getDirectionX()==1){p=w0/3-x;} 
			else {p=(2*w0)/3-x;}
			}
		
		else if (x+xOff<a*w0*1./b) {p=a*w0/b-x;}
		else if (x+w+xOff>(b-a)*w0*1./b) {p=(b-a)*w0/b-x-w;}
		
		playerServer.setxOffset(p+(c*(xOff-p))/d);handler.setxOffset(p+(c*(xOff-p))/d);
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

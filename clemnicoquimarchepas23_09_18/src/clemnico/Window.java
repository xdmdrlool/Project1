package clemnico;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Window extends JFrame {

	////Attributs////
	public Panel 	panel;
	private JLabel 	statusBar;
	private int 	fps;
	protected ImageLoader loader=new ImageLoader();
	
	//Objets de la fenêtre
	Player player =new Player(100,100,20,20,20,"Player1", 0, 300, false); 
	Portal portal1 =new Portal(-500,-500,100,20);
	Portal portal2 =new Portal(-500,-500,100,20);
	Obstacle obstacle=new Obstacle(0, 400, 600, 100,0);
	Obstacle obstacle2=new Obstacle(600, 400, 200, 100,0);
	Obstacle[] obstacles= {obstacle, obstacle2};
	FC fc=new FC();
	
	////Constructeur////
	public Window(int fps) {
		this.fps=fps;
		
		this.setTitle("Ma fenetre");
		this.setSize(1500, 1000);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
		this.setResizable(true);
		
		portal1.getForm().setColor(Color.BLUE);
		portal2.getForm().setColor(Color.ORANGE);
		
		initPanel();
		stepGame(player);
		

	}
	
	////Methodes////
	
	private void initPanel(){
		panel=new Panel();
		panel.setBackground(Color.WHITE);
		add(panel,BorderLayout.CENTER);
		
		statusBar= new JLabel("default");
		add(statusBar, BorderLayout.SOUTH);
	
		Handlerclass handler =new Handlerclass(panel, statusBar, player, portal1, portal2);
		panel.addMouseListener(handler);
		panel.addMouseMotionListener(handler);
		addKeyListener(handler);
		
		//Animations
		Animation animation= createAnimation(Animations.AnimationPlayerDefault2,player.getWidth(),player.getHeight());
		player.setAnimation(animation);
		Animation animation1= createAnimation(Animations.AnimationPortal1Default,portal1.getWidth(),portal1.getHeight());
		portal1.setAnimation(animation1);
		Animation animation2= createAnimation(Animations.AnimationPortal2Default,portal2.getWidth(),portal2.getHeight());
		portal2.setAnimation(animation2);
		Animation animation3= createAnimation(Animations.AnimationObsatcleDefault,obstacle.getWidth(),obstacle.getHeight());
		obstacle.setAnimation(animation3);
		Animation animation4= createAnimation(Animations.AnimationObsatcleDefault,obstacle2.getWidth(),obstacle2.getHeight());
		obstacle2.setAnimation(animation4);
		ArrayList<Entity> array = new ArrayList<Entity>();
		array.add(obstacle);
		array.add(obstacle2);
		array.add(player);
		array.add(portal1);
		array.add(portal2);
				
		panel.setEntityList(array);
		
	}
	
	private void stepGame(Player player) {
		
		Timer chrono =new Timer();
		int delay=100;
		int period=1000/this.fps;
		
		chrono.schedule(new TimerTask() {
		long time=0;
			@Override
			public void run() {
				time=time+1;
				player.step(period);
				//Gestion portails teleportations
				player.portalInteraction(fc,portal1,portal2);

				//Gestion obstacle
				player.obstacleInteraction(fc, obstacles);
				
				if (time%30 ==0 ) {
					player.getAnimation().update();					
				}
				panel.repaint();
			}
				
			}, delay, period);
	}
	
	public Animation createAnimation(Animations enumAnim, int width,int height) {
		int[][] listeArg =enumAnim.getListeArg();
		int[][] listeArg2 =new int[listeArg.length][listeArg[0].length+2];
		for(int i=0;i<listeArg.length;i++) {
			listeArg2[i][0]=listeArg[i][0];
			listeArg2[i][1]=listeArg[i][1];
			listeArg2[i][2]=listeArg[i][2];
			listeArg2[i][3]=listeArg[i][3];
			listeArg2[i][4]=width;
			listeArg2[i][5]=height;
			listeArg2[i][6]=listeArg[i][4];
		}
		return createAnimationGeneral(enumAnim.getPath(), enumAnim.getDef(), listeArg2);	
	}
	
	
	public Animation createAnimationGeneral(String path,int def, int[][] liste_arg) {
		BufferedImage img = this.loader.loadImage(path);
		SpriteSheet ss=new SpriteSheet(img,def);
//		Sprite sprite2= new Sprite(spriteSheet, 1,0, 64, 64,64,64,45);
		int nbSprite= liste_arg.length;
		Sprite[] spriteTab = new Sprite[nbSprite];
		for (int i=0; i< nbSprite ;i++) {
			Sprite sp= new Sprite(ss, liste_arg[i][0], liste_arg[i][1], liste_arg[i][2], liste_arg[i][3], liste_arg[i][4], liste_arg[i][5],(double) liste_arg[i][6]);
			spriteTab[i]=sp;
			}
		return new Animation(spriteTab);
			
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
}

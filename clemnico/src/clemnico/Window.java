package clemnico;


import java.awt.BorderLayout;
import java.awt.Color;
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
	
	Player player =new Player(100,100,20,20,20,"Player1", 0, 300, false); 
	Portal portal1 =new Portal(-500,-500,100,20);
	Portal portal2 =new Portal(-500,-500,100,20);
	Obstacle obstacle1=new Obstacle(200, 250, 150, 100,45);
	FC fc=new FC();
	
	
	////Constructeur////
	public Window(int fps) {
		this.fps=fps;
		
		this.setTitle("Ma fenetre");
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
		this.setResizable(true);
		
		
		initPanel();
		stepGame(player);
		

	}
	
	////Methodes////
	private void initPanel() {
		panel=new Panel();
		panel.setBackground(Color.WHITE);
		add(panel,BorderLayout.CENTER);
		statusBar= new JLabel("default");
		add(statusBar, BorderLayout.SOUTH);
	
		Handlerclass handler =new Handlerclass(panel, statusBar, player, portal1, portal2);
		panel.addMouseListener(handler);
		panel.addMouseMotionListener(handler);
		addKeyListener(handler);
		
		

		///////////////////////
		///////ANIMATION///////
		///////////////////////
		
	
		Animation animation= createAnimation(Animations.AnimationPlayerDefault,player.getWidth(),player.getHeight());
		player.setAnimation(animation);
		
		
		Animation animation1= createAnimation(Animations.AnimationPortal1Default,portal1.getWidth(),portal1.getHeight());
		portal1.setAnimation(animation1);
		
		
		
		Animation animation2= createAnimation(Animations.AnimationPortal2Default,portal2.getWidth(),portal2.getHeight());
		portal2.setAnimation(animation2);
		
		
		
		Animation animation3= createAnimation(Animations.AnimationObsatcleDefault,obstacle1.getWidth(),obstacle1.getHeight());
		obstacle1.setAnimation(animation3);
		
		
		ArrayList<Entity> array2 = new ArrayList<Entity>();
		array2.add(obstacle1);
		array2.add(player);
		array2.add(portal1);
		array2.add(portal2);
		
		panel.setEntityList(array2);
		
		
		
		
		
		
	}
	
	private void stepGame(Player player) {
		
		Timer chrono =new Timer();
		int delay=100;
		int period=1000/this.fps;
		
		chrono.schedule(new TimerTask() {
		long temps=0;
			@Override
			public void run() {
				
				temps=temps+1;
				player.step(period);
				panel.repaint();
				if (temps%30 ==0 ) {
					player.getAnimation().update();					
				}
				if (temps%1 ==0 ) {
				
					//Obtenir les points A et B des deux portails
					int xAPortal1=(int) fc.Rect2Array(portal1.getForm())[0].x;
					int yAPortal1=(int) fc.Rect2Array(portal1.getForm())[0].y;
					int xBPortal1=(int) fc.Rect2Array(portal1.getForm())[1].x;
					int yBPortal1=(int) fc.Rect2Array(portal1.getForm())[1].y;
					int xDPortal1=(int) fc.Rect2Array(portal1.getForm())[3].x;
					int yDPortal1=(int) fc.Rect2Array(portal1.getForm())[3].y;
					int xAPortal2=(int) fc.Rect2Array(portal2.getForm())[0].x;
					int yAPortal2=(int) fc.Rect2Array(portal2.getForm())[0].y;
					int xBPortal2=(int) fc.Rect2Array(portal2.getForm())[1].x;
					int yBPortal2=(int) fc.Rect2Array(portal2.getForm())[1].y;
					int xDPortal2=(int) fc.Rect2Array(portal2.getForm())[3].x;
					int yDPortal2=(int) fc.Rect2Array(portal2.getForm())[3].y;
					
					int distancePortal=10;
						
					if (player.getHitbox().colision(portal1.getHitbox())) {
						double distancePlayerPortal1=Math.sqrt((player.getX()-xAPortal1)*(player.getX()-xAPortal1)+
															   (player.getY()-yAPortal1)*(player.getY()-yAPortal1));
						
						double distancePlayerPortal2=portal2.getWidth()-distancePlayerPortal1;
						double[] vectorABPortal2= {(xBPortal2-xAPortal2)*1./portal2.getWidth()*1.,(yBPortal2-yAPortal2)*1./portal2.getWidth()*1.};
						double[] vectorDAPortal2= {(xAPortal2-xDPortal2)*1./portal2.getHeight()*1.,(yAPortal2-yDPortal2)*1./portal2.getHeight()*1.};
						
						
						player.moveIn((int) (xAPortal2+vectorABPortal2[0]*distancePlayerPortal2+distancePortal*vectorDAPortal2[0]),
									  (int) (yAPortal2+vectorABPortal2[1]*distancePlayerPortal2+distancePortal*vectorDAPortal2[1]));
					}
					else if (player.getHitbox().colision(portal2.getHitbox())){
						double distancePlayerPortal2=Math.sqrt((player.getX()-xAPortal2)*(player.getX()-xAPortal2)+(player.getY()-yAPortal2)*(player.getY()-yAPortal2));
						
						double distancePlayerPortal1=portal1.getWidth()-distancePlayerPortal2;
						double[] vectorABPortal1= {(xBPortal1-xAPortal1)*1./portal1.getWidth()*1.,(yBPortal1-yAPortal1)*1./portal1.getWidth()*1.};
						double[] vectorDAPortal1= {(xAPortal1-xDPortal1)*1./portal1.getHeight()*1.,(yAPortal1-yDPortal1)*1./portal1.getHeight()*1.};
						
						player.moveIn((int) (xAPortal1+vectorABPortal1[0]*distancePlayerPortal1+distancePortal*vectorDAPortal1[0]),
								      (int) (yAPortal1+vectorABPortal1[1]*distancePlayerPortal1+distancePortal*vectorDAPortal1[1]));
					}
				}
				
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

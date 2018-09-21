package clemnico;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
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
	
	Player player =new Player("Player1", 20, 0.2, 0, 300, false); 
	Portal portal1 =new Portal(-500,-500,20,100);
	Portal portal2 =new Portal(-500,-500,20,100);
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
		
		
		ArrayList<Form> array = new ArrayList<Form>();
		array.add(player.getForm());
		array.add(portal1.getForm());
		array.add(portal2.getForm());
		
		
		array.add(player.getHitbox().getForm());
//		array.add(portal1.getHitbox().getForm());
		
		panel.setFormList(array);
		
	}
	
	private void stepGame(Player player) {
		
		Timer chrono =new Timer();
		int delay=100;
		int period=1000/this.fps;
		
		chrono.schedule(new TimerTask() {
		int temps=0;
			@Override
			public void run() {
				temps=temps+1;
				player.step(period);
				
				panel.repaint();
				if (temps%1 ==0) {
//				System.out.println(portal1.getHitbox().getForm().getAngle());
				
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

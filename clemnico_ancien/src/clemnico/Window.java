package clemnico;


import java.awt.BorderLayout;
import java.awt.Color;
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
				if (temps%10 ==0) {
//				System.out.println(portal1.getHitbox().getForm().getAngle());
				if (player.getHitbox().colision(portal1.getHitbox())) {
					System.out.println("ca touche");
				}}
				
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

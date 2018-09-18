package clemnico;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {

	////Attributs////
	public Panel 	panel;
	private JLabel 	statusBar;
	private int 	fps;
	Player player =new Player("Player1", 20, 0.2, 0, 300, false); 
	Portal portal =new Portal(50,50,50,50,0);
	
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
		stepGame(player, portal);
		

	}
	
	////Methodes////
	private void initPanel(){
		panel=new Panel();
		panel.setBackground(Color.WHITE);
		add(panel,BorderLayout.CENTER);
		
		statusBar= new JLabel("default");
		add(statusBar, BorderLayout.SOUTH);
	
		Handlerclass handler =new Handlerclass(panel, statusBar, player, portal);
		panel.addMouseListener(handler);
		panel.addMouseMotionListener(handler);
		addKeyListener(handler);
		
		
		ArrayList<Form> array = new ArrayList<Form>();
		array.add(player.getForm());
		array.add(portal.getForm());
		
		panel.setFormList(array);
		
	}
	
	private void stepGame(Player player, Portal portal) {
		
		Timer chrono =new Timer();
		int delay=100;
		int period=1000/this.fps;
		
		chrono.schedule(new TimerTask() {
			
			@Override
			public void run() {
				player.step(period);
				portal.step();
				
				panel.repaint();
				if (player.getHitbox().colision(portal.getHitbox())) {
					System.out.println("Ca touche !");
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

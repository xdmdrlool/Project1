package fr.clement.InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre extends JFrame {



	private Panneau	moussepanel1;
	private JLabel statusbar;
	private int fps;
	
	public Fenetre(int fps) {
		this.fps=fps;
		
		this.setTitle("Ma fenetre");
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
		this.setResizable(true);
		
		
		
		moussepanel1=new Panneau();
		
		moussepanel1.setBackground(Color.WHITE);
		add(moussepanel1,BorderLayout.CENTER);
		
		statusbar= new JLabel("default");
		add(statusbar, BorderLayout.SOUTH);
		
		Player player =new Player("Player1", 20, 0.2, 0, 300, false); 
		
		Handlerclass handler =new Handlerclass(player);
		
		moussepanel1.addMouseListener(handler);
		moussepanel1.addMouseMotionListener(handler);
		addKeyListener(handler);
		
		
		
		
		
		Timer chrono =new Timer();
		int delay=100;
		int period=1000/this.fps;
		
		chrono.schedule(new TimerTask() {
		
		@Override
		public void run() {
			int dx= player.getSpeed()*period/1000;
			if (player.isMove()) {
				switch (player.getDirection()) {
			
		case 0 :
			moussepanel1.setY0(moussepanel1.getY0()-dx);
			break;
		case 1 :
			moussepanel1.setX0(moussepanel1.getX0()-dx);
			break;
		case 2 :
			moussepanel1.setX0(moussepanel1.getX0()+dx);
			break;
		case 3 :
			moussepanel1.setY0(moussepanel1.getY0()+dx);
			break;
			
			}
				moussepanel1.repaint();
			}
		}
		}, delay, period);
		

	}
	
	
	
	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	
	
	//Classe qui gere les clics et les appuis de touche
	
	private class Handlerclass implements MouseListener, MouseMotionListener, KeyListener {
		
		private Player player;

		public  Handlerclass(Player player) {
			this.player=player;
		}
		
		
		public void mouseClicked(MouseEvent event) {
			 
			int x=event.getX();
			int y=event.getY();
			int w =moussepanel1.getWidth();
			int h =moussepanel1.getHeight();
			
			statusbar.setText("Click en "+ x +" "+ y);
			
		}

		@Override
		public void mouseDragged(MouseEvent event) {
			int x=event.getX();
			int y=event.getY();
			statusbar.setText(x+" "+y);
			moussepanel1.setX0(x);
			moussepanel1.setY0(y);
			moussepanel1.repaint();
			
			
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			statusbar.setText("ca bouge !" );;
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {	
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			moussepanel1.setBackground(Color.WHITE);

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			statusbar.setText(" "+arg0.getX());;
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key =e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_UP :
				player.setDirection(0);
				player.setMove(true);
				break;
			case KeyEvent.VK_LEFT :
				player.setDirection(1);
				player.setMove(true);
				break;
			case KeyEvent.VK_RIGHT :
				player.setDirection(2);
				player.setMove(true);
				break;
			case KeyEvent.VK_DOWN :
				player.setDirection(3);
				player.setMove(true);
				break;
			default :
				statusbar.setText("rien");
				break;
				
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			player.setMove(false);
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
}

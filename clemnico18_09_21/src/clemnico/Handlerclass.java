package clemnico;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;


//Classe qui gère les évènements souris et clavier

public class Handlerclass implements MouseListener, MouseMotionListener, KeyListener {
	
	////Attributs////
	private Panel 	panel;
	private JLabel 	statusBar;
	

	private Player 	player;
	private Portal portal1;
	private Portal portal2;
	////Constructeur////
	public  Handlerclass(Panel panel, JLabel statusBar, Player player, Portal portal1, Portal portal2) {
		this.panel=panel;
		this.statusBar=statusBar;
		this.player=player;
		this.portal1=portal1;
		this.portal2=portal2;
		
	}
	
	
	////////////////////////////////
	/////// EVENEMENT SOURIS////////
	////////////////////////////////
	
	public void mouseClicked(MouseEvent event) {
		 
		int xClic=event.getX();
		int yClic=event.getY();
		
		statusBar.setText("Click en "+ xClic +" "+ yClic);
		
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		int xClic=event.getX();
		int yClic=event.getY();
		
		panel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		statusBar.setText("ca bouge !" );;
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		panel.setBackground(Color.WHITE);

	}

	@Override
	public void mousePressed(MouseEvent event) {
		statusBar.setText(" "+event.getX());;
		
		int xClic=event.getX();
		int yClic=event.getY();
		
		//Commandes portail
		if (event.getButton()==MouseEvent.BUTTON1) {
			portal1.setX(xClic-portal1.getWidth()/2);
			portal1.setY(yClic-portal1.getHeight()/2);
			portal1.setRotation(player.getX(), player.getY(),xClic,yClic);
		}
		else if (event.getButton()==MouseEvent.BUTTON3) {
			portal2.setX(xClic-portal2.getWidth()/2);
			portal2.setY(yClic-portal2.getHeight()/2);
			portal2.setRotation(player.getX(), player.getY(),xClic,yClic);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	////////////////////////////////
	/////// EVENEMENT CLAVIER///////
	////////////////////////////////
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int key =e.getKeyCode();
		player.actionKeyboard(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (player.getKeyPressed()==e.getKeyCode()) {
			player.setMove(false);
			player.setKeyPressed(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
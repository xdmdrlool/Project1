package fr.nicolas.snakex;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;


//Classe qui g�re les �v�nements souris et clavier

public class Handlerclass implements MouseListener, MouseMotionListener, KeyListener {
	
	////Attributs////
	private Player 	player;
	private Panel 	panel;
	private JLabel 	statusBar;
	
	////Constructeur////
	public  Handlerclass(Panel panel, JLabel statusBar, Player player) {
		this.panel=panel;
		this.statusBar=statusBar;
		this.player=player;
		
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
		
		statusBar.setText(xClic+" "+yClic);
		panel.setX0(xClic);
		panel.setY0(yClic);
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
	public void mousePressed(MouseEvent arg0) {
		statusBar.setText(" "+arg0.getX());;
		
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
		player.setMove(false);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
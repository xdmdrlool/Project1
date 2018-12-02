package clemnico;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;


//Classe qui gère les évènements souris et clavier

public class Handlerclass implements MouseListener, MouseMotionListener, KeyListener {
	
	////Attributs////
	private JLabel 	statusBar;
	
	private int xOffset=0;
	private int yOffset=0;

	private PlayerLocal player;
	
	////Constructeur////
	public  Handlerclass( JLabel statusBar, PlayerLocal player) {
		this.statusBar=statusBar;
		this.player=player;		
	}
	
	
	////////////////////////////////
	/////// EVENEMENT SOURIS////////
	////////////////////////////////
	
	public void mouseClicked(MouseEvent event) {
		
		int xClic=event.getX();
		int yClic=event.getY();
		
		statusBar.setText("Click en "+ (xClic-xOffset) +" "+ (yClic-yOffset));
		
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		player.setxMouse(event.getX()-xOffset);
		player.setyMouse(event.getY()-yOffset);
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		player.setxMouse(event.getX()-xOffset);
		player.setyMouse(event.getY()-yOffset);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		

	}

	@Override
	public void mousePressed(MouseEvent event) {
		statusBar.setText(" "+event.getX());;
		
		int xClic=event.getX()-xOffset;
		int yClic=event.getY()-yOffset;
		
		if (event.getButton()==MouseEvent.BUTTON1) {
			player.actionMousePressed(1,xClic,yClic);
		}
		else if (event.getButton()==MouseEvent.BUTTON3) {
			player.actionMousePressed(3,xClic,yClic);
		}
		else {
			player.actionMousePressed(2,xClic,yClic);
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		int xClic=event.getX()-xOffset;
		int yClic=event.getY()-yOffset;
		
		if (event.getButton()==MouseEvent.BUTTON1) {
			player.actionMouseReleased(1,xClic,yClic);
		}
		else if (event.getButton()==MouseEvent.BUTTON3) {
			player.actionMouseReleased(3,xClic,yClic);
		}
		else {
			player.actionMouseReleased(2,xClic,yClic);
		}
	}

	////////////////////////////////
	/////// EVENEMENT CLAVIER///////
	////////////////////////////////
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int key =e.getKeyCode();
		player.actionKeyboardPressed(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key =e.getKeyCode();
		player.actionKeyboardReleased(key);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	public int getxOffset() {
		return xOffset;
	}


	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}


	public int getyOffset() {
		return yOffset;
	}


	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}
}
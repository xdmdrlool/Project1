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
	private Panel 	panel;
	private JLabel 	statusBar;
	
	private int xOffset=0;
	private int yOffset=0;

	private Player 	player;
	private Portal portal1;
	private Portal portal2;
	private ArrayList<Obstacle> obstacles;
	private Projectile projectile;
	
	////Constructeur////
	public  Handlerclass(Panel panel, JLabel statusBar, Player player, Portal portal1, Portal portal2, ArrayList<Obstacle> obstacles) {
		this.panel=panel;
		this.statusBar=statusBar;
		this.player=player;
		this.portal1=portal1;
		this.portal2=portal2;
		this.obstacles=obstacles;
		
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
	public void mouseMoved(MouseEvent event) {
		player.setxMouse(event.getX()-xOffset);
		player.setyMouse(event.getY()-yOffset);
		
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
		
		int xClic=event.getX()-xOffset;
		int yClic=event.getY()-yOffset;
		
		//Commandes portail
		if (event.getButton()==MouseEvent.BUTTON1) {
			portal1.movePortal(obstacles,player,xClic,yClic);
		}
		else if (event.getButton()==MouseEvent.BUTTON3) {
			portal2.movePortal(obstacles,player,xClic,yClic);
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

	public Projectile getProjectile() {
		return projectile;
	}


	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
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
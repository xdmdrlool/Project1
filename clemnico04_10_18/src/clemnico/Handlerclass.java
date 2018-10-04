package clemnico;

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
	private Panel 	panel;
	private JLabel 	statusBar;
	

	private Player 	player;
	private Portal portal1;
	private Portal portal2;
	private Obstacle[] obstacles;
	////Constructeur////
	public  Handlerclass(Panel panel, JLabel statusBar, Player player, Portal portal1, Portal portal2, Obstacle[] obstacles) {
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
		player.actionKeyboard(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Si la touche relach�e est la derni�re appuy�e, on consid�re qu'aucune touche n'est appuy�e
		if (player.getKeyPressed()==e.getKeyCode()) {
			player.setKeyPressed(0);
			//Si c'est une touche gauche/droite, on arr�te le mouvement lat�ral cr�� par le joueur,
			//et l'inertie si il touche le sol
			if (e.getKeyCode()==KeyEvent.VK_Q || e.getKeyCode()==KeyEvent.VK_D) {
				player.setMoveX(false);
				if (!player.isInTheAir()) {
					player.setVx(0);
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
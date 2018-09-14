package fr.clement.InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Mouse extends JFrame {
	private JPanel mousepanel;
	private JLabel statusbar;
	
	public Mouse() {
		super("interface souris");
		
		mousepanel = new JPanel();
		mousepanel.setBackground(Color.WHITE);
		add(mousepanel, BorderLayout.CENTER);
		
		statusbar=new JLabel("default");
		add(statusbar,BorderLayout.SOUTH);
		
		HandlerClass handler = new HandlerClass();
		mousepanel.addMouseListener(handler);
		mousepanel.addMouseMotionListener(handler);
	}
	
	private class HandlerClass implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			statusbar.setText(String.format("Clicked at %d, %d", event.getX(), event.getY()));
		}
		public void mousePressed(MouseEvent event) {
			statusbar.setText("you pressed down the mouse");
		}
		public void mouseReleased(MouseEvent event) {
			statusbar.setText("you released the button");
		}
		public void mouseEntered(MouseEvent event) {
			statusbar.setText("You enetered the area");
			mousepanel.setBackground(Color.RED);
		}
		public void mouseExited(MouseEvent event) {
			statusbar.setText("the mouse has left the window");
			mousepanel.setBackground(Color.WHITE);
		}
		
		//These are mouse motion event
		@Override
		public void mouseDragged(MouseEvent event) {
			statusbar.setText("you are dragging the mouse");
		}
		@Override
		public void mouseMoved(MouseEvent event) {
			statusbar.setText("you moved the mouse");
		}
		
		
	}
	
}

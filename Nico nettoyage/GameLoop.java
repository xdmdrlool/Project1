package fr.nicolas.snakex;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop{
	
	////Attributs////
	private int 	fps=60;
	private Window 	window=new Window(fps);
	private Player 	player =new Player("Player1", 20, 0.2, 0, 300, false);
	
	
	public GameLoop(int fps) {
		Timer chrono =new Timer();
		int delay=100;
		int period=1000/this.fps;
		
		chrono.schedule(new TimerTask() {
			
			@Override
			public void run() {
				int dxPlayer= player.getSpeed()*period/1000;
				if (player.isMove()) {
					switch (player.getDirection()) {
				
			case 0 :
				window.panel.setY0(window.panel.getY0()-dxPlayer);
				break;
			case 1 :
				window.panel.setX0(window.panel.getX0()-dxPlayer);
				break;
			case 2 :
				window.panel.setX0(window.panel.getX0()+dxPlayer);
				break;
			case 3 :
				window.panel.setY0(window.panel.getY0()+dxPlayer);
				break;
				
				}
					window.panel.repaint();
				}
			}
			}, delay, period);
		
	}
}

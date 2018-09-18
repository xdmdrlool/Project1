package clemnico;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameLoop{
	
	////Attributs////
	private int 	fps=60;
	private Window 	window=new Window(fps);
	private Player 	player =new Player("Player1", 20, 0.2, 0, 300, false);
	
	
	public GameLoop(int fps) {
		
//		ArrayList<Form> array = new ArrayList<Form>();
//		array.add(player.getForm());
//		array.add(window.portal.getForm());
//		window.panel.setFormList(array);
		
		
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
				player.setyPlayer(player.getyPlayer()-dxPlayer);

				break;
			case 1 :
				player.setxPlayer(player.getxPlayer()-dxPlayer);
				break;
			case 2 :
				player.setxPlayer(player.getxPlayer()+dxPlayer);
				break;
			case 3 :
				player.setyPlayer(player.getyPlayer()+dxPlayer);
				break;
				
				}
					window.panel.repaint();
				}
			}
			}, delay, period);
		
	}
}

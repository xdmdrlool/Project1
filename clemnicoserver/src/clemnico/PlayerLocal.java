package clemnico;

public class PlayerLocal extends Player{
	
	//Message à envoyer aux autres joueurs du réseau
	private String messageWeb;

	public PlayerLocal(int x, int y, int width, int height, String name, int direction, int vxOnGround) {
		super(x, y, width, height, name, direction, vxOnGround);
	}
	
	public void actionKeyboardPressed(int key) {
		super.actionKeyboardPressed(key);
		setMessageWeb("p"+key);
	}
	
	public void actionKeyboardReleased(int key) {
		super.actionKeyboardReleased(key);
		setMessageWeb("r"+key);
	}
	
	
	
	
	

	public String getMessageWeb() {
		return messageWeb;
	}

	public void setMessageWeb(String messageWeb) {
		this.messageWeb = messageWeb;
	}
	

}

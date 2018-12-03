package clemnico;

public class ObstacleFixGateNoPlayer extends ObstacleFix {

	public ObstacleFixGateNoPlayer(int x, int y, int width, int height, String name, double angle) {
		super(x, y, width, height, name, angle);
		// TODO Auto-generated constructor stub
	}
	
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationObstacleFixGateNoPlayer,width,height));		
		setCurrentAnimation(NameAnimation.DEFAULT);
	}

}

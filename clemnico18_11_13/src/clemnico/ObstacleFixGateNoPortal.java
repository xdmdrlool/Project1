package clemnico;

public class ObstacleFixGateNoPortal extends ObstacleFix {

	public ObstacleFixGateNoPortal(int x, int y, int width, int height, String name, double angle) {
		super(x, y, width, height, name, angle);
		// TODO Auto-generated constructor stub
	}
	
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));		
		setCurrentAnimation(NameAnimation.DEFAULT);
	}


}

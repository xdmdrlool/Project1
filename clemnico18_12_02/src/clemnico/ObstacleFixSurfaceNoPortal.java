package clemnico;

public class ObstacleFixSurfaceNoPortal extends ObstacleFix {

	public ObstacleFixSurfaceNoPortal(int x, int y, int width, int height, String name, double angle) {
		super(x, y, width, height, name, angle);
		
	}
	
	
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));		
		setCurrentAnimation(NameAnimation.DEFAULT);
	}

}

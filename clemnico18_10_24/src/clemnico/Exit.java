package clemnico;

public class Exit extends Entity {

	public Exit(int x, int y, int width, int height,String name) {
		super(name, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		setCurrentAnimation(name);
	}
	
	
	@Override
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));		
		setCurrentAnimation(NameAnimation.DEFAULT);
	}

}

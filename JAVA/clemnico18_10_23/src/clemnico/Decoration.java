package clemnico;

public class Decoration extends Entity{

	public Decoration(String name, int x, int y, int width, int height) {
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
		
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationLandScapeDefault,width,height));
		
	}

}

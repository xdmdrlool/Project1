package clemnico;

public class Enter extends Entity {

	public Enter(int x, int y, int width, int height,String name) {
		super(name, x, y, width, height);
		// TODO Auto-generated constructor stub
	}


	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		setCurrentAnimation(name);
	}
	
	
	
	

	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationPlayerDefault,width,height));		
	}
}

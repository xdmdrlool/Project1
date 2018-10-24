package clemnico;



public abstract class Obstacle extends Entity {
	
	private double angle;

	
	public Obstacle(int x, int y, int width,int height,String name,double angle) {
		super(name, x,y, width, height);

		setAngle(angle);
	}

	
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void chooseAnimation() {
		NameAnimation name=NameAnimation.DEFAULT;
		setCurrentAnimation(name);
	}

	
	
	
	@Override
	public void useDefaultAnimations() {
		addAnimation(NameAnimation.DEFAULT,ACreator.createAnimation(Animations.AnimationObsatcleDefault2,width,height));		
		setCurrentAnimation(NameAnimation.DEFAULT);
	}
	
	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	

	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
		this.form.setAngle(angle);
		this.hitbox.setAngle(angle);
		if (this.currentAnimation != null) {this.currentAnimation.setAngle(angle);}		
	}



	public void setCurrentAnimation(NameAnimation name) {
		Animation anime = ListAnimation.get(name);
		if (this.currentAnimation!=anime) {
			this.currentAnimation=anime;
			this.currentAnimation.reset();}
		this.currentAnimation.setAngle(this.getAngle());
	}
		

	

}



package clemnico;

public class Animation {
	private Sprite[] listeSprite;
	private int[] listeTime;
	private int numeroFrame =0 ;
	private int timeinframe =0;
	private int nbFrame;
	private double angle;

	public Animation(Sprite[] listeSprite,int[] listeTime ) {
		setListeSprite(listeSprite);
		setListeTime(listeTime);
		setNbFrame(listeSprite.length);
	}
	
	
	public void update() {
		this.timeinframe++;
		if (timeinframe==listeTime[numeroFrame]) {
		this.numeroFrame=(this.numeroFrame+1)%this.nbFrame;
		this.timeinframe=0;}
		
	}
	
	public void reset() {
		this.numeroFrame=0;
	}
	
	
	public Sprite getSprite() {
		return this.listeSprite[this.numeroFrame];
	}
	
////////////////////////////////
/////// GETTER AND SETTER //////
////////////////////////////////


	public Sprite[] getListeSprite() {
		return listeSprite;
	}
	public void setListeSprite(Sprite[] listeSprite) {
		this.listeSprite = listeSprite;
	}

	public int getNumeroFrame() {
		return numeroFrame;
	}


	public void setNumeroFrame(int numeroFrame) {
		this.numeroFrame = numeroFrame;
	}
	
	public int getNbFrame() {
		return nbFrame;
	}
	public void setNbFrame(int nbFrame) {
		this.nbFrame = nbFrame;
	}


	public double getAngle() {
		return angle;
	}


	public void setAngle(double angle) {
		this.angle = angle;
		for (Sprite sp : listeSprite) {
			sp.setAngle(angle);
		}
	}


	public int getTimeinframe() {
		return timeinframe;
	}


	public void setTimeinframe(int timeinframe) {
		this.timeinframe = timeinframe;
	}
	public int[] getListeTime() {
		return listeTime;
	}


	public void setListeTime(int[] listeTime) {
		this.listeTime = listeTime;
	}

}

package clemnico;

public class Animation {
	private Sprite[] listeSprite;
	private int numeroFrame =0 ;
	private int nbFrame;
	private double angle;

	public Animation(Sprite[] listeSprite ) {
		setListeSprite(listeSprite);
		setNbFrame(listeSprite.length);
	}
	
	
	public void update() {
		this.numeroFrame=(this.numeroFrame+1)%this.nbFrame;
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


}

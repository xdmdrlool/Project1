package clemnico;

public class ObstacleMoving extends Obstacle  {
	
	
	private int xA;
	private int yA;
	private int xB;
	private int yB;
	private int frame=0;
	private int period;
	


	public ObstacleMoving(String name,int xA,int yA,int xB,int yB, int width, int height,int period) {
		super(xA, yA, width, height,name,0);
		setxA(xA);
		setxB(xB);
		setyA(yA);
		setyB(yB);
		setPeriod(period);
	}
	
	public void update() {
		setxBefore(x);
		setyBefore(y);
		this.frame=(frame+1)%period;
		if (frame<period/2) {
			double ux=2/(1.0*period);
			double uy=2/(1.0*period);
			setX((int) (xA+frame*ux*(xB-xA)));
			setY((int) (yA+frame*uy*(yB-yA)));
		}
		else {
			double ux=2/(1.0*period);
			double uy=2/(1.0*period);
			setX((int) (xB+(frame-period/2)*ux*(xA-xB)));
			setY((int) (yB+(frame-period/2)*uy*(yA-yB)));
			
		}
	}
	


	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////


	public int getxA() {
		return xA;
	}
	
	public int getyA() {
		return yA;
	}

	public int getxB() {
		return xB;
	}

	public int getyB() {
		return yB;
	}

	public int getPeriod() {
		return period;
	}

	public void setxA(int xA) {
		this.xA = xA;
	}

	public void setyA(int yA) {
		this.yA = yA;
	}

	public void setxB(int xB) {
		this.xB = xB;
	}

	public void setyB(int yB) {
		this.yB = yB;
	}


	public void setPeriod(int period) {
		this.period = period;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	
	
	
	
	

}

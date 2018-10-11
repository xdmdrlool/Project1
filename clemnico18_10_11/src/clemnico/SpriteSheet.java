package clemnico;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	private int def;
	
	public SpriteSheet(BufferedImage image, int def) {
		setDef(def);
		setImage(image);

	}
	
	
	public BufferedImage grabImage(int col, int row, int width, int height) {
		BufferedImage img=image.getSubimage(col*def, row*def, width, height);
		return img;
	}


	
////////////////////////////////
/////// GETTER AND SETTER //////
////////////////////////////////
	
	public int getDef() {
		return def;
	}


	public void setDef(int def) {
		this.def = def;
	}


	public BufferedImage getImage() {
		return image;
	}


	public void setImage(BufferedImage image) {
		this.image = image;
	}


}

package clemnico;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

	public BufferedImage loadImage(String path) {

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("resources/" + path));
		} catch (IOException e) {
			System.out.println("Ne trouve pas l'image : " + path);
		}
		
		return image;
	}

}

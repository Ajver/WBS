package Other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {

	public BufferedImage swordIcon = null;
	public BufferedImage bootIcon = null;
	public BufferedImage bigBootIcon = null;
	public BufferedImage actionBg = null;
	
	public Textures() {
		try {
			swordIcon = ImageIO.read(new File("res/Graphics/Icons/SwordIcon.png"));
			bootIcon = ImageIO.read(new File("res/Graphics/Icons/BootIcon.png"));
			bigBootIcon = ImageIO.read(new File("res/Graphics/Icons/BigBootIcon.png"));
			actionBg = ImageIO.read(new File("res/Graphics/Icons/ActionBg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

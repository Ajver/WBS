package Other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {

	public BufferedImage swordman = null;
	
	public BufferedImage swordIcon = null;
	public BufferedImage bootIcon = null;
	public BufferedImage bigBootIcon = null;
	public BufferedImage actionBg = null;
	
	/*	[Type] 	[Action]
	 * 	
	 * 	Types = [ blue, red ]
	 * 	Actions = [ isClickable, hover ]
	 */
	public BufferedImage[][] mapTile = new BufferedImage[2][2];
	
	public Textures() {
		try {
			swordman = ImageIO.read(new File("res/Graphics/Icons/Swordman.png"));
			
			swordIcon = ImageIO.read(new File("res/Graphics/Icons/SwordIcon.png"));
			bootIcon = ImageIO.read(new File("res/Graphics/Icons/BootIcon.png"));
			bigBootIcon = ImageIO.read(new File("res/Graphics/Icons/BigBootIcon.png"));
			actionBg = ImageIO.read(new File("res/Graphics/Icons/ActionBg.png"));
			
			mapTile[0][0] = ImageIO.read(new File("res/Graphics/Icons/mapClick.png"));
			mapTile[0][1] = ImageIO.read(new File("res/Graphics/Icons/mapHover.png"));
			
			mapTile[1][0] = ImageIO.read(new File("res/Graphics/Icons/enemyClick.png"));
			mapTile[1][1] = ImageIO.read(new File("res/Graphics/Icons/enemyHover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

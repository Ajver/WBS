package Other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {

	public BufferedImage[] swordman = new BufferedImage[6];
	
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
			BufferedImage swordmanSS = ImageIO.read(new File("res/Graphics/Icons/SS/Swordman.png"));
			for(int i=0; i<6; i++) { swordman[i] = getFromSS(swordmanSS, i, 0, 32, 32); }
			
			
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
		
	private BufferedImage getFromSS(BufferedImage ss, int x, int y, int w, int h) {
		return ss.getSubimage(x*w, y*h, w, h);
	}
}

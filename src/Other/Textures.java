package Other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {

	public BufferedImage[] swordman = new BufferedImage[6];
	public BufferedImage[] attack = new BufferedImage[10];

	public BufferedImage actionBg;

	public BufferedImage[] swordIcon = new BufferedImage[2];
	public BufferedImage[] runattackIcon = new BufferedImage[2];
	public BufferedImage[] bootIcon = new BufferedImage[2];
	public BufferedImage[] bigBootIcon = new BufferedImage[2];

	/*	[Type] 	[Action]
	 * 	
	 * 	Types = [ blue, red ]
	 * 	Actions = [ isClickable, hover ]
	 */
	public BufferedImage[][] mapTile = new BufferedImage[2][2];
	
	public Textures() {
		try {
			BufferedImage swordmanSS = ImageIO.read(new File("res/Graphics/SS/Swordman.png"));
			for(int i=0; i<6; i++) { swordman[i] = getFromSS(swordmanSS, i, 0, 32, 32); }

            BufferedImage attackSS = ImageIO.read(new File("res/Graphics/SS/Attack.png"));
            for(int i=0; i<10; i++) { attack[i] = getFromSS(attackSS, i, 0, 32, 48); }

			actionBg = ImageIO.read(new File("res/Graphics/Icons/ActionBg.png"));

            swordIcon[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/SwordIcon.png")), 0);
            swordIcon[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/SwordIcon.png")), 1);

			runattackIcon[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/RunattackIcon.png")), 0);
			runattackIcon[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/RunattackIcon.png")), 1);

			bootIcon[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BootIcon.png")), 0);
			bootIcon[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BootIcon.png")), 1);

			bigBootIcon[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BigBootIcon.png")), 0);
			bigBootIcon[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BigBootIcon.png")), 1);

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

	private BufferedImage getHalfFromSS(BufferedImage ss, int nr) {
		return ss.getSubimage(0, ss.getHeight() / 2 * nr, ss.getWidth(), ss.getHeight() / 2);
	}
}

package Other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {

	// Animations
	public BufferedImage[] swordman = new BufferedImage[6];
	public BufferedImage[] attack = new BufferedImage[10];


	// Action buttons
	public BufferedImage actionBg;

	public BufferedImage[] swordAB = new BufferedImage[2];
	public BufferedImage[] runattackAB = new BufferedImage[2];
	public BufferedImage[] bootAB = new BufferedImage[2];
	public BufferedImage[] bigBootAB = new BufferedImage[2];
	public BufferedImage[] timerAB = new BufferedImage[2];


	// Items
	public BufferedImage sword;

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

            swordAB[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/SwordIcon.png")), 0);
            swordAB[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/SwordIcon.png")), 1);

			runattackAB[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/RunattackIcon.png")), 0);
			runattackAB[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/RunattackIcon.png")), 1);

			bootAB[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BootIcon.png")), 0);
			bootAB[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BootIcon.png")), 1);

			bigBootAB[0] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BigBootIcon.png")), 0);
			bigBootAB[1] = getHalfFromSS(ImageIO.read(new File("res/Graphics/Icons/BigBootIcon.png")), 1);

			timerAB[0] = timerAB[0] = ImageIO.read(new File("res/Graphics/Icons/TimerIcon.png"));

			mapTile[0][0] = ImageIO.read(new File("res/Graphics/Icons/mapClick.png"));
			mapTile[0][1] = ImageIO.read(new File("res/Graphics/Icons/mapHover.png"));
			
			mapTile[1][0] = ImageIO.read(new File("res/Graphics/Icons/enemyClick.png"));
			mapTile[1][1] = ImageIO.read(new File("res/Graphics/Icons/enemyHover.png"));

			BufferedImage items = ImageIO.read(new File("res/Graphics/SS/Items.png"));
			sword = getFromSS(items, 0, 0, 32, 32);
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

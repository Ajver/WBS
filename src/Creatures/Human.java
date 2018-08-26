package Creatures;

import java.awt.Graphics;

import Character.Dice;
import MainFiles.MainClass;
import Other.Handler;

public class Human extends Creature {
	
	public Human(int mx, int my, Handler handler) {
		super(mx, my, handler);
				
		for(int at=0; at<8; at++) {
			att.begin[0][at] = 20 + Dice.roll2d10();
			att.current[0][at] = 20 + Dice.roll2d10();
		}
		
		att.begin[1][0] = 11;
		att.begin[1][1] = 1;
		att.begin[1][2] = (int)(att.begin[0][2] / 10);
		att.begin[1][3] = (int)(att.begin[0][3] / 10);
		att.begin[1][4] = (int)(att.begin[0][4] / 10);
		
		att.current[1][0] = 11;
		att.current[1][1] = 1;
		att.current[1][2] = (int)(att.current[0][2] / 10);
		att.current[1][3] = (int)(att.current[0][3] / 10);
		att.current[1][4] = (int)(att.current[0][4] / 10);
		
		this.hp = att.current[1][0];
		
		this.img = MainClass.tex.swordman;
	}

	public void render(Graphics g) {
		g.drawImage(img, (int)(x), (int)(y), null);
	}
}

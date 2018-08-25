package Creatures;

import java.awt.Color;
import java.awt.Graphics;

import Character.Dice;
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
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 255));
		g.fillOval((int)(x + Handler.cellW * 0.1f), (int)(y + Handler.cellH * 0.1f), (int)(Handler.cellW * 0.8f), (int)(Handler.cellH * 0.8f));
	}
}

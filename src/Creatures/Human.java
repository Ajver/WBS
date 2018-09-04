package Creatures;

import Character.Dice;
import MainFiles.MainClass;
import Other.Handler;

public class Human extends Creature {

	public Human(int mx, int my, Handler handler) {
		super(mx, my, handler);
				
		for(int at=0; at<8; at++) {
			att.begin[0][at] = att.current[0][at] = 20 + Dice.roll2d10();
		}
		
		att.begin[1][0] = 1;
		att.begin[1][1] = 11;
		att.begin[1][2] = att.begin[0][2] / 10;
		att.begin[1][3] = att.begin[0][3] / 10;
		att.begin[1][4] = att.begin[0][4] / 10;
		att.begin[1][5] = 0;
		att.begin[1][6] = 0;
		att.begin[1][7] = 3;
		
		att.current[1][0] = 1;
		att.current[1][1] = 11;
		att.current[1][2] = att.current[0][2] / 10;
		att.current[1][3] = att.current[0][3] / 10;
		att.current[1][4] = att.current[0][4] / 10;
		att.current[1][5] = 0;
		att.current[1][6] = 0;
		att.current[1][7] = 3;

		this.hp = att.getZyw();

		this.name = "Cz³owiek";

		this.moveAnimation = new Animation(0.4f, MainClass.tex.swordman);
		this.attackAnimation = new Animation(0.6f, MainClass.tex.attack);
	}

	
}

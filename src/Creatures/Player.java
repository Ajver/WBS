package Creatures;

import java.awt.Color;
import java.awt.Graphics;

import Other.Handler;

public class Player extends Creature {

	public Player(int mx, int my, Handler handler) {
		super(mx, my, handler);
	}

	public void update(float et) {
		if(isMoving) { 
			move(et); 
			x += velX * et;
			y += velY * et;
			
			mx = (int)((x + Handler.cellW/2.0f) / Handler.cellW);
			my = (int)((y + Handler.cellH/2.0f) / Handler.cellH);
		}else {
			setMXY(mx, my);
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 255));
		g.fillOval((int)(x + Handler.cellW * 0.1f), (int)(y + Handler.cellH * 0.1f), (int)(Handler.cellW * 0.8f), (int)(Handler.cellH * 0.8f));
	}

}

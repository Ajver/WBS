package Map;

import java.awt.Color;
import java.awt.Graphics;

import Map.Map.ID;
import Other.Handler;

public class Rock extends MapObject {

	public Rock(int mx, int my) {
		super(mx, my);
		this.id = ID.rock;
		this.mayByPath = false;
	}
	
	public void update(float et) {
		
	}

	public void render(Graphics g) {
		if(isClickable) {
			g.setColor(Color.blue);
		}else {
			g.setColor(Color.gray);
		}
		
		g.fillOval((int)x, (int)y, (int)Handler.cellW, (int)Handler.cellH);
	}

}

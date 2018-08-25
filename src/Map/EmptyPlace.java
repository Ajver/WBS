package Map;

import java.awt.Color;
import java.awt.Graphics;

import Map.Map.ID;
import Other.Handler;

public class EmptyPlace extends MapObject {

	public EmptyPlace(int mx, int my) {
		super(mx, my);
		
		this.id = ID.n;
	}

	public void update(float et) {
		
	}

	public void render(Graphics g) {
		if(isClickable && hover) {
			g.setColor(col);
		}else {
			g.setColor(new Color(10, 150, 50));
		}
		g.fillRect((int)(x), (int)(y), (int)Handler.cellW, (int)Handler.cellH);
		
		g.setColor(new Color(10, 135, 45));
		g.drawRect((int)(x), (int)(y), (int)Handler.cellW, (int)Handler.cellH);
		
		if(isClickable) {
			g.setColor(col);
			g.fillOval((int)x, (int)y, (int)Handler.cellW, (int)Handler.cellH);
		}
	}

}

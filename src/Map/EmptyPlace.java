package Map;

import java.awt.Color;
import java.awt.Graphics;

import MainFiles.MainClass;
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
		g.setColor(new Color(20, 150, 50));
		g.fillRect((int)(x), (int)(y), (int)Handler.cellW, (int)Handler.cellH);
		
		g.setColor(new Color(10, 135, 45));
		g.drawRect((int)(x), (int)(y), (int)Handler.cellW, (int)Handler.cellH);
		
		if(isClickable) {
			if(hover) {
				g.drawImage(MainClass.tex.mapTile[imgNr][1], (int)x, (int)y, null);
			}else {
				g.drawImage(MainClass.tex.mapTile[imgNr][0], (int)x, (int)y, null);
			}
		}
	}

}

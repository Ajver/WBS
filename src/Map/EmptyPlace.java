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
		if(col != null) {
			g.setColor(col);
			g.fillRect((int) (x), (int) (y), (int) Handler.cellW, (int) Handler.cellH);

			try {
				int rgb = col.getRGB();
				int re = (int) (((rgb >> 16) & 0xff) * 0.9f);
				int gr = (int) (((rgb >> 8) & 0xff) * 0.9f);
				int bl = (int) (((rgb >> 0) & 0xff) * 0.9f);

				g.setColor(new Color(re, gr, bl));
				g.drawRect((int) (x), (int) (y), (int) Handler.cellW, (int) Handler.cellH);
			}catch (NullPointerException e) {
				System.out.println("col!=null == null = 1 >>>? (Empty place > col)");
				System.out.println(col);
			}
		}else {
			g.setColor(new Color(20, 150, 50));
			g.fillRect((int) (x), (int) (y), (int) Handler.cellW, (int) Handler.cellH);

			g.setColor(new Color(10, 135, 45));
			g.drawRect((int) (x), (int) (y), (int) Handler.cellW, (int) Handler.cellH);
		}
		if(isClickable) {
			if(hover) {
				g.drawImage(MainClass.tex.mapTile[imgNr][1], (int)x, (int)y, null);
			}else {
				g.drawImage(MainClass.tex.mapTile[imgNr][0], (int)x, (int)y, null);
			}
		}
	}

}

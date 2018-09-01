package Other;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Button {

	private float x, y, w, h;
	private String caption = "";
	
	private BufferedImage img = null;
	private boolean hover = false;
	private boolean soundPlayed = false;
	
	public Button(float x, float y, float w, float h, String caption) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.caption = caption;
	}
	
	public void render(Graphics g) {
		if(img == null) {
			if(hover) {
				g.setColor(Color.GREEN);
			}else {
				g.setColor(Color.GRAY);
			}
			
			g.fillRect((int)x, (int)y, (int)w, (int)h);
			
			g.setColor(Color.WHITE); 
		}else {
			g.drawImage(img, (int)x, (int)y, null);
		}
		
		g.setFont(new Font("arial", 0, (int)(this.h * 0.7f)));
		FontMetrics f = g.getFontMetrics();
		float sx = x + (w - f.stringWidth(caption)) / 2.0f;
		float sy = y + (f.getAscent() + (h - (f.getAscent() + f.getDescent())) / 2);
		g.drawString(caption, (int)sx, (int)sy);
	}
	
	public void loadImg(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public void hover(int mx, int my) { 
		this.hover = mouseOver(mx, my);
		
		if(this.hover) {
			CursorManager.setCursor(CursorManager.HAND);
			if(!soundPlayed) {
				soundPlayed = (new SoundPlayer()).playSound("res/Sounds/click.wav");
			}				
		}else {
			soundPlayed = false;
		}
	}
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x && mx <= x+w && my >= y && my <= y+h;
	}
}

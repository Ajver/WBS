package Other;

import java.awt.*;
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

	private float rotate = 0.0f;
	private float rx, ry;

	public Button(String caption) {
		this(0,0,0,0, caption);
	}

	public Button(float x, float y, float w, float h, String caption) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.caption = caption;

		this.rx = x+w/2.0f;
		this.ry = y+h/2.0f;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(rotate, rx, ry);

		if(img == null) {
			if(hover) {
				g.setColor(Gamecol.BROWN);
			}else {
				g.setColor(Gamecol.DARK);
			}
			
			g.fillRect((int)x, (int)y, (int)w, (int)h);

			g.setColor(Gamecol.LIGHT);
		}else {
			g.drawImage(img, (int)x, (int)y, null);
		}
		
		g.setFont(new Font("arial", 0, (int)(this.h * 0.7f)));
		FontMetrics f = g.getFontMetrics();
		float sx = x + (w - f.stringWidth(caption)) / 2.0f;
		float sy = y + (f.getAscent() + (h - (f.getAscent() + f.getDescent())) / 2);
		g.drawString(caption, (int)sx, (int)sy);

		g2d.rotate(-rotate, rx, ry);
	}
	
	public void loadImg(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		if(rotate != 0) {
			float diffX = mx - rx;
			float diffY = ry - my;
			mx = (int)(Math.cos(rotate)*diffX - Math.sin(rotate)*diffY + rx);
			my = (int)(Math.sin(rotate)*diffX + Math.cos(rotate)*diffY + ry);
		}

		return mx >= x && mx <= x+w && my >= y && my <= y+h;
	}

	public void setCaption(String caption) { this.caption = caption; }
	public float getX() { return x; };
	public float getY() { return y; };
	public float getW() { return w; };
	public float getH() { return h; };

	public float getRotate() { return this.rotate; }

	public void rotate(float rotate) { this.rotate += rotate; }
	public void rotateTo(float rotate) { this.rotate = rotate; }

	public void rotate(float rotate, float rx, float ry) {
		this.rotate += rotate;
		this.rx = rx;
		this.ry = ry;
	}
	public void rotateTo(float rotate, float rx, float ry) {
		this.rotate = rotate;
		this.rx = rx;
		this.ry = ry;
	}

	public void setRX(float rx) { this.rx = rx; }
	public void setRY(float ry) { this.ry = ry; }

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
}

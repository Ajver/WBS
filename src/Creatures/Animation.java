package Creatures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Other.Handler;

public class Animation {

	private BufferedImage[] imgs;
	private int frames;
	private float progress = 0.0f;
	private float duration;
	
	public Animation(float duration, BufferedImage... allFrames) {
		this.duration = duration;
		
		this.frames = allFrames.length;
		this.imgs = new BufferedImage[frames];
		
		for(int i=0; i<frames; i++) {
			this.imgs[i] = allFrames[i];
		}
	}
	
	public void update(float et) {
		progress += et;
		
		if(progress >= duration) { // End of animation
			progress = 0.0f;
		}
	}
	
	public void render(Graphics g, float x, float y) {		
		int currentFrame = (int)((progress * frames) / duration);
		g.drawImage(imgs[currentFrame], (int)x, (int)y, null);
	}	
	
	public void render(Graphics g, float x, float y, float theta) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(theta, x+Handler.cellW/2.0f, y+Handler.cellH/2.0f);
		
		int currentFrame = (int)((progress * frames) / duration);
		g.drawImage(imgs[currentFrame], (int)x, (int)y, null);
		
		g2d.rotate(-theta, x+Handler.cellW/2.0f, y+Handler.cellH/2.0f);
	}	
}

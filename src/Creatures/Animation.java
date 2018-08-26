package Creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
}

package Fight;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class Action {
	
	// Position on Screen
	protected float x, y;
	
	// Position to animation
	protected float nextX, nextY;
	
	// Animation progress
	protected float progress = 0; // <0; 1>, where 0 - begin, 1 - end of animation
	protected float speed = 1; // Speed of animation progressing
	protected boolean isAnimated = false;
	
	protected BufferedImage img;
	
	// How long is this Action (no animation of this)
	protected int duration = 1; 
	
	public Action(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void update(float et);
	public abstract void render(Graphics g);
	public abstract void use();
	
	public void updateAnimation(float et) {
		if(!isAnimated) return;
		
		progress += speed * et;
		
		if(progress >= 1) {
			isAnimated = false;
			x = nextX;
			y = nextY;
		}
	}
	
	protected void startAnimation() {
		isAnimated = true;
		progress = 0.0f;
	}

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setNextX(float nextX) {
		this.nextX = nextX;
		startAnimation();
	}
	public void setNextY(float nextY) {
		this.nextY = nextY;
		startAnimation();
	}
	public void setNextXY(float nextX, float nextY) {
		this.nextX = nextX;
		this.nextY = nextY;
		startAnimation();
	}	
	public void setSpeed(float speed) { this.speed = speed; }
	
	public void setDuration(int d) { this.duration = d; }

	
	public int getDuration() { return duration; }
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x && mx <= x+(ActionManager.buttonW*duration) && my >= y && my <= y+ActionManager.buttonW;
	}
	
	public abstract void mouseReleased(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
}

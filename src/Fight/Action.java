package Fight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class Action {
	
	// Position on Screen
	protected float x, y;
	
	// Position to animation
	protected float nextX, nextY;
	protected float velX, velY;
	
	// Animation progress
	protected float progress = 0; // <0; 1>, where 0 - begin, 1 - end of animation
	protected float speed = 4.0f; // Speed of animation progressing
	protected boolean isAnimated = false;
	
	protected BufferedImage img;
	protected boolean hover = false;
	
	
	// How long is this Action (no animation of this)
	protected int duration = 1; 
	
	public Action(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(float et) {
		x += velX * et;
		y += velY * et;
		
		slUpdate(et);
		updateAnimation(et);
	}
	
	public void render(Graphics g) {
		if(hover) {
			g.setColor(new Color(162, 143, 64));
		}else {
			g.setColor(new Color(132, 103, 54));
		}
		
		if(isAnimated) {
			g.fillRect((int)(x + (nextX - x)*progress), (int)(y + (nextY - y)*progress), (int)(ActionManager.buttonW * duration), (int)(ActionManager.buttonW));
		}else {
			g.fillRect((int)x, (int)y, (int)(ActionManager.buttonW * duration), (int)(ActionManager.buttonW));
		}
	}
	
	public abstract void slUpdate(float et);
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

	public void move(int nx, int ny) {
		this.nextX = nx;
		this.nextY = ny;
		
	}
	
	public int getDuration() { return duration; }
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x + (nextX - x)*progress && 
				mx <= x + (nextX - x)*progress + (ActionManager.buttonW*duration) && 
				my >= y + (nextY - y)*progress && 
				my <= y + (nextY - y)*progress + ActionManager.buttonW;
	}
	
	public void hover(int mx, int my) { this.hover = mouseOver(mx, my); }
	
	public abstract void mouseReleased(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
}

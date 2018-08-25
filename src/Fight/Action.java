package Fight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Creatures.Creature;
import Other.Handler;
import Other.SoundPlayer;

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
	
	public BufferedImage img;
	protected boolean hover = false;
	private boolean soundPlayed = false;
	
	protected boolean mayBeCaneled = true;
	
	// How long is this Action (no animation of this)
	protected int duration = 1; 
	
	protected Creature c;
	protected Handler handler;
	
	protected float timer = 0.0f, breakTime;
	protected boolean isTimer = false;
	
	public Action(float x, float y, Creature c, Handler handler) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.handler = handler;
	}
	
	public void update(float et) {
		x += velX * et;
		y += velY * et;
		
		slUpdate(et);
		updateAnimation(et);
		
		if(isTimer) {
			timer += et;
			
			if(timer >= breakTime) {
				// Next action
				handler.nextAction();
				
				// Reset timer
				isTimer = false;
				timer = 0.0f;
			}
		}
	}
	
	public void render(Graphics g) {
		if(img == null) {
			if(hover) {
				g.setColor(new Color(162, 143, 64));
			}else {
				g.setColor(new Color(132, 103, 54));
			}
		}
		
		if(isAnimated) {
			if(img == null) {
				g.fillRect((int)getX(), (int)getY(), (int)(ActionManager.buttonW * duration), (int)(ActionManager.buttonW));
			}else {
				g.drawImage(img, (int)getX(), (int)getY(), null);
			}
		}else {
			if(img == null) {
				g.fillRect((int)x, (int)y, (int)(ActionManager.buttonW * duration), (int)(ActionManager.buttonW));
			}else {
				if(hover) {
					g.setColor(new Color(162, 143, 64));
					g.fillRect((int)x-4, (int)y-4, (int)(ActionManager.buttonW * duration + 8), (int)(ActionManager.buttonW + 8));
				}
				g.drawImage(img, (int)x, (int)y, null);
			}
		}
	}
	
	public abstract void slUpdate(float et);
	public abstract void select();
	public abstract void canel();
	
	public void use() { // To override 
		System.out.println("Error: No args 'mapX', 'mapY'");
	}
	public void use(int mapx, int mapY) { // To override 
		System.out.println("Error: use(mapX, mapY) is not overrided");
	}
	
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
	
	protected void startTimer(float bt) {
		this.breakTime = bt;
		this.isTimer = true;
	}
	
	public int getDuration() { return duration; }
	public float getX() { return x + (nextX - x)*progress; }
	public float getY() { return y + (nextY - y)*progress; }
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x + (nextX - x)*progress && 
				mx <= x + (nextX - x)*progress + (ActionManager.buttonW*duration) && 
				my >= y + (nextY - y)*progress && 
				my <= y + (nextY - y)*progress + ActionManager.buttonW;
	}
	
	public void hover(int mx, int my) { 
		this.hover = mouseOver(mx, my);
		
		if(this.hover) {
			if(!soundPlayed) {
				soundPlayed = (new SoundPlayer()).playSound("res/Sounds/click.wav");
			}				
		}else {
			soundPlayed = false;
		}
	}
	
	public boolean mayBeCaneled() { return this.mayBeCaneled; }
	
	public abstract void mouseReleased(MouseEvent e);
	public abstract void slMouseMoved(MouseEvent e);
	
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		int mapX = (int) ((mx + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((my + handler.camera.getY()) / Handler.cellH);

		for(int yy=0; yy<handler.map.h; yy++) {
			for(int xx=0; xx<handler.map.w; xx++) {
				handler.map.grid[xx][yy].setHover(false);
			}
		}
		if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
			handler.map.grid[mapX][mapY].setHover(true);
		}
		
		slMouseMoved(e);
	}
}

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

	public BufferedImage[] img = new BufferedImage[2];
	protected boolean hover = false;
	protected boolean wasHover = false;
	private boolean soundPlayed = false;
	private boolean isVisible = true;
	
	// How long is this Action (no animation of this)
	protected int duration = 1; 

	protected boolean isActive = true;

	protected Creature c;
	protected Handler handler;
	
	protected long breakTime;
	protected boolean isTimer = false;
	protected boolean used = false;
	
	public Action(Creature c, Handler handler) {
		this.c = c;
		this.handler = handler;
	}
	
	public void update(float et) {
		slUpdate(et);

		if(isTimer) {
			if(System.currentTimeMillis() >= breakTime) {
				// Next action
				handler.nextAction();

				// Reset
				isTimer = false;
				used = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(hover) {
			g.setColor(new Color(162, 143, 64));
			g.fillRect((int)x-4, (int)y-4, (int)(ActionManagerGUI.buttonW * duration + 8), (int)(ActionManagerGUI.buttonW + 8));
		}
		g.drawImage(img[isActive ? 0 : 1], (int)x, (int)y, null);
	}
	
	public abstract void slUpdate(float et);
	public abstract void select();
	public abstract void canel();

	public void use() { // To override 
		System.out.println("Error: No set use() function!");
	}
	public void use(int mapx, int mapY) { // To override 
		System.out.println("Error: No set use(mapX, mapY) function!");
	}

	public boolean can() {
		System.out.println("Error: No set can() function!");
		return false;
	}

	public boolean can(int mapX, int mapY)  {
		System.out.println("Error: No set can(mapX, mapY) function!");
		return false;
	}

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	
	protected void startTimer(long bt) {
		this.breakTime = System.currentTimeMillis() + bt;
		this.isTimer = true;
	}
	
	public int getDuration() { return duration; }
	public float getX() { return x; }
	public float getY() { return y; }
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x &&
				mx <= x + (ActionManagerGUI.buttonW*duration) &&
				my >= y &&
				my <= y + ActionManagerGUI.buttonW;
	}
	
	private void hover(MouseEvent e) {
		this.hover = mouseOver(e.getX(), e.getY());
		
		if(this.hover) {
			if(!soundPlayed) {
				wasHover = true;
				slMouseEntered();
				soundPlayed = (new SoundPlayer()).playSound("res/Sounds/click.wav");
			}				
		}else if(wasHover) {
			wasHover = false;
			slMouseLeved();
			soundPlayed = false;
		}
	}
	
	public boolean used() { return this.used; }
	
	public void mouseReleased(MouseEvent e) {}
	public void slMouseMoved(MouseEvent e) {}
	public void slMouseEntered() {}
	public void slMouseLeved() {}

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

		if(isVisible) {
			hover(e);
		}

		slMouseMoved(e);
	}

	private void mouseEntered(MouseEvent e) {
		slMouseMoved(e);
	}

	public void setVisible(boolean flag) { this.isVisible = flag; }
}

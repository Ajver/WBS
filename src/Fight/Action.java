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

	public BufferedImage img;
	protected boolean hover = false;
	private boolean soundPlayed = false;
	
	protected boolean mayBeCaneled = true;
	
	// How long is this Action (no animation of this)
	protected int duration = 1; 
	
	protected Creature c;
	protected Handler handler;
	
	protected long breakTime;
	protected boolean isTimer = false;
	
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
				
				// Reset timer
				isTimer = false;
				
				// Reset 
				mayBeCaneled = true;
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

			if(img == null) {
				g.fillRect((int)getX(), (int)getY(), (int)(ActionManagerGUI.buttonW * duration), (int)(ActionManagerGUI.buttonW));
			}else {
				g.drawImage(img, (int)getX(), (int)getY(), null);
			}
			if(img == null) {
				g.fillRect((int)x, (int)y, (int)(ActionManagerGUI.buttonW * duration), (int)(ActionManagerGUI.buttonW));
			}else {
				if(hover) {
					g.setColor(new Color(162, 143, 64));
					g.fillRect((int)x-4, (int)y-4, (int)(ActionManagerGUI.buttonW * duration + 8), (int)(ActionManagerGUI.buttonW + 8));
				}
				g.drawImage(img, (int)x, (int)y, null);
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

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	
	protected void startTimer(int bt) {
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

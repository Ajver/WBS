package Fight;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Creatures.Creature;
import Other.Comment;
import Other.CursorManager;
import Other.Handler;
import Other.SoundPlayer;

public abstract class Action {
	
	// Position on Screen
	protected float x, y;

	public BufferedImage[] img = new BufferedImage[2];
	protected boolean hover = false;
	protected boolean wasHover = false;
	private boolean isVisible = true;
	
	// How long is this Action (no animation of this)
	protected int duration = 1; 

	protected boolean isActive = true;
	protected Comment comment = new Comment();

	protected Creature c;
	protected Handler handler;
	
	protected long breakTime;
	protected boolean isTimer = false;
	protected boolean used = false;

	private int pMapX, pMapY = -1;
	private long sound_bt = 0;

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
		if (hover) {
			if(isActive) {
				g.setColor(new Color(162, 143, 64));
				g.fillRect((int)(x-4), (int)(y-4), (int)(ActionManagerGUI.buttonW * duration + 8), (int)(ActionManagerGUI.buttonW + 8));
			}else {
				handler.addComment(comment);
			}
		}

		g.drawImage(img[isActive ? 0 : 1], (int) x, (int) y, null);
	}

	public abstract void refresh();
	public abstract void canel();
	public abstract void select();

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
	
	private void hover(MouseEvent e) {
		this.hover = mouseOver(e.getX(), e.getY());

		if (this.hover) {
			if(isActive) {
				CursorManager.setCursor(CursorManager.HAND);
			}
			if (!wasHover) {
				wasHover = true;
				mouseEntered();
			}
		} else if (wasHover) {
			wasHover = false;
			mouseLeved();
		}
	}
	
	public boolean used() { return this.used; }
	
	public void mouseReleased(MouseEvent e) {}
	public void slMouseMoved(MouseEvent e) {}
	public void slMouseEntered() {}
	public void slMouseLeved() {}
	public void slUpdate(float et) {}

	public void mouseMoved(MouseEvent e) {
		comment.mouseMoved(e);

		if(isVisible) {
			hover(e);
		}

		if(!isActive) return;

		int mx = e.getX();
		int my = e.getY();

		int mapX = (int) ((mx + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((my + handler.camera.getY()) / Handler.cellH);

		for (int yy = 0; yy < handler.map.h; yy++) {
			for (int xx = 0; xx < handler.map.w; xx++) {
				handler.map.grid[xx][yy].setHover(false);
			}
		}

		if (mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
			if(handler.map.grid[mapX][mapY].isClickable()) {
				CursorManager.setCursor(CursorManager.HAND);
				handler.map.grid[mapX][mapY].setHover(true);

				if(mapX != pMapX || mapY != pMapY) {
					if(System.currentTimeMillis() >= sound_bt) {
						new SoundPlayer().playSound("res/Sounds/click.wav");
						pMapX = mapX;
						pMapY = mapY;
						sound_bt = System.currentTimeMillis() + 150;
					}
				}
			}
		}

		slMouseMoved(e);
	}

	private void mouseEntered() {
		if(isActive) {
			new SoundPlayer().playSound("res/Sounds/click.wav");
		}
		slMouseEntered();
	}

	private void mouseLeved() {
		slMouseLeved();
	}

	public void setVisible(boolean flag) { this.isVisible = flag; }

	public boolean mouseOver(int mx, int my) {
		return mx >= x &&
				mx <= x + (ActionManagerGUI.buttonW*duration) &&
				my >= y &&
				my <= y + ActionManagerGUI.buttonW;
	}
}

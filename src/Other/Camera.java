package Other;

import Creatures.Creature;
import MainFiles.MainClass;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Camera {

	private float x, y;

	// Original position
	private float ox, oy, diffX, diffY;
	private AnimationTiming animation;
	private boolean isFollowing = false;

	// Previous mouse position
	private int pmx, pmy;
    private boolean isDragging = false;

    private Creature c;
	private fPoint p;

	// Point of target
    private class fPoint {
    	float x, y;
    	public fPoint(float x, float y) {
    		set(x, y);
		}
		public void set(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	public Camera(Handler handler) {
		Creature c = handler.creatures.get(0);

		this.x = c.getX() - MainClass.WW / 2.0f + Handler.cellW / 2.0f;
		this.y = c.getY() - MainClass.WH / 2.0f + Handler.cellH / 2.0f;

		p = new fPoint(c.getX(), c.getY());

		animation = new AnimationTiming(700, AnimationTiming.TimingFun.ease, AnimationTiming.RepeatableFun.norepeat);

		start();
	}
	
	public void update() {
	    if(!isDragging) {
			if (!animation.update()) {
				x = ox + diffX;
				y = oy + diffY;

				ox = x;
				oy = y;
				diffX = diffY = 0;
			}
			x = ox + diffX * animation.getProgress();
			y = oy + diffY * animation.getProgress();
        }
	}
	
	public void focus(Creature c) {
		if(c != null) {
			this.c = c;
			start();
		}
	}
	
	private void setDiffs() {
		this.diffX = (p.x - MainClass.WW / 2.0f + Handler.cellW / 2.0f) - this.x;
		this.diffY = (p.y - MainClass.WH / 2.0f + Handler.cellH / 2.0f) - this.y;
	}

	private void start() {
		setDiffs();
		if(diffX != 0 || diffY != 0) {
			animation.start();
		}
	}

	public void mousePressed(MouseEvent e) {
	    if(e.getButton() == 3) {
            pmx = e.getX() + (int) x;
            pmy = e.getY() + (int) y;

            CursorManager.setCursor(CursorManager.MOVE);

            isDragging = true;
        }
    }

	public void mouseDragged(MouseEvent e) {
	    if(isDragging) {
            int mx = e.getX();
            int my = e.getY();

            this.x = this.ox = pmx - mx;
            this.y = this.oy = pmy - my;
        }
    }

    public void mouseReleased(MouseEvent e) {
		isDragging = false;

		CursorManager.setCursor(CursorManager.DEFAULT);
    }
	
	public float getX() { return x; }
	public float getY() { return y; }
}

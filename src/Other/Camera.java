package Other;

import Creatures.Creature;
import MainFiles.MainClass;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Camera {

	private float x, y;
	private float velX, velY;

	// Previous mouse position
	private int pmx, pmy;
    private boolean isDragging = false;

    private Creature c;

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

	private fPoint p;


	public Camera(Handler handler) {
		Creature c = handler.creatures.get(0);

		this.x = c.getX() - MainClass.WW / 2.0f + Handler.cellW / 2.0f;
		this.y = c.getY() - MainClass.WH / 2.0f + Handler.cellH / 2.0f;

		p = new fPoint(x, y);
	}
	
	public void update(float et) {
	    if(!isDragging) {
            setVelocity();

            x += velX * et;
            y += velY * et;
        }
	}
	
	public void focus(Creature c) {
		if(c != null) {
			this.c = c;
		}
	}
	
	private void setVelocity() {
		if(c != null) {
			float speed = 0.4f;
			this.velX = ((c.getX() - MainClass.WW / 2.0f + Handler.cellW / 2.0f) - this.x) / speed;
			this.velY = ((c.getY() - MainClass.WH / 2.0f + Handler.cellH / 2.0f) - this.y) / speed;
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

            this.x = pmx - mx;
            this.y = pmy - my;
        }
    }

    public void mouseReleased(MouseEvent e) {
		isDragging = false;
		CursorManager.setCursor(CursorManager.DEFAULT);
    }
	
	public float getX() { return x; }
	public float getY() { return y; }
}

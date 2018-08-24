package Other;

import java.awt.Graphics;

import Map.Map;
import Map.Map.ID;

public abstract class GameObject {
	
	// Position on Screen (Before camera translations)
	protected float x, y;
	
	// Position on Map (2D array)
	protected int mx, my;
	
	public ID id;
	
	public GameObject(int mx, int my) {
		setMX(mx);
		setMY(my);
	}
	
	public abstract void update(float et);
	public abstract void render(Graphics g);
	
	public int getMX() { return mx; }
	public int getMY() { return my; }
	public float getX() { return x; }
	public float getY() { return y; }
	
	public void setMXY(int mx, int my) {
		this.setMX(mx);
		this.setMY(my);
	}
	
	public void setMX(int mx) {
		this.mx = mx;
		this.x = mx * Handler.cellW;
	}
	
	public void setMY(int my) {
		this.my = my;
		this.y = my * Handler.cellH;
	}
}

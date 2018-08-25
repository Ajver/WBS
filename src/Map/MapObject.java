package Map;

import java.awt.Color;

import Other.GameObject;

public abstract class MapObject extends GameObject {
	
	protected boolean isClickable = false;
	protected boolean mayByPath = true;
	protected boolean hover = false;
	
	protected Color col;
	
	public MapObject(int mx, int my) {
		super(mx, my);
	}
	
	public void setClickable(Color col) { this.isClickable = col != null; this.col = col; } 
	public void setHover(boolean flag) { if(isClickable) { this.hover = flag; } }
	
	public boolean mayBePath() { return this.mayByPath; }
	public boolean isClickable() { return this.isClickable; }
}

package Map;

import Other.GameObject;

public abstract class MapObject extends GameObject {
	
	protected boolean isClickable = false;
	protected boolean mayByPath = true;
	protected boolean hover = false;
	
	public MapObject(int mx, int my) {
		super(mx, my);
	}
	
	public void setClickable(boolean flag) { this.isClickable = flag; } 
	public void setHover(boolean flag) { if(isClickable) { this.hover = flag; } }
	
	public boolean mayBePath() { return this.mayByPath; }
	public boolean isClickable() { return this.isClickable; }
}
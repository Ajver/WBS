package Other;

import Creatures.Creature;
import MainFiles.MainClass;

public class Camera {

	private float x, y;
	private float velX, velY;
	
	private Creature c;
	
	
	public Camera(Handler handler) {
		this.c = handler.creatures.get(0);
		
		this.x = c.getX() - MainClass.WW / 2.0f + Handler.cellW / 2.0f;
		this.y = c.getY() - MainClass.WH / 2.0f + Handler.cellH / 2.0f;
	}
	
	public void update(float et) {
		setVelocity();
		
		x += velX * et;
		y += velY * et;
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
	
	public float getX() { 
		return x;
	}
	
	public float getY() { 
		return y;
	}
}

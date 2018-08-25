package Other;

import Creatures.Player;
import MainFiles.MainClass;

public class Camera {

	private float x, y;
	private float velX, velY;
	
	private Handler handler;
	private Player player;
	
	
	public Camera(Handler handler) {
		this.handler = handler;
		this.player = handler.player;
		
		this.x = player.getX() - MainClass.WW / 2.0f + Handler.cellW / 2.0f;
		this.y = player.getY() - MainClass.WH / 2.0f + Handler.cellH / 2.0f;
	}
	
	public void update(float et) {
		if(player != null) {
			float speed = 0.4f;
			this.velX = ((player.getX() - MainClass.WW / 2.0f + Handler.cellW / 2.0f) - this.x) / speed;
			this.velY = ((player.getY() - MainClass.WH / 2.0f + Handler.cellH / 2.0f) - this.y) / speed;
			
			x += velX * et;
			y += velY * et;
		}
	}
	
	public float getX() { 
		return x;
	}
	
	public float getY() { 
		return y;
	}
}

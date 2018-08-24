package Other;

import Creatures.Player;
import MainFiles.MainClass;

public class Camera {

	private float x, y;
	
	private Handler handler;
	private Player player;
	
	
	public Camera(Handler handler) {
		this.handler = handler;
		this.player = handler.player;
	}
	
	public void update(float et) {
		if(player != null) {
			this.x = player.getX() - MainClass.WW / 2.0f + Handler.cellW / 2.0f;
			this.y = player.getY() - MainClass.WH / 2.0f + Handler.cellH / 2.0f;
		}
	}
	
	public float getX() { 
		return x;
	}
	
	public float getY() { 
		return y;
	}
}

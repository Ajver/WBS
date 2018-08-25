package Creatures;

import java.awt.Point;
import java.util.LinkedList;

import Other.GameObject;
import Other.Handler;
import Other.SoundPlayer;

public abstract class Creature extends GameObject {
	
	// Move velocity (over Screen)
	protected float velX, velY;
	
	protected Handler handler;
	
	protected boolean isMoving = false;
	private LinkedList<Point> path;
	private int currentPoint = 0;
	private float timer = animationSpeed;
	
	public static float animationSpeed = 0.4f;
	
	private SoundPlayer soundPlayer;
	
	public Creature(int mx, int my, Handler handler) {
		super(mx, my);
		this.handler = handler;	
		
		soundPlayer = new SoundPlayer("res/sounds/steping_on_dirt.wav");
	}
	
	protected void move(float et) {
		timer += et;
		if(timer >= animationSpeed) {
			if(currentPoint < path.size()) {
				soundPlayer.start();
				velX = (path.get(currentPoint).x - mx) * (Handler.cellW / animationSpeed);
				velY = (path.get(currentPoint).y - my) * (Handler.cellH / animationSpeed);
				currentPoint++;
				timer = 0.0f;
			}else {
				soundPlayer.stop();
				soundPlayer.reload();
				timer = animationSpeed;
				velX = velY = 0;
				currentPoint = 0;
				isMoving = false;
			}
			setMXY(mx, my);
		}
	}
	
	public void move(LinkedList<Point> path) {
		isMoving = true;
		this.path = path;
	}
}

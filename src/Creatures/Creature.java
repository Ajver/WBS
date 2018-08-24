package Creatures;

import java.awt.Point;
import java.util.LinkedList;

import Other.GameObject;
import Other.Handler;

public abstract class Creature extends GameObject {
	
	// Move velocity (over Screen)
	protected float velX, velY;
	
	protected Handler handler;
	
	protected boolean isMoving = false;
	private LinkedList<Point> path;
	private int currentPoint = 0;
	private float timer = 0;
	
	public Creature(int mx, int my, Handler handler) {
		super(mx, my);
		
		this.handler = handler;	
	}
	
	protected void move(float et) {
		timer += et;
		if(timer >= 0.4f) {
			timer = 0;
			if(currentPoint < path.size()) {
				setMXY(path.get(currentPoint).x, path.get(currentPoint).y);
				currentPoint++;
			}else {
				currentPoint = 0;
				isMoving = false;
			}
		}
	}
	
	public void move(LinkedList<Point> path) {
		isMoving = true;
		this.path = path;
	}
}

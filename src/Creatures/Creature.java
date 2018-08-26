package Creatures;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import Character.Attributes;
import Fight.ActionList;
import Fight.ArtificialIntelligence;
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
	
	protected Animation moveAnimation;
	private int direction = 0;
	
	public ActionList al;
	private ArtificialIntelligence AI;
	private boolean hasAI = true;
	
	private SoundPlayer soundPlayer;
	
	public Attributes att;
	public int hp;
	
	public Creature(int mx, int my, Handler handler) {
		super(mx, my);
		this.handler = handler;	
		
		this.att = new Attributes();
		
		this.al = new ActionList(handler, this);
		this.AI = new ArtificialIntelligence(this, handler);
		
		soundPlayer = new SoundPlayer("res/sounds/step_on_dirt.wav");
	}
	
	public void hit(int dmg) {
		this.hp -= dmg;
		handler.msg.add("HP left: " + this.hp);
		
		if(hp < 0) {
			//handler.removeCreature(this);
		}
	}
	
	public void update(float et) {
		if(isMoving) { 
			move(et); 
			
			x += velX * et;
			y += velY * et;
			
			mx = (int)((x + Handler.cellW/2.0f) / Handler.cellW);
			my = (int)((y + Handler.cellH/2.0f) / Handler.cellH);
			
			if(velX == 0 && velY < 0) direction = 0;
			else if(velX > 0 && velY < 0) direction = 1;
			else if(velX > 0 && velY == 0) direction = 2;
			else if(velX > 0 && velY > 0) direction = 3;
			
			else if(velX == 0 && velY > 0) direction = 4;
			else if(velX < 0 && velY > 0) direction = 5;
			else if(velX < 0 && velY == 0) direction = 6;
			else if(velX < 0 && velY < 0) direction = 7;
			
			moveAnimation.update(et);
		}else {
			setMXY(mx, my);
		}
		
		if(hasAI) {
			AI.update(et);
		}
	}
	
	public void render(Graphics g) {
		moveAnimation.render(g, x, y, direction*0.7853125f);
	}
	
	protected void move(float et) {
		timer += et;
		if(timer >= animationSpeed) {
			if(currentPoint < path.size()) {
				soundPlayer.start();
				soundPlayer.loop();
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
	
	public void round() {
		if(hasAI) {
			AI.round();
		}
	}
	
	public void setDirection(int direction) { this.direction = direction % 8; }
	public void setFocus(int fmx, int fmy) { // Where Should look
		if(mx == fmx && my < fmy) direction = 0;
		else if(fmx > mx && fmy < my) direction = 1;
		else if(fmx > mx && fmy == my) direction = 2;
		else if(fmx > mx && fmy > my) direction = 3;
		
		else if(fmx == mx && fmy > my) direction = 4;
		else if(fmx < mx && fmy > my) direction = 5;
		else if(fmx < mx && fmy == my) direction = 6;
		else if(fmx < mx && fmy < my) direction = 7;		
	}
	
	public void setHasAI(boolean flag) { this.hasAI = flag; }
}

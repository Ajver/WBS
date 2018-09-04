package Creatures;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import Character.Attributes;
import Fight.AI;
import Fight.ActionManager;
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
	private long timer;
	
	public static float moveDuration = 0.6f;
	public static float attackDuration = 0.6f;

	protected Animation moveAnimation;
	protected Animation attackAnimation;
	private int direction = 0;

	private boolean isAttacking = false;

	public ActionManager am;
	public AI AI;
	private boolean hasAI = true;

	private long stepTimer;
	private int currentStep = 0;
	private String[] stepsPath = { "step_0.wav", "step_1.wav" };
	private Random r = new Random();

	public Attributes att;
	public int hp;

	public String name;
	public int group;
	
	public Creature(int mx, int my, Handler handler) {
		super(mx, my);
		this.handler = handler;	
		
		this.att = new Attributes();

		this.AI = new AI(this, handler);
	}
	
	public void hit(int dmg) {
		this.hp -= dmg;
		SoundPlayer.playNextSound("res/Sounds/damage.wav");

		handler.addSmallMessage(this, "-"+dmg, new Color(255, 0, 0));

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
			if(isAttacking) {
			    if(!attackAnimation.update(et)) {
			        isAttacking = false;
                }
            }
		}
		
		if(hasAI) {
			AI.update(et);
		}
	}
	
	public void render(Graphics g) {
        if(isAttacking) {
            attackAnimation.render(g, x, y-16, direction * 0.7853125f, x+Handler.cellW/2.0f, y+Handler.cellH/2.0f);
        }else {
            moveAnimation.render(g, x, y, direction * 0.7853125f);
        }
	}
	
	protected void move(float et) {
		if(System.currentTimeMillis() >= stepTimer) {
			stepTimer += (long)(moveDuration*500.0f);
//			SoundPlayer.playNextSound("res/Sounds/" + stepsPath[r.nextInt(stepsPath.length)]);
			SoundPlayer.playNextSound("res/Sounds/" + stepsPath[currentStep]);
			currentStep = (currentStep+1) % 2;
		}

		if(System.currentTimeMillis() >= timer) {
			timer = System.currentTimeMillis() + (long)(moveDuration*1000.0f);

			if(currentPoint < path.size()) {
				velX = (path.get(currentPoint).x - mx) * (Handler.cellW / moveDuration);
				velY = (path.get(currentPoint).y - my) * (Handler.cellH / moveDuration);
				currentPoint++;
			}else {
				velX = velY = 0;
				currentPoint = 0;
				isMoving = false;
				setMXY(mx, my);
			}
		}
	}
	
	public void move(LinkedList<Point> path) {
		if(path.size() > 0) {
			this.moveDuration = 3.0f / path.size();
			if(this.moveDuration < 0.35f) { this.moveDuration = 0.35f; }
			else if(this.moveDuration > 0.5f) { this.moveDuration = 0.5f; }

			moveAnimation.setDuration(this.moveDuration);
			isMoving = true;
			this.path = path;
			stepTimer = System.currentTimeMillis() + (long) (moveDuration * 500.0f);
			timer = 0;
		}
	}

	public void round() {
		if(hasAI) {
			AI.round();
		}
	}

	public void setFocus(int fmx, int fmy) { // Where Should look
		if(mx == fmx && fmy < my) direction = 0;
		else if(fmx > mx && fmy < my) direction = 1;
		else if(fmx > mx && fmy == my) direction = 2;
		else if(fmx > mx && fmy > my) direction = 3;
		
		else if(fmx == mx && fmy > my) direction = 4;
		else if(fmx < mx && fmy > my) direction = 5;
		else if(fmx < mx && fmy == my) direction = 6;
		else if(fmx < mx && fmy < my) direction = 7;
	}

	public void attack() {
	    this.isAttacking = true;
		SoundPlayer.playNextSound("res/Sounds/sword_" + r.nextInt(2) + ".wav");
    }

	public void setHasAI(boolean flag) { this.hasAI = flag; }
}

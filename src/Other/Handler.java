package Other;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Creatures.Creature;
import Creatures.Player;
import Fight.ActionManager;
import MainFiles.MainClass;
import Map.Map;

public class Handler extends MouseAdapter {
	
	private MainClass m;
	
	public static float cellW = 32, cellH = 32;
	
	public LinkedList<Creature> creatures = new LinkedList<Creature>();
	public Player player;
	
	public Map map;
	
	public ActionManager actionManager;
	public Camera camera;
	
	public Handler(MainClass m) {
		this.m = m;
		
		player = new Player(0, 10, this);
		
		map = new Map(50, 30);
		actionManager = new ActionManager(this);
		
		camera = new Camera(this);
	}
	
	public void update(float et) {
		for(Creature c:creatures) {
			c.update(et);
		}
		player.update(et);
		
		map.update(et);
		actionManager.update(et);
		camera.update(et);
	}
	
	public void render(Graphics g) {
		g.translate((int)-camera.getX(), (int)-camera.getY());
		////////////////////////////////////////////////////////////
			
			map.render(g);
			
			for(Creature c:creatures) {
				c.render(g);
			}
			player.render(g);
			
		////////////////////////////////////////////////////////////
		g.translate((int)camera.getX(), (int)camera.getY());
			
		actionManager.render(g);
	}	
	
	public Creature getFromMap(int x, int y) {
		for(Creature c : creatures) {
			if(c.getMX() == x) {
				if(c.getMY() == y) {
					return c;
				}
			}
		}
		
		return null;
	}
	
	public void addCreature(Creature c) {
		if(c != null) {
			creatures.add(c);
		}
	}
	
	public void removeCreature(Creature c) {
		if(c != null) {
			creatures.remove(c);
		}
	}
	
	public void removeCreature(int i) {
		if(i >= 0) {
			creatures.remove(i);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
	}
	
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		actionManager.mouseReleased(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		actionManager.mouseMoved(e);
	}

}

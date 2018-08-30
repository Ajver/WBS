package Other;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Creatures.Creature;
import Creatures.Human;
import Fight.ActionManager;
import Fight.ActionManagerGUI;
import Fight.FightMessage;
import MainFiles.MainClass;
import Map.Map;

public class Handler extends MouseAdapter {
	
	private MainClass m;
	
	public static float cellW = 32, cellH = 32;
	
	public LinkedList<Creature> creatures = new LinkedList<Creature>();
	private int currentCreature = 0;
	
	public Map map;
	
	public ActionManagerGUI actionManager;
	public Camera camera;
	public FightMessage msg;
	
	public Handler(MainClass m) {
		this.m = m;
		
		this.msg = new FightMessage(MainClass.WW - 512 - ActionManagerGUI.buttonW,
				MainClass.WH - 256 - ActionManagerGUI.buttonW,
				512, 256);
		
		// Player 
		creatures.add(new Human(17, 10, this));
		creatures.get(0).setHasAI(false);
		
		// Other creatures
		creatures.add(new Human(10, 10, this));
		
		map = new Map(50, 30, this);
		actionManager = new ActionManagerGUI(this, creatures.get(0));
		
		camera = new Camera(this);
		camera.focus(creatures.get(currentCreature));
	}
	
	public void update(float et) {
		if(currentCreature == 0) {
			actionManager.update(et);
		}else {
			creatures.get(currentCreature).round();
		}
		
		for(Creature c : creatures) {
			c.update(et);
		}
		
		map.update(et);
		camera.update(et);
	}
	
	public void render(Graphics g) {
		g.translate((int)-camera.getX(), (int)-camera.getY());
		////////////////////////////////////////////////////////////
			
			map.render(g);
			
			for(Creature c:creatures) {
				c.render(g);
			}
			
		////////////////////////////////////////////////////////////
		g.translate((int)camera.getX(), (int)camera.getY());
			
		if(currentCreature == 0) {
			actionManager.render(g);
		}
		
		msg.render(g);
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
	
	public void nextRound() {
		currentCreature++;
		
		if(currentCreature >= creatures.size()) {
			currentCreature = 0;
		}
		
		camera.focus(creatures.get(currentCreature));
	}
	
	public void nextAction() {
		if(currentCreature == 0) {
			actionManager.nextAction();
		}else {
			creatures.get(currentCreature).AI.nextAction();
		}
	}
	
	public void addCreature(Creature c) {
		if(c != null) {
			creatures.add(c);
		}
	}
	
	public void removeCreature(Creature c) {
		for(int i=0; i<creatures.size(); i++) {
			if(c == creatures.get(i)) {
				removeCreature(i);
			}
		}
	}
	
	public void removeCreature(int i) {
		if(i >= 0 && i < creatures.size()) {
			creatures.remove(i);
			
			if(i < currentCreature) {
				currentCreature--;
				
				if(currentCreature < 0) {
					currentCreature = 0;
				}
			}
		}
	}
	
	public void removeAllCreatures() {
		creatures.clear();
	}	
	
	////////////////////////////////////////////////////////////////////////////////
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		camera.mousePressed(e);

		if(e.getButton() == 2) {
            int mapX = (int) ((e.getX() + camera.getX()) / Handler.cellW);
            int mapY = (int) ((e.getY() + camera.getY()) / Handler.cellH);
			System.out.println("Clicked: " + mapX + " | " + mapY);
        }
	}
	
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(currentCreature == 0) {
			actionManager.mouseReleased(e);
		}

        camera.mouseReleased(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		if(currentCreature == 0) {
			actionManager.mouseMoved(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		camera.mouseDragged(e);
	}

}

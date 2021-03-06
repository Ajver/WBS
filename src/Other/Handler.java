package Other;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Creatures.Creature;
import Creatures.Human;
import Fight.ActionManager;
import Fight.ActionManagerGUI;
import Fight.FightMessage;
import Fight.Message;
import MainFiles.MainClass;
import Map.Map;

public class Handler extends MouseAdapter {
	
	private MainClass m;
	
	public static float cellW = 32, cellH = 32;
	
	public LinkedList<Creature> creatures = new LinkedList<>();
	private int currentCreature = 0;

	private LinkedList<Message> smallMessages = new LinkedList<>();
	
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
		creatures.add(new Human(2, 10, this));
		
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

		for(Message m : smallMessages) {
			if(!m.update(et)) {
				smallMessages.remove(m);
			}
		}

		map.update(et);
		camera.update(et);
	}
	
	public void render(Graphics g) {
		int tx = (int)camera.getX();
		int ty = (int)camera.getY();
		g.translate(-tx, -ty);
		////////////////////////////////////////////////////////////
			
			map.render(g);
			
			for(Creature c:creatures) {
				c.render(g);
			}

			for(Message m : smallMessages) {
				m.render(g);
			}
			
		////////////////////////////////////////////////////////////
		g.translate(tx, ty);
			
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
			actionManager.refresh();
		}
		
		camera.focus(creatures.get(currentCreature));
	}
	
	public void nextAction() {
		creatures.get(currentCreature).am.nextAction();
	}
	
	public void addCreature(Creature c) {
		if(c != null) {
			creatures.add(c);
		}
	}

	public void addSmallMessage(float x, float y, String s, Color col) {
		this.smallMessages.add(new Message(x, y, s, col));
	}

	public void addSmallMessage(Creature c, String s, Color col) {
		this.smallMessages.add(new Message(c, s, col));
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
		CursorManager.setCursor(CursorManager.DEFAULT);

		if(currentCreature == 0) {
			actionManager.mouseMoved(e);
		}

		m.setCursor(CursorManager.cursor);
	}

	public void mouseDragged(MouseEvent e) {
		camera.mouseDragged(e);
	}

	//////////////////////////////////////////////////////////////////////////////////

	public void keyPressed(KeyEvent e) {
		if(currentCreature == 0) {
			actionManager.keyPressed(e);
		}
	}

}

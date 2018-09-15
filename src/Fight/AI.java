package Fight;

import Creatures.Creature;
import Other.Handler;

import java.awt.*;
import java.util.LinkedList;

public class AI {
	
	private Creature c;
	private Handler handler;
	private ActionManager am;
	
	public AI(Creature c, Handler handler) {
		this.c = c;
		this.handler = handler;
		this.am = new ActionManager(handler, c);
	}
	
	public void update(float et) {
		if(am.current() != null) {
			am.current().update(et);
		}
	}

	public void round() {
		if(am.current() == null) { // Selecting action
			// Finding player
			int ex = handler.creatures.get(0).getMX();
			int ey = handler.creatures.get(0).getMY();
			
			// Is player in range
			boolean mayAttack = false;
			
			for(int yy=-1; yy<=1; yy++) {
				for(int xx=-1; xx<=1; xx++) {
					if(c.getMX()+xx == ex) {
						if(c.getMY()+yy == ey) {
							mayAttack = true;
							yy = xx = 10;
						}
					}
				}
			}
			if(mayAttack) {
				// Attack
				am.select(new ActionAttack(c, handler, null));
				am.current().use(ex, ey);
			}else {
				int nex, ney;

				float len;
				boolean mayRunattack = false;

				LinkedList<Point> moveP = new LinkedList<>();
				LinkedList<Point> runP = new LinkedList<>();

				for(int yy=-1; yy<=1; yy++) {
					for (int xx=-1; xx<=1; xx++) {
						if(xx != 0 || yy != 0) {
							len = handler.map.getPathLength(c.getMX(), c.getMY(), ex + xx, ey + yy);
							if (len > 0 && len < c.att.getSz()) {
								moveP.add(new Point(ex+xx, ey+yy));
							}else if(len >= c.att.getSz() && len <= c.att.getSz() * 2) {
								mayRunattack = true;
								yy = xx = 10;
							}else if(len > c.att.getSz() * 2) {
								runP.add(new Point(ex+xx, ey+yy));
							}
						}
					}
				}

				if(mayRunattack) {
					nex = ex;
					ney = ey;
					am.select(new ActionRunattack(c, handler, null));
					am.current().use(nex, ney);
				}else if(moveP.size() > 0) {
					Point selP = handler.map.getNearestPoint(moveP, c.getMX(), c.getMY());
					nex = selP.x;
					ney = selP.y;
					System.out.println("AI: move to " + nex + " | " + ney);
					am.select(new ActionMove(c, handler, null));
					am.current().use(nex, ney);
				}else if(runP.size() > 0) {
					Point selP = handler.map.getNearestPoint(runP, c.getMX(), c.getMY());
					nex = selP.x;
					ney = selP.y;
					System.out.println("AI: run to " + nex + " | " + ney);
					am.select(new ActionRun(c, handler, null));
					am.current().use(nex, ney);
				}else {
					am.select(new ActionSkip(null, handler, null));
					am.current().use();
					System.out.println("Can't go to the player");
				}
			}
		}
	}
}

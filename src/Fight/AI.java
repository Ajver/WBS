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
			int px = handler.creatures.get(0).getMX();
			int py = handler.creatures.get(0).getMY();
			
			// Is player in range
			boolean mayAttack = false;
			
			for(int yy=-1; yy<=1; yy++) {
				for(int xx=-1; xx<=1; xx++) {
					if(c.getMX()+xx == px) {
						if(c.getMY()+yy == py) {
							mayAttack = true;
							yy = xx = 10;
						}
					}
				}
			}
			if(mayAttack) {
				// Attack
				am.select(new ActionAttack(c, handler));
				am.current().use(px, py);
			}else {
				float len;

				boolean mayRunattack = false, mayMove = false;

				for(int yy=-1; yy<=1; yy++) {
					for (int xx=-1; xx<=1; xx++) {
						if(xx != 0 || yy != 0) {
							len = handler.map.getPathLength(c.getMX(), c.getMY(), px + xx, py + yy);
							if (len > 0 && len < c.att.getSz()) {
								mayMove = true;
							}else if(len >= c.att.getSz() && len <= c.att.getSz() * 2) {
								mayRunattack = true;
								yy = xx = 2;
							}
						}
					}
				}

				Point bp = handler.map.getNearestPoint(c.getMX(), c.getMY(), px, py);

				if(bp != null) {
					int npx = bp.x;
					int npy = bp.y;

					if(mayRunattack) {
						npx = px;
						npy = py;
						am.select(new ActionRunattack(c, handler));
						System.out.println("AI: runnatack to " + npx + " | " + npy);
					}else if(mayMove) {
						am.select(new ActionMove(c, handler));
						System.out.println("AI: move to " + npx + " | " + npy);
					}else {
						am.select(new ActionRun(c, handler));
						System.out.println("AI: run to " + npx + " | " + npy);
					}

					am.current().use(npx, npy);
				}else {

				}
			}
		}
	}

	public void nextAction() {
		am.nextAction();
	}
}

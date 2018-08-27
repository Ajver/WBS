package Fight;

import Creatures.Creature;
import Other.Handler;

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
			boolean isPlayer = false;
			
			for(int yy=-1; yy<=1; yy++) {
				for(int xx=-1; xx<=1; xx++) {
					if(c.getMX()+xx == px) {
						if(c.getMY()+yy == py) {
							isPlayer = true;
							yy = xx = 10;
						}
					}
				}
			}
			if(isPlayer) {
				// Attack
				am.select(new ActionAttack(c, handler));
				am.current().use(px, py);
			}else {
				int npx = px, npy = py;
				float len = -1;
				for(int yy=-1; yy<=1; yy++) {
					for(int xx=-1; xx<=1; xx++) {
						int diffX = px+xx - c.getMX();
						int diffY = py+yy - c.getMY();
						float newLen = (float)(Math.sqrt(diffX*diffX + diffY*diffY));
						if(len == -1 || newLen < len) {
							if(newLen > 0) {
								len = newLen;
								npx = px+xx;
								npy = py+yy;
							}
						}
					}
				}
				
				if(handler.map.getPathLength(c.getMX(), c.getMY(), npx, npy) <= c.att.getSz() * 2) {
					am.select(new ActionMove(c, handler));
				}else {
					am.select(new ActionRun(c, handler));
				}

				am.current().use(npx, npy);
			}
		}
	}

	public void nextAction() {
		am.nextAction();
	}
}

package Fight;

import Creatures.Creature;
import Other.Handler;

public class ArtificialIntelligence {
	
	private Creature c;
	private Handler handler;
	private ActionList al;
	
	public ArtificialIntelligence(Creature c, Handler handler) {
		this.c = c;
		this.handler = handler;
		al = c.al;
	}
	
	public void update(float et) {
		if(al.current() != null) {
			al.current().update(et);
		}
	}

	public void round() {
		if(al.current() == null) { // Selecting action
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
				al.select(new ActionAttack(0, 0, c, handler));
				al.current().use(px, py);
			}else {
				if(px != c.getMX() || py != c.getMY()) {
					al.select(new ActionMove(0, 0, c, handler));
					al.current().use(px, py);
				}
			}
			
			
		}
	}
}

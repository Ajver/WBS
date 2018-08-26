package Fight;

import java.util.LinkedList;

import Creatures.Creature;
import Other.Handler;

public class ActionList {

	public LinkedList<Action> actions = new LinkedList<Action>();
	public Action[] selected = { null, null };
	public int currentAction = 0;
	
	private Handler handler;
	
	public ActionList(Handler handler, Creature c) {
		this.handler = handler;
		
		actions.add(new ActionAttack(c, handler));
		actions.add(new ActionMove(c, handler));
		actions.add(new ActionRun(c, handler));
	}
	
	public void reset() {
		selected[0] = null;
		selected[1] = null;
		currentAction = 0;
	}
	
	public void nextAction() {
		currentAction++;
		
		if(currentAction > 1 || selected[0].getDuration() == 2) { // End of round
			reset();
			handler.nextRound();
		}
	}
	
	public void select(Action a) { if(current() == null) { selected[currentAction] = a; } }
	public Action current() { return selected[currentAction]; }
}

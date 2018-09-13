package Fight;

import Creatures.Creature;
import Other.Handler;

public class ActionManager {

    public Action[] selected = { null, null };
    public int currentAction = 0;

	protected Handler handler;
	public Creature c;

    public int attackCounter;
    public int[] MOD = new int[8];
    public int MOD_DMG = 0;

	public ActionManager(Handler handler, Creature c) {
        this.handler = handler;
        this.c = c;
        c.am = this;
	}

    public void reset() {
        selected[0] = null;
        selected[1] = null;
        currentAction = 0;
        attackCounter = 0;

        for(int i=0; i<8; i++) {
            MOD[i] = 0;
        }

        MOD_DMG = 0;
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

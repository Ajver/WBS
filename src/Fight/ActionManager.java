package Fight;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Button;
import Other.Handler;

public class ActionManager {

    public Action[] selected = { null, null };
    public int currentAction = 0;

	protected Handler handler;
	public Creature c;

	public ActionManager(Handler handler, Creature c) {
        this.handler = handler;
        this.c = c;
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

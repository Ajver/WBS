package Fight;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Handler;
import Character.HUD;

public class ActionSkip extends Action {

    public ActionSkip(Creature c, Handler handler, HUD hud) {
        super(c, handler, hud);
        this.img = MainClass.tex.timerAB;
    }

    public void select() {
        use();
    }
    public void use() { startTimer(500); }

    public void refresh() {}
    public void canel() { stopTimer(); }
}

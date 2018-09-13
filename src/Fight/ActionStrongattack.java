package Fight;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Handler;
import Character.HUD;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ActionStrongattack extends Action {

    private Action attack;

    public ActionStrongattack(Creature c, Handler handler, HUD hud) {
        super(c, handler, hud);
        this.img = MainClass.tex.swordIcon;
        this.duration = 2;

        this.attack = new ActionAttack(c, handler, hud);
    }

    public void refresh() {
        if(c.am.currentAction == 1) {
            this.isActive = false;
            comment.set("Nie mo¿esz ju¿ wykonaæ silnego ataku w tej rundzie");
        }else {
            this.isActive = attack.can();
            this.comment = attack.comment;
        }
    }

    public void canel() {
        attack.canel();
    }

    public boolean can() {
        return attack.can();
    }

    public void select() {
        attack.select();
        this.hover = false;
        slMouseLeved();
    }

    public boolean mouseOver(int mx, int my) {
        return mx >= x && mx <= x+ActionManagerGUI.buttonW * duration && my >= y && my <= y+ActionManagerGUI.buttonW;
    }

    public void use(int mapX, int mapY) {
        c.am.MOD_DMG += 2;
        attack.use(mapX, mapY);
        startTimer((long)(c.attackDuration * 1000.0f));
        used = true;
    }

    public void mouseReleased(MouseEvent e) {
        int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
        int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);

        if(handler.map.isClickable(mapX, mapY)) {
            canel();
            use(mapX, mapY);
        }
    }

    public void slMouseEntered() {
        attack.slMouseEntered();
    }

    public void slMouseLeved() {
        attack.slMouseLeved();
    }
}

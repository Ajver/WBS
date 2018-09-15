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
        this.img = MainClass.tex.swordAB;
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
        clearColors();
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
        int cx = c.getMX();
        int cy = c.getMY();

        for(int yy=-1; yy<=1; yy++) {
            for (int xx = -1; xx <= 1; xx++) {
                if (cx + xx >= 0 && cx + xx < handler.map.w && cy + yy >= 0 && cy + yy < handler.map.h) {
                    if (xx != 0 || yy != 0) {
                        handler.map.grid[cx + xx][cy + yy].setColor(new Color(142, 29, 1));
                    }
                }
            }
        }

        hud.light(HUD.WW);
        hud.light(HUD.S);
        incMOD(HUD.S, 2);
    }

    protected void slResetMOD() {
        hud.incMOD(HUD.S, -2);
    }
}

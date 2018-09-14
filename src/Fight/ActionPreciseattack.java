package Fight;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Handler;
import Character.HUD;

import java.awt.*;

public class ActionPreciseattack extends ActionAttack {

    public ActionPreciseattack(Creature c, Handler handler, HUD hud) {
        super(c, handler, hud);
        this.img = MainClass.tex.swordIcon;
        this.duration = 2;
    }

    public void refresh() {
        if(c.am.currentAction == 1) {
            this.isActive = false;
            comment.set("Nie mo¿esz ju¿ wykonaæ silnego ataku w tej rundzie");
        }else {
            this.isActive = can();
        }
    }

    public void use(int mapX, int mapY) {
        c.am.MOD[HUD.WW] += 20;

        Creature enemy = handler.getFromMap(mapX, mapY);
        c.setFocus(mapX, mapY);

        if(c.att.WW(c.am.MOD[HUD.WW])) {
            int dmg = c.eq.weapon.useInt() + c.am.MOD_DMG;
            enemy.hit(dmg);
        }else {
            handler.addSmallMessage(enemy, "Pud³o", new Color(0, 0, 255));
        }

        startTimer((long)(c.attackDuration * 1000.0f));
        c.attack();
        used = true;
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
        incMOD(HUD.WW, 20);
    }

    protected void slResetMOD() {
        hud.incMOD(HUD.WW, -20);
    }
}

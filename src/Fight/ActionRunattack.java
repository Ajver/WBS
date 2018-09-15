package Fight;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Handler;
import Character.HUD;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ActionRunattack extends Action {

    private int mapX, mapY;

    private boolean isLocalTimer = false;
    private long localBreakTime;

    public ActionRunattack(Creature c, Handler handler, HUD hud) {
        super(c, handler, hud);
        this.duration = 2;
        this.img = MainClass.tex.runattackAB;
    }

    public void slUpdate(float et) {
        if(isLocalTimer) {
            if(System.currentTimeMillis() >= localBreakTime) {
                isLocalTimer = false;
                c.am.MOD[HUD.WW] += 10;
                attack();
            }
        }
    }

    public void select() {
        int cx = c.getMX();
        int cy = c.getMY();

        int minLen = c.att.getSz() + 1;
        int maxLen = c.att.getSz() * 2 + 1;

        clearColors();

        for(int yy=-maxLen; yy<=maxLen; yy++) {
            for(int xx=-maxLen; xx<=maxLen; xx++) {
                if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
                    int len = handler.map.getPathLength(cx, cy, cx+xx, cy+yy);
                    if (len >= minLen && len <= maxLen) {
                        if (handler.getFromMap(cx + xx, cy + yy) != null) {
                            handler.map.setClickable(cx+xx, cy+yy, 1);
                        }
                    }
                }
            }
        }
    }

    private void attack() {
        new ActionAttack(c, handler, hud).use(mapX, mapY);
        startTimer((long)(c.attackDuration*1000.0f));
    }

    public void use(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;

        if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
            int cx = c.getMX();
            int cy = c.getMY();

            float len;

            LinkedList<Point> runattackP = new LinkedList<>();

            for(int yy=-1; yy<=1; yy++) {
                for (int xx=-1; xx<=1; xx++) {
                    if(xx != 0 || yy != 0) {
                        len = handler.map.getPathLength(c.getMX(), c.getMY(), mapX + xx, mapY + yy);
                        if(len >= c.att.getSz() && len <= c.att.getSz() * 2) {
                            runattackP.add(new Point(mapX+xx, mapY+yy));
                        }
                    }
                }
            }

            if(runattackP.size() > 0) {
                Point selP = handler.map.getNearestPoint(runattackP, c.getMX(), c.getMY());

                System.out.println("Runattack to " + selP.x + " | " + selP.y);

                LinkedList<Point> path = handler.map.getPath(cx, cy, selP.x, selP.y);

                used = true;

                c.move(path);
                c.moveDuration *= 0.75f;
                startLocalTimer((int) (c.moveDuration * (path.size()) * 1000.0f));
            }else {
                System.out.println("Can't runattack to " + mapX + " | " + mapY);
            }
        }
    }

    private void startLocalTimer(int bt) {
        this.localBreakTime = System.currentTimeMillis() + bt;
        this.isLocalTimer = true;
    }

    public void canel() {
        for(int yy=0; yy<handler.map.h; yy++) {
            for(int xx=0; xx<handler.map.w; xx++) {
                handler.map.setClickable(xx, yy, false);
            }
        }
    }

    public void refresh() {
        if(c.am.currentAction == 0) {
            int cx = c.getMX();
            int cy = c.getMY();

            int minLen = c.att.getSz() + 1;
            int maxLen = c.att.getSz() * 2 + 1;

            for (int yy = -maxLen; yy <= maxLen; yy++) {
                for (int xx = -maxLen; xx <= maxLen; xx++) {
                    if (cx + xx >= 0 && cx + xx < handler.map.w && cy + yy >= 0 && cy + yy < handler.map.h) {
                        int len = handler.map.getPathLength(cx, cy, cx + xx, cy + yy);
                        if (len >= minLen && len <= maxLen) {
                            if (handler.getFromMap(cx + xx, cy + yy) != null) {
                                isActive = true;
                                return;
                            }
                        }
                    }
                }
            }

            comment.set("Nie ma przeciwnika w zasiêgu");
        }else {
            comment.set("Nie mo¿esz ju¿ szar¿owaæ w tej rundzie");
        }

        isActive = false;
    }

    public void mouseReleased(MouseEvent e) {
        int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
        int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);

        if(handler.map.isClickable(mapX, mapY)) {
            if(handler.getFromMap(mapX, mapY) != null) {
                canel();
                use(mapX, mapY);
            }
        }
    }

    public void slMouseEntered() {
        int cx = c.getMX();
        int cy = c.getMY();

        int minLen = c.att.getSz() + 1;
        int maxLen = c.att.getSz() * 2 + 1;

        for(int yy=-maxLen; yy<=maxLen; yy++) {
            for(int xx=-maxLen; xx<=maxLen; xx++) {
                if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
                    int len = handler.map.getPathLength(cx, cy, cx+xx, cy+yy);
                    if (len >= minLen && len <= maxLen) {
                        handler.map.grid[cx + xx][cy + yy].setColor(new Color(142, 29, 1));
                    }
                }
            }
        }

        hud.light(HUD.WW);
        hud.light(HUD.Sz);

        incMOD(HUD.WW, 10);
    }

    protected void slResetMOD() {
        hud.incMOD(HUD.WW, -10);
    }
}

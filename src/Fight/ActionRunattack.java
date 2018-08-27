package Fight;

import Creatures.Creature;
import Creatures.Human;
import MainFiles.MainClass;
import Other.Handler;
import Character.Dice;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ActionRunattack extends Action {

    private int mapX, mapY;

    private boolean isLocalTimer = false;
    private long localBreakTime;

    public ActionRunattack(Creature c, Handler handler) {
        super(c, handler);
        this.duration = 2;
        this.img = MainClass.tex.runattackIcon;
    }

    public void slUpdate(float et) {
        if(isLocalTimer) {
            if(System.currentTimeMillis() >= localBreakTime) {
                isLocalTimer = false;
                attack();
            }
        }
    }

    public void select() {
        int cx = c.getMX();
        int cy = c.getMY();

        int maxLen = c.att.current[1][3] * 4 + 1;

        boolean isInRange = false;

        for(int yy=-maxLen; yy<=maxLen; yy++) {
            for(int xx=-maxLen; xx<=maxLen; xx++) {
                if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
                    int len = handler.map.getPathLength(cx, cy, cx+xx, cy+yy);
                    if(len > 0 && len <= maxLen) {
                        for(int nyy=-1; nyy<=1; nyy++) {
                            for(int nxx=-1; nxx<=1; nxx++) {
                                if(xx+nxx != 0 || yy+nyy != 0) {
                                    if (handler.getFromMap(cx + xx + nxx, cy + yy + nyy) != null) {
                                        handler.map.grid[cx + xx + nxx][cy + yy + nyy].setClickable(1);
                                        isInRange = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(!isInRange) {
            handler.msg.set("No enemies in Range");
        }
    }

    private void attack() {
        Creature enemy = handler.getFromMap(mapX, mapY);
        c.setFocus(mapX, mapY);

        if(enemy != null) {
            if(c.att.WW(0)) {
                int dmg = Dice.roll1d10();
                handler.msg.set("Damage: " + dmg);
                enemy.hit(dmg);
            }else {
                handler.msg.set("Miss");
            }
            c.attack();
            startTimer(1000);
        }else {
            handler.msg.set("No enemy on xy: " + mapX + " | " + mapY);
        }
    }

    public void use(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;

        if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
            int cx = c.getMX();
            int cy = c.getMY();

            c.setFocus(mapX, mapY);

            int npx = mapX, npy = mapY;
            float len = -1;
            for(int yy=-1; yy<=1; yy++) {
                for(int xx=-1; xx<=1; xx++) {
                    int diffX = mapX+xx - c.getMX();
                    int diffY = mapY+yy - c.getMY();
                    float newLen = (float)(Math.sqrt(diffX*diffX + diffY*diffY));
                    if(len == -1 || newLen < len) {
                        if(newLen > 0) {
                            len = newLen;
                            npx = mapX+xx;
                            npy = mapY+yy;
                        }
                    }
                }
            }

            LinkedList<Point> path = handler.map.getPath(cx, cy, npx, npy);
            if(path != null) {
                int maxLen = c.att.current[1][3] * 4 + 1;

                if(path.size() > maxLen+1) {
                    LinkedList<Point> newPath = new LinkedList<Point>();

                    for(int i=0; i<maxLen+1; i++) {
                        newPath.add(path.get(i));
                    }

                    path = newPath;
                }



                mayBeCaneled = false;

                c.move(path);
                startLocalTimer((int)(Human.animationSpeed * (path.size()+1) * 1000.0f));
            }else {
                handler.msg.set("No path to runattack");
            }
        }
    }

    private void startLocalTimer(int bt) {
        this.localBreakTime = System.currentTimeMillis() + bt;
        this.isLocalTimer = true;
        System.out.println("Current time: " + System.currentTimeMillis());
        System.out.println("Local timer:  " + localBreakTime);
    }

    public void canel() {
        for(int yy=0; yy<handler.map.h; yy++) {
            for(int xx=0; xx<handler.map.w; xx++) {
                handler.map.grid[xx][yy].setClickable(false);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
        int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);

        canel();
        use(mapX, mapY);
    }

    public void slMouseMoved(MouseEvent e) {

    }
}

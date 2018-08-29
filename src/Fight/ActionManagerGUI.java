package Fight;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Button;
import Other.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ActionManagerGUI extends ActionManager {

    public static float buttonW = 64;

    private ActionGroup[] ag = new ActionGroup[3];

    private Button canelBtn;

    private BufferedImage actionBg;

    float y = MainClass.WH - buttonW*2.0f;

    float selX = buttonW;

    public ActionManagerGUI(Handler handler, Creature c) {
        super(handler, c);

        this.actionBg = MainClass.tex.actionBg;

        canelBtn = new Button(buttonW-16, y+buttonW+20, buttonW*2.0f+32, 32, "Anuluj akcjê");

        Creature p = handler.creatures.get(0);
        float x = (MainClass.WW - 288) / 2.0f;
        this.ag[0] = new ActionGroup(x, y+64, new ActionAttack(p, handler), new ActionRunattack(p, handler));
        this.ag[1] = new ActionGroup(x+96, y+64, new ActionMove(p, handler), new ActionRun(p, handler));
        this.ag[2] = new ActionGroup(x+192, y+64, new ActionAttack(p, handler));
    }

    public void update(float et) {
        for(ActionGroup ag : ag) {
            ag.update(et);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(204, 150, 0));
        g.fillOval((int)(selX + buttonW*currentAction - 16), (int)y-16, (int)buttonW + 32, (int)buttonW + 32);

        g.drawImage(actionBg, (int)selX, (int)y, null);
        g.drawImage(actionBg, (int)(selX + buttonW), (int)y, null);

        if(selected[0] != null) {
            g.drawImage(selected[0].img, (int)selX, (int)y, null);

            if(selected[1] != null) {
                g.drawImage(selected[1].img, (int)(selX+buttonW), (int)y, null);
            }
        }

        g.setColor(new Color(0,0, 0));
        FontMetrics f = g.getFontMetrics();

        int sx = (int)(selX + buttonW - f.stringWidth("Wybrane akcje") / 2.0f);
        g.drawString("Wybrane akcje", sx, (int)(y - buttonW * 0.5f));

        int index = -1;
        for(int i=0; i<3; i++) {
            if(ag[i].isOpen()) {
                index = i;
                i = 3;
            }
        }

        if(index != -1) {
            for(int i=0; i<3; i++) {
                if(i != index) {
                    ag[i].render(g);
                }
            }
            ag[index].render(g);
        }else {
            for(ActionGroup ag : ag) {
                ag.render(g);
            }
        }

        if(selected[currentAction] != null) {
            if(!selected[currentAction].used()) {
                canelBtn.render(g);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
        int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);

        if(e.getButton() == 2) {
            System.out.println(handler.map.getPathLength(c.getMX(), c.getMY(), mapX, mapY));
        }

        if(current() == null) {
            for(ActionGroup ag : ag) {
                Action a = ag.mouseReleased(e);

                if(a != null) {
                    if (a.getDuration() == 1 || (a.getDuration() == 2 && currentAction == 0)) {
                        select(a);
                    }
                }
            }
        }else {
            for(ActionGroup ag : ag) {
                for(Action a : ag.actions) {
                    if(a.mouseOver(e.getX(), e.getY())) {
                        return;
                    }
                }
            }

            if(mouseOver(e.getX(), e.getY(), selX, y, buttonW*2, buttonW)) {
                return;
            }

            if(!selected[currentAction].used()) {
                if(canelBtn.mouseOver(e.getX(), e.getY())) {
                    selected[currentAction].canel();
                    selected[currentAction] = null;
                    showActions();
                    return;
                }
            }

            if(!current().used()) {
                current().mouseReleased(e);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        int index = -1;
        for(int i=0; i<3; i++) {
            if(ag[i].isOpen()) {
                index = i;
                i = 3;
            }
        }
        if(index != -1) {
            ag[index].mouseMoved(e);
        }else {
            for(ActionGroup ag : ag) {
                ag.mouseMoved(e);
            }
        }

        if(current() != null) {
            boolean good = true;

            if(good) {
                current().mouseMoved(e);
            }
            if(!current().used()) {
                canelBtn.hover(e.getX(), e.getY());
            }
        }
    }

    public void nextAction() {
        currentAction++;

        if(currentAction > 1 || selected[0].getDuration() == 2) { // End of round
            reset();
            handler.nextRound();
        }

        showActions();
    }

    private void showActions() {
        for(ActionGroup ag : ag) {
            ag.show();
        }
    }

    private void hideActions() {
        for(ActionGroup ag : ag) {
            ag.hide();
        }
    }

    public void select(Action a) {
        if(current() == null) {
            selected[currentAction] = a;
            hideActions();
        }
    }

    private boolean mouseOver(int mx, int my, float x, float y, float w, float h) {
        return mx >= x && mx <= x+w && my >= y && my <= y+h;
    }
}

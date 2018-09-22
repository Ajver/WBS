package Fight;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Button;
import Other.Gamecol;
import Other.Handler;
import Character.HUD;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ActionManagerGUI extends ActionManager {

    public static float buttonW = 64;

    private ActionGroup[] ag = new ActionGroup[3];
    private Action previousAction = null;

    private Button canelBtn;

    private BufferedImage actionBg;

    float y = MainClass.WH - buttonW - MainClass.margin;

    float selX = buttonW;

    public ActionManagerGUI(Handler handler, Creature c, HUD hud) {
        super(handler, c);

        this.c.name = "Ty";
        this.actionBg = MainClass.tex.actionBg;

        canelBtn = new Button(buttonW-16, y+buttonW+20, buttonW*2.0f+32, 32, "Anuluj akcjê");

        Creature p = handler.creatures.get(0);
        float x = (MainClass.WW - 288) / 2.0f;

        this.ag[0] = new ActionGroup(x, y+MainClass.margin,
                new ActionAttack(p, handler, hud),
                new ActionRunattack(p, handler, hud),
                new ActionStrongattack(p, handler, hud),
                new ActionPreciseattack(p, handler, hud));

        this.ag[1] = new ActionGroup(x+96, y+MainClass.margin,
                new ActionMove(p, handler, hud),
                new ActionRun(p, handler, hud));

        this.ag[2] = new ActionGroup(x+192, y+MainClass.margin,
                new ActionSkip(p, handler, hud));

        refresh();
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
            g.drawImage(selected[0].img[0], (int)selX, (int)y, null);

            if(selected[1] != null) {
                g.drawImage(selected[1].img[0], (int)(selX+buttonW), (int)y, null);
            }
        }

        g.setFont(new Font("arial", 0, 30));

        g.setColor(Gamecol.DARK);
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
        if(e.getButton() == 1) {
            if (current() == null) {
                for (ActionGroup ag : ag) {
                    Action a = ag.mouseReleased(e);

                    if (a != null) {
                        select(a);
                    }
                }
            } else {
                if (mouseOver(e.getX(), e.getY(), selX, y, buttonW * 2, buttonW)) {
                    return;
                }

                if (!current().used()) {
                    if (canelBtn.mouseOver(e.getX(), e.getY())) {
                        canelAction();
                        return;
                    }
                }

                if (!current().used()) {
                    current().mouseReleased(e);
                }
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

    public void refresh() {
        for(ActionGroup ag : ag) {
            for (Action a : ag.actions) {
                a.refresh();
            }
        }
    }

    public void nextAction() {
        currentAction++;

        if(currentAction > 1 || selected[0].getDuration() == 2) { // End of round
            reset();
            handler.nextRound();
        }

        refresh();

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
            if(a != null) {
                if(a.isActive) {
                    if (a.getDuration() == 1 || currentAction == 0) {
                        selected[currentAction] = a;
                        previousAction = a;
                        a.select();
                        hideActions();
                    }
                }
            }
        }
    }

    private void canelAction() {
        current().canel();
        selected[currentAction] = null;
        showActions();
    }

    private boolean mouseOver(int mx, int my, float x, float y, float w, float h) {
        return mx >= x && mx <= x+w && my >= y && my <= y+h;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                select(previousAction);
                break;
            case KeyEvent.VK_ESCAPE:
                canelAction();
                break;
            default:
                System.out.println(e.getKeyChar());
        }
    }
}

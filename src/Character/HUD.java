package Character;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Button;
import Other.Comment;
import Other.Gamecol;
import Other.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HUD {

    private float x, y;
    private float w, h;
    private float cellW, cellH = 32;

    private Creature c;
    private Handler handler;

    private String[][] attNames = { Attributes.getShortcutOrder(0), Attributes.getShortcutOrder(1) };

    private boolean isComment = false;
    private Comment comment = new Comment();

    private Button hideButton;
    private float progress = 0.0f, vel = 1.0f;
    private float tx;
    private boolean isAnimating = false;
    private boolean isVisible = true;

    public HUD(Handler handler) {
        this.handler = handler;
        this.c = handler.creatures.get(0);

        this.w = 320;

        this.cellW = w / 8.0f;

        this.h = 16 + cellH*4 + 24;

        this.x = MainClass.WW - w - 64;
        this.y = MainClass.WH - h - 64;

        tx = h + 64;

        int bw = 100, bh = 30;
        hideButton = new Button(MainClass.WW-bw-64, y-bh, bw, bh, "Schowaj");
    }

    public void update(float et) {
        if(isAnimating) {
            progress += et * vel;

            if(progress >= 1.0f) {
                progress = 1.0f;
                isAnimating = false;
                isVisible = false;
                hideButton.setCaption("Poka¿");
            }else if(progress < 0.0f) {
                progress = 0.0f;
                isAnimating = false;
                hideButton.setCaption("Schowaj");
            }
        }
    }

    public void render(Graphics g) {
        g.translate(0, (int)(tx*progress));
        if(isVisible) {
            // Health Points
            g.setColor(new Color(48, 24, 3));
            g.fillRect((int) x, (int) y, (int) w, 24);

            int margin = 2;
            int hpw = (int) (c.hp * (w - margin * 2) / c.att.getZyw());
            g.setColor(new Color(139, 10, 10));
            g.fillRect((int) (x + margin), (int) (y + margin), (int) (hpw), (24 - margin * 2));

            g.setFont(new Font("arial", 0, 18));
            String shp = c.hp + " / " + c.att.getZyw();
            FontMetrics f = g.getFontMetrics();
            g.setColor(Gamecol.LIGHT);
            g.drawString(shp, (int) (x + (w - f.stringWidth(shp)) / 2.0f), (int) (y + 18));

            // Attributes
            for (int i = 0; i < 2; i++) {
                int offset = (int) (72 * i + y + 32);
                g.setColor(new Color(48, 24, 3));
                g.fillRect((int) x, (int) (offset), (int) w, (int) cellH);

                g.setColor(Gamecol.LIGHT);
                g.fillRect((int) x, (int) (cellH + offset), (int) w, (int) cellH);
            }

            g.setFont(new Font("arial", 1, 16));
            f = g.getFontMetrics();
            for (int i = 0; i < 8; i++) {
                // Main attributes
                String name = attNames[0][i];
                String value = "" + c.att.current[0][i];
                int nx = (int) (x + i * cellW + (cellW - f.stringWidth(name)) / 2.0f);
                int vx = (int) (x + i * cellW + (cellW - f.stringWidth(value)) / 2.0f);
                int offset = (int) (y + 32 + cellH - 9);

                g.setColor(Gamecol.LIGHT);
                g.drawString(name, nx, (offset));

                g.setColor(Gamecol.DARK);
                g.drawString(value, vx, (int) (cellH + offset));

                // Second attributes
                name = attNames[1][i];
                value = "" + c.att.current[1][i];
                nx = (int) (x + i * cellW + (cellW - f.stringWidth(name)) / 2.0f);
                vx = (int) (x + i * cellW + (cellW - f.stringWidth(value)) / 2.0f);
                offset += 72;

                g.setColor(Gamecol.LIGHT);
                g.drawString(name, nx, (offset));

                g.setColor(Gamecol.DARK);
                g.drawString(value, vx, (int) (cellH + offset));
            }

            if(isComment) {
                handler.addComment(comment);
            }
        }

        hideButton.render(g);
        g.translate(0, (int)-(tx*progress));
    }

    private void show() {
        isAnimating = true;
        vel = -4.0f;
        isVisible = true;
    }

    private void hide() {
        isAnimating = true;
        vel = 4.0f;
    }

    public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY() - (int)(tx*progress);

        if(isVisible) {
            if (mouseOver(mx, my)) {
                isComment = true;
                comment.mouseMoved(e);

                if (mouseOver(mx, my, x, y, w, 32)) {
                    comment.set("¯ywotnoœæ: " + c.hp);
                } else if (mouseOver(mx, my, x, y + 32, w - 1, cellH * 4 + 8)) {
                    int nr = my > y + 40 + cellH * 2 ? 1 : 0;
                    int i = (int) ((mx - x) / cellW);

                    String[] names = Attributes.getNamesOrder(nr);

                    comment.set(names[i]);
                } else {
                    isComment = false;
                }
            } else {
                isComment = false;
            }
        }

        hideButton.hover(mx, my);
    }

    public void mousePressed(MouseEvent e) {
        if(hideButton.mouseOver(e.getX(), e.getY() - (int)(tx*progress))) {
            if(isVisible) {
                hide();
            }else {
                show();
            }
        }
    }

    private boolean mouseOver(int mx, int my) {
        return mx >= x && mx <= x+w && my >= y && my <= y+h;
    }

    private boolean mouseOver(int mx, int my, float x, float y, float w, float h) {
        return mx >= x && mx <= x+w && my >= y && my <= y+h;
    }
}

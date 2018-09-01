package Other;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Comment {

    private float mx, my;
    private String caption;

    public Comment() {
        this("");
    }

    public Comment(String caption) {
        this.caption = caption;
    }

    public void render(Graphics g) {
        g.setFont(new Font("arial", 0, 20));
        FontMetrics f = g.getFontMetrics();

        int w = f.stringWidth(caption) + 16;
        int h = f.getHeight()+6;

        int x = (int)(mx - w/2.0f);
        int y = (int)(my - h - 4);

        int r = 5;
        g.setColor(new Color(99, 59, 7));
        g.fillRoundRect((int)x, (int)(y),
                w, h, r, r);

        g.setColor(Gamecol.DARK);
        g.drawRoundRect((int)x, (int)(y),
                w, h, r, r);

        g.setColor(Gamecol.LIGHT);
        g.drawString(caption, (int)(x+8), (int)(y+22));
    }

    public void set(String caption) {
        this.caption = caption;
    }

    public void mouseMoved(MouseEvent e) {
        this.mx = e.getX();
        this.my = e.getY();
    }
}

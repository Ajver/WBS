package Fight;

import Creatures.Creature;

import java.awt.*;
import java.util.Random;

public class Message {

    private float x, y;
    private float velX, velY;
    private float nextX, nextY;
    private String caption;
    private Color col;

    private long bt;

    public Message(Creature c, String caption, Color col) {
        Random r = new Random();

        create(c.getX() + r.nextInt(16) + 8, c.getY()-4, caption, col);
    }

    public Message(float x, float y, String caption, Color col) {
        create(x, y, caption, col);
    }

    private void create(float x, float y, String caption, Color col) {
        this.x = x;
        this.y = y;
        this.caption = caption;
        this.col = col;

        Random r = new Random();

        nextX = x - r.nextInt(40) + 20;
        nextY = y - r.nextInt(20) - 20;

        bt = System.currentTimeMillis() + 1000;
    }

    public boolean update(float et) {
        velX = (nextX - x) * 0.5f;
        velY = (nextY - y) * 0.5f;

        x += velX * et;
        y += velY * et;

        if(System.currentTimeMillis() >= bt) {
            return false;
        }

        return true;
    }

    public void render(Graphics g) {
        g.setFont(new Font("arial", 0, 20));
        g.setColor(col);

        FontMetrics f = g.getFontMetrics();
        g.drawString(caption, (int)(x - f.stringWidth(caption)/2.0f), (int)y);
    }
}

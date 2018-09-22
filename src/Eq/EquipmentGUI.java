package Eq;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Button;
import Other.Gamecol;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EquipmentGUI extends Equipment {

    private float x, y, w, h;
    private float ch;
    int margin = 16;

    private Button hideButton;
    private float progress = 0.0f, vel = 1.0f;
    private float movingSpeed = 5.0f;
    private float tx;
    private boolean isAnimating = false;
    private boolean isVisible = true;

    int bw = 100, bh = 30;

    public EquipmentGUI(Creature c) {
        super(c);
        pushItem(new Sword(c));

        ch = 24;

        w = 320;
        h = 180 + backpack.length*(ch+8);
        x = MainClass.WW - w - MainClass.margin;
        y = MainClass.margin;

        this.hideButton = new Button(x+w-bw,y-bh, bw, bh, "Schowaj");
        this.hideButton.setRX(hideButton.getX()+bw);

        tx = w + MainClass.margin;
    }

    public void update(float et) {
        if(isAnimating) {
            progress += et * vel;

            if(progress >= 1.0f) {
                progress = 1.0f;
                isAnimating = false;
                isVisible = false;
            }else if(progress < 0.0f) {
                progress = 0.0f;
                isAnimating = false;
            }
        }
    }

    public void render(Graphics g) {
        g.translate((int)(tx*progress), 0);

        g.setColor(Gamecol.DARK_BROWN);
        g.fillRect((int)x, (int)y, (int)w, (int)h);

        g.setColor(Gamecol.LIGHT);
        g.setFont(new Font("arial", 0, (int)(ch * 0.6)));

        for(int i=0; i<backpack.length; i++) {
            int xx = (int)(x+ch+margin*2);
            int yy = (int)(260 + (i+1)*(ch+4));
            g.drawLine(xx, yy, (int)(x+w-margin), yy);

            Item it = backpack[i];
            if(it != null) {
                g.drawString(it.name, xx, yy-6);
            }

            xx = (int) (x + margin);
            yy = (int) (yy - ch);

            if(it != null) {
                it.render(g, xx, yy);
            }else {
                g.drawRect(xx, yy, (int) ch, (int) ch);
            }
        }

        g.translate(-(int)(tx*progress), 0);

        g.translate((int)((64-hideButton.getH()/2)*progress), 0);
        hideButton.rotateTo(-progress * (float)Math.PI / 2.0f);
        hideButton.render(g);
        g.translate(-(int)((64-hideButton.getH()/2)*progress), 0);
    }

    private void show() {
        isAnimating = true;
        vel = -movingSpeed;
        isVisible = true;
        hideButton.setCaption("Schowaj");
    }

    private void hide() {
        isAnimating = true;
        vel = movingSpeed;
        hideButton.setCaption("Poka¿");
    }

    public void mouseMoved(MouseEvent e) {
        hideButton.hover(e.getX()-(int)((64-hideButton.getH()/2)*progress), e.getY());
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1) {
            if (hideButton.mouseOver(e.getX()-(int)((64-hideButton.getH()/2)*progress), e.getY())) {
                if (isVisible) {
                    hide();
                } else {
                    show();
                }
            }
        }
    }
}

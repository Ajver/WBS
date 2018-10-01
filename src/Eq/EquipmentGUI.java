package Eq;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.AnimationTiming;
import Other.Button;
import Other.Gamecol;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EquipmentGUI extends Equipment {

    private float x, y, w, h;
    private float ch;
    int margin = 16;

    private Button hideButton;
    private float tx;
    private boolean isAnimating = false;
    private boolean isVisible = true;
    private AnimationTiming animation;

    int bw = 100, bh = 30;

    public EquipmentGUI(Creature c) {
        super(c);
        pushItem(new Sword(c));

        ch = 28;

        w = 320;
        h = 180 + backpack.length*(ch+8);
        x = MainClass.WW - w - MainClass.margin;
        y = MainClass.margin;

        this.hideButton = new Button(x+w-bw,y-bh, bw, bh, "Schowaj");
        this.hideButton.setRX(hideButton.getX()+bw);

        animation = new AnimationTiming(300, AnimationTiming.TimingFun.ease, AnimationTiming.RepeatableFun.norepeat);

        tx = w + MainClass.margin;
    }

    public void update(float et) {
        if(isAnimating) {
            if(!animation.update()) {
                isAnimating = false;

                if(animation.getProgress() == 1.0f) {
                    isVisible = false;
                }
            }
        }
    }

    public void render(Graphics g) {
        g.translate((int)(tx*animation.getProgress()), 0);

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

        g.translate(-(int)(tx*animation.getProgress()), 0);

        g.translate((int)((64-hideButton.getH()/2)*animation.getProgress()), 0);
        hideButton.rotateTo(-animation.getProgress() * (float)Math.PI / 2.0f);
        hideButton.render(g);
        g.translate(-(int)((64-hideButton.getH()/2)*animation.getProgress()), 0);
    }

    private void show() {
        isVisible = true;
        hideButton.setCaption("Schowaj");
        animation.back();
        if(!isAnimating) {
            isAnimating = true;
            animation.start();
        }
    }

    private void hide() {

        hideButton.setCaption("Poka¿");
        animation.front();
        if(!isAnimating) {
            isAnimating = true;
            animation.start();
        }
    }

    public void mouseMoved(MouseEvent e) {
        hideButton.hover(e.getX()-(int)((64-hideButton.getH()/2)*animation.getProgress()), e.getY());
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1) {
            if (hideButton.mouseOver(e.getX()-(int)((64-hideButton.getH()/2)*animation.getProgress()), e.getY())) {
                if (isVisible) {
                    hide();
                } else {
                    show();
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }
}

package Eq;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Button;
import Other.Gamecol;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EquipmentGUI extends Equipment {

    private Button hideButton;
    private float x, y, w, h;

    public EquipmentGUI(Creature c) {
        super(c);

        w = 320;
        h = 600;
        x = MainClass.WW - w - 64;
        y = 64;

        int bw = 100, bh = 30;
        this.hideButton = new Button(x+w-bw,y-bh, bw, bh, "Schowaj");
    }

    public void render(Graphics g) {
        g.setColor(Gamecol.DARK_BROWN);

        g.fillRect((int)x, (int)y, (int)w, (int)h);

        hideButton.render(g);
    }

    public void mouseMoved(MouseEvent e) {
        hideButton.hover(e.getX(), e.getY());
    }
}

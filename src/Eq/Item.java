package Eq;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {

    protected Category cat;
    protected String name;
    protected BufferedImage icon;

    public enum Category {
        normal,
        weapon,
        support
    }

    public Item(Category cat, String name, BufferedImage icon) {
        this.cat = cat;
        this.name = name;
        this.icon = icon;
    }

    public void render(Graphics g) {

    }

    public void use() { System.out.println("To override use method from Item"); }
    public int useInt() { System.out.println("To override useInt method from Item"); return -1; }

    public boolean canBeUsed() {

        return true;
    }
}

package Eq;

import Creatures.Creature;

import java.awt.*;

public class Equipment {

    private Creature c;

    public Item weapon;
    private Item[] backpack = new Item[10];

    private Button closeButton;

    public Equipment(Creature c) {
        this.c = c;
        this.closeButton = new Button("Open");

        this.weapon = new Sword(c);
    }

    public void update(float et) {

    }

    public void render(Graphics g) {

    }

    public void H_PrintBackpack() {
        System.out.println("=====================================");
        System.out.println("Backpack:");
        System.out.println("Weapon: "+weapon.name);

        for(int i=0; i<backpack.length; i++) {
            System.out.print("Backpack " + i + "> ");
            if(backpack[i] != null) {
                System.out.println(backpack[i].name);
            }else {
                System.out.println("---");
            }
        }

        System.out.println("=====================================");
    }

    public boolean pushItem(Item it) {
        for(int i=0; i<backpack.length; i++) {
            if(backpack[i] == null) {
                backpack[i] = it;
                return true;
            }
        }

        return false;
    }

    public Item copyItem(int i) {
        if(i < backpack.length) {
            return backpack[i];
        }

        return null;
    }

    public Item popItem(int i) {
        if(i < backpack.length) {
            Item temp = backpack[i];
            backpack[i] = null;
            return temp;
        }

        return null;
    }
}

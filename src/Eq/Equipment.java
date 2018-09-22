package Eq;

import Creatures.Creature;

public class Equipment {

    protected Creature c;

    public Item mainHand;
    public Item secondHand;

    protected Item[] backpack = new Item[10];

    protected Item[] armor = new Item[6];
    /* 0 - Head
     * 1 - Body
     * 2 - Left hand
     * 3 - Right hand
     * 4 - Left leg
     * 5 - Right leg
     */

    public Equipment(Creature c) {
        this.c = c;

        this.mainHand = new Sword(c);
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

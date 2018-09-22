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
//
//    public void H_PrintBackpack() {
//        System.out.println("=====================================");
//        System.out.println("Backpack:");
//        System.out.println("Weapon: "+weapon.name);
//
//        for(int i=0; i<backpack.length; i++) {
//            System.out.print("Backpack " + i + "> ");
//            if(backpack[i] != null) {
//                System.out.println(backpack[i].name);
//            }else {
//                System.out.println("---");
//            }
//        }
//
//        System.out.println("=====================================");
//    }

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

package Eq;

import Character.Dice;
import Creatures.Creature;
import MainFiles.MainClass;

public class Sword extends Item {

    private Creature c;

    public Sword(Creature c) {
        super(Category.weapon, "Miecz jednorêczny", null);
        this.c = c;
        this.icon = MainClass.tex.sword;
    }

    public int useInt() {
        return Dice.roll1d10() + c.att.getS();
    }
}

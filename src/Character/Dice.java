package Character;

import java.util.Random;

public class Dice {
	
	private static Random r = new Random();
	
	public static int rolld100() { return r.nextInt(100) + 1; }
	
	public static int roll1d5() { return r.nextInt(5) + 1; }
	
	public static int roll1d10() { return r.nextInt(10) + 1; }
	public static int roll2d10() { return roll1d10() + roll1d10(); }
	public static int roll3d10() { return roll2d10() + roll1d10(); }
	
}

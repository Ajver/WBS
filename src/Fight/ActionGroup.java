package Fight;

import java.awt.Graphics;
import java.util.LinkedList;

public class ActionGroup {
	
	private float x, y;
	private LinkedList<Action> actions = new LinkedList<Action>();
	
	public ActionGroup(float x, float y, Action... act) {
		this.x = x;
		this.y = y;
		for(Action a : act) {
			actions.add(a);
		}
	}
	
	public void update(float et) {
		
	}
	
	public void render(Graphics g) {
		
	}
}	

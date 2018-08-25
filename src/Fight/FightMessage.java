package Fight;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.LinkedList;

public class FightMessage {

	private float x, y, w, h;
	
	private String msg = "";
	
	public FightMessage(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.drawRect((int)x, (int)y, (int)w, (int)h);
		
		FontMetrics f = g.getFontMetrics();
		
		if(f.stringWidth(msg) > w) {
			LinkedList<String> lines = new LinkedList<String>();
			LinkedList<String> words = new LinkedList<String>();
			
			String word = "";
			for(int i=0; i<msg.length(); i++) {
				if(msg.charAt(i) == ' ') {
					if(word != "") {
						words.add(word);						
						word = "";
					}
				}else {
					word += msg.charAt(i);
				}
			}
			if(word != "") {
				words.add(word);
			}
			
			lines.add(words.get(0));
			
			int lineNr = 0;
			int counter = 1;
			
			while(counter < words.size()) {
				boolean shouldContinue = true;
				while(shouldContinue) {
					lines.set(lineNr, lines.get(lineNr) + ' ' + words.get(counter));
					counter++;
					
					if(lineNr < lines.size() && counter < words.size()) {
						shouldContinue = f.stringWidth(lines.get(lineNr) + words.get(counter)) < w;
					}else {
						shouldContinue = false;
					}
				}	
				if(counter < words.size()) {
					lines.add(words.get(counter));
					lineNr++;
					counter++;
				}
			}
			
			lineNr = 0;
			for(String line : lines) {
				int sx = (int)(x + (w - f.stringWidth(line)) / 2.0f);
				int sy = (int)(y + h/2.0f + f.getDescent() + lineNr*40);
				
				g.drawString(line, sx, sy);
				lineNr++;
			}
		}else {
			int sx = (int)(x + (w - f.stringWidth(msg)) / 2.0f);
			int sy = (int)(y + h/2.0f + f.getDescent());
			
			g.drawString(msg, sx, sy);
		}		
	}
	
	public void set(String msg) { this.msg = msg; }
	public void add(String msg) { this.msg += ' ' + msg; }
}

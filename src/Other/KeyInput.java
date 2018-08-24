package Other;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import MainFiles.MainClass;


public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private MainClass m;
	
	public KeyInput(Handler handler, MainClass m) {
		this.handler = handler;
		this.m = m;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
}	

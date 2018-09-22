package MainFiles;

import javax.swing.JFrame;

public class Window {
	
	public Window(String title, MainClass m) {
		JFrame f = new JFrame(title);
		
		f.setUndecorated(true);
		f.setAlwaysOnTop(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(m);
		f.pack();
		f.setVisible(true);
		f.setResizable(false);
		
		f.setSize(MainClass.WW, MainClass.WH);

		m.start();
	}
}

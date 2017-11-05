import javax.swing.SwingUtilities;

import Graphic.IntroFrame;

class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new IntroFrame();
			}
		});
	}
}

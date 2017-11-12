package com.bfpaul.chess.timer;

import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;

public class TimerTest {
	public static void main(String[] args) {
		FlatFrame frame = new FlatFrame();
		
		frame.setSize(600, 300);
		frame.setLocationOnScreenCenter();
		frame.getContainer().setLayout(new LinearLayout(Orientation.HORIZONTAL));
		
		frame.show();
	}
}

package com.bfpaul.chess.timer;

import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class TimerTest {
	public static void main(String[] args) {
		FlatFrame frame = new FlatFrame();
		Timer timer = new Timer();
		
		frame.setSize(600, 300);
		frame.setLocationOnScreenCenter();
		frame.getContainer().setLayout(new LinearLayout(Orientation.HORIZONTAL));
		frame.getContainer().add(timer, new LinearConstraints(1, LinearSpace.MATCH_PARENT));

		timer.start();
		
		frame.show();
	}
}

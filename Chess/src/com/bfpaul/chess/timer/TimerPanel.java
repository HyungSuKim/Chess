package com.bfpaul.chess.timer;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;


@SuppressWarnings("serial")
class TimerPanel extends FlatPanel {

	private boolean isWhite;
	
	private FlatLabel timerLabel = createTimerLabel();
	
	private Timer timer;

	TimerPanel(boolean isWhite) {
//		System.out.println(isWhite);
		this.isWhite = isWhite;
		timer = new Timer(this);
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		
		add(createTimerInfo(), createMatchParentConstraints(8));
		add(timerLabel, createMatchParentConstraints(15));
	}

	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
	private FlatLabel createTimerInfo() {
		FlatLabel timerInfo = new FlatLabel();
		
		if(isWhite) {
			timerInfo.setText("화이트(분:초)");
		} else {
			timerInfo.setText("블랙(분:초)");
		}
		
		timerInfo.setOpaque(false);
		timerInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		timerInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		timerInfo.setFont(Theme.BOLD_FONT_15PT);
		return timerInfo;
	}
	
	private FlatLabel createTimerLabel() {
		FlatLabel timerLabel = new FlatLabel();
		timerLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		timerLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		timerLabel.setBackground(Theme.LIGHT_BLUE_COLOR);
		timerLabel.setFont(Theme.BOLD_FONT_30PT);
		return timerLabel;
	}
	
	FlatLabel getTimerLabel() {
		return timerLabel;
	}
	
	boolean getIsWhite() {
		return isWhite;
	}
	
	Timer getTimer() {
		return timer;
	}
}

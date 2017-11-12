package com.bfpaul.chess.timer;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;

@SuppressWarnings("serial")
public class GameTimerPanel extends FlatPanel {
	
	private FlatButton phaseEndButton = createPhaseEndButton();
	
	private TimerView whiteTimer = new TimerView(true);
	private TimerView blackTimer = new TimerView(false);
	
	public GameTimerPanel() {
		setLayout(new LinearLayout(10));
		setBackground(Theme.DARK_BLUE_COLOR);
		setOpaque(true);
		add(whiteTimer, createMatchParentConstraints(3));
		add(blackTimer, createMatchParentConstraints(3));
		add(createPhaseEndButtonPanel(), createMatchParentConstraints(1));
	}
	
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
	private FlatPanel createPhaseEndButtonPanel() {
		FlatPanel phaseEndButtonPanel = new FlatPanel(new LinearLayout(Orientation.VERTICAL, 0));
		phaseEndButtonPanel.add(createPhaseEndButtonInfo(), createMatchParentConstraints(8));
		phaseEndButtonPanel.add(phaseEndButton, createMatchParentConstraints(15));
		return phaseEndButtonPanel;
	}
	
	private FlatLabel createPhaseEndButtonInfo() {
		FlatLabel phaseEndButtonInfo = new FlatLabel("Â÷·Ê");
		phaseEndButtonInfo.setOpaque(false);
		phaseEndButtonInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		phaseEndButtonInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		phaseEndButtonInfo.setFont(Theme.BOLD_FONT_15PT);
		return phaseEndButtonInfo;
	}
	
	private FlatButton createPhaseEndButton() {
		FlatButton phaseEndButton = new FlatButton("Á¾·á");
		phaseEndButton.setBackground(Theme.YELLOW_COLOR);
		return phaseEndButton;
	}
}

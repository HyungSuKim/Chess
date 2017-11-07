package com.bfpaul.chess.timer;

import java.awt.Component;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;


@SuppressWarnings("serial")
public class Timer extends FlatPanel implements Runnable {
	private FlatLabel whiteTimerLabel = createWhiteTimerLabel();

	private FlatLabel blackTimerLabel = createBlackTimerLabel();

	private static Properties whiteTimer = new Properties();
	private static Properties blackTimer = new Properties();

	private FlatButton phaseEndButton = createPhaseEndButton();
	
	Thread thread;
	boolean stopped;
	boolean whitePhase;

	Timer() {
		thread = new Thread(this);
		
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		setBackground(Theme.DARK_BLUE_COLOR);
		setOpaque(true);

		add(createTimerInfoView(), createCommonConstraints(8));
		add(createTimerPanel(), createCommonConstraints(15));
	}

	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	private FlatPanel createTimerInfoView() {
		FlatPanel timerInfoView = new FlatPanel(new LinearLayout(10));

		timerInfoView.add(createWhiteTimerInfo(), createCommonConstraints(3));

		timerInfoView.add(createBlackTimerInfo(), createCommonConstraints(3));

		timerInfoView.add(createPhaseEndButtonInfo(), createCommonConstraints(1));

		return timerInfoView;
	}

	private FlatPanel createTimerPanel() {
		FlatPanel timerPanel = new FlatPanel(new LinearLayout(10));

		timerPanel.add(whiteTimerLabel, createCommonConstraints(3));

		timerPanel.add(blackTimerLabel, createCommonConstraints(3));

		timerPanel.add(phaseEndButton, createCommonConstraints(1));

		return timerPanel;
	}

	private FlatLabel createWhiteTimerInfo() {
		FlatLabel whiteTimerInfo = new FlatLabel("화이트(분:초)");
		whiteTimerInfo.setOpaque(false);
		whiteTimerInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		whiteTimerInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		whiteTimerInfo.setFont(Theme.BOLD_FONT_15PT);
		return whiteTimerInfo;
	}

	private FlatLabel createBlackTimerInfo() {
		FlatLabel blackTimerInfo = new FlatLabel("블랙(분:초)");
		blackTimerInfo.setOpaque(false);
		blackTimerInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		blackTimerInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		blackTimerInfo.setFont(Theme.BOLD_FONT_15PT);
		return blackTimerInfo;
	}

	private FlatLabel createPhaseEndButtonInfo() {
		FlatLabel phaseEndButtonInfo = new FlatLabel("차례");
		phaseEndButtonInfo.setOpaque(false);
		phaseEndButtonInfo.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		phaseEndButtonInfo.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		phaseEndButtonInfo.setFont(Theme.BOLD_FONT_15PT);
		return phaseEndButtonInfo;
	}

	private FlatLabel createBlackTimerLabel() {
		FlatLabel blackTimerLabel = new FlatLabel(blackTimer.toString());
		blackTimerLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		blackTimerLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		blackTimerLabel.setBackground(Theme.LIGHT_BLUE_COLOR);
		blackTimerLabel.setFont(Theme.BOLD_FONT_30PT);
		return blackTimerLabel;
	}

	private FlatLabel createWhiteTimerLabel() {
		FlatLabel whiteTimerLabel = new FlatLabel(whiteTimer.toString());
		whiteTimerLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		whiteTimerLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		whiteTimerLabel.setBackground(Theme.LIGHT_BLUE_COLOR);
		whiteTimerLabel.setFont(Theme.BOLD_FONT_30PT);
		return whiteTimerLabel;
	}
	
//	private void setWhiteTimer() {
//		whiteTimer.run();
//		whiteTimerLabel.setText(whiteTimer.toString());
//	}
//
//	private void setBlackTimer() {
////		blackTimerLabel.setText(game.getBlackPlayer().getTimer().toString());
//	}

	private FlatButton createPhaseEndButton() {
		FlatButton phaseEndButton = new FlatButton("종료");
		phaseEndButton.setBackground(Theme.YELLOW_COLOR);
		phaseEndButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				whitePhase = !whitePhase;
			}
		});
		return phaseEndButton;
	}

	@Override
	public void run() {
		while(!stopped) {
			if(!whitePhase) {
				whiteTimer.timeGoes();
				whiteTimerLabel.setText(whiteTimer.toString());
			} else {
				blackTimer.timeGoes();
				blackTimerLabel.setText(blackTimer.toString());
			}
		}
	}
	
//	public void suspend() { whitePhase = true; }
//	public void resume() { whitePhase = false; }
	public void start() { thread.start(); }
}

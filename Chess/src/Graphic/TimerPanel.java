package Graphic;

import java.awt.Component;

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

import Manager.Game;

@SuppressWarnings("serial")
class TimerPanel extends FlatPanel {

	private static FlatLabel whiteTimerLabel = createWhiteTimerLabel();

	private static FlatLabel blackTimerLabel = createBlackTimerLabel();

	private FlatButton phaseEndButton = createPhaseEndButton();

	private Game game;

	TimerPanel(Game game) {
		this.game = game;
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));

		add(createTimerInfoView(), createCommonConstraints(8));
		add(createTimerPanel(), createCommonConstraints(15));
		setWhiteTimer();
		setBlackTimer();
		// while(game.getWhitePlayer().getPhase()) {
		// game.getWhitePlayer().getTimer().timeGoes();
		// setWhiteTimer();
		// }
	}

	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	private FlatPanel createTimerInfoView() {
		FlatPanel timerInfoView = new FlatPanel(new LinearLayout(10));
		timerInfoView.setBackground(Theme.DARK_BLUE);
		timerInfoView.setOpaque(true);

		timerInfoView.add(createWhiteTimerInfo(), createCommonConstraints(3));

		timerInfoView.add(createBlackTimerInfo(), createCommonConstraints(3));

		timerInfoView.add(createPhaseEndButtonInfo(), createCommonConstraints(1));

		return timerInfoView;
	}

	private FlatPanel createTimerPanel() {
		FlatPanel timerPanel = new FlatPanel(new LinearLayout(10));
		timerPanel.setBackground(Theme.DARK_BLUE);
		timerPanel.setOpaque(true);

		timerPanel.add(whiteTimerLabel, createCommonConstraints(3));

		timerPanel.add(blackTimerLabel, createCommonConstraints(3));

		timerPanel.add(phaseEndButton, createCommonConstraints(1));

		return timerPanel;
	}

	private void setWhiteTimer() {
		whiteTimerLabel.setText(game.getWhitePlayer().getTimer().toString());

	}

	private void setBlackTimer() {
		blackTimerLabel.setText(game.getBlackPlayer().getTimer().toString());
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

	private static FlatLabel createBlackTimerLabel() {
		FlatLabel blackTimerLabel = new FlatLabel();
		blackTimerLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		blackTimerLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		blackTimerLabel.setBackground(Theme.LIGHT_BLUE);
		return blackTimerLabel;
	}

	private static FlatLabel createWhiteTimerLabel() {
		FlatLabel whiteTimerLabel = new FlatLabel();
		whiteTimerLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		whiteTimerLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		whiteTimerLabel.setBackground(Theme.LIGHT_BLUE);
		return whiteTimerLabel;
	}

	private FlatButton createPhaseEndButton() {
		FlatButton phaseEndButton = new FlatButton("종료");
		phaseEndButton.setBackground(Theme.YELLOW);
		phaseEndButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				game.phaseEnd();
			}
		});
		return phaseEndButton;
	}
}

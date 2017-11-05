package Graphic;

import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.util.ScreenManager;

import Manager.Game;

class GameFrame {

	private static Game game = new Game();

	GameFrame() {
		FlatFrame frame = createFrame();
		FlatPanel board = new Board(game);

		frame.getContainer().add(createRelatedInfoPanel(), createCommonConstraints(2));
		frame.getContainer().add(board, createCommonConstraints(10));

		frame.show();
	}

	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	private FlatFrame createFrame() {
		FlatFrame frame = new FlatFrame();
		ScreenManager screenManager = ScreenManager.getInstance();
		frame.setTitle("Chess Game");
		frame.setSize(screenManager.dip2px(500), screenManager.dip2px(530));
		frame.getContainer().setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		frame.setLocationOnScreenCenter();
		frame.setIconImage(Images.ICON);
		frame.setProcessIconImage(Images.ICON);
		frame.setTitleBarColor(Theme.TITLE_BAR_COLOR);
		frame.setResizable(false);
		frame.setWindowExit(true);
		return frame;
	}

	private FlatPanel createRelatedInfoPanel() {
		FlatPanel relatedInfoView = new FlatPanel(new LinearLayout(5));
		FlatPanel timerAndButtonPanel = new FlatPanel(new LinearLayout(Orientation.VERTICAL, 5));
		relatedInfoView.setBackground(Theme.DARK_BLUE);
		relatedInfoView.setOpaque(true);

		relatedInfoView.add(new CurrentChessmanView(game), createCommonConstraints(4));
		relatedInfoView.add(timerAndButtonPanel, createCommonConstraints(3));
		timerAndButtonPanel.add(new TimerPanel(game), createCommonConstraints(2));
		timerAndButtonPanel.add(createButtonPanel(), createCommonConstraints(1));
		return relatedInfoView;
	}

	private FlatPanel createButtonPanel() {
		FlatPanel buttonPanel = new FlatPanel(new LinearLayout(0));
		buttonPanel.setBackground(Theme.DARK_BLUE);
		buttonPanel.setOpaque(true);

		buttonPanel.add(createRuleDescriptionButton(), createCommonConstraints(1));
		buttonPanel.add(createGiveUpButton(), createCommonConstraints(1));
		return buttonPanel;
	}

	private FlatButton createRuleDescriptionButton() {
		FlatButton ruleDescriptionButton = new FlatButton("규칙 설명");
		ruleDescriptionButton.setBackground(Theme.LIGHT_BLUE);
		return ruleDescriptionButton;
	}

	private FlatButton createGiveUpButton() {
		FlatButton giveUpButton = new FlatButton("기 권");
		giveUpButton.setBackground(Theme.YELLOW);
		return giveUpButton;
	}
}

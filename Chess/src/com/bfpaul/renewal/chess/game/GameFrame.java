package com.bfpaul.renewal.chess.game;

import java.awt.Component;
import java.io.IOException;

import javax.swing.border.EmptyBorder;

import com.bfpaul.renewal.chess.Images;
import com.bfpaul.renewal.chess.Theme;
import com.bfpaul.renewal.chess.board.BoardPanel;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.timer.GameTimerView;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.frame.FlatDialog;
import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.util.ScreenManager;

public class GameFrame {
	private FlatFrame frame = createFrame();
	private CurrentChessmanView currentChessmanView = new CurrentChessmanView();
	private GameHelper gameHelper = new GameHelper(currentChessmanView);
	private GameTimerView gameTimerView = new GameTimerView(gameHelper);
	
	public GameFrame(boolean isWhite) {
		
		frame.getContainer().add(createRelatedInfoPanel(), createCommonConstraints(2));
		frame.getContainer().add(new BoardPanel(gameHelper, isWhite), createCommonConstraints(10));
		
		if(isWhite) {
			frame.setLocation(0, 0);
		} else {
			frame.setLocation(950, 0);
		}
		
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
		
		relatedInfoView.setBackground(Theme.DARK_BLUE_COLOR);
		relatedInfoView.setOpaque(true);
		
		relatedInfoView.add(currentChessmanView, createCommonConstraints(4));
		relatedInfoView.add(timerAndButtonPanel, createCommonConstraints(3));
		timerAndButtonPanel.add(gameTimerView, createCommonConstraints(2));
		timerAndButtonPanel.add(createButtonPanel(), createCommonConstraints(1));
		return relatedInfoView;
	}
	
	private FlatPanel createButtonPanel() {
		FlatPanel buttonPanel = new FlatPanel(new LinearLayout(0));
		buttonPanel.setBackground(Theme.DARK_BLUE_COLOR);
		buttonPanel.setOpaque(true);
		
		buttonPanel.add(createRuleDescriptionButton(), createCommonConstraints(1));
		buttonPanel.add(createGiveUpButton(), createCommonConstraints(1));
		return buttonPanel;
	}
	
	private FlatButton createRuleDescriptionButton() {
		FlatButton ruleDescriptionButton = new FlatButton("규칙 설명");
		ruleDescriptionButton.setBackground(Theme.LIGHT_BLUE_COLOR);
		ruleDescriptionButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		ruleDescriptionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				Runtime runtime = Runtime.getRuntime();
				
				try {
					runtime.exec("explorer.exe https://ko.wikipedia.org/wiki/%EC%B2%B4%EC%8A%A4");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		return ruleDescriptionButton;
	}
	
	private FlatButton createGiveUpButton() {
		FlatButton giveUpButton = new FlatButton("기 권");
		giveUpButton.setBackground(Theme.YELLOW_COLOR);
		giveUpButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		giveUpButton.setOnClickListener(new OnClickListener() {
			
			private String giveUpPlayer;
			private String winngPlayer;
			
			public void onClick(Component component) {
				
				if(gameHelper.getPlayerColor()) {
					giveUpPlayer = "흰색 플레이어";
					winngPlayer = "검은색 플레이어";
				} else {
					giveUpPlayer = "검은색 플레이어";
					winngPlayer = "흰색 플레이어";
				}
				
				new FlatDialog.Builder()
				.setTitle("기권").setTitleBackgroundColor(Theme.LIGHT_BLUE_COLOR)
				.setContent(giveUpPlayer + "가 기권하였습니다." + System.lineSeparator()
				+ winngPlayer + "승리!" + System.lineSeparator() + "확인 시 게임을 종료합니다.")
				.setContentBackgroundColor(Theme.LIGHT_BLUE_COLOR)
				.setButtonAreaBackgroundColor(Theme.LIGHT_BLUE_COLOR)
				.setButtonBackgroundColor(Theme.LIGHT_BLUE_COLOR)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(Component component) {
						frame.hide();
					}
					
				}).setLocationRelativeTo(frame.getContainer()).build().show();
				
			}
		});
		return giveUpButton;
	}

}

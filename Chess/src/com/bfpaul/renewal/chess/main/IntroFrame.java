package com.bfpaul.renewal.chess.main;

import java.awt.Component;

import javax.swing.border.EmptyBorder;

import com.bfpaul.renewal.chess.Images;
import com.bfpaul.renewal.chess.Theme;
import com.bfpaul.renewal.chess.game.GameFrame;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.util.ScreenManager;

// 체스프로그램의 시작화면으로써 게임을 혼자할지 두명이서 할지 선택하게한다. 
class IntroFrame {
	
	IntroFrame() {
		FlatFrame frame = createFrame();
		
		frame.getContainer().add(createImageView(), createMatchParentConstraints(3));
		
		frame.getContainer().add(createSinglePlayButton(), createMatchParentConstraints(1));
		
		frame.getContainer().add(createDoublePlayButton(), createMatchParentConstraints(1));
		
		frame.show();
	}
//	정해진 무게만큼의 영역을 차지하고 부모의 영역에 영역을 맞춰주는 제약조건을 반환한다.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
//	프레임을 만들어준다
	private FlatFrame createFrame() {
		FlatFrame frame = new FlatFrame();
		ScreenManager screenManager = ScreenManager.getInstance();
		frame.setTitle("Chess Intro");
		frame.setSize(screenManager.dip2px(250), screenManager.dip2px(350));
		frame.getContainer().setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		frame.setLocationOnScreenCenter();
		frame.setIconImage(Images.ICON);
		frame.setProcessIconImage(Images.ICON);
		frame.setTitleBarColor(Theme.TITLE_BAR_COLOR);
		frame.setResizable(false);
		frame.setWindowExit(true);
		return frame;
	}
//	인트로에 사용되는 이미지를 보여준다
	private FlatImagePanel createImageView() {
		return new FlatImagePanel(Images.INTRO, ImageOption.MATCH_PARENT);
	}
//	혼자 할 수 있는 게임을 생성하게 하는 버튼이다.
	private FlatButton createSinglePlayButton() {
		FlatButton singlePlayButton = new FlatButton("혼자하기");
		singlePlayButton.setBackground(Theme.LIGHT_BLUE_COLOR);
		singlePlayButton.setBorder(new EmptyBorder(0,0,0,0));
		
		return singlePlayButton;
	}
//	둘이서 할 수 있는 게임을 생성하게 하는 버튼이다. 
	private FlatButton createDoublePlayButton() {
		FlatButton doublePlayButton = new FlatButton("둘이하기");
		doublePlayButton.setBackground(Theme.DARK_BLUE_COLOR);
		doublePlayButton.setBorder(new EmptyBorder(0,0,0,0));
		doublePlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(Component component) {
				new GameFrame(true);
				new GameFrame(false);
			}
		});
		
		return doublePlayButton;
	}
}
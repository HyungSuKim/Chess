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

// ü�����α׷��� ����ȭ�����ν� ������ ȥ������ �θ��̼� ���� �����ϰ��Ѵ�. 
class IntroFrame {
	
	IntroFrame() {
		FlatFrame frame = createFrame();
		
		frame.getContainer().add(createImageView(), createMatchParentConstraints(3));
		
		frame.getContainer().add(createSinglePlayButton(), createMatchParentConstraints(1));
		
		frame.getContainer().add(createDoublePlayButton(), createMatchParentConstraints(1));
		
		frame.show();
	}
//	������ ���Ը�ŭ�� ������ �����ϰ� �θ��� ������ ������ �����ִ� ���������� ��ȯ�Ѵ�.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
//	�������� ������ش�
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
//	��Ʈ�ο� ���Ǵ� �̹����� �����ش�
	private FlatImagePanel createImageView() {
		return new FlatImagePanel(Images.INTRO, ImageOption.MATCH_PARENT);
	}
//	ȥ�� �� �� �ִ� ������ �����ϰ� �ϴ� ��ư�̴�.
	private FlatButton createSinglePlayButton() {
		FlatButton singlePlayButton = new FlatButton("ȥ���ϱ�");
		singlePlayButton.setBackground(Theme.LIGHT_BLUE_COLOR);
		singlePlayButton.setBorder(new EmptyBorder(0,0,0,0));
		
		return singlePlayButton;
	}
//	���̼� �� �� �ִ� ������ �����ϰ� �ϴ� ��ư�̴�. 
	private FlatButton createDoublePlayButton() {
		FlatButton doublePlayButton = new FlatButton("�����ϱ�");
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
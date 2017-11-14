package com.bfpaul.chess.game;

import java.awt.Image;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.chess.chessman.ChessmanImage;
import com.bfpaul.chess.chessman.ChessmanType;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;
//���� ������ ���� ����ִ� ü�������� �����ִ�(View)�ν��� ������ �ϴ� �г��̴�.
//�������� ������������ ��Ȳ�� �̹����� ���ڷν� ǥ�����ش�.
@SuppressWarnings("serial")
class CurrentChessmanView extends FlatPanel {
//	���� ü������Ȳ�� �並 �������ִ� �����ڷ� ü������Ȳ���� ������ ���Ե� ���������� ���� ��ܿ���ġ�ϰ�
//	�� ������ �Ͼḻ��� ���������� ��Ȳ�� ������� �並 ��ġ�Ͽ���.
	CurrentChessmanView() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		setBackground(Theme.LIGHT_BLUE_COLOR);
		setOpaque(true);
		
		add(createViewInfoLabel(), createMatchParentConstraints(1));
		add(createChessmanView(true), createMatchParentConstraints(2));
		add(createChessmanView(false), createMatchParentConstraints(2));
	}
	
//	������ ���Ը�ŭ �׸��� �θ��� ũ�� ��ŭ ������ �����ϰ��ϴ� ���������� ��ȯ�Ѵ�.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints(weight, LinearSpace.MATCH_PARENT);
	}
	
//	ü������ �ʱ� ������ �޾Ƽ� ǥ�����ش�.
	static private FlatLabel createCountLabel(int initCount) {
		FlatLabel countLabel = new FlatLabel("X" + initCount);
		countLabel.setOpaque(false);
		countLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		countLabel.setFont(Theme.BOLD_FONT_15PT);
		return countLabel;
	}

//	CurrentChessmanView�� ��� ������ ������ ��Ÿ���ִ� ���� �������ִ� �޼����̴�.
//	CurrentChessmanView�� LightBlueColor�� ����ϰ��ִµ� ������ �ΰ����ֱ����� DarkBlueColor�� �������־���.
	private FlatLabel createViewInfoLabel() {
		FlatLabel viewInfoLabel = new FlatLabel("ü���� ��Ȳ");
		
		viewInfoLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		viewInfoLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		viewInfoLabel.setFont(Theme.BOLD_FONT_15PT);
		viewInfoLabel.setBackground(Theme.DARK_BLUE_COLOR);
		
		return viewInfoLabel;
	}
	
//	������� �ƴ��� ������ �޾Ƽ� ������� ��Ȳ Ȥ�� ������ ���� ��Ȳ�� �������ش�.
	private FlatPanel createChessmanView(boolean isWhite) {
		FlatPanel chessmanView = new FlatPanel(new LinearLayout(0));
		chessmanView.setOpaque(false);
		
		ChessmanType[] chessmanType = ChessmanType.values();
		
		for(ChessmanType type : chessmanType) {
			chessmanView.add(createChessmanImageView(ChessmanImage.getChessmanImage(isWhite, type))
					, createMatchParentConstraints(2));
			chessmanView.add(createCountLabel(type.getInitCount()), createMatchParentConstraints(1));
		}
		return chessmanView;
	}

//	�Ķ���ͷ� ���� �̹����� ���� ü������ �̹����� ���� �г��� ���� ��ȯ���ش�.
	private FlatImagePanel createChessmanImageView(Image image) {
		FlatImagePanel chessmanImagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		chessmanImagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		chessmanImagePanel.setOpaque(false);
		return chessmanImagePanel;
	}
}

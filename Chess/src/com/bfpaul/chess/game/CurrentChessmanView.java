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
//게임 진행중 현재 살아있는 체스말들을 보여주는(View)로써의 역할을 하는 패널이다.
//흰색말들과 검정색말들의 현황을 이미지와 숫자로써 표현해준다.
@SuppressWarnings("serial")
class CurrentChessmanView extends FlatPanel {
//	현재 체스말현황의 뷰를 생성해주는 생성자로 체스말현황뷰의 설명이 포함된 뷰인포라벨을 가장 상단에배치하고
//	그 밑으로 하얀말들과 검정말들의 현황을 보여쥬는 뷰를 배치하였다.
	CurrentChessmanView() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		setBackground(Theme.LIGHT_BLUE_COLOR);
		setOpaque(true);
		
		add(createViewInfoLabel(), createMatchParentConstraints(1));
		add(createChessmanView(true), createMatchParentConstraints(2));
		add(createChessmanView(false), createMatchParentConstraints(2));
	}
	
//	설정된 무게만큼 그리고 부모의 크기 만큼 영역을 차지하게하는 제약조건을 반환한다.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints(weight, LinearSpace.MATCH_PARENT);
	}
	
//	체스말의 초기 갯수를 받아서 표현해준다.
	static private FlatLabel createCountLabel(int initCount) {
		FlatLabel countLabel = new FlatLabel("X" + initCount);
		countLabel.setOpaque(false);
		countLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		countLabel.setFont(Theme.BOLD_FONT_15PT);
		return countLabel;
	}

//	CurrentChessmanView가 어떠한 뷰인지 정보를 나타내주는 라벨을 생성해주는 메서드이다.
//	CurrentChessmanView가 LightBlueColor를 사용하고있는데 정보를 부각해주기위해 DarkBlueColor를 설정해주었다.
	private FlatLabel createViewInfoLabel() {
		FlatLabel viewInfoLabel = new FlatLabel("체스말 현황");
		
		viewInfoLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		viewInfoLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		viewInfoLabel.setFont(Theme.BOLD_FONT_15PT);
		viewInfoLabel.setBackground(Theme.DARK_BLUE_COLOR);
		
		return viewInfoLabel;
	}
	
//	흰색인지 아닌지 정보를 받아서 흰색말의 현황 혹은 검정색 말의 현황을 생성해준다.
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

//	파라미터로 받은 이미지를 통해 체스말의 이미지를 갖는 패널을 만들어서 반환해준다.
	private FlatImagePanel createChessmanImageView(Image image) {
		FlatImagePanel chessmanImagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		chessmanImagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		chessmanImagePanel.setOpaque(false);
		return chessmanImagePanel;
	}
}

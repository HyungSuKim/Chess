package com.bfpaul.chess.game;

import java.awt.Image;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.Theme;
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
//	아래의 라벨들은 각 말들의 현재 살아있는 숫자를 표현해주는 라벨이다. 우선은 게임을 하나만 생성한다고 가정했지만
//	숫자의 변화가 생겼을때 공통된 변화를 갖게 하고자해서 static으로 선언했고 추후 게임을 여러개 생성하게 변경된다면
//	static은 제외할 계획이다.
	private static FlatLabel blackKingCount = createCountLabel();
	private static FlatLabel blackQueenCount = createCountLabel();
	private static FlatLabel blackBishopCount = createCountLabel();
	private static FlatLabel blackKnightCount = createCountLabel();
	private static FlatLabel blackRookCount = createCountLabel();
	private static FlatLabel blackPawnCount = createCountLabel();
	
	private static FlatLabel whiteKingCount = createCountLabel();
	private static FlatLabel whiteQueenCount = createCountLabel();
	private static FlatLabel whiteBishopCount = createCountLabel();
	private static FlatLabel whiteKnightCount = createCountLabel();
	private static FlatLabel whiteRookCount = createCountLabel();
	private static FlatLabel whitePawnCount = createCountLabel();
	
//	현재 체스말현황의 뷰를 생성해주는 생성자로 체스말현황뷰의 설명이 포함된 뷰인포라벨을 가장 상단에배치하고
//	그 밑으로 하얀말들과 검정말들의 현황을 보여쥬는 뷰를 배치하였다.
	CurrentChessmanView() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		setBackground(Theme.LIGHT_BLUE_COLOR);
		setOpaque(true);
		
		add(createViewInfoLabel(), createCommonConstraints(1));
		add(createWhiteChessmanView(), createCommonConstraints(2));
		add(createBlackChessmanView(), createCommonConstraints(2));
	}
	
//	이 클래스에서 주로 사용하고있는 리니어 레이아웃의  공통의 제약을 지정한 메서드로써 파라미터로써 weight를 받아서 제약을 반환해준다.
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints(weight, LinearSpace.MATCH_PARENT);
	}
	
//	멤버변수인 각 말들의 CountLabel을 생성해주는 메서드로써 투명하게, 폰트는 15포인트 정중앙으로 셋팅된 라벨을 생성해준다.
	static private FlatLabel createCountLabel() {
		FlatLabel countLabel = new FlatLabel("8");
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
	
//	검정체스말들의 현황뷰를 생성해주는 메서드로써 이 메서드가 실행되면 체스말의 이미지와 X(곱하기표시)가 포함된 뷰가 생성된다.
//	포함되는 체스말의 이미지는 createXXXXView를 통해 각각의 말의 이미지를 갖는 뷰로써 생성되며 킹 퀸 비숍 나이트 룩 폰의 이미지를 갖는다.
	private FlatPanel createBlackChessmanView() {
		FlatPanel blackChessmanView = new FlatPanel(new LinearLayout(0));
		blackChessmanView.setOpaque(false);
		
		blackChessmanView.add(createSingleChessmanView(Images.BLACK_KING), createCommonConstraints(3));
		blackChessmanView.add(blackKingCount, createCommonConstraints(1));
		
		blackChessmanView.add(createSingleChessmanView(Images.BLACK_QUEEN), createCommonConstraints(3));
		blackChessmanView.add(blackQueenCount, createCommonConstraints(1));
		
		blackChessmanView.add(createSingleChessmanView(Images.BLACK_BISHOP), createCommonConstraints(3));
		blackChessmanView.add(blackBishopCount, createCommonConstraints(1));
		
		blackChessmanView.add(createSingleChessmanView(Images.BLACK_KNIGHT), createCommonConstraints(3));
		blackChessmanView.add(blackKnightCount, createCommonConstraints(1));
		
		blackChessmanView.add(createSingleChessmanView(Images.BLACK_ROOK), createCommonConstraints(3));
		blackChessmanView.add(blackRookCount, createCommonConstraints(1));
		
		blackChessmanView.add(createSingleChessmanView(Images.BLACK_PAWN), createCommonConstraints(3));
		blackChessmanView.add(blackPawnCount, createCommonConstraints(1));
		
		return blackChessmanView;
	}
//	흰색체스말들의 현황뷰를 생성해주는 메서드로써 이 메서드가 실행되면 체스말의 이미지와 X(곱하기표시)가 포함된 뷰가 생성된다.
//	포함되는 체스말의 이미지는 createXXXXView를 통해 각각의 말의 이미지를 갖는 뷰로써 생성되며 킹 퀸 비숍 나이트 룩 폰의 이미지를 갖는다.	
	private FlatPanel createWhiteChessmanView() {
		FlatPanel whiteChessmanView = new FlatPanel(new LinearLayout(0));
		
		whiteChessmanView.add(createSingleChessmanView(Images.WHITE_KING), createCommonConstraints(3));
		whiteChessmanView.add(whiteKingCount, createCommonConstraints(1));
		whiteChessmanView.add(createSingleChessmanView(Images.WHITE_QUEEN), createCommonConstraints(3));
		whiteChessmanView.add(whiteQueenCount, createCommonConstraints(1));
		whiteChessmanView.add(createSingleChessmanView(Images.WHITE_BISHOP), createCommonConstraints(3));
		whiteChessmanView.add(whiteBishopCount, createCommonConstraints(1));
		whiteChessmanView.add(createSingleChessmanView(Images.WHITE_KNIGHT), createCommonConstraints(3));
		whiteChessmanView.add(whiteKnightCount, createCommonConstraints(1));
		whiteChessmanView.add(createSingleChessmanView(Images.WHITE_ROOK), createCommonConstraints(3));
		whiteChessmanView.add(whiteRookCount, createCommonConstraints(1));
		whiteChessmanView.add(createSingleChessmanView(Images.WHITE_PAWN), createCommonConstraints(3));
		whiteChessmanView.add(whitePawnCount, createCommonConstraints(1));
		
		return whiteChessmanView;
	}
	
//	이미지를 받아서 하나의 체스맨의 이미지와 카운트 심벌을 갖는 뷰를 만들어주는 메서드이다.
//	내부적으로 createChessmanImagePanel 메서드를 통해 파라미터로 받은 이미지를 갖는 패널을 생성한뒤 더해주고
//	createCountSymbolLabel 메서드를 통해 기호가 포함된 라벨을 더해준다.
	private FlatPanel createSingleChessmanView(Image image) {
		FlatPanel chessmanView = new FlatPanel(new LinearLayout(0));
		chessmanView.setOpaque(false);
		
		chessmanView.add(createChessmanImagePanel(image), createCommonConstraints(3));
		chessmanView.add(createCountSymbolLabel(), createCommonConstraints(1));
		
		return chessmanView;
	}
	
//	파라미터로 받은 이미지를 통해 체스말의 이미지를 갖는 패널을 만들어서 반환해준다.
	private FlatImagePanel createChessmanImagePanel(Image image) {
		FlatImagePanel chessmanImagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		chessmanImagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		chessmanImagePanel.setOpaque(false);
		return chessmanImagePanel;
	}
	
//	count기호를 포함한 라벨을 생성해서 반환해주는 메서드이다.
	private FlatLabel createCountSymbolLabel() {
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.LEFT);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		return countSymbol;
	}
}

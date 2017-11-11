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
//���� ������ ���� ����ִ� ü�������� �����ִ�(View)�ν��� ������ �ϴ� �г��̴�.
//�������� ������������ ��Ȳ�� �̹����� ���ڷν� ǥ�����ش�. 
@SuppressWarnings("serial")
class CurrentChessmanView extends FlatPanel {
//	�Ʒ��� �󺧵��� �� ������ ���� ����ִ� ���ڸ� ǥ�����ִ� ���̴�. �켱�� ������ �ϳ��� �����Ѵٰ� ����������
//	������ ��ȭ�� �������� ����� ��ȭ�� ���� �ϰ����ؼ� static���� �����߰� ���� ������ ������ �����ϰ� ����ȴٸ�
//	static�� ������ ��ȹ�̴�.
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
	
//	���� ü������Ȳ�� �並 �������ִ� �����ڷ� ü������Ȳ���� ������ ���Ե� ���������� ���� ��ܿ���ġ�ϰ�
//	�� ������ �Ͼḻ��� ���������� ��Ȳ�� ������� �並 ��ġ�Ͽ���.
	CurrentChessmanView() {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		setBackground(Theme.LIGHT_BLUE_COLOR);
		setOpaque(true);
		
		add(createViewInfoLabel(), createCommonConstraints(1));
		add(createWhiteChessmanView(), createCommonConstraints(2));
		add(createBlackChessmanView(), createCommonConstraints(2));
	}
	
//	�� Ŭ�������� �ַ� ����ϰ��ִ� ���Ͼ� ���̾ƿ���  ������ ������ ������ �޼���ν� �Ķ���ͷν� weight�� �޾Ƽ� ������ ��ȯ���ش�.
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints(weight, LinearSpace.MATCH_PARENT);
	}
	
//	��������� �� ������ CountLabel�� �������ִ� �޼���ν� �����ϰ�, ��Ʈ�� 15����Ʈ ���߾����� ���õ� ���� �������ش�.
	static private FlatLabel createCountLabel() {
		FlatLabel countLabel = new FlatLabel("8");
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
	
//	����ü�������� ��Ȳ�並 �������ִ� �޼���ν� �� �޼��尡 ����Ǹ� ü������ �̹����� X(���ϱ�ǥ��)�� ���Ե� �䰡 �����ȴ�.
//	���ԵǴ� ü������ �̹����� createXXXXView�� ���� ������ ���� �̹����� ���� ��ν� �����Ǹ� ŷ �� ��� ����Ʈ �� ���� �̹����� ���´�.
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
//	���ü�������� ��Ȳ�並 �������ִ� �޼���ν� �� �޼��尡 ����Ǹ� ü������ �̹����� X(���ϱ�ǥ��)�� ���Ե� �䰡 �����ȴ�.
//	���ԵǴ� ü������ �̹����� createXXXXView�� ���� ������ ���� �̹����� ���� ��ν� �����Ǹ� ŷ �� ��� ����Ʈ �� ���� �̹����� ���´�.	
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
	
//	�̹����� �޾Ƽ� �ϳ��� ü������ �̹����� ī��Ʈ �ɹ��� ���� �並 ������ִ� �޼����̴�.
//	���������� createChessmanImagePanel �޼��带 ���� �Ķ���ͷ� ���� �̹����� ���� �г��� �����ѵ� �����ְ�
//	createCountSymbolLabel �޼��带 ���� ��ȣ�� ���Ե� ���� �����ش�.
	private FlatPanel createSingleChessmanView(Image image) {
		FlatPanel chessmanView = new FlatPanel(new LinearLayout(0));
		chessmanView.setOpaque(false);
		
		chessmanView.add(createChessmanImagePanel(image), createCommonConstraints(3));
		chessmanView.add(createCountSymbolLabel(), createCommonConstraints(1));
		
		return chessmanView;
	}
	
//	�Ķ���ͷ� ���� �̹����� ���� ü������ �̹����� ���� �г��� ���� ��ȯ���ش�.
	private FlatImagePanel createChessmanImagePanel(Image image) {
		FlatImagePanel chessmanImagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		chessmanImagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		chessmanImagePanel.setOpaque(false);
		return chessmanImagePanel;
	}
	
//	count��ȣ�� ������ ���� �����ؼ� ��ȯ���ִ� �޼����̴�.
	private FlatLabel createCountSymbolLabel() {
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.LEFT);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		return countSymbol;
	}
}

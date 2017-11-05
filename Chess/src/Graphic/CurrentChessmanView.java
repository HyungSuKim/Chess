package Graphic;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.flat.text.textarea.alignment.FlatHorizontalAlignment;
import com.mommoo.flat.text.textarea.alignment.FlatVerticalAlignment;

import Manager.Game;

@SuppressWarnings("serial")
class CurrentChessmanView extends FlatPanel {
	
	private static Images images = new Images();
	private static Theme theme = new Theme();
	
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
	
	CurrentChessmanView(Game game) {
		setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		setBackground(theme.getLightBlueColor());
		setOpaque(true);
		
		initializeCount(game);
		add(createViewInfoLabel(), createCommonConstraints(1));
		add(createBlackChessmanView(), createCommonConstraints(2));
		add(createWhiteChessmanView(), createCommonConstraints(2));
	}
	
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints(weight, LinearSpace.MATCH_PARENT);
	}
	
	static private FlatLabel createCountLabel() {
		FlatLabel countLabel = new FlatLabel();
		countLabel.setOpaque(false);
		countLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		countLabel.setFont(theme.getBoldFont15Pt());
		return countLabel;
	}
	
	private void initializeCount(Game game) {		
		
		blackKingCount.setText(Integer.toString(game.getBlackChessmanCount("King")));
		blackQueenCount.setText(Integer.toString(game.getBlackChessmanCount("Queen")));
		blackBishopCount.setText(Integer.toString(game.getBlackChessmanCount("Bishop")));
		blackKnightCount.setText(Integer.toString(game.getBlackChessmanCount("Knight")));
		blackRookCount.setText(Integer.toString(game.getBlackChessmanCount("Rook")));
		blackPawnCount.setText(Integer.toString(game.getBlackChessmanCount("Pawn")));
		
		whiteKingCount.setText(Integer.toString(game.getWhiteChessmanCount("King")));
		whiteQueenCount.setText(Integer.toString(game.getWhiteChessmanCount("Queen")));
		whiteBishopCount.setText(Integer.toString(game.getWhiteChessmanCount("Bishop")));
		whiteKnightCount.setText(Integer.toString(game.getWhiteChessmanCount("Knight")));
		whiteRookCount.setText(Integer.toString(game.getWhiteChessmanCount("Rook")));
		whitePawnCount.setText(Integer.toString(game.getWhiteChessmanCount("Pawn")));
		
	}

	private FlatLabel createViewInfoLabel() {
		FlatLabel viewInfoLabel = new FlatLabel("체스말 현황");
		
		viewInfoLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		viewInfoLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		viewInfoLabel.setFont(theme.getBoldFont15Pt());
		viewInfoLabel.setBackground(theme.getDarkBlueColor());
		
		return viewInfoLabel;
	}
	
	private FlatPanel createBlackChessmanView() {
		FlatPanel blackChessmanView = new FlatPanel(new LinearLayout(0));
		blackChessmanView.setOpaque(false);
		
		blackChessmanView.add(createKingView(images.getBlackKingImage()), createCommonConstraints(1));
		blackChessmanView.add(createQueenView(images.getBlackQueenImage()), createCommonConstraints(1));
		blackChessmanView.add(createBishopView(images.getBlackBishopImage()), createCommonConstraints(1));
		blackChessmanView.add(createKnightView(images.getBlackKnightImage()), createCommonConstraints(1));
		blackChessmanView.add(createRookView(images.getBlackRookImage()), createCommonConstraints(1));
		blackChessmanView.add(createPawnView(images.getBlackPawnImage()), createCommonConstraints(1));
		
		return blackChessmanView;
	}
	
	private FlatPanel createWhiteChessmanView() {
		FlatPanel whiteChessmanView = new FlatPanel(new LinearLayout(0));
		
		whiteChessmanView.add(createKingView(images.getWhiteKingImage()), createCommonConstraints(1));
		whiteChessmanView.add(createQueenView(images.getWhiteQueenImage()), createCommonConstraints(1));
		whiteChessmanView.add(createBishopView(images.getWhiteBishopImage()), createCommonConstraints(1));
		whiteChessmanView.add(createKnightView(images.getWhiteKnightImage()), createCommonConstraints(1));
		whiteChessmanView.add(createRookView(images.getWhiteRookImage()), createCommonConstraints(1));
		whiteChessmanView.add(createPawnView(images.getWhitePawnImage()), createCommonConstraints(1));
		
		return whiteChessmanView;
	}
		
	private FlatLabel createKingLabel(Image image) {
		FlatLabel kingLabel = new FlatLabel();
		kingLabel.insertIcon(new ImageIcon(image));
		kingLabel.setOpaque(false);
		kingLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		kingLabel.setVerticalAlignment(FlatVerticalAlignment.TOP);
		return kingLabel;
	}
	
	private FlatPanel createKingView(Image image) {
		FlatPanel kingView = new FlatPanel(new LinearLayout(0));
		kingView.setOpaque(false);
		
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		
		kingView.add(createKingLabel(image), createCommonConstraints(3));
		
		kingView.add(countSymbol, createCommonConstraints(1));
		
		if(image.equals(images.getBlackKingImage())) {
			kingView.add(blackKingCount, createCommonConstraints(1));
		} else {
			kingView.add(whiteKingCount, createCommonConstraints(1));
		}
		
		return kingView;
	}
	
	private FlatLabel createQueenLabel(Image image) {
		FlatLabel queenLabel = new FlatLabel();
		queenLabel.insertIcon(new ImageIcon(image));
		queenLabel.setOpaque(false);
		queenLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		queenLabel.setVerticalAlignment(FlatVerticalAlignment.TOP);
		return queenLabel;
	}
	
	private FlatPanel createQueenView(Image image) {
		FlatPanel queenView = new FlatPanel(new LinearLayout(0));
		queenView.setOpaque(false);
		
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		
		queenView.add(createQueenLabel(image), createCommonConstraints(3));
		
		queenView.add(countSymbol, createCommonConstraints(1));
		
		if(image.equals(images.getBlackQueenImage())) {
			queenView.add(blackQueenCount, createCommonConstraints(1));
		} else {
			queenView.add(whiteQueenCount, createCommonConstraints(1));
		}
		
		return queenView;
	}
	
	private FlatLabel createBishopLabel(Image image) {
		FlatLabel bishopLabel = new FlatLabel();
		bishopLabel.insertIcon(new ImageIcon(image));
		bishopLabel.setOpaque(false);
		bishopLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		bishopLabel.setVerticalAlignment(FlatVerticalAlignment.TOP);
		return bishopLabel;
	}
	
	private FlatPanel createBishopView(Image image) {
		FlatPanel bishopView = new FlatPanel(new LinearLayout(0));
		bishopView.setOpaque(false);
		
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		
		bishopView.add(createBishopLabel(image), createCommonConstraints(3));
		
		bishopView.add(countSymbol, createCommonConstraints(1));
		
		if(image.equals(images.getBlackBishopImage())) {
			bishopView.add(blackBishopCount, createCommonConstraints(1));
		} else {
			bishopView.add(whiteBishopCount, createCommonConstraints(1));
		}
		
		return bishopView;
	}
	
	private FlatLabel createKnightLabel(Image image) {
		FlatLabel knightLabel = new FlatLabel();
		knightLabel.insertIcon(new ImageIcon(image));
		knightLabel.setOpaque(false);
		knightLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		knightLabel.setVerticalAlignment(FlatVerticalAlignment.TOP);
		return knightLabel;
	}
	
	private FlatPanel createKnightView(Image image) {
		FlatPanel knightView = new FlatPanel(new LinearLayout(0));
		knightView.setOpaque(false);
		
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		
		knightView.add(createKnightLabel(image), createCommonConstraints(3));
		
		knightView.add(countSymbol, createCommonConstraints(1));
		
		if(image.equals(images.getBlackKnightImage())) {
			knightView.add(blackKnightCount, createCommonConstraints(1));
		} else {
			knightView.add(whiteKnightCount, createCommonConstraints(1));
		}
		
		return knightView;
	}
	
	private FlatLabel createRookLabel(Image image) {
		FlatLabel rookLabel = new FlatLabel();
		rookLabel.insertIcon(new ImageIcon(image));
		rookLabel.setOpaque(false);
		rookLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		rookLabel.setVerticalAlignment(FlatVerticalAlignment.TOP);
		return rookLabel;
	}
	
	private FlatPanel createRookView(Image image) {
		FlatPanel rookView = new FlatPanel(new LinearLayout(0));
		rookView.setOpaque(false);
		
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		
		rookView.add(createRookLabel(image), createCommonConstraints(3));
		
		rookView.add(countSymbol, createCommonConstraints(1));
		
		if(image.equals(images.getBlackRookImage())) {
			rookView.add(blackRookCount, createCommonConstraints(1));
		} else {
			rookView.add(whiteRookCount, createCommonConstraints(1));
		}
		
		return rookView;
	}
	
	private FlatLabel createPawnLabel(Image image) {
		FlatLabel pawnLabel = new FlatLabel();
		pawnLabel.insertIcon(new ImageIcon(image));
		pawnLabel.setOpaque(false);
		pawnLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		pawnLabel.setVerticalAlignment(FlatVerticalAlignment.TOP);
		return pawnLabel;
	}
	
	private FlatPanel createPawnView(Image image) {
		FlatPanel pawnView = new FlatPanel(new LinearLayout(0));
		pawnView.setOpaque(false);
		
		FlatLabel countSymbol = new FlatLabel("X");
		countSymbol.setOpaque(false);
		countSymbol.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		countSymbol.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		
		pawnView.add(createPawnLabel(image), createCommonConstraints(3));
		
		pawnView.add(countSymbol, createCommonConstraints(1));
		
		if(image.equals(images.getBlackPawnImage())) {
			pawnView.add(blackPawnCount, createCommonConstraints(1));
		} else {
			pawnView.add(whitePawnCount, createCommonConstraints(1));
		}
		
		return pawnView;
	}
}

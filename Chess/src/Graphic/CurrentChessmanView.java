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
import Manager.Chessman.Type;

@SuppressWarnings("serial")
class CurrentChessmanView extends FlatPanel {

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
		setBackground(Theme.LIGHT_BLUE);
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
		countLabel.setFont(Theme.BOLD_FONT_15PT);
		return countLabel;
	}

	private void initializeCount(Game game) {

		blackKingCount.setText(Integer.toString(game.getBlackChessmanCount(Type.KING)));
		blackQueenCount.setText(Integer.toString(game.getBlackChessmanCount(Type.QUEEN)));
		blackBishopCount.setText(Integer.toString(game.getBlackChessmanCount(Type.BISHOP)));
		blackKnightCount.setText(Integer.toString(game.getBlackChessmanCount(Type.KNIGHT)));
		blackRookCount.setText(Integer.toString(game.getBlackChessmanCount(Type.ROOK)));
		blackPawnCount.setText(Integer.toString(game.getBlackChessmanCount(Type.PAWN)));

		whiteKingCount.setText(Integer.toString(game.getWhiteChessmanCount(Type.KING)));
		whiteQueenCount.setText(Integer.toString(game.getWhiteChessmanCount(Type.QUEEN)));
		whiteBishopCount.setText(Integer.toString(game.getWhiteChessmanCount(Type.BISHOP)));
		whiteKnightCount.setText(Integer.toString(game.getWhiteChessmanCount(Type.KNIGHT)));
		whiteRookCount.setText(Integer.toString(game.getWhiteChessmanCount(Type.ROOK)));
		whitePawnCount.setText(Integer.toString(game.getWhiteChessmanCount(Type.PAWN)));

	}

	private FlatLabel createViewInfoLabel() {
		FlatLabel viewInfoLabel = new FlatLabel("체스말 현황");

		viewInfoLabel.setHorizontalAlignment(FlatHorizontalAlignment.CENTER);
		viewInfoLabel.setVerticalAlignment(FlatVerticalAlignment.CENTER);
		viewInfoLabel.setFont(Theme.BOLD_FONT_15PT);
		viewInfoLabel.setBackground(Theme.DARK_BLUE);

		return viewInfoLabel;
	}

	private FlatPanel createBlackChessmanView() {
		FlatPanel blackChessmanView = new FlatPanel(new LinearLayout(0));
		blackChessmanView.setOpaque(false);

		blackChessmanView.add(createKingView(Images.BLACK_KING), createCommonConstraints(1));
		blackChessmanView.add(createQueenView(Images.BLACK_QUEEN), createCommonConstraints(1));
		blackChessmanView.add(createBishopView(Images.BLACK_BISHOP), createCommonConstraints(1));
		blackChessmanView.add(createKnightView(Images.BLACK_KNIGHT), createCommonConstraints(1));
		blackChessmanView.add(createRookView(Images.BLACK_ROOK), createCommonConstraints(1));
		blackChessmanView.add(createPawnView(Images.BLACK_PAWN), createCommonConstraints(1));

		return blackChessmanView;
	}

	private FlatPanel createWhiteChessmanView() {
		FlatPanel whiteChessmanView = new FlatPanel(new LinearLayout(0));

		whiteChessmanView.add(createKingView(Images.WHITE_KING), createCommonConstraints(1));
		whiteChessmanView.add(createQueenView(Images.WHITE_QUEEN), createCommonConstraints(1));
		whiteChessmanView.add(createBishopView(Images.WHITE_BISHOP), createCommonConstraints(1));
		whiteChessmanView.add(createKnightView(Images.WHITE_KNIGHT), createCommonConstraints(1));
		whiteChessmanView.add(createRookView(Images.WHITE_ROOK), createCommonConstraints(1));
		whiteChessmanView.add(createPawnView(Images.WHITE_PAWN), createCommonConstraints(1));

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

		if (image.equals(Images.BLACK_KING)) {
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

		if (image.equals(Images.BLACK_QUEEN)) {
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

		if (image.equals(Images.BLACK_BISHOP)) {
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

		if (image.equals(Images.BLACK_KNIGHT)) {
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

		if (image.equals(Images.BLACK_ROOK)) {
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

		if (image.equals(Images.BLACK_PAWN)) {
			pawnView.add(blackPawnCount, createCommonConstraints(1));
		} else {
			pawnView.add(whitePawnCount, createCommonConstraints(1));
		}

		return pawnView;
	}
}

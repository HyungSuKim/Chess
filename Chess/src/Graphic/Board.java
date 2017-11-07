package Graphic;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import Manager.Game;

@SuppressWarnings("serial")
class Board extends FlatPanel {
	private static Chessman chessman = new Chessman();
	
	private static final FlatPanel[][] square = new FlatPanel[8][8];
	
	Board(Game game) {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);
		
		createBoard();
		setChessmanOnBoard(game);
	}
	
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
	private void createBoard() {
		for(int row = 8; row > 0; row--) {
			for(int col = 0; col < 8; col++) {
				this.add(createSquare(row-1, col), createCommonConstraints(1));
			}
		}	
	}
	
	private FlatPanel createSquare(int row, int col) {
		square[row][col] = new FlatPanel(new LinearLayout(Orientation.HORIZONTAL));
		square[row][col].setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		square[row][col].setOpaque(true);
		
		if(((row%2==0) && (col%2==0))||((row%2==1)&&(col%2==1))) {
			square[row][col].setBackground(Theme.BOARD_DARK_SQUARE_COLOR);
		} else {
			square[row][col].setBackground(Theme.BOARD_LIGHT_SQUARE_COLOR);
		}	
		return square[row][col];
	}

	
	private void setChessmanOnBoard(Game game) {
		final int BLACK_FRONT_ROW = 6;
		final int BLACK_BACK_ROW = 7;
		final int WHITE_FRONT_ROW = 1;
		final int WHITE_BACK_ROW = 0;
		for(int col = 0; col < 8; col++) { 
			square[BLACK_FRONT_ROW][col].add(chessman.getBlackPawn(col, 6, game), createCommonConstraints(1));
			square[WHITE_FRONT_ROW][col].add(chessman.getWhitePawn(col, 1, game), createCommonConstraints(1));
		}
			square[BLACK_BACK_ROW][0].add(chessman.getBlackRook(0, 7, game), createCommonConstraints(1));
			square[BLACK_BACK_ROW][1].add(chessman.getBlackKnight(1, 7, game), createCommonConstraints(1));
			square[BLACK_BACK_ROW][2].add(chessman.getBlackBishop(2, 7, game), createCommonConstraints(1));
			square[BLACK_BACK_ROW][3].add(chessman.getBlackQueen(3, 7, game), createCommonConstraints(1));			
			square[BLACK_BACK_ROW][4].add(chessman.getBlackKing(4, 7, game), createCommonConstraints(1));
			square[BLACK_BACK_ROW][5].add(chessman.getBlackBishop(5, 7, game), createCommonConstraints(1));
			square[BLACK_BACK_ROW][6].add(chessman.getBlackKnight(6, 7, game), createCommonConstraints(1));
			square[BLACK_BACK_ROW][7].add(chessman.getBlackRook(7, 7, game), createCommonConstraints(1));

			square[WHITE_BACK_ROW][0].add(chessman.getBlackRook(0, 0, game), createCommonConstraints(1));
			square[WHITE_BACK_ROW][1].add(chessman.getWhiteKnight(1, 0, game), createCommonConstraints(1));
			square[WHITE_BACK_ROW][2].add(chessman.getWhiteBishop(2, 0, game), createCommonConstraints(1));
			square[WHITE_BACK_ROW][3].add(chessman.getWhiteQueen(3, 0, game), createCommonConstraints(1));
			square[WHITE_BACK_ROW][4].add(chessman.getWhiteKing(4, 0, game), createCommonConstraints(1));
			square[WHITE_BACK_ROW][5].add(chessman.getWhiteBishop(5, 0, game), createCommonConstraints(1));
			square[WHITE_BACK_ROW][6].add(chessman.getWhiteKnight(6, 0, game), createCommonConstraints(1));
			square[WHITE_BACK_ROW][7].add(chessman.getWhiteRook(7, 0, game), createCommonConstraints(1));			
	}

}

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
	private static Theme theme = new Theme();
	private static Chessman chessman = new Chessman();
	
	private static FlatPanel[][] square = new FlatPanel[8][8];
	
	Board(Game game) {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(theme.getBoardBorderColor());
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
			square[row][col].setBackground(theme.getBoardDarkSquareColor());
		} else {
			square[row][col].setBackground(theme.getBoardLightSquareColor());
		}	
		return square[row][col];
	}

	
	private void setChessmanOnBoard(Game game) {
		for(int col = 0; col < 8; col++) { 
			square[6][col].add(chessman.getBlackPawn(col, 6, game), createCommonConstraints(1));
			square[1][col].add(chessman.getWhitePawn(col, 1, game), createCommonConstraints(1));
			
			if(col==0||col==7) {
				square[7][col].add(chessman.getBlackRook(col, 7, game), createCommonConstraints(1));
				square[0][col].add(chessman.getWhiteRook(col, 0, game), createCommonConstraints(1));
				
			} else if(col==1||col==6) {
				square[7][col].add(chessman.getBlackKnight(col, 7, game), createCommonConstraints(1));
				square[0][col].add(chessman.getWhiteKnight(col, 0, game), createCommonConstraints(1));
				
			} else if(col==2||col==5) {
				square[7][col].add(chessman.getBlackBishop(col, 7, game), createCommonConstraints(1));
				square[0][col].add(chessman.getWhiteBishop(col, 0, game), createCommonConstraints(1));
				
			} else if(col==3) {
				square[7][col].add(chessman.getBlackQueen(col, 7, game), createCommonConstraints(1));
				square[0][col].add(chessman.getWhiteQueen(col, 0, game), createCommonConstraints(1));
				
			} else {
				square[7][col].add(chessman.getBlackKing(col, 7, game), createCommonConstraints(1));
				square[0][col].add(chessman.getWhiteKing(col, 0, game), createCommonConstraints(1));
			}
		}
	}
	
}

package com.bfpaul.chess.board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;


@SuppressWarnings("serial")
public class Board extends FlatPanel {
	
	private FlatPanel[][] square = new FlatPanel[8][8];
	private FlatPanel board = createBoard();
	
	public Board() { 
		
		setLayout(new BorderLayout());
		
		add(board);

	}
	
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
	private FlatPanel createBoard() {
		FlatPanel board = new FlatPanel(new GridLayout(8, 8));
		
		board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		board.setBackground(Theme.BOARD_BORDER_COLOR);
		
		board.setOpaque(true);
		
		for(int y = 8; y > 0; y--) {
			for(int x = 0; x < 8; x++) {
				board.add(createSquare(x, (y-1)), createCommonConstraints(1));
			}
		}	
		
		return board;
	}
	
	private FlatPanel createSquare(int x, int y) {
		square[y][x] = new FlatPanel(new LinearLayout(Orientation.HORIZONTAL));
		square[y][x].setOpaque(true);
		
		if(((y%2==0) && (x%2==0))||((y%2==1)&&(x%2==1))) {
			square[y][x].setBackground(Theme.BOARD_DARK_SQUARE_COLOR);
		} else {
			square[y][x].setBackground(Theme.BOARD_LIGHT_SQUARE_COLOR);
		}
		
		return square[y][x];
	}
	
	public void setChessmanOnSquare(FlatImagePanel chessman, int x, int y) {
		
		square[y][x].add(chessman, createCommonConstraints(1));
		
	}
}

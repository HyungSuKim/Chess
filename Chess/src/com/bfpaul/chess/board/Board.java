package com.bfpaul.chess.board;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;


@SuppressWarnings("serial")
public class Board extends FlatPanel {
	
	private static FlatPanel[][] square = new FlatPanel[8][8];
	
	private static Properties properties = new Properties();
	
	public Board() {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);
		
		createBoard();
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
		square[row][col].setOpaque(true);
		
		if(((row%2==0) && (col%2==0))||((row%2==1)&&(col%2==1))) {
			square[row][col].setBackground(Theme.BOARD_DARK_SQUARE_COLOR);
		} else {
			square[row][col].setBackground(Theme.BOARD_LIGHT_SQUARE_COLOR);
		}	
		return square[row][col];
	}
	
	public Properties getProperties() {
		return properties;
	}
}

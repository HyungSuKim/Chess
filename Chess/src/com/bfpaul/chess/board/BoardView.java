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

// x�� y�� 8ĭ�� square�� ���� Board�� �����Ͽ� �����ش�.
@SuppressWarnings("serial")
public class BoardView extends FlatPanel {
//	ü�� ���� �ϳ��ϳ��� square 
	private FlatPanel[][] square = new FlatPanel[8][8];
	
// ���带 �����Ͽ� �߾ӿ� ������ �ٿ��ش�.
	public BoardView() { 
		
		setLayout(new BorderLayout());
		
		add(createBoard());
	}
	
//	�Էµ� ���Կ� �θ��� ũ�⸸ŭ ������ �����ϴ� ���������� �����Ͽ� ��ȯ�Ѵ�.  
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	GridLayout���� ��ġ�� square���� ���� board�� �����ؼ� ��ȯ���ش�.
	private FlatPanel createBoard() {
		FlatPanel board = new FlatPanel(new GridLayout(8, 8));
		
		board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		board.setBackground(Theme.BOARD_BORDER_COLOR);
		
		board.setOpaque(true);
		
		for(int y = 8; y > 0; y--) {
			for(int x = 0; x < 8; x++) {
				board.add(createSquare(x, (y-1)), createMatchParentConstraints(1));
			}
		}
		
		return board;
	}
	
//	�Էµ� x, y�� �̿��ؼ� Ư�� ���� ���� ü������ square�� ����� ��ȯ�Ѵ�. 
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
	
//	chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square�� �÷��ش�.
	public void setChessmanOnSquare(FlatImagePanel chessman, int x, int y) {
		
		square[y][x].add(chessman, createMatchParentConstraints(1));
		
	}
}

package com.bfpaul.chess.board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// x�� y�� 8ĭ�� square�� ���� Board�� �����Ͽ� �����ش�.
@SuppressWarnings("serial")
public class BoardView extends FlatPanel {
//	ü�� ���� �ϳ��ϳ��� square 
	private Square[][] square = new Square[8][8];
	
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
	
//	�Էµ� ��ǥ x, y�� ���� square�� �����ؼ� ȯ�Ѵ�. 
	private Square createSquare(int x, int y) {
		return square[y][x] = new Square(x, y);
	}
	
//	chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square�� �÷��ش�.
	public void setChessmanOnSquare(FlatImagePanel chessman, int x, int y) {
		square[y][x].setChessmanOnSquare(chessman);
	}
}

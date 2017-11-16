package com.bfpaul.chess.board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// x축 y축 8칸의 square을 가진 Board를 생성하여 보여준다.
@SuppressWarnings("serial")
public class BoardView extends FlatPanel {
//	체스 판의 하나하나의 square 
	private Square[][] square = new Square[8][8];
	
// 보드를 생성하여 중앙에 꽉차게 붙여준다.
	public BoardView() { 
		
		setLayout(new BorderLayout());
		
		add(createBoard());
	}
	
//	입력된 무게와 부모의 크기만큼 영역을 차지하는 제약조건을 생성하여 반환한다.  
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	GridLayout으로 배치된 square들을 가진 board를 생성해서 반환해준다.
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
	
//	입력된 좌표 x, y를 갖는 square를 생성해서 환한다. 
	private Square createSquare(int x, int y) {
		return square[y][x] = new Square(x, y);
	}
	
//	chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에 올려준다.
	public void setChessmanOnSquare(FlatImagePanel chessman, int x, int y) {
		square[y][x].setChessmanOnSquare(chessman);
	}
}

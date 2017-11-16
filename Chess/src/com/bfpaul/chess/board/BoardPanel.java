package com.bfpaul.chess.board;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// x축 y축 8칸의 square을 가진 Board의 생성 및 Board위에서 일어나는 이벤트들(체스말을 놓아준다던가 체스말이 죽어서 제거한다던가 체스말의 이동가능범위를 보여준다던가...)을
// 처리하는 역할을 할 것이다.
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
//	체스 판의 하나하나의 square로써 체스말을 놓아준다던가 체스말을 제외해준다거나 이동가능범위를 표현해줄 최소단위의 칸이다. 
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	
// 8 X 8의 square를 가진 체스판을 만들어준다.
	public BoardPanel() { 
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);
		
		for(int y = 8; y > 0; y--) {
			for(int x = 0; x < 8; x++) {
				add(createBoardSquare(x, (y-1)), createMatchParentConstraints(1));
			}
		}
		
	}
	
//	입력된 무게와 부모의 크기만큼 영역을 차지하는 제약조건을 생성하여 반환한다.  
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	정해진 좌표의 하나의 칸을 생성해서 하나의 square가 배열로써 저장되어 좌표의 의미를 갖도록 해서 추후 클래스의 설명에 적혀있는 이벤트를 처리할때
//	좌표의 값을 이용해서 square에 이벤트를 처리 할 수 있도록 하려고한다.
	private BoardSquare createBoardSquare(int x, int y) {
		boardSquare[y][x] = new BoardSquare();
		boardSquare[y][x].setLayout(new LinearLayout(0));
		boardSquare[y][x].setOpaque(true);
		
		if((x+y)%2==0) {
			boardSquare[y][x].setBackground(Theme.BOARD_DARK_SQUARE_COLOR);
		} else {
			boardSquare[y][x].setBackground(Theme.BOARD_LIGHT_SQUARE_COLOR);
		}
		return boardSquare[y][x];
	}
	
//	chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에 올려준다.
//  올려주는 역할을 BoardSquare class의 메서드를 이용해서 하는데 이를통해 원하는 로직을 따라서 체스말이 추가된다. 
	public void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[y][x].setChessmanOnSquare(chessman);
	}
}

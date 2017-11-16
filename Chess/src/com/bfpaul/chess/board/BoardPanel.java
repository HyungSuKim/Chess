package com.bfpaul.chess.board;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// x�� y�� 8ĭ�� square�� ���� Board�� ���� �� Board������ �Ͼ�� �̺�Ʈ��(ü������ �����شٴ��� ü������ �׾ �����Ѵٴ��� ü������ �̵����ɹ����� �����شٴ���...)��
// ó���ϴ� ������ �� ���̴�.
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
//	ü�� ���� �ϳ��ϳ��� square�ν� ü������ �����شٴ��� ü������ �������شٰų� �̵����ɹ����� ǥ������ �ּҴ����� ĭ�̴�. 
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	
// 8 X 8�� square�� ���� ü������ ������ش�.
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
	
//	�Էµ� ���Կ� �θ��� ũ�⸸ŭ ������ �����ϴ� ���������� �����Ͽ� ��ȯ�Ѵ�.  
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	������ ��ǥ�� �ϳ��� ĭ�� �����ؼ� �ϳ��� square�� �迭�ν� ����Ǿ� ��ǥ�� �ǹ̸� ������ �ؼ� ���� Ŭ������ ���� �����ִ� �̺�Ʈ�� ó���Ҷ�
//	��ǥ�� ���� �̿��ؼ� square�� �̺�Ʈ�� ó�� �� �� �ֵ��� �Ϸ����Ѵ�.
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
	
//	chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square�� �÷��ش�.
//  �÷��ִ� ������ BoardSquare class�� �޼��带 �̿��ؼ� �ϴµ� �̸����� ���ϴ� ������ ���� ü������ �߰��ȴ�. 
	public void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[y][x].setChessmanOnSquare(chessman);
	}
}

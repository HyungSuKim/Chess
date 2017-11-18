package com.bfpaul.chess.board;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
import com.bfpaul.chess.chessman.pawn.Pawn;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// x�� y�� 8ĭ�� square�� ���� Board�� ���� �� Board������ �Ͼ�� �̺�Ʈ��(ü������ �����شٴ��� ü������ �׾ �����Ѵٴ��� ü������ �̵����ɹ����� �����شٴ���...)��
// ó���ϴ� ������ �� ���̴�.
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
//	ü�� ���� �ϳ��ϳ��� square�ν� ü������ �����شٴ��� ü������ �������شٰų� �̵����ɹ����� ǥ������ �ּҴ����� ĭ�̴�. 
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private BoardSquare tempSquare = null;
	boolean once = true;
	
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
		
		setWholeChessmanOnBoard();
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
		boardSquare[y][x].setOriginalColor();
		boardSquare[y][x].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				boardSquare[y][x].getChessman().setMoveableSquareCoordinate(x, y);
				ArrayList<Coordinate> listTest = boardSquare[y][x].getChessman().getMoveableSquareCoordinate();
//				System.out.println(listTest.size());
				for(Coordinate test : listTest) {
//					System.out.println("("+test.getX()+","+test.getY()+")");
//					System.out.println(test.getY());
					boardSquare[test.getY()][test.getX()].setSquareEventColor();
				}
				boardSquare[y][x].getChessman().refreshMoveableSquareCoordinate();
//				int moveableCount = MoveAbleTest.moveableTest(x, y, boardSquare[y][x].getSquareContains());
//				for(int count = 0; count < moveableCount; count++) {
//					boardSquare[y+count][x].setSquareEventColor();
//				}
//				if(once) {
//					if(tempSquare!=null) {
//						tempSquare.setSquareOriginalColor();
//						System.out.println("0000");
//					}
//					tempSquare = boardSquare[y][x];
//					tempSquare.setSquareEventColor();
//					
//					once = false;
//				} else {
//					System.out.println("2222");
//					tempSquare.setSquareOriginalColor();
//					tempSquare = boardSquare[y][x];
//					tempSquare.setSquareEventColor();
//					
//					once = true;
//				}
			}
		});
		
		return boardSquare[y][x];
	}
	
//	chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square�� �÷��ش�.
//  �÷��ִ� ������ BoardSquare class�� �޼��带 �̿��ؼ� �ϴµ� �̸����� ���ϴ� ������ ���� ü������ �߰��ȴ�. 
	public void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[y][x].setChessmanOnSquare(chessman);
	}
	
	// ��� ü������ �������� �����Ѵ�.
	private void setWholeChessmanOnBoard() {
		for (ChessmanType type : ChessmanType.values()) {
			setChessmanOnBoard(type);
		}
	}

	// ü������ Ÿ�Ժ��� ������ �ʱ���ġ�� �������ֱ����� �޼����ε� �� Ÿ�Կ� ���� ������ �ʱ���ġ�� �������ش�. 
	private void setChessmanOnBoard(ChessmanType type) {
		switch (type) {
		case KING:
			setSingleChessmanOnBoard(ChessmanType.KING);
			break;
		case QUEEN:
			setSingleChessmanOnBoard(ChessmanType.QUEEN);
			break;
		case BISHOP:
			setPairChessmanOnBoard(ChessmanType.BISHOP);
			break;
		case KNIGHT:
			setPairChessmanOnBoard(ChessmanType.KNIGHT);
			break;
		case ROOK:
			setPairChessmanOnBoard(ChessmanType.ROOK);
			break;
		case PAWN:
			setPawnOnBoard(ChessmanType.PAWN);
			break;
		default:
		}
	}
//	�ʱ� ������ 1���� ��(ŷ, ��) �� ��� enum�� ordinal�� 0, 1�ε� ���⼭ 3�� ���� ���� ���� ���� ���� x�ν� �ǹ̸� ���� �ȴٴ� ���� �˰�
//	�̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 1���� ���� �Ѳ����� ó���ؼ� Square�� �÷��ְԵǾ���.
	private void setSingleChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(true), (type.ordinal()+3), 0);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal()+3), 7);
	}
	
//	�ʱ� ������ 2���� ��(���, ����Ʈ, ��) �� ��� enum�� ordinal�� 2, 3, 4�ε� ���⼭ 3�� ���� ���� 7���� �� ���� �� ����
//	���� ���� ���� x�ν� �ǹ̸� ���� �ȴٴ� ���� �˰� �̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 2���� ���� �Ѳ����� ó���ؼ� Square�� �÷��ְԵǾ���.
	private void setPairChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(true), (7-(type.ordinal()+3)), 0);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal()+3), 0);
		setChessmanOnSquare(type.createChessman(false), (7-(type.ordinal()+3)), 7);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal()+3), 7);
	}

//  ������ ������ ������(�ʱ�) ��ġ�� �������ֱ����ؼ� �ۼ��Ͽ���. ���� ��� �ʱ� ������ŭ �ݺ��ؼ� �����ؼ� Square���� �÷��ִµ�
//	����� �������� ��Ī���� ������ �Ȱ����� �̿��ߴ�.
	private void setPawnOnBoard(ChessmanType type) {
		int initCount = type.getInitCount();
		for (int count = 0; count < initCount; count++) {
			setChessmanOnSquare(type.createChessman(true), count, 1);
			setChessmanOnSquare(type.createChessman(false), count, 6);
		}
	}
}

class MoveAbleTest {
	private MoveAbleTest() {	}
	
	static int moveableTest(int x, int y, Chessman chessman) {
		if( chessman instanceof Pawn) {
			return 2;
		}
		return 0;
	}
}

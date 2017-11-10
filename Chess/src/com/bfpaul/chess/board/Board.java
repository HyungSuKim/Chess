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

// Ŭ������ ����... x�� y�� 8ĭ�� square�� ���� Chess Board�� �����Ѵ�.
@SuppressWarnings("serial")
public class Board extends FlatPanel {
//	ü�� ���� �ϳ��ϳ��� squre�� ��Ÿ���� ��������ν�  ü������ ���忡 �����ְų� �߰� �̺�Ʈ�� ó�� �� ����� ���� �ִٰ� �Ǵ��ؼ� ��������� ���� 
	private FlatPanel[][] square = new FlatPanel[8][8];
	
// Board�� �����ڷν� BorderLayout�� ���̾ƿ����� �������� ������ ���� EventViewer Ŭ������ �� �������� ��ó�� ���̱� �����̾��µ�
//	�̴� �������ɼ��� �ִ� �κ��̴�. ���带 �����ؼ� �ڱ��ڽſ��� �ٿ��ش�. ���⼭ board�� �����ν� ó������ �ʰ� �޼���ν� ó���� ������
//	���� ��ü�� ������ ���� ������ ����� ���� ���ٰ� �Ǵ��߱� �����̴�.
	public Board() { 
		
		setLayout(new BorderLayout());
		
		add(createBoard());
	}
	
//	�ַ� ����ϴ� LinearLayout�� �Ϲ����� ���������� ��ȯ���ִ� �޼���ν� �Ķ���ͷ� ���Ը� �޾Ƽ� ���Ը� �������ְ� �θ��� ũ�⸸ŭ ������ ü���ش�. 
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	���带 �����ؼ� �гη� ��ȯ���ִ� �޼���ν� �׸��� ���̾ƿ��� ����ؼ� 8 X 8�� square�� ���� ���带 ������ִµ� �̸� ���ؼ� ����������
//	��ø�� for���� �̿��ؼ� x, y�� �Ķ���ͷ� ���� createSquare �޼��带 ȣ�����ش�.
//	�̶� y�� 8���� �����ϰ� x�� 0���� �����ϵ��� ������ ������ GridLayout Ư���� ���� ��ܺ��� ���������� ��ġ�ϱ� ������
//	(x=0, y=7)�� �Ӽ��� ���� squre�� ���� ��ġ�� �Ǿ�� ���� squre�� �̿��� �ٸ� �̺�Ʈ���� ó���� ������ ���̶� �Ǵ��߱� �����̴�.
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
	
//	�Ķ���ͷ� x, y�� �޾Ƽ� ü������ square�� �������ִ� �޼���ν�
//	ü������ Ư���� x, y�� 2�� ���������� �������� 0�̰ų� 1�ΰ�쿡�� ��ο� ������ ������
//	�׷��� ���� ��쿡�� ���� ������ �����ٴ� ������ �ľ��ؼ� if-else���� ����ؼ� �´� ������ �������ְ� ���־���
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
	
//	FlatImagePanel�� chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� ���� ��ġ�� �����ֱ����� �޼���
	public void setChessmanOnSquare(FlatImagePanel chessman, int x, int y) {
		
		square[y][x].add(chessman, createCommonConstraints(1));
		
	}
}

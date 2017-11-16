package com.bfpaul.chess.board;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
// ü������ �ϳ��� �׸�ĭ�̴�.
@SuppressWarnings("serial")
public class Square extends FlatPanel {
//	�׸�ĭ�� X, Y ��ǥ
	private final int X;
	private final int Y;
//	ü������ �������ִ��� �ƴ���
	private boolean isContain;
	
	Square(int x, int y) {
		X = x;
		Y = y;
		
		setLayout(new LinearLayout(Orientation.HORIZONTAL));
		setOpaque(true);
		
		if(((y%2==0) && (x%2==0))||((y%2==1)&&(x%2==1))) {
			setBackground(Theme.BOARD_DARK_SQUARE_COLOR);
		} else {
			setBackground(Theme.BOARD_LIGHT_SQUARE_COLOR);
		}
	}
	
	boolean getIsContain() {
		return isContain;
	}
	
	int getSquareX() {
		return X;
	}
	
	int getSquareY() {
		return Y;
	}
//	�������ִ� ü������ ������ ü������ �׸�ĭ���� �÷��ش�.
	void setChessmanOnSquare(FlatImagePanel chessman) {
		if(!isContain) {
			isContain = true;
			add(chessman, new LinearConstraints(1, LinearSpace.MATCH_PARENT));	
		}
	}
//	ü������ ������������ ü������ �׸�ĭ������ �����Ѵ�.
	void removeChessmanFromSquare() {
		if(isContain) {
			isContain = false;
			remove(0);
		}
	}
}

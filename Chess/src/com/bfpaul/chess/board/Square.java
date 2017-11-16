package com.bfpaul.chess.board;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
// 체스판의 하나의 네모칸이다.
@SuppressWarnings("serial")
public class Square extends FlatPanel {
//	네모칸의 X, Y 좌표
	private final int X;
	private final int Y;
//	체스말을 가지고있는지 아닌지
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
//	가지고있는 체스말이 없으면 체스말을 네모칸위에 올려준다.
	void setChessmanOnSquare(FlatImagePanel chessman) {
		if(!isContain) {
			isContain = true;
			add(chessman, new LinearConstraints(1, LinearSpace.MATCH_PARENT));	
		}
	}
//	체스말을 가지고있으면 체스말을 네모칸위에서 제거한다.
	void removeChessmanFromSquare() {
		if(isContain) {
			isContain = false;
			remove(0);
		}
	}
}

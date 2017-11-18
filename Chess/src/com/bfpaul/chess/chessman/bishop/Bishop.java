package com.bfpaul.chess.chessman.bishop;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//비숍의 속성을 가지고있다.
//비숍 속성인 움직일 수 있는 칸 수를 가지고있다.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
//	비숍의 움직일 수 있는 칸 수로 모든 비숍에게 적용되는 동일 속성이다.
	private static final int MOVEABLE_SQUARE_COUNT = 7;
//	비숍의 속성을 가지고 있는 체스말을 생성해준다.
	public Bishop(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.BISHOP);
	}
	
	public void setMoveableSquareCoordinate(int x, int y) {
		for (int count = 0; count <= MOVEABLE_SQUARE_COUNT; count++) {
			super.setMoveableSquareCoordinate(x - count, y - count);
			super.setMoveableSquareCoordinate(x - count, y + count);
			super.setMoveableSquareCoordinate(x + count, y - count);
			super.setMoveableSquareCoordinate(x + count, y + count);
		}
	}
}

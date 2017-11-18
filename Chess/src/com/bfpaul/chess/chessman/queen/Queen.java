package com.bfpaul.chess.chessman.queen;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//퀸의 속성을 가지고있다.
//퀸의 속성인 움직일 수 있는 칸 수를 가지고있다.
@SuppressWarnings("serial")
public class Queen extends Chessman {
// 퀸의 움직일 수 있는 칸 수로 모든 퀸에게 적용되는 동일 속성이다.
	private static final int MOVEABLE_SQUARE_COUNT = 7;
//	퀸의 속성을 갖는 체스말을 생성한다. 
	public Queen(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.QUEEN);
	}
	
	public void setMoveableSquareCoordinate(int x, int y) {
		for(int count = 0; count <= MOVEABLE_SQUARE_COUNT; count++) {
			super.setMoveableSquareCoordinate(x - count, y + count);
			super.setMoveableSquareCoordinate(x   	   , y + count);
			super.setMoveableSquareCoordinate(x + count, y + count);
			super.setMoveableSquareCoordinate(x - count, y);
			super.setMoveableSquareCoordinate(x + count, y);
			super.setMoveableSquareCoordinate(x - count, y - count);
			super.setMoveableSquareCoordinate(x        , y - count);
			super.setMoveableSquareCoordinate(x + count, y - count);
		}
	}
}

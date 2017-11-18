package com.bfpaul.chess.chessman.knight;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;

//나이트의 속성을 가지고있다.
//나이트 속성인 움직일 수 있는 칸 수를 가지고있다.
@SuppressWarnings("serial")
public class Knight extends Chessman {
	// 나이트의 움직일 수 있는 칸 수로 모든 나이트에게 적용되는 동일 속성이다.
	private static final int MOVEABLE_SQUARE_COUNT = 3;

	// 나이트의 속성을 갖는 체스말을 생성한다.
	public Knight(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.KNIGHT);

	}

	public void setMoveableSquareCoordinate(int x, int y) {
		super.setMoveableSquareCoordinate(x + 1, y + 2);
		super.setMoveableSquareCoordinate(x + 2, y + 1);
		super.setMoveableSquareCoordinate(x + 2, y - 1);
		super.setMoveableSquareCoordinate(x + 1, y - 2);
		super.setMoveableSquareCoordinate(x - 1, y - 2);
		super.setMoveableSquareCoordinate(x - 2, y - 1);
		super.setMoveableSquareCoordinate(x - 2, y + 1);
		super.setMoveableSquareCoordinate(x - 1, y + 2);
	}
}

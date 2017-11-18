package com.bfpaul.chess.chessman.king;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;

//킹의 속성을 가지고있다.
//킹의 움직일 수 있는 칸수와 움직임이 있었는지 아닌지의 속성이 있다.
@SuppressWarnings("serial")
public class King extends Chessman {
	// 킹의 움직일 수 있는 칸 수로 모든 킹에게 적용되는 동일 속성이다.
	private static final int MOVEABLE_SQUARE_COUNT = 1;
	// 추후 king과 rook의 캐슬링을 구현하기 위한 움직였는지 아닌지 속성
	private boolean isMoved;

	// 킹의 속성을 갖는 체스말을 생성한다.
	public King(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.KING);
	}

	// 첫 움직임 이후 움직였음을 설정해준다.
	public void setIsMoved() {
		isMoved = true;
	}

	// 움직임이 있었는지 없었는지를 반환한다.
	public boolean getIsMoved() {
		return isMoved;
	}

	public void setMoveableSquareCoordinate(int x, int y) {
		super.setMoveableSquareCoordinate(x - 1, y + 1);
		super.setMoveableSquareCoordinate(x    , y + 1);
		super.setMoveableSquareCoordinate(x + 1, y + 1);
		super.setMoveableSquareCoordinate(x - 1, y);
		super.setMoveableSquareCoordinate(x + 1, y);
		super.setMoveableSquareCoordinate(x - 1, y - 1);
		super.setMoveableSquareCoordinate(x    , y - 1);
		super.setMoveableSquareCoordinate(x + 1, y - 1);
	}
}

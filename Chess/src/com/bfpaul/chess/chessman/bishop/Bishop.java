package com.bfpaul.chess.chessman.bishop;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//����� �Ӽ��� �������ִ�.
//��� �Ӽ��� ������ �� �ִ� ĭ ���� �������ִ�.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
//	����� ������ �� �ִ� ĭ ���� ��� ��󿡰� ����Ǵ� ���� �Ӽ��̴�.
	private static final int MOVEABLE_SQUARE_COUNT = 7;
//	����� �Ӽ��� ������ �ִ� ü������ �������ش�.
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

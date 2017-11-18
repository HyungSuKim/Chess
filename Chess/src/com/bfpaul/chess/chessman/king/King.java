package com.bfpaul.chess.chessman.king;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;

//ŷ�� �Ӽ��� �������ִ�.
//ŷ�� ������ �� �ִ� ĭ���� �������� �־����� �ƴ����� �Ӽ��� �ִ�.
@SuppressWarnings("serial")
public class King extends Chessman {
	// ŷ�� ������ �� �ִ� ĭ ���� ��� ŷ���� ����Ǵ� ���� �Ӽ��̴�.
	private static final int MOVEABLE_SQUARE_COUNT = 1;
	// ���� king�� rook�� ĳ������ �����ϱ� ���� ���������� �ƴ��� �Ӽ�
	private boolean isMoved;

	// ŷ�� �Ӽ��� ���� ü������ �����Ѵ�.
	public King(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.KING);
	}

	// ù ������ ���� ���������� �������ش�.
	public void setIsMoved() {
		isMoved = true;
	}

	// �������� �־����� ���������� ��ȯ�Ѵ�.
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

package com.bfpaul.chess.chessman.rook;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;

//���� �Ӽ��� �������ִ�.
//���� ������ �� �ִ� ĭ���� ù �������� �־����� �ƴ����� �Ӽ��� �ִ�.
@SuppressWarnings("serial")
public class Rook extends Chessman {
	// ���� ������ �� �ִ� ĭ ���� ��� �迡�� ����Ǵ� ���� �Ӽ��̴�.
	private static final int MOVEABLE_SQUARE_COUNT = 7;
	// ���� ŷ���� ĳ������ �����ϱ� ���� ���������� �ƴ��� �Ӽ�
	private boolean isMoved;

	// ���� �Ӽ��� ���� ü������ �����Ѵ�
	public Rook(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.ROOK);
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
		for (int count = 0; count <= MOVEABLE_SQUARE_COUNT; count++) {
			super.setMoveableSquareCoordinate(x - count, y);
			super.setMoveableSquareCoordinate(x + count, y);
			super.setMoveableSquareCoordinate(x, y - count);
			super.setMoveableSquareCoordinate(x, y + count);
		}
	}
}

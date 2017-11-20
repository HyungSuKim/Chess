package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Queen�� Ư���� ���� ü�����ν��� ������ �ϱ����ؼ� ���������.   
 * Queen�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * moveableSquareCount() { return -1 } : Queen�� �������� ĭ���� ������ ������ ǥ���ϰ��� �ߴ�.
 */
class Queen implements Chessman {
	
	private final boolean IS_WHITE;
	
	Queen(boolean isWhite) {
		IS_WHITE = isWhite;
	}

	@Override
	public Direction[] getDirection() {
		return Direction.values();
	}

	@Override
	public boolean isWhite() {
		return IS_WHITE;
	}

	@Override
	public int getMoveableSquareCount() {
		return -1;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.QUEEN);
	}
	
}

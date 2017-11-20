package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Bishop�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * Bishop�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * moveableSquareCount() { return -1 } : Bishop�� �������� ĭ���� ������ ������ ǥ���ϰ��� �ߴ�.
 */
class Bishop implements Chessman {
	
	private final boolean IS_WHITE;
	
	Bishop(boolean isWhite) {
		IS_WHITE = isWhite;
	}

	@Override
	public Direction[] getDirection() {
		return new Direction[] { Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT };
	}

	@Override
	public boolean isWhite() {
		return IS_WHITE;
	}

	@Override
	public int moveableSquareCount() {
		return -1;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.BISHOP);
	}

}

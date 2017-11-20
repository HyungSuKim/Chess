package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Rook�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * Rook�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * isMoved ���� king�� rook�� ĳ������ �����ϱ� ���� �Ӽ�
 */
class Rook implements Chessman {

	private final boolean IS_WHITE;
	private boolean isMoved;
	
	Rook(boolean isWhite) {
		IS_WHITE = isWhite;
	}

	@Override
	public Direction[] getDirection() {
		return new Direction[] { Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT };
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
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.ROOK);
	}
	
	public void setIsMoved() {
		isMoved = true;
	}
	
	public boolean isMoved() {
		return isMoved;
	}
}

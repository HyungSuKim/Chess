package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * King�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * King�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * isMoved ���� king�� rook�� ĳ������ �����ϱ� ���� �Ӽ�
 */
class King implements Chessman {
	
	private final boolean IS_WHITE;
	private boolean isMoved;
	
	King(boolean isWhite) {
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
		return 1;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.KING);
	}
	
	public void setIsMoved() {
		isMoved = true;
	}
	
	public boolean isMoved() {
		return isMoved;
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.KING;
	}
}

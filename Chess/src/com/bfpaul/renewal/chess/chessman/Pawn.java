package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Pawn�� Ư���� ���� ü�����ν��� ������ �ϱ����ؼ� ���������.   
 * Pawn�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * isMoved : Pawn�� ù �������� �ֱ��� 2ĭ�� ������ �� �ִٴ� ���� ǥ���ϱ����� ����
 */
class Pawn implements Chessman {
	private final boolean IS_WHITE;
	private boolean isMoved;
	
	Pawn(boolean isWhite) {
		IS_WHITE = isWhite;
	}
	
	@Override
	public Direction[] getDirection() {
		return new Direction[] { Direction.UP, Direction.LEFT, Direction.RIGHT };
	}
	
	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.PAWN);
	}

	@Override
	public boolean isWhite() {
		return IS_WHITE;
	}

	@Override
	public int getMoveableSquareCount() {
		if(!isMoved) {
			return 2;
		} else {
			return 1;
		}
	}
	
	public void setIsMoved() {
		isMoved = true;
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.PAWN;
	}
}

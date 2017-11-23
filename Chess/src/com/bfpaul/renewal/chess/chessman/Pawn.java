package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Pawn�� Ư���� ���� ü�����ν��� ������ �ϱ����ؼ� ���������.   
 * Pawn�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * isMoved : Pawn�� ù �������� �ֱ��� 2ĭ�� ������ �� �ִٴ� ���� ǥ���ϱ����� ����
 */
public class Pawn implements Chessman {
	private final boolean IS_WHITE;
	private boolean isMoved;
	
	public Pawn(boolean isWhite) {
		IS_WHITE = isWhite;
	}
	
	@Override
	public Direction[] getDirection() {
		if(IS_WHITE) {
			return new Direction[] { Direction.UP };
		} else {
			return new Direction[] { Direction.DOWN };
		}
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

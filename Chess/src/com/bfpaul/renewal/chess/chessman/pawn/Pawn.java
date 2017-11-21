package com.bfpaul.renewal.chess.chessman.pawn;

import java.awt.Image;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.ChessmanImage;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.chessman.Direction;
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

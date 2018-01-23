package com.bfpaul.renewal.chess.chessman.data;

import java.awt.Image;

import com.bfpaul.renewal.chess.route.Direction;
/*
 * King�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * King�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * isMoved ���� king�� rook�� ĳ������ �����ϱ� ���� �Ӽ�
 */
public class King extends Chessman {
	
	private final boolean IS_WHITE;
	private boolean isMoved;
	
	public King(boolean isWhite) {
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
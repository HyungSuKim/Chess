package com.bfpaul.renewal.chess.chessman.data;

import java.awt.Image;

import com.bfpaul.renewal.chess.route.Direction;
/*
 * Rook�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * Rook�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * isMoved ���� king�� rook�� ĳ������ �����ϱ� ���� �Ӽ�
 */
public class Rook extends Chessman implements SpecialChessmanMovedIndicator {

	private final boolean IS_WHITE;
	private boolean isMoved;
	
	public Rook(boolean isWhite) {
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
	public int getMoveableSquareCount() {
		return -1;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.ROOK);
	}
	
	public void setMoved() {
		isMoved = true;
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.ROOK;
	}

	@Override
	public void setMovedSquareCount(int squareCount) {
		setMoved();
	}

	@Override
	public boolean isMoved() {
		return isMoved;
	}
}

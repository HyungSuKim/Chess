package com.bfpaul.renewal.chess.chessman.data;

import java.awt.Image;

import com.bfpaul.renewal.chess.route.Direction;
/*
 * Rook의 특성을 가진 체스말로써 역할 하기위해서 만들어졌다.   
 * Rook의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * isMoved 추후 king과 rook의 캐슬링을 구현하기 위한 속성
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

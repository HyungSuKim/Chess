package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Knight의 특성을 가진 체스말로써 역할 하기위해서 만들어졌다.   
 * Knight의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * Direction[] getDirection() { return Direction.values(); }
 * -> Knight의 이동은 UP, DOWN, RIGHT, LEFT로 2칸 이동 후 UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT로 1칸 이동한다고 생각하였다.
 */
class Knight implements Chessman {
	
	private final boolean IS_WHITE;
	
	Knight(boolean iswhite) {
		IS_WHITE = iswhite;
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
		return 3;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.KNIGHT);
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.KNIGHT;
	}
	

}

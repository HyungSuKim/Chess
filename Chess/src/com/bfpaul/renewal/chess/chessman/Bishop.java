package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Bishop의 특성을 가진 체스말로써 역할 하기위해서 만들어졌다.   
 * Bishop의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * moveableSquareCount() { return -1 } : Bishop의 움직임은 칸수의 제한이 없음을 표현하고자 했다.
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

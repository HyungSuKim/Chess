package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Queen의 특성을 가진 체스말로써의 역할을 하기위해서 만들어졌다.   
 * Queen의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * moveableSquareCount() { return -1 } : Queen의 움직임은 칸수의 제한이 없음을 표현하고자 했다.
 */
class Queen implements Chessman {
	
	private final boolean IS_WHITE;
	
	Queen(boolean isWhite) {
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
		return -1;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.QUEEN);
	}
	
}

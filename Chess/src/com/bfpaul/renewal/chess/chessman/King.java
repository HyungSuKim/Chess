package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * King의 특성을 가진 체스말로써 역할 하기위해서 만들어졌다.   
 * King의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * isMoved 추후 king과 rook의 캐슬링을 구현하기 위한 속성
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

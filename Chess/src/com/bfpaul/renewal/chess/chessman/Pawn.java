package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Pawn의 특성을 가진 체스말로써의 역할을 하기위해서 만들어졌다.   
 * Pawn의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * isMoved : Pawn은 첫 움직임이 있기전 2칸을 움직일 수 있다는 것을 표현하기위한 변수
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

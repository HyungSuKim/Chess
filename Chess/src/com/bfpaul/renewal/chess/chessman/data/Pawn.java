package com.bfpaul.renewal.chess.chessman.data;

import java.awt.Image;

import com.bfpaul.renewal.chess.route.Direction;
/*
 * Pawn의 특성을 가진 체스말로써의 역할을 하기위해서 만들어졌다.   
 * Pawn의 표현을 위한 이미지와 이동을 위한 정보를 제공한다.
 * 
 * isMoved : Pawn은 첫 움직임이 있기전 2칸을 움직일 수 있다는 것을 표현하기위한 변수
 */
public class Pawn extends Chessman implements SpecialChessmanMovedIndicator {
	// 폰의 색상정보를 나타내는 데이터로써 필요한 멤버변수, 이는 모든 chessman에 동일하게 적용된다.
	private final boolean IS_WHITE;
	
	// 폰이 움직인 칸수를 나타내는 데이터로써 필요한 멤버변수, 폰은 자기 자신이 움직인 칸수를 기억해야 될 필요성이 있다.
	// 다른 말들과 다르게 폰은 2칸을 움직였을때 발생하는 이벤트가 있는데 이는 위에서 언급한 isMoved멤버변수 만으로는 표현 할 수 없다고 판단했다.
	// 그 이유는 특별한 움직임(2칸을 움직일 수 있는 상황)에서 1칸을 움직이는 경우도 존재하기 때문이다.
	private int movedSquareCount;
	
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
		if(movedSquareCount == 0) {
			return 2;
		} else {
			return 1;
		}
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.PAWN;
	}

	@Override
	public void setMovedSquareCount(int squareCount) {
		movedSquareCount = squareCount;
	}

	public int getMovedSquareCount() {
		return movedSquareCount;
	}

	@Override
	public boolean isMoved() {
		if(movedSquareCount == 0) {
			return false;
		} else {
			return true;
		}
	}
}

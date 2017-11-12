package com.bfpaul.chess.chessman.pawn;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
// 폰의 속성을 가지고있다.
// 폰의 움직일 수 있는 칸수와 첫 움직임이 있었는지 아닌지의 속성이 있다.
@SuppressWarnings("serial")
public class Pawn extends Chessman {
//	폰의 움직일 수 있는 칸 수로 모든 폰에게 적용되는 동일 속성이다.
	private static final int MOVEABLE_SQUARE_COUNT = 1;
//	폰이 움직였는지 아닌지의 속성으로써 첫 움직임이 없는 상태에서 2칸을 움직일 수 있는 폰의 특성을 나타내기위하여 사용된다.
	private boolean isMoved;
	
//	폰의 속성을 갖는 체스말을 생성한다.  
	public Pawn(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.PAWN);
	}

//	폰이 움직일 수 있는 square의 수를 반환하며 조상클래스의 getMoveableSquareCount()를 오버라이딩 한다. 
//	만약에 움직임이 없었으면 첫움직임에서 움직일수 있는 칸 수 2를 반환하고 첫 움직임이 있었다면 기본으로 움직일수 있는 1을 반환해준다.
	@Override
	public int getMoveableSquareCount() {
		if(!isMoved) {
			return super.getMoveableSquareCount() * 2;
		} else {
			return super.getMoveableSquareCount();
		}
	}
	
//	첫 움직임 이후 움직였음을 설정해준다.
	public void setIsMoved() {
		isMoved = true;
	}
	
//	움직임이 있었는지 없었는지를 반환한다.
	public boolean getIsMoved() {
		return isMoved;
	}
}
package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/* 
 * 각각 다른 체스말들을 하나의 공통된 것으로 처리하기 위해서 만들어졌다.
 * 보드에 체스말을 놓거나 사라지게 하기위해서는 이미지가 필요하다. Chessman을 구현하는 하위 클래스들은 그 필요한 이미지 데이터를 가지고있다.
 * Chessman을 구현하는 하위 클래스들은 체스말이 이동하기 위해 필요한 데이터들을 제공해준다.
 * 
 * MOVEABLE_SQUARE_COUNT : -1 -> 이동가능한 칸의 수가 정해지지 않은 말들 (Queen, Bishop, Rook)
 */
interface Chessman {
	
	Direction[] getDirection();
	
	boolean isWhite();
	
	int getMoveableSquareCount();
	
	Image getChessmanImage();
	
	
}

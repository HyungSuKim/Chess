package com.bfpaul.renewal.chess.controller.chessman;

import com.bfpaul.renewal.chess.chessman.Chessman;

/* 
 * 체스말들을 이용해서 보드위에 놓여지는 것과 사라지는 것, 이동하는 것을 보드위에 표현하기 위해 존재한다. 
 * ChessmanController는 칸 위에 있는 말의 이동가능한 경로를 계산하여 보드에 제공함으로써 체스말이 이동 할 수있는 경로를 표현 할 수 있게 해준다. 
 * 
 * 작성중...
 */
class ChessmanController {
	
	private ChessmanController() { }
	
	public static void pickUpChessman(Chessman chessman) {
		switch(chessman.getChessmanType()) {
		case KING :
		case QUEEN :
		case BISHOP :
		case KNIGHT :
		case ROOK :
		case PAWN :
			default : break;
		}
	}
}

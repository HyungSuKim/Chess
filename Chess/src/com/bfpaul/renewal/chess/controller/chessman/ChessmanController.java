package com.bfpaul.renewal.chess.controller.chessman;

import com.bfpaul.renewal.chess.chessman.Chessman;

/* 
 * ü�������� �̿��ؼ� �������� �������� �Ͱ� ������� ��, �̵��ϴ� ���� �������� ǥ���ϱ� ���� �����Ѵ�. 
 * ChessmanController�� ĭ ���� �ִ� ���� �̵������� ��θ� ����Ͽ� ���忡 ���������ν� ü������ �̵� �� ���ִ� ��θ� ǥ�� �� �� �ְ� ���ش�. 
 * 
 * �ۼ���...
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

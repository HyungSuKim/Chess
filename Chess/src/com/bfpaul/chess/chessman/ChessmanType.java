package com.bfpaul.chess.chessman;
// Chessman(체스말)의 타입과 각 타입의 말의 수가 최대 몇인지 지정해준다.
public enum ChessmanType{
	KING(1),
	QUEEN(1),
	BISHOP(2),
	KNIGHT(2),
	ROOK(2),
	PAWN(8);
//	각 말의 최대 수
	private final int MAX_COUNT;

	private ChessmanType(int maxCount) {
		this.MAX_COUNT = maxCount;
	}
// 각 말의 최대 수를 반환한다.	
	public int getMaxCount() {
		return MAX_COUNT;
	}
}

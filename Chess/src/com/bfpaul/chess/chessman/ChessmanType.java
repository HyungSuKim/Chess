package com.bfpaul.chess.chessman;
// Chessman(체스말)의 타입과 각 타입의 말의 게임 시작 시 초기 말의 수를 정해준다.
public enum ChessmanType{
	KING(1),
	QUEEN(1),
	BISHOP(2),
	KNIGHT(2),
	ROOK(2),
	PAWN(8);
//	각 말의 초기 갯수
	private final int INIT_COUNT;

	private ChessmanType(int initCount) {
		this.INIT_COUNT = initCount;
	}
// 각 말의 초기 갯수를 반환한다.	
	public int getInitCount() {
		return INIT_COUNT;
	}
}

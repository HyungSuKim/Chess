package com.bfpaul.chess.chessman;

public enum ChessmanType{
	KING(1),
	QUEEN(1),
	BISHOP(2),
	KNIGHT(2),
	ROOK(2),
	PAWN(8);
	
	private final int MAX_COUNT;
	
	private ChessmanType(int maxCount) {
		this.MAX_COUNT = maxCount;
	}
	
	public int getMaxCount() {
		return MAX_COUNT;
	}
}

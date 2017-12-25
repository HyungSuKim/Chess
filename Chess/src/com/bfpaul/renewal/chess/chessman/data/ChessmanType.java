package com.bfpaul.renewal.chess.chessman.data;

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
	
	public Chessman createChessman(boolean isWhite) {
		switch(this) {
		case KING : return new King(isWhite);
		case QUEEN : return new Queen(isWhite);
		case BISHOP : return new Bishop(isWhite);
		case KNIGHT : return new Knight(isWhite);
		case ROOK : return new Rook(isWhite);
		case PAWN : return new Pawn(isWhite);
		default : return new King(isWhite);
		}
	}
}
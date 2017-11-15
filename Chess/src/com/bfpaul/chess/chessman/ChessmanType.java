package com.bfpaul.chess.chessman;

// Chessman(ü����)�� Ÿ�԰� �� Ÿ���� ���� ���� ���� �� �ʱ� ���� ���� �����ش�.
public enum ChessmanType{
	KING(1),
	QUEEN(1),
	BISHOP(2),
	KNIGHT(2),
	ROOK(2),
	PAWN(8);
//	�� ���� �ʱ� ����
	private final int INIT_COUNT;

	private ChessmanType(int initCount) {
		this.INIT_COUNT = initCount;
	}
// �� ���� �ʱ� ������ ��ȯ�Ѵ�.	
	public int getInitCount() {
		return INIT_COUNT;
	}
	
//	Chessman createChessman(boolean isWhite, ChessmanType type) {
//		switch(type) {
//		case KING : return new King(isWhite);
//		case QUEEN : return new Queen(isWhite);
//		case BISHOP : return new Bishop(isWhite);
//		case KNIGHT : return new Knight(isWhite);
//		case ROOK : return new Rook(isWhite);
//		case PAWN : return new Pawn(isWhite);
//		default : return new King(isWhite);
//		}
//	}
}
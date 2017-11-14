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
}

package com.bfpaul.chess.chessman;
// Chessman(ü����)�� Ÿ�԰� �� Ÿ���� ���� ���� �ִ� ������ �������ش�.
public enum ChessmanType{
	KING(1),
	QUEEN(1),
	BISHOP(2),
	KNIGHT(2),
	ROOK(2),
	PAWN(8);
//	�� ���� �ִ� ��
	private final int MAX_COUNT;

	private ChessmanType(int maxCount) {
		this.MAX_COUNT = maxCount;
	}
// �� ���� �ִ� ���� ��ȯ�Ѵ�.	
	public int getMaxCount() {
		return MAX_COUNT;
	}
}

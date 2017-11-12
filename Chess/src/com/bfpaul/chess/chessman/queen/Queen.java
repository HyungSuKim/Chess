package com.bfpaul.chess.chessman.queen;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//���� �Ӽ��� �������ִ�.
//���� �Ӽ��� ������ �� �ִ� ĭ ���� �������ִ�.
@SuppressWarnings("serial")
public class Queen extends Chessman {
// ���� ������ �� �ִ� ĭ ���� ��� ������ ����Ǵ� ���� �Ӽ��̴�.
	private static final int MOVEABLE_SQUARE_COUNT = 7;
//	���� �Ӽ��� ���� ü������ �����Ѵ�. 
	public Queen(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.QUEEN);
	}
}

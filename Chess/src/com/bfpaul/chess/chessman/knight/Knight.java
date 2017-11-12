package com.bfpaul.chess.chessman.knight;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//����Ʈ�� �Ӽ��� �������ִ�.
//����Ʈ �Ӽ��� ������ �� �ִ� ĭ ���� �������ִ�.
@SuppressWarnings("serial")
public class Knight extends Chessman {
//	����Ʈ�� ������ �� �ִ� ĭ ���� ��� ����Ʈ���� ����Ǵ� ���� �Ӽ��̴�.
	private static final int MOVEABLE_SQUARE_COUNT = 3;
//	����Ʈ�� �Ӽ��� ���� ü������ �����Ѵ�. 
	public Knight(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.KNIGHT);
		
	}

}

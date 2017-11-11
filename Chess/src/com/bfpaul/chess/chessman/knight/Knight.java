package com.bfpaul.chess.chessman.knight;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman(ü����) ��Ű���� ���Ե� Knight�� ���� ���ְ� �� Knight�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Knight extends Chessman {
//	����Ʈ�� ������ �� �ִ� ĭ���� ��Ÿ���� ����ν� static���� �������� ������ �ν��Ͻ� ���� ���� ������ �ش� ������ ������ �ְ� �ϱ������̴�.
	private static final int MOVEABLE_SQUARE = 3;
//	����Ʈ�� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����Ʈ�̹����� �����ͼ� ��������ش�.
	public Knight(boolean isWhite) {
//	�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ���� ����Ʈ�� ��� 2ĭ �������� �����̰� 1ĭ �밢������ �����δ� ���
//	�������� 3ĭ�� �����ϼ� ������ ����Ŭ������ MOVEABLE_SQUARE�� 3���� �ʱ�ȭ ���־���.
//  �׸��� �����ϰ��� �ϴ� ChessmanType�� ���������ν� �θ� Ŭ������ �����ڰ� Ÿ�Կ� �´� �̹����� ���� �� �� �ֵ��� ���ش�.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.KNIGHT);
		
	}

}

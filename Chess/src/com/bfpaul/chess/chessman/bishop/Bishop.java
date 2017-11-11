package com.bfpaul.chess.chessman.bishop;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman(ü����) ��Ű���� ���Ե� bishop�� ���ְ� �� bishop�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
//	����� ������ �� �ִ� ĭ���� ��Ÿ���� ����ν� static���� �������� ������ �ν��Ͻ� ���� ���� ������ �ش� ������ ������ �ְ� �ϱ������̴�.
	private static final int MOVEABLE_SQUARE = 7;
//	����� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����̹����� �����ͼ� ��������ش�.
	public Bishop(boolean isWhite) {
//	�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� �� ����� �̵������� square�� ������ �����Ѵ�.
//	����� �밢������ �̵��ϱ�� ������ �ִ�� ������ 7ĭ�� ������ �� �ִٴ� �������� 7�� ������ �����ڿ� �Ķ���ͷ� �����Ͽ���.
//  �׸��� �����ϰ��� �ϴ� ChessmanType�� ���������ν� �θ� Ŭ������ �����ڰ� Ÿ�Կ� �´� �̹����� ���� �� �� �ֵ��� ���ش�.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.BISHOP);
	}
}

package com.bfpaul.chess.chessman.queen;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman ��Ű�� ������ Queen�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Queen extends Chessman {
//	���� ������ �� �ִ� ĭ���� ��Ÿ���� ����ν� static���� �������� ������ �ν��Ͻ� ���� ���� ������ �ش� ������ ������ �ְ� �ϱ������̴�.
	private static final int MOVEABLE_SQUARE = 7;
//	���� �����ڷν� ����̸� ��� �� �̹����� ���� ���� �����ϰ� �ƴϸ� ���� �� �̹����� ���� ���� �����Ѵ�.
	public Queen(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ���� ���� ĭ�� ���� Ȥ�� �밢������ �̵��Ҽ��ִµ�
//		�̶� ���� �ִ�� �̵������� ĭ���� 7�̶� �Ǵ��ؼ� ������ MOVEABLE_SQUARE�� 7�� �ʱ�ȭ���־���.
//  �׸��� �����ϰ��� �ϴ� ChessmanType�� ���������ν� �θ� Ŭ������ �����ڰ� Ÿ�Կ� �´� �̹����� ���� �� �� �ֵ��� ���ش�.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.QUEEN);
	}
}

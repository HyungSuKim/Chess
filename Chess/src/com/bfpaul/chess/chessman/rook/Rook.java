package com.bfpaul.chess.chessman.rook;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman ��Ű�� ������ Rook�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Rook extends Chessman {
//	���� ������ �� �ִ� ĭ���� ��Ÿ���� ����ν� static���� �������� ������ �ν��Ͻ� ���� ���� ������ �ش� ������ ������ �ְ� �ϱ������̴�.
	private static final int MOVEABLE_SQUARE = 7;
//	rook�� ĳ����(�� ŷ �Ѵ� ������ ���� ����ߵ�)�� �����ϱ� ���� ������ �־�� �� �Ӽ��̶� �ǴܵǾ� ������ ����(�⺻�� false)
	private boolean isMoved;
//	���� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ���̹����� �����ͼ� ��������ش�.
	public Rook(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ����
//		���� ��� ��������(�����¿�) �ִ� 7ĭ�� �����ϼ� �ִٴ� �������� ������ MOVEABLE_SQUARE�� 7�� �ʱ�ȭ���־���.
//  �׸��� �����ϰ��� �ϴ� ChessmanType�� ���������ν� �θ� Ŭ������ �����ڰ� Ÿ�Կ� �´� �̹����� ���� �� �� �ֵ��� ���ش�.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.ROOK);
	}

//	�������� �߻������� ���������� ǥ���ϱ����� �޼���
	public void setIsMoved() {
		isMoved = true;
	}
//	���������� �ƴ��� ��ȯ�ϴ� �޼���
	public boolean getIsMoved() {
		return isMoved;
	}

}

package com.bfpaul.chess.chessman.king;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
//chessman(ü����) ��Ű���� ���Ե� King�� ���� ���ְ� �� King�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class King extends Chessman {
//	ŷ�� ������ �� �ִ� ĭ���� ��Ÿ���� ����ν� static���� �������� ������ �ν��Ͻ� ���� ���� ������ �ش� ������ ������ �ְ� �ϱ������̴�.
	private static final int MOVEABLE_SQUARE = 1;
//	���� king�� rook�� ĳ����(�Ѵ� ������ ���� ����ߵ�)�� �����ϱ� ���� ������ �־�� �� �Ӽ��̶� �ǴܵǾ� ������ ����(�⺻�� false)
	private boolean isMoved;
	
//	ŷ�� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ŷ�̹����� �����ͼ� ��������ش�.
	public King(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� �� ���� ��� ��������(�����¿�,�밢��)
//		1ĭ�� �����ϼ� �ִٴ� �������� ������ MOVEABLE_SQUARE ����� 1�� �ʱ�ȭ���ش�.
//  �׸��� �����ϰ��� �ϴ� ChessmanType�� ���������ν� �θ� Ŭ������ �����ڰ� Ÿ�Կ� �´� �̹����� ���� �� �� �ֵ��� ���ش�.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.KING);
	}
	
//	�������� �߻������� ���������� ǥ���ϱ����� �޼���
	public void setIsMoved() {
		isMoved = true;
	}
	
//	���������� �ƴ��� �˼��ִ� �޼���
	public boolean getIsMoved() {
		return isMoved;
	}
}

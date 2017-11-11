package com.bfpaul.chess.chessman.pawn;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
// chessman ��Ű�� ������ pawn�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Pawn extends Chessman {
//	���� ������ �� �ִ� ĭ���� ��Ÿ���� ����ν� static���� �������� ������ �ν��Ͻ� ���� ���� ������ �ش� ������ ������ �ְ� �ϱ������̴�.
	private static final int MOVEABLE_SQUARE = 1;
//	���� ���������� �ƴ��� ������ �������ִ� ����
	private boolean isMoved;
	
//	���� �����ڷν� ����̸� ��� �� �̹����� ���� ���� �����ϰ� �ƴϸ� ���� �� �̹����� ���� ���� �����Ѵ�.
	public Pawn(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ����
//		���� ù��° �������� �����ϰ� 1�� �����ϼ� �����Ƿ� ����Ŭ������ MOVEABLE_SQUARE�� 1�� �ʱ�ȭ���־���.
//  �׸��� �����ϰ��� �ϴ� ChessmanType�� ���������ν� �θ� Ŭ������ �����ڰ� Ÿ�Կ� �´� �̹����� ���� �� �� �ֵ��� ���ش�.
		super(isWhite, MOVEABLE_SQUARE, ChessmanType.PAWN);
	}

//	���� ������ �� �ִ� square�� ���� ��ȯ�ϴ� �޼���ν� ����Ŭ������ getMoveableSquare �޼��带 �������̵��ؼ� �����ߴ�. 
//	���࿡ �������� �������� ù�����ӿ��� �����ϼ� �ִ� ĭ���� ��ȯ�ϰ� ù �������� �־��ٸ� �⺻���� �����ϼ� �ִ� 1�� ��ȯ���ش�.
	@Override
	public int getMoveableSquare() {
		if(!isMoved) {
			return super.getMoveableSquare() * 2;
		} else {
			return super.getMoveableSquare();
		}
	}
	
//	ù ������ ���� ���������� �������ֱ����� �޼���
	public void setIsMoved() {
		isMoved = true;
	}
	
//	isMoved�� ��ȯ�޾Ƽ� �������� �־����� �������� Ȯ���ϱ� ���� �޼���
	public boolean getIsMoved() {
		return isMoved;
	}
}
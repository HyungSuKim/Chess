package com.bfpaul.chess.chessman.pawn;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanType;
// ���� �Ӽ��� �������ִ�.
// ���� ������ �� �ִ� ĭ���� ù �������� �־����� �ƴ����� �Ӽ��� �ִ�.
@SuppressWarnings("serial")
public class Pawn extends Chessman {
//	���� ������ �� �ִ� ĭ ���� ��� ������ ����Ǵ� ���� �Ӽ��̴�.
	private static final int MOVEABLE_SQUARE_COUNT = 1;
//	���� ���������� �ƴ����� �Ӽ����ν� ù �������� ���� ���¿��� 2ĭ�� ������ �� �ִ� ���� Ư���� ��Ÿ�������Ͽ� ���ȴ�.
	private boolean isMoved;
	
//	���� �Ӽ��� ���� ü������ �����Ѵ�.  
	public Pawn(boolean isWhite) {
		super(isWhite, MOVEABLE_SQUARE_COUNT, ChessmanType.PAWN);
	}

//	���� ������ �� �ִ� square�� ���� ��ȯ�ϸ� ����Ŭ������ getMoveableSquareCount()�� �������̵� �Ѵ�. 
//	���࿡ �������� �������� ù�����ӿ��� �����ϼ� �ִ� ĭ �� 2�� ��ȯ�ϰ� ù �������� �־��ٸ� �⺻���� �����ϼ� �ִ� 1�� ��ȯ���ش�.
	@Override
	public int getMoveableSquareCount() {
		if(!isMoved) {
			return super.getMoveableSquareCount() * 2;
		} else {
			return super.getMoveableSquareCount();
		}
	}
	
//	ù ������ ���� ���������� �������ش�.
	public void setIsMoved() {
		isMoved = true;
	}
	
//	�������� �־����� ���������� ��ȯ�Ѵ�.
	public boolean getIsMoved() {
		return isMoved;
	}
}
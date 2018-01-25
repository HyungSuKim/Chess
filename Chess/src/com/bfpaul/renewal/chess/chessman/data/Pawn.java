package com.bfpaul.renewal.chess.chessman.data;

import java.awt.Image;

import com.bfpaul.renewal.chess.route.Direction;
/*
 * Pawn�� Ư���� ���� ü�����ν��� ������ �ϱ����ؼ� ���������.   
 * Pawn�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * isMoved : Pawn�� ù �������� �ֱ��� 2ĭ�� ������ �� �ִٴ� ���� ǥ���ϱ����� ����
 */
public class Pawn extends Chessman implements SpecialChessmanMovedIndicator {
	// ���� ���������� ��Ÿ���� �����ͷν� �ʿ��� �������, �̴� ��� chessman�� �����ϰ� ����ȴ�.
	private final boolean IS_WHITE;
	
	// ���� ������ ĭ���� ��Ÿ���� �����ͷν� �ʿ��� �������, ���� �ڱ� �ڽ��� ������ ĭ���� ����ؾ� �� �ʿ伺�� �ִ�.
	// �ٸ� ����� �ٸ��� ���� 2ĭ�� ���������� �߻��ϴ� �̺�Ʈ�� �ִµ� �̴� ������ ����� isMoved������� �����δ� ǥ�� �� �� ���ٰ� �Ǵ��ߴ�.
	// �� ������ Ư���� ������(2ĭ�� ������ �� �ִ� ��Ȳ)���� 1ĭ�� �����̴� ��쵵 �����ϱ� �����̴�.
	private int movedSquareCount;
	
	public Pawn(boolean isWhite) {
		IS_WHITE = isWhite;
	}
	
	@Override
	public Direction[] getDirection() {
		if(IS_WHITE) {
			return new Direction[] { Direction.UP };
		} else {
			return new Direction[] { Direction.DOWN };
		}
	}
	
	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.PAWN);
	}

	@Override
	public boolean isWhite() {
		return IS_WHITE;
	}

	@Override
	public int getMoveableSquareCount() {
		if(movedSquareCount == 0) {
			return 2;
		} else {
			return 1;
		}
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.PAWN;
	}

	@Override
	public void setMovedSquareCount(int squareCount) {
		movedSquareCount = squareCount;
	}

	public int getMovedSquareCount() {
		return movedSquareCount;
	}

	@Override
	public boolean isMoved() {
		if(movedSquareCount == 0) {
			return false;
		} else {
			return true;
		}
	}
}

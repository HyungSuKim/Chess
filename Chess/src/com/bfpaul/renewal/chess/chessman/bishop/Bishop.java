package com.bfpaul.renewal.chess.chessman.bishop;

import java.awt.Image;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.ChessmanImage;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.chessman.Direction;
/*
 * Bishop�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * Bishop�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * moveableSquareCount() { return -1 } : Bishop�� �������� ĭ���� ������ ������ ǥ���ϰ��� �ߴ�.
 */
public class Bishop implements Chessman {
	
	private final boolean IS_WHITE;
	
	public Bishop(boolean isWhite) {
		IS_WHITE = isWhite;
	}

	@Override
	public Direction[] getDirection() {
		return new Direction[] { Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT };
	}

	@Override
	public boolean isWhite() {
		return IS_WHITE;
	}

	@Override
	public int getMoveableSquareCount() {
		return -1;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.BISHOP);
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.BISHOP;
	}

}

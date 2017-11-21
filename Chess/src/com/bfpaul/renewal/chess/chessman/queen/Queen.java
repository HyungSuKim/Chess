package com.bfpaul.renewal.chess.chessman.queen;

import java.awt.Image;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.ChessmanImage;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.chessman.Direction;
/*
 * Queen�� Ư���� ���� ü�����ν��� ������ �ϱ����ؼ� ���������.   
 * Queen�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * moveableSquareCount() { return -1 } : Queen�� �������� ĭ���� ������ ������ ǥ���ϰ��� �ߴ�.
 */
public class Queen implements Chessman {
	
	private final boolean IS_WHITE;
	
	public Queen(boolean isWhite) {
		IS_WHITE = isWhite;
	}

	@Override
	public Direction[] getDirection() {
		return Direction.values();
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
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.QUEEN);
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.QUEEN;
	}
	
}

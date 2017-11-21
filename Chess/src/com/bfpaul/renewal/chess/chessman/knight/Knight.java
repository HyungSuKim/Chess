package com.bfpaul.renewal.chess.chessman.knight;

import java.awt.Image;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.ChessmanImage;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.chessman.Direction;
/*
 * Knight�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * Knight�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * Direction[] getDirection() { return Direction.values(); }
 * -> Knight�� �̵��� UP, DOWN, RIGHT, LEFT�� 2ĭ �̵� �� UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT�� 1ĭ �̵��Ѵٰ� �����Ͽ���.
 */
public class Knight implements Chessman {
	
	private final boolean IS_WHITE;
	
	public Knight(boolean iswhite) {
		IS_WHITE = iswhite;
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
		return 3;
	}

	@Override
	public Image getChessmanImage() {
		return ChessmanImage.getChessmanImage(IS_WHITE, ChessmanType.KNIGHT);
	}

	@Override
	public ChessmanType getChessmanType() {
		return ChessmanType.KNIGHT;
	}
	

}

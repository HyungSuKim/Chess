package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/*
 * Knight�� Ư���� ���� ü�����ν� ���� �ϱ����ؼ� ���������.   
 * Knight�� ǥ���� ���� �̹����� �̵��� ���� ������ �����Ѵ�.
 * 
 * Direction[] getDirection() { return Direction.values(); }
 * -> Knight�� �̵��� UP, DOWN, RIGHT, LEFT�� 2ĭ �̵� �� UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT�� 1ĭ �̵��Ѵٰ� �����Ͽ���.
 * 
 *  * 	public Direction[] getDirection() {	return new Direction[] { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT }; }
 * �������� ��� 1 -> Knight�� �̵��� UP, DOWN, RIGHT, LEFT�� 2ĭ �̵���
 * 				UP/DOWN�� ��� LEFT�� RIGHT�� LEFT/RIGHT�� ��� UP�̳� DOWN���� 1ĭ �̵��Ѵ�
 * 				<��Ű����� ������ ��� 1�� �����ϰ� �����ϰ��ִ� 2ĭ ���� �� �¿� 1ĭ�̵�>
 * �������� ��� 2 -> Knight�� �̵��� UP, DOWN, RIGHT, LEFT�� 1ĭ �̵���
 * 				UP�� ��� UP_LEFT, UP_RIGHT�� / DOWN�� ��� DOWN_LEFT, DOWN_RIGHT��
 * 				LEFT�� ��� UP_LEFT, DOWN_LEFT�� / RIGHT�� ��� UP_RIGHT�� DOWN_RIGHT�� 1ĭ �̵��Ѵ�.
 */
public class Knight extends Chessman {
	
	private final boolean IS_WHITE;
	
	public Knight(boolean iswhite) {
		IS_WHITE = iswhite;
	}

	@Override
	public Direction[] getDirection() {
		return new Direction[] { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT };
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

package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/* 
 * ���� �ٸ� ü�������� �ϳ��� ����� ������ ó���ϱ� ���ؼ� ���������.
 * ���忡 ü������ ���ų� ������� �ϱ����ؼ��� �̹����� �ʿ��ϴ�. Chessman�� �����ϴ� ���� Ŭ�������� �� �ʿ��� �̹��� �����͸� �������ִ�.
 * Chessman�� �����ϴ� ���� Ŭ�������� ü������ �̵��ϱ� ���� �ʿ��� �����͵��� �������ش�.
 * 
 * MOVEABLE_SQUARE_COUNT : -1 -> �̵������� ĭ�� ���� �������� ���� ���� (Queen, Bishop, Rook)
 */
public abstract class Chessman {
	
	public abstract Direction[] getDirection();
	
	public abstract boolean isWhite();
	
	public abstract int getMoveableSquareCount();
	
	public abstract Image getChessmanImage();
	
	public abstract ChessmanType getChessmanType();
}

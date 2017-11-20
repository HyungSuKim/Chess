package com.bfpaul.renewal.chess.chessman;

import java.awt.Image;
/* 
 * ���� �ٸ� ü�������� �ϳ��� ����� ������ ó���ϱ� ���ؼ� ���������.
 * ���忡 ü������ ���ų� ������� �ϱ����ؼ��� �̹����� �ʿ��ϴ�. Chessman�� �����ϴ� ���� Ŭ�������� �� �ʿ��� �̹��� �����͸� �������ִ�.
 * Chessman�� �����ϴ� ���� Ŭ�������� ü������ �̵��ϱ� ���� �ʿ��� �����͵��� �������ش�.
 * 
 * MOVEABLE_SQUARE_COUNT : -1 -> �̵������� ĭ�� ���� �������� ���� ���� (Queen, Bishop, Rook)
 */
interface Chessman {
	
	Direction[] getDirection();
	
	boolean isWhite();
	
	int getMoveableSquareCount();
	
	Image getChessmanImage();
	
	
}

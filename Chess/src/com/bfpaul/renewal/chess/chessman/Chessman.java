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
	
	private static final Chessman INVALID_CHESSMAN = createInvalidChessman();
	
	public abstract Direction[] getDirection();
	
	public abstract boolean isWhite();
	
	public abstract int getMoveableSquareCount();
	
	public abstract Image getChessmanImage();
	
	public abstract ChessmanType getChessmanType();
	
	private static Chessman createInvalidChessman() {
		return new Chessman() {

			@Override
			public Direction[] getDirection() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isWhite() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getMoveableSquareCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Image getChessmanImage() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ChessmanType getChessmanType() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}
	
	public static Chessman getInvalidChessman() {
		return INVALID_CHESSMAN;
	}
	
	public boolean isValid() {
		return this != INVALID_CHESSMAN;
	}
}

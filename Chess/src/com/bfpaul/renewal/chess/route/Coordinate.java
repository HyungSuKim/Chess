package com.bfpaul.renewal.chess.route;

public class Coordinate {
	private final static int MINIMUM_X = 0;
	private final static int MAXIMUM_X = 7;
	private final static int MINIMUM_Y = 0;
	private final static int MAXIMUM_Y = 7;

	private int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// �ǹ̾��� ��ǥ�� �����ϱ� ���� �޼���ν� null ��� �ǹ̾��� ��ǥ�� ǥ���ϰ� �����ϰ��� �ߴ�. 
	public static Coordinate createInValidateCoordinate() {
		return new Coordinate(-1, -1);
	}
	
	// �ǹ̾��� ��ǥ���� �����ϴ� �޼����̴�.
	public boolean isInValidate() {
		return (x == -1 && y == -1);
	}

	public static boolean isValidate(int x, int y) {
		return (MINIMUM_X <= x && x <= MAXIMUM_X) && (MINIMUM_Y <= y && y <= MAXIMUM_Y);
	}

	public static int getMaximumX() {
		return MAXIMUM_X;
	}

	public static int getMaximumY() {
		return MAXIMUM_Y;
	}

	public static int getMinimumX() {
		return MINIMUM_X;
	}

	public static int getMinimumY() {
		return MINIMUM_Y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}

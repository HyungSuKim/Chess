package com.bfpaul.renewal.chess.route;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.Knight;

/**
 * MoveableRouteCalculator�� ü������ ������ �� �ִ� ��θ� ������ֱ����� Ŭ�����̴�.
 * ü������ �Ӽ��� Direction, moveableSquareCount�� Coordinate�� �̿��ؼ�
 * ���⺰�� �����ϼ� �ִ� ��ǥ���� ���� Data�� MoveableRoute�� �����Ѵ�.
 * �� MoveableRoute���� ArrayList�� ��� ��ȯ�����ν� BoardPanel�� ���� �ʿ��� ������
 * ü������ ���� �� ���ִ� ����� ��ǥ���� Data�� Ȱ�� �� �� �ְԲ� ���ֱ� ���ؼ� �ʿ��ϴ�. 
 * @author ������
 */
public class MoveableRouteCalculator {
	private static final int NONE_DIRECTION = 0; // ������ ������ ��Ÿ���� ���
	private static final int UP_DIRECTION = -1; // ����, ����(��ǥ�� �۾����� ����)�� ��Ÿ���� ���
	private static final int DOWN_DIRECTION = 1; // �Ʒ���, ������(��ǥ�� Ŀ���� ����)�� ��Ÿ���� ���
	
	private static Coordinate initCoordinate;
	private static Chessman selectedChessman;

	private MoveableRouteCalculator() {
	}
	
	/**
	 * �ܺ�(BoardPanel)���� ü������ �������� �� ȣ��Ǵ� �޼���ν� MoveableRouteCalculator���� �����ϰ� ���� �Ǿ��ִ� �޼����̴�.
	 * getChessmanMoveableRouteList�� ���õ� ü����, ü������ �����ִ� x, y ��ǥ�� �̿��ؼ� 
	 * �ٸ� �޼������ ü������ ���� �� �� �ִ� ��θ� �����ؼ� ��ȯ �� �� �ֵ����Ѵ�.
	 * ü������ Knight�� ���� �׷��� ���� ���� �����Ͽ� ��θ� ��ȯ�Ѵ�.
	 * @param chessman : ���õ� ü����
	 * @param x : ü������ �����ִ� x ��ǥ
	 * @param y : ü������ �����ִ� y ��ǥ
	 * @return : ü������ ���������� ���� �� ���ִ� MoveableRoute���� ���� ArrayList�� ��ȯ�Ѵ�. 
	 */
	public static ArrayList<MoveableRoute> getChessmanMoveableRouteList(Chessman chessman, int x, int y) {
		initCoordinate = new Coordinate(x, y);
		selectedChessman = chessman;
		
		int moveableSquareCount = selectedChessman.getMoveableSquareCount();

		if (selectedChessman instanceof Knight) {

			return getKnightRoute(moveableSquareCount, x, y);

		} else {
			
			return getCommonChessmanRoute(moveableSquareCount, x, y);
			
		}
	}

	/**
	 * getKnightRoute �޼���� ����Ʈ�� ���� �� ���ִ� ��θ���Ʈ(Direction, Coordinate[])�� �����ؼ� ��ȯ�ϴ� �޼����̴�.
	 * x, y �׸��� Coordinate�� MaximumX/Y, MinimunX/Y�� �̿��ؼ� �� �������� ������ �� �ִ� ĭ���� ����Ѵ�.(up/down/left/rightMoveableCount) 
	 * ����Ʈ�� �ֿ� Ư¡�� �ѹ������� 2ĭ�� �̵��ϰ� ���Ʒ� Ȥ�� ���ʿ��������� 1ĭ �̵� �Ѵٴ� ���̴�. ��, �ʱ� 2ĭ�� ���� �� �����ٸ� �̵���ü�� �Ұ��� ���̴�.
	 * �̸� �ذ��ϱ����� isAvailMoveTwoSquares�� �̿��ؼ� �ش�������� 2ĭ�� �����̴� ���� �������� �����ϰ�
	 * �����ϴٸ� ���⿡ �°� calX �Ǵ� calY�� +2 ������ �ѵڿ�
	 * 1ĭ �̵��ϴ� ��ǥ�� ����Ͽ� MoveableRoute�� �����ϰ� List�� �߰��ϴ� ������� �����Ͽ���.
	 * 
	 * @param moveableSquareCount : ����Ʈ�� ���� �� ���ִ� ĭ���� 3�̴�.
	 * @param x : ����Ʈ�� ���� x ��ǥ
	 * @param y : ����Ʈ�� ���� y ��ǥ
	 * @return : moveableRouteList : ����Ʈ�� ���� �� �� �ִ� ��θ� MoveableRoute(����, ��ǥ��)�ν� ������ ����Ʈ
	 */
	private static ArrayList<MoveableRoute> getKnightRoute(int moveableSquareCount, int x, int y) {
		ArrayList<MoveableRoute> moveableRouteList = new ArrayList<>();
		
		int upMoveableCount = Math.abs(Coordinate.getMinimumY() - y);
		int downMoveableCount = Math.abs(Coordinate.getMaximumY() - y);
		int leftMoveableCount = Math.abs(Coordinate.getMinimumX() - x);
		int rightMoveableCount = Math.abs(Coordinate.getMaximumX() - x);

		for (Direction direction : selectedChessman.getDirection()) {
			int calX = x;
			int calY = y;
			
			switch (direction) {
			case UP:
				if(isAvailMoveTwoSquares(moveableSquareCount, upMoveableCount)) {
					calY += (UP_DIRECTION * 2);
					
					moveableRouteList.add(new MoveableRoute(Direction.UP, getLeftCoordinate(calX, calY)));
					moveableRouteList.add(new MoveableRoute(Direction.UP, getRightCoordinate(calX, calY)));
				}

				break;
			case DOWN:
				if(isAvailMoveTwoSquares(moveableSquareCount, downMoveableCount)) {
					calY += (DOWN_DIRECTION * 2);
					
					moveableRouteList.add(new MoveableRoute(Direction.UP, getLeftCoordinate(calX, calY)));
					moveableRouteList.add(new MoveableRoute(Direction.UP, getRightCoordinate(calX, calY)));
				}

				break;
			case LEFT:
				if(isAvailMoveTwoSquares(moveableSquareCount, leftMoveableCount)) {
					calX += (UP_DIRECTION * 2);
					
					moveableRouteList.add(new MoveableRoute(Direction.LEFT, getUpCoordinate(calX, calY)));
					moveableRouteList.add(new MoveableRoute(Direction.LEFT, getDownCoordinate(calX, calY)));
				}

				break;
			case RIGHT:
				if(isAvailMoveTwoSquares(moveableSquareCount, rightMoveableCount)) {
					calX += (DOWN_DIRECTION * 2);
					
					moveableRouteList.add(new MoveableRoute(Direction.RIGHT, getUpCoordinate(calX, calY)));
					moveableRouteList.add(new MoveableRoute(Direction.RIGHT, getDownCoordinate(calX, calY)));
				}
				
				break;
			default:	break;
			}
		}
		
		return moveableRouteList;
	}

	/** 
	 * Knight�� �ʱ⿡ ��� �������� 2ĭ�� �����̴� ���� �������� �Ǻ��ϴ� �޼����̴�.
	 * getAvailMoveableCount�� ȣ���ؼ� �� ���� 2�϶��� ��ȿ�� ������ �Ǵ��Ѵ�.
	 * 
	 * @param moveableSquareCount : knight�� ������ ���ִ� ĭ �� ( 3 )
	 * @param directionMoveableCount : �ش� �������� ���� �� �� �ִ� ĭ ��
	 * @return ������ �� �ִ� ĭ ����  2ĭ �̶��  �� �ƴ϶�� ������ ��ȯ�Ѵ�.
	 */
	private static boolean isAvailMoveTwoSquares(int moveableSquareCount, int directionMoveableCount) {
		return getAvailMoveableCount(moveableSquareCount - 1, directionMoveableCount) == 2;
	}
	
	// �Է¹��� x, y�������� �������� 1ĭ �̵��� ��ȿ�� ��ǥ�� ��ȯ�ϴ� �޼���
	private static Coordinate[] getUpCoordinate(int x, int y) {
		Coordinate[] upCoordinate = new Coordinate[1];
		upCoordinate[0] = getValidCoordinate(x, y + UP_DIRECTION);
		return upCoordinate;
	}
	
	// �Է¹��� x, y�������� �Ʒ������� 1ĭ �̵��� ��ȿ�� ��ǥ�� ��ȯ�ϴ� �޼���
	private static Coordinate[] getDownCoordinate(int x, int y) {
		Coordinate[] downCoordinate = new Coordinate[1];
		downCoordinate[0] = getValidCoordinate(x, y + DOWN_DIRECTION);
		return downCoordinate;
	}
	
	// �Է¹��� x, y�������� �������� 1ĭ �̵��� ��ȿ�� ��ǥ�� ��ȯ�ϴ� �޼���	
	private static Coordinate[] getLeftCoordinate(int x, int y) {
		Coordinate[] leftCoordinate = new Coordinate[1];
		leftCoordinate[0] = getValidCoordinate(x + UP_DIRECTION, y);
		return leftCoordinate;
	}
	
	// �Է¹��� x, y�������� ���������� 1ĭ �̵��� ��ȿ�� ��ǥ�� ��ȯ�ϴ� �޼���
	private static Coordinate[] getRightCoordinate(int x, int y) {
		Coordinate[] rightCoordinate = new Coordinate[1];
		rightCoordinate[0] = getValidCoordinate(x + DOWN_DIRECTION, y);
		return rightCoordinate;
	}
	
	/**
	 * �Էµ� x, y ���� �̿��ؼ� ��ȿ�� ��ǥ�� ��ȯ�ϴ� �޼����̴�.
	 * @param x : �Էµ� x ��ǥ
	 * @param y : �Էµ� y ��ǥ
	 * @return �Էµ� x/y ��ǥ�� ��ȿ�� ��ǥ�� ���(�������� ����� �ʾ������) �ش� x, y�� ���� Coordinate�� ��ȯ�ϰ�
	 * 			�׷��� �ʴٸ� �ʱ� ��ǥ�� ��ȯ�Ѵ�.
	 */
	private static Coordinate getValidCoordinate(int x, int y) {
		if(Coordinate.isValidate(x, y)) {
			return new Coordinate(x, y);
		} else {
			return initCoordinate;
		}
	}
	
	/**
	 * getCommonChessmanRoute �޼���� ����Ʈ�� ������ ü�������� ���� �� ���ִ� ��θ���Ʈ(Direction, Coordinate[])�� �����ؼ� ��ȯ�ϴ� �޼����̴�.
	 * �� �޼���� ���õ� ü������ ������ �� �ִ� Direction, moveableSquareCount, x, y�� �̿��ؼ� ��θ���Ʈ�� �����Ѵ�.
	 * x, y �׸��� Coordinate�� MaximumX/Y, MinimunX/Y�� �̿��ؼ� �� �������� ������ �� �ִ� ĭ���� ����Ѵ�.(up/down/left/rightMoveableCount) 
	 * �� Direction ����  ����MoveableCount�� moveableSquareCount�� �̿��Ͽ� ü������ ������ ������ �� �ִ� ĭ���� ���Ѵ�(realMoveableCount)
	 * �׸��� �� ���⺰�� xDir, yDir�� �������־� ���⿡ �´� ���� ���� Coordinate[]�� �����Ͽ� moveableRoute�� �����ؼ� List�� �����Ͽ� ��ȯ�Ѵ�. 
	 * 
	 * @param moveableSquareCount : ü������ ���� �� �� �ִ� ĭ���� -1 �� ���Ѵ�, �� �ܴ̿� ������ ĭ �� �̴�.
	 * @param x : ü������ ���� x ��ǥ
	 * @param y : ü������ ���� y ��ǥ
	 * @return : moveableRouteList : ü������ ���� �� �� �ִ� ��θ� MoveableRoute(����, ��ǥ��)�ν� ������ ����Ʈ
	 */

	private static ArrayList<MoveableRoute> getCommonChessmanRoute(int moveableSquareCount, int x, int y) {
		ArrayList<MoveableRoute> moveableRouteList = new ArrayList<>();

		int realMoveableCount = 0;
		int diagonalMoveableCount = 0;

		int upMoveableCount = Math.abs(Coordinate.getMinimumY() - y);
		int downMoveableCount = Math.abs(Coordinate.getMaximumY() - y);
		int leftMoveableCount = Math.abs(Coordinate.getMinimumX() - x);
		int rightMoveableCount = Math.abs(Coordinate.getMaximumX() - x);
		
		for (Direction direction : selectedChessman.getDirection()) {
			
			int xDir = NONE_DIRECTION;
			int yDir = NONE_DIRECTION;

			switch (direction) {
			case UP:
				yDir = UP_DIRECTION;
				
				if(moveableSquareCount == -1) { // ������ �� �ִ� ĭ���� ���Ѵ��϶�
					realMoveableCount = upMoveableCount;
				} else { // ������ �� �ִ� ĭ���� ������������
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, upMoveableCount);
				}
				
				break;
			case DOWN:
				yDir = DOWN_DIRECTION;
				
				if(moveableSquareCount == -1) { // ������ �� �ִ� ĭ���� ���Ѵ��϶�
					realMoveableCount = downMoveableCount;
				} else { // ������ �� �ִ� ĭ���� ������������
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, downMoveableCount);
				}

				break;
			case LEFT:
				xDir = UP_DIRECTION;
				
				if(moveableSquareCount == -1) { // ������ �� �ִ� ĭ���� ���Ѵ��϶�
					realMoveableCount = leftMoveableCount;
				} else { // ������ �� �ִ� ĭ���� ������������
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, leftMoveableCount);
				}

				break;
			case RIGHT:
				xDir = DOWN_DIRECTION;
				
				if(moveableSquareCount == -1) { // ������ �� �ִ� ĭ���� ���Ѵ��϶�
					realMoveableCount = rightMoveableCount;
				} else { // ������ �� �ִ� ĭ���� ������������
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, rightMoveableCount);
				}
				

				break;
			case UP_LEFT:
				xDir = UP_DIRECTION;
				yDir = UP_DIRECTION;
				
				if(moveableSquareCount == -1) { // ������ �� �ִ� ĭ���� ���Ѵ��϶�
					realMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, leftMoveableCount);
				} else { // ������ �� �ִ� ĭ���� ������������
					diagonalMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, leftMoveableCount);

					realMoveableCount = getAvailMoveableCount(moveableSquareCount, diagonalMoveableCount);
				}
				
				break;
			case UP_RIGHT:
				xDir = DOWN_DIRECTION;
				yDir = UP_DIRECTION;
				
				if(moveableSquareCount == -1) { // ������ �� �ִ� ĭ���� ���Ѵ��϶�
					realMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, rightMoveableCount);
				} else { // ������ �� �ִ� ĭ���� ������������
					diagonalMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, rightMoveableCount);

					realMoveableCount = getAvailMoveableCount(moveableSquareCount, diagonalMoveableCount);
				}

				break;
			case DOWN_LEFT:
				xDir = UP_DIRECTION;
				yDir = DOWN_DIRECTION;
				
				if(moveableSquareCount == -1) { // ������ �� �ִ� ĭ���� ���Ѵ��϶�
					realMoveableCount = getRealDiagonalMoveableCount(downMoveableCount, leftMoveableCount);
				} else { // ������ �� �ִ� ĭ���� ������������
					diagonalMoveableCount = getRealDiagonalMoveableCount(downMoveableCount, leftMoveableCount);

					realMoveableCount = getAvailMoveableCount(moveableSquareCount, diagonalMoveableCount);
				}

				break;
			case DOWN_RIGHT:
				xDir = DOWN_DIRECTION;
				yDir = DOWN_DIRECTION;
				
				if(moveableSquareCount == -1) {
					realMoveableCount = getRealDiagonalMoveableCount(downMoveableCount, rightMoveableCount);
				} else {
					diagonalMoveableCount = getRealDiagonalMoveableCount(downMoveableCount, rightMoveableCount);

					realMoveableCount = getAvailMoveableCount(moveableSquareCount, diagonalMoveableCount);
				}

				break;
			}

			Coordinate[] coordinates = new Coordinate[realMoveableCount];

			for (int count = 0; count < realMoveableCount; count++) {
				coordinates[count] = getValidCoordinate(x + (xDir * (count + 1)), y + (yDir * (count + 1)));
			}

			moveableRouteList.add(new MoveableRoute(direction, coordinates));
		}

		return moveableRouteList;
	}
	
	/**
	 * getAvailMoveableCount �޼���� ���� �� �� �ִ� ĭ���� �������ִ� ü������ ������� �� �޼����̴�.
	 * �� �޼���� ü������ ���� �� �� �ִ� ĭ���� �ش�������� ���� �� �� �ִ� ĭ������ ū�� ������ ���Ͽ� �� ���� ���� �����Ѵ�.
	 * ü������ ���� �� �� �ִ� ĭ���� �ش�������� ���� �� �� �ִ� ĭ������ ũ�� ü���� ������ ����� �� �� �̱� ������
	 * �� �޼��带 ����ؼ� ������ �� �ִ� ĭ���� ��ȯ�Ѵ�.
	 * 
	 * @param moveableSquareCount : ü������ ���� �� �� �ִ� ĭ��
	 * @param directionMoveableCount : �ش� �������� ���� �� �� �ִ� ĭ��
	 * @return �� �Ķ���� ���� ���� ���� �������ش�.
	 */

	private static int getAvailMoveableCount(int moveableSquareCount, int directionMoveableCount) {
		return moveableSquareCount >= directionMoveableCount ? directionMoveableCount : moveableSquareCount;
	}

	/**
	 * getRealDiagonalMoveableCount �޼���� �밢�� ������ ���� ���� �� �� �ִ� ĭ���� ����� �ֱ� ���� �ʿ��ϴ�.
	 * �밢���� x����� y������ ������ �����̴�. �� ��ǥ�������� x�������� ���� �� ���ִ� ĭ���� 2�̰� y�������� ���� �� �� �ִ� ĭ����
	 * 2��� �밢������ �̵������� ĭ�� 2ĭ�̹Ƿ� ��꿡 ������ ����. ������ x�������� ���� �� ���ִ� ĭ���� 3�̰� y�������� ������ ��
	 * �ִ� ĭ���� 2��� �밢������ �̵������� ĭ�� 2ĭ�̴� �̴� �ݴ��� ��쿡�� ���� ����Ǵ� ���� Ȯ���Ͽ���. ���� �밢���� ��� x,
	 * y�� ������ �� �ִ� ĭ�� �� �� ���� ĭ�� �̻����� �̵��� �� ������ �� ���־���. �� ���� UP_LEFT, UP_RIGHT,
	 * DOWN_LEFT, DOWN_RIGHT���� �����ϰ� �����ϰ� ǥ���� ���� �ϱ� ���� �޼���� �и��Ͽ���.
	 * 
	 * @param xMoveableCount
	 *            : ���� ��ǥ���� x �������� ���� �� �� �ִ� ĭ��
	 * @param yMoveableCount
	 *            : ���� ��ǥ���� y �������� ���� �� �� �ִ� ĭ��
	 * @return �� �Ķ���� ���� ���� ���� �������ش�.
	 */
	private static int getRealDiagonalMoveableCount(int yMoveableCount, int xMoveableCount) {
		return xMoveableCount >= yMoveableCount ? yMoveableCount : xMoveableCount;
	}
}
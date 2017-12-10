package com.bfpaul.renewal.chess.controller.chessman;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.chessman.Knight;
import com.bfpaul.renewal.chess.controller.Coordinate;
import com.bfpaul.renewal.chess.controller.MoveableRoute;

public class MoveableRouteCalculator {
	private static Coordinate initCoordinate;
	private static Chessman selectedChessman;

	private MoveableRouteCalculator() {
	}
	
	public static ArrayList<MoveableRoute> selectChessman(Chessman chessman, int x, int y) {
		System.out.println(x);
		System.out.println(y);
		initCoordinate = new Coordinate(x, y);
		selectedChessman = chessman;
		ArrayList<MoveableRoute> moveableRoute = new ArrayList<>();
		int moveableSquareCount = chessman.getMoveableSquareCount();
		for (Direction direction : chessman.getDirection()) {
			switch (direction) {
			case UP:
				moveableRoute.add(new MoveableRoute(Direction.UP, upRouteCalculator(moveableSquareCount, x, y)));
				break;
			case DOWN:
				moveableRoute.add(new MoveableRoute(Direction.DOWN, downRouteCalculator(moveableSquareCount, x, y)));
				break;
			case LEFT:
				moveableRoute.add(new MoveableRoute(Direction.LEFT, leftRouteCalculator(moveableSquareCount, x, y)));
				break;
			case RIGHT:
				moveableRoute.add(new MoveableRoute(Direction.RIGHT, rightRouteCalculator(moveableSquareCount, x, y)));
				break;
			case UP_LEFT:
				moveableRoute.add(new MoveableRoute(Direction.UP_LEFT, upLeftRouteCalculator(moveableSquareCount, x, y)));
				break;
			case UP_RIGHT:
				moveableRoute.add(new MoveableRoute(Direction.UP_RIGHT, upRightRouteCalculator(moveableSquareCount, x, y)));
				break;
			case DOWN_LEFT:
				moveableRoute.add(new MoveableRoute(Direction.DOWN_LEFT, downLeftRouteCalculator(moveableSquareCount, x, y)));
				break;
			case DOWN_RIGHT:
				moveableRoute.add(new MoveableRoute(Direction.DOWN_RIGHT, downRightRouteCalculator(moveableSquareCount, x, y)));
				break;
			}
		}
		return moveableRoute;
	}

	private static int upSquareCountConverter(int moveableSquareCount, int y) {
		if (moveableSquareCount == 3 && moveableSquareCount - 1 > y - Coordinate.getMinimumY()) {
			moveableSquareCount = 0;
		} else if ((moveableSquareCount == -1 || moveableSquareCount > y - Coordinate.getMinimumY())
				&& !(selectedChessman instanceof Knight)) {
			moveableSquareCount = y - Coordinate.getMinimumY();
		}
		System.out.println(moveableSquareCount + "변환결과");
		return moveableSquareCount;
	}

	private static int downSquareCountConverter(int moveableSquareCount, int y) {
		if (moveableSquareCount == 3 && moveableSquareCount - 1 > Coordinate.getMaximumY() - y) {
			moveableSquareCount = 0;
		} else if ((moveableSquareCount == -1 || moveableSquareCount > Coordinate.getMaximumY() - y)
				&& !(selectedChessman instanceof Knight)) {
			moveableSquareCount = Coordinate.getMaximumY() - y;
		}
		System.out.println(moveableSquareCount + "변환결과");
		return moveableSquareCount;
	}

	private static Coordinate[] upRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);
		System.out.println(moveableSquareCount);

		if (selectedChessman instanceof Knight && moveableSquareCount != 0 && moveableSquareCount != 1) {
			Coordinate[] availCoordinate = new Coordinate[2];
			System.out.println(moveableSquareCount + "MSC");
			System.out.println(x);
			System.out.println(y);
			availCoordinate[0] = leftRouteCalculator(1, x, y - moveableSquareCount + 1)[0];
			availCoordinate[1] = rightRouteCalculator(1, x, y - moveableSquareCount + 1)[0];
			return availCoordinate;
		} else {
			if (moveableSquareCount == 0 && !(selectedChessman instanceof Knight)) {
				Coordinate[] availCoordinate = new Coordinate[1];
				availCoordinate[0] = initCoordinate;
				return availCoordinate;
			}
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				if (Coordinate.isValidate(x, (y - count))) {
					availCoordinate[count - 1] = new Coordinate(x, (y - count));
				} else {
					availCoordinate[count - 1] = initCoordinate;
				}
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] downRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);

		if (selectedChessman instanceof Knight && moveableSquareCount != 0 && moveableSquareCount != 1) {
			Coordinate[] availCoordinate = new Coordinate[2];
			availCoordinate[0] = leftRouteCalculator(1, x, (y + moveableSquareCount - 1))[0];
			availCoordinate[1] = rightRouteCalculator(1, x, (y + moveableSquareCount - 1))[0];
			return availCoordinate;
		} else {
			if (moveableSquareCount == 0 && !(selectedChessman instanceof Knight)) {
				Coordinate[] availCoordinate = new Coordinate[1];
				availCoordinate[0] = initCoordinate;
				return availCoordinate; 
			}
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				if (Coordinate.isValidate(x, (y + count))) {
					availCoordinate[count - 1] = new Coordinate(x, (y + count));
				} else {
					availCoordinate[count - 1] = initCoordinate;
				}
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] leftRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == 3 && moveableSquareCount - 1 > x - Coordinate.getMinimumX()) {
			moveableSquareCount = 0;
		} else if ((moveableSquareCount == -1 || moveableSquareCount > x - Coordinate.getMinimumX())
				&& !(selectedChessman instanceof Knight)) {
			moveableSquareCount = x - Coordinate.getMinimumX();
		}

		if (selectedChessman instanceof Knight && moveableSquareCount != 0 && moveableSquareCount != 1) {
			Coordinate[] availCoordinate = new Coordinate[2];
			availCoordinate[0] = upRouteCalculator(1, x - moveableSquareCount + 1, y)[0];
			availCoordinate[1] = downRouteCalculator(1, x - moveableSquareCount + 1, y)[0];
			return availCoordinate;
		} else {
			if (moveableSquareCount == 0 && !(selectedChessman instanceof Knight)) {
				Coordinate[] availCoordinate = new Coordinate[1];
				availCoordinate[0] = initCoordinate;
				return availCoordinate; 
			}
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				if (Coordinate.isValidate((x - count), y)) {
					availCoordinate[count - 1] = new Coordinate((x - count), y);
				} else {
					availCoordinate[count - 1] = initCoordinate;
				}
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] rightRouteCalculator(int moveableSquareCount, int x, int y) {
		if (moveableSquareCount == 3 && moveableSquareCount - 1 > Coordinate.getMaximumX() - x) {
			moveableSquareCount = 0;
		} else if ((moveableSquareCount == -1 || moveableSquareCount > Coordinate.getMaximumX() - x)
				&& !(selectedChessman instanceof Knight)) {
			moveableSquareCount = Coordinate.getMaximumX() - x;
		}

		if (selectedChessman instanceof Knight && moveableSquareCount != 0 && moveableSquareCount != 1) {
			Coordinate[] availCoordinate = new Coordinate[2];
			availCoordinate[0] = upRouteCalculator(1, x + moveableSquareCount - 1, y)[0];
			availCoordinate[1] = downRouteCalculator(1, x + moveableSquareCount - 1, y)[0];
			return availCoordinate;
		} else {
			if (moveableSquareCount == 0 && !(selectedChessman instanceof Knight)) {
				Coordinate[] availCoordinate = new Coordinate[1];
				availCoordinate[0] = initCoordinate;
				return availCoordinate;
			}
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				if (Coordinate.isValidate((x + count), y)) {
					availCoordinate[count - 1] = new Coordinate((x + count), y);
				} else {
					availCoordinate[count - 1] = initCoordinate;
				}
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] upLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			Coordinate[] availCoordinate = new Coordinate[1];
			availCoordinate[0] = initCoordinate;
			return availCoordinate; 
		}
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if (Coordinate.isValidate((x - count), (y - count))) {
				availCoordinate[count - 1] = new Coordinate((x - count), (y - count));
			} else {
				availCoordinate[count - 1] = initCoordinate;
			}
		}
		return availCoordinate;
	}

	private static Coordinate[] upRightRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			Coordinate[] availCoordinate = new Coordinate[1];
			availCoordinate[0] = initCoordinate;
			return availCoordinate; 
		}
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if (Coordinate.isValidate((x + count), (y - count))) {
				availCoordinate[count - 1] = new Coordinate((x + count), (y - count));
			} else {
				availCoordinate[count - 1] = initCoordinate;
			}
		}
		return availCoordinate;
	}

	private static Coordinate[] downLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			Coordinate[] availCoordinate = new Coordinate[1];
			availCoordinate[0] = initCoordinate;
			return availCoordinate; 
		}
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if (Coordinate.isValidate((x - count), (y + count))) {
				availCoordinate[count - 1] = new Coordinate((x - count), (y + count));
			} else {
				availCoordinate[count - 1] = initCoordinate;
			}
		}
		return availCoordinate;
	}

	private static Coordinate[] downRightRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			Coordinate[] availCoordinate = new Coordinate[1];
			availCoordinate[0] = initCoordinate;
			return availCoordinate; 
		}
		Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
		for (int count = 1; count <= moveableSquareCount; count++) {
			if (Coordinate.isValidate((x + count), (y + count))) {
				availCoordinate[count - 1] = new Coordinate((x + count), (y + count));
			} else {
				availCoordinate[count - 1] = initCoordinate;
			}
		}
		return availCoordinate;
	}
}
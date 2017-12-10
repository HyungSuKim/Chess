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
		initCoordinate = new Coordinate(x, y);
		selectedChessman = chessman;

		if (selectedChessman instanceof Knight) {

			return getKnightRoute(x, y);

		} else {
			return getCommonChessmanRoute(x, y);
		}
	}

	 private static ArrayList<MoveableRoute> getKnightRoute(int x, int y) {
		 ArrayList<MoveableRoute> moveableRoute = new ArrayList<>();
		 int moveableSquareCount = selectedChessman.getMoveableSquareCount();
			for (Direction direction : selectedChessman.getDirection()) {
				switch (direction) {
				case UP:
					if(upSquareCountConverter(moveableSquareCount - 1, y) == 2) {
						moveableRoute.add(new MoveableRoute(Direction.UP, leftRouteCalculator(1, x, y-2)));
						moveableRoute.add(new MoveableRoute(Direction.UP, rightRouteCalculator(1, x, y-2)));
					}
					break;
				case DOWN:
					if(downSquareCountConverter(moveableSquareCount - 1, y) == 2) {
						moveableRoute.add(new MoveableRoute(Direction.DOWN, leftRouteCalculator(1, x, y+2)));
						moveableRoute.add(new MoveableRoute(Direction.DOWN, rightRouteCalculator(1, x, y+2)));
					}
					break;
				case LEFT:
					if(leftSquareCountConverter(moveableSquareCount - 1, x) == 2) {
						moveableRoute.add(new MoveableRoute(Direction.LEFT, upRouteCalculator(1, x-2, y)));
						moveableRoute.add(new MoveableRoute(Direction.LEFT, downRouteCalculator(1, x-2, y)));
					}
					break;
				case RIGHT:
					if(rightSquareCountConverter(moveableSquareCount - 1, x) == 2) {
						moveableRoute.add(new MoveableRoute(Direction.RIGHT, upRouteCalculator(1, x+2, y)));
						moveableRoute.add(new MoveableRoute(Direction.RIGHT, downRouteCalculator(1, x+2, y)));
					}
					break;
				default :
				}
			}
			return moveableRoute;
	 }

	private static ArrayList<MoveableRoute> getCommonChessmanRoute(int x, int y) {
		ArrayList<MoveableRoute> moveableRoute = new ArrayList<>();
		int moveableSquareCount = selectedChessman.getMoveableSquareCount();
		for (Direction direction : selectedChessman.getDirection()) {
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
				moveableRoute
						.add(new MoveableRoute(Direction.UP_LEFT, upLeftRouteCalculator(moveableSquareCount, x, y)));
				break;
			case UP_RIGHT:
				moveableRoute
						.add(new MoveableRoute(Direction.UP_RIGHT, upRightRouteCalculator(moveableSquareCount, x, y)));
				break;
			case DOWN_LEFT:
				moveableRoute.add(
						new MoveableRoute(Direction.DOWN_LEFT, downLeftRouteCalculator(moveableSquareCount, x, y)));
				break;
			case DOWN_RIGHT:
				moveableRoute.add(
						new MoveableRoute(Direction.DOWN_RIGHT, downRightRouteCalculator(moveableSquareCount, x, y)));
				break;
			}
		}

		return moveableRoute;
	}

	private static int upSquareCountConverter(int moveableSquareCount, int y) {
		if ((moveableSquareCount == -1 || moveableSquareCount > y - Coordinate.getMinimumY())) {
			moveableSquareCount = y - Coordinate.getMinimumY();
		}
		return moveableSquareCount;
	}

	private static int downSquareCountConverter(int moveableSquareCount, int y) {
		if ((moveableSquareCount == -1 || moveableSquareCount > Coordinate.getMaximumY() - y)) {
			moveableSquareCount = Coordinate.getMaximumY() - y;
		}
		return moveableSquareCount;
	}
	
	private static int leftSquareCountConverter(int moveableSquareCount, int x) {
		if ((moveableSquareCount == -1 || moveableSquareCount > x - Coordinate.getMinimumX())) {
			moveableSquareCount = x - Coordinate.getMinimumX();
		}
		return moveableSquareCount;
	}
	
	private static int rightSquareCountConverter(int moveableSquareCount, int x) {
		if ((moveableSquareCount == -1 || moveableSquareCount > Coordinate.getMaximumX() - x)) {
			moveableSquareCount = Coordinate.getMaximumX() - x;
		}
		return moveableSquareCount;
	}
	
	private static Coordinate[] getInitCoordinateAsArray() {
		Coordinate[] availCoordinate = new Coordinate[1];
		availCoordinate[0] = initCoordinate;
		return availCoordinate;
	}
	
	private static Coordinate getValidateCoordinate(int x, int y) {
		if (Coordinate.isValidate(x, y)) {
			return new Coordinate(x, y);
		} else {
			return initCoordinate;
		}
	}

	private static Coordinate[] upRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count-1] = getValidateCoordinate(x, (y - count));
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] downRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count - 1] = getValidateCoordinate(x, (y + count));
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] leftRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = leftSquareCountConverter(moveableSquareCount, x);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count - 1] = getValidateCoordinate((x - count), y);
			}
			return availCoordinate;
		}
	}
	
	private static Coordinate[] rightRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = rightSquareCountConverter(moveableSquareCount, x);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count - 1] = getValidateCoordinate((x + count), y);
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] upLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count - 1] = getValidateCoordinate((x - count), (y - count));
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] upRightRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = upSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count - 1] = getValidateCoordinate((x + count), (y - count));
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] downLeftRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count - 1] = getValidateCoordinate((x - count), (y + count));
			}
			return availCoordinate;
		}
	}

	private static Coordinate[] downRightRouteCalculator(int moveableSquareCount, int x, int y) {
		moveableSquareCount = downSquareCountConverter(moveableSquareCount, y);

		if (moveableSquareCount == 0) {
			return getInitCoordinateAsArray();
		} else {
			Coordinate[] availCoordinate = new Coordinate[moveableSquareCount];
			for (int count = 1; count <= moveableSquareCount; count++) {
				availCoordinate[count - 1] = getValidateCoordinate((x + count), (y + count));
			}
			return availCoordinate;
		}
	}
}
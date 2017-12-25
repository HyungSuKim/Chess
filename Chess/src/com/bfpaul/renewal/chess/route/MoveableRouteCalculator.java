package com.bfpaul.renewal.chess.route;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.Knight;

/**
 * MoveableRouteCalculator는 체스말이 움직일 수 있는 경로를 계산해주기위한 클래스이다.
 * 체스말의 속성인 Direction, moveableSquareCount와 Coordinate를 이용해서
 * 방향별로 움직일수 있는 좌표들을 가진 Data인 MoveableRoute를 생성한다.
 * 이 MoveableRoute들을 ArrayList에 담아 반환함으로써 BoardPanel과 같이 필요한 곳에서
 * 체스말이 움직 일 수있는 방향과 좌표들의 Data를 활용 할 수 있게끔 해주기 위해서 필요하다. 
 * @author 김형수
 */
public class MoveableRouteCalculator {
	private static final int NONE_DIRECTION = 0; // 방향이 없음을 나타내는 상수
	private static final int UP_DIRECTION = -1; // 위쪽, 왼쪽(좌표가 작아지는 방향)을 나타내는 상수
	private static final int DOWN_DIRECTION = 1; // 아래쪽, 오른쪽(좌표가 커지는 방향)을 나타내는 상수
	
	private static Coordinate initCoordinate;
	private static Chessman selectedChessman;

	private MoveableRouteCalculator() {
	}
	
	/**
	 * 외부(BoardPanel)에서 체스말을 선택했을 때 호출되는 메서드로써 MoveableRouteCalculator에서 유일하게 공개 되어있는 메서드이다.
	 * getChessmanMoveableRouteList은 선택된 체스말, 체스말이 놓여있는 x, y 좌표를 이용해서 
	 * 다른 메서드들이 체스말이 움직 일 수 있는 경로를 생성해서 반환 할 수 있도록한다.
	 * 체스말이 Knight일 경우와 그렇지 않은 경우로 구분하여 경로를 반환한다.
	 * @param chessman : 선택된 체스말
	 * @param x : 체스말이 놓여있는 x 좌표
	 * @param y : 체스말이 놓여있는 y 좌표
	 * @return : 체스말이 보드위에서 움직 일 수있는 MoveableRoute들을 가진 ArrayList를 반환한다. 
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
	 * getKnightRoute 메서드는 나이트의 움직 일 수있는 경로리스트(Direction, Coordinate[])를 생성해서 반환하는 메서드이다.
	 * x, y 그리고 Coordinate의 MaximumX/Y, MinimunX/Y를 이용해서 한 방향으로 움직일 수 있는 칸수를 계산한다.(up/down/left/rightMoveableCount) 
	 * 나이트의 주요 특징은 한방향으로 2칸을 이동하고 위아래 혹은 왼쪽오른쪽으로 1칸 이동 한다는 것이다. 즉, 초기 2칸을 움직 일 수없다면 이동자체가 불가한 것이다.
	 * 이를 해결하기위해 isAvailMoveTwoSquares를 이용해서 해당방향으로 2칸을 움직이는 것이 가능한지 검증하고
	 * 가능하다면 방향에 맞게 calX 또는 calY에 +2 셋팅을 한뒤에
	 * 1칸 이동하는 좌표를 계산하여 MoveableRoute를 생성하고 List에 추가하는 방식으로 구현하였다.
	 * 
	 * @param moveableSquareCount : 나이트가 움직 일 수있는 칸수는 3이다.
	 * @param x : 나이트의 현재 x 좌표
	 * @param y : 나이트의 현재 y 좌표
	 * @return : moveableRouteList : 나이트가 움직 일 수 있는 경로를 MoveableRoute(방향, 좌표들)로써 저장한 리스트
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
	 * Knight가 초기에 어떠한 방향으로 2칸을 움직이는 것이 가능한지 판별하는 메서드이다.
	 * getAvailMoveableCount를 호출해서 그 값이 2일때만 유효한 것으로 판단한다.
	 * 
	 * @param moveableSquareCount : knight의 움직일 수있는 칸 수 ( 3 )
	 * @param directionMoveableCount : 해당 방향으로 움직 일 수 있는 칸 수
	 * @return 움직일 수 있는 칸 수가  2칸 이라면  참 아니라면 거짓을 반환한다.
	 */
	private static boolean isAvailMoveTwoSquares(int moveableSquareCount, int directionMoveableCount) {
		return getAvailMoveableCount(moveableSquareCount - 1, directionMoveableCount) == 2;
	}
	
	// 입력받은 x, y기준으로 위쪽으로 1칸 이동한 유효한 좌표를 반환하는 메서드
	private static Coordinate[] getUpCoordinate(int x, int y) {
		Coordinate[] upCoordinate = new Coordinate[1];
		upCoordinate[0] = getValidCoordinate(x, y + UP_DIRECTION);
		return upCoordinate;
	}
	
	// 입력받은 x, y기준으로 아래쪽으로 1칸 이동한 유효한 좌표를 반환하는 메서드
	private static Coordinate[] getDownCoordinate(int x, int y) {
		Coordinate[] downCoordinate = new Coordinate[1];
		downCoordinate[0] = getValidCoordinate(x, y + DOWN_DIRECTION);
		return downCoordinate;
	}
	
	// 입력받은 x, y기준으로 왼쪽으로 1칸 이동한 유효한 좌표를 반환하는 메서드	
	private static Coordinate[] getLeftCoordinate(int x, int y) {
		Coordinate[] leftCoordinate = new Coordinate[1];
		leftCoordinate[0] = getValidCoordinate(x + UP_DIRECTION, y);
		return leftCoordinate;
	}
	
	// 입력받은 x, y기준으로 오른쪽으로 1칸 이동한 유효한 좌표를 반환하는 메서드
	private static Coordinate[] getRightCoordinate(int x, int y) {
		Coordinate[] rightCoordinate = new Coordinate[1];
		rightCoordinate[0] = getValidCoordinate(x + DOWN_DIRECTION, y);
		return rightCoordinate;
	}
	
	/**
	 * 입력된 x, y 값을 이용해서 유효한 좌표를 반환하는 메서드이다.
	 * @param x : 입력된 x 좌표
	 * @param y : 입력된 y 좌표
	 * @return 입력된 x/y 좌표가 유효한 좌표일 경우(범위에서 벗어나지 않았을경우) 해당 x, y를 갖는 Coordinate를 반환하고
	 * 			그렇지 않다면 초기 좌표를 반환한다.
	 */
	private static Coordinate getValidCoordinate(int x, int y) {
		if(Coordinate.isValidate(x, y)) {
			return new Coordinate(x, y);
		} else {
			return initCoordinate;
		}
	}
	
	/**
	 * getCommonChessmanRoute 메서드는 나이트를 제외한 체스말들의 움직 일 수있는 경로리스트(Direction, Coordinate[])를 생성해서 반환하는 메서드이다.
	 * 이 메서드는 선택된 체스말의 움직일 수 있는 Direction, moveableSquareCount, x, y를 이용해서 경로리스트를 생성한다.
	 * x, y 그리고 Coordinate의 MaximumX/Y, MinimunX/Y를 이용해서 한 방향으로 움직일 수 있는 칸수를 계산한다.(up/down/left/rightMoveableCount) 
	 * 각 Direction 별로  방향MoveableCount와 moveableSquareCount를 이용하여 체스말이 실제로 움직일 수 있는 칸수를 구한다(realMoveableCount)
	 * 그리고 각 방향별로 xDir, yDir을 셋팅해주어 방향에 맞는 값을 가진 Coordinate[]을 생성하여 moveableRoute를 생성해서 List에 저장하여 반환한다. 
	 * 
	 * @param moveableSquareCount : 체스말의 움직 일 수 있는 칸수로 -1 은 무한대, 그 이외는 정해진 칸 수 이다.
	 * @param x : 체스말의 현재 x 좌표
	 * @param y : 체스말의 현재 y 좌표
	 * @return : moveableRouteList : 체스말이 움직 일 수 있는 경로를 MoveableRoute(방향, 좌표들)로써 저장한 리스트
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
				
				if(moveableSquareCount == -1) { // 움직일 수 있는 칸수가 무한대일때
					realMoveableCount = upMoveableCount;
				} else { // 움직일 수 있는 칸수가 정해져있을때
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, upMoveableCount);
				}
				
				break;
			case DOWN:
				yDir = DOWN_DIRECTION;
				
				if(moveableSquareCount == -1) { // 움직일 수 있는 칸수가 무한대일때
					realMoveableCount = downMoveableCount;
				} else { // 움직일 수 있는 칸수가 정해져있을때
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, downMoveableCount);
				}

				break;
			case LEFT:
				xDir = UP_DIRECTION;
				
				if(moveableSquareCount == -1) { // 움직일 수 있는 칸수가 무한대일때
					realMoveableCount = leftMoveableCount;
				} else { // 움직일 수 있는 칸수가 정해져있을때
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, leftMoveableCount);
				}

				break;
			case RIGHT:
				xDir = DOWN_DIRECTION;
				
				if(moveableSquareCount == -1) { // 움직일 수 있는 칸수가 무한대일때
					realMoveableCount = rightMoveableCount;
				} else { // 움직일 수 있는 칸수가 정해져있을때
					realMoveableCount = getAvailMoveableCount(moveableSquareCount, rightMoveableCount);
				}
				

				break;
			case UP_LEFT:
				xDir = UP_DIRECTION;
				yDir = UP_DIRECTION;
				
				if(moveableSquareCount == -1) { // 움직일 수 있는 칸수가 무한대일때
					realMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, leftMoveableCount);
				} else { // 움직일 수 있는 칸수가 정해져있을때
					diagonalMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, leftMoveableCount);

					realMoveableCount = getAvailMoveableCount(moveableSquareCount, diagonalMoveableCount);
				}
				
				break;
			case UP_RIGHT:
				xDir = DOWN_DIRECTION;
				yDir = UP_DIRECTION;
				
				if(moveableSquareCount == -1) { // 움직일 수 있는 칸수가 무한대일때
					realMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, rightMoveableCount);
				} else { // 움직일 수 있는 칸수가 정해져있을때
					diagonalMoveableCount = getRealDiagonalMoveableCount(upMoveableCount, rightMoveableCount);

					realMoveableCount = getAvailMoveableCount(moveableSquareCount, diagonalMoveableCount);
				}

				break;
			case DOWN_LEFT:
				xDir = UP_DIRECTION;
				yDir = DOWN_DIRECTION;
				
				if(moveableSquareCount == -1) { // 움직일 수 있는 칸수가 무한대일때
					realMoveableCount = getRealDiagonalMoveableCount(downMoveableCount, leftMoveableCount);
				} else { // 움직일 수 있는 칸수가 정해져있을때
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
	 * getAvailMoveableCount 메서드는 움직 일 수 있는 칸수가 정해져있는 체스말을 대상으로 한 메서드이다.
	 * 이 메서드는 체스말의 움직 일 수 있는 칸수가 해당방향으로 움직 일 수 있는 칸수보다 큰지 작은지 비교하여 더 작은 값을 리턴한다.
	 * 체스말이 움직 일 수 있는 칸수가 해당방향으로 움직 일 수 있는 칸수보다 크면 체스판 밖으로 벗어나게 될 것 이기 때문에
	 * 이 메서드를 사용해서 움직일 수 있는 칸수를 반환한다.
	 * 
	 * @param moveableSquareCount : 체스말이 움직 일 수 있는 칸수
	 * @param directionMoveableCount : 해당 방향으로 움직 일 수 있는 칸수
	 * @return 위 파라미터 값중 작은 값을 리턴해준다.
	 */

	private static int getAvailMoveableCount(int moveableSquareCount, int directionMoveableCount) {
		return moveableSquareCount >= directionMoveableCount ? directionMoveableCount : moveableSquareCount;
	}

	/**
	 * getRealDiagonalMoveableCount 메서드는 대각선 방향의 실제 움직 일 수 있는 칸수를 계산해 주기 위해 필요하다.
	 * 대각선은 x방향과 y방향이 합쳐진 개념이다. 현 좌표기준으로 x방향으로 움직 일 수있는 칸수가 2이고 y방향으로 움직 일 수 있는 칸수가
	 * 2라면 대각선으로 이동가능한 칸은 2칸이므로 계산에 문제가 없다. 하지만 x방향으로 움직 일 수있는 칸수가 3이고 y방향으로 움직일 수
	 * 있는 칸수가 2라면 대각선으로 이동가능한 칸은 2칸이다 이는 반대의 경우에도 동일 적용되는 것을 확인하였다. 따라서 대각선의 경우 x,
	 * y의 움직일 수 있는 칸수 중 더 작은 칸수 이상으로 이동할 수 없음을 알 수있었다. 이 논리를 UP_LEFT, UP_RIGHT,
	 * DOWN_LEFT, DOWN_RIGHT에도 동일하게 적용하고 표현을 좋게 하기 위해 메서드로 분리하였다.
	 * 
	 * @param xMoveableCount
	 *            : 현재 좌표에서 x 방향으로 움직 일 수 있는 칸수
	 * @param yMoveableCount
	 *            : 현재 좌표에서 y 방향으로 움직 일 수 있는 칸수
	 * @return 위 파라미터 값중 작은 값을 리턴해준다.
	 */
	private static int getRealDiagonalMoveableCount(int yMoveableCount, int xMoveableCount) {
		return xMoveableCount >= yMoveableCount ? yMoveableCount : xMoveableCount;
	}
}
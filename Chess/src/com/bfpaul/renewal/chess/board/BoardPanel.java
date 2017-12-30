package com.bfpaul.renewal.chess.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;

import com.bfpaul.renewal.chess.Theme;
import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.ChessmanType;
import com.bfpaul.renewal.chess.chessman.data.Pawn;
import com.bfpaul.renewal.chess.chessman.data.King;
import com.bfpaul.renewal.chess.chessman.data.Rook;
import com.bfpaul.renewal.chess.event.PawnPromotionSelectEventFrame;
import com.bfpaul.renewal.chess.layer.Layer;
import com.bfpaul.renewal.chess.route.Coordinate;
import com.bfpaul.renewal.chess.route.MoveableRoute;
import com.bfpaul.renewal.chess.route.MoveableRouteCalculator;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// 이 BoardPanel은 체스게임의 하나의 보드로써 역할을 하기위해서 만들어졌다.
// 이 Board를 View가 아닌 Panel로 정의 한 것은 Board가 내부의 데이터 변화에 따라서 뷰가 바뀌는 것이 아니라
// 외부의 사용자 및 클래스들로 인해 그 때 그 때 처리 한 결과를 이용하여 상태가 바뀌기 때문에 Panel로써 정의하였다. 
//
// 하나의 보드로써 64칸의 boardSquare를 가지고 체스말과 각각의 boardSquare간의 관계를 맺어주는 역할을 한다.
// boardSquare에 체스말을 놓거나 제외하거나, 체스말을 선택했을때 이동경로를 계산해서 경로상의 다른 Square들에게 표시를 해준다거나
// 어떤 위치의 boardSquare에 잡을 수 있는 체스말이 있다 던가.
//
// 이렇게 boardSquare와 Chessman 그리고 MoveableRouteCalculator의 관계를 맺어주고 그 데이터들을 이용하여 데이터에 걸맞는 것을 표현하기위하여 필요하다.
//
// BoardPanel의 주요 기능은 아래와 같다.
// 1. 체스말을 집어서 이동경로를 보여준다(공격가능경로 포함) 2. 해당경로로 말을 이동시킨다. 3. 말의 이동 후 다음 이동시 체크가 가능한지 판단한다.
// BoardPanel은 내부클래스로 MoveHelper, CastlingHelper, PawnAttackHelper, EnPassantHelper, CheckmateHelper를 가지고있는데
// 이 클래스들을 내부클래스로 분류한 이유는 BoardPanel의 내부에서 체스말이 이동하는 경우, 특수한 이동의 경우를 내부클래스로 따로 구현하여
// BoardPanel에서 체스말의 이동을 내부 클래스들이 담당하도록 하기위함이다.
// 내부클래스로 구현함으로써 각 내부클래스는 자기 자신의 역할만 하게 되고 서로 연관성이 없어지게 된다. 

/* 클래스 작성 간 점검요소
 * 1. 올바른 클래스 이름인가? : 
 * 2. 멤버변수의 존재 여부 : 
 * 3. 클래스의 역할(Data/Controller/View) : 
 * 4. Data 공정과 공통로직의 분리 : 
 * 5. 접근제어자/한정자/자료형 : 
 * 6. inner class / abstract / interface : 
 * 7. 프로그램의 구조 : 
 * 8. class간의 연관관계 및 필요조건 : 
 * 9. 시점 : 
 * 10.중복 동일 구조가 반복되지 않는가 : 
 * */

@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel implements Layer {
	// 체스 판의 하나하나의 square로써 체스말을 놓아준다던가 체스말을 제외해준다거나 이동가능범위를 표현해줄 최소단위의 칸이다.
	private final BoardSquare[][] BOARD_SQUARE = new BoardSquare[8][8];
	private BoardSquare selectedSquare = null;
	private boolean isWhite = true;

	private boolean isFinish;

	// 8 X 8의 square를 가진 체스판을 만들어준다.
	public BoardPanel(boolean isWhite) {

		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);

		createSquares();
		setSquareLayoutByColor(isWhite);

		setWholeChessmanOnBoard();
	}

	private void setSquareLayoutByColor(boolean isWhite) {
		if (isWhite) {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					add(BOARD_SQUARE[x][y], createMatchParentConstraints(1));
				}
			}

		} else {
			for (int y = 7; y >= 0; y--) {
				for (int x = 0; x < 8; x++) {
					add(BOARD_SQUARE[x][y], createMatchParentConstraints(1));
				}
			}
		}
	}

	private void createSquares() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				createBoardSquare(x, y);
			}
		}
	}

	// 입력된 무게와 부모의 크기만큼 영역을 차지하는 제약조건을 생성하여 반환한다.
	private LinearConstraints createMatchParentConstraints(int weight) { // ㅇ
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// 정해진 좌표의 하나의 칸을 생성해서 하나의 square가 배열로써 저장되어 좌표의 의미를 갖도록 해서 추후 클래스의 설명에 적혀있는
	// 이벤트를 처리할때
	// 좌표의 값을 이용해서 square에 이벤트를 처리 할 수 있도록 하려고한다.
	private void createBoardSquare(int x, int y) { // ㅇ
		BOARD_SQUARE[x][y] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR);
		BOARD_SQUARE[x][y].setOnClickListener(getListenerToControlChessman(x, y));
	}

	private OnClickListener getListenerToControlChessman(int x, int y) {
		return new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// 처음눌렀을때
				if (BOARD_SQUARE[x][y].isContainChessman() && selectedSquare == null) {
					selectChessman(x, y);
				} else if (selectedSquare == BOARD_SQUARE[x][y]) {
					deSelectChessman();
				} else if (isSameColorChessmanClicked(x, y)) {
					selectChessman(x, y);
				} else {
					moveSelectedChessman(x, y);
				}
			}
		};
	}
	
	private boolean isSameColorChessmanClicked(int x, int y) {
		return BOARD_SQUARE[x][y].isContainChessman() 
				&& selectedSquare.isContainChessmanWhite() == BOARD_SQUARE[x][y].isContainChessmanWhite()
				&& BOARD_SQUARE[x][y].getBackground() != Color.YELLOW;
		
	}

	private void selectChessman(int x, int y) {
		ArrayList<Helper> helperList = new ArrayList<Helper>();
		helperList.add(new MoveHelper());
		helperList.add(new PawnAttackHelper());
		helperList.add(new CastlingHelper());
		helperList.add(new MateHelper());
		helperList.add(new EnPassantHelper());
		
		clearSquaresEventColor();

		selectedSquare = BOARD_SQUARE[x][y]; // 그 눌린말의 스퀘어 정보를 선택된 스퀘어에 저장하고
		for (Helper helper : helperList) {
			helper.show(selectedSquare.getChessman(), x, y);
		}

		if (selectedSquare.getContainChessmanType() == ChessmanType.PAWN) {

		}

		selectedSquare.setSquareMoveableColor(); // 눌린말의 칸은 움직일수있는 색상으로 표시해주고

	}

	private void deSelectChessman() {
		clearSquaresEventColor();

		selectedSquare.setSquareOriginalColor();

		selectedSquare = null;

		enableSquareClickEvent();

	}

	private void moveSelectedChessman(int x, int y) {

		if (selectedSquare.getContainChessmanType() == ChessmanType.KING) {
			King king = ((King) (selectedSquare.getChessman()));
			king.setIsMoved();
			new CastlingHelper().operateCastling(x, y);
		} else if (selectedSquare.getContainChessmanType() == ChessmanType.ROOK) {
			Rook rook = ((Rook) (selectedSquare.getChessman()));
			rook.setIsMoved();
			BOARD_SQUARE[x][y].setChessmanOnSquare(selectedSquare.getChessman());
		} else if (selectedSquare.getContainChessmanType() == ChessmanType.PAWN) {
			Pawn pawn = ((Pawn) (selectedSquare.getChessman()));
			pawn.setIsMoved();
			BOARD_SQUARE[x][y].setChessmanOnSquare(selectedSquare.getChessman());
		} else {
			BOARD_SQUARE[x][y].setChessmanOnSquare(selectedSquare.getChessman());
		}

		selectedSquare.removeChessmanFromSquare();
		selectedSquare.setSquareOriginalColor();
		selectedSquare = null;

		clearSquaresEventColor();

		new MateHelper().checkMateRoute();

		disableSquareClickEvent();

		/* 애매한부분 */
		if (BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].getContainChessmanType() == ChessmanType.PAWN
				&& (y == 0 || y == 7)) {
			PawnPromotionSelectEventFrame selectView = new PawnPromotionSelectEventFrame(BOARD_SQUARE[x][y]);
			selectView.setCallBack(new OnClickListener() {
				@Override
				public void onClick(Component component) {
					// TODO Auto-generated method stub
					isFinish = true;
				}
			});
		} else {
			isFinish = true;
		}

	}

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에
	// 올려준다.
	// 올려주는 역할을 BoardSquare class의 메서드를 이용해서 하는데 이를통해 원하는 로직을 따라서 체스말이 추가된다.
	private void setChessmanOnSquare(Chessman chessman, int x, int y) {
		BOARD_SQUARE[x][y].setChessmanOnSquare(chessman);
	}

	// 모든 체스말을 보드위에 셋팅한다.
	private void setWholeChessmanOnBoard() {
		for (ChessmanType type : ChessmanType.values()) {
			setChessmanOnBoard(type);
		}
	}

	// 체스말의 타입별로 보드의 초기위치에 설정해주기위한 메서드인데 각 타입에 따라 보드위 초기위치에 설정해준다.
	private void setChessmanOnBoard(ChessmanType type) {
		switch (type) {
		case KING:
			setSingleChessmanOnBoard(ChessmanType.KING);
			break;
		case QUEEN:
			setSingleChessmanOnBoard(ChessmanType.QUEEN);
			break;
		case BISHOP:
			setPairChessmanOnBoard(ChessmanType.BISHOP);
			break;
		case KNIGHT:
			setPairChessmanOnBoard(ChessmanType.KNIGHT);
			break;
		case ROOK:
			setPairChessmanOnBoard(ChessmanType.ROOK);
			break;
		case PAWN:
//			setPawnOnBoard(ChessmanType.PAWN);
			break;
		default:
		}
	}

	// 초기 갯수가 1개인 말(킹, 퀸) 의 경우 enum의 ordinal이 0, 1인데 여기서 3을 더한 값이 실제 말이 놓일 x로써 의미를
	// 갖게 된다는 것을 알고
	// 이러한 규칙을 이용해서 초기 갯수가 1개인 말을 한꺼번에 처리해서 Square에 올려주게되었다.
	private void setSingleChessmanOnBoard(ChessmanType type) {

		int xCoordinate;

		if (type == ChessmanType.KING) {
			xCoordinate = type.ordinal() + 4;
		} else {
			xCoordinate = type.ordinal() + 2;
		}

		setChessmanOnSquare(type.createChessman(false), xCoordinate, 0);
		setChessmanOnSquare(type.createChessman(true), xCoordinate, 7);
	}

	// 초기 갯수가 2개인 말(비숍, 나이트, 룩) 의 경우 enum의 ordinal이 2, 3, 4인데 여기서 3을 더한 값과 7에서 이 값을
	// 뺀 값이
	// 실제 말이 놓일 x로써 의미를 갖게 된다는 것을 알고 이러한 규칙을 이용해서 초기 갯수가 2개인 말을 한꺼번에 처리해서 Square에
	// 올려주게되었다.
	private void setPairChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), 0);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), 7);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 7);
	}

	// 폰들을 보드위 정해진(초기) 위치에 셋팅해주기위해서 작성하였다. 폰의 경우 초기 갯수만큼 반복해서 생성해서 Square위에 올려주는데
	// 흰색과 검정색이 대칭으로 갯수가 똑같음을 이용했다.
	private void setPawnOnBoard(ChessmanType type) {
		int initCount = type.getInitCount();
		for (int count = 0; count < initCount; count++) {
			setChessmanOnSquare(type.createChessman(false), count, 1);
			setChessmanOnSquare(type.createChessman(true), count, 6);
		}
	}

	private void disableSquareClickEvent() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				BOARD_SQUARE[x][y].setEnableClickEvent(false);
			}
		}
	}

	private void enableSquareClickEvent() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].isContainChessmanWhite() == isWhite) {
					BOARD_SQUARE[x][y].setEnableClickEvent(true);
				} else {
					BOARD_SQUARE[x][y].setEnableClickEvent(false);
				}
			}
		}
	}

	private void clearSquaresEventColor() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				BOARD_SQUARE[x][y].setSquareOriginalColor();
			}
		}
	}

	private abstract class Helper {

		abstract void show(Chessman chessman, int x, int y);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * MoveHelper는 MoveableRouteCalculator로 부터 계산된 MoveableRouteList를 이용해서
	 * BoardPanel 위 Chessman의 움직임(공격/이동)을 구현하기위해서 만들어진 내부클래스이다.
	 * 
	 * MoveHelper는 체스말이 선택되었을때 MoveableCalculator로 부터 계산된 MoveableRouteList를 받아 분석하여
	 * 움직일수 있는 경로를 표현한다. 1. 체스말은 움직일 수 있는 경로상에 같은색의 말이 있으면 움직이지 못한다. (단, 나이트 제외) 2.
	 * 체스말은 움직일 수 있는 경로상에 다른색의 말이 있으면 공격 할 수있다. (단, Pawn 제외) 3. 1, 2번의 경우를 충족하면서 그
	 * 이후의 칸들을 더 움직일 수 있는 범위라고 해도 움직 일 수없다.
	 * 
	 * @author 김형수
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class MoveHelper extends Helper {

		@Override
		void show(Chessman chessman, int x, int y) {
			ArrayList<MoveableRoute> moveableRouteList = MoveableRouteCalculator.getChessmanMoveableRouteList(chessman,
					x, y);

			for (MoveableRoute moveableRoute : moveableRouteList) {
				showMoveableCoordinates(moveableRoute.getCoordinates());
			}
		}

		// showMoveableRoute로부터 파라미터로 받은 MoveableRoute의 Coordinate들을 하나 하나 처리하는 메서드로써
		// 1. 좌표에 말이 없으면 해당 좌표를 움직일 수 있는 색상으로 표시해주는 메서드를 호출해서 움직 일 수 있는 경로임을 표시하고
		// 2. 좌표에 말이 있고 그 좌표의 말이 적군이라면 공격 할 수 있는 색상으로 표시하는 메서드를 호출하여 공격할 수 있는 경로임을 표시한다.
		private void showMoveableCoordinates(Coordinate[] coordinates) {
			for (Coordinate coordinate : coordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();

				if (BOARD_SQUARE[x][y].isContainChessman()) {
					if (isEnemy(x, y) && selectedSquare.getChessman().getChessmanType() != ChessmanType.PAWN) {
						BOARD_SQUARE[x][y].setSquareAttackableColor();
					}
					break;
				} else {
					BOARD_SQUARE[x][y].setSquareMoveableColor();
				}
			}
		}

		// 해당 좌표에 있는 말이 적군인지 검사하는 메서드
		private boolean isEnemy(int x, int y) {
			return selectedSquare.getChessman().isWhite() != BOARD_SQUARE[x][y].getChessman().isWhite();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * PawnAttackHelper는 BoardPanel 위에서 Chessman Pawn의 공격을 구현하기위해서 만들어진 내부클래스이다. 이
	 * 내부클래스는 폰의 현재 위치좌표와 색상을 이용해서 공격가능한 좌표에 다른색상의 말, 적이 있는지 체크한다 그리고 체크결과를
	 * pawnAttackableRouteList에 저장하여 폰의 공격가능경로(0 ~ 2가지)를 보여줄 수 있다.
	 * 
	 * 기존의 MoveableRouteCalculator의 계산된 경로와는 별도의 논리로 구성되어있다. 이유는 폰을 제외한 다른 말들은 움직임과
	 * 공격의 경로가 똑같지만 폰의 경우 움직이는 경로와 공격의 경로가 같지 않기 때문이다.
	 * 
	 * @author 김형수
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class PawnAttackHelper extends Helper {
		@Override
		void show(Chessman chessman, int x, int y) {
			if (chessman.getChessmanType() == ChessmanType.PAWN) {
				if (isWhite) {
					showAvailablePawnAttackableRoute(x, y - 1);

				} else {
					showAvailablePawnAttackableRoute(x, y + 1);
				}
			}
		}

		// 적인지 아닌지
		private boolean isEnemy(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && (BOARD_SQUARE[x][y].isContainChessmanWhite() != isWhite);
		}

		/**
		 * 폰의 공격가능한 루트에 적이 있으면 리스트에 경로를 추가해주는 메서드 폰의 공격 가능한 방향에 왼쪽 좌표(x-1)와 오른쪽 좌표(x+1)에
		 * 적이 있는지 검사하고 적이 있다면 공격가능한 경로로써 List에 추가한다.
		 * 
		 * @param direction
		 *            : 적이 있는지 확인 해야 할 방향
		 * @param x
		 *            : 폰의 현재 x좌표 에서 x - 1, x + 1에 적이있는지 확인하기 위한 파라미터
		 * @param y
		 *            : 메서드 호출부에서 입력된 방향에 따른 y좌표
		 */
		private void showAvailablePawnAttackableRoute(int x, int y) {
			if (Coordinate.isValidate(x - 1, y) && isEnemy(x - 1, y)) {
				BOARD_SQUARE[x - 1][y].setSquareAttackableColor();
			}

			if (Coordinate.isValidate(x + 1, y) && isEnemy(x + 1, y)) {
				BOARD_SQUARE[x + 1][y].setSquareAttackableColor();
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * CheckmateHelper는 BoardPanel의 Chessman이 이동하고 턴이 넘어가기 전 왕을 잡을수 있는 경로를 가진 체스말이
	 * 있는지 검사하는 기능을 구현한다. 따라서 체크메이트 핼퍼는 턴이 넘어가기전 현재색상을 기준으로 현재색상의 체스말들의 이동가능한 경로를
	 * 계산하고 이 계산한 경로 상에 반대색상의 왕이 있다면 해당 경로를 체크메이트경로리스트에 추가(저장)한다. 턴이 넘어가고 반대색상의 말을
	 * 클릭했을때 체스메이트경로리스트에 저장된 경로를 보여주도록 설계되었다.
	 * 
	 * 기존의 MoveableRouteCalculator의 계산된 경로에서 체크메이트 루트를 검사해서 추출하며 PawnAttackHelper를
	 * 이용해서 폰으로 왕을 체크메이트 할 수있는 경우에 대해서도 계산한다.
	 *
	 * CheckmateHelper는 MoveHelper와 다르게 경로를 보여주는 역할만 하는 것이고 경로도 특정 경로만 표시해야 되기에
	 * 분류하였다.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class MateHelper extends Helper {
		@Override
		void show(Chessman chessman, int x, int y) {
			showMateRoute();
		}

		private boolean isNotCurrentColorChessman(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].isContainChessmanWhite() != isWhite;
		}

		private boolean isCurrentColorChessman(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].isContainChessmanWhite() == isWhite;
		}

		private boolean isCurrentColorKing(int x, int y) {
			return isCurrentColorChessman(x, y) && BOARD_SQUARE[x][y].getContainChessmanType() == ChessmanType.KING;
		}

		private boolean isNotCurrentColorKing(int x, int y) {
			return isNotCurrentColorChessman(x, y) && BOARD_SQUARE[x][y].getContainChessmanType() == ChessmanType.KING;
		}

		private void showMateRoute() {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (isNotCurrentColorChessman(x, y)) {
						Chessman chessman = BOARD_SQUARE[x][y].getChessman();
						showMateRoute(chessman, x, y);
					}
				}
			}
		}

		private void checkMateRoute() {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (isCurrentColorChessman(x, y)) {
						Chessman chessman = BOARD_SQUARE[x][y].getChessman();
						checkMateRoute(chessman, x, y);
					}
				}
			}
		}

		private ArrayList<MoveableRoute> getMoveableRouteList(Chessman chessman, int x, int y) {
			return MoveableRouteCalculator.getChessmanMoveableRouteList(chessman, x, y);
		}

		private void showMateRoute(Chessman chessman, int x, int y) {
			for (MoveableRoute moveableRoute : getMoveableRouteList(chessman, x, y)) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {

					int xCoordinate = coordinate.getX();
					int yCoordinate = coordinate.getY();

					if (isCurrentColorKing(xCoordinate, yCoordinate)) {
						for (Coordinate mateCoordinate : moveableRoute.getCoordinates()) {
							BOARD_SQUARE[mateCoordinate.getX()][mateCoordinate.getY()].setSquareCheckColor();
						}
					} else if (isCurrentColorChessman(xCoordinate, yCoordinate)) {
						break;
					} else {

					}
				}
			}
		}

		private void checkMateRoute(Chessman chessman, int x, int y) {
			for (MoveableRoute moveableRoute : getMoveableRouteList(chessman, x, y)) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					int xCoordinate = coordinate.getX();
					int yCoordinate = coordinate.getY();

					if (isNotCurrentColorKing(xCoordinate, yCoordinate)) {
						System.out.println("체 크 !!!!!!!");
					} else if (isCurrentColorChessman(xCoordinate, yCoordinate)) {
						break;
					} else {

					}
				}
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * EnPassantHelper는 BoardPanel의 Pawn의 이동방법중 앙파상 잡기를 구현하기 위하여 필요한 내부클래스이다.
	 * EnPassantHelper는 폰이 2칸 움직이는 순간에 계산을 실시하는데 이는 앙파상 잡기가 2칸 이동하고 바로 다음턴만 유효하다는
	 * 특성을 활용하기 위해서이다. 그리고 앙파상 잡기의 다른 조건으로 2칸 이동한 폰의 옆에 다른말이 있는지 여부또한 확인하고 로직을 수행한다.
	 * 
	 * 이는 기존의 MoveableRouteCalculator로 계산 할 수 없는 특수 케이스라 판단해서 따로 분리하였다.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class EnPassantHelper extends Helper {
		// 앙파상을 검사해야 되는경우는 폰의 초기 움직임이 2일때 뿐이기때문에 초기 움직임 Y를 변수로써 가지고있도록 했다.

		@Override
		void show(Chessman chessman, int x, int y) {
			if (chessman.getChessmanType() == ChessmanType.PAWN) {
				showEnPassantSquare(x, y);
			}
		}

		private void showEnPassantSquare(int x, int y) {
			if (Coordinate.isValidate(x - 1, y)) {
				if (BOARD_SQUARE[x - 1][y].isContainChessman()
						&& BOARD_SQUARE[x - 1][y].getContainChessmanType() == ChessmanType.PAWN && BOARD_SQUARE[x][y]
								.isContainChessmanWhite() != BOARD_SQUARE[x - 1][y].isContainChessmanWhite()) {
					if (isWhite) {
						BOARD_SQUARE[x - 1][y - 1].setSquareAttackableColor();
					} else {
						BOARD_SQUARE[x - 1][y + 1].setSquareAttackableColor();
					}
				}
			}

			if (Coordinate.isValidate(x + 1, y)) {
				if (BOARD_SQUARE[x + 1][y].isContainChessman()
						&& BOARD_SQUARE[x + 1][y].getContainChessmanType() == ChessmanType.PAWN && BOARD_SQUARE[x][y]
								.isContainChessmanWhite() != BOARD_SQUARE[x + 1][y].isContainChessmanWhite()) {
					if (isWhite) {
						BOARD_SQUARE[x + 1][y - 1].setSquareAttackableColor();
					} else {
						BOARD_SQUARE[x + 1][y + 1].setSquareAttackableColor();
					}
				}
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * CastlingHelper는 BoardPanel의 Chessman의 이동방법중 캐슬링을 구현하기 위하여 필요한 내부클래스이다.
	 * CastlingHelper는 King이 선택되었을때 그 왕이 움직임이 있었는지 검사한다(움직임이 있었으면 캐슬링을 못하니까) 움직임이
	 * 없었다면 CastilngSquare을 정하기위해 왕의 왼편과 오른쪽 룩 중 캐슬링의 조건을 만족하는 것이 있는지 검사한다. 조건을 만족하는
	 * 룩이 있다면 그 룩의 BoardSquare를 캐슬링 가능한 목록에 추가한다. 그리고 추가된 리스트를 보여줌으로써 캐슬링 가능한 칸을
	 * 표시한다. 이는 기존의 MoveableRouteCalculator로 계산 할 수 없는 특수 케이스라 판단해서 따로 분리하였다.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class CastlingHelper extends Helper {

		@Override
		void show(Chessman chessman, int x, int y) {
			if (chessman.getChessmanType() == ChessmanType.KING) {
				showCastlingSquare();
			}
		}

		// 캐슬링에 적합한 검증과정(1.킹과 룩 사이에 말들이 없고 2.룩이 움직인적이없어야 한다)을 거치고 검증과정에 맞으면 캐슬링 스퀘어에
		// 추가한다.
		private void showCastlingSquare() {
			if (isWhite) {

				if (isXAndUpperSquareEmpty(5, 7) && isSquareContainRook(7, 7)) {
					showCastlingSquareIfRookNotMoved(7, 7);
				}

				if (isXAndUpperTwoSquareEmpty(1, 7) && isSquareContainRook(0, 7)) {
					showCastlingSquareIfRookNotMoved(0, 7);
				}
			} else {

				if (isXAndUpperSquareEmpty(5, 0) && isSquareContainRook(7, 0)) {
					showCastlingSquareIfRookNotMoved(7, 0);
				}

				if (isXAndUpperTwoSquareEmpty(1, 0) && isSquareContainRook(0, 0)) {
					showCastlingSquareIfRookNotMoved(0, 0);
				}
			}
		}

		// x, x + 1 의 칸이 비었는지 확인한다
		private boolean isXAndUpperSquareEmpty(int x, int y) {
			return !BOARD_SQUARE[x][y].isContainChessman() && !BOARD_SQUARE[x + 1][y].isContainChessman();
		}

		// x, x + 1, x + 2 의 칸이 비었는지 확인한다
		private boolean isXAndUpperTwoSquareEmpty(int x, int y) {
			return isXAndUpperSquareEmpty(x, y) && !BOARD_SQUARE[x + 2][y].isContainChessman();
		}

		// 칸이 룩을 가지고있는지 확인한다.
		private boolean isSquareContainRook(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman()
					&& BOARD_SQUARE[x][y].getContainChessmanType() == ChessmanType.ROOK;
		}

		// 룩이 움직이지 않았는지 확인한다.
		private boolean isRookNotMoved(int x, int y) {
			return !((Rook) BOARD_SQUARE[x][y].getChessman()).isMoved();
		}

		// 룩이 움직이지 않았다면 캐슬링 가능한 좌표에 추가한다.
		private void showCastlingSquareIfRookNotMoved(int x, int y) {
			if (isRookNotMoved(x, y)) {
				BOARD_SQUARE[x][y].setSquareCastlingColor();
			}
		}

		// 움직임으로써 클릭한 sqaure의 좌표가 룩의 좌표일 때 캐슬링 움직임을 실행한다. 그 이외라면 단순 움직임을 실행한다.
		private void operateCastling(int x, int y) {
			if ((x == 7 && y == 7) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 0 && y == 0)) {
				operateCastlingMove(x, y);

			} else {
				BOARD_SQUARE[x][y].setChessmanOnSquare(selectedSquare.getChessman());
				selectedSquare.removeChessmanFromSquare();
			}
		}

		// 캐슬링 움직임을 실행해주는 메서드로써 선택된칸(왕) 그리고 움직임으로써 클릭한 square의 좌표에 대해 캐슬링 움직임을 수행한다.
		private void operateCastlingMove(int x, int y) {
			((King) selectedSquare.getChessman()).setIsMoved();
			((Rook) BOARD_SQUARE[x][y].getChessman()).setIsMoved();

			if (x == 0) {

				BOARD_SQUARE[x + 2][y].setChessmanOnSquare(selectedSquare.getChessman());
				BOARD_SQUARE[x + 3][y].setChessmanOnSquare(BOARD_SQUARE[x][y].getChessman());
			} else if (x == 7) {

				BOARD_SQUARE[x - 1][y].setChessmanOnSquare(selectedSquare.getChessman());
				BOARD_SQUARE[x - 2][y].setChessmanOnSquare(BOARD_SQUARE[x][y].getChessman());
			}

			selectedSquare.removeChessmanFromSquare();
			BOARD_SQUARE[x][y].removeChessmanFromSquare();
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void execute(Object[] object) {
		isWhite = (boolean) object[0];
		isFinish = false;
		enableSquareClickEvent();
	}

	/**
	 * BoardPanel에서 말을 옮기고 더이상 아무것도 할 수 없을 때, BoardPanel의 Datas를 반환한다.
	 * 
	 * @return
	 */
	@Override
	public Object[] getDatas() {
		Map<Boolean, Map<ChessmanType, Integer>> chessmanCountMap = new HashMap<>();
		chessmanCountMap.put(true, new HashMap<>());
		chessmanCountMap.put(false, new HashMap<>());

		for (ChessmanType type : ChessmanType.values()) {
			chessmanCountMap.get(true).put(type, 0);
			chessmanCountMap.get(false).put(type, 0);
		}

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (BOARD_SQUARE[x][y].isContainChessman()) {
					Chessman chessman = BOARD_SQUARE[x][y].getChessman();

					int result = chessmanCountMap.get(chessman.isWhite()).get(chessman.getChessmanType()) + 1;

					chessmanCountMap.get(chessman.isWhite()).put(chessman.getChessmanType(), result);

				}
			}
		}

		return new Object[] { chessmanCountMap };
	}

	@Override
	public boolean isFinish() {
		return isFinish;
	}

}

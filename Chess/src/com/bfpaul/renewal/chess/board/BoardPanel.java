package com.bfpaul.renewal.chess.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import com.bfpaul.renewal.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.chessman.King;
import com.bfpaul.renewal.chess.chessman.Knight;
import com.bfpaul.renewal.chess.chessman.Pawn;
import com.bfpaul.renewal.chess.chessman.PawnPromotionSelectView;
import com.bfpaul.renewal.chess.chessman.Rook;
import com.bfpaul.renewal.chess.controller.Coordinate;
import com.bfpaul.renewal.chess.controller.MoveableRoute;
import com.bfpaul.renewal.chess.controller.chessman.MoveableRouteCalculator;
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
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
	// 체스 판의 하나하나의 square로써 체스말을 놓아준다던가 체스말을 제외해준다거나 이동가능범위를 표현해줄 최소단위의 칸이다.
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private BoardSquare selectedSquare = null;
	private boolean isWhite = true;

	// 체스말이 보드위에서 일정 방법으로 움직이는 것을 도와주는 내부 클래스들로써
	// 보드가 만들어지고 말이 놓여지고 행동을 할 때 이러한 것들이 필요하다고 판단해서
	// BoardPanel의 생성자에서 각 내부클래스의 인스턴스를 생성한다.
	private MoveHelper moveHelper;
	private CastlingHelper castlingHelper;
	private PawnAttackHelper pawnAttackHelper;
	private EnPassantHelper enPassantHelper;
	private CheckmateHelper checkmateHelper;

	// 8 X 8의 square를 가진 체스판을 만들어준다.
	public BoardPanel() {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				createBoardSquare(x, y);
				add(boardSquare[x][y], createMatchParentConstraints(1));
			}
		}

		// for (int y = 0; y < 8; y++) {
		// for(int x = 0; x < 8; x++) {
		// add(boardSquare[y][x], createMatchParentConstraints(1));
		// }
		// }
		setWholeChessmanOnBoard();
		createHelper();
		disableSquareClickEvent();
		enableSquareClickEvent(isWhite);

	}

	private void createHelper() {
		moveHelper = new MoveHelper();
		castlingHelper = new CastlingHelper();
		pawnAttackHelper = new PawnAttackHelper();
		enPassantHelper = new EnPassantHelper();
		checkmateHelper = new CheckmateHelper();
	}

	// 입력된 무게와 부모의 크기만큼 영역을 차지하는 제약조건을 생성하여 반환한다.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// 정해진 좌표의 하나의 칸을 생성해서 하나의 square가 배열로써 저장되어 좌표의 의미를 갖도록 해서 추후 클래스의 설명에 적혀있는
	// 이벤트를 처리할때
	// 좌표의 값을 이용해서 square에 이벤트를 처리 할 수 있도록 하려고한다.
	private BoardSquare createBoardSquare(int x, int y) {
		boardSquare[x][y] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR);
		boardSquare[x][y].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// 처음눌렀을때
				if (boardSquare[x][y].isContainChessman() && selectedSquare == null) {
					selectChessman(x, y);

				} else if (selectedSquare == boardSquare[x][y]) {
					deSelectChessman();

				} else {
					moveSelectedChessman(x, y);
				}
			}
		});
		return boardSquare[x][y];
	}

	private void selectChessman(int x, int y) {
		disableSquareClickEvent();

		selectedSquare = boardSquare[x][y];

		moveHelper.setMoveableRouteList(MoveableRouteCalculator.selectChessman(selectedSquare.getChessman(), x, y));

		selectedSquare.setSquareMoveableColor();

		 if (selectedSquare.getChessman() instanceof Pawn) {
		 pawnAttackHelper.checkPawnAttackableSquare(x, y);
		 pawnAttackHelper.showPawnAttackableSquare(x, y);
		 enPassantHelper.checkShowEnPassantSquare(x, y);
		 }

		moveHelper.showMoveableRoute();
		checkmateHelper.showCheckmateRoute();

		if (selectedSquare.getChessman() instanceof King) {
			castlingHelper.checkShowCastlingSquare(x, y);
		}
	}

	private void deSelectChessman() {
		moveHelper.disableMoveableRoute();

		initSquareEvent(isWhite);

		pawnAttackHelper.disablePawnAttackableSquare();

		castlingHelper.cancelCastling();

		enPassantHelper.cancelEnPassant();

		checkmateHelper.disableCheckmateRoute();
	}

	private void moveSelectedChessman(int x, int y) {
		// 이동경로로 이동했을 때
		isWhite = !isWhite;
		if (boardSquare[x][y].isContainChessman()) {
			// 잡 았을때 잡힌말을 추후에 CurrentChessmanView에 적용 시키기위해 테스트 하던 부분
			// 문제는 앙파상을 하는경우에 잡힌 폰이 표시되지 않는 다는 것과 캐슬링을 하는 경우 Rook이 잡힌걸로 표시된다는 것이다.
			// 그래서 움직임이 끝날때마다 각 말을 세어줄까... 생각은 하고있는데 아직 미정이다.
			// System.out.println(boardSquare[x][y].getChessman().getChessmanType().name());
		}

		// 캐슬링 관련 & 앙파상 관련
		if (selectedSquare.getChessman() instanceof King && castlingHelper.castlingSquare.size() != 0) {
			castlingHelper.moveCastling(x, y);
			enPassantHelper.initEnPassantSquare();
		} else {
			boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
		}

		if (boardSquare[x][y].getChessman() instanceof Pawn) {
			pawnAttackHelper.pawnAttack(x, y);
			enPassantHelper.moveEnPassant(x, y);
			pawnAttackHelper.checkPawnAttackableSquare(x, y);
		}

		if (boardSquare[x][y].getChessman() instanceof King) {
			((King) boardSquare[x][y].getChessman()).setIsMoved();
		}

		if (boardSquare[x][y].getChessman() instanceof Rook) {
			((Rook) boardSquare[x][y].getChessman()).setIsMoved();
		}

		if (boardSquare[x][y].getChessman() instanceof Pawn && (y == 0 || y == 7)) {
			new PawnPromotionSelectView(boardSquare[x][y]);
		}

		selectedSquare.removeChessmanFromSquare();

		checkmateHelper.disableCheckmateRoute();

		checkmateHelper.checkEnemyChessmanRoute();

		moveHelper.disableMoveableRoute();

		initSquareEvent(isWhite);
	}

	private void initSquareEvent(boolean isWhite) {
		selectedSquare.setSquareOriginalColor();
		enableSquareClickEvent(isWhite);
		selectedSquare = null;
	}

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에
	// 올려준다.
	// 올려주는 역할을 BoardSquare class의 메서드를 이용해서 하는데 이를통해 원하는 로직을 따라서 체스말이 추가된다.
	private void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[x][y].setChessmanOnSquare(chessman);
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
			setPawnOnBoard(ChessmanType.PAWN);
			break;
		default:
		}
	}

	// 초기 갯수가 1개인 말(킹, 퀸) 의 경우 enum의 ordinal이 0, 1인데 여기서 3을 더한 값이 실제 말이 놓일 x로써 의미를
	// 갖게 된다는 것을 알고
	// 이러한 규칙을 이용해서 초기 갯수가 1개인 말을 한꺼번에 처리해서 Square에 올려주게되었다.
	private void setSingleChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 7);
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
				boardSquare[x][y].setEnableClickEvent(false);
			}
		}
	}

	private void enableSquareClickEvent(boolean isWhite) {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() == isWhite) {
					boardSquare[x][y].setEnableClickEvent(true);
				} else {
					boardSquare[x][y].setEnableClickEvent(false);
				}
			}
		}
	}

	private class MoveHelper {
		private ArrayList<MoveableRoute> moveableRouteList = new ArrayList<>();

		// MoveableRouteCalculator로부터 계산된 움직일수 있는 경로의 List를 MoveHelper의
		// moveableRouteList에 저장한다.
		private void setMoveableRouteList(ArrayList<MoveableRoute> moveableRouteList) {
			this.moveableRouteList = moveableRouteList;
		}

		// 보여진 움직일수있는 경로를 안보이게(원래의 색상으로 바꾸어서) 한다.
		private void disableMoveableRoute() {
			for (MoveableRoute moveableRoute : moveableRouteList) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					boardSquare[coordinate.getX()][coordinate.getY()].setSquareOriginalColor();
				}
			}
		}

		// MoveableRouteCalculator로 부터 계산된 경로들 중
		// 움직 일 수 있는 경로를 계산하고 보여주기 위한 메서드이다.
		// Direction별로 생성된 MoveableRouteList의 요소를 MoveableRoute 단위로 쪼개어 연산한다.
		private void showMoveableRoute() {
			for (MoveableRoute moveableRoute : moveableRouteList) {
				checkMoveableCoordinates(moveableRoute.getCoordinates());
			}
		}

		// showMoveableRoute로부터 파라미터로 받은 MoveableRoute의 Coordinate들을 하나 하나 처리하는 메서드로써
		// 1. 좌표에 말이 없으면 해당 좌표를 움직일 수 있는 색상으로 표시해주는 메서드를 호출해서 움직 일 수 있는 경로임을 표시하고
		// 2. 좌표에 말이 있고 그 좌표의 말이 적군이라면 공격 할 수 있는 색상으로 표시하는 메서드를 호출하여 공격할 수 있는 경로임을 표시한다.
		private void checkMoveableCoordinates(Coordinate[] coordinates) {
			for (Coordinate coordinate : coordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();

				if (boardSquare[x][y].isContainChessman()) {
					// pawn 관련 설정 테스트중//
					if (isEnemy(x, y) && !(selectedSquare.getChessman() instanceof Pawn)) {
						setAttackableSquare(x, y);
					}
					// pawn 관련 설정 테스트중//
					break;
				} else {
					setMoveableSquare(x, y);
				}
			}
		}

		// 해당 좌표에 있는 말이 적군인지 검사하는 메서드
		private boolean isEnemy(int x, int y) {
			return selectedSquare.getChessman().isWhite() != boardSquare[x][y].getChessman().isWhite();
		}

		// 해당 좌표를 공격할 수 있는 색으로 변경한다
		private void setAttackableSquare(int x, int y) {
			boardSquare[x][y].setSquareAttackableColor();
		}

		// 해당 좌표를 움직일 수 있는 색으로 변경한다
		private void setMoveableSquare(int x, int y) {
			boardSquare[x][y].setSquareMoveableColor();
		}
	}

	private class PawnAttackHelper {
		private ArrayList<BoardSquare> pawnAttackableSquare = new ArrayList<>(); // 내부클래스

		private void disablePawnAttackableSquare() {
			if (!pawnAttackableSquare.isEmpty()) {
				for (BoardSquare square : pawnAttackableSquare) {
					square.setSquareOriginalColor();
				}
			}
		}

		private void showPawnAttackableSquare(int x, int y) {
			if (!pawnAttackableSquare.isEmpty()) {
				for (BoardSquare square : pawnAttackableSquare) {
					square.setSquareAttackableColor();
				}
			}
		}

		private void pawnAttack(int x, int y) {
			if (selectedSquare.getChessman() instanceof Pawn && !pawnAttackableSquare.isEmpty()) {
				for (BoardSquare square : pawnAttackableSquare) {
					square.setSquareOriginalColor();
				}
				pawnAttackableSquare.clear();
			} else {
				boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
			}
		}
		
		private boolean isEnemy(int x, int y) {
			return boardSquare[x][y].isContainChessman() && (boardSquare[x][y].getChessman().isWhite() != isWhite);
		}

		private void checkPawnAttackableSquare(int x, int y) {
			pawnAttackableSquare.clear();
			if (isWhite) {
				if (Coordinate.isValidate(x - 1, y - 1))
					if (isEnemy(x - 1, y - 1)) {
						pawnAttackableSquare.add(boardSquare[x - 1][y - 1]);
					}

				if (Coordinate.isValidate(x + 1, y - 1))
					if (isEnemy(x + 1, y - 1)) {
						pawnAttackableSquare.add(boardSquare[x + 1][y - 1]);
					}

			} else {
				if (Coordinate.isValidate(x + 1, y + 1))
					if (isEnemy(x + 1, y + 1)) {
						pawnAttackableSquare.add(boardSquare[x + 1][y + 1]);
					}

				if (Coordinate.isValidate(x - 1, y + 1))
					if (isEnemy(x - 1, y + 1)) {
						pawnAttackableSquare.add(boardSquare[x - 1][y + 1]);
					}
			}
		}
	}

	private class CheckmateHelper {
		private ArrayList<MoveableRoute> checkmateRouteList = new ArrayList<>();
		private ArrayList<MoveableRoute> moveableRouteList = new ArrayList<>();

		// 계산된 체크메이트 가능 경로를 보여주는 메서드로 왕을 선택했을때와 다른 말을 골랐을때로 분리한 이유는
		// 왕은 계산된 경로로 이동이 가능해도 이동이 불가해야되고 다른말은 계산된 경로와 움직일 수 있는 경로가 겹칠경우
		// 해당경로로 이동해서 왕의 체크메이트 경로를 차단 할 수 있어야 되기에 분리하였다.
		private void showCheckmateRoute() {
			for (MoveableRoute moveableRoute : checkmateRouteList) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					int x = coordinate.getX();
					int y = coordinate.getY();

					if (selectedSquare.getChessman() instanceof King) { // 선택된 말이 왕일때
						showRouteWhenKingSelected(x, y);
					} else { // 선택된 말이 왕이 아닐때
						showRouteWhenOthersSeleted(x, y);
					}

				}
			}
		}

		private void showRouteWhenKingSelected(int x, int y) {
			boardSquare[x][y].setSquareCheckmateColor(); // 체크메이트 경로를 체크메이트 색으로 바꾸고
			selectedSquare.setSquareMoveableCheckmateColor(); // 누른말의 왕은 움직일수있는 체크메이트 색으로 바꾼다.
		}

		private void showRouteWhenOthersSeleted(int x, int y) {
			// 다른 말을 눌렀을 때 다른말의 이동경로가 체크에이트 경로와 같을 때
			if (boardSquare[x][y].getBackground().equals(Theme.LIGHT_BLUE_COLOR)) {
				boardSquare[x][y].setSquareMoveableCheckmateColor();
			} else {
				// 다른 말을 눌렀을 때 다른말의 이동경로가 체크에이트 경로와 다를 때
				boardSquare[x][y].setSquareCheckmateColor();
			}
		}

		// 보여진 체크메이트 경로를 안보이게(원래대로) 바꾸어준다.
		private void disableCheckmateRoute() {
			for (MoveableRoute moveableRoute : checkmateRouteList)
				for (Coordinate coordinate : moveableRoute.getCoordinates())
					boardSquare[coordinate.getX()][coordinate.getY()].setSquareOriginalColor();
		}

		// 말이 움직인 뒤에 체스말들 중에 체크메이트가 가능한 말이 있는지 검사하는 메서드로써
		// 검사 결과 적의 왕을 체크가 가능한 말이 있다면 그말의의 경로를 받아서 저장한다.
		private void checkEnemyChessmanRoute() {
			checkmateRouteList.clear();
			moveableRouteList.clear();

			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (isEnemy(x, y)) {
						Chessman enemyChessman = boardSquare[x][y].getChessman();
						moveableRouteList = MoveableRouteCalculator.selectChessman(enemyChessman, x, y);
						checkEnemyCheckmateRoute();
					}
				} // end of for x
			} // end of for y
		}

		// 선택된 칸의 체스말이 적군인지 아닌지 검사한다.
		private boolean isEnemy(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() != isWhite;
		}

		// 상대편의 체스말인지 검사한다.
		private boolean isOtherSide(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() == isWhite;
		}

		// 적군의 움직일수 있는 경로를 가져와 체크메이트 할 수있는 경로가 있는지 검사한다.
		private void checkEnemyCheckmateRoute() {
			for (MoveableRoute moveableRoute : moveableRouteList) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					if (isEnemyHasCheckmateRoute(moveableRoute, coordinate))
						break;
				}
			}
		}

		// 상대편의 왕 인지 검사한다.
		private boolean isOtherSideKing(int x, int y) {
			return isOtherSide(x, y) && boardSquare[x][y].getChessman() instanceof King;
		}

		// 적군이 체크메이트 할 수 있는 경로가 있는지 검사해서 있다면 그 경로를 checkmateRouteList에 추가한다.
		private boolean isEnemyHasCheckmateRoute(MoveableRoute moveableRoute, Coordinate coordinate) {
			if (isOtherSideKing(coordinate.getX(), coordinate.getY())) {
				checkmateRouteList.add(moveableRoute);
				// new BoardEventInfoView(Images.CHECK);
				return true;

			} else {
				return false;
			}
		}
	}

	private class EnPassantHelper {
		private BoardSquare enPassantSquare = boardSquare[0][0];

		private void initEnPassantSquare() {
			enPassantSquare = boardSquare[0][0];
		}

		private void checkShowEnPassantSquare(int x, int y) {
			if (enPassantSquare != boardSquare[0][0] && boardSquare[x][y].getChessman() instanceof Pawn) {
				if ((Math.abs(enPassantSquare.getX() - boardSquare[x][y].getX()) == 114)
						&& (Math.abs(enPassantSquare.getY() - boardSquare[x][y].getY()) == 95))
					enPassantSquare.setSquareAttackableColor();
			}
		}

		private void cancelEnPassant() {
			if (enPassantSquare != boardSquare[0][0]) {
				enPassantSquare.setSquareOriginalColor();
			}
		}

		private void moveEnPassant(int x, int y) {
			if (boardSquare[x][y].getChessman() instanceof Pawn) {
				// 만약에 폰의 이동이 기존위치에서 차가 2일때 만 앙파상 체크를 한다... 하면 괜찮을거같은ㄷ?
				((Pawn) boardSquare[x][y].getChessman()).setIsMoved();

				if (Math.abs(boardSquare[x][y].getY() - selectedSquare.getY()) == 190) {
					setEnPassantSquare(boardSquare[x][y].getChessman().isWhite(), x, y);
					return;

				} else if (enPassantSquare != boardSquare[0][0] && boardSquare[x][y] == enPassantSquare) {
					if (boardSquare[x][y].getChessman().isWhite()) {
						boardSquare[x][y + 1].removeChessmanFromSquare();
					} else {
						boardSquare[x][y - 1].removeChessmanFromSquare();
					}

				}

				enPassantSquare.setSquareOriginalColor();
				enPassantSquare = boardSquare[0][0];

			}
		}

		private void setEnPassantSquare(boolean isWhite, int x, int y) {

			if (isWhite) {
				// boardSquare[y][x-1] 또는 boardSquare[y][x+1] 있는게 검정폰이냐? 를 검사하고
				if (Coordinate.isValidate(x - 1, y))
					if (isSquareContainPawn(x - 1, y) && !boardSquare[x - 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y + 1];
					}
				// 맞으면 boardSquare[y-1][x]를 앙파상 스퀘어에 저장
				if (Coordinate.isValidate(x + 1, y))
					if (isSquareContainPawn(x + 1, y) && !boardSquare[x + 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y + 1];
					}

			} else {
				// boardSquare[y][x-1] 또는 boardSquare[y][x+1] 있는게 흰색폰이냐? 를 검사하고
				if (Coordinate.isValidate(x - 1, y))
					if (isSquareContainPawn(x - 1, y) && boardSquare[x - 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y - 1];
					}
				// 맞으면 boardSquare[y+1][x]를 앙파상 스퀘어에 저장
				if (Coordinate.isValidate(x + 1, y))
					if (isSquareContainPawn(x + 1, y) && boardSquare[x + 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y - 1];
					}
			}
		}

		private boolean isSquareContainPawn(int x, int y) {
			return (boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman() instanceof Pawn);
		}
	}

	private class CastlingHelper {
		// 캐슬링 관련
		private ArrayList<BoardSquare> castlingSquare = new ArrayList<>(); // 내부클래스

		private void checkShowCastlingSquare(int x, int y) {
			// 캐슬링 관련
			if ((boardSquare[x][y].getChessman() instanceof King
					&& !((King) boardSquare[x][y].getChessman()).isMoved())) {

				setCastlingSquare(boardSquare[x][y].getChessman().isWhite());

				if (!castlingSquare.isEmpty()) {
					for (BoardSquare square : castlingSquare) {
						square.setSquareCastlingColor();
					}
				}
			}
		}

		private void cancelCastling() {
			// 캐슬링 관련
			if (!castlingSquare.isEmpty()) {
				for (BoardSquare square : castlingSquare) {
					square.setSquareOriginalColor();
				}
			}
		}

		private void moveCastling(int x, int y) {
			operateCastling(selectedSquare.getChessman().isWhite(), x, y);
			for (BoardSquare square : castlingSquare) {
				square.setSquareOriginalColor();
			}
			castlingSquare.clear();
		}

		private boolean isBetweenTwoSquareEmpty(int x, int y) {
			return !boardSquare[x][y].isContainChessman() && !boardSquare[x + 1][y].isContainChessman();
		}

		private boolean isBetweenThreeSquareEmpty(int x, int y) {
			return isBetweenTwoSquareEmpty(x, y) && !boardSquare[x + 2][y].isContainChessman();
		}

		private boolean isSquareContainRook(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman() instanceof Rook;
		}

		// 캐슬링 관련
		private void setCastlingSquare(boolean isWhite) {
			castlingSquare.clear();
			if (isWhite) {

				if (isBetweenTwoSquareEmpty(5, 7) && isSquareContainRook(7, 7)) {
					if (!((Rook) boardSquare[7][7].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[7][7]);
					}
				}

				if (isBetweenThreeSquareEmpty(1, 7) && isSquareContainRook(0, 7)) {
					if (!((Rook) boardSquare[0][7].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[0][7]);
					}
				}
			} else {

				if (isBetweenTwoSquareEmpty(5, 0) && isSquareContainRook(7, 0)) {
					if (!((Rook) boardSquare[7][0].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[7][0]);
					}
				}

				if (isBetweenThreeSquareEmpty(1, 0) && isSquareContainRook(0, 0)) {
					if (!((Rook) boardSquare[0][0].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[0][0]);
					}
				}
			}
		}

		// 캐슬링 관련
		private void operateCastling(boolean isWhite, int x, int y) {
			if (isWhite) {
				if (selectedSquare.getChessman() instanceof King && selectedSquare.getChessman().isWhite()
						&& ((x == 7 && y == 7) || (x == 0 && y == 7))) {
					operateCastlingMove(x, y);
				} else {
					boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
				}

			} else {
				if (selectedSquare.getChessman() instanceof King && !selectedSquare.getChessman().isWhite()
						&& ((x == 7 && y == 0) || (x == 0 && y == 0))) {
					operateCastlingMove(x, y);
				} else {
					boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
				}
			}
		}

		// 캐슬링 관련
		private void operateCastlingMove(int x, int y) {
			if (x == 0) {
				if (boardSquare[x + 2][y].getBackground().equals(Color.GREEN))
					boardSquare[x + 2][y].setSquareOriginalColor();

				boardSquare[x + 2][y].setChessmanOnSquare(selectedSquare.getChessman());
				boardSquare[x + 3][y].setChessmanOnSquare(boardSquare[x][y].getChessman());
			} else if (x == 7) {
				if (boardSquare[x - 1][y].getBackground().equals(Color.GREEN))
					boardSquare[x - 1][y].setSquareOriginalColor();

				boardSquare[x - 1][y].setChessmanOnSquare(selectedSquare.getChessman());
				boardSquare[x - 2][y].setChessmanOnSquare(boardSquare[x][y].getChessman());
			}

			((King) selectedSquare.getChessman()).setIsMoved();
			((Rook) boardSquare[x][y].getChessman()).setIsMoved();

			selectedSquare.removeChessmanFromSquare();
			boardSquare[x][y].removeChessmanFromSquare();
		}
	}
}

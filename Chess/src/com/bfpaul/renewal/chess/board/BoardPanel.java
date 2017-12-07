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
	boolean isWhite = true;

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

		for (int y = 8; y > 0; y--) {
			for (int x = 0; x < 8; x++) {
				createBoardSquare(x, (y - 1));
				add(boardSquare[y - 1][x], createMatchParentConstraints(1));
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
		boardSquare[y][x] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_DARK_SQUARE_COLOR : Theme.BOARD_LIGHT_SQUARE_COLOR);
		boardSquare[y][x].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// 처음눌렀을때
				if (boardSquare[y][x].isContain() && selectedSquare == null) {
					disableSquareClickEvent();

					selectedSquare = boardSquare[y][x];

					moveHelper.setMoveableRoute(
							MoveableRouteCalculator.selectChessman(selectedSquare.getChessman(), x, y));

					selectedSquare.setSquareMoveableColor();

					if (selectedSquare.getChessman() instanceof Pawn) {
						pawnAttackHelper.checkPawnAtackableSquare(x, y);
						pawnAttackHelper.showPawnAttackableSquare(x, y);
						enPassantHelper.checkShowEnPassantSquare(x, y);
					}

					moveHelper.showMoveableRoute(selectedSquare.getChessman().isWhite());
					checkmateHelper.showCheckmateRoute();

					if (selectedSquare.getChessman() instanceof King) {
						castlingHelper.checkShowCastlingSquare(x, y);
					}

				} else if (selectedSquare == boardSquare[y][x]) {
					initSquareEvent(isWhite);

					pawnAttackHelper.disablePawnAttackableSquare();

					castlingHelper.cancelCastling();

					enPassantHelper.cancelEnPassant();

					checkmateHelper.disableCheckmateRoute();

				} else {
					// 이동경로로 이동했을 때
					isWhite = !isWhite;
					if (boardSquare[y][x].isContain()) {
						// 잡 았을때 잡힌말을 추후에 CurrentChessmanView에 적용 시키기위해 테스트 하던 부분
						// 문제는 앙파상을 하는경우에 잡힌 폰이 표시되지 않는 다는 것과 캐슬링을 하는 경우 Rook이 잡힌걸로 표시된다는 것이다.
						// 그래서 움직임이 끝날때마다 각 말을 세어줄까... 생각은 하고있는데 아직 미정이다.
						System.out.println(boardSquare[y][x].getChessman().getChessmanType().name());
					}

					// 캐슬링 관련 & 앙파상 관련
					if (selectedSquare.getChessman() instanceof King && castlingHelper.castlingSquare.size() != 0) {
						castlingHelper.moveCastling(x, y);
						enPassantHelper.initEnPassantSquare();
					} else {
						boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
					}

					if (boardSquare[y][x].getChessman() instanceof Pawn) {
						pawnAttackHelper.pawnAttack(x, y);
						enPassantHelper.moveEnPassant(x, y);
						pawnAttackHelper.checkPawnAtackableSquare(x, y);
					}

					if (boardSquare[y][x].getChessman() instanceof King) {
						((King) boardSquare[y][x].getChessman()).setIsMoved();
					}

					if (boardSquare[y][x].getChessman() instanceof Rook) {
						((Rook) boardSquare[y][x].getChessman()).setIsMoved();
					}

					if (boardSquare[y][x].getChessman() instanceof Pawn && (y == 0 || y == 7)) {
						new PawnPromotionSelectView(boardSquare[y][x]);
					}

					selectedSquare.removeChessmanFromSquare();

					checkmateHelper.disableCheckmateRoute();

					checkmateHelper.checkmateChecker(isWhite);

					initSquareEvent(isWhite);
				}
			}
		});
		return boardSquare[y][x];
	}

	public void initSquareEvent(boolean isWhite) {
		selectedSquare.setSquareOriginalColor();
		moveHelper.disableMoveableRoute();
		enableSquareClickEvent(isWhite);
		selectedSquare = null;
	}

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에
	// 올려준다.
	// 올려주는 역할을 BoardSquare class의 메서드를 이용해서 하는데 이를통해 원하는 로직을 따라서 체스말이 추가된다.
	public void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[y][x].setChessmanOnSquare(chessman);
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
			// setPawnOnBoard(ChessmanType.PAWN);
			break;
		default:
		}
	}

	// 초기 갯수가 1개인 말(킹, 퀸) 의 경우 enum의 ordinal이 0, 1인데 여기서 3을 더한 값이 실제 말이 놓일 x로써 의미를
	// 갖게 된다는 것을 알고
	// 이러한 규칙을 이용해서 초기 갯수가 1개인 말을 한꺼번에 처리해서 Square에 올려주게되었다.
	private void setSingleChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 7);
	}

	// 초기 갯수가 2개인 말(비숍, 나이트, 룩) 의 경우 enum의 ordinal이 2, 3, 4인데 여기서 3을 더한 값과 7에서 이 값을
	// 뺀 값이
	// 실제 말이 놓일 x로써 의미를 갖게 된다는 것을 알고 이러한 규칙을 이용해서 초기 갯수가 2개인 말을 한꺼번에 처리해서 Square에
	// 올려주게되었다.
	private void setPairChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), 0);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), 7);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 7);
	}

	// 폰들을 보드위 정해진(초기) 위치에 셋팅해주기위해서 작성하였다. 폰의 경우 초기 갯수만큼 반복해서 생성해서 Square위에 올려주는데
	// 흰색과 검정색이 대칭으로 갯수가 똑같음을 이용했다.
	private void setPawnOnBoard(ChessmanType type) {
		int initCount = type.getInitCount();
		for (int count = 0; count < initCount; count++) {
			setChessmanOnSquare(type.createChessman(true), count, 1);
			setChessmanOnSquare(type.createChessman(false), count, 6);
		}
	}

	private void disableSquareClickEvent() {
		for (int y = 8; y > 0; y--) {
			for (int x = 0; x < 8; x++) {
				boardSquare[y - 1][x].setEnableClickEvent(false);
			}
		}
	}

	private void enableSquareClickEvent(boolean isWhite) {
		for (int y = 8; y > 0; y--) {
			for (int x = 0; x < 8; x++) {
				if (boardSquare[y - 1][x].isContain() && boardSquare[y - 1][x].getChessman().isWhite() == isWhite) {
					boardSquare[y - 1][x].setEnableClickEvent(true);
				} else {
					boardSquare[y - 1][x].setEnableClickEvent(false);
				}
			}
		}
	}

	class MoveHelper {
		private ArrayList<MoveableRoute> moveableRoute = new ArrayList<>();

		private void setMoveableRoute(ArrayList<MoveableRoute> moveableRoute) {
			this.moveableRoute = moveableRoute;
		}

		private void disableMoveableRoute() {
			for (MoveableRoute moveableRoute : moveableRoute) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					boardSquare[coordinate.getY()][coordinate.getX()].setSquareOriginalColor();
				}
			}
		}

		private void showMoveableRoute(boolean isWhite) {
			for (MoveableRoute moveableRoute : moveableRoute) {
				checkSquaresIsContain(isWhite, moveableRoute.getCoordinates());
			}
		}

		// 선택한 말의 움직일 수 있는 좌표의 칸에 말이 있는지 없는지 확인한다.
		// 말이 있으면 말이 있을때의 로직을 없을 때는 없을때의 로직을 수행한다.
		private void checkSquaresIsContain(boolean isWhite, Coordinate[] moaveableCoordinates) {
			for (Coordinate coordinate : moaveableCoordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();
				if (boardSquare[y][x].isContain()) {
					if (!checkContainChessmanIsAlly(isWhite, x, y))
						break;
				} else {
					notContainSquareOperation(x, y);
				}
			}
		}

		// 체스말이 같인편인지 확인하고 그에 맞는 로직을 시행한다
		private boolean checkContainChessmanIsAlly(boolean isWhite, int x, int y) {
			if (boardSquare[y][x].getChessman().isWhite() == isWhite) {
				return allyChessmanSquareOperation(x, y);
			} else {
				return notAllyChessmanSquareOperation(x, y);
			}
		}

		// 확인하는 칸의 체스말이 같은편이라면 수행하는 로직
		private boolean allyChessmanSquareOperation(int x, int y) {
			if (!(selectedSquare.getChessman() instanceof Knight)) {
				return false; // 나이트가 아니라면 해당경로에서 break해야함
			} else {
				return true; // 나이트라면 해당경로에서 break 하지 않고 계속 계산해야됨
			}
		}

		// 확인하는 칸의 체스말이 다른편이라면 수행하는 로직
		private boolean notAllyChessmanSquareOperation(int x, int y) {
			if (selectedSquare.getChessman() instanceof Knight) {
				boardSquare[y][x].setSquareAttackableColor();
				return true; // 나이트라면 해당경로에서 break 하지 않고 계속 공격경로를 계산해야됨
			} else if (selectedSquare.getChessman() instanceof Pawn) {
				return false; // 폰 이라면 해당경로에서 break해야함
			} else {
				boardSquare[y][x].setSquareAttackableColor();
				return false; // 폰과 나이트가 아니라면 공격경로를 설정하고 해당경로에서 break해야함
			}
		}

		// 확인하는 칸이 아무것도 없을때 수행하는 로직
		private void notContainSquareOperation(int x, int y) {
			boardSquare[y][x].setSquareMoveableColor();
		}
	}

	class PawnAttackHelper {
		private ArrayList<BoardSquare> pawnAtackableSquare = new ArrayList<>(); // 내부클래스

		private void disablePawnAttackableSquare() {
			if (!pawnAtackableSquare.isEmpty()) {
				for (BoardSquare square : pawnAtackableSquare) {
					square.setSquareOriginalColor();
				}
			}
		}

		private void showPawnAttackableSquare(int x, int y) {
			if (!pawnAtackableSquare.isEmpty()) {
				for (BoardSquare square : pawnAtackableSquare) {
					square.setSquareAttackableColor();
				}
			}
		}

		private void pawnAttack(int x, int y) {
			if (selectedSquare.getChessman() instanceof Pawn && !pawnAtackableSquare.isEmpty()) {
				for (BoardSquare square : pawnAtackableSquare) {
					square.setSquareOriginalColor();
				}
				pawnAtackableSquare.clear();
			} else {
				boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
			}
		}

		private void checkPawnAtackableSquare(int x, int y) {
			pawnAtackableSquare.clear();
			if (isWhite) {

				if (Coordinate.isValidate(x + 1, y + 1))
					if (boardSquare[y + 1][x + 1].isContain() && !boardSquare[y + 1][x + 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[y + 1][x + 1]);
					}

				if (Coordinate.isValidate(x - 1, y + 1))
					if (boardSquare[y + 1][x - 1].isContain() && !boardSquare[y + 1][x - 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[y + 1][x - 1]);
					}

			} else {
				if (Coordinate.isValidate(x - 1, y - 1))
					if (boardSquare[y - 1][x - 1].isContain() && boardSquare[y - 1][x - 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[y - 1][x - 1]);
					}

				if (Coordinate.isValidate(x + 1, y - 1))
					if (boardSquare[y - 1][x + 1].isContain() && boardSquare[y - 1][x + 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[y - 1][x + 1]);
					}
			}
		}
	}

	class CheckmateHelper {
		private ArrayList<MoveableRoute> checkmateRoute = new ArrayList<>();

		private void showCheckmateRoute() {
			for (MoveableRoute moveableRoute : checkmateRoute) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					int x = coordinate.getX();
					int y = coordinate.getY();

					showCheckmateRouteOperation(x, y);
				}
			}
		}

		private void showCheckmateRouteOperation(int x, int y) {
			if (selectedSquare.getChessman() instanceof King) { // 누른 말이 왕일때
				boardSquare[y][x].setSquareCheckmateColor(); // 체크메이트 경로를 체크메이트 색으로 바꾸고
				selectedSquare.setSquareMoveableCheckmateColor(); // 누른말의 왕은 움직일수있는 체크메이트 색으로 바꾼다.

			} else if (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman() instanceof King) {
				// 다른 말을 눌렀을 때
				boardSquare[y][x].setSquareCheckmateColor();

			} else {
				// 다른 말을 눌렀을 때 다른말의 이동경로가 체크에이트 경로와 같을 때
				if (boardSquare[y][x].getBackground().equals(Theme.LIGHT_BLUE_COLOR)) {
					boardSquare[y][x].setSquareMoveableCheckmateColor();
				} else {
					// 다른 말을 눌렀을 때 다른말의 이동경로가 체크에이트 경로와 다를 때
					boardSquare[y][x].setSquareCheckmateColor();
				}

			}
		}

		private void disableCheckmateRoute() {
			for (MoveableRoute moveableRoute : checkmateRoute)
				for (Coordinate coordinate : moveableRoute.getCoordinates())
					boardSquare[coordinate.getY()][coordinate.getX()].setSquareOriginalColor();
		}

		private void checkmateChecker(boolean isWhite) {
			checkmateRoute.clear();

			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman().isWhite() != isWhite) {
						Chessman checkingChessman = boardSquare[y][x].getChessman();
						checkmateRoute(checkingChessman,
								MoveableRouteCalculator.selectChessman(checkingChessman, x, y));
					}
				} // end of for x
			} // end of for y
		}

		private void checkmateRoute(Chessman checkingChessman, ArrayList<MoveableRoute> moveableRoute) {
			for (MoveableRoute route : moveableRoute) {
				for (Coordinate coordinate : route.getCoordinates()) {
					if (!checkmateRouteOperator(checkingChessman, route, coordinate))
						break;
				}
			}
		}

		private boolean checkmateRouteOperator(Chessman checkingChessman, MoveableRoute route, Coordinate coordinate) {
			int x = coordinate.getX();
			int y = coordinate.getY();

			if (boardSquare[y][x].isContain()
					&& boardSquare[y][x].getChessman().isWhite() != checkingChessman.isWhite()) {

				return isEffectiveRoute(checkingChessman, route, coordinate);

			} else {
				return true;
			}
		}

		private boolean isEffectiveRoute(Chessman checkingChessman, MoveableRoute route, Coordinate coordinate) {
			if (boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) {
				if (checkingChessman instanceof Knight) {
					Coordinate[] checkmateCoordinate = new Coordinate[1];
					checkmateCoordinate[0] = coordinate;
					checkmateRoute.add(new MoveableRoute(route.getDirection(), checkmateCoordinate));
					return true;

				} else {
					checkmateRoute.add(new MoveableRoute(route.getDirection(), route.getCoordinates()));
					return true;
				}

			} else {
				return false;
			}
		}

	}

	class EnPassantHelper {
		private BoardSquare enPassantSquare = boardSquare[0][0];

		private void initEnPassantSquare() {
			enPassantSquare = boardSquare[0][0];
		}

		private void checkShowEnPassantSquare(int x, int y) {
			if (enPassantSquare != boardSquare[0][0] && boardSquare[y][x].getChessman() instanceof Pawn) {
				if ((Math.abs(enPassantSquare.getX() - boardSquare[y][x].getX()) == 114)
						&& (Math.abs(enPassantSquare.getY() - boardSquare[y][x].getY()) == 95))
					enPassantSquare.setSquareAttackableColor();
			}
		}

		private void cancelEnPassant() {
			if (enPassantSquare != boardSquare[0][0]) {
				enPassantSquare.setSquareOriginalColor();
			}
		}

		private void moveEnPassant(int x, int y) {
			if (boardSquare[y][x].getChessman() instanceof Pawn) {
				// 만약에 폰의 이동이 기존위치에서 차가 2일때 만 앙파상 체크를 한다... 하면 괜찮을거같은ㄷ?
				((Pawn) boardSquare[y][x].getChessman()).setIsMoved();

				if (Math.abs(boardSquare[y][x].getY() - selectedSquare.getY()) == 190) {
					setEnPassantSquare(boardSquare[y][x].getChessman().isWhite(), x, y);
					return;

				} else if (enPassantSquare != boardSquare[0][0] && boardSquare[y][x] == enPassantSquare) {
					if (boardSquare[y][x].getChessman().isWhite()) {
						boardSquare[y - 1][x].removeChessmanFromSquare();
					} else {
						boardSquare[y + 1][x].removeChessmanFromSquare();
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
					if (isSquareContainPawn(x - 1, y) && !boardSquare[y][x - 1].getChessman().isWhite()) {
						enPassantSquare = boardSquare[y - 1][x];
					}
				// 맞으면 boardSquare[y-1][x]를 앙파상 스퀘어에 저장
				if (Coordinate.isValidate(x + 1, y))
					if (isSquareContainPawn(x + 1, y) && !boardSquare[y][x + 1].getChessman().isWhite()) {
						enPassantSquare = boardSquare[y - 1][x];
					}

			} else {
				// boardSquare[y][x-1] 또는 boardSquare[y][x+1] 있는게 흰색폰이냐? 를 검사하고
				if (Coordinate.isValidate(x - 1, y))
					if (isSquareContainPawn(x - 1, y) && boardSquare[y][x - 1].getChessman().isWhite()) {
						enPassantSquare = boardSquare[y + 1][x];
					}
				// 맞으면 boardSquare[y+1][x]를 앙파상 스퀘어에 저장
				if (Coordinate.isValidate(x + 1, y))
					if (isSquareContainPawn(x + 1, y) && boardSquare[y][x + 1].getChessman().isWhite()) {
						enPassantSquare = boardSquare[y + 1][x];
					}
			}
		}

		private boolean isSquareContainPawn(int x, int y) {
			return (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman() instanceof Pawn);
		}
	}

	class CastlingHelper {
		// 캐슬링 관련
		private ArrayList<BoardSquare> castlingSquare = new ArrayList<>(); // 내부클래스

		private void checkShowCastlingSquare(int x, int y) {
			// 캐슬링 관련
			if ((boardSquare[y][x].getChessman() instanceof King
					&& !((King) boardSquare[y][x].getChessman()).isMoved())) {

				setCastlingSquare(boardSquare[y][x].getChessman().isWhite());

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
			return !boardSquare[y][x].isContain() && !boardSquare[y][x + 1].isContain();
		}

		private boolean isBetweenThreeSquareEmpty(int x, int y) {
			return isBetweenTwoSquareEmpty(x, y) && !boardSquare[y][x + 2].isContain();
		}

		private boolean isSquareContainRook(int x, int y) {
			return boardSquare[y][x].isContain() && boardSquare[y][x].getChessman() instanceof Rook;
		}

		// 캐슬링 관련
		private void setCastlingSquare(boolean isWhite) {
			castlingSquare.clear();
			if (isWhite) {

				if (isBetweenTwoSquareEmpty(5, 0) && isSquareContainRook(7, 0)) {
					if (!((Rook) boardSquare[0][7].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[0][7]);
					}
				}

				if (isBetweenThreeSquareEmpty(1, 0) && isSquareContainRook(0, 0)) {
					if (!((Rook) boardSquare[0][0].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[0][0]);
					}
				}
			} else {

				if (isBetweenTwoSquareEmpty(5, 7) && isSquareContainRook(7, 7)) {
					if (!((Rook) boardSquare[7][7].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[7][7]);
					}
				}

				if (isBetweenThreeSquareEmpty(1, 7) && isSquareContainRook(0, 7)) {
					if (!((Rook) boardSquare[7][0].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[7][0]);
					}
				}
			}
		}

		// 캐슬링 관련
		private void operateCastling(boolean isWhite, int x, int y) {
			if (isWhite) {
				if (selectedSquare.getChessman() instanceof King && selectedSquare.getChessman().isWhite()
						&& ((x == 7 && y == 0) || (x == 0 && y == 0))) {
					operateCastlingMove(x, y);
				} else {
					boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
				}
			} else {
				if (selectedSquare.getChessman() instanceof King && !selectedSquare.getChessman().isWhite()
						&& ((x == 7 && y == 7) || (x == 0 && y == 7))) {
					operateCastlingMove(x, y);
				} else {
					boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
				}
			}
		}

		// 캐슬링 관련
		private void operateCastlingMove(int x, int y) {
			if (x == 0) {
				if (boardSquare[y][x + 2].getBackground().equals(Color.GREEN))
					boardSquare[y][x + 2].setSquareOriginalColor();

				boardSquare[y][x + 2].setChessmanOnSquare(selectedSquare.getChessman());
				boardSquare[y][x + 3].setChessmanOnSquare(boardSquare[y][x].getChessman());
			} else if (x == 7) {
				if (boardSquare[y][x - 1].getBackground().equals(Color.GREEN))
					boardSquare[y][x - 1].setSquareOriginalColor();

				boardSquare[y][x - 1].setChessmanOnSquare(selectedSquare.getChessman());
				boardSquare[y][x - 2].setChessmanOnSquare(boardSquare[y][x].getChessman());
			}

			((King) selectedSquare.getChessman()).setIsMoved();
			((Rook) boardSquare[y][x].getChessman()).setIsMoved();

			selectedSquare.removeChessmanFromSquare();
			boardSquare[y][x].removeChessmanFromSquare();
		}
	}
}

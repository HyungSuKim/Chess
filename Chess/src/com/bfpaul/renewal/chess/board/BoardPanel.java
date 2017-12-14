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
	private static BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private static BoardSquare selectedSquare = null;
	private static boolean isWhite = true;

	// 체스말이 보드위에서 일정 방법으로 움직이는 것을 도와주는 내부 클래스들로써
	// 보드가 만들어지고 말이 놓여지고 행동을 할 때 이러한 것들이 필요하다고 판단해서
	// BoardPanel의 생성자에서 각 내부클래스의 인스턴스를 생성한다.
	private static MoveHelper moveHelper;
	private static CastlingHelper castlingHelper;
	private static PawnAttackHelper pawnAttackHelper;
	private static EnPassantHelper enPassantHelper;
	private static CheckmateHelper checkmateHelper;

	// 8 X 8의 square를 가진 체스판을 만들어준다.
	public BoardPanel(boolean isWhite) {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);

		createSquares();
		setSquareLayoutByColor(isWhite);

		setWholeChessmanOnBoard();
		createHelper();
		disableSquareClickEvent();
		enableSquareClickEvent(BoardPanel.isWhite);

	}
	
	private void setSquareLayoutByColor(boolean isWhite) {
		if(isWhite) {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					add(boardSquare[x][y], createMatchParentConstraints(1));
				}
			}
			
		} else {
			for (int y = 7; y >= 0; y--) {
				for (int x = 0; x < 8; x++) {
					add(boardSquare[x][y], createMatchParentConstraints(1));
				}
			}
		}
	}
	
	private static void createSquares() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				createBoardSquare(x, y);
			}
		}
	}

	private void createHelper() {
		moveHelper = new MoveHelper();
		castlingHelper = new CastlingHelper();
		pawnAttackHelper = new PawnAttackHelper();
		enPassantHelper = new EnPassantHelper();
		checkmateHelper = new CheckmateHelper();
	}

	// 입력된 무게와 부모의 크기만큼 영역을 차지하는 제약조건을 생성하여 반환한다.
	private LinearConstraints createMatchParentConstraints(int weight) { // ㅇ
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// 정해진 좌표의 하나의 칸을 생성해서 하나의 square가 배열로써 저장되어 좌표의 의미를 갖도록 해서 추후 클래스의 설명에 적혀있는
	// 이벤트를 처리할때
	// 좌표의 값을 이용해서 square에 이벤트를 처리 할 수 있도록 하려고한다.
	private static BoardSquare createBoardSquare(int x, int y) { // ㅇ
		boardSquare[x][y] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR);
		boardSquare[x][y].setOnClickListener(getListenerToControlChessman(x, y));
		System.out.println(boardSquare[x][y].hashCode());
		return boardSquare[x][y];
	}
	
	private static OnClickListener getListenerToControlChessman(int x, int y) {
		return new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// 처음눌렀을때
				if (boardSquare[x][y].isContainChessman() && selectedSquare == null) {
					selectChessman(x, y);
					System.out.println(boardSquare[x][y].hashCode());
				} else if (selectedSquare == boardSquare[x][y]) {
					deSelectChessman();

				} else {
					moveSelectedChessman(x, y);
				}
			}
		};
	}

	private static void selectChessman(int x, int y) {
		disableSquareClickEvent(); // 초기에 말을 골랐을때 모든 칸의 클릭 이벤트를 풀고

		selectedSquare = boardSquare[x][y]; // 그 눌린말의 스퀘어 정보를 선택된 스퀘어에 저장하고

		moveHelper.setMoveableRouteList(MoveableRouteCalculator.selectChessman(selectedSquare.getChessman(), x, y));
		// moveHelper 내 moveableRouteList의 값을 MoveableRouteCalculator의 계산된 경로로 셋팅을 해주고 :
		// 경로계산

		selectedSquare.setSquareMoveableColor(); // 눌린말의 칸은 움직일수있는 색상으로 표시해주고

		if (selectedSquare.getChessman() instanceof Pawn) { // 선택된 말이 폰이면

			pawnAttackHelper.checkPawnAttackableSquare(x, y); // 폰이 공격할 수 있는 칸이 있는지 확인하고 : 경로계산
			pawnAttackHelper.showPawnAttackableSquare(x, y); // 폰이 공격할 수 있는 경로를 보여주고 : 경로보여주기
			enPassantHelper.checkShowEnPassantSquare(x, y); // 앙파상 할 수있는 칸이 있는지 체크하고 가능하다면 앙파상 경로를 보여주고 : 경로계산 + 보여주기
		}

		moveHelper.showMoveableRoute(); // 위에서 계산된 움직일 수 있는 경로를 보여준다 : 경로보여주기
		checkmateHelper.showCheckmateRoute(); // 체크메이트 가능한 경로가 있으면 체크메이트 가능한 경로를 보여준다. : 보여주기

		if (selectedSquare.getChessman() instanceof King) { // 선택된 말이 왕이면
			castlingHelper.setShowCastlingSquare(); // 캐슬링이 가능한지 확인하고 캐슬링 가능한 칸을 보여준다 : 경로계산 + 보여주기
		}
	}

	private static void deSelectChessman() {
		moveHelper.disableMoveableRoute(); // 움직일수 있는 경로를 비활성화한다 : 보여주기(감추기)

		initSquareEvent(isWhite); // 클릭된 말 이외에 클릭이 비활성 되었던 것에서 현재 색상의 말들의 클릭이벤트를 활성화해준다. : 기능적

		pawnAttackHelper.disablePawnAttackableSquare(); // 폰이 공격할수있는 경로를 비활성화한다 : 보여주기(감추기)

		castlingHelper.initCastlingSquareList(); // 캐슬링 할 수 있는 경로를 비활성화 한다 : 보여주기(감추기)

		enPassantHelper.cancelEnPassant(); // 앙파상 할 수있는 경로를 비활성화 한다 : 보여주기(감추기)

		checkmateHelper.disableCheckmateRoute(); // 체크메이트 할 수 있는 경로를 비활성화 한다 : 보여주기(감추기)
	}

	private static void moveSelectedChessman(int x, int y) {
		// 이동경로로 이동했을 때
		if (boardSquare[x][y].isContainChessman()) {
			// 잡 았을때 잡힌말을 추후에 CurrentChessmanView에 적용 시키기위해 테스트 하던 부분
			// 문제는 앙파상을 하는경우에 잡힌 폰이 표시되지 않는 다는 것과 캐슬링을 하는 경우 Rook이 잡힌걸로 표시된다는 것이다.
			// 그래서 움직임이 끝날때마다 각 말을 세어줄까... 생각은 하고있는데 아직 미정이다.
			// System.out.println(boardSquare[x][y].getChessman().getChessmanType().name());
		}

		// 캐슬링 관련 & 앙파상 관련
		if (selectedSquare.getChessman() instanceof King && castlingHelper.castlingSquareList.size() != 0) {
			// 선택된 체스말이 왕이고 캐슬링 할 수 있다면
			castlingHelper.moveCastling(x, y); // 흠?
			enPassantHelper.initEnPassantSquare(); // 앙파상이 활성화 되어있는 상태로 캐슬링을 하면 앙파상은 무효
		} else {
			boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman()); // 그 이외의 움직임을 실행했을때
		}

		if (selectedSquare.getChessman() instanceof Pawn) { // 선택된 말이 폰이면
			pawnAttackHelper.disablePawnAttackableSquare();
			enPassantHelper.checkEnPassant(x, y); // 흠?
		}

		if (selectedSquare.getChessman() instanceof King) {
			((King) selectedSquare.getChessman()).setIsMoved();
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

		isWhite = !isWhite; // 이동을 했을 때 움직인 색의 반대색으로 설정한다.
		initSquareEvent(isWhite);
	}

	private static void initSquareEvent(boolean isWhite) {
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

	private static void disableSquareClickEvent() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				boardSquare[x][y].setEnableClickEvent(false);
			}
		}
	}

	private static void enableSquareClickEvent(boolean isWhite) {
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
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
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

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
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

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * CheckmateHelper는 BoardPanel의 Chessman이 이동하고 턴이 넘어가기 전 왕을 잡을수 있는 경로를 가진 체스말이 있는지 검사하는 기능을 구현한다.
	 * 따라서 체크메이트 핼퍼는 턴이 넘어가기전 (흰색/검은색)색상의 체스말들의 경로를 계산하여 체크메이트 할 수 있는 유효한 경로만을 저장했다가
	 * 턴이 넘어가 반대편 색상의 말을 클릭했을때 체스메이트 할 수 있는 경로를 보여주도록 설계되었다. 
	 * 
	 * 기존의 MoveableRouteCalculator의 계산된 경로에서 체크메이트 루트를 검사해서 추출하며
	 * PawnAttackHelper를 이용해서 폰으로 왕을 체크메이트 할 수있는 경우에 대해서도 계산한다.
	 *
	 * CheckmateHelper는 MoveHelper와 다르게 경로를 보여주는 역할만 하는 것이고 경로도 특정 경로만 표시해야 되기에 분류하였다.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class CheckmateHelper {
		// 체크메이트 할 수있는 경로들의 리스트
		private ArrayList<MoveableRoute> checkmateRouteList = new ArrayList<>();
		// 검사하는 체스말의 경로 리스트
		private ArrayList<MoveableRoute> moveableRouteList = new ArrayList<>();
		// 폰이 체크메이트를 할 수있다면 체크메이트 가능 칸를 담는 변수
		private BoardSquare pawnCheckmateSquare = boardSquare[0][0];

		// 계산된 체크메이트 가능 경로를 보여주는 메서드로 왕을 선택했을때와 다른 말을 골랐을때로 분리한 이유는
		// 왕은 계산된 경로로 이동이 가능해도 이동이 불가해야되고 다른말은 계산된 경로와 움직일 수 있는 경로가 겹칠경우
		// 해당경로로 이동해서 왕의 체크메이트 경로를 차단 할 수 있어야 되기에 분리하였다.
		private void showCheckmateRoute() {
			if (isPawnHasCheckmateSquare()) {
				if (selectedSquare.getChessman() instanceof King) { // 선택된 말이 왕일때
					pawnCheckmateSquare.setSquareMoveableCheckmateColor();
				} else {
					pawnCheckmateSquare.setSquareCheckmateColor();
				}
			}

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

		// 폰이 체크메이트 square를 가지고있는지 아닌지
		private boolean isPawnHasCheckmateSquare() {
			return pawnCheckmateSquare != boardSquare[0][0];
		}

		// 왕을 선택했을때 체크메이트 루트를 보여주는 것인데 선택된 왕을 다시 누를수있도록 선택된 칸은 클릭이 가능하게 해준다.
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

			pawnCheckmateSquare.setSquareOriginalColor();
		}

		// 말이 움직인 뒤에 체스말들 중에 체크메이트가 가능한 말이 있는지 검사하는 메서드로써
		// 검사 결과 적의 왕을 체크가 가능한 말이 있다면 그말의의 경로를 받아서 저장한다.
		private void checkEnemyChessmanRoute() {
			pawnCheckmateSquare = boardSquare[0][0];
			checkmateRouteList.clear();
			moveableRouteList.clear();

			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (isAlly(x, y) && !(boardSquare[x][y].getChessman() instanceof Pawn)) {
						Chessman enemyChessman = boardSquare[x][y].getChessman();
						moveableRouteList = MoveableRouteCalculator.selectChessman(enemyChessman, x, y);
						checkEnemyCheckmateRoute();
					} else if ((isAlly(x, y) && boardSquare[x][y].getChessman() instanceof Pawn)) {
						pawnAttackHelper.checkPawnAttackableSquare(x, y);
						setPawnCheckmateSquare();
					}
				} // end of for x
			} // end of for y
		}

//		폰으로 부터 폰이 공격할 수있는 경로를 받아와서 폰이 공격 할 수있는 경로중 왕이 있는 경로만 뽑아서 pawnCheckmateSquare에 저장해준다.
		private void setPawnCheckmateSquare() {
			if (!pawnAttackHelper.pawnAttackableSquare.isEmpty()) {
				for (BoardSquare boardSquare : pawnAttackHelper.pawnAttackableSquare) {
					if (boardSquare.getChessman() instanceof King) {
						pawnCheckmateSquare = boardSquare;
						// new BoardEventInfoView(Images.CHECK);
					}
				}
			}
		}

		// 선택된 칸의 체스말이 적군인지 아닌지 검사한다.
		private boolean isAlly(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() == isWhite;
		}

		// 상대편의 체스말인지 검사한다.
		private boolean isEnemy(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() != isWhite;
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
		private boolean isEnemyKing(int x, int y) {
			return isEnemy(x, y) && boardSquare[x][y].getChessman() instanceof King;
		}

		// 적군이 체크메이트 할 수 있는 경로가 있는지 검사해서 있다면 그 경로를 checkmateRouteList에 추가한다.
		private boolean isEnemyHasCheckmateRoute(MoveableRoute moveableRoute, Coordinate coordinate) {
			if (isEnemyKing(coordinate.getX(), coordinate.getY())) {
				checkmateRouteList.add(moveableRoute);
				// new BoardEventInfoView(Images.CHECK);
				return true;

			} else {
				return false;
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
	private class EnPassantHelper {
		// 앙파상을 할 수 있는 칸은 한번에 하나만 있을 수 있기 때문에 기존의 BoardSquare자료형을 이용하였고 초기 값을 null로 하는대신
		// 기존의 [0][0]자료를 활용했다.
		private BoardSquare enPassantSquare = boardSquare[0][0];
		// 앙파상을 검사해야 되는경우는 폰의 초기 움직임이 2일때 뿐이기때문에 초기 움직임 Y를 변수로써 가지고있도록 했다.
		private int initY = -1;
		// 말이 앙파상 할 수있는 좌표에 있는지 검사하기위한 변수로써 앙파상을 할 수있는 경우는 위의 initY로써의 조건을 성립하면서 아래의 조건을
		// 성립할 때이다.
		// 예) 흰색폰의 위치 기준으로 왼쪽 또는 오른쪽에 검정폰이 위치했을 때 이다 그렇다면
		// 흰색폰의 x기준으로 검정폰의 x와의 차가 절대값 1, y기준으로 검정폰의 y와의 차가 0인 위치에 있다면
		// 앙파상을 할 수 있는 것이다.
		private int enPassantX = -1;
		private int enPassantY = -1;

		// 한번 사용한 앙파상칸은 쓸필요가 없기때문에 기존의 초기값으로 정해둔 것으로 초기화한다.
		private void initEnPassantSquare() {
			enPassantSquare = boardSquare[0][0];
		}

		// 초기값으로 정해진 값이 있으면 비어있는 것이다.
		private boolean isEnPassantSquareNotEmpty() {
			return enPassantSquare != boardSquare[0][0];
		}

		// 앙파상 칸이 비어있지 않고 앙파상 스퀘어가 가진값과 움직이고자하는 보드스퀘어가 같다면 앙파상 움직임이다.
		private boolean isEnPassantMove(int x, int y) {
			return isEnPassantSquareNotEmpty() && boardSquare[x][y] == enPassantSquare;
		}

		// 초기 y값과 움직인 y의 차가 2인지 검사한다 ( 2라면 초기움직임 2를 했다고 보기위함이다. )
		private boolean isPawnMoveTwoSquares(int y) {
			return Math.abs(initY - y) == 2;
		}

		// 위에서 언급했던 앙파상이 가능한 위치에 있는지 확인하는 메서드
		private boolean isAvailEnPassantCoordinate(int x, int y) {
			return (Math.abs(enPassantX - x) == 1) && (enPassantY - y == 0);
		}

		// 폰을 클릭했을때 호출되는 메서드로써 폰의 y위치를 저장하고 앙파상 할 수 있는 칸이 있다면 보여준다.
		private void checkShowEnPassantSquare(int x, int y) {
			initY = y;
			if (isEnPassantSquareNotEmpty() && boardSquare[x][y].getChessman() instanceof Pawn) {
				if (isAvailEnPassantCoordinate(x, y)) {
					enPassantSquare.setSquareAttackableColor();
				}
			}
		}

		// 앙파상을 취소한다.
		private void cancelEnPassant() {
			if (enPassantSquare != boardSquare[0][0]) {
				enPassantSquare.setSquareOriginalColor();
			}
		}

		// 움직인 좌표가 앙파상으로 설정된 좌표라면 앙파상위에 있는 체스말이 흰색인지 검은색인지 판단해서 그 뒤에 있는 말을 제거한다.
		private void moveEnPassant(int x, int y) {
			if (isEnPassantMove(x, y)) {
				if (enPassantSquare.getChessman().isWhite()) {
					boardSquare[x][y + 1].removeChessmanFromSquare();
				} else {
					boardSquare[x][y - 1].removeChessmanFromSquare();
				}
			}
		}

		// 폰이 움직였을때 움직인 좌표의 폰이 앙파상 대상인지 확인한다. 앙파상 대상이라면 앙파상 스퀘어를 셋팅하고 메서드를 종료하고
		// 앙파상 된 칸으로 이동하는 경우엔 앙파상 잡기를 실행한다.
		private void checkEnPassant(int x, int y) {
			((Pawn) selectedSquare.getChessman()).setIsMoved();
			// 폰이 2칸 움직였다면 앙파상 칸을 설정하고 메서드를 벗어난다.
			if (isPawnMoveTwoSquares(y)) {
				setEnPassantSquare(selectedSquare.getChessman().isWhite(), x, y);
				return;
			}

			moveEnPassant(x, y);

			enPassantSquare.setSquareOriginalColor();
			initEnPassantSquare();
		}
		// 지정된 좌표의 square가 적 폰을 가지고있는지 확인한다.
		private boolean isContainEnemyPawn(boolean isWhite, int x, int y) {
			return Coordinate.isValidate(x, y) && isContainPawn(x, y)
					&& (boardSquare[x][y].getChessman().isWhite() != isWhite);
		}
		// 지정된 좌표의 square가 폰을 가지고있는지 확인한다.
		private boolean isContainPawn(int x, int y) {
			return (boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman() instanceof Pawn);
		}
		// 폰의 좌우에 적군 폰이 있는지 확인하고 있다면 앙파상 스퀘어를 셋팅한다.
		private void setEnPassantSquare(boolean isWhite, int x, int y) {
			enPassantX = x;
			enPassantY = y;

			if (isWhite) {
				if (isContainEnemyPawn(isWhite, x - 1, y) || isContainEnemyPawn(isWhite, x + 1, y)) {
					enPassantSquare = boardSquare[x][y + 1];
				}

			} else {
				if (isContainEnemyPawn(isWhite, x - 1, y) || isContainEnemyPawn(isWhite, x + 1, y)) {
					enPassantSquare = boardSquare[x][y - 1];
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
	private class CastlingHelper {
		/**
		 * castlingSquareList : 캐슬링 가능한 BoardSquare를 저장하고 있는 리스트, 굳이 리스트로 선언한 이유는 좌 우 룩이
		 * 동시에 캐슬링 할 수 있는 대상일 경우도 처리해 주기 위함이다.
		 */
		private ArrayList<BoardSquare> castlingSquareList = new ArrayList<>(); // 내부클래스

		/**
		 * 1.setShowCastlingSquare()를 호출하는 부분에서 selectedSquare가 King임을 이미 검증하였기 때문에 왕이
		 * 움직이지 않았는지만 확인하고 2.움직이지 않았다면 캐슬링에 적합한 검증과정(1.킹과 룩 사이에 말들이 없고 2.룩이 움직인적이없어야
		 * 한다)을 거치고 검증과정에 맞으면 캐슬링 스퀘어에 추가한다. 3.그리고 캐슬링 가능한 칸들을 보여준다.
		 */
		private void setShowCastlingSquare() {
			if (!((King) selectedSquare.getChessman()).isMoved()) {

				validateAndSetCastlingSquareList();

				showCastlingSquareList();
			}
		}

		// 캐슬링에 적합한 검증과정(1.킹과 룩 사이에 말들이 없고 2.룩이 움직인적이없어야 한다)을 거치고 검증과정에 맞으면 캐슬링 스퀘어에
		// 추가한다.
		private void validateAndSetCastlingSquareList() {
			castlingSquareList.clear();
			if (isWhite) {

				if (isXAndUpperSquareEmpty(5, 7) && isSquareContainRook(7, 7)) {
					addCastlingSquareIfRookNotMoved(7, 7);
				}

				if (isXAndUpperTwoSquareEmpty(1, 7) && isSquareContainRook(0, 7)) {
					addCastlingSquareIfRookNotMoved(0, 7);
				}
			} else {

				if (isXAndUpperSquareEmpty(5, 0) && isSquareContainRook(7, 0)) {
					addCastlingSquareIfRookNotMoved(7, 0);
				}

				if (isXAndUpperTwoSquareEmpty(1, 0) && isSquareContainRook(0, 0)) {
					addCastlingSquareIfRookNotMoved(0, 0);
				}
			}
		}

		// x, x + 1 의 칸이 비었는지 확인한다
		private boolean isXAndUpperSquareEmpty(int x, int y) {
			return !boardSquare[x][y].isContainChessman() && !boardSquare[x + 1][y].isContainChessman();
		}

		// x, x + 1, x + 2 의 칸이 비었는지 확인한다
		private boolean isXAndUpperTwoSquareEmpty(int x, int y) {
			return isXAndUpperSquareEmpty(x, y) && !boardSquare[x + 2][y].isContainChessman();
		}

		// 칸이 룩을 가지고있는지 확인한다.
		private boolean isSquareContainRook(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman() instanceof Rook;
		}

		// 룩이 움직이지 않았는지 확인한다.
		private boolean isRookNotMoved(int x, int y) {
			return !((Rook) boardSquare[x][y].getChessman()).isMoved();
		}

		// 룩이 움직이지 않았다면 캐슬링 가능한 좌표에 추가한다.
		private void addCastlingSquareIfRookNotMoved(int x, int y) {
			if (isRookNotMoved(x, y)) {
				castlingSquareList.add(boardSquare[x][y]);
			}
		}

		// 캐슬링 가능한 칸들의 리스트가 비어있지않으면 칸들을 캐슬링 색으로 셋팅해준다
		private void showCastlingSquareList() {
			if (!castlingSquareList.isEmpty()) {
				for (BoardSquare castlingSquare : castlingSquareList) {
					castlingSquare.setSquareCastlingColor();
				}
			}
		}

		// 캐슬링 가능한 칸들을 초기화해준다.(캐슬링 가능 표시가 된 것들을 원래대로 돌리고 리스트릴 비운다)
		private void initCastlingSquareList() {
			if (!castlingSquareList.isEmpty()) {
				for (BoardSquare castlingSquare : castlingSquareList) {
					castlingSquare.setSquareOriginalColor();
				}
			}
			castlingSquareList.clear();
		}

		/**
		 * 킹을 클릭한 상태에서 캐슬링 표시가 된 룩을 클릭했을때 수행되는 메서드 내부적으로 캐슬링을 수행해주는 메서드를 호출한다.
		 * 
		 * @param x
		 *            : 캐슬링 표시가 된 룩을 클릭했을때 룩의 x 좌표
		 * @param y
		 *            : 캐슬링 표시가 된 룩을 클릭했을때 룩의 y 좌표 각 좌표를 받는 이유는 룩이 x좌표 0 또는 7에 위치하고있을때
		 *            이동하는 칸이 다르기 때문이다.
		 */
		private void moveCastling(int x, int y) {
			operateCastling(x, y);
			initCastlingSquareList();
		}

		// 움직임으로써 클릭한 sqaure의 좌표가 룩의 좌표일 때 캐슬링 움직임을 실행한다. 그 이외라면 단순 움직임을 실행한다.
		private void operateCastling(int x, int y) {
			if ((x == 7 && y == 7) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 0 && y == 0)) {
				operateCastlingMove(x, y);
			} else {
				boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
			}
		}

		// 캐슬링 움직임을 실행해주는 메서드로써 선택된칸(왕) 그리고 움직임으로써 클릭한 square의 좌표에 대해 캐슬링 움직임을 수행한다.
		private void operateCastlingMove(int x, int y) {
			((King) selectedSquare.getChessman()).setIsMoved();
			((Rook) boardSquare[x][y].getChessman()).setIsMoved();

			if (x == 0) {

				boardSquare[x + 2][y].setChessmanOnSquare(selectedSquare.getChessman());
				boardSquare[x + 3][y].setChessmanOnSquare(boardSquare[x][y].getChessman());
			} else if (x == 7) {

				boardSquare[x - 1][y].setChessmanOnSquare(selectedSquare.getChessman());
				boardSquare[x - 2][y].setChessmanOnSquare(boardSquare[x][y].getChessman());
			}

			selectedSquare.removeChessmanFromSquare();
			boardSquare[x][y].removeChessmanFromSquare();
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////
}

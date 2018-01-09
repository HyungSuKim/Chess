package com.bfpaul.renewal.chess.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;

import com.bfpaul.renewal.chess.Theme;
import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.ChessmanType;
import com.bfpaul.renewal.chess.chessman.data.King;
import com.bfpaul.renewal.chess.chessman.data.Pawn;
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

// 체스판(Board)의 시각적 구성요소로는 Chessman(체스말), Square(칸)들이 있다.
// Board는 이 구성요소들을 항상 정해진 방법으로 보여주지 않는다.(보여 줄 수 없다.)(View가 아닌이유)
// Board의 각 구성요소는 상황마다 보여주는 내용이 다를 수 밖에없다.(선택된 체스말에 따라 위치에따라 다른체스말의 위치에따라 등등)
// 각각에 상황에 따라 별도의 방법을 수행해서 다르게 보여 줄 수 밖에 없다.
// 따라서 일정 방법을 통해 일정하게 보여주는 View로써의 역할을 하는 목적이라기 보다는
// 상황에 따라 각각의 보여주는 방법들이 유기적으로 작동해서 보여주는 내용이 상황마다 다른 Panel로써 역할을 하는 것이 목적이고 그렇게 될 수 밖에 없다.

// BoardPanel클래스는 체스판의 속성을 갖기위해 체스판의 구성 요소인 칸을 표현하는 64개의 BoardSquare들을 가지고 있다.
// BoardPanel은 특정 칸(BoardSquare)의 위치(좌표)와 가지고 있는 데이터(체스말)을 이용하여
// 다른 칸들과 연관지어 표현해야 될 상황들을 계산하고 그 결과를 다른 칸들이 표현하도록 제어 할 수 있다.
// 초기 체스말이 놓여져 있는 보드 자체를 하나의 BoardPanel로써 바라본다.(생각한다)

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
	// BoardPanel의 BoardSquare들을 가지고 있는 멤버변수로 써 Square의 제어를 위해 선언 했던 멤버변수인데
	// 멤버변수를 사용하지 않더라도 특정 Square를 알 수 있는 방법이 있고
	// 보드패널 내에서 사용되는 것 이외에 다른 클래스에서 사용되지 않고
	// 초기에 생성되면 그 이후에 칸 자체가 없어지거나 아예 바뀌는 것은 아니기 때문에 필요없다고 생각한다.
	// 통과!! (나중에 지워버리잫...)
	// private final BoardSquare[][] BOARD_SQUARE = new BoardSquare[8][8];
	
	// BoardPanel의 Square에 접근하기 위해서는 x, y좌표가 필요하다.
	// 각 Square의 OnClickListener는 현재 클릭 된 Square의 x, y 좌표를 제공할 수 있다.
	// 하지만 과거 클릭 되었던 Square의 x, y좌표가 필요한 경우가 있다.
	// 따라서 BoardPanel에 과거 선택되었던 x, y좌표를 기억 할 수 있는 공간(변수)가 필요하다.
	// 이를 메서드 내에 지역변수를 통해서 혹은 메서드의 파라미터를 통해서 제공하는 것은 
	// OnClickListener가 현재 x, y좌표를 제공하고 호출 될 때 마다 초기화되기 때문에 불가능하다.
	private BoardSquare selectedSquare = null;
	private Coordinate selectedCoordinate = Coordinate.createInValidateCoordinate();
	
	// 다시 생각해보기
	private BoardSquare movedSquare = null;
	// 이 isWhite는 사용자가 선택 할 수 있는 체스말의 색상을 나타내기 위한 목적으로 작성되었다.
	// isWhite는 턴이 종료되는 시점에 값이 변한다. 즉, 턴에 따라 사용자가 선택 할 수 있는 체스말을 제한하기 위해서 필요한 변수다.
	// 하지만 멤버변수 selectedSquare를 통해서 사용자가 선택한 체스말의 색상을 얻을 수 있다.
	// 따라서 selectedSquare의 체스말 색상 정보를 이용해서 턴에 따라 사용자가 선택 할 수 있는 제스말을 제한 할 수 있다.
	// 지금 isWhite는 초기의 목적과 다르게 사용 된 곳도 많다. (적군을 파악한다던가 아군을 파악한다던가)
	// 이는 멤버변수의 초기 목적과 다르게 사용된 것이다.
	// 
	// 따라서 이 멤버변수는 의미가 없고 필요없다고 생각한다.
	private boolean isWhite = true;
	// 이 멤버변수는 BoardPanel에서 할 역할이 끝났음을 표현해주는 멤버변수이다.
	// BoardPanel, Timer, CurrentChessmanView는 정해진 순서로 실행되어야 하는 구조를 가지고있다.
	// BoardPanel의 수행 직후 BoardPanel의 정보를 얻어야 하는 CurrentChessmanView는
	// Layer를 통해 BoardPanel의 수행(1턴)이 완료되었음 isFinish() 메서드를 통해 이 멤버변수의 데이터를 확인함으로써 수행된다.
	// 따라서 이 멤버변수는 필요하다고 생각한다.
	private boolean isFinish;

	private BoardPanel() {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);
		setSquaresOnBoard();
		FunctionType.bind(this);
	}

	// BoardPanel에서 생성 된 Board를 바라볼때 흰색말을 기점으로 바라볼지, 검정말을 기점으로 바라볼지의 개념이 필요했다.
	// 기존에는 생성자에 파라미터로써 색상을 받아서 위의 개념을 수행 하였는데 생성자와 파라미터의 값으로만으로는 목표를 표현하는데 부족하였다.
	// 따라서 위의 목표를 표현하는 이름을 가진 메서드를 만들어 목표를 수행하였다.
	// 이 메서드를 이용해서 BoardPanel을 만들고 체스말을 색상에 맞게(ㅇㅇ말을 기점으로 바라볼지) 배치한다는 것을 메서드 명을 통해 표현
	// 할 수 있게 되었다.
	// 위의 목표를 위해 외부에서 BoardPanel의 생성자의 접근을 막고 메서드를 통해서 목표에 맞는 BoardPanel을 내부적으로 생성하고자 했다.
	// static 사용 이유 : BoardPanel의 인스턴스가 생성되기 전에 BoardPanel의 메서드에 접근하는 것은 불가능하다. 위에서 언급 한 것과 같이 생성자는
	// private 접근제어자를 통해 막혀있다. 따라서 static을 사용하여 인스턴스가 생성되지 않았음에도 BoardPanel의 메서드를 사용 할 수 있게 하고자 하였다.
	public static BoardPanel createBoardPanelAtWhiteSide() {
		return new BoardPanel().setWholeChessmanOnBoard(true);
	}

	public static BoardPanel createBoardPanelAtBlackSide() {
		return new BoardPanel().setWholeChessmanOnBoard(false);
	}
	
	private BoardSquare getBoardSquare(Coordinate coordinate) {
		return getBoardSquare(coordinate.getX(), coordinate.getY());
	}

	private BoardSquare getBoardSquare(int x, int y) {
		return (BoardSquare) getComponent(x + (y * 8));
	}

	private void setSquaresOnBoard() {
		forEach((x, y) -> add(createBoardSquare(x, y), new LinearConstraints(1, LinearSpace.MATCH_PARENT)));
	}

	private BoardSquare createBoardSquare(int x, int y) {
		BoardSquare boardSquare = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR);
		boardSquare.setOnClickListener(getListenerToControlChessman(x, y));
		return boardSquare;
	}

	private OnClickListener getListenerToControlChessman(int x, int y) {
		return new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// 로직 재검토 전체 if / else - if / else 관계 다시 고민
				if (getBoardSquare(x, y).isContainChessman() && selectedCoordinate.isInValidate()) {
					selectChessman(x, y);
				} else if (selectedSquare == getBoardSquare(x, y)) {
					deSelectChessman();
				} else if (isSameColorChessmanClicked(x, y)) {
					selectChessman(x, y);
				} else {
					moveSelectedChessmanTo(x, y);
				}
			}
		};
	}

	// getBoardSquare(x, y).getBackground() != Color.YELLOW; 한번에 두개의 관심사(목표)가 같이
	// 표현되어있어서 이상함
	private boolean isSameColorChessmanClicked(int x, int y) {
		return getBoardSquare(x, y).isContainChessman()
				&& selectedSquare.isContainChessmanWhite() == getBoardSquare(x, y).isContainChessmanWhite()
				&& getBoardSquare(x, y).getBackground() != Color.YELLOW;

	}

	// selectChessman의 주요 목표가 무엇인지, 목표를 잘 표현하고있는지 고민
	private void selectChessman(int x, int y) {
		clearSquaresEventColor();

		selectedSquare = getBoardSquare(x, y);

		for (Function helper : FunctionType.getList()) {
			helper.show(selectedSquare.getChessman(), x, y);
		}

		selectedSquare.setSquareMoveableColor(); // 눌린말의 칸은 움직일수있는 색상으로 표시해주고

	}

	// deSelectChessman의 주요 목표가 무엇인지, 목표를 잘 표현하고있는지 고민
	private void deSelectChessman() {
		clearSquaresEventColor();

		selectedSquare.setSquareOriginalColor();

		selectedSquare = null;

		enableSquareClickEvent();

	}

	// moveSelectChessmanTo의 주요 목표가 무엇인지, 목표를 잘 표현하고있는지 고민
	private void moveSelectedChessmanTo(int x, int y) {
		/* 210 ~ 212 이전에 움직인 칸을 넣는 코드 위치, 표현 이상함 '표현' 에 신경안쓰고, '기능'에 집중한듯 */
		// 움직였던 칸 으로써의 정보를 저장하는 것이므로 movedSelectedChessman의 메서드가 호출되는 시점에 이미 움직였다고 가정하는
		// 것이 맞다고 생각했다.
		// 따라서 메서드의 시작부에서 데이터를 저장한다.
		movedSquare = getBoardSquare(x, y);

		ChessmanType selectedChessmanType = selectedSquare.getContainChessmanType();
		/*
		 * if ~ else-if ~ else 각기 로직의 일관성 부족, (데이터 정제후 -> 로직) 표현 부족 밑으로 다 구림
		 */
		if (selectedChessmanType == ChessmanType.KING) {
			King king = ((King) (selectedSquare.getChessman()));
			king.setMoved();
			((Castling) FunctionType.CASTLING.get()).operateCastling(x, y);
		} else {
			if (selectedChessmanType == ChessmanType.ROOK) {
				Rook rook = ((Rook) (selectedSquare.getChessman()));
				rook.setMoved();
			}

			if (selectedChessmanType == ChessmanType.PAWN) {
				Pawn pawn = ((Pawn) (selectedSquare.getChessman()));
				pawn.setMoved();
				pawn.setMovedSquareCount(Math.abs(getSelectedSquareY(x, y) - y));

				/* DataSetting for Pawn EnPassant */
				EnPassant enPassantHelper = ((EnPassant) FunctionType.ENPASSANT.get());
				if (enPassantHelper.isEnPassantMove(x, y)) {
					enPassantHelper.moveEnPassant(x, y);
				}
			}

			getBoardSquare(x, y).setChessmanOnSquare(selectedSquare.getChessman());
			selectedSquare.removeChessmanFromSquare();
		}

		selectedSquare.setSquareOriginalColor();
		selectedSquare = null;

		clearSquaresEventColor();

		((Mate) FunctionType.MATE.get()).checkMateRoute();

		disableSquareClickEvent();

		/* 애매한부분 */
		boolean isPromotion = getBoardSquare(x, y).isContainChessman()
				&& getBoardSquare(x, y).getContainChessmanType() == ChessmanType.PAWN && (y == 0 || y == 7);

		if (isPromotion) {
			new PawnPromotionSelectEventFrame(isWhite).setOnPromotionSelectedListener(chessmanType -> {
				isFinish = true;
				setChessmanOnSquare(chessmanType.createChessman(isWhite), x, y);
			});
		} else {
			isFinish = true;
		}

	}

	/* DataSetting for Pawn EnPassant */
	/*
	 * 고친 결과물... 문제점 : selectedSquare가 존재하나 BoardSquare 자료형으로써 이동 한 y좌표에대해서 비교 할 수가
	 * 없었다.
	 */
	// 따라서 해결방안으로 생각한 것이 selectedSquare와 맞는 BoardSquare를 찾아서 그 y좌표를 구하고 이동한 y좌표에 대해서
	// 비교하자고 생각했다.
	private int getSelectedSquareY(int x, int y) {
		int selectedSquareY = 0;

		for (int findY = 0; findY < 8; findY++) {
			for (int findX = 0; findX < 8; findX++) {
				if (selectedSquare == getBoardSquare(findX, findY)) {
					selectedSquareY = findY;
				}
			}
		}

		return selectedSquareY;
	}

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에
	// 올려준다.
	// 올려주는 역할을 BoardSquare class의 메서드를 이용해서 하는데 이를통해 원하는 로직을 따라서 체스말이 추가된다.
	private void setChessmanOnSquare(Chessman chessman, int x, int y) {
		getBoardSquare(x, y).setChessmanOnSquare(chessman);
	}

	// 모든 체스말을 보드위에 셋팅한다.
	private BoardPanel setWholeChessmanOnBoard(boolean isWhite) {
		for (ChessmanType type : ChessmanType.values()) {
			setChessmanOnBoard(isWhite, type);
		}
		return this;
	}

	// 체스말의 타입별로 보드의 초기위치에 설정해주기위한 메서드인데 각 타입에 따라 보드위 초기위치에 설정해준다.
	private void setChessmanOnBoard(boolean isWhite, ChessmanType type) {
		switch (type) {
		case KING:
			setSingleChessmanOnBoard(isWhite, ChessmanType.KING);
			break;
		case QUEEN:
			setSingleChessmanOnBoard(isWhite, ChessmanType.QUEEN);
			break;
		case BISHOP:
			setPairChessmanOnBoard(isWhite, ChessmanType.BISHOP);
			break;
		case KNIGHT:
			setPairChessmanOnBoard(isWhite, ChessmanType.KNIGHT);
			break;
		case ROOK:
			setPairChessmanOnBoard(isWhite, ChessmanType.ROOK);
			break;
		case PAWN:
			setPawnOnBoard(isWhite, ChessmanType.PAWN);
			break;
		default:
		}
	}

	private int getWhiteYCoordinateByColor(boolean isWhite) {
		return isWhite ? 7 : 0;
	}

	private int getBlackYCoordinateByColor(boolean isWhite) {
		return isWhite ? 0 : 7;
	}

	// 초기 갯수가 1개인 말(킹, 퀸) 의 경우 enum의 ordinal이 0, 1인데 여기서 3을 더한 값이 실제 말이 놓일 x로써 의미를
	// 갖게 된다는 것을 알고
	// 이러한 규칙을 이용해서 초기 갯수가 1개인 말을 한꺼번에 처리해서 Square에 올려주게되었다.
	private void setSingleChessmanOnBoard(boolean isWhite, ChessmanType type) {

		int xCoordinate;

		if (type == ChessmanType.KING) {
			xCoordinate = type.ordinal() + 4;
		} else {
			xCoordinate = type.ordinal() + 2;
		}

		setChessmanOnSquare(type.createChessman(true), xCoordinate, getWhiteYCoordinateByColor(isWhite));
		setChessmanOnSquare(type.createChessman(false), xCoordinate, getBlackYCoordinateByColor(isWhite));
	}

	// 초기 갯수가 2개인 말(비숍, 나이트, 룩) 의 경우 enum의 ordinal이 2, 3, 4인데 여기서 3을 더한 값과 7에서 이 값을
	// 뺀 값이
	// 실제 말이 놓일 x로써 의미를 갖게 된다는 것을 알고 이러한 규칙을 이용해서 초기 갯수가 2개인 말을 한꺼번에 처리해서 Square에
	// 올려주게되었다.
	private void setPairChessmanOnBoard(boolean isWhite, ChessmanType type) {
		int whiteY = getWhiteYCoordinateByColor(isWhite);
		int blackY = getBlackYCoordinateByColor(isWhite);

		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), whiteY);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), whiteY);
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), blackY);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), blackY);
	}

	// 폰들을 보드위 정해진(초기) 위치에 셋팅해주기위해서 작성하였다. 폰의 경우 초기 갯수만큼 반복해서 생성해서 Square위에 올려주는데
	// 흰색과 검정색이 대칭으로 갯수가 똑같음을 이용했다.
	private void setPawnOnBoard(boolean isWhite, ChessmanType type) {
		int initCount = type.getInitCount();
		int whiteY = getWhiteYCoordinateByColor(isWhite);
		int blackY = getBlackYCoordinateByColor(isWhite);

		if (whiteY == 7 && blackY == 0) {
			whiteY = 6;
			blackY = 1;
		} else {
			whiteY = 1;
			blackY = 6;
		}

		for (int count = 0; count < initCount; count++) {
			setChessmanOnSquare(type.createChessman(true), count, whiteY);
			setChessmanOnSquare(type.createChessman(false), count, blackY);
		}
	}

	// 반복문 for가 중복되어 쓰이는 경우가 많음 주로 for가 사용되는 이유는 BoardSquare를 전부 접근하기 위해서였다.
	// 하지만 이 때 x, y의 좌표값을 이용해서 별도의 행동을 하는 경우의 논리를 가진 경우도 있었다.
	// 따라서 x, y를 이용하면 BoardSquare를 구할 수 있기 때문에 x, y를 기본이 되는 개념으로 생각해서
	// x, y를 이용할 수 있는 forEach를 먼저 만들고 이 forEach를 이용해서 BoardSquare에 접근 할 수 있는 메서드를
	// 작성하였다.
	// interface를 사용한 callback 기법을 이용하였다.
	// forEach 메서드를 호출하는쪽에서 구현된 인터페이스를 forEach에 전달하면
	// forEach의 로직을 수행하는 도중 전달받은 인터페이스(호출된쪽)의 onSelected메서드를 수행해서 callBack 하게 된다.
	// forEach의 작성 이유 : 중첩된 for문을 사용하면 로직의 목표가 무엇인지 표현하고 집중하는데 어려움이 있다.
	// (for문이 어떻게 돌아가는지 까지 신경써야되고 깊이도 늘어나니까)
	// 따라서 이 중첩된 for문을 대신 수행해주는 forEach를 통해 로직의 목표가 무엇인지 표현하고 집중하는데 도움을 주고자 했다.
	// CallBack 개념 : A라는 인스턴스의 a라는 메서드는 B라는 인스턴스의 b라는 메서드를 통해 수행 될 수 있다고 가정한다.
	// A는 자기자신의 인스턴스를 B의 b메서드에 전달하여 b는  A의 a메서드를 수행해주는데 A의 입장에서 봤을때는 b의 메서드를 call했는데
	// b의 메서드는 A자기자신의 a를 수행시켜주므로 back이다. 따라서 이러한 동작을 하는 것을 CallBack 구조라 한다. 
	// 메서드를 수행하려면 결국은 인스턴스를 넘겨주어야 하는데 이를 인터페이스를 익명클래스로 구현함으로써 구현된 메서드를 CallBack 하는 것이다.
	private interface OnSelectedSquareListener {
		public void onSelected(BoardSquare square);
	}

	private interface OnSelectedCoordinateListener {
		public void onSelected(int x, int y);
	}

	private void forEach(OnSelectedCoordinateListener onSelectedCoordinateListener) {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				onSelectedCoordinateListener.onSelected(x, y);
			}
		}
	}

	private void forEach(OnSelectedSquareListener onSelectedSquareListener) {
		forEach((x, y) -> onSelectedSquareListener.onSelected(getBoardSquare(x, y)));
	}

	private void disableSquareClickEvent() {
		forEach(square -> square.setEnableClickEvent(false));
	}

	private void enableSquareClickEvent() {
		forEach(square -> {
			if (square.isContainChessman() && square.isContainChessmanWhite() == isWhite) {
				square.setEnableClickEvent(true);
			} else {
				square.setEnableClickEvent(false);
			}
		});
	}

	private void clearSquaresEventColor() {
		forEach(square -> square.setSquareOriginalColor());
	}

	/**
	 * ㅇㅇㅇㅇType(이름 아직 미정) 은 BoardPanel에 정의 되어있는 ㅇㅇㅇㅇ들을 사용자측에서 쉽게 알 수 있도록 도와주고
	 * 위 정보를 이용하여 이 ㅇㅇㅇㅇ들을 받아서 사용하기 위해서 작성된 열거형 클래스이다.
	 * 
	 * 따라서 ㅇㅇㅇㅇType은 사용자측에서 사용 할 수 있는 ㅇㅇㅇㅇ들을 List로 갖는다.
	 * 
	 * 이 열거형클래스의 List를 사용하기위해서는 선행 조건으로 BoardPanel의 인스턴스가 생성되고
	 * 각 기능들이 BoardPanel의 인스턴스를 이용해서 생성되어야 한다는 전제조건이 있다.(각 기능들은 BoardPanel의 인스턴스를 사용한 작업을 수행하기 때문)
	 * 따라서 이 선행조건을 수행하는 bind(boardPanel) 메서드의 호출이 BoardPanel의 생성자에서 선행되어야 한다.
	 */
	private static enum FunctionType {
		MOVE(0), PAWN_ATTACK(1), CASTLING(2), ENPASSANT(3), MATE(4);

		private static List<Function> functionList;
		private final int INDEX;

		private FunctionType(int index) {
			INDEX = index;
		}
		
		private static List<Function> createFunctionList(BoardPanel boardPanel) {
			List<Function> functionList = new ArrayList<Function>();
			functionList.add(new Move(boardPanel));
			functionList.add(new PawnAttack(boardPanel));
			functionList.add(new Castling(boardPanel));
			functionList.add(new EnPassant(boardPanel));
			functionList.add(new Mate(boardPanel));
			return functionList;
		}

		public static void bind(BoardPanel boardPanel) {
			functionList = createFunctionList(boardPanel);
		}

		private static void validateIsBinded() {
			if (functionList == null) {
				try {
					throw new Exception("바인드를 먼저 한 후에 사용하세요");
				} catch (Exception e) {
				}
			}
		}

		public Function get() {
			validateIsBinded();
			return functionList.get(this.INDEX);
		}

		public static List<Function> getList() {
			validateIsBinded();
			return functionList;
		}
	}

	/*
	 * 다시 생각해보기
	 */
	private static class Function {
		private BoardPanel boardPanel;

		protected Function(BoardPanel boardPanel) {
			this.boardPanel = boardPanel;
		}

		protected BoardPanel getBoardPanel() {
			return boardPanel;
		}

		protected BoardSquare getBoardSquare(int x, int y) {
			return boardPanel.getBoardSquare(x, y);
		}
		
		protected BoardSquare getBoardSquare(Coordinate coordinate) {
			return boardPanel.getBoardSquare(coordinate);
		}

		protected void show(Chessman chessman, int x, int y) {}
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
	private static class Move extends Function {

		private Move(BoardPanel boardPanel) {
			super(boardPanel);
		}

		@Override
		protected void show(Chessman chessman, int x, int y) {
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

				if (getBoardSquare(x, y).isContainChessman()) {
					if (isEnemy(x, y)
							&& getBoardPanel().selectedSquare.getChessman().getChessmanType() != ChessmanType.PAWN) {
						getBoardSquare(x, y).setSquareAttackableColor();
					}
					break;
				} else {
					getBoardSquare(x, y).setSquareMoveableColor();
				}
			}
		}

		// 해당 좌표에 있는 말이 적군인지 검사하는 메서드
		private boolean isEnemy(int x, int y) {
			return getBoardPanel().selectedSquare.getChessman().isWhite() != getBoardPanel().getBoardSquare(x, y)
					.getChessman().isWhite();
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
	private static class PawnAttack extends Function {
		private PawnAttack(BoardPanel boardPanel) {
			super(boardPanel);
		}

		@Override
		protected void show(Chessman chessman, int x, int y) {
			if (chessman.getChessmanType() == ChessmanType.PAWN) {
				if (getBoardPanel().isWhite) {
					showAvailablePawnAttackableRoute(x, y - 1);

				} else {
					showAvailablePawnAttackableRoute(x, y + 1);
				}
			}
		}

		// 적인지 아닌지
		private boolean isEnemy(int x, int y) {
			return getBoardSquare(x, y).isContainChessman()
					&& (getBoardSquare(x, y).isContainChessmanWhite() != getBoardPanel().isWhite);
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
				getBoardSquare(x - 1, y).setSquareAttackableColor();
			}

			if (Coordinate.isValidate(x + 1, y) && isEnemy(x + 1, y)) {
				getBoardSquare(x + 1, y).setSquareAttackableColor();
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
	private static class Mate extends Function {
		private Mate(BoardPanel boardPanel) {
			super(boardPanel);
		}

		@Override
		protected void show(Chessman chessman, int x, int y) {
			showMateRoute();
		}

		private boolean isNotCurrentColorChessman(int x, int y) {
			return getBoardSquare(x, y).isContainChessman()
					&& getBoardSquare(x, y).isContainChessmanWhite() != getBoardPanel().isWhite;
		}

		private boolean isCurrentColorChessman(int x, int y) {
			return getBoardSquare(x, y).isContainChessman()
					&& getBoardSquare(x, y).isContainChessmanWhite() == getBoardPanel().isWhite;
		}

		private boolean isCurrentColorKing(int x, int y) {
			return isCurrentColorChessman(x, y) && getBoardSquare(x, y).getContainChessmanType() == ChessmanType.KING;
		}

		private boolean isNotCurrentColorKing(int x, int y) {
			return isNotCurrentColorChessman(x, y)
					&& getBoardSquare(x, y).getContainChessmanType() == ChessmanType.KING;
		}

		private void showMateRoute() {
			getBoardPanel().forEach((x, y) -> {
				if (isNotCurrentColorChessman(x, y)) {
					showMateRoute(getBoardSquare(x, y).getChessman(), x, y);
				}
			});
		}

		private void checkMateRoute() {
			getBoardPanel().forEach((x, y) -> {
				if (isCurrentColorChessman(x, y)) {
					checkMateRoute(getBoardSquare(x, y).getChessman(), x, y);
				}
			});

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
							getBoardSquare(mateCoordinate.getX(), mateCoordinate.getY()).setSquareCheckColor();
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
	private static class EnPassant extends Function {
		// 앙파상을 검사해야 되는경우는 폰의 초기 움직임이 2일때 뿐이기때문에 초기 움직임 Y를 변수로써 가지고있도록 했다.

		private EnPassant(BoardPanel boardPanel) {
			super(boardPanel);
		}

		@Override
		protected void show(Chessman chessman, int x, int y) {
			if (chessman.getChessmanType() == ChessmanType.PAWN) {
				showEnPassantSquare(x, y);
			}
		}

		private boolean isEnPassantMove(int x, int y) {
			int enPassantY;

			if (getBoardPanel().isWhite) {
				enPassantY = y + 1;
			} else {
				enPassantY = y - 1;
			}

			if (getBoardSquare(x, enPassantY).isContainChessman()) {
				return isBoardSquareContainsEnemyPawn(x, enPassantY);
			} else {
				return false;
			}

		}

		private void moveEnPassant(int x, int y) {
			if (getBoardPanel().isWhite) {
				getBoardSquare(x, y + 1).removeChessmanFromSquare();
			} else {
				getBoardSquare(x, y - 1).removeChessmanFromSquare();
			}
		}

		private void showEnPassantSquareByColor(int x, int y) {
			if (getBoardPanel().isWhite) {
				getBoardSquare(x, y - 1).setSquareAttackableColor();
			} else {
				getBoardSquare(x, y + 1).setSquareAttackableColor();
			}
		}

		private boolean isBoardSquareContainsEnemyPawn(int x, int y) {
			return getBoardSquare(x, y).getContainChessmanType() == ChessmanType.PAWN && getBoardPanel().selectedSquare
					.isContainChessmanWhite() != getBoardSquare(x, y).isContainChessmanWhite();
		}

		private boolean isEnemyPawnMovedTwoSquare(int x, int y) {
			return ((Pawn) getBoardSquare(x, y).getChessman()).getMovedSquareCount() == 2;
		}

		private boolean isAvailToEnPassent(int x, int y) {
			return isBoardSquareContainsEnemyPawn(x, y) && isEnemyPawnMovedTwoSquare(x, y);
		}

		private void showEnPassantSquare(int x, int y) {
			if (Coordinate.isValidate(x - 1, y) && getBoardSquare(x - 1, y).isContainChessman()
					&& isAvailToEnPassent(x - 1, y) && getBoardPanel().movedSquare == getBoardSquare(x - 1, y)) {
				showEnPassantSquareByColor(x - 1, y);
			}

			if (Coordinate.isValidate(x + 1, y) && getBoardSquare(x + 1, y).isContainChessman()
					&& isAvailToEnPassent(x + 1, y) && getBoardPanel().movedSquare == getBoardSquare(x + 1, y)) {
				showEnPassantSquareByColor(x + 1, y);
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
	private static class Castling extends Function {

		private Castling(BoardPanel boardPanel) {
			super(boardPanel);
		}

		@Override
		protected void show(Chessman chessman, int x, int y) {
			ChessmanType chessmanType = chessman.getChessmanType();

			if (chessmanType == ChessmanType.KING && !((King) chessman).isMoved()) {
				showCastlingSquare();
			}
		}

		// 캐슬링에 적합한 검증과정(1.킹과 룩 사이에 말들이 없고 2.룩이 움직인적이없어야 한다)을 거치고 검증과정에 맞으면 캐슬링 스퀘어에
		// 추가한다.
		private void showCastlingSquare() {
			if (getBoardPanel().isWhite) {

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
			return !getBoardSquare(x, y).isContainChessman() && !getBoardSquare(x + 1, y).isContainChessman();
		}

		// x, x + 1, x + 2 의 칸이 비었는지 확인한다
		private boolean isXAndUpperTwoSquareEmpty(int x, int y) {
			return isXAndUpperSquareEmpty(x, y) && !getBoardSquare(x + 2, y).isContainChessman();
		}

		// 칸이 룩을 가지고있는지 확인한다.
		private boolean isSquareContainRook(int x, int y) {
			return getBoardSquare(x, y).isContainChessman()
					&& getBoardSquare(x, y).getContainChessmanType() == ChessmanType.ROOK;
		}

		// 룩이 움직이지 않았는지 확인한다.
		private boolean isRookNotMoved(int x, int y) {
			return !((Rook) getBoardSquare(x, y).getChessman()).isMoved();
		}

		// 룩이 움직이지 않았다면 캐슬링 가능한 좌표에 추가한다.
		private void showCastlingSquareIfRookNotMoved(int x, int y) {
			if (isRookNotMoved(x, y)) {
				getBoardSquare(x, y).setSquareCastlingColor();
			}
		}

		// 움직임으로써 클릭한 sqaure의 좌표가 룩의 좌표일 때 캐슬링 움직임을 실행한다. 그 이외라면 단순 움직임을 실행한다.
		private void operateCastling(int x, int y) {
			if ((x == 7 && y == 7) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 0 && y == 0)) {
				operateCastlingMove(x, y);
			} else {
				getBoardSquare(x, y).setChessmanOnSquare(getBoardPanel().selectedSquare.getChessman());
				getBoardPanel().selectedSquare.removeChessmanFromSquare();
			}
		}

		// 캐슬링 움직임을 실행해주는 메서드로써 선택된칸(왕) 그리고 움직임으로써 클릭한 square의 좌표에 대해 캐슬링 움직임을 수행한다.
		private void operateCastlingMove(int x, int y) {
			((King) getBoardPanel().selectedSquare.getChessman()).setMoved();
			((Rook) getBoardSquare(x, y).getChessman()).setMoved();

			if (x == 0) {

				getBoardSquare(x + 2, y).setChessmanOnSquare(getBoardPanel().selectedSquare.getChessman());
				getBoardSquare(x + 3, y).setChessmanOnSquare(getBoardSquare(x, y).getChessman());
			} else if (x == 7) {

				getBoardSquare(x - 1, y).setChessmanOnSquare(getBoardPanel().selectedSquare.getChessman());
				getBoardSquare(x - 2, y).setChessmanOnSquare(getBoardSquare(x, y).getChessman());
			}

			getBoardPanel().selectedSquare.removeChessmanFromSquare();
			getBoardSquare(x, y).removeChessmanFromSquare();
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

		forEach(square -> {
			if (square.isContainChessman()) {
				Chessman chessman = square.getChessman();

				int result = chessmanCountMap.get(chessman.isWhite()).get(chessman.getChessmanType()) + 1;

				chessmanCountMap.get(chessman.isWhite()).put(chessman.getChessmanType(), result);

			}
		});

		return new Object[] { chessmanCountMap };
	}

	@Override
	public boolean isFinish() {
		return isFinish;
	}

}

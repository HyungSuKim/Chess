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
import com.bfpaul.renewal.chess.chessman.data.SpecialChessmanMovedIndicator;
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

@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel implements Layer {
	// BoardPanel의 Square에 접근하기 위해서는 x, y좌표가 필요하다.
	// 각 Square의 OnClickListener는 현재 클릭 된 Square의 x, y 좌표를 제공할 수 있다.
	// 하지만 과거 클릭 되었던 Square의 x, y좌표가 필요한 경우가 있다.
	// 따라서 BoardPanel에 과거 선택되었던 x, y좌표를 기억 할 수 있는 공간(변수)가 필요하다.
	// 이를 메서드 내에 지역변수를 통해서 혹은 메서드의 파라미터를 통해서 제공하는 것은
	// OnClickListener가 현재 x, y좌표를 제공하고 호출 될 때 마다 초기화되기 때문에 불가능하다.
	// 추가 : 위의 멤버변수의 개념에 추가해서 selectedSquare에서 selectedCoordinate로 변경하였다.
	// 위에서 언급했던것 처럼 과거 클릭되었던 Square의 x, y좌표가 필요한 경우가 있다. 따라서 이를 기억할 수 있는 공간이 필요하다.
	// 기존에는 BoardPanel 전역에서 과거에 선택되었던 Square만 제공하면 모든 로직을 처리 할 수 있었다.
	// 하지만 x, y 좌표의 데이터가 필요한 로직이 발생하였고 이 selectedSquare로는 x, y의 좌표를 제공 할 수 없었다.
	// 이 x, y가 필요 한 로직은 이전의 x, y좌표에서 현재 이동한 x, y좌표의 y차가 필요한 경우다.
	// 따라서 Coordinate를 통해 BoardSquare을 기억 할 수 있고 추가 논리를 처리 할 수 있다고 판단했기에 변경하기로했다.
	private Coordinate selectedCoordinate = Coordinate.createInValidateCoordinate();

	// 다시 생각해보기
	private BoardSquare movedSquare222 = null;
	// 이 isWhite는 사용자가 선택 할 수 있는 체스말의 색상을 나타내기 위한 목적으로 작성되었다.
	// isWhite는 턴이 종료되는 시점에 값이 변한다. 즉, 턴에 따라 사용자가 선택 할 수 있는 체스말을 제한하기 위해서 필요한 변수다.
	// 하지만 멤버변수 selectedSquare를 통해서 사용자가 선택한 체스말의 색상을 얻을 수 있다.
	// 따라서 selectedSquare의 체스말 색상 정보를 이용해서 턴에 따라 사용자가 선택 할 수 있는 제스말을 제한 할 수 있다.
	// 지금 isWhite는 초기의 목적과 다르게 사용 된 곳도 많다. (적군을 파악한다던가 아군을 파악한다던가)
	// 이는 멤버변수의 초기 목적과 다르게 사용된 것이다.
	//
	// 따라서 이 멤버변수는 의미가 없고 필요없다고 생각한다.
	private boolean controlColor = true;
	// 이 멤버변수는 BoardPanel에서 할 역할이 끝났음을 표현해주는 멤버변수이다.
	// BoardPanel, Timer, CurrentChessmanView는 정해진 순서로 실행되어야 하는 구조를 가지고있다.
	// BoardPanel의 수행 직후 BoardPanel의 정보를 얻어야 하는 CurrentChessmanView는
	// Layer를 통해 BoardPanel의 수행(1턴)이 완료되었음 isFinish() 메서드를 통해 이 멤버변수의 데이터를 확인함으로써
	// 수행된다.
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
	// 위의 목표를 위해 외부에서 BoardPanel의 생성자의 접근을 막고 메서드를 통해서 목표에 맞는 BoardPanel을 내부적으로
	// 생성하고자 했다.
	// static 사용 이유 : BoardPanel의 인스턴스가 생성되기 전에 BoardPanel의 메서드에 접근하는 것은 불가능하다. 위에서
	// 언급 한 것과 같이 생성자는
	// private 접근제어자를 통해 막혀있다. 따라서 static을 사용하여 인스턴스가 생성되지 않았음에도 BoardPanel의 메서드를 사용
	// 할 수 있게 하고자 하였다.
	public static BoardPanel createBoardPanelAtWhiteSideView() {
		final boolean WHITE = true;
		return new BoardPanel().placeWholeChessmanByColorSideView(WHITE);
	}

	public static BoardPanel createBoardPanelAtBalckSideView() {
		final boolean BLACK = false;
		return new BoardPanel().placeWholeChessmanByColorSideView(BLACK);
	}

	private BoardSquare getBoardSquareAt(Coordinate atCoordinate) {
		return getBoardSquareAt(atCoordinate.getX(), atCoordinate.getY());
	}

	private BoardSquare getBoardSquareAt(int atX, int atY) {
		return (BoardSquare) getComponent(atX + (atY * 8));
	}
	
	private Chessman getChessmanAtSquare(Coordinate atCoordinate) {
		if(atCoordinate.isInValidate()) {
			return null;
		}
		
		return getChessmanAtSquare(atCoordinate.getX(), atCoordinate.getY());
	}
	
	private Chessman getChessmanAtSquare(int atX, int atY) {
		if(isExistChessmanAtSquare(atX, atY)) {
			return getBoardSquareAt(atX, atY).getChessman();
		} else {
			return null;
		}
	}

	private void setSquaresOnBoard() {
		forEach((x, y) -> add(createBoardSquareAt(x, y), new LinearConstraints(1, LinearSpace.MATCH_PARENT)));
	}

	private BoardSquare createBoardSquareAt(int atX, int atY) {
		BoardSquare boardSquare = new BoardSquare(squareOriginalColorByCreateCoordinate(atX, atY));
		boardSquare.setOnClickListener(getSquareSelectedListener(atX, atY));
		return boardSquare;
	}

	/**
	 * 체스판의 칸은 교차로 밝은칸 어두운칸을 갖고 생성된다. 이 특성을 분석한 결과 생성 될 x, y좌표의 값의 합이 짝수인지 홀수인지에 따라
	 * 밝은색상 또는 어두운 색상을 갖는 특징이 있다.
	 * 
	 * @param atX
	 *            : 칸이 생성 될 x 좌표
	 * @param atY
	 *            : 칸이 생성 될 y 좌표
	 * @return 짝수일 경우 밝은색상을 홀수일 경우 어두운색상을 반환한다.
	 */
	private Color squareOriginalColorByCreateCoordinate(int atX, int atY) {
		return (atX + atY) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR;
	}
	/**
	 *   BoardPanel은 구성요소인 Square들이 마우스 클릭을 통해 반응하고 표현하도록 설계되었다.
	 * 이 Square들의 상태로는 1. 제어 할 수 있는 체스말이 있는 Square 2. 비어있는 Square 3. 제어 할 수 없는 체스말 이 있는 Square가 있다.
	 * 이 메서드는 제어 할 수 있는 체스말이 있는 Square를 클릭하는 것을 '체스말의 선택'으로 판단한다.--> 이는 '선택 된 체스말의 이동' 전 무조건 선행되어야 하는 조건이다.
	 * -->그리고 비어있는 Square, 제어 할 수 없는 체스말이 있는 Square을 클릭하는 것은 '선택 된 체스말의 이동'으로 판단한다.
	 * 
	 *   개요에서 언급한 것을 살펴보면 Square의 상태는 3가지 지만 제어 할 수 있는 체스말이 있는 Square를 클릭하는 것(체스말의 선택)만 판단하면 
	 * 그 이외의 2가지 상태의 Square를 클릭하는 것은 선택된 체스말의 이동이라는 하나의 개념으로 판단 할 수 있음을 알 수 있다.
	 * 따라서 제어 할 수 있는 체스말이 있는 Square를 클릭한 것인지 판단하는 조건은 아래와 같이 두가지 조건이 있다.
	 * 
	 * -->
	 *   첫번째 클릭 한 칸에 체스말이 있는지 없는지 검증해야 한다.
	 * 두번째 첫번째 조건을 만족하면 체스말의 색이 제어 할 수 있는 색인지 검증해야 한다. 
	 * 
	 * -->
	 *  이 메서드는 결론적으로 클릭 할 수 있는 세가지 상태의 Square들을 '체스말의 선택' '선택 된 체스말의 이동' 두가지로 판단하여 BoardPanel을 제어한다. 
	 * 
	 * 
	 * @param atX : 사용자의 마우스클릭이 발생한 Square의 x좌표
	 * @param atY : 사용자의 마우스클릭이 발생한 Square의 y좌표
	 * @return : 사용자가 체스말을 컨트롤하기위한 로직이 작성되어있는 OnClickListener
	 */
	private OnClickListener userClickListenerToControlChessman(int atX, int atY) {

		return new OnClickListener() {
			@Override
			public void onClick(Component component) {
				clearRoute();
				
				if(isClickForSelectChessman(atX, atY)) {
					
					if(isAlreadySelectedChessman(atX, atY)) {
						selectedCoordinate = Coordinate.createInValidateCoordinate();
					} else {
						showChessmanRoute(atX, atY);
						selectedCoordinate = new Coordinate(atX, atY);
					}
					
				} else {
					moveSelectedCoordinateChessmanTo(atX, atY);
					afterChessmanMoved(atX, atY);
					selectedCoordinate = Coordinate.createInValidateCoordinate();
				}
			}
		};
	}
	
	private OnClickListener getSquareSelectedListener(int atX, int atY) {
		return new OnClickListener() {
			@Override
			public void onClick(Component component) {
				clearRoute();
				whenSelectedSquare(atX, atY);
			}
			
		};
	}
	
	
	
	
	private void whenSelectedSquare(int atX, int atY) {
		
		Chessman previousChessman = getChessmanAtSquare(selectedCoordinate);
		Chessman currentChessman = getChessmanAtSquare(atX, atY);
		
		// 이전의 선택 된 체스말이 없거나 이전에 선택 된 체스말과 현재 선택된 체스말이 같은색상이거나
		if(selectedCoordinate.isInValidate() || 
				(isExistChessmanAtSquare(atX, atY) && previousChessman.isWhite() == currentChessman.isWhite())) {
			selectedCoordinate = new Coordinate(atX, atY);
			
			if(previousChessman != currentChessman) {
				for(Function function : FunctionType.getList()) {
					function.show(atX, atY);
				}
			} else {
				selectedCoordinate = Coordinate.createInValidateCoordinate();
			}

		} else {
			// 시나리오
			// 0. else문 내부 -> 선택된 square로 움직였다.
			// 1. 선택된 체스말의 그림을 해당 x, y 좌표로 옮긴다.
			// 1. 움직인 체스말 중 움직였다 표현 해야 할 말들은 표현한다.
			// 1. 앙파상 움직임인지 검사한다. -> 앙파상 움직임이라면 앙파상 잡기를 수행한다.
			// 1. 프로모션 검사한다. -> 프로모션 조건을 충족하면 프로모션한다.
			// 2. 체크메이트 검사한다. -> 움직 였을 때 검사해야 하기때문에 else문 내부 위치
			//
			// 1. 의 시나리오들은 각각 별도의 시나리오 이며 순서에 구애받지 않는다.
			// 1의 시나리오 중 앙파상과 프로모션은 동시에 시행되지 않는다.
			// 1의 시나리오 중 앙파상과 프로모션은 움직인 말이 폰 일때만 해당되는 시나리오다.
			// 프로모션 움직인 좌표 atX, atY에 대해서 검사, 수행
			// 앙파상 움직인 좌표 atX, atY에 대해서 검사, atX, atY - 1에 대해서 수행
			// 체크메이트 움직임 직후 움직인 색상의 말들에 대해서 수행
			// 앙파상과 프로모션은 움직임에 대한 특별한 검사
			// 시나리오상 공통개념...?
			// 1. 폰 -> 앙파상 / 프로모션 ==> 움직인게 폰인지 검사한다. 폰이라면 앙파상 프로모션 검사한다.
			// 2. 앙파상 프로모션 -> 특수움직임 ==> 특수움직임인지 검사한다. 특수움직이라면 앙파상 프로모션 검사한다.
			// 2-1. 특수움직임인지 검사해서(앙파상인지 프로모션인지 둘중하나로 스위치해서) 특수움직임에 대한 행동을 한다.
			moveSelectedChessmanAt(atX, atY);
			
			SpecialMoveType specialMoveType = getTypeIfSpecialMove(atX, atY);
			
			switch(specialMoveType) {
			case ENPASSANT :
				EnPassant enPassantHelper = ((EnPassant) FunctionType.ENPASSANT.get());
				enPassantHelper.moveEnPassant(atX, atY);
				isFinish = true;
				((Mate) FunctionType.MATE.get()).checkMateRoute();
			break;
			
			case PROMOTION :
				new PawnPromotionSelectEventFrame(controlColor).setOnPromotionSelectedListener(chessmanType -> {
					setChessmanOnSquare(chessmanType.createChessman(controlColor), atX, atY);
					isFinish = true;
					((Mate) FunctionType.MATE.get()).checkMateRoute();
				});				
				break;
			
			default :
				isFinish = true;
				((Mate) FunctionType.MATE.get()).checkMateRoute();
				break;
			}
			// isFinish의 설정과 checkMateRoute() 메서드의 실행시점에 대한 고민...
			// 프로모션의 경우에만 프로모션이 완료 될 때까지 기다린 후 되어야한다...
			
			selectedCoordinate = Coordinate.createInValidateCoordinate();
		}
	}
	
	private SpecialMoveType getTypeIfSpecialMove(int atX, int atY) {
		EnPassant enPassantHelper = ((EnPassant) FunctionType.ENPASSANT.get());
		boolean isPromotion = isExistChessmanAtSquare(atX, atY)
				&& getBoardSquareAt(atX, atY).getChessmanType() == ChessmanType.PAWN && (atY == 0 || atY == 7);
		
		if (enPassantHelper.isEnPassantMove(atX, atY)) {
			return SpecialMoveType.ENPASSANT;
		}
		
		if (isPromotion) {
			return SpecialMoveType.PROMOTION;
		}
		
		return SpecialMoveType.NONE;
	}
	
	private enum SpecialMoveType {
		ENPASSANT,
		PROMOTION,
		NONE;
	}
	
	private void moveSelectedChessmanAt(int atX, int atY) {
		Chessman selectedChessman = getChessmanAtSquare(selectedCoordinate);
		getBoardSquareAt(atX, atY).setChessmanOnSquare(selectedChessman);
		getBoardSquareAt(selectedCoordinate).removeChessmanFromSquare();
		
		if(selectedChessman instanceof SpecialChessmanMovedIndicator) {
			int movedSquareCount = Math.abs(selectedCoordinate.getY() - atY);
			
			((SpecialChessmanMovedIndicator)selectedChessman).setMovedSquareCount(movedSquareCount);
		}
	}

	private boolean isClickForSelectChessman(int atX, int atY) {
		if(isExistChessmanAtSquare(atX, atY)) {
			return getChessmanAtSquare(atX, atY).isWhite() == controlColor;
		} else {
			return false;
		}
	}
	
	private boolean isAlreadySelectedChessman(int atX, int atY) {
		int controllingX = selectedCoordinate.getX();
		int controllingY = selectedCoordinate.getY();
		
		return (controllingX == atX) && (controllingY == atY);
	}
	
	private boolean isControllingChessmanExist() {
		return !selectedCoordinate.isInValidate();
	}
	
	
	private boolean isControlAvailColorChessman(int atX, int atY) {
		return getChessmanAtSquare(atX, atY).isWhite() == controlColor;
	}

	// getBoardSquare(x, y).getBackground() != Color.YELLOW; 한번에 두개의 관심사(목표)가 같이
	// 표현되어있어서 이상함
	private boolean isSameColorChessmanClickedAt(int atX, int atY) {
		int selectedX = selectedCoordinate.getX();
		int selectedY = selectedCoordinate.getY();
		return getChessmanAtSquare(selectedX, selectedY).isWhite() == getChessmanAtSquare(atX, atY).isWhite();

	}

	private boolean isCastlingChessmanClickedAt(int atX, int atY) {
		// 다른 방법 모색 필요
		return getBoardSquareAt(atX, atY).getBackground() == Color.YELLOW;
	}

	/**
	 * 
	 */
	private void showChessmanRoute(int atX, int atY) {

		selectedCoordinate = new Coordinate(atX, atY);

		for (Function route : FunctionType.getList()) {
			route.show(atX, atY);
		}
	}

	private boolean isSelectedCoordinate(int atX, int atY) {
		int selectedX = selectedCoordinate.getX();
		int selectedY = selectedCoordinate.getY();

		return selectedX == atX && selectedY == atY;
	}

	//
	private void moveSelectedCoordinateChessmanTo(int toX, int toY) {
		movedSquare222 = getBoardSquareAt(toX, toY); // 고민대상 // 지울 예정

		BoardSquare selectedSquare = getBoardSquareAt(selectedCoordinate);
		BoardSquare toSquare = getBoardSquareAt(toX, toY);
		toSquare.setChessmanOnSquare(selectedSquare.getChessman());

		selectedSquare.removeChessmanFromSquare();

		BoardSquare movedSquare = getBoardSquareAt(toX, toY);
		ChessmanType movedChessmanType = movedSquare.getChessmanType();

		if (movedChessmanType == ChessmanType.KING) {
			King king = ((King) (movedSquare.getChessman()));
//			king.setMoved();
		} else if (movedChessmanType == ChessmanType.ROOK) {
			Rook rook = ((Rook) (movedSquare.getChessman()));
			rook.setMoved();
		} else if (movedChessmanType == ChessmanType.PAWN) {
			Pawn pawn = ((Pawn) (movedSquare.getChessman()));
//			pawn.setMoved();
			pawn.setMovedSquareCount(Math.abs(selectedCoordinate.getY() - toY));

			EnPassant enPassantHelper = ((EnPassant) FunctionType.ENPASSANT.get());
			if (enPassantHelper.isEnPassantMove(toX, toY)) {
				enPassantHelper.moveEnPassant(toX, toY);
			}
		}
	}

	private void afterChessmanMoved(int x, int y) {
		((Mate) FunctionType.MATE.get()).checkMateRoute();

		disableSquareClickEvent();

		/* 애매한부분 */
		boolean isPromotion = isExistChessmanAtSquare(x, y)
				&& getBoardSquareAt(x, y).getChessmanType() == ChessmanType.PAWN && (y == 0 || y == 7);

		if (isPromotion) {
			new PawnPromotionSelectEventFrame(controlColor).setOnPromotionSelectedListener(chessmanType -> {
				isFinish = true;
				setChessmanOnSquare(chessmanType.createChessman(controlColor), x, y);
			});
		} else {
			isFinish = true;
		}
	}

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에
	// 올려준다.
	// 올려주는 역할을 BoardSquare class의 메서드를 이용해서 하는데 이를통해 원하는 로직을 따라서 체스말이 추가된다.
	private void setChessmanOnSquare(Chessman chessman, int x, int y) {
		getBoardSquareAt(x, y).setChessmanOnSquare(chessman);
	}

	// 모든 체스말을 보드위에 셋팅한다.
	private BoardPanel placeWholeChessmanByColorSideView(boolean isWhite) {

		ChessmanType[] wholeChessmanType = ChessmanType.values();

		for (ChessmanType chessmanType : wholeChessmanType) {
			switch (chessmanType) {
			case KING:
				placeSingleChessmanByColorSideView(isWhite, ChessmanType.KING);
				break;
			case QUEEN:
				placeSingleChessmanByColorSideView(isWhite, ChessmanType.QUEEN);
				break;
			case BISHOP:
				placePairChessmanByColorSideView(isWhite, ChessmanType.BISHOP);
				break;
			case KNIGHT:
				placePairChessmanByColorSideView(isWhite, ChessmanType.KNIGHT);
				break;
			case ROOK:
				placePairChessmanByColorSideView(isWhite, ChessmanType.ROOK);
				break;
			case PAWN:
				placePawnOnBoardByColorSideView(isWhite, ChessmanType.PAWN);
				break;
			default:
			}
		}

		return this;
	}

	private int whiteYCoordinateByColorSideView(boolean isWhite) {
		if (isWhite) {
			return 7;
		} else {
			return 0;
		}

	}

	private int blackYCoordinateByColorSideView(boolean isWhite) {
		if (isWhite) {
			return 0;
		} else {
			return 7;
		}
	}

	private int kingsXCoordinateByColorSide(boolean isWhite) {
		return isWhite ? 4 : 3;
	}

	private int queenXCoordinateByColorSide(boolean isWhite) {
		return isWhite ? 3 : 4;
	}

	// 초기 갯수가 1개인 말(킹, 퀸) 의 경우 enum의 ordinal이 0, 1인데 여기서 3을 더한 값이 실제 말이 놓일 x로써 의미를
	// 갖게 된다는 것을 알고
	// 이러한 규칙을 이용해서 초기 갯수가 1개인 말을 한꺼번에 처리해서 Square에 올려주게되었다.
	private void placeSingleChessmanByColorSideView(boolean isWhite, ChessmanType type) {

		int xCoordinate;

		if (type == ChessmanType.KING) {
			xCoordinate = kingsXCoordinateByColorSide(isWhite);
		} else {
			xCoordinate = queenXCoordinateByColorSide(isWhite);
		}

		setChessmanOnSquare(type.createChessman(true), xCoordinate, whiteYCoordinateByColorSideView(isWhite));
		setChessmanOnSquare(type.createChessman(false), xCoordinate, blackYCoordinateByColorSideView(isWhite));
	}

	// 초기 갯수가 2개인 말(비숍, 나이트, 룩) 의 경우 enum의 ordinal이 2, 3, 4인데 여기서 3을 더한 값과 7에서 이 값을
	// 뺀 값이
	// 실제 말이 놓일 x로써 의미를 갖게 된다는 것을 알고 이러한 규칙을 이용해서 초기 갯수가 2개인 말을 한꺼번에 처리해서 Square에
	// 올려주게되었다.
	private void placePairChessmanByColorSideView(boolean color, ChessmanType type) {
		int whiteY = whiteYCoordinateByColorSideView(color);
		int blackY = blackYCoordinateByColorSideView(color);

		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), whiteY);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), whiteY);
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), blackY);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), blackY);
	}

	// 폰들을 보드위 정해진(초기) 위치에 셋팅해주기위해서 작성하였다. 폰의 경우 초기 갯수만큼 반복해서 생성해서 Square위에 올려주는데
	// 흰색과 검정색이 대칭으로 갯수가 똑같음을 이용했다.
	private void placePawnOnBoardByColorSideView(boolean byColorSideView, ChessmanType type) {
		int initCount = type.getInitCount();
		int whiteY = whiteYCoordinateByColorSideView(byColorSideView);
		int blackY = blackYCoordinateByColorSideView(byColorSideView);

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
	// A는 자기자신의 인스턴스를 B의 b메서드에 전달하여 b는 A의 a메서드를 수행해주는데 A의 입장에서 봤을때는 b의 메서드를 call했는데
	// b의 메서드는 A자기자신의 a를 수행시켜주므로 back이다. 따라서 이러한 동작을 하는 것을 CallBack 구조라 한다.
	// 메서드를 수행하려면 결국은 인스턴스를 넘겨주어야 하는데 이를 인터페이스를 익명클래스로 구현함으로써 구현된 메서드를 CallBack 하는
	// 것이다.
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
		forEach((x, y) -> onSelectedSquareListener.onSelected(getBoardSquareAt(x, y)));
	}

	private void disableSquareClickEvent() {
		forEach(square -> square.setEnableClickEvent(false));
	}

	private void enableSquareClickEventByControlColor(boolean controlColor) {
		forEach((atX, atY) -> {
 			if (isExistChessmanAtSquare(atX, atY) && getChessmanAtSquare(atX, atY).isWhite() == controlColor) {
 				getBoardSquareAt(atX, atY).setEnableClickEvent(true);
 			} else {
 				getBoardSquareAt(atX, atY).setEnableClickEvent(false);
 			}
 		});
	}

	private void clearRoute() {
		forEach(square -> square.setSquareOriginalColor());
		BoardPanel.this.repaint();
	}
	
	private boolean isEmptySquareAt(int atX, int atY) {
		return getBoardSquareAt(atX, atY).getType() == BoardSquareType.EMPTY;
	}
	
	private boolean isExistChessmanAtSquare(int atX, int atY) {
		return !isEmptySquareAt(atX, atY);
	}
	
	/**
	 * ㅇㅇㅇㅇType(이름 아직 미정) 은 BoardPanel에 정의 되어있는 ㅇㅇㅇㅇ들을 사용자측에서 쉽게 알 수 있도록 도와주고 위 정보를
	 * 이용하여 이 ㅇㅇㅇㅇ들을 받아서 사용하기 위해서 작성된 열거형 클래스이다.
	 * 
	 * 따라서 ㅇㅇㅇㅇType은 사용자측에서 사용 할 수 있는 ㅇㅇㅇㅇ들을 List로 갖는다.
	 * 
	 * 이 열거형클래스의 List를 사용하기위해서는 선행 조건으로 BoardPanel의 인스턴스가 생성되고 각 기능들이 BoardPanel의
	 * 인스턴스를 이용해서 생성되어야 한다는 전제조건이 있다.(각 기능들은 BoardPanel의 인스턴스를 사용한 작업을 수행하기 때문) 따라서
	 * 이 선행조건을 수행하는 bind(boardPanel) 메서드의 호출이 BoardPanel의 생성자에서 선행되어야 한다.
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
			return boardPanel.getBoardSquareAt(x, y);
		}

		protected BoardSquare getBoardSquare(Coordinate coordinate) {
			return boardPanel.getBoardSquareAt(coordinate);
		}

		protected void show(int x, int y) {
		}
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
		protected void show(int x, int y) {
			BoardSquare boardSquare = getBoardPanel().getBoardSquareAt(x, y);
			boardSquare.setSquareMoveableColor();

			Chessman chessman = boardSquare.getChessman();

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

				if (getBoardPanel().isExistChessmanAtSquare(x, y)) {
					BoardPanel boardPanel = getBoardPanel();
					BoardSquare selectedSquare = boardPanel.getBoardSquareAt(boardPanel.selectedCoordinate);
					
					if (isEnemy(x, y)
							&& selectedSquare.getChessman().getChessmanType() != ChessmanType.PAWN) {
						getBoardSquare(x, y).setSquareAttackableColor();
						getBoardSquare(x, y).setEnableClickEvent(true);
					}
					break;
				} else {
					getBoardSquare(x, y).setSquareMoveableColor();
					getBoardSquare(x, y).setEnableClickEvent(true);
				}
			}
		}

		// 해당 좌표에 있는 말이 적군인지 검사하는 메서드
		private boolean isEnemy(int x, int y) {
			BoardPanel boardPanel = getBoardPanel();
			BoardSquare selectedSquare = boardPanel.getBoardSquareAt(boardPanel.selectedCoordinate);

			return selectedSquare.getChessman().isWhite() != boardPanel.getBoardSquareAt(x, y).getChessman().isWhite();
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
		protected void show(int x, int y) {
			BoardSquare boardSquare = getBoardPanel().getBoardSquareAt(x, y);
			Chessman chessman = boardSquare.getChessman();

			if (chessman.getChessmanType() == ChessmanType.PAWN) {
				if (chessman.isWhite()) {
					showAvailablePawnAttackableRoute(x, y - 1);

				} else {
					showAvailablePawnAttackableRoute(x, y + 1);
				}
			}
		}

		// 적인지 아닌지
		private boolean isEnemy(int x, int y) {
			return getBoardPanel().isExistChessmanAtSquare(x, y)
					&& getBoardPanel().getChessmanAtSquare(x, y).isWhite() != getBoardPanel().controlColor;
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
				getBoardSquare(x - 1, y).setEnableClickEvent(true);
			}

			if (Coordinate.isValidate(x + 1, y) && isEnemy(x + 1, y)) {
				getBoardSquare(x + 1, y).setSquareAttackableColor();
				getBoardSquare(x + 1, y).setEnableClickEvent(true);
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
		protected void show(int x, int y) {
			showMateRoute();
		}

		private boolean isNotCurrentColorChessman(int x, int y) {
			return getBoardPanel().isExistChessmanAtSquare(x, y)
					&& getBoardPanel().getChessmanAtSquare(x, y).isWhite() != getBoardPanel().controlColor;
		}

		private boolean isCurrentColorChessman(int x, int y) {
			return getBoardPanel().isExistChessmanAtSquare(x, y)
					&& getBoardPanel().getChessmanAtSquare(x, y).isWhite() == getBoardPanel().controlColor;
		}

		private boolean isCurrentColorKing(int x, int y) {
			return isCurrentColorChessman(x, y) && getBoardSquare(x, y).getChessmanType() == ChessmanType.KING;
		}

		private boolean isNotCurrentColorKing(int x, int y) {
			return isNotCurrentColorChessman(x, y)
					&& getBoardSquare(x, y).getChessmanType() == ChessmanType.KING;
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
		protected void show(int x, int y) {
			Chessman chessman = getBoardPanel().getChessmanAtSquare(x, y);

			if (chessman.getChessmanType() == ChessmanType.PAWN) {
				showEnPassantSquare(x, y);
			}
		}

		private boolean isEnPassantMove(int x, int y) {
			int enPassantY;

			if (getBoardPanel().controlColor) {
				enPassantY = y + 1;
			} else {
				enPassantY = y - 1;
			}

			if (getBoardPanel().isExistChessmanAtSquare(x, enPassantY)) {
				return isBoardSquareContainsEnemyPawn(x, enPassantY);
			} else {
				return false;
			}

		}

		private void moveEnPassant(int x, int y) {
			if (getBoardPanel().controlColor) {
				getBoardSquare(x, y + 1).removeChessmanFromSquare();
			} else {
				getBoardSquare(x, y - 1).removeChessmanFromSquare();
			}
		}

		private void showEnPassantSquareByColor(int x, int y) {
			if (getBoardPanel().controlColor) {
				getBoardSquare(x, y - 1).setSquareAttackableColor();
				getBoardSquare(x, y - 1).setEnableClickEvent(true);
			} else {
				getBoardSquare(x, y + 1).setSquareAttackableColor();
				getBoardSquare(x, y + 1).setEnableClickEvent(true);
			}
		}

		private boolean isBoardSquareContainsEnemyPawn(int x, int y) {
			return getBoardSquare(x, y).getChessmanType() == ChessmanType.PAWN
					&& getBoardPanel().getBoardSquareAt(getBoardPanel().selectedCoordinate).getType()
							 != getBoardSquare(x, y).getType();
		}

		private boolean isEnemyPawnMovedTwoSquare(int x, int y) {
			return ((Pawn) getBoardSquare(x, y).getChessman()).getMovedSquareCount() == 2;
		}

		private boolean isAvailToEnPassent(int x, int y) {
			return isBoardSquareContainsEnemyPawn(x, y) && isEnemyPawnMovedTwoSquare(x, y);
		}

		private void showEnPassantSquare(int x, int y) {
			if (Coordinate.isValidate(x - 1, y) && getBoardPanel().isExistChessmanAtSquare(x - 1, y)
					&& isAvailToEnPassent(x - 1, y)) {
				showEnPassantSquareByColor(x - 1, y);
			}

			if (Coordinate.isValidate(x + 1, y) && getBoardPanel().isExistChessmanAtSquare(x + 1, y)
					&& isAvailToEnPassent(x + 1, y)) {
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
		protected void show(int x, int y) {
			if(!getBoardPanel().isExistChessmanAtSquare(x, y)) {
				return;
			}
			
			Chessman chessman = getBoardPanel().getBoardSquareAt(x, y).getChessman();
			ChessmanType chessmanType = chessman.getChessmanType();

			if (chessmanType == ChessmanType.KING && !((King) chessman).isMoved()) {
				showCastlingSquare();
			}
		}

		// 캐슬링에 적합한 검증과정(1.킹과 룩 사이에 말들이 없고 2.룩이 움직인적이없어야 한다)을 거치고 검증과정에 맞으면 캐슬링 스퀘어에
		// 추가한다.
		private void showCastlingSquare() {
			if (getBoardPanel().controlColor) {

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
			return getBoardPanel().isEmptySquareAt(x, y) && getBoardPanel().isEmptySquareAt(x + 1, y);
		}

		// x, x + 1, x + 2 의 칸이 비었는지 확인한다
		private boolean isXAndUpperTwoSquareEmpty(int x, int y) {
			return isXAndUpperSquareEmpty(x, y) && getBoardPanel().isEmptySquareAt(x + 2, y);
		}

		// 칸이 룩을 가지고있는지 확인한다.
		private boolean isSquareContainRook(int x, int y) {
			return getBoardPanel().isExistChessmanAtSquare(x, y)
					&& getBoardSquare(x, y).getChessmanType() == ChessmanType.ROOK;
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
			}
		}

		// 캐슬링 움직임을 실행해주는 메서드로써 선택된칸(왕) 그리고 움직임으로써 클릭한 square의 좌표에 대해 캐슬링 움직임을 수행한다.
		private void operateCastlingMove(int x, int y) {
			BoardSquare selectedSquare = getBoardPanel().getBoardSquareAt(getBoardPanel().selectedCoordinate);
			((King) selectedSquare.getChessman()).setMoved();
			((Rook) getBoardSquare(x, y).getChessman()).setMoved();

			if (x == 0) {

				getBoardSquare(x + 2, y).setChessmanOnSquare(selectedSquare.getChessman());
				getBoardSquare(x + 3, y).setChessmanOnSquare(getBoardSquare(x, y).getChessman());
			} else if (x == 7) {

				getBoardSquare(x - 1, y).setChessmanOnSquare(selectedSquare.getChessman());
				getBoardSquare(x - 2, y).setChessmanOnSquare(getBoardSquare(x, y).getChessman());
			}

			selectedSquare.removeChessmanFromSquare();
			getBoardSquare(x, y).removeChessmanFromSquare();
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void execute(Object[] object) {
		isFinish = false;
		controlColor = (Boolean) object[0];
		enableSquareClickEventByControlColor(controlColor);
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

		forEach((atX, atY) -> {
			if (isExistChessmanAtSquare(atX, atY)) {
				Chessman chessman = getChessmanAtSquare(atX, atY);

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

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

// �� BoardPanel�� ü�������� �ϳ��� ����ν� ������ �ϱ����ؼ� ���������.

// ü����(Board)�� �ð��� ������ҷδ� Chessman(ü����), Square(ĭ)���� �ִ�.
// Board�� �� ������ҵ��� �׻� ������ ������� �������� �ʴ´�.(���� �� �� ����.)(View�� �ƴ�����)
// Board�� �� ������Ҵ� ��Ȳ���� �����ִ� ������ �ٸ� �� �ۿ�����.(���õ� ü������ ���� ��ġ������ �ٸ�ü������ ��ġ������ ���)
// ������ ��Ȳ�� ���� ������ ����� �����ؼ� �ٸ��� ���� �� �� �ۿ� ����.
// ���� ���� ����� ���� �����ϰ� �����ִ� View�ν��� ������ �ϴ� �����̶�� ���ٴ�
// ��Ȳ�� ���� ������ �����ִ� ������� ���������� �۵��ؼ� �����ִ� ������ ��Ȳ���� �ٸ� Panel�ν� ������ �ϴ� ���� �����̰� �׷��� �� �� �ۿ� ����.

// BoardPanelŬ������ ü������ �Ӽ��� �������� ü������ ���� ����� ĭ�� ǥ���ϴ� 64���� BoardSquare���� ������ �ִ�.
// BoardPanel�� Ư�� ĭ(BoardSquare)�� ��ġ(��ǥ)�� ������ �ִ� ������(ü����)�� �̿��Ͽ�
// �ٸ� ĭ��� �������� ǥ���ؾ� �� ��Ȳ���� ����ϰ� �� ����� �ٸ� ĭ���� ǥ���ϵ��� ���� �� �� �ִ�.
// �ʱ� ü������ ������ �ִ� ���� ��ü�� �ϳ��� BoardPanel�ν� �ٶ󺻴�.(�����Ѵ�)

@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel implements Layer {
	// BoardPanel�� Square�� �����ϱ� ���ؼ��� x, y��ǥ�� �ʿ��ϴ�.
	// �� Square�� OnClickListener�� ���� Ŭ�� �� Square�� x, y ��ǥ�� ������ �� �ִ�.
	// ������ ���� Ŭ�� �Ǿ��� Square�� x, y��ǥ�� �ʿ��� ��찡 �ִ�.
	// ���� BoardPanel�� ���� ���õǾ��� x, y��ǥ�� ��� �� �� �ִ� ����(����)�� �ʿ��ϴ�.
	// �̸� �޼��� ���� ���������� ���ؼ� Ȥ�� �޼����� �Ķ���͸� ���ؼ� �����ϴ� ����
	// OnClickListener�� ���� x, y��ǥ�� �����ϰ� ȣ�� �� �� ���� �ʱ�ȭ�Ǳ� ������ �Ұ����ϴ�.
	// �߰� : ���� ��������� ���信 �߰��ؼ� selectedSquare���� selectedCoordinate�� �����Ͽ���.
	// ������ ����ߴ��� ó�� ���� Ŭ���Ǿ��� Square�� x, y��ǥ�� �ʿ��� ��찡 �ִ�. ���� �̸� ����� �� �ִ� ������ �ʿ��ϴ�.
	// �������� BoardPanel �������� ���ſ� ���õǾ��� Square�� �����ϸ� ��� ������ ó�� �� �� �־���.
	// ������ x, y ��ǥ�� �����Ͱ� �ʿ��� ������ �߻��Ͽ��� �� selectedSquare�δ� x, y�� ��ǥ�� ���� �� �� ������.
	// �� x, y�� �ʿ� �� ������ ������ x, y��ǥ���� ���� �̵��� x, y��ǥ�� y���� �ʿ��� ����.
	// ���� Coordinate�� ���� BoardSquare�� ��� �� �� �ְ� �߰� ���� ó�� �� �� �ִٰ� �Ǵ��߱⿡ �����ϱ���ߴ�.
	private Coordinate selectedCoordinate = Coordinate.createInValidateCoordinate();

	// �ٽ� �����غ���
	private BoardSquare movedSquare222 = null;
	// �� isWhite�� ����ڰ� ���� �� �� �ִ� ü������ ������ ��Ÿ���� ���� �������� �ۼ��Ǿ���.
	// isWhite�� ���� ����Ǵ� ������ ���� ���Ѵ�. ��, �Ͽ� ���� ����ڰ� ���� �� �� �ִ� ü������ �����ϱ� ���ؼ� �ʿ��� ������.
	// ������ ������� selectedSquare�� ���ؼ� ����ڰ� ������ ü������ ������ ���� �� �ִ�.
	// ���� selectedSquare�� ü���� ���� ������ �̿��ؼ� �Ͽ� ���� ����ڰ� ���� �� �� �ִ� �������� ���� �� �� �ִ�.
	// ���� isWhite�� �ʱ��� ������ �ٸ��� ��� �� ���� ����. (������ �ľ��Ѵٴ��� �Ʊ��� �ľ��Ѵٴ���)
	// �̴� ��������� �ʱ� ������ �ٸ��� ���� ���̴�.
	//
	// ���� �� ��������� �ǹ̰� ���� �ʿ���ٰ� �����Ѵ�.
	private boolean controlColor = true;
	// �� ��������� BoardPanel���� �� ������ �������� ǥ�����ִ� ��������̴�.
	// BoardPanel, Timer, CurrentChessmanView�� ������ ������ ����Ǿ�� �ϴ� ������ �������ִ�.
	// BoardPanel�� ���� ���� BoardPanel�� ������ ���� �ϴ� CurrentChessmanView��
	// Layer�� ���� BoardPanel�� ����(1��)�� �Ϸ�Ǿ��� isFinish() �޼��带 ���� �� ��������� �����͸� Ȯ�������ν�
	// ����ȴ�.
	// ���� �� ��������� �ʿ��ϴٰ� �����Ѵ�.
	private boolean isFinish;

	private BoardPanel() {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);

		setSquaresOnBoard();
		
		FunctionType.bind(this);
	}

	// BoardPanel���� ���� �� Board�� �ٶ󺼶� ������� �������� �ٶ���, �������� �������� �ٶ����� ������ �ʿ��ߴ�.
	// �������� �����ڿ� �Ķ���ͷν� ������ �޾Ƽ� ���� ������ ���� �Ͽ��µ� �����ڿ� �Ķ������ �����θ����δ� ��ǥ�� ǥ���ϴµ� �����Ͽ���.
	// ���� ���� ��ǥ�� ǥ���ϴ� �̸��� ���� �޼��带 ����� ��ǥ�� �����Ͽ���.
	// �� �޼��带 �̿��ؼ� BoardPanel�� ����� ü������ ���� �°�(�������� �������� �ٶ���) ��ġ�Ѵٴ� ���� �޼��� ���� ���� ǥ��
	// �� �� �ְ� �Ǿ���.
	// ���� ��ǥ�� ���� �ܺο��� BoardPanel�� �������� ������ ���� �޼��带 ���ؼ� ��ǥ�� �´� BoardPanel�� ����������
	// �����ϰ��� �ߴ�.
	// static ��� ���� : BoardPanel�� �ν��Ͻ��� �����Ǳ� ���� BoardPanel�� �޼��忡 �����ϴ� ���� �Ұ����ϴ�. ������
	// ��� �� �Ͱ� ���� �����ڴ�
	// private ���������ڸ� ���� �����ִ�. ���� static�� ����Ͽ� �ν��Ͻ��� �������� �ʾ������� BoardPanel�� �޼��带 ���
	// �� �� �ְ� �ϰ��� �Ͽ���.
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
	 * ü������ ĭ�� ������ ����ĭ ��ο�ĭ�� ���� �����ȴ�. �� Ư���� �м��� ��� ���� �� x, y��ǥ�� ���� ���� ¦������ Ȧ�������� ����
	 * �������� �Ǵ� ��ο� ������ ���� Ư¡�� �ִ�.
	 * 
	 * @param atX
	 *            : ĭ�� ���� �� x ��ǥ
	 * @param atY
	 *            : ĭ�� ���� �� y ��ǥ
	 * @return ¦���� ��� ���������� Ȧ���� ��� ��ο������ ��ȯ�Ѵ�.
	 */
	private Color squareOriginalColorByCreateCoordinate(int atX, int atY) {
		return (atX + atY) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR;
	}
	/**
	 *   BoardPanel�� ��������� Square���� ���콺 Ŭ���� ���� �����ϰ� ǥ���ϵ��� ����Ǿ���.
	 * �� Square���� ���·δ� 1. ���� �� �� �ִ� ü������ �ִ� Square 2. ����ִ� Square 3. ���� �� �� ���� ü���� �� �ִ� Square�� �ִ�.
	 * �� �޼���� ���� �� �� �ִ� ü������ �ִ� Square�� Ŭ���ϴ� ���� 'ü������ ����'���� �Ǵ��Ѵ�.--> �̴� '���� �� ü������ �̵�' �� ������ ����Ǿ�� �ϴ� �����̴�.
	 * -->�׸��� ����ִ� Square, ���� �� �� ���� ü������ �ִ� Square�� Ŭ���ϴ� ���� '���� �� ü������ �̵�'���� �Ǵ��Ѵ�.
	 * 
	 *   ���信�� ����� ���� ���캸�� Square�� ���´� 3���� ���� ���� �� �� �ִ� ü������ �ִ� Square�� Ŭ���ϴ� ��(ü������ ����)�� �Ǵ��ϸ� 
	 * �� �̿��� 2���� ������ Square�� Ŭ���ϴ� ���� ���õ� ü������ �̵��̶�� �ϳ��� �������� �Ǵ� �� �� ������ �� �� �ִ�.
	 * ���� ���� �� �� �ִ� ü������ �ִ� Square�� Ŭ���� ������ �Ǵ��ϴ� ������ �Ʒ��� ���� �ΰ��� ������ �ִ�.
	 * 
	 * -->
	 *   ù��° Ŭ�� �� ĭ�� ü������ �ִ��� ������ �����ؾ� �Ѵ�.
	 * �ι�° ù��° ������ �����ϸ� ü������ ���� ���� �� �� �ִ� ������ �����ؾ� �Ѵ�. 
	 * 
	 * -->
	 *  �� �޼���� ��������� Ŭ�� �� �� �ִ� ������ ������ Square���� 'ü������ ����' '���� �� ü������ �̵�' �ΰ����� �Ǵ��Ͽ� BoardPanel�� �����Ѵ�. 
	 * 
	 * 
	 * @param atX : ������� ���콺Ŭ���� �߻��� Square�� x��ǥ
	 * @param atY : ������� ���콺Ŭ���� �߻��� Square�� y��ǥ
	 * @return : ����ڰ� ü������ ��Ʈ���ϱ����� ������ �ۼ��Ǿ��ִ� OnClickListener
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
		
		// ������ ���� �� ü������ ���ų� ������ ���� �� ü������ ���� ���õ� ü������ ���������̰ų�
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
			// �ó�����
			// 0. else�� ���� -> ���õ� square�� ��������.
			// 1. ���õ� ü������ �׸��� �ش� x, y ��ǥ�� �ű��.
			// 1. ������ ü���� �� �������� ǥ�� �ؾ� �� ������ ǥ���Ѵ�.
			// 1. ���Ļ� ���������� �˻��Ѵ�. -> ���Ļ� �������̶�� ���Ļ� ��⸦ �����Ѵ�.
			// 1. ���θ�� �˻��Ѵ�. -> ���θ�� ������ �����ϸ� ���θ���Ѵ�.
			// 2. üũ����Ʈ �˻��Ѵ�. -> ���� ���� �� �˻��ؾ� �ϱ⶧���� else�� ���� ��ġ
			//
			// 1. �� �ó��������� ���� ������ �ó����� �̸� ������ ���ֹ��� �ʴ´�.
			// 1�� �ó����� �� ���Ļ�� ���θ���� ���ÿ� ������� �ʴ´�.
			// 1�� �ó����� �� ���Ļ�� ���θ���� ������ ���� �� �϶��� �ش�Ǵ� �ó�������.
			// ���θ�� ������ ��ǥ atX, atY�� ���ؼ� �˻�, ����
			// ���Ļ� ������ ��ǥ atX, atY�� ���ؼ� �˻�, atX, atY - 1�� ���ؼ� ����
			// üũ����Ʈ ������ ���� ������ ������ ���鿡 ���ؼ� ����
			// ���Ļ�� ���θ���� �����ӿ� ���� Ư���� �˻�
			// �ó������� ���밳��...?
			// 1. �� -> ���Ļ� / ���θ�� ==> �����ΰ� ������ �˻��Ѵ�. ���̶�� ���Ļ� ���θ�� �˻��Ѵ�.
			// 2. ���Ļ� ���θ�� -> Ư�������� ==> Ư������������ �˻��Ѵ�. Ư�������̶�� ���Ļ� ���θ�� �˻��Ѵ�.
			// 2-1. Ư������������ �˻��ؼ�(���Ļ����� ���θ������ �����ϳ��� ����ġ�ؼ�) Ư�������ӿ� ���� �ൿ�� �Ѵ�.
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
			// isFinish�� ������ checkMateRoute() �޼����� ��������� ���� ���...
			// ���θ���� ��쿡�� ���θ���� �Ϸ� �� ������ ��ٸ� �� �Ǿ���Ѵ�...
			
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

	// getBoardSquare(x, y).getBackground() != Color.YELLOW; �ѹ��� �ΰ��� ���ɻ�(��ǥ)�� ����
	// ǥ���Ǿ��־ �̻���
	private boolean isSameColorChessmanClickedAt(int atX, int atY) {
		int selectedX = selectedCoordinate.getX();
		int selectedY = selectedCoordinate.getY();
		return getChessmanAtSquare(selectedX, selectedY).isWhite() == getChessmanAtSquare(atX, atY).isWhite();

	}

	private boolean isCastlingChessmanClickedAt(int atX, int atY) {
		// �ٸ� ��� ��� �ʿ�
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
		movedSquare222 = getBoardSquareAt(toX, toY); // ��δ�� // ���� ����

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

		/* �ָ��Ѻκ� */
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

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square��
	// �÷��ش�.
	// �÷��ִ� ������ BoardSquare class�� �޼��带 �̿��ؼ� �ϴµ� �̸����� ���ϴ� ������ ���� ü������ �߰��ȴ�.
	private void setChessmanOnSquare(Chessman chessman, int x, int y) {
		getBoardSquareAt(x, y).setChessmanOnSquare(chessman);
	}

	// ��� ü������ �������� �����Ѵ�.
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

	// �ʱ� ������ 1���� ��(ŷ, ��) �� ��� enum�� ordinal�� 0, 1�ε� ���⼭ 3�� ���� ���� ���� ���� ���� x�ν� �ǹ̸�
	// ���� �ȴٴ� ���� �˰�
	// �̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 1���� ���� �Ѳ����� ó���ؼ� Square�� �÷��ְԵǾ���.
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

	// �ʱ� ������ 2���� ��(���, ����Ʈ, ��) �� ��� enum�� ordinal�� 2, 3, 4�ε� ���⼭ 3�� ���� ���� 7���� �� ����
	// �� ����
	// ���� ���� ���� x�ν� �ǹ̸� ���� �ȴٴ� ���� �˰� �̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 2���� ���� �Ѳ����� ó���ؼ� Square��
	// �÷��ְԵǾ���.
	private void placePairChessmanByColorSideView(boolean color, ChessmanType type) {
		int whiteY = whiteYCoordinateByColorSideView(color);
		int blackY = blackYCoordinateByColorSideView(color);

		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), whiteY);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), whiteY);
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), blackY);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), blackY);
	}

	// ������ ������ ������(�ʱ�) ��ġ�� �������ֱ����ؼ� �ۼ��Ͽ���. ���� ��� �ʱ� ������ŭ �ݺ��ؼ� �����ؼ� Square���� �÷��ִµ�
	// ����� �������� ��Ī���� ������ �Ȱ����� �̿��ߴ�.
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

	// �ݺ��� for�� �ߺ��Ǿ� ���̴� ��찡 ���� �ַ� for�� ���Ǵ� ������ BoardSquare�� ���� �����ϱ� ���ؼ�����.
	// ������ �� �� x, y�� ��ǥ���� �̿��ؼ� ������ �ൿ�� �ϴ� ����� ���� ���� ��쵵 �־���.
	// ���� x, y�� �̿��ϸ� BoardSquare�� ���� �� �ֱ� ������ x, y�� �⺻�� �Ǵ� �������� �����ؼ�
	// x, y�� �̿��� �� �ִ� forEach�� ���� ����� �� forEach�� �̿��ؼ� BoardSquare�� ���� �� �� �ִ� �޼��带
	// �ۼ��Ͽ���.
	// interface�� ����� callback ����� �̿��Ͽ���.
	// forEach �޼��带 ȣ���ϴ��ʿ��� ������ �������̽��� forEach�� �����ϸ�
	// forEach�� ������ �����ϴ� ���� ���޹��� �������̽�(ȣ�����)�� onSelected�޼��带 �����ؼ� callBack �ϰ� �ȴ�.
	// forEach�� �ۼ� ���� : ��ø�� for���� ����ϸ� ������ ��ǥ�� �������� ǥ���ϰ� �����ϴµ� ������� �ִ�.
	// (for���� ��� ���ư����� ���� �Ű��ߵǰ� ���̵� �þ�ϱ�)
	// ���� �� ��ø�� for���� ��� �������ִ� forEach�� ���� ������ ��ǥ�� �������� ǥ���ϰ� �����ϴµ� ������ �ְ��� �ߴ�.
	// CallBack ���� : A��� �ν��Ͻ��� a��� �޼���� B��� �ν��Ͻ��� b��� �޼��带 ���� ���� �� �� �ִٰ� �����Ѵ�.
	// A�� �ڱ��ڽ��� �ν��Ͻ��� B�� b�޼��忡 �����Ͽ� b�� A�� a�޼��带 �������ִµ� A�� ���忡�� �������� b�� �޼��带 call�ߴµ�
	// b�� �޼���� A�ڱ��ڽ��� a�� ��������ֹǷ� back�̴�. ���� �̷��� ������ �ϴ� ���� CallBack ������ �Ѵ�.
	// �޼��带 �����Ϸ��� �ᱹ�� �ν��Ͻ��� �Ѱ��־�� �ϴµ� �̸� �������̽��� �͸�Ŭ������ ���������ν� ������ �޼��带 CallBack �ϴ�
	// ���̴�.
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
	 * ��������Type(�̸� ���� ����) �� BoardPanel�� ���� �Ǿ��ִ� ������������ ����������� ���� �� �� �ֵ��� �����ְ� �� ������
	 * �̿��Ͽ� �� ������������ �޾Ƽ� ����ϱ� ���ؼ� �ۼ��� ������ Ŭ�����̴�.
	 * 
	 * ���� ��������Type�� ����������� ��� �� �� �ִ� ������������ List�� ���´�.
	 * 
	 * �� ������Ŭ������ List�� ����ϱ����ؼ��� ���� �������� BoardPanel�� �ν��Ͻ��� �����ǰ� �� ��ɵ��� BoardPanel��
	 * �ν��Ͻ��� �̿��ؼ� �����Ǿ�� �Ѵٴ� ���������� �ִ�.(�� ��ɵ��� BoardPanel�� �ν��Ͻ��� ����� �۾��� �����ϱ� ����) ����
	 * �� ���������� �����ϴ� bind(boardPanel) �޼����� ȣ���� BoardPanel�� �����ڿ��� ����Ǿ�� �Ѵ�.
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
					throw new Exception("���ε带 ���� �� �Ŀ� ����ϼ���");
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
	 * �ٽ� �����غ���
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
	 * MoveHelper�� MoveableRouteCalculator�� ���� ���� MoveableRouteList�� �̿��ؼ�
	 * BoardPanel �� Chessman�� ������(����/�̵�)�� �����ϱ����ؼ� ������� ����Ŭ�����̴�.
	 * 
	 * MoveHelper�� ü������ ���õǾ����� MoveableCalculator�� ���� ���� MoveableRouteList�� �޾� �м��Ͽ�
	 * �����ϼ� �ִ� ��θ� ǥ���Ѵ�. 1. ü������ ������ �� �ִ� ��λ� �������� ���� ������ �������� ���Ѵ�. (��, ����Ʈ ����) 2.
	 * ü������ ������ �� �ִ� ��λ� �ٸ����� ���� ������ ���� �� ���ִ�. (��, Pawn ����) 3. 1, 2���� ��츦 �����ϸ鼭 ��
	 * ������ ĭ���� �� ������ �� �ִ� ������� �ص� ���� �� ������.
	 * 
	 * @author ������
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

		// showMoveableRoute�κ��� �Ķ���ͷ� ���� MoveableRoute�� Coordinate���� �ϳ� �ϳ� ó���ϴ� �޼���ν�
		// 1. ��ǥ�� ���� ������ �ش� ��ǥ�� ������ �� �ִ� �������� ǥ�����ִ� �޼��带 ȣ���ؼ� ���� �� �� �ִ� ������� ǥ���ϰ�
		// 2. ��ǥ�� ���� �ְ� �� ��ǥ�� ���� �����̶�� ���� �� �� �ִ� �������� ǥ���ϴ� �޼��带 ȣ���Ͽ� ������ �� �ִ� ������� ǥ���Ѵ�.
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

		// �ش� ��ǥ�� �ִ� ���� �������� �˻��ϴ� �޼���
		private boolean isEnemy(int x, int y) {
			BoardPanel boardPanel = getBoardPanel();
			BoardSquare selectedSquare = boardPanel.getBoardSquareAt(boardPanel.selectedCoordinate);

			return selectedSquare.getChessman().isWhite() != boardPanel.getBoardSquareAt(x, y).getChessman().isWhite();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * PawnAttackHelper�� BoardPanel ������ Chessman Pawn�� ������ �����ϱ����ؼ� ������� ����Ŭ�����̴�. ��
	 * ����Ŭ������ ���� ���� ��ġ��ǥ�� ������ �̿��ؼ� ���ݰ����� ��ǥ�� �ٸ������� ��, ���� �ִ��� üũ�Ѵ� �׸��� üũ�����
	 * pawnAttackableRouteList�� �����Ͽ� ���� ���ݰ��ɰ��(0 ~ 2����)�� ������ �� �ִ�.
	 * 
	 * ������ MoveableRouteCalculator�� ���� ��οʹ� ������ ���� �����Ǿ��ִ�. ������ ���� ������ �ٸ� ������ �����Ӱ�
	 * ������ ��ΰ� �Ȱ����� ���� ��� �����̴� ��ο� ������ ��ΰ� ���� �ʱ� �����̴�.
	 * 
	 * @author ������
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

		// ������ �ƴ���
		private boolean isEnemy(int x, int y) {
			return getBoardPanel().isExistChessmanAtSquare(x, y)
					&& getBoardPanel().getChessmanAtSquare(x, y).isWhite() != getBoardPanel().controlColor;
		}

		/**
		 * ���� ���ݰ����� ��Ʈ�� ���� ������ ����Ʈ�� ��θ� �߰����ִ� �޼��� ���� ���� ������ ���⿡ ���� ��ǥ(x-1)�� ������ ��ǥ(x+1)��
		 * ���� �ִ��� �˻��ϰ� ���� �ִٸ� ���ݰ����� ��ην� List�� �߰��Ѵ�.
		 * 
		 * @param direction
		 *            : ���� �ִ��� Ȯ�� �ؾ� �� ����
		 * @param x
		 *            : ���� ���� x��ǥ ���� x - 1, x + 1�� �����ִ��� Ȯ���ϱ� ���� �Ķ����
		 * @param y
		 *            : �޼��� ȣ��ο��� �Էµ� ���⿡ ���� y��ǥ
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
	 * CheckmateHelper�� BoardPanel�� Chessman�� �̵��ϰ� ���� �Ѿ�� �� ���� ������ �ִ� ��θ� ���� ü������
	 * �ִ��� �˻��ϴ� ����� �����Ѵ�. ���� üũ����Ʈ ���۴� ���� �Ѿ���� ��������� �������� ��������� ü�������� �̵������� ��θ�
	 * ����ϰ� �� ����� ��� �� �ݴ������ ���� �ִٸ� �ش� ��θ� üũ����Ʈ��θ���Ʈ�� �߰�(����)�Ѵ�. ���� �Ѿ�� �ݴ������ ����
	 * Ŭ�������� ü������Ʈ��θ���Ʈ�� ����� ��θ� �����ֵ��� ����Ǿ���.
	 * 
	 * ������ MoveableRouteCalculator�� ���� ��ο��� üũ����Ʈ ��Ʈ�� �˻��ؼ� �����ϸ� PawnAttackHelper��
	 * �̿��ؼ� ������ ���� üũ����Ʈ �� ���ִ� ��쿡 ���ؼ��� ����Ѵ�.
	 *
	 * CheckmateHelper�� MoveHelper�� �ٸ��� ��θ� �����ִ� ���Ҹ� �ϴ� ���̰� ��ε� Ư�� ��θ� ǥ���ؾ� �Ǳ⿡
	 * �з��Ͽ���.
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
						System.out.println("ü ũ !!!!!!!");
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
	 * EnPassantHelper�� BoardPanel�� Pawn�� �̵������ ���Ļ� ��⸦ �����ϱ� ���Ͽ� �ʿ��� ����Ŭ�����̴�.
	 * EnPassantHelper�� ���� 2ĭ �����̴� ������ ����� �ǽ��ϴµ� �̴� ���Ļ� ��Ⱑ 2ĭ �̵��ϰ� �ٷ� �����ϸ� ��ȿ�ϴٴ�
	 * Ư���� Ȱ���ϱ� ���ؼ��̴�. �׸��� ���Ļ� ����� �ٸ� �������� 2ĭ �̵��� ���� ���� �ٸ����� �ִ��� ���ζ��� Ȯ���ϰ� ������ �����Ѵ�.
	 * 
	 * �̴� ������ MoveableRouteCalculator�� ��� �� �� ���� Ư�� ���̽��� �Ǵ��ؼ� ���� �и��Ͽ���.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private static class EnPassant extends Function {
		// ���Ļ��� �˻��ؾ� �Ǵ°��� ���� �ʱ� �������� 2�϶� ���̱⶧���� �ʱ� ������ Y�� �����ν� �������ֵ��� �ߴ�.

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
	 * CastlingHelper�� BoardPanel�� Chessman�� �̵������ ĳ������ �����ϱ� ���Ͽ� �ʿ��� ����Ŭ�����̴�.
	 * CastlingHelper�� King�� ���õǾ����� �� ���� �������� �־����� �˻��Ѵ�(�������� �־����� ĳ������ ���ϴϱ�) ��������
	 * �����ٸ� CastilngSquare�� ���ϱ����� ���� ����� ������ �� �� ĳ������ ������ �����ϴ� ���� �ִ��� �˻��Ѵ�. ������ �����ϴ�
	 * ���� �ִٸ� �� ���� BoardSquare�� ĳ���� ������ ��Ͽ� �߰��Ѵ�. �׸��� �߰��� ����Ʈ�� ���������ν� ĳ���� ������ ĭ��
	 * ǥ���Ѵ�. �̴� ������ MoveableRouteCalculator�� ��� �� �� ���� Ư�� ���̽��� �Ǵ��ؼ� ���� �и��Ͽ���.
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

		// ĳ������ ������ ��������(1.ŷ�� �� ���̿� ������ ���� 2.���� ���������̾���� �Ѵ�)�� ��ġ�� ���������� ������ ĳ���� �����
		// �߰��Ѵ�.
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

		// x, x + 1 �� ĭ�� ������� Ȯ���Ѵ�
		private boolean isXAndUpperSquareEmpty(int x, int y) {
			return getBoardPanel().isEmptySquareAt(x, y) && getBoardPanel().isEmptySquareAt(x + 1, y);
		}

		// x, x + 1, x + 2 �� ĭ�� ������� Ȯ���Ѵ�
		private boolean isXAndUpperTwoSquareEmpty(int x, int y) {
			return isXAndUpperSquareEmpty(x, y) && getBoardPanel().isEmptySquareAt(x + 2, y);
		}

		// ĭ�� ���� �������ִ��� Ȯ���Ѵ�.
		private boolean isSquareContainRook(int x, int y) {
			return getBoardPanel().isExistChessmanAtSquare(x, y)
					&& getBoardSquare(x, y).getChessmanType() == ChessmanType.ROOK;
		}

		// ���� �������� �ʾҴ��� Ȯ���Ѵ�.
		private boolean isRookNotMoved(int x, int y) {
			return !((Rook) getBoardSquare(x, y).getChessman()).isMoved();
		}

		// ���� �������� �ʾҴٸ� ĳ���� ������ ��ǥ�� �߰��Ѵ�.
		private void showCastlingSquareIfRookNotMoved(int x, int y) {
			if (isRookNotMoved(x, y)) {
				getBoardSquare(x, y).setSquareCastlingColor();
			}
		}

		// ���������ν� Ŭ���� sqaure�� ��ǥ�� ���� ��ǥ�� �� ĳ���� �������� �����Ѵ�. �� �̿ܶ�� �ܼ� �������� �����Ѵ�.
		private void operateCastling(int x, int y) {
			if ((x == 7 && y == 7) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 0 && y == 0)) {
				operateCastlingMove(x, y);
			}
		}

		// ĳ���� �������� �������ִ� �޼���ν� ���õ�ĭ(��) �׸��� ���������ν� Ŭ���� square�� ��ǥ�� ���� ĳ���� �������� �����Ѵ�.
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
	 * BoardPanel���� ���� �ű�� ���̻� �ƹ��͵� �� �� ���� ��, BoardPanel�� Datas�� ��ȯ�Ѵ�.
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

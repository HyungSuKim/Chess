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

// �� BoardPanel�� ü�������� �ϳ��� ����ν� ������ �ϱ����ؼ� ���������.
// �� Board�� View�� �ƴ� Panel�� ���� �� ���� Board�� ������ ������ ��ȭ�� ���� �䰡 �ٲ�� ���� �ƴ϶�
// �ܺ��� ����� �� Ŭ������� ���� �� �� �� �� ó�� �� ����� �̿��Ͽ� ���°� �ٲ�� ������ Panel�ν� �����Ͽ���. 
//
// �ϳ��� ����ν� 64ĭ�� boardSquare�� ������ ü������ ������ boardSquare���� ���踦 �ξ��ִ� ������ �Ѵ�.
// boardSquare�� ü������ ���ų� �����ϰų�, ü������ ���������� �̵���θ� ����ؼ� ��λ��� �ٸ� Square�鿡�� ǥ�ø� ���شٰų�
// � ��ġ�� boardSquare�� ���� �� �ִ� ü������ �ִ� ����.
//
// �̷��� boardSquare�� Chessman �׸��� MoveableRouteCalculator�� ���踦 �ξ��ְ� �� �����͵��� �̿��Ͽ� �����Ϳ� �ɸ´� ���� ǥ���ϱ����Ͽ� �ʿ��ϴ�.
//
// BoardPanel�� �ֿ� ����� �Ʒ��� ����.
// 1. ü������ ��� �̵���θ� �����ش�(���ݰ��ɰ�� ����) 2. �ش��η� ���� �̵���Ų��. 3. ���� �̵� �� ���� �̵��� üũ�� �������� �Ǵ��Ѵ�.
// BoardPanel�� ����Ŭ������ MoveHelper, CastlingHelper, PawnAttackHelper, EnPassantHelper, CheckmateHelper�� �������ִµ�
// �� Ŭ�������� ����Ŭ������ �з��� ������ BoardPanel�� ���ο��� ü������ �̵��ϴ� ���, Ư���� �̵��� ��츦 ����Ŭ������ ���� �����Ͽ�
// BoardPanel���� ü������ �̵��� ���� Ŭ�������� ����ϵ��� �ϱ������̴�.
// ����Ŭ������ ���������ν� �� ����Ŭ������ �ڱ� �ڽ��� ���Ҹ� �ϰ� �ǰ� ���� �������� �������� �ȴ�. 
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
	// ü�� ���� �ϳ��ϳ��� square�ν� ü������ �����شٴ��� ü������ �������شٰų� �̵����ɹ����� ǥ������ �ּҴ����� ĭ�̴�.
	private static BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private static BoardSquare selectedSquare = null;
	private static boolean isWhite = true;

	// ü������ ���������� ���� ������� �����̴� ���� �����ִ� ���� Ŭ������ν�
	// ���尡 ��������� ���� �������� �ൿ�� �� �� �̷��� �͵��� �ʿ��ϴٰ� �Ǵ��ؼ�
	// BoardPanel�� �����ڿ��� �� ����Ŭ������ �ν��Ͻ��� �����Ѵ�.
	private static MoveHelper moveHelper;
	private static CastlingHelper castlingHelper;
	private static PawnAttackHelper pawnAttackHelper;
	private static EnPassantHelper enPassantHelper;
	private static CheckmateHelper checkmateHelper;

	// 8 X 8�� square�� ���� ü������ ������ش�.
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

	// �Էµ� ���Կ� �θ��� ũ�⸸ŭ ������ �����ϴ� ���������� �����Ͽ� ��ȯ�Ѵ�.
	private LinearConstraints createMatchParentConstraints(int weight) { // ��
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// ������ ��ǥ�� �ϳ��� ĭ�� �����ؼ� �ϳ��� square�� �迭�ν� ����Ǿ� ��ǥ�� �ǹ̸� ������ �ؼ� ���� Ŭ������ ���� �����ִ�
	// �̺�Ʈ�� ó���Ҷ�
	// ��ǥ�� ���� �̿��ؼ� square�� �̺�Ʈ�� ó�� �� �� �ֵ��� �Ϸ����Ѵ�.
	private static BoardSquare createBoardSquare(int x, int y) { // ��
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
				// ó����������
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
		disableSquareClickEvent(); // �ʱ⿡ ���� ������� ��� ĭ�� Ŭ�� �̺�Ʈ�� Ǯ��

		selectedSquare = boardSquare[x][y]; // �� �������� ������ ������ ���õ� ����� �����ϰ�

		moveHelper.setMoveableRouteList(MoveableRouteCalculator.selectChessman(selectedSquare.getChessman(), x, y));
		// moveHelper �� moveableRouteList�� ���� MoveableRouteCalculator�� ���� ��η� ������ ���ְ� :
		// ��ΰ��

		selectedSquare.setSquareMoveableColor(); // �������� ĭ�� �����ϼ��ִ� �������� ǥ�����ְ�

		if (selectedSquare.getChessman() instanceof Pawn) { // ���õ� ���� ���̸�

			pawnAttackHelper.checkPawnAttackableSquare(x, y); // ���� ������ �� �ִ� ĭ�� �ִ��� Ȯ���ϰ� : ��ΰ��
			pawnAttackHelper.showPawnAttackableSquare(x, y); // ���� ������ �� �ִ� ��θ� �����ְ� : ��κ����ֱ�
			enPassantHelper.checkShowEnPassantSquare(x, y); // ���Ļ� �� ���ִ� ĭ�� �ִ��� üũ�ϰ� �����ϴٸ� ���Ļ� ��θ� �����ְ� : ��ΰ�� + �����ֱ�
		}

		moveHelper.showMoveableRoute(); // ������ ���� ������ �� �ִ� ��θ� �����ش� : ��κ����ֱ�
		checkmateHelper.showCheckmateRoute(); // üũ����Ʈ ������ ��ΰ� ������ üũ����Ʈ ������ ��θ� �����ش�. : �����ֱ�

		if (selectedSquare.getChessman() instanceof King) { // ���õ� ���� ���̸�
			castlingHelper.setShowCastlingSquare(); // ĳ������ �������� Ȯ���ϰ� ĳ���� ������ ĭ�� �����ش� : ��ΰ�� + �����ֱ�
		}
	}

	private static void deSelectChessman() {
		moveHelper.disableMoveableRoute(); // �����ϼ� �ִ� ��θ� ��Ȱ��ȭ�Ѵ� : �����ֱ�(���߱�)

		initSquareEvent(isWhite); // Ŭ���� �� �̿ܿ� Ŭ���� ��Ȱ�� �Ǿ��� �Ϳ��� ���� ������ ������ Ŭ���̺�Ʈ�� Ȱ��ȭ���ش�. : �����

		pawnAttackHelper.disablePawnAttackableSquare(); // ���� �����Ҽ��ִ� ��θ� ��Ȱ��ȭ�Ѵ� : �����ֱ�(���߱�)

		castlingHelper.initCastlingSquareList(); // ĳ���� �� �� �ִ� ��θ� ��Ȱ��ȭ �Ѵ� : �����ֱ�(���߱�)

		enPassantHelper.cancelEnPassant(); // ���Ļ� �� ���ִ� ��θ� ��Ȱ��ȭ �Ѵ� : �����ֱ�(���߱�)

		checkmateHelper.disableCheckmateRoute(); // üũ����Ʈ �� �� �ִ� ��θ� ��Ȱ��ȭ �Ѵ� : �����ֱ�(���߱�)
	}

	private static void moveSelectedChessman(int x, int y) {
		// �̵���η� �̵����� ��
		if (boardSquare[x][y].isContainChessman()) {
			// �� ������ �������� ���Ŀ� CurrentChessmanView�� ���� ��Ű������ �׽�Ʈ �ϴ� �κ�
			// ������ ���Ļ��� �ϴ°�쿡 ���� ���� ǥ�õ��� �ʴ� �ٴ� �Ͱ� ĳ������ �ϴ� ��� Rook�� �����ɷ� ǥ�õȴٴ� ���̴�.
			// �׷��� �������� ���������� �� ���� �����ٱ�... ������ �ϰ��ִµ� ���� �����̴�.
			// System.out.println(boardSquare[x][y].getChessman().getChessmanType().name());
		}

		// ĳ���� ���� & ���Ļ� ����
		if (selectedSquare.getChessman() instanceof King && castlingHelper.castlingSquareList.size() != 0) {
			// ���õ� ü������ ���̰� ĳ���� �� �� �ִٸ�
			castlingHelper.moveCastling(x, y); // ��?
			enPassantHelper.initEnPassantSquare(); // ���Ļ��� Ȱ��ȭ �Ǿ��ִ� ���·� ĳ������ �ϸ� ���Ļ��� ��ȿ
		} else {
			boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman()); // �� �̿��� �������� ����������
		}

		if (selectedSquare.getChessman() instanceof Pawn) { // ���õ� ���� ���̸�
			pawnAttackHelper.disablePawnAttackableSquare();
			enPassantHelper.checkEnPassant(x, y); // ��?
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

		isWhite = !isWhite; // �̵��� ���� �� ������ ���� �ݴ������ �����Ѵ�.
		initSquareEvent(isWhite);
	}

	private static void initSquareEvent(boolean isWhite) {
		selectedSquare.setSquareOriginalColor();
		enableSquareClickEvent(isWhite);
		selectedSquare = null;
	}

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square��
	// �÷��ش�.
	// �÷��ִ� ������ BoardSquare class�� �޼��带 �̿��ؼ� �ϴµ� �̸����� ���ϴ� ������ ���� ü������ �߰��ȴ�.
	private void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[x][y].setChessmanOnSquare(chessman);
	}

	// ��� ü������ �������� �����Ѵ�.
	private void setWholeChessmanOnBoard() {
		for (ChessmanType type : ChessmanType.values()) {
			setChessmanOnBoard(type);
		}
	}

	// ü������ Ÿ�Ժ��� ������ �ʱ���ġ�� �������ֱ����� �޼����ε� �� Ÿ�Կ� ���� ������ �ʱ���ġ�� �������ش�.
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

	// �ʱ� ������ 1���� ��(ŷ, ��) �� ��� enum�� ordinal�� 0, 1�ε� ���⼭ 3�� ���� ���� ���� ���� ���� x�ν� �ǹ̸�
	// ���� �ȴٴ� ���� �˰�
	// �̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 1���� ���� �Ѳ����� ó���ؼ� Square�� �÷��ְԵǾ���.
	private void setSingleChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 7);
	}

	// �ʱ� ������ 2���� ��(���, ����Ʈ, ��) �� ��� enum�� ordinal�� 2, 3, 4�ε� ���⼭ 3�� ���� ���� 7���� �� ����
	// �� ����
	// ���� ���� ���� x�ν� �ǹ̸� ���� �ȴٴ� ���� �˰� �̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 2���� ���� �Ѳ����� ó���ؼ� Square��
	// �÷��ְԵǾ���.
	private void setPairChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), 0);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), 7);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 7);
	}

	// ������ ������ ������(�ʱ�) ��ġ�� �������ֱ����ؼ� �ۼ��Ͽ���. ���� ��� �ʱ� ������ŭ �ݺ��ؼ� �����ؼ� Square���� �÷��ִµ�
	// ����� �������� ��Ī���� ������ �Ȱ����� �̿��ߴ�.
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

		// MoveableRouteCalculator�κ��� ���� �����ϼ� �ִ� ����� List�� MoveHelper��
		// moveableRouteList�� �����Ѵ�.
		private void setMoveableRouteList(ArrayList<MoveableRoute> moveableRouteList) {
			this.moveableRouteList = moveableRouteList;
		}

		// ������ �����ϼ��ִ� ��θ� �Ⱥ��̰�(������ �������� �ٲپ) �Ѵ�.
		private void disableMoveableRoute() {
			for (MoveableRoute moveableRoute : moveableRouteList) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					boardSquare[coordinate.getX()][coordinate.getY()].setSquareOriginalColor();
				}
			}
		}

		// MoveableRouteCalculator�� ���� ���� ��ε� ��
		// ���� �� �� �ִ� ��θ� ����ϰ� �����ֱ� ���� �޼����̴�.
		// Direction���� ������ MoveableRouteList�� ��Ҹ� MoveableRoute ������ �ɰ��� �����Ѵ�.
		private void showMoveableRoute() {
			for (MoveableRoute moveableRoute : moveableRouteList) {
				checkMoveableCoordinates(moveableRoute.getCoordinates());
			}
		}

		// showMoveableRoute�κ��� �Ķ���ͷ� ���� MoveableRoute�� Coordinate���� �ϳ� �ϳ� ó���ϴ� �޼���ν�
		// 1. ��ǥ�� ���� ������ �ش� ��ǥ�� ������ �� �ִ� �������� ǥ�����ִ� �޼��带 ȣ���ؼ� ���� �� �� �ִ� ������� ǥ���ϰ�
		// 2. ��ǥ�� ���� �ְ� �� ��ǥ�� ���� �����̶�� ���� �� �� �ִ� �������� ǥ���ϴ� �޼��带 ȣ���Ͽ� ������ �� �ִ� ������� ǥ���Ѵ�.
		private void checkMoveableCoordinates(Coordinate[] coordinates) {
			for (Coordinate coordinate : coordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();

				if (boardSquare[x][y].isContainChessman()) {
					// pawn ���� ���� �׽�Ʈ��//
					if (isEnemy(x, y) && !(selectedSquare.getChessman() instanceof Pawn)) {
						setAttackableSquare(x, y);
					}
					// pawn ���� ���� �׽�Ʈ��//
					break;
				} else {
					setMoveableSquare(x, y);
				}
			}
		}

		// �ش� ��ǥ�� �ִ� ���� �������� �˻��ϴ� �޼���
		private boolean isEnemy(int x, int y) {
			return selectedSquare.getChessman().isWhite() != boardSquare[x][y].getChessman().isWhite();
		}

		// �ش� ��ǥ�� ������ �� �ִ� ������ �����Ѵ�
		private void setAttackableSquare(int x, int y) {
			boardSquare[x][y].setSquareAttackableColor();
		}

		// �ش� ��ǥ�� ������ �� �ִ� ������ �����Ѵ�
		private void setMoveableSquare(int x, int y) {
			boardSquare[x][y].setSquareMoveableColor();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class PawnAttackHelper {
		private ArrayList<BoardSquare> pawnAttackableSquare = new ArrayList<>(); // ����Ŭ����

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
	 * CheckmateHelper�� BoardPanel�� Chessman�� �̵��ϰ� ���� �Ѿ�� �� ���� ������ �ִ� ��θ� ���� ü������ �ִ��� �˻��ϴ� ����� �����Ѵ�.
	 * ���� üũ����Ʈ ���۴� ���� �Ѿ���� (���/������)������ ü�������� ��θ� ����Ͽ� üũ����Ʈ �� �� �ִ� ��ȿ�� ��θ��� �����ߴٰ�
	 * ���� �Ѿ �ݴ��� ������ ���� Ŭ�������� ü������Ʈ �� �� �ִ� ��θ� �����ֵ��� ����Ǿ���. 
	 * 
	 * ������ MoveableRouteCalculator�� ���� ��ο��� üũ����Ʈ ��Ʈ�� �˻��ؼ� �����ϸ�
	 * PawnAttackHelper�� �̿��ؼ� ������ ���� üũ����Ʈ �� ���ִ� ��쿡 ���ؼ��� ����Ѵ�.
	 *
	 * CheckmateHelper�� MoveHelper�� �ٸ��� ��θ� �����ִ� ���Ҹ� �ϴ� ���̰� ��ε� Ư�� ��θ� ǥ���ؾ� �Ǳ⿡ �з��Ͽ���.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class CheckmateHelper {
		// üũ����Ʈ �� ���ִ� ��ε��� ����Ʈ
		private ArrayList<MoveableRoute> checkmateRouteList = new ArrayList<>();
		// �˻��ϴ� ü������ ��� ����Ʈ
		private ArrayList<MoveableRoute> moveableRouteList = new ArrayList<>();
		// ���� üũ����Ʈ�� �� ���ִٸ� üũ����Ʈ ���� ĭ�� ��� ����
		private BoardSquare pawnCheckmateSquare = boardSquare[0][0];

		// ���� üũ����Ʈ ���� ��θ� �����ִ� �޼���� ���� ������������ �ٸ� ���� ��������� �и��� ������
		// ���� ���� ��η� �̵��� �����ص� �̵��� �Ұ��ؾߵǰ� �ٸ����� ���� ��ο� ������ �� �ִ� ��ΰ� ��ĥ���
		// �ش��η� �̵��ؼ� ���� üũ����Ʈ ��θ� ���� �� �� �־�� �Ǳ⿡ �и��Ͽ���.
		private void showCheckmateRoute() {
			if (isPawnHasCheckmateSquare()) {
				if (selectedSquare.getChessman() instanceof King) { // ���õ� ���� ���϶�
					pawnCheckmateSquare.setSquareMoveableCheckmateColor();
				} else {
					pawnCheckmateSquare.setSquareCheckmateColor();
				}
			}

			for (MoveableRoute moveableRoute : checkmateRouteList) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					int x = coordinate.getX();
					int y = coordinate.getY();

					if (selectedSquare.getChessman() instanceof King) { // ���õ� ���� ���϶�
						showRouteWhenKingSelected(x, y);
					} else { // ���õ� ���� ���� �ƴҶ�
						showRouteWhenOthersSeleted(x, y);
					}

				}
			}
		}

		// ���� üũ����Ʈ square�� �������ִ��� �ƴ���
		private boolean isPawnHasCheckmateSquare() {
			return pawnCheckmateSquare != boardSquare[0][0];
		}

		// ���� ���������� üũ����Ʈ ��Ʈ�� �����ִ� ���ε� ���õ� ���� �ٽ� �������ֵ��� ���õ� ĭ�� Ŭ���� �����ϰ� ���ش�.
		private void showRouteWhenKingSelected(int x, int y) {
			boardSquare[x][y].setSquareCheckmateColor(); // üũ����Ʈ ��θ� üũ����Ʈ ������ �ٲٰ�
			selectedSquare.setSquareMoveableCheckmateColor(); // �������� ���� �����ϼ��ִ� üũ����Ʈ ������ �ٲ۴�.
		}

		private void showRouteWhenOthersSeleted(int x, int y) {
			// �ٸ� ���� ������ �� �ٸ����� �̵���ΰ� üũ����Ʈ ��ο� ���� ��
			if (boardSquare[x][y].getBackground().equals(Theme.LIGHT_BLUE_COLOR)) {
				boardSquare[x][y].setSquareMoveableCheckmateColor();
			} else {
				// �ٸ� ���� ������ �� �ٸ����� �̵���ΰ� üũ����Ʈ ��ο� �ٸ� ��
				boardSquare[x][y].setSquareCheckmateColor();
			}
		}

		// ������ üũ����Ʈ ��θ� �Ⱥ��̰�(�������) �ٲپ��ش�.
		private void disableCheckmateRoute() {
			for (MoveableRoute moveableRoute : checkmateRouteList)
				for (Coordinate coordinate : moveableRoute.getCoordinates())
					boardSquare[coordinate.getX()][coordinate.getY()].setSquareOriginalColor();

			pawnCheckmateSquare.setSquareOriginalColor();
		}

		// ���� ������ �ڿ� ü������ �߿� üũ����Ʈ�� ������ ���� �ִ��� �˻��ϴ� �޼���ν�
		// �˻� ��� ���� ���� üũ�� ������ ���� �ִٸ� �׸����� ��θ� �޾Ƽ� �����Ѵ�.
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

//		������ ���� ���� ������ ���ִ� ��θ� �޾ƿͼ� ���� ���� �� ���ִ� ����� ���� �ִ� ��θ� �̾Ƽ� pawnCheckmateSquare�� �������ش�.
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

		// ���õ� ĭ�� ü������ �������� �ƴ��� �˻��Ѵ�.
		private boolean isAlly(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() == isWhite;
		}

		// ������� ü�������� �˻��Ѵ�.
		private boolean isEnemy(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() != isWhite;
		}

		// ������ �����ϼ� �ִ� ��θ� ������ üũ����Ʈ �� ���ִ� ��ΰ� �ִ��� �˻��Ѵ�.
		private void checkEnemyCheckmateRoute() {
			for (MoveableRoute moveableRoute : moveableRouteList) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					if (isEnemyHasCheckmateRoute(moveableRoute, coordinate))
						break;
				}
			}
		}

		// ������� �� ���� �˻��Ѵ�.
		private boolean isEnemyKing(int x, int y) {
			return isEnemy(x, y) && boardSquare[x][y].getChessman() instanceof King;
		}

		// ������ üũ����Ʈ �� �� �ִ� ��ΰ� �ִ��� �˻��ؼ� �ִٸ� �� ��θ� checkmateRouteList�� �߰��Ѵ�.
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
	 * EnPassantHelper�� BoardPanel�� Pawn�� �̵������ ���Ļ� ��⸦ �����ϱ� ���Ͽ� �ʿ��� ����Ŭ�����̴�.
	 * EnPassantHelper�� ���� 2ĭ �����̴� ������ ����� �ǽ��ϴµ� �̴� ���Ļ� ��Ⱑ 2ĭ �̵��ϰ� �ٷ� �����ϸ� ��ȿ�ϴٴ�
	 * Ư���� Ȱ���ϱ� ���ؼ��̴�. �׸��� ���Ļ� ����� �ٸ� �������� 2ĭ �̵��� ���� ���� �ٸ����� �ִ��� ���ζ��� Ȯ���ϰ� ������ �����Ѵ�.
	 * 
	 * �̴� ������ MoveableRouteCalculator�� ��� �� �� ���� Ư�� ���̽��� �Ǵ��ؼ� ���� �и��Ͽ���.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class EnPassantHelper {
		// ���Ļ��� �� �� �ִ� ĭ�� �ѹ��� �ϳ��� ���� �� �ֱ� ������ ������ BoardSquare�ڷ����� �̿��Ͽ��� �ʱ� ���� null�� �ϴ´��
		// ������ [0][0]�ڷḦ Ȱ���ߴ�.
		private BoardSquare enPassantSquare = boardSquare[0][0];
		// ���Ļ��� �˻��ؾ� �Ǵ°��� ���� �ʱ� �������� 2�϶� ���̱⶧���� �ʱ� ������ Y�� �����ν� �������ֵ��� �ߴ�.
		private int initY = -1;
		// ���� ���Ļ� �� ���ִ� ��ǥ�� �ִ��� �˻��ϱ����� �����ν� ���Ļ��� �� ���ִ� ���� ���� initY�ν��� ������ �����ϸ鼭 �Ʒ��� ������
		// ������ ���̴�.
		// ��) ������� ��ġ �������� ���� �Ǵ� �����ʿ� �������� ��ġ���� �� �̴� �׷��ٸ�
		// ������� x�������� �������� x���� ���� ���밪 1, y�������� �������� y���� ���� 0�� ��ġ�� �ִٸ�
		// ���Ļ��� �� �� �ִ� ���̴�.
		private int enPassantX = -1;
		private int enPassantY = -1;

		// �ѹ� ����� ���Ļ�ĭ�� ���ʿ䰡 ���⶧���� ������ �ʱⰪ���� ���ص� ������ �ʱ�ȭ�Ѵ�.
		private void initEnPassantSquare() {
			enPassantSquare = boardSquare[0][0];
		}

		// �ʱⰪ���� ������ ���� ������ ����ִ� ���̴�.
		private boolean isEnPassantSquareNotEmpty() {
			return enPassantSquare != boardSquare[0][0];
		}

		// ���Ļ� ĭ�� ������� �ʰ� ���Ļ� ����� �������� �����̰����ϴ� ���彺��� ���ٸ� ���Ļ� �������̴�.
		private boolean isEnPassantMove(int x, int y) {
			return isEnPassantSquareNotEmpty() && boardSquare[x][y] == enPassantSquare;
		}

		// �ʱ� y���� ������ y�� ���� 2���� �˻��Ѵ� ( 2��� �ʱ������ 2�� �ߴٰ� ���������̴�. )
		private boolean isPawnMoveTwoSquares(int y) {
			return Math.abs(initY - y) == 2;
		}

		// ������ ����ߴ� ���Ļ��� ������ ��ġ�� �ִ��� Ȯ���ϴ� �޼���
		private boolean isAvailEnPassantCoordinate(int x, int y) {
			return (Math.abs(enPassantX - x) == 1) && (enPassantY - y == 0);
		}

		// ���� Ŭ�������� ȣ��Ǵ� �޼���ν� ���� y��ġ�� �����ϰ� ���Ļ� �� �� �ִ� ĭ�� �ִٸ� �����ش�.
		private void checkShowEnPassantSquare(int x, int y) {
			initY = y;
			if (isEnPassantSquareNotEmpty() && boardSquare[x][y].getChessman() instanceof Pawn) {
				if (isAvailEnPassantCoordinate(x, y)) {
					enPassantSquare.setSquareAttackableColor();
				}
			}
		}

		// ���Ļ��� ����Ѵ�.
		private void cancelEnPassant() {
			if (enPassantSquare != boardSquare[0][0]) {
				enPassantSquare.setSquareOriginalColor();
			}
		}

		// ������ ��ǥ�� ���Ļ����� ������ ��ǥ��� ���Ļ����� �ִ� ü������ ������� ���������� �Ǵ��ؼ� �� �ڿ� �ִ� ���� �����Ѵ�.
		private void moveEnPassant(int x, int y) {
			if (isEnPassantMove(x, y)) {
				if (enPassantSquare.getChessman().isWhite()) {
					boardSquare[x][y + 1].removeChessmanFromSquare();
				} else {
					boardSquare[x][y - 1].removeChessmanFromSquare();
				}
			}
		}

		// ���� ���������� ������ ��ǥ�� ���� ���Ļ� ������� Ȯ���Ѵ�. ���Ļ� ����̶�� ���Ļ� ����� �����ϰ� �޼��带 �����ϰ�
		// ���Ļ� �� ĭ���� �̵��ϴ� ��쿣 ���Ļ� ��⸦ �����Ѵ�.
		private void checkEnPassant(int x, int y) {
			((Pawn) selectedSquare.getChessman()).setIsMoved();
			// ���� 2ĭ �������ٸ� ���Ļ� ĭ�� �����ϰ� �޼��带 �����.
			if (isPawnMoveTwoSquares(y)) {
				setEnPassantSquare(selectedSquare.getChessman().isWhite(), x, y);
				return;
			}

			moveEnPassant(x, y);

			enPassantSquare.setSquareOriginalColor();
			initEnPassantSquare();
		}
		// ������ ��ǥ�� square�� �� ���� �������ִ��� Ȯ���Ѵ�.
		private boolean isContainEnemyPawn(boolean isWhite, int x, int y) {
			return Coordinate.isValidate(x, y) && isContainPawn(x, y)
					&& (boardSquare[x][y].getChessman().isWhite() != isWhite);
		}
		// ������ ��ǥ�� square�� ���� �������ִ��� Ȯ���Ѵ�.
		private boolean isContainPawn(int x, int y) {
			return (boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman() instanceof Pawn);
		}
		// ���� �¿쿡 ���� ���� �ִ��� Ȯ���ϰ� �ִٸ� ���Ļ� ����� �����Ѵ�.
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
	 * CastlingHelper�� BoardPanel�� Chessman�� �̵������ ĳ������ �����ϱ� ���Ͽ� �ʿ��� ����Ŭ�����̴�.
	 * CastlingHelper�� King�� ���õǾ����� �� ���� �������� �־����� �˻��Ѵ�(�������� �־����� ĳ������ ���ϴϱ�) ��������
	 * �����ٸ� CastilngSquare�� ���ϱ����� ���� ����� ������ �� �� ĳ������ ������ �����ϴ� ���� �ִ��� �˻��Ѵ�. ������ �����ϴ�
	 * ���� �ִٸ� �� ���� BoardSquare�� ĳ���� ������ ��Ͽ� �߰��Ѵ�. �׸��� �߰��� ����Ʈ�� ���������ν� ĳ���� ������ ĭ��
	 * ǥ���Ѵ�. �̴� ������ MoveableRouteCalculator�� ��� �� �� ���� Ư�� ���̽��� �Ǵ��ؼ� ���� �и��Ͽ���.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class CastlingHelper {
		/**
		 * castlingSquareList : ĳ���� ������ BoardSquare�� �����ϰ� �ִ� ����Ʈ, ���� ����Ʈ�� ������ ������ �� �� ����
		 * ���ÿ� ĳ���� �� �� �ִ� ����� ��쵵 ó���� �ֱ� �����̴�.
		 */
		private ArrayList<BoardSquare> castlingSquareList = new ArrayList<>(); // ����Ŭ����

		/**
		 * 1.setShowCastlingSquare()�� ȣ���ϴ� �κп��� selectedSquare�� King���� �̹� �����Ͽ��� ������ ����
		 * �������� �ʾҴ����� Ȯ���ϰ� 2.�������� �ʾҴٸ� ĳ������ ������ ��������(1.ŷ�� �� ���̿� ������ ���� 2.���� ���������̾����
		 * �Ѵ�)�� ��ġ�� ���������� ������ ĳ���� ����� �߰��Ѵ�. 3.�׸��� ĳ���� ������ ĭ���� �����ش�.
		 */
		private void setShowCastlingSquare() {
			if (!((King) selectedSquare.getChessman()).isMoved()) {

				validateAndSetCastlingSquareList();

				showCastlingSquareList();
			}
		}

		// ĳ������ ������ ��������(1.ŷ�� �� ���̿� ������ ���� 2.���� ���������̾���� �Ѵ�)�� ��ġ�� ���������� ������ ĳ���� �����
		// �߰��Ѵ�.
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

		// x, x + 1 �� ĭ�� ������� Ȯ���Ѵ�
		private boolean isXAndUpperSquareEmpty(int x, int y) {
			return !boardSquare[x][y].isContainChessman() && !boardSquare[x + 1][y].isContainChessman();
		}

		// x, x + 1, x + 2 �� ĭ�� ������� Ȯ���Ѵ�
		private boolean isXAndUpperTwoSquareEmpty(int x, int y) {
			return isXAndUpperSquareEmpty(x, y) && !boardSquare[x + 2][y].isContainChessman();
		}

		// ĭ�� ���� �������ִ��� Ȯ���Ѵ�.
		private boolean isSquareContainRook(int x, int y) {
			return boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman() instanceof Rook;
		}

		// ���� �������� �ʾҴ��� Ȯ���Ѵ�.
		private boolean isRookNotMoved(int x, int y) {
			return !((Rook) boardSquare[x][y].getChessman()).isMoved();
		}

		// ���� �������� �ʾҴٸ� ĳ���� ������ ��ǥ�� �߰��Ѵ�.
		private void addCastlingSquareIfRookNotMoved(int x, int y) {
			if (isRookNotMoved(x, y)) {
				castlingSquareList.add(boardSquare[x][y]);
			}
		}

		// ĳ���� ������ ĭ���� ����Ʈ�� ������������� ĭ���� ĳ���� ������ �������ش�
		private void showCastlingSquareList() {
			if (!castlingSquareList.isEmpty()) {
				for (BoardSquare castlingSquare : castlingSquareList) {
					castlingSquare.setSquareCastlingColor();
				}
			}
		}

		// ĳ���� ������ ĭ���� �ʱ�ȭ���ش�.(ĳ���� ���� ǥ�ð� �� �͵��� ������� ������ ����Ʈ�� ����)
		private void initCastlingSquareList() {
			if (!castlingSquareList.isEmpty()) {
				for (BoardSquare castlingSquare : castlingSquareList) {
					castlingSquare.setSquareOriginalColor();
				}
			}
			castlingSquareList.clear();
		}

		/**
		 * ŷ�� Ŭ���� ���¿��� ĳ���� ǥ�ð� �� ���� Ŭ�������� ����Ǵ� �޼��� ���������� ĳ������ �������ִ� �޼��带 ȣ���Ѵ�.
		 * 
		 * @param x
		 *            : ĳ���� ǥ�ð� �� ���� Ŭ�������� ���� x ��ǥ
		 * @param y
		 *            : ĳ���� ǥ�ð� �� ���� Ŭ�������� ���� y ��ǥ �� ��ǥ�� �޴� ������ ���� x��ǥ 0 �Ǵ� 7�� ��ġ�ϰ�������
		 *            �̵��ϴ� ĭ�� �ٸ��� �����̴�.
		 */
		private void moveCastling(int x, int y) {
			operateCastling(x, y);
			initCastlingSquareList();
		}

		// ���������ν� Ŭ���� sqaure�� ��ǥ�� ���� ��ǥ�� �� ĳ���� �������� �����Ѵ�. �� �̿ܶ�� �ܼ� �������� �����Ѵ�.
		private void operateCastling(int x, int y) {
			if ((x == 7 && y == 7) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 0 && y == 0)) {
				operateCastlingMove(x, y);
			} else {
				boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
			}
		}

		// ĳ���� �������� �������ִ� �޼���ν� ���õ�ĭ(��) �׸��� ���������ν� Ŭ���� square�� ��ǥ�� ���� ĳ���� �������� �����Ѵ�.
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

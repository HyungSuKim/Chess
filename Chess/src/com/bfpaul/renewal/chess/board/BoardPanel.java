package com.bfpaul.renewal.chess.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import com.bfpaul.renewal.chess.Images;
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
import com.mommoo.flat.image.FlatImagePanel;
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
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private BoardSquare selectedSquare = null;
	private boolean isWhite = true;

	// ü������ ���������� ���� ������� �����̴� ���� �����ִ� ���� Ŭ������ν�
	// ���尡 ��������� ���� �������� �ൿ�� �� �� �̷��� �͵��� �ʿ��ϴٰ� �Ǵ��ؼ�
	// BoardPanel�� �����ڿ��� �� ����Ŭ������ �ν��Ͻ��� �����Ѵ�.
	private MoveHelper moveHelper;
	private CastlingHelper castlingHelper;
	private PawnAttackHelper pawnAttackHelper;
	private EnPassantHelper enPassantHelper;
	private CheckmateHelper checkmateHelper;

	// 8 X 8�� square�� ���� ü������ ������ش�.
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

	// �Էµ� ���Կ� �θ��� ũ�⸸ŭ ������ �����ϴ� ���������� �����Ͽ� ��ȯ�Ѵ�.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// ������ ��ǥ�� �ϳ��� ĭ�� �����ؼ� �ϳ��� square�� �迭�ν� ����Ǿ� ��ǥ�� �ǹ̸� ������ �ؼ� ���� Ŭ������ ���� �����ִ�
	// �̺�Ʈ�� ó���Ҷ�
	// ��ǥ�� ���� �̿��ؼ� square�� �̺�Ʈ�� ó�� �� �� �ֵ��� �Ϸ����Ѵ�.
	private BoardSquare createBoardSquare(int x, int y) {
		boardSquare[x][y] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR);
		boardSquare[x][y].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// ó����������
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

		moveHelper.setMoveableRoute(MoveableRouteCalculator.selectChessman(selectedSquare.getChessman(), x, y));

		selectedSquare.setSquareMoveableColor();

		if (selectedSquare.getChessman() instanceof Pawn) {
			pawnAttackHelper.checkPawnAtackableSquare(x, y);
			pawnAttackHelper.showPawnAttackableSquare(x, y);
			enPassantHelper.checkShowEnPassantSquare(x, y);
		}

		// moveHelper.showMoveableRoute(selectedSquare.getChessman().isWhite());
		moveHelper.showMoveableRoute2();
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
		// �̵���η� �̵����� ��
		isWhite = !isWhite;
		if (boardSquare[x][y].isContainChessman()) {
			// �� ������ �������� ���Ŀ� CurrentChessmanView�� ���� ��Ű������ �׽�Ʈ �ϴ� �κ�
			// ������ ���Ļ��� �ϴ°�쿡 ���� ���� ǥ�õ��� �ʴ� �ٴ� �Ͱ� ĳ������ �ϴ� ��� Rook�� �����ɷ� ǥ�õȴٴ� ���̴�.
			// �׷��� �������� ���������� �� ���� �����ٱ�... ������ �ϰ��ִµ� ���� �����̴�.
			System.out.println(boardSquare[x][y].getChessman().getChessmanType().name());
		}

		// ĳ���� ���� & ���Ļ� ����
		if (selectedSquare.getChessman() instanceof King && castlingHelper.castlingSquare.size() != 0) {
			castlingHelper.moveCastling(x, y);
			enPassantHelper.initEnPassantSquare();
		} else {
			boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
		}

		if (boardSquare[x][y].getChessman() instanceof Pawn) {
			pawnAttackHelper.pawnAttack(x, y);
			enPassantHelper.moveEnPassant(x, y);
			pawnAttackHelper.checkPawnAtackableSquare(x, y);
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

		checkmateHelper.checkmateChecker(isWhite);

		moveHelper.disableMoveableRoute();

		initSquareEvent(isWhite);
	}

	private void initSquareEvent(boolean isWhite) {
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
//			setPawnOnBoard(ChessmanType.PAWN);
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
				if (boardSquare[x][y].isContainChessman()
						&& boardSquare[x][y].getChessman().isWhite() == isWhite) {
					boardSquare[x][y].setEnableClickEvent(true);
				} else {
					boardSquare[x][y].setEnableClickEvent(false);
				}
			}
		}
	}

	private class MoveHelper {
		private ArrayList<MoveableRoute> moveableRoute = new ArrayList<>();

		private void setMoveableRoute(ArrayList<MoveableRoute> moveableRoute) {
			this.moveableRoute = moveableRoute;
		}

		private void disableMoveableRoute() {
			for (MoveableRoute moveableRoute : moveableRoute) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					boardSquare[coordinate.getX()][coordinate.getY()].setSquareOriginalColor();
				}
			}
		}

		private void showMoveableRoute(boolean isWhite) {
			for (MoveableRoute moveableRoute : moveableRoute) {
				checkSquaresIsContain(isWhite, moveableRoute.getCoordinates());
			}
		}
		//////

		private void showMoveableRoute2() {
			for (MoveableRoute moveableRoute : moveableRoute) {
				splitMoveableCoordinate(moveableRoute.getCoordinates());
			}
		}

		private void splitMoveableCoordinate(Coordinate[] coordinates) {
			for (Coordinate coordinate : coordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();
				System.out.println("x = " + x + "// y = " + y);

				if (boardSquare[x][y].isContainChessman()) {
					if (isEnemy(x, y)) {
						setAttackableSquare(x, y);
					}
					break;
				} else {
					setMoveableSquare(x, y);
				}
			}
		}
		
		private boolean isEnemy(int x, int y) {
			return selectedSquare.getChessman().isWhite() != boardSquare[x][y].getChessman().isWhite();
		}

		private void setAttackableSquare(int x, int y) {
			boardSquare[x][y].setSquareAttackableColor();
		}

		private void setMoveableSquare(int x, int y) {
			boardSquare[x][y].setSquareMoveableColor();
		}

		//////
		// ������ ���� ������ �� �ִ� ��ǥ�� ĭ�� ���� �ִ��� ������ Ȯ���Ѵ�.
		// ���� ������ ���� �������� ������ ���� ���� �������� ������ �����Ѵ�.
		private void checkSquaresIsContain(boolean isWhite, Coordinate[] moaveableCoordinates) {
			for (Coordinate coordinate : moaveableCoordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();
				if (boardSquare[x][y].isContainChessman()) {
					if (!checkContainChessmanIsAlly(isWhite, x, y))
						break;
				} else {
					notContainSquareOperation(x, y);
				}
			}
		}

		// ü������ ���������� Ȯ���ϰ� �׿� �´� ������ �����Ѵ�
		private boolean checkContainChessmanIsAlly(boolean isWhite, int x, int y) {
			if (boardSquare[x][y].getChessman().isWhite() == isWhite) {
				return allyChessmanSquareOperation(x, y);
			} else {
				return notAllyChessmanSquareOperation(x, y);
			}
		}

		// Ȯ���ϴ� ĭ�� ü������ �������̶�� �����ϴ� ����
		private boolean allyChessmanSquareOperation(int x, int y) {
			if (selectedSquare.getChessman() instanceof Knight) {
				return true; // ����Ʈ��� �ش��ο��� break ���� �ʰ� ��� ����ؾߵ�
			} else {
				return false; // ����Ʈ�� �ƴ϶�� �ش��ο��� break�ؾ���
			}
		}

		// Ȯ���ϴ� ĭ�� ü������ �ٸ����̶�� �����ϴ� ����
		private boolean notAllyChessmanSquareOperation(int x, int y) {
			if (selectedSquare.getChessman() instanceof Knight) {
				boardSquare[x][y].setSquareAttackableColor();
				return true; // ����Ʈ��� �ش��ο��� break ���� �ʰ� ��� ���ݰ�θ� ����ؾߵ�
			} else if (selectedSquare.getChessman() instanceof Pawn) {
				return false; // �� �̶�� �ش��ο��� break�ؾ���
			} else {
				boardSquare[x][y].setSquareAttackableColor();
				return false; // ���� ����Ʈ�� �ƴ϶�� ���ݰ�θ� �����ϰ� �ش��ο��� break�ؾ���
			}
		}

		// Ȯ���ϴ� ĭ�� �ƹ��͵� ������ �����ϴ� ����
		private void notContainSquareOperation(int x, int y) {
			boardSquare[x][y].setSquareMoveableColor();
		}
	}

	private class PawnAttackHelper {
		private ArrayList<BoardSquare> pawnAtackableSquare = new ArrayList<>(); // ����Ŭ����

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
				boardSquare[x][y].setChessmanOnSquare(selectedSquare.getChessman());
			}
		}

		private void checkPawnAtackableSquare(int x, int y) {
			pawnAtackableSquare.clear();
			if (isWhite) {

				if (Coordinate.isValidate(x + 1, y + 1))
					if (boardSquare[x + 1][y + 1].isContainChessman()
							&& !boardSquare[x + 1][y + 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[x + 1][y + 1]);
					}

				if (Coordinate.isValidate(x - 1, y + 1))
					if (boardSquare[x - 1][y + 1].isContainChessman()
							&& !boardSquare[x - 1][y + 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[x - 1][y + 1]);
					}

			} else {
				if (Coordinate.isValidate(x - 1, y - 1))
					if (boardSquare[x - 1][y - 1].isContainChessman()
							&& boardSquare[x - 1][y - 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[x - 1][y - 1]);
					}

				if (Coordinate.isValidate(x + 1, y - 1))
					if (boardSquare[x + 1][y - 1].isContainChessman()
							&& boardSquare[x + 1][y - 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[x + 1][y - 1]);
					}
			}
		}
	}

	private class CheckmateHelper {
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
			if (selectedSquare.getChessman() instanceof King) { // ���� ���� ���϶�
				boardSquare[x][y].setSquareCheckmateColor(); // üũ����Ʈ ��θ� üũ����Ʈ ������ �ٲٰ�
				selectedSquare.setSquareMoveableCheckmateColor(); // �������� ���� �����ϼ��ִ� üũ����Ʈ ������ �ٲ۴�.

			} else if (boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman() instanceof King) {
				// �ٸ� ���� ������ ��
				boardSquare[x][y].setSquareCheckmateColor();

			} else {
				// �ٸ� ���� ������ �� �ٸ����� �̵���ΰ� üũ����Ʈ ��ο� ���� ��
				if (boardSquare[x][y].getBackground().equals(Theme.LIGHT_BLUE_COLOR)) {
					boardSquare[x][y].setSquareMoveableCheckmateColor();
				} else {
					// �ٸ� ���� ������ �� �ٸ����� �̵���ΰ� üũ����Ʈ ��ο� �ٸ� ��
					boardSquare[x][y].setSquareCheckmateColor();
				}

			}
		}

		private void disableCheckmateRoute() {
			for (MoveableRoute moveableRoute : checkmateRoute)
				for (Coordinate coordinate : moveableRoute.getCoordinates())
					boardSquare[coordinate.getX()][coordinate.getY()].setSquareOriginalColor();
		}

		private void checkmateChecker(boolean isWhite) {
			checkmateRoute.clear();

			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (boardSquare[x][y].isContainChessman() && boardSquare[x][y].getChessman().isWhite() != isWhite) {
						Chessman checkingChessman = boardSquare[x][y].getChessman();
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

			if (boardSquare[x][y].isContainChessman()
					&& boardSquare[x][y].getChessman().isWhite() != checkingChessman.isWhite()) {

				return isEffectiveRoute(checkingChessman, route, coordinate);

			} else {
				return true;
			}
		}

		private boolean isEffectiveRoute(Chessman checkingChessman, MoveableRoute route, Coordinate coordinate) {
			if (boardSquare[coordinate.getX()][coordinate.getY()].getChessman() instanceof King) {
				if (checkingChessman instanceof Knight) {
					Coordinate[] checkmateCoordinate = new Coordinate[1];
					checkmateCoordinate[0] = coordinate;
					checkmateRoute.add(new MoveableRoute(route.getDirection(), checkmateCoordinate));
					return true;

				} else {
					checkmateRoute.add(new MoveableRoute(route.getDirection(), route.getCoordinates()));
//					new BoardEventInfoView(Images.CHECK);
					return true;
				}

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
			if (boardSquare[x][y].getChessman() instanceof Pawn) {
				// ���࿡ ���� �̵��� ������ġ���� ���� 2�϶� �� ���Ļ� üũ�� �Ѵ�... �ϸ� �������Ű�����?
				((Pawn) boardSquare[x][y].getChessman()).setIsMoved();

				if (Math.abs(boardSquare[x][y].getY() - selectedSquare.getY()) == 190) {
					setEnPassantSquare(boardSquare[x][y].getChessman().isWhite(), x, y);
					return;

				} else if (enPassantSquare != boardSquare[0][0] && boardSquare[x][y] == enPassantSquare) {
					if (boardSquare[x][y].getChessman().isWhite()) {
						boardSquare[x][y - 1].removeChessmanFromSquare();
					} else {
						boardSquare[x][y + 1].removeChessmanFromSquare();
					}

				}

				enPassantSquare.setSquareOriginalColor();
				enPassantSquare = boardSquare[0][0];

			}
		}

		private void setEnPassantSquare(boolean isWhite, int x, int y) {
			if (isWhite) {
				// boardSquare[y][x-1] �Ǵ� boardSquare[y][x+1] �ִ°� �������̳�? �� �˻��ϰ�
				if (Coordinate.isValidate(x - 1, y))
					if (isSquareContainPawn(x - 1, y) && !boardSquare[x - 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y - 1];
					}
				// ������ boardSquare[y-1][x]�� ���Ļ� ����� ����
				if (Coordinate.isValidate(x + 1, y))
					if (isSquareContainPawn(x + 1, y) && !boardSquare[x + 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y - 1];
					}

			} else {
				// boardSquare[y][x-1] �Ǵ� boardSquare[y][x+1] �ִ°� ������̳�? �� �˻��ϰ�
				if (Coordinate.isValidate(x - 1, y))
					if (isSquareContainPawn(x - 1, y) && boardSquare[x - 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y + 1];
					}
				// ������ boardSquare[y+1][x]�� ���Ļ� ����� ����
				if (Coordinate.isValidate(x + 1, y))
					if (isSquareContainPawn(x + 1, y) && boardSquare[x + 1][y].getChessman().isWhite()) {
						enPassantSquare = boardSquare[x][y + 1];
					}
			}
		}

		private boolean isSquareContainPawn(int x, int y) {
			return (boardSquare[x][y].isContainChessman() && boardSquare[y][x].getChessman() instanceof Pawn);
		}
	}

	private class CastlingHelper {
		// ĳ���� ����
		private ArrayList<BoardSquare> castlingSquare = new ArrayList<>(); // ����Ŭ����

		private void checkShowCastlingSquare(int x, int y) {
			// ĳ���� ����
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
			// ĳ���� ����
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

		// ĳ���� ����
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

		// ĳ���� ����
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

		// ĳ���� ����
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

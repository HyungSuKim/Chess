package com.bfpaul.renewal.chess.board;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;

import com.bfpaul.renewal.chess.Images;
import com.bfpaul.renewal.chess.Theme;
import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.ChessmanType;
import com.bfpaul.renewal.chess.chessman.data.King;
import com.bfpaul.renewal.chess.chessman.data.Pawn;
import com.bfpaul.renewal.chess.chessman.data.Rook;
import com.bfpaul.renewal.chess.event.BoardEventInfoView;
import com.bfpaul.renewal.chess.event.GameResultEventManager;
import com.bfpaul.renewal.chess.event.PawnPromotionSelectEventFrame;
import com.bfpaul.renewal.chess.layer.Layer;
import com.bfpaul.renewal.chess.route.Coordinate;
import com.bfpaul.renewal.chess.route.Direction;
import com.bfpaul.renewal.chess.route.MoveableRoute;
import com.bfpaul.renewal.chess.route.MoveableRouteCalculator;
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

/* Ŭ���� �ۼ� �� ���˿��
 * 1. �ùٸ� Ŭ���� �̸��ΰ�? : 
 * 2. ��������� ���� ���� : 
 * 3. Ŭ������ ����(Data/Controller/View) : 
 * 4. Data ������ ��������� �и� : 
 * 5. ����������/������/�ڷ��� : 
 * 6. inner class / abstract / interface : 
 * 7. ���α׷��� ���� : 
 * 8. class���� �������� �� �ʿ����� : 
 * 9. ���� : 
 * 10.�ߺ� ���� ������ �ݺ����� �ʴ°� : 
 * */

@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel implements Layer {
	// ü�� ���� �ϳ��ϳ��� square�ν� ü������ �����شٴ��� ü������ �������شٰų� �̵����ɹ����� ǥ������ �ּҴ����� ĭ�̴�.
	private final BoardSquare[][] BOARD_SQUARE = new BoardSquare[8][8];
	private static BoardSquare selectedSquare = null;
	private static boolean isWhite = true;

	// ü������ ���������� ���� ������� �����̴� ���� �����ִ� ���� Ŭ������ν�
	// ���尡 ��������� ���� �������� �ൿ�� �� �� �̷��� �͵��� �ʿ��ϴٰ� �Ǵ��ؼ�
	// BoardPanel�� �����ڿ��� �� ����Ŭ������ �ν��Ͻ��� �����Ѵ�.
	private MoveHelper moveHelper;
	private CastlingHelper castlingHelper;
	private PawnAttackHelper pawnAttackHelper;
	private EnPassantHelper enPassantHelper;
	private MateHelper checkmateHelper;

	private boolean isFinish;

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

	private void createHelper() {
		moveHelper = new MoveHelper();
		castlingHelper = new CastlingHelper();
		pawnAttackHelper = new PawnAttackHelper();
		enPassantHelper = new EnPassantHelper();
		checkmateHelper = new MateHelper();
	}

	// �Էµ� ���Կ� �θ��� ũ�⸸ŭ ������ �����ϴ� ���������� �����Ͽ� ��ȯ�Ѵ�.
	private LinearConstraints createMatchParentConstraints(int weight) { // ��
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// ������ ��ǥ�� �ϳ��� ĭ�� �����ؼ� �ϳ��� square�� �迭�ν� ����Ǿ� ��ǥ�� �ǹ̸� ������ �ؼ� ���� Ŭ������ ���� �����ִ�
	// �̺�Ʈ�� ó���Ҷ�
	// ��ǥ�� ���� �̿��ؼ� square�� �̺�Ʈ�� ó�� �� �� �ֵ��� �Ϸ����Ѵ�.
	private void createBoardSquare(int x, int y) { // ��
		BOARD_SQUARE[x][y] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_LIGHT_SQUARE_COLOR : Theme.BOARD_DARK_SQUARE_COLOR);
		BOARD_SQUARE[x][y].setOnClickListener(getListenerToControlChessman(x, y));
	}

	private OnClickListener getListenerToControlChessman(int x, int y) {
		return new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// ó����������
				if (BOARD_SQUARE[x][y].isContainChessman() && selectedSquare == null) {
					selectChessman(x, y);
				} else if (selectedSquare == BOARD_SQUARE[x][y]) {
					deSelectChessman();
				} else {
					moveSelectedChessman(x, y);
				}
			}
		};
	}

	private void selectChessman(int x, int y) {
		disableSquareClickEvent(); // �ʱ⿡ ���� ������� ��� ĭ�� Ŭ�� �̺�Ʈ�� Ǯ��

		selectedSquare = BOARD_SQUARE[x][y]; // �� �������� ������ ������ ���õ� ����� �����ϰ�

		moveHelper.setMoveableRouteList(
				MoveableRouteCalculator.getChessmanMoveableRouteList(selectedSquare.getChessman(), x, y));
		// moveHelper �� moveableRouteList�� ���� MoveableRouteCalculator�� ���� ��η� ������ ���ְ� :
		// ��ΰ��

		selectedSquare.setSquareMoveableColor(); // �������� ĭ�� �����ϼ��ִ� �������� ǥ�����ְ�

		if (selectedSquare.getChessman() instanceof Pawn) { // ���õ� ���� ���̸�

			pawnAttackHelper.showPawnAttackableSquare(x, y); // ���� ������ �� �ִ� ��θ� �����ְ� : ��κ����ֱ�
			enPassantHelper.showEnPassantSquare(x, y); // ���Ļ� �� ���ִ� ĭ�� �ִ��� üũ�ϰ� �����ϴٸ� ���Ļ� ��θ� �����ְ� : ��ΰ�� + �����ֱ�
		}

		if (selectedSquare.getChessman() instanceof King) { // ���õ� ���� ���̸�
			castlingHelper.setShowCastlingSquare(); // ĳ������ �������� Ȯ���ϰ� ĳ���� ������ ĭ�� �����ش� : ��ΰ�� + �����ֱ�
		}

		moveHelper.showMoveableRoute(); // ������ ���� ������ �� �ִ� ��θ� �����ش� : ��κ����ֱ�
		checkmateHelper.showCheckRoute(); // üũ����Ʈ ������ ��ΰ� ������ üũ����Ʈ ������ ��θ� �����ش�. : �����ֱ�
	}

	private void deSelectChessman() {
		selectedSquare.setSquareOriginalColor();

		selectedSquare = null;
		
		enableSquareClickEvent();

		moveHelper.disableMoveableRoute(); // �����ϼ� �ִ� ��θ� ��Ȱ��ȭ�Ѵ� : �����ֱ�(���߱�)

		pawnAttackHelper.disablePawnAttackableSquare(); // ���� �����Ҽ��ִ� ��θ� ��Ȱ��ȭ�Ѵ� : �����ֱ�(���߱�)

		castlingHelper.initCastlingSquareList(); // ĳ���� �� �� �ִ� ��θ� ��Ȱ��ȭ �Ѵ� : �����ֱ�(���߱�)

		enPassantHelper.cancelEnPassant(); // ���Ļ� �� ���ִ� ��θ� ��Ȱ��ȭ �Ѵ� : �����ֱ�(���߱�)

		checkmateHelper.disableCheckRoute(); // üũ����Ʈ �� �� �ִ� ��θ� ��Ȱ��ȭ �Ѵ� : �����ֱ�(���߱�)

	}

	private void moveSelectedChessman(int x, int y) {

		// ĳ���� ���� & ���Ļ� ����
		if (selectedSquare.getChessman() instanceof King && castlingHelper.castlingSquareList.size() != 0) {
			// ���õ� ü������ ���̰� ĳ���� �� �� �ִٸ�
			castlingHelper.moveCastling(x, y); // ��?
			enPassantHelper.initEnPassantSquare(); // ���Ļ��� Ȱ��ȭ �Ǿ��ִ� ���·� ĳ������ �ϸ� ���Ļ��� ��ȿ
		} else {

			BOARD_SQUARE[x][y].setChessmanOnSquare(selectedSquare.getChessman()); // �� �̿��� �������� ����������
		}
		
		if (selectedSquare.getChessman() instanceof Pawn) { // ���õ� ���� ���̸�
			pawnAttackHelper.disablePawnAttackableSquare();
			enPassantHelper.checkEnPassant(x, y); // ��?
		}
		
		if (selectedSquare.getChessman() instanceof King) {
			((King) selectedSquare.getChessman()).setIsMoved();
		}
		
		if (selectedSquare.getChessman() instanceof Rook) {
			((Rook) selectedSquare.getChessman()).setIsMoved();
		}

		selectedSquare.removeChessmanFromSquare();

		checkmateHelper.disableCheckRoute();

		checkmateHelper.checkCurrentColorChessmanCheckRoute();

		moveHelper.disableMoveableRoute();
		
		selectedSquare.setSquareOriginalColor();
		selectedSquare = null;
		
		disableSquareClickEvent();

		/* Test */
		GameResultEventManager.fiftyCountManager(isWhite).setLocationRelativeTo(this);
		/* Test */
		if (BOARD_SQUARE[x][y].getChessman() instanceof Pawn && (y == 0 || y == 7)) {
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

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square��
	// �÷��ش�.
	// �÷��ִ� ������ BoardSquare class�� �޼��带 �̿��ؼ� �ϴµ� �̸����� ���ϴ� ������ ���� ü������ �߰��ȴ�.
	private void setChessmanOnSquare(Chessman chessman, int x, int y) {
		BOARD_SQUARE[x][y].setChessmanOnSquare(chessman);
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

		int xCoordinate;

		if (type.equals(ChessmanType.KING)) {
			xCoordinate = type.ordinal() + 4;
		} else {
			xCoordinate = type.ordinal() + 2;
		}

		setChessmanOnSquare(type.createChessman(false), xCoordinate, 0);
		setChessmanOnSquare(type.createChessman(true), xCoordinate, 7);
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
				BOARD_SQUARE[x][y].setEnableClickEvent(false);
			}
		}
	}

	private void enableSquareClickEvent() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].getChessman().isWhite() == isWhite) {
					BOARD_SQUARE[x][y].setEnableClickEvent(true);
				} else {
					BOARD_SQUARE[x][y].setEnableClickEvent(false);
				}
			}
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
					BOARD_SQUARE[coordinate.getX()][coordinate.getY()].setSquareOriginalColor();
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

				if (BOARD_SQUARE[x][y].isContainChessman()) {
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
			return selectedSquare.getChessman().isWhite() != BOARD_SQUARE[x][y].getChessman().isWhite();
		}

		// �ش� ��ǥ�� ������ �� �ִ� ������ �����Ѵ�
		private void setAttackableSquare(int x, int y) {
			BOARD_SQUARE[x][y].setSquareAttackableColor();
		}

		// �ش� ��ǥ�� ������ �� �ִ� ������ �����Ѵ�
		private void setMoveableSquare(int x, int y) {
			BOARD_SQUARE[x][y].setSquareMoveableColor();
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
	private class PawnAttackHelper {
		private ArrayList<MoveableRoute> pawnAttackableRouteList = new ArrayList<>();

		// ���� ���ݰ����� ĭ�� ���� �������� �ٲپ��ش�.
		private void disablePawnAttackableSquare() {
			for (MoveableRoute attackableRoute : pawnAttackableRouteList) {
				for (Coordinate attackableCoordinate : attackableRoute.getCoordinates())
					BOARD_SQUARE[attackableCoordinate.getX()][attackableCoordinate.getY()].setSquareOriginalColor();
			}
		}

		// ���� ���� ������ ĭ�� �ִ� �� Ȯ���ϰ� ������ ĭ�� �ִٸ� ���ݰ��� �� ĭ���ν� �����ش�.
		private void showPawnAttackableSquare(int x, int y) {
			checkPawnAttackableRoute(x, y);

			for (MoveableRoute atttackableRoute : pawnAttackableRouteList) {
				for (Coordinate attackableCoordinate : atttackableRoute.getCoordinates())
					BOARD_SQUARE[attackableCoordinate.getX()][attackableCoordinate.getY()].setSquareAttackableColor();
			}
		}

		// ������ �ƴ���
		private boolean isEnemy(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && (BOARD_SQUARE[x][y].getChessman().isWhite() != isWhite);
		}

		/**
		 * ���� ������ �� �ִ� ��ǥ�� �Ķ���ͷ� �Է¹޾� �Է¹��� ��ǥ�� ���� ũ�� 1�� �迭�� ��ȯ�Ѵ�.
		 * 
		 * @param x
		 *            : ���� ���� �� �� �ִ� x��ǥ
		 * @param y
		 *            : ���� ���� �� �� �ִ� y��ǥ
		 * @return : ���� ���� �� ���ִ� Coordinate(x, y)�� ũ�� 1�� �迭�� ��ȯ�Ѵ�.
		 */
		private Coordinate[] getPawnAttackableCoordinate(int x, int y) {
			Coordinate[] pawnAttackableCoordinate = new Coordinate[1];
			pawnAttackableCoordinate[0] = new Coordinate(x, y);
			return pawnAttackableCoordinate;
		}
		
		/**
		 * ���� ���ݰ����� ��Ʈ�� ���� ������ ����Ʈ�� ��θ� �߰����ִ� �޼���
		 * ���� ���� ������ ���⿡ ���� ��ǥ(x-1)�� ������ ��ǥ(x+1)�� ���� �ִ��� �˻��ϰ�
		 * ���� �ִٸ� ���ݰ����� ��ην� List�� �߰��Ѵ�. 
		 * @param direction : ���� �ִ��� Ȯ�� �ؾ� �� ����
		 * @param x : ���� ���� x��ǥ ���� x - 1, x + 1�� �����ִ��� Ȯ���ϱ� ���� �Ķ����
		 * @param y : �޼��� ȣ��ο��� �Էµ� ���⿡ ���� y��ǥ
		 */
		private void addAvailablePawnAttackableRoute(Direction direction, int x, int y) {
			if (Coordinate.isValidate(x - 1, y) && isEnemy(x - 1, y)) {

				pawnAttackableRouteList
						.add(new MoveableRoute(direction, getPawnAttackableCoordinate(x - 1, y)));
			}

			if (Coordinate.isValidate(x + 1, y) && isEnemy(x + 1, y)) {

				pawnAttackableRouteList
						.add(new MoveableRoute(direction, getPawnAttackableCoordinate(x + 1, y)));
			}
		}

		/**
		 * ���� ������ġ ��ǥ(x,y)�� isWhite ������ �̿��ؼ� ���� ���� ������ 2���� ���(��ǥ)�� ��ü������ �����ִ��� Ȯ���Ѵ� Ȯ��
		 * ��� �� ü������ �ִٸ� ���� ���ݰ����� ��θ� ����Ʈ�� �߰��Ѵ�.
		 * 
		 * @param x
		 *            : ���� ������ġ x��ǥ
		 * @param y
		 *            : ���� ������ġ y��ǥ
		 */
		private void checkPawnAttackableRoute(int x, int y) {
			pawnAttackableRouteList.clear();
			if (isWhite) {
				addAvailablePawnAttackableRoute(Direction.UP, x, y - 1);
				
			} else {
				addAvailablePawnAttackableRoute(Direction.DOWN, x, y + 1);
				
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
	private class MateHelper {
		// üũ����Ʈ �� ���ִ� ��ε��� ����Ʈ
		private ArrayList<MoveableRoute> checkRouteList = new ArrayList<>();
		private boolean eventInfoNotShowed = true;

		// ���� üũ����Ʈ ���� ��θ� �����ִ� �޼���� ���� ������������ �ٸ� ���� ��������� �и��� ������
		// ���� ���� ��η� �̵��� �����ص� �̵��� �Ұ��ؾߵǰ� �ٸ����� ���� ��ο� ������ �� �ִ� ��ΰ� ��ĥ���
		// �ش��η� �̵��ؼ� ���� üũ����Ʈ ��θ� ���� �� �� �־�� �Ǳ⿡ �и��Ͽ���.
		private void showCheckRoute() {
			for (MoveableRoute checkRoute : checkRouteList) {
				for (Coordinate checkCoordinate : checkRoute.getCoordinates()) {
					int x = checkCoordinate.getX();
					int y = checkCoordinate.getY();

					if (selectedSquare.getChessman() instanceof King) { // ���õ� ���� ���϶�
						showRouteWhenKingSelected(x, y);
					} else { // ���õ� ���� ���� �ƴҶ�
						showRouteWhenOthersSeleted(x, y);
					}

				}
			}
		}

		// ���� ���������� üũ����Ʈ ��Ʈ�� �����ִ� ���ε� ���õ� ���� �ٽ� �������ֵ��� ���õ� ĭ�� Ŭ���� �����ϰ� ���ش�.
		private void showRouteWhenKingSelected(int x, int y) {
			if (BOARD_SQUARE[x][y] == selectedSquare) {
				BOARD_SQUARE[x][y].setSquareMoveableCheckColor();
			} else {
				BOARD_SQUARE[x][y].setSquareCheckColor();
			}
		}

		private void showRouteWhenOthersSeleted(int x, int y) {
			// �ٸ� ���� ������ �� �ٸ����� �̵���ΰ� üũ����Ʈ ��ο� ���� ��
			if (BOARD_SQUARE[x][y].getBackground().equals(Theme.LIGHT_BLUE_COLOR)) {
				BOARD_SQUARE[x][y].setSquareMoveableCheckColor();
			} else {
				// �ٸ� ���� ������ �� �ٸ����� �̵���ΰ� üũ����Ʈ ��ο� �ٸ� ��
				BOARD_SQUARE[x][y].setSquareCheckColor();
			}
		}

		// ������ üũ����Ʈ ��θ� �Ⱥ��̰�(�������) �ٲپ��ش�.
		private void disableCheckRoute() {
			for (MoveableRoute checkRoute : checkRouteList)
				for (Coordinate checkCoordinate : checkRoute.getCoordinates())
					BOARD_SQUARE[checkCoordinate.getX()][checkCoordinate.getY()].setSquareOriginalColor();

		}

		// ���� ������ �ڿ� ü������ �߿� üũ����Ʈ�� ������ ���� �ִ��� �˻��ϴ� �޼���ν�
		// �˻� ��� ���� ���� üũ�� ������ ���� �ִٸ� �׸����� ��θ� �޾Ƽ� �����Ѵ�.
		private void checkCurrentColorChessmanCheckRoute() {
			eventInfoNotShowed = true;
			checkRouteList.clear();

			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					checkCurrentColorChessmanCheckRoute(x, y);

				} // end of for x
			} // end of for y
		}
		
		/**
		 * ���� ������ ü������ ü������Ʈ ��Ʈ�� �������ִ��� �˻��ϴ� �޼���ν�
		 * �˻��ϴ� x, y��ǥ�� ü������ �ְ� �� ü������ �������� ������ ü�����̸�
		 * �� ü������ üũ �� �� �ִ� ��θ� �������ִ��� �˻��Ѵ�. 
		 * @param x : �˻��ϴ� BOARD_SQUARE�� x��ǥ
		 * @param y : �˻��ϴ� BOARD_SQUARE�� y��ǥ
		 */
		private void checkCurrentColorChessmanCheckRoute(int x, int y) {
			if (isCurrentColorChessman(x, y)) {

				Chessman chessman = BOARD_SQUARE[x][y].getChessman();

				if (isPawn(chessman)) {
					checkPawnHasCheckRoute(x, y);

				} else {
					checkChessmanHasCheckRoute(chessman, x, y);
				}

			}
		}

		// ������ �ƴ���
		private boolean isPawn(Chessman chessman) {
			return chessman instanceof Pawn;
		}

		// PawnAttackHelper�� checkPawnAttackableSquare x, y�� ��ġ�ϰ��ִ� ���� ���� ������ ��θ� ���ϰ�
		// ���ݰ����� ��θ� ���� pawnAttackableRouteList�� �̿��ؼ� ���ݰ����� ��ο� �ݴ������ ŷ�� �ִ� �� �˻��Ѵ�.
		private void checkPawnHasCheckRoute(int x, int y) {
			pawnAttackHelper.checkPawnAttackableRoute(x, y);

			for (MoveableRoute pawnAttackableRoute : pawnAttackHelper.pawnAttackableRouteList) {
				for (Coordinate attackableCoordinate : pawnAttackableRoute.getCoordinates())
					isChessmanHasCheckRoute(pawnAttackableRoute, attackableCoordinate);
			}
		}

		// ���� ������ ü����, x, y�� �̿��Ͽ� ���� ���� �� ���ִ� ��� ����Ʈ�� �޴´�.
		// �� ��θ���Ʈ�� ��ο� ��ǥ�� �̿��ؼ� �ݴ������ ���� �ִ��� �˻��ϴ� isChssmanHasCheckmateRoute�� ȣ���ϴµ�
		// ���� �ݴ������ ���� ã���� true�� ��ȯ�ؼ� break�Ѵ�.
		private void checkChessmanHasCheckRoute(Chessman chessman, int x, int y) {
			ArrayList<MoveableRoute> moveableRouteList
			= MoveableRouteCalculator.getChessmanMoveableRouteList(chessman, x, y);

			for (MoveableRoute moveableRoute : moveableRouteList) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					if (isChessmanHasCheckRoute(moveableRoute, coordinate))
						break;
				}
			}
		}

		// ������� ü�������� �˻��Ѵ�
		private boolean isCurrentColorChessman(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].getChessman().isWhite() == isWhite;
		}

		// �ݴ���� ü�������� �˻��Ѵ�.
		private boolean isOppositeColorChessman(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].getChessman().isWhite() != isWhite;
		}

		// �ݴ���� �� ���� �˻��Ѵ�.
		private boolean isOppositeColorKing(int x, int y) {
			return isOppositeColorChessman(x, y) && BOARD_SQUARE[x][y].getChessman() instanceof King;
		}

		// ���� ���� ü������ �̵������� ��ο� ��ǥ�� �޾ƿͼ� �޾ƿ� ��ǥ�� �ݴ������ ���� ������ �̵������� ��θ� üũ����Ʈ ���� ��ην�
		// �߰��Ѵ�.
		private boolean isChessmanHasCheckRoute(MoveableRoute moveableRoute, Coordinate coordinate) {
			if (isOppositeColorKing(coordinate.getX(), coordinate.getY())) {
				checkRouteList.add(moveableRoute);

				if (eventInfoNotShowed) {
//					new BoardEventInfoView(Images.CHECK).setLocation((int) (BoardPanel.super.getWidth() / 3.3),
//							(int) (BoardPanel.super.getHeight() / 2));

					eventInfoNotShowed = false;
				}

				return true;
			} else if (isOppositeColorChessman(coordinate.getX(), coordinate.getY())) {

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
		private BoardSquare enPassantSquare = null;
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
			if (isEnPassantSquareNotEmpty()) {
				enPassantSquare.setSquareOriginalColor();
			}

			enPassantSquare = null;
		}

		// �ʱⰪ���� ������ ���� ������ ����ִ� ���̴�.
		private boolean isEnPassantSquareNotEmpty() {
			return enPassantSquare != null;
		}

		// ���Ļ� ĭ�� ������� �ʰ� ���Ļ� ����� �������� �����̰����ϴ� ���彺��� ���ٸ� ���Ļ� �������̴�.
		private boolean isEnPassantMove(int x, int y) {
			return isEnPassantSquareNotEmpty() && BOARD_SQUARE[x][y] == enPassantSquare;
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
		private void showEnPassantSquare(int x, int y) {
			initY = y;
			if (isEnPassantSquareNotEmpty() && BOARD_SQUARE[x][y].getChessman() instanceof Pawn) {
				if (isAvailEnPassantCoordinate(x, y)) {
					enPassantSquare.setSquareAttackableColor();
				}
			}
		}

		// ���Ļ��� ����Ѵ�.
		private void cancelEnPassant() {
			if (enPassantSquare != null) {
				enPassantSquare.setSquareOriginalColor();
			}
		}

		// ������ ��ǥ�� ���Ļ����� ������ ��ǥ��� ���Ļ����� �ִ� ü������ ������� ���������� �Ǵ��ؼ� �� �ڿ� �ִ� ���� �����Ѵ�.
		private void moveEnPassant(int x, int y) {
			if (isEnPassantMove(x, y)) {
				boolean movedPawnColor = enPassantSquare.getChessman().isWhite();
				if (movedPawnColor) {
					BOARD_SQUARE[x][y + 1].removeChessmanFromSquare();
				} else {
					BOARD_SQUARE[x][y - 1].removeChessmanFromSquare();
				}
				new BoardEventInfoView(Images.ENPASSANT);
//				new BoardEventInfoView(Images.ENPASSANT).setLocation((int) (BoardPanel.super.getWidth() / 3.3),
//						(int) (BoardPanel.super.getHeight() / 2));
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

			initEnPassantSquare();
		}

		// ������ ��ǥ�� square�� �� ���� �������ִ��� Ȯ���Ѵ�.
		private boolean isContainEnemyPawn(boolean isWhite, int x, int y) {
			return Coordinate.isValidate(x, y) && isContainPawn(x, y)
					&& (BOARD_SQUARE[x][y].getChessman().isWhite() != isWhite);
		}

		// ������ ��ǥ�� square�� ���� �������ִ��� Ȯ���Ѵ�.
		private boolean isContainPawn(int x, int y) {
			return (BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].getChessman() instanceof Pawn);
		}

		// ���� �¿쿡 ���� ���� �ִ��� Ȯ���ϰ� �ִٸ� ���Ļ� ����� �����Ѵ�.
		private void setEnPassantSquare(boolean isWhite, int x, int y) {
			enPassantX = x;
			enPassantY = y;

			if (isWhite) {
				if (isContainEnemyPawn(isWhite, x - 1, y) || isContainEnemyPawn(isWhite, x + 1, y)) {
					enPassantSquare = BOARD_SQUARE[x][y + 1];
				}

			} else {
				if (isContainEnemyPawn(isWhite, x - 1, y) || isContainEnemyPawn(isWhite, x + 1, y)) {
					enPassantSquare = BOARD_SQUARE[x][y - 1];
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
			return !BOARD_SQUARE[x][y].isContainChessman() && !BOARD_SQUARE[x + 1][y].isContainChessman();
		}

		// x, x + 1, x + 2 �� ĭ�� ������� Ȯ���Ѵ�
		private boolean isXAndUpperTwoSquareEmpty(int x, int y) {
			return isXAndUpperSquareEmpty(x, y) && !BOARD_SQUARE[x + 2][y].isContainChessman();
		}

		// ĭ�� ���� �������ִ��� Ȯ���Ѵ�.
		private boolean isSquareContainRook(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && BOARD_SQUARE[x][y].getChessman() instanceof Rook;
		}

		// ���� �������� �ʾҴ��� Ȯ���Ѵ�.
		private boolean isRookNotMoved(int x, int y) {
			return !((Rook) BOARD_SQUARE[x][y].getChessman()).isMoved();
		}

		// ���� �������� �ʾҴٸ� ĳ���� ������ ��ǥ�� �߰��Ѵ�.
		private void addCastlingSquareIfRookNotMoved(int x, int y) {
			if (isRookNotMoved(x, y)) {
				castlingSquareList.add(BOARD_SQUARE[x][y]);
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
				BOARD_SQUARE[x][y].setChessmanOnSquare(selectedSquare.getChessman());
			}
		}

		// ĳ���� �������� �������ִ� �޼���ν� ���õ�ĭ(��) �׸��� ���������ν� Ŭ���� square�� ��ǥ�� ���� ĳ���� �������� �����Ѵ�.
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

//			new BoardEventInfoView(Images.CASTLING).setLocation((int) (BoardPanel.super.getWidth() / 3.3),
//					(int) (BoardPanel.super.getHeight() / 2));
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

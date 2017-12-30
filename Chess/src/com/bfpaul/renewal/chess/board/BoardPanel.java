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
	private BoardSquare selectedSquare = null;
	private boolean isWhite = true;

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

		selectedSquare = BOARD_SQUARE[x][y]; // �� �������� ������ ������ ���õ� ����� �����ϰ�
		for (Helper helper : helperList) {
			helper.show(selectedSquare.getChessman(), x, y);
		}

		if (selectedSquare.getContainChessmanType() == ChessmanType.PAWN) {

		}

		selectedSquare.setSquareMoveableColor(); // �������� ĭ�� �����ϼ��ִ� �������� ǥ�����ְ�

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

		/* �ָ��Ѻκ� */
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
//			setPawnOnBoard(ChessmanType.PAWN);
			break;
		default:
		}
	}

	// �ʱ� ������ 1���� ��(ŷ, ��) �� ��� enum�� ordinal�� 0, 1�ε� ���⼭ 3�� ���� ���� ���� ���� ���� x�ν� �ǹ̸�
	// ���� �ȴٴ� ���� �˰�
	// �̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 1���� ���� �Ѳ����� ó���ؼ� Square�� �÷��ְԵǾ���.
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
	private class MoveHelper extends Helper {

		@Override
		void show(Chessman chessman, int x, int y) {
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

		// �ش� ��ǥ�� �ִ� ���� �������� �˻��ϴ� �޼���
		private boolean isEnemy(int x, int y) {
			return selectedSquare.getChessman().isWhite() != BOARD_SQUARE[x][y].getChessman().isWhite();
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

		// ������ �ƴ���
		private boolean isEnemy(int x, int y) {
			return BOARD_SQUARE[x][y].isContainChessman() && (BOARD_SQUARE[x][y].isContainChessmanWhite() != isWhite);
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
				BOARD_SQUARE[x - 1][y].setSquareAttackableColor();
			}

			if (Coordinate.isValidate(x + 1, y) && isEnemy(x + 1, y)) {
				BOARD_SQUARE[x + 1][y].setSquareAttackableColor();
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
	private class EnPassantHelper extends Helper {
		// ���Ļ��� �˻��ؾ� �Ǵ°��� ���� �ʱ� �������� 2�϶� ���̱⶧���� �ʱ� ������ Y�� �����ν� �������ֵ��� �ߴ�.

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
	 * CastlingHelper�� BoardPanel�� Chessman�� �̵������ ĳ������ �����ϱ� ���Ͽ� �ʿ��� ����Ŭ�����̴�.
	 * CastlingHelper�� King�� ���õǾ����� �� ���� �������� �־����� �˻��Ѵ�(�������� �־����� ĳ������ ���ϴϱ�) ��������
	 * �����ٸ� CastilngSquare�� ���ϱ����� ���� ����� ������ �� �� ĳ������ ������ �����ϴ� ���� �ִ��� �˻��Ѵ�. ������ �����ϴ�
	 * ���� �ִٸ� �� ���� BoardSquare�� ĳ���� ������ ��Ͽ� �߰��Ѵ�. �׸��� �߰��� ����Ʈ�� ���������ν� ĳ���� ������ ĭ��
	 * ǥ���Ѵ�. �̴� ������ MoveableRouteCalculator�� ��� �� �� ���� Ư�� ���̽��� �Ǵ��ؼ� ���� �и��Ͽ���.
	 */
	////////////////////////////////////////////////////////////////////////////////////////////////
	private class CastlingHelper extends Helper {

		@Override
		void show(Chessman chessman, int x, int y) {
			if (chessman.getChessmanType() == ChessmanType.KING) {
				showCastlingSquare();
			}
		}

		// ĳ������ ������ ��������(1.ŷ�� �� ���̿� ������ ���� 2.���� ���������̾���� �Ѵ�)�� ��ġ�� ���������� ������ ĳ���� �����
		// �߰��Ѵ�.
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
			return BOARD_SQUARE[x][y].isContainChessman()
					&& BOARD_SQUARE[x][y].getContainChessmanType() == ChessmanType.ROOK;
		}

		// ���� �������� �ʾҴ��� Ȯ���Ѵ�.
		private boolean isRookNotMoved(int x, int y) {
			return !((Rook) BOARD_SQUARE[x][y].getChessman()).isMoved();
		}

		// ���� �������� �ʾҴٸ� ĳ���� ������ ��ǥ�� �߰��Ѵ�.
		private void showCastlingSquareIfRookNotMoved(int x, int y) {
			if (isRookNotMoved(x, y)) {
				BOARD_SQUARE[x][y].setSquareCastlingColor();
			}
		}

		// ���������ν� Ŭ���� sqaure�� ��ǥ�� ���� ��ǥ�� �� ĳ���� �������� �����Ѵ�. �� �̿ܶ�� �ܼ� �������� �����Ѵ�.
		private void operateCastling(int x, int y) {
			if ((x == 7 && y == 7) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 0 && y == 0)) {
				operateCastlingMove(x, y);

			} else {
				BOARD_SQUARE[x][y].setChessmanOnSquare(selectedSquare.getChessman());
				selectedSquare.removeChessmanFromSquare();
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

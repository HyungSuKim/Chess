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
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private BoardSquare selectedSquare = null;
	boolean isWhite = true;

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

	// �Էµ� ���Կ� �θ��� ũ�⸸ŭ ������ �����ϴ� ���������� �����Ͽ� ��ȯ�Ѵ�.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// ������ ��ǥ�� �ϳ��� ĭ�� �����ؼ� �ϳ��� square�� �迭�ν� ����Ǿ� ��ǥ�� �ǹ̸� ������ �ؼ� ���� Ŭ������ ���� �����ִ�
	// �̺�Ʈ�� ó���Ҷ�
	// ��ǥ�� ���� �̿��ؼ� square�� �̺�Ʈ�� ó�� �� �� �ֵ��� �Ϸ����Ѵ�.
	private BoardSquare createBoardSquare(int x, int y) {
		boardSquare[y][x] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_DARK_SQUARE_COLOR : Theme.BOARD_LIGHT_SQUARE_COLOR);
		boardSquare[y][x].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// ó����������
				if (boardSquare[y][x].isContain() && selectedSquare == null) {
					disableSquareClickEvent();
					
					selectedSquare = boardSquare[y][x];
					moveHelper.moveableRoute = MoveableRouteCalculator.selectChessman(boardSquare[y][x].getChessman(), x, y);
					selectedSquare.setSquareMoveableColor();
					moveHelper.showMoveableRoute(boardSquare[y][x].getChessman().isWhite());
					
					if(selectedSquare.getChessman() instanceof Pawn) {
						pawnAttackHelper.checkShowPawnAttackableSquare(x, y);
						enPassantHelper.checkShowEnPassantSquare(x, y);
					}
					
					if(selectedSquare.getChessman() instanceof King) {
						castlingHelper.checkShowCastlingSquare(x, y);
					}

					checkmateHelper.showCheckmateRoute();
										
				} else if (selectedSquare == boardSquare[y][x]) {
					initSquareEvent(isWhite);
					
					pawnAttackHelper.disablePawnAttackableSquare();

					castlingHelper.cancelCastling();

					enPassantHelper.cancelEnPassant();
					
					checkmateHelper.disableCheckmateRoute();

				} else {
					// �̵���η� �̵����� ��
					isWhite = !isWhite;
					if (boardSquare[y][x].isContain()) {
						// �� ������ �������� ���Ŀ� CurrentChessmanView�� ���� ��Ű������ �׽�Ʈ �ϴ� �κ�
						// ������ ���Ļ��� �ϴ°�쿡 ���� ���� ǥ�õ��� �ʴ� �ٴ� �Ͱ� ĳ������ �ϴ� ��� Rook�� �����ɷ� ǥ�õȴٴ� ���̴�.
						// �׷��� �������� ���������� �� ���� �����ٱ�... ������ �ϰ��ִµ� ���� �����̴�.
						System.out.println(boardSquare[y][x].getChessman().getChessmanType().name());
					}

					// ĳ���� ���� & ���Ļ� ����
					if (selectedSquare.getChessman() instanceof King && castlingHelper.castlingSquare.size() != 0) {
						castlingHelper.moveCastling(x, y);
						enPassantHelper.enPassantSquare = boardSquare[0][0];
					} else {
						boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
					}

					if (boardSquare[y][x].getChessman() instanceof Pawn) {
						pawnAttackHelper.pawnAttack(x, y);
						enPassantHelper.moveEnPassant(x, y);
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

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)�� ���ϴ� ��ǥ��(x,y)�� square��
	// �÷��ش�.
	// �÷��ִ� ������ BoardSquare class�� �޼��带 �̿��ؼ� �ϴµ� �̸����� ���ϴ� ������ ���� ü������ �߰��ȴ�.
	public void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[y][x].setChessmanOnSquare(chessman);
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
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 7);
	}

	// �ʱ� ������ 2���� ��(���, ����Ʈ, ��) �� ��� enum�� ordinal�� 2, 3, 4�ε� ���⼭ 3�� ���� ���� 7���� �� ����
	// �� ����
	// ���� ���� ���� x�ν� �ǹ̸� ���� �ȴٴ� ���� �˰� �̷��� ��Ģ�� �̿��ؼ� �ʱ� ������ 2���� ���� �Ѳ����� ó���ؼ� Square��
	// �÷��ְԵǾ���.
	private void setPairChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), 0);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), 7);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 7);
	}

	// ������ ������ ������(�ʱ�) ��ġ�� �������ֱ����ؼ� �ۼ��Ͽ���. ���� ��� �ʱ� ������ŭ �ݺ��ؼ� �����ؼ� Square���� �÷��ִµ�
	// ����� �������� ��Ī���� ������ �Ȱ����� �̿��ߴ�.
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
		
		private void disableMoveableRoute() {
			for (MoveableRoute moveableRoute : moveableRoute) {
				for (Coordinate coordinate : moveableRoute.getCoordinates()) {
					boardSquare[coordinate.getY()][coordinate.getX()].setSquareOriginalColor();
				}
			}
		}

		private void showMoveableRoute(boolean isWhite) {
			for (MoveableRoute moveableRoute : moveableRoute) {
				moveableSquareJudger(isWhite, moveableRoute.getCoordinates());
			}
		}

		private void moveableSquareJudger(boolean isWhite, Coordinate[] moveableCoordinates) {
			for (Coordinate coordinate : moveableCoordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();
				if (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman().isWhite() == isWhite) {
					if (!(selectedSquare.getChessman() instanceof Knight))
						break;
					// ���õ� ���� ����Ʈ�̰� �̵���ο� ������������
				} else if (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman().isWhite() != isWhite
						&& selectedSquare.getChessman() instanceof Knight) {
					boardSquare[y][x].setSquareAttackableColor();

				} else if (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman().isWhite() != isWhite) {
					if (selectedSquare.getChessman() instanceof Pawn)
						break;
					boardSquare[y][x].setSquareAttackableColor();
					break;

				} else {
					if (selectedSquare.getChessman() instanceof Pawn && boardSquare[y][x].isContain())
						break;

					if (boardSquare[y][x].getBackground().equals(Color.GREEN)
							&& selectedSquare.getChessman() instanceof King) {
						boardSquare[y][x].setSquareCheckmateColor();
					} else {
						boardSquare[y][x].setSquareMoveableColor();
					}
				}
			}
		}
	}
	
	class PawnAttackHelper {
		private ArrayList<BoardSquare> pawnAtackableSquare = new ArrayList<>(); // ����Ŭ����
		
		private void disablePawnAttackableSquare() {
			if (!pawnAtackableSquare.isEmpty()) {
				for (BoardSquare square : pawnAtackableSquare) {
					square.setSquareOriginalColor();
				}
			}
		}
		
		private void checkShowPawnAttackableSquare(int x, int y) {
			pawnAtackableChecker(x, y);
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

		private void pawnAtackableChecker(int x, int y) {
			pawnAtackableSquare.clear();
			if (isWhite && boardSquare[y][x].getChessman() instanceof Pawn) {
				if (Coordinate.isValidate(x + 1, y + 1))
					if (boardSquare[y + 1][x + 1].isContain() && !boardSquare[y + 1][x + 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[y + 1][x + 1]);
					}
				if (Coordinate.isValidate(x - 1, y + 1))
					if (boardSquare[y + 1][x - 1].isContain() && !boardSquare[y + 1][x - 1].getChessman().isWhite()) {
						pawnAtackableSquare.add(boardSquare[y + 1][x - 1]);
					}
			} else if (!isWhite && boardSquare[y][x].getChessman() instanceof Pawn) {
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
					if (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman() instanceof King) {
						boardSquare[y][x].setBackground(Color.GREEN);
					} else {
						boardSquare[y][x].setSquareCheckmateColor();
					}
				}
			}
		}

		private void disableCheckmateRoute() {
			for (MoveableRoute moveableRoute : checkmateRoute)
				for (Coordinate coordinate : moveableRoute.getCoordinates())
					boardSquare[coordinate.getY()][coordinate.getX()].setSquareOriginalColor();
			
			checkmateRoute.clear();
		}

		private void checkmateChecker(boolean isWhite) {
			checkmateRoute.clear();

			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (boardSquare[y][x].isContain() && boardSquare[y][x].getChessman().isWhite() != isWhite) {
						Chessman checkingChessman = boardSquare[y][x].getChessman();
						checkmateRoute(checkingChessman, MoveableRouteCalculator.selectChessman(checkingChessman, x, y));
					}
				} // end of for x
			} // end of for y
		}

		private void checkmateRoute(Chessman checkingChessman, ArrayList<MoveableRoute> moveableRoute) {
			for (MoveableRoute route : moveableRoute) {
				for (Coordinate coordinate : route.getCoordinates()) {
					int x = coordinate.getX();
					int y = coordinate.getY();

					if (boardSquare[y][x].isContain()
							&& boardSquare[y][x].getChessman().isWhite() == checkingChessman.isWhite()) {
						break;
					} else if (boardSquare[y][x].isContain()
							&& boardSquare[y][x].getChessman().isWhite() != checkingChessman.isWhite()) {

						if (boardSquare[y][x].getChessman() instanceof King) {
							if (checkingChessman instanceof Knight) {
								Coordinate[] knightCheckmateCoordinate = new Coordinate[1];
								knightCheckmateCoordinate[0] = coordinate;
								checkmateRoute.add(new MoveableRoute(route.getDirection(), knightCheckmateCoordinate));
								break;
							} else {
								checkmateRoute.add(new MoveableRoute(route.getDirection(), route.getCoordinates()));
								break;
							}
						} else {
							break;
						}
					}
				}
			}
		}
	}

	class EnPassantHelper {
		private BoardSquare enPassantSquare = boardSquare[0][0];

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
				// ���࿡ ���� �̵��� ������ġ���� ���� 2�϶� �� ���Ļ� üũ�� �Ѵ�... �ϸ� �������Ű�����?
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
				// boardSquare[y][x-1] �Ǵ� boardSquare[y][x+1] �ִ°� �������̳�? �� �˻��ϰ�
				if (Coordinate.isValidate(x - 1, y))
					if ((boardSquare[y][x - 1].isContain() && boardSquare[y][x - 1].getChessman() instanceof Pawn
							&& !boardSquare[y][x - 1].getChessman().isWhite())) {
						enPassantSquare = boardSquare[y - 1][x];
					}
				// ������ boardSquare[y-1][x]�� ���Ļ� ����� ����
				if (Coordinate.isValidate(x + 1, y))
					if ((boardSquare[y][x + 1].isContain() && boardSquare[y][x + 1].getChessman() instanceof Pawn
							&& !boardSquare[y][x + 1].getChessman().isWhite())) {
						enPassantSquare = boardSquare[y - 1][x];
					}

			} else {
				// boardSquare[y][x-1] �Ǵ� boardSquare[y][x+1] �ִ°� ������̳�? �� �˻��ϰ�
				if (Coordinate.isValidate(x - 1, y))
					if ((boardSquare[y][x - 1].isContain() && boardSquare[y][x - 1].getChessman() instanceof Pawn
							&& boardSquare[y][x - 1].getChessman().isWhite())) {
						enPassantSquare = boardSquare[y + 1][x];
					}
				// ������ boardSquare[y+1][x]�� ���Ļ� ����� ����
				if (Coordinate.isValidate(x + 1, y))
					if ((boardSquare[y][x + 1].isContain() && boardSquare[y][x + 1].getChessman() instanceof Pawn
							&& boardSquare[y][x + 1].getChessman().isWhite())) {
						enPassantSquare = boardSquare[y + 1][x];
					}
			}
		}
	}

	class CastlingHelper {
		// ĳ���� ����
		private ArrayList<BoardSquare> castlingSquare = new ArrayList<>(); // ����Ŭ����

		private void checkShowCastlingSquare(int x, int y) {
			// ĳ���� ����
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

		// ĳ���� ����
		private void setCastlingSquare(boolean isWhite) {
			castlingSquare.clear();
			if (isWhite) {
				if (!boardSquare[0][5].isContain() && !boardSquare[0][6].isContain() && boardSquare[0][7].isContain()
						&& boardSquare[0][7].getChessman() instanceof Rook) {
					if (!((Rook) boardSquare[0][7].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[0][7]);
					}
				}

				if (!boardSquare[0][1].isContain() && !boardSquare[0][2].isContain() && !boardSquare[0][3].isContain()
						&& boardSquare[0][0].isContain() && boardSquare[0][0].getChessman() instanceof Rook) {
					if (!((Rook) boardSquare[0][0].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[0][0]);
					}
				}
			} else {
				if (!boardSquare[7][5].isContain() && !boardSquare[7][6].isContain() && boardSquare[7][7].isContain()
						&& boardSquare[7][7].getChessman() instanceof Rook) {
					if (!((Rook) boardSquare[7][7].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[7][7]);
					}
				}

				if (!boardSquare[7][1].isContain() && !boardSquare[7][2].isContain() && !boardSquare[7][3].isContain()
						&& boardSquare[7][0].isContain() && boardSquare[7][0].getChessman() instanceof Rook) {
					if (!((Rook) boardSquare[7][0].getChessman()).isMoved()) {
						castlingSquare.add(boardSquare[7][0]);
					}
				}
			}
		}

		// ĳ���� ����
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

		// ĳ���� ����
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

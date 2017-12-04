package com.bfpaul.renewal.chess.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;

import com.bfpaul.renewal.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.chessman.Direction;
import com.bfpaul.renewal.chess.chessman.King;
import com.bfpaul.renewal.chess.chessman.Knight;
import com.bfpaul.renewal.chess.chessman.Pawn;
import com.bfpaul.renewal.chess.chessman.PawnPromotionSelectView;
import com.bfpaul.renewal.chess.chessman.Rook;
import com.bfpaul.renewal.chess.controller.Coordinate;
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
// BoardSquare�� �ֿ� ����� �Ʒ��� ����.
// 1. ü������ ��� �̵���θ� �����ش�(���ݰ��ɰ�� ����) 2. �ش��η� ���� �̵���Ų��. 3. ���� �̵� �� ���� �̵��� üũ�� �������� �Ǵ��Ѵ�.
// BoardPanel�� ����Ŭ������ Castling, EnPassant�� �������ִµ� �� Ŭ�������� ����Ŭ������ �з��� ������
// boardSquare�� ü������ �̵��� ���� �κ����ν� BoardPanel�� �����ϴ� ���� ���������� ������ ���ٰ� �Ǵ�������
// BoardPanel���� �⺻�̵� �̿��� Ư�� �̵����� Castling�� EnPassant�� ���� ������ ������ BoardPanel���� ����/��������� ������ �ֱ⿡
// ����Ŭ������ ���� �����Ͽ���. ����Ŭ������ ���������ν� �� Ŭ������ �ܺ�Ŭ������ BoardPanel���� ���� �ʿ�� ���� �ʴ� �޼��带 ���ߴ� ȿ�� ���� �ִ�. 
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
	// ü�� ���� �ϳ��ϳ��� square�ν� ü������ �����شٴ��� ü������ �������شٰų� �̵����ɹ����� ǥ������ �ּҴ����� ĭ�̴�.
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private BoardSquare selectedSquare = null;
	private Map<Direction, Coordinate[]> moveableSquare;
	boolean isWhite = true;

	// ĳ���� ����
	private Castling castling = new Castling();

	private ArrayList<BoardSquare> pawnAtackableSquare = new ArrayList<>(); // ����Ŭ����

	private EnPassant enPassant;

	private Map<Direction, Coordinate[]> checkmateSquare = new HashMap<>(); // �и� ���

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
		disableSquareClickEvent();
		ableSquareClickEvent(isWhite);

		enPassant = new EnPassant();

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
					boardSquare[y][x].setSquareMoveableColor();

					pawnAtackableChecker(x, y);
					if (!pawnAtackableSquare.isEmpty()) {
						for (BoardSquare square : pawnAtackableSquare) {
							square.setSquareAttackableColor();
						}
					}

					enPassant.checkEnPassantAvailable(x, y);

					/* �����ִ� �κ� */
					moveableSquare = MoveableRouteCalculator.selectChessman(boardSquare[y][x].getChessman(), x, y);
					/* �����ִ� �κ� */
					/* üũ����Ʈ �׽��� �ڵ� */
					if (!checkmateSquare.isEmpty()) {
						for (Direction direction : checkmateSquare.keySet()) {
							for (Coordinate coordinate : checkmateSquare.get(direction)) {
								if (Coordinate.isValidate(coordinate.getX(), coordinate.getY()))
									if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()) {
										if (boardSquare[coordinate.getY()][coordinate.getX()]
												.getChessman() instanceof King) {
											boardSquare[coordinate.getY()][coordinate.getX()]
													.setBackground(Color.GREEN);
										}
									} else {
										boardSquare[coordinate.getY()][coordinate.getX()].setSquareCheckmateColor();
									}
							}
						}
					}
					/* üũ����Ʈ �׽��� �ڵ� */

					castling.checkCastling(x, y);

					showMoveableSquare(boardSquare[y][x].getChessman().isWhite());
					// �ٽ� ��������
				} else if (selectedSquare == boardSquare[y][x]) {
					initSquareEvent(isWhite);
					if (!pawnAtackableSquare.isEmpty()) {
						for (BoardSquare square : pawnAtackableSquare) {
							square.setSquareOriginalColor();
						}
					}

					castling.cancelCastling();

					enPassant.cancelEnPassant();
					/* üũ����Ʈ �׽�Ʈ */
					initCheckmateSquare();
					/* üũ����Ʈ �׽�Ʈ */

				} else {
					// �̵���η� �̵����� ��
					isWhite = !isWhite;
					if (boardSquare[y][x].getChessman() != null) {
						// �� ������ �������� ���Ŀ� CurrentChessmanView�� ���� ��Ű������ �׽�Ʈ �ϴ� �κ�
						// ������ ���Ļ��� �ϴ°�쿡 ���� ���� ǥ�õ��� �ʴ� �ٴ� �Ͱ� ĳ������ �ϴ� ��� Rook�� �����ɷ� ǥ�õȴٴ� ���̴�.
						// �׷��� �������� ���������� �� ���� �����ٱ�... ������ �ϰ��ִµ� ���� �����̴�.
						System.out.println(boardSquare[y][x].getChessman().getChessmanType().name());
					}

					// ĳ���� ���� & ���Ļ� ����
					if (selectedSquare.getChessman() instanceof King && castling.castlingSquare.size() != 0) {
						castling.moveCastling(x, y);
						enPassant.enPassantSquare = boardSquare[0][0];
					} else {
						boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
					}

					if (boardSquare[y][x].getChessman() instanceof Pawn) {
						pawnAttack(x, y);
						enPassant.moveEnPassant(x, y);
					}

					initCheckmateSquare();
					checkmateSquare.clear();

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
					checkmateChecker(isWhite);
					initSquareEvent(isWhite);
				}
			}
		});
		return boardSquare[y][x];
	}

	private void initCheckmateSquare() {
		if (!checkmateSquare.isEmpty()) {
			for (Direction direction : checkmateSquare.keySet())
				for (Coordinate coordinate : checkmateSquare.get(direction))
					if (coordinate != null)
						boardSquare[coordinate.getY()][coordinate.getX()].setSquareOriginalColor();
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

	public void initSquareEvent(boolean isWhite) {
		selectedSquare.setSquareOriginalColor();
		disableMoveableSquare();
		ableSquareClickEvent(isWhite);
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

	private void ableSquareClickEvent(boolean isWhite) {
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

	private void disableMoveableSquare() {
		for (Direction direction : moveableSquare.keySet()) {
			for (Coordinate coordinate : moveableSquare.get(direction)) {
				boardSquare[coordinate.getY()][coordinate.getX()].setSquareOriginalColor();
			}
		}
	}

	private void showMoveableSquare(boolean isWhite) {
		for (Direction direction : moveableSquare.keySet()) {
			moveableSquareJudger(isWhite, moveableSquare.get(direction));
		}
	}

	private void moveableSquareJudger(boolean isWhite, Coordinate[] moveableCoordinate) {
		for (Coordinate coordinate : moveableCoordinate) {
			// �̵������ ��ǥ�� �������� ������
			if(coordinate != null)
			if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
					&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite) {

				if (!(selectedSquare.getChessman() instanceof Knight))
					break;
				// ���õ� ���� ����Ʈ�̰� �̵���ο� ������������
			} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
					&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() != isWhite
					&& selectedSquare.getChessman() instanceof Knight) {

				boardSquare[coordinate.getY()][coordinate.getX()].setSquareAttackableColor();

			} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
					&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() != isWhite) {

				if (selectedSquare.getChessman() instanceof Pawn)
					break;

				boardSquare[coordinate.getY()][coordinate.getX()].setSquareAttackableColor();
				break;

			} else {
				if (selectedSquare.getChessman() instanceof Pawn
						&& boardSquare[coordinate.getY()][coordinate.getX()].isContain())
					break;
				if (boardSquare[coordinate.getY()][coordinate.getX()].getBackground().equals(Color.GREEN)
						&& selectedSquare.getChessman() instanceof King) {
					boardSquare[coordinate.getY()][coordinate.getX()].setSquareCheckmateColor();
				} else {
					boardSquare[coordinate.getY()][coordinate.getX()].setSquareMoveableColor();
				}

			}
		}
	}

	/////////////////////// üũ����Ʈ////////////////////////////
	private void checkmateChecker(boolean isWhite) {
		// checkmateSquare.clear();
		for (int y = 8; y > 0; y--) {
			for (int x = 0; x < 8; x++) {
				if (boardSquare[y - 1][x].isContain() && boardSquare[y - 1][x].getChessman().isWhite() != isWhite)
					checkmateRoute(boardSquare[y - 1][x],
							MoveableRouteCalculator.selectChessman(boardSquare[y - 1][x].getChessman(), x, y - 1));
			}
		}
	}

	private void checkmateRoute(BoardSquare checkingSquare, Map<Direction, Coordinate[]> moveableSquare) {
		for (Direction direction : moveableSquare.keySet()) {
			for (Coordinate coordinate : moveableSquare.get(direction)) {
				if(coordinate != null)
				if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == checkingSquare
								.getChessman().isWhite()) {
					break;
				} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() != checkingSquare
								.getChessman().isWhite()) {

					if (!(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)) {
						break;
					} else if (boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) {
						switch (direction) {
						case UP:
							if (checkingSquare.getChessman() instanceof Knight) {
								if (!moveableSquare.isEmpty())
									knightCheckmateRoute(moveableSquare, Direction.UP);
								break;
							} else {
								checkmateSquare.put(Direction.UP, moveableSquare.get(Direction.UP));
								break;
							}
						case DOWN:
							if (checkingSquare.getChessman() instanceof Knight) {
								if (!moveableSquare.isEmpty())
									knightCheckmateRoute(moveableSquare, Direction.DOWN);
								break;
							} else {
								checkmateSquare.put(Direction.DOWN, moveableSquare.get(Direction.DOWN));
								break;
							}
						case LEFT:
							if (!moveableSquare.isEmpty())
								if (checkingSquare.getChessman() instanceof Knight) {
									knightCheckmateRoute(moveableSquare, Direction.LEFT);
									break;
								} else {
									checkmateSquare.put(Direction.LEFT, moveableSquare.get(Direction.LEFT));
									break;
								}
						case RIGHT:
							if (checkingSquare.getChessman() instanceof Knight) {
								if (!moveableSquare.isEmpty())
									knightCheckmateRoute(moveableSquare, Direction.RIGHT);
								break;
							} else {
								checkmateSquare.put(Direction.RIGHT, moveableSquare.get(Direction.RIGHT));
								break;
							}
						case UP_LEFT:
							checkmateSquare.put(Direction.UP_LEFT, moveableSquare.get(Direction.UP_LEFT));
							break;
						case UP_RIGHT:
							checkmateSquare.put(Direction.UP_RIGHT, moveableSquare.get(Direction.UP_RIGHT));
							break;
						case DOWN_LEFT:
							checkmateSquare.put(Direction.DOWN_LEFT, moveableSquare.get(Direction.DOWN_LEFT));
							break;
						case DOWN_RIGHT:
							checkmateSquare.put(Direction.DOWN_RIGHT, moveableSquare.get(Direction.DOWN_RIGHT));
							break;
						default:
							break;
						}
						break;
					}
				}
			}
		}
	}

	private void knightCheckmateRoute(Map<Direction, Coordinate[]> moveableSquare, Direction direction) {
		for (Coordinate moveableCoordinate : moveableSquare.get(direction)) {
			if (Coordinate.isValidate(moveableCoordinate.getX(), moveableCoordinate.getY()))
				if (boardSquare[moveableCoordinate.getY()][moveableCoordinate.getX()].getChessman() instanceof King) {
					Coordinate[] checkResult = new Coordinate[1];
					checkResult[0] = moveableCoordinate;
					checkmateSquare.put(direction, checkResult);
				}
		}
	}

	class EnPassant {
		private BoardSquare enPassantSquare = boardSquare[0][0];

		void checkEnPassantAvailable(int x, int y) {
			if (enPassantSquare != boardSquare[0][0] && boardSquare[y][x].getChessman() instanceof Pawn) {
				if ((Math.abs(enPassantSquare.getX() - boardSquare[y][x].getX()) == 114)
						&& (Math.abs(enPassantSquare.getY() - boardSquare[y][x].getY()) == 95))
					enPassantSquare.setSquareAttackableColor();
			}
		}

		void cancelEnPassant() {
			if (enPassantSquare != boardSquare[0][0]) {
				enPassantSquare.setSquareOriginalColor();
			}
		}

		void moveEnPassant(int x, int y) {
			if (boardSquare[y][x].getChessman() instanceof Pawn) {
				// ���࿡ ���� �̵��� ������ġ���� ���� 2�϶� �� ���Ļ� üũ�� �Ѵ�... �ϸ� �������Ű�����?
				if (Math.abs(boardSquare[y][x].getY() - selectedSquare.getY()) == 190) {
					((Pawn) boardSquare[y][x].getChessman()).setIsMoved();
					setEnPassantSquare(boardSquare[y][x].getChessman().isWhite(), x, y);

				} else if (enPassantSquare != boardSquare[0][0] && boardSquare[y][x] == enPassantSquare) {
					if (boardSquare[y][x].getChessman().isWhite()) {
						boardSquare[y - 1][x].removeChessmanFromSquare();
						enPassantSquare.setSquareOriginalColor();
						enPassantSquare = boardSquare[0][0];
					} else {
						boardSquare[y + 1][x].removeChessmanFromSquare();
						enPassantSquare.setSquareOriginalColor();
						enPassantSquare = boardSquare[0][0];
					}
				} else if (enPassantSquare != boardSquare[0][0] && boardSquare[y][x] != enPassantSquare) {
					enPassantSquare.setSquareOriginalColor();
					enPassantSquare = boardSquare[0][0];
				} else {
					((Pawn) boardSquare[y][x].getChessman()).setIsMoved();
					if (enPassantSquare != boardSquare[0][0]) {
						enPassantSquare.setSquareOriginalColor();
						enPassantSquare = boardSquare[0][0];
					}
				}
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

	class Castling {
		// ĳ���� ����
		private ArrayList<BoardSquare> castlingSquare = new ArrayList<>(); // ����Ŭ����

		void checkCastling(int x, int y) {
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

		void cancelCastling() {
			// ĳ���� ����
			if (!castlingSquare.isEmpty()) {
				for (BoardSquare square : castlingSquare) {
					square.setSquareOriginalColor();
				}
			}
		}

		void moveCastling(int x, int y) {
			operateCastling(selectedSquare.getChessman().isWhite(), x, y);
			for (BoardSquare square : castlingSquare) {
				square.setSquareOriginalColor();
			}
			castlingSquare.clear();
		}

		// ĳ���� ����
		void setCastlingSquare(boolean isWhite) {
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
		void operateCastling(boolean isWhite, int x, int y) {
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

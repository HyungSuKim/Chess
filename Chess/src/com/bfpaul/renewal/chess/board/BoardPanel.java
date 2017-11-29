package com.bfpaul.renewal.chess.board;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
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

// x�� y�� 8ĭ�� square�� ���� Board�� ���� �� Board������ �Ͼ�� �̺�Ʈ��(ü������ �����شٴ��� ü������ �׾ �����Ѵٴ��� ü������ �̵����ɹ����� �����شٴ���...)��
// ó���ϴ� ������ �� ���̴�.
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
	// ü�� ���� �ϳ��ϳ��� square�ν� ü������ �����شٴ��� ü������ �������شٰų� �̵����ɹ����� ǥ������ �ּҴ����� ĭ�̴�.
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private BoardSquare selectedSquare = null;
	private Map<Direction, Coordinate[]> moveableSquare;
	private ArrayList<BoardSquare> kingCastlingSquare = new ArrayList<>();
	private ArrayList<BoardSquare> pawnAtackableSquare = new ArrayList<>();
	private BoardSquare enPassantSquare = null;
	private Coordinate[] checkmateSquare = null;
	boolean isWhite = true;

	// 8 X 8�� square�� ���� ü������ ������ش�.
	public BoardPanel() {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Theme.BOARD_BORDER_COLOR);
		setOpaque(true);

		for (int y = 8; y > 0; y--) {
			for (int x = 0; x < 8; x++) {
				add(createBoardSquare(x, (y - 1)), createMatchParentConstraints(1));
			}
		}
		setWholeChessmanOnBoard();
		disableSquareClickEvent();
		ableSquareClickEvent(isWhite);
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
					boardSquare[y][x].setSquareEventColor();

					pawnAtackableChecker(x, y);
					if (!pawnAtackableSquare.isEmpty()) {
						for (BoardSquare square : pawnAtackableSquare) {
							square.setSquareAttackableColor();
						}
					}

					if (enPassantSquare != null && boardSquare[y][x].getChessman() instanceof Pawn) {
						if ((Math.abs(enPassantSquare.getX() - boardSquare[y][x].getX()) == 114)
								&& (Math.abs(enPassantSquare.getY() - boardSquare[y][x].getY()) == 95))
							enPassantSquare.setSquareAttackableColor();
					}

					/* �����ִ� �κ� */
					moveableSquare = MoveableRouteCalculator.selectChessman(boardSquare[y][x].getChessman(), x, y);
					/* �����ִ� �κ� */
					/*üũ����Ʈ �׽��� �ڵ�*/
					if(checkmateSquare != null) {
						for(Coordinate coordinate : checkmateSquare) {
							if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) break;
							boardSquare[coordinate.getY()][coordinate.getX()].setSquareCheckmateColor();
						}
//						boardSquare[y][x].setSquareEventColor();
					}
					/*üũ����Ʈ �׽��� �ڵ�*/
					if ((boardSquare[y][x].getChessman() instanceof King
							&& !((King) boardSquare[y][x].getChessman()).isMoved())) {
						kingCastlingChecker(boardSquare[y][x].getChessman().isWhite(), x, y);
						if (!kingCastlingSquare.isEmpty()) {
							for (BoardSquare square : kingCastlingSquare) {
								square.setSquareCastlingColor();
							}
						}
					}

					showMoveableSquare(boardSquare[y][x].getChessman().isWhite());
					// �ٽ� ��������
				} else if (selectedSquare == boardSquare[y][x]) {
					initSquareEvent(isWhite);
					if (!pawnAtackableSquare.isEmpty()) {
						for (BoardSquare square : pawnAtackableSquare) {
							square.setSquareOriginalColor();
						}
					}

					if (!kingCastlingSquare.isEmpty()) {
						for (BoardSquare square : kingCastlingSquare) {
							square.setSquareOriginalColor();
						}
					}

					if (enPassantSquare != null) {
						enPassantSquare.setSquareOriginalColor();
					}
					
					if(checkmateSquare != null) {
						for(Coordinate coordinate : checkmateSquare) {
							if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) break;
							boardSquare[coordinate.getY()][coordinate.getX()].setSquareOriginalColor();
						}
//						boardSquare[y][x].setSquareEventColor();
					}
				} else {
					// �̵���η� �̵����� ��
					isWhite = !isWhite;
					if (boardSquare[y][x].getChessman() != null) {
						System.out.println(boardSquare[y][x].getChessman().getChessmanType().name());
					}

					if (selectedSquare.getChessman() instanceof King && kingCastlingSquare.size() != 0) {
						kingCastling(selectedSquare.getChessman().isWhite(), x, y);

						for (BoardSquare square : kingCastlingSquare) {
							square.setSquareOriginalColor();
						}
						kingCastlingSquare.clear();
						enPassantSquare = null;
					} else {
						boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
						if (!(boardSquare[y][x].getChessman() instanceof Pawn))
							enPassantSquare = null;
					}

					if (boardSquare[y][x].getChessman() instanceof Pawn) {
						pawnAttack(x, y);
						pawnEnPassant(x, y);
					}
					
					if(boardSquare[y][x].isContain()) {
//						������ ĭ�� ���� ������������ �� üũ����Ʈ ����� �ϵ��� �ϴ� ���̴�. ������ ���Ļ��� ��쿡�� Ŭ���� ĭ���� �̹� ���� �������̱� �����̴�.
						checkmateChecker(isWhite,
								MoveableRouteCalculator.selectChessman(boardSquare[y][x].getChessman(), x, y));
					}

					if (boardSquare[y][x].getChessman() instanceof King) {
						((King) boardSquare[y][x].getChessman()).setIsMoved();
					}

					if (boardSquare[y][x].getChessman() instanceof Rook) {
						((Rook) boardSquare[y][x].getChessman()).setIsMoved();
					}

					if (boardSquare[y][x].getChessman() instanceof Pawn && (y == 0 || y == 7)) {
						// System.out.println("�� ���θ�ǰ���");
						new PawnPromotionSelectView(boardSquare[y][x]);
					}

					selectedSquare.removeChessmanFromSquare();
					initSquareEvent(isWhite);
				}
			}
		});
		return boardSquare[y][x];
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

	private void pawnEnPassant(int x, int y) {
		if (boardSquare[y][x].getChessman() instanceof Pawn) {
			// ���࿡ ���� �̵��� ������ġ���� ���� 2�϶� �� ���Ļ� üũ�� �Ѵ�... �ϸ� �������Ű�����?
			if (Math.abs(boardSquare[y][x].getY() - selectedSquare.getY()) == 190) {
				((Pawn) boardSquare[y][x].getChessman()).setIsMoved();
				pawnEnPassantChecker(boardSquare[y][x].getChessman().isWhite(), x, y);

			} else if (enPassantSquare != null && boardSquare[y][x] == enPassantSquare) {
				if (boardSquare[y][x].getChessman().isWhite()) {
					boardSquare[y - 1][x].removeChessmanFromSquare();
					enPassantSquare.setSquareOriginalColor();
					enPassantSquare = null;
				} else {
					boardSquare[y + 1][x].removeChessmanFromSquare();
					enPassantSquare.setSquareOriginalColor();
					enPassantSquare = null;
				}
			} else if (enPassantSquare != null && boardSquare[y][x] != enPassantSquare) {
				enPassantSquare.setSquareOriginalColor();
				enPassantSquare = null;
			} else {
				((Pawn) boardSquare[y][x].getChessman()).setIsMoved();
				if (enPassantSquare != null) {
					enPassantSquare.setSquareOriginalColor();
					enPassantSquare = null;
				}
			}
		}
	}

	private void pawnEnPassantChecker(boolean isWhite, int x, int y) {
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

	private void kingCastlingChecker(boolean isWhite, int x, int y) {
		kingCastlingSquare.clear();
		if (isWhite) {
			if (!boardSquare[0][5].isContain() && !boardSquare[0][6].isContain() && boardSquare[0][7].isContain()
					&& boardSquare[0][7].getChessman() instanceof Rook) {
				if (!((Rook) boardSquare[0][7].getChessman()).isMoved()) {
					kingCastlingSquare.add(boardSquare[0][7]);
				}
			}

			if (!boardSquare[0][1].isContain() && !boardSquare[0][2].isContain() && !boardSquare[0][3].isContain()
					&& boardSquare[0][0].isContain() && boardSquare[0][0].getChessman() instanceof Rook) {
				if (!((Rook) boardSquare[0][0].getChessman()).isMoved()) {
					kingCastlingSquare.add(boardSquare[0][0]);
				}
			}
		} else {
			if (!boardSquare[7][5].isContain() && !boardSquare[7][6].isContain() && boardSquare[7][7].isContain()
					&& boardSquare[7][7].getChessman() instanceof Rook) {
				if (!((Rook) boardSquare[7][7].getChessman()).isMoved()) {
					kingCastlingSquare.add(boardSquare[7][7]);
				}
			}

			if (!boardSquare[7][1].isContain() && !boardSquare[7][2].isContain() && !boardSquare[7][3].isContain()
					&& boardSquare[7][0].isContain() && boardSquare[7][0].getChessman() instanceof Rook) {
				if (!((Rook) boardSquare[7][0].getChessman()).isMoved()) {
					kingCastlingSquare.add(boardSquare[7][0]);
				}
			}
		}
	}

	private void kingCastling(boolean isWhite, int x, int y) {
		if (isWhite) {
			if (selectedSquare.getChessman() instanceof King && selectedSquare.getChessman().isWhite()
					&& (x == 7 && y == 0)) {
				((King) selectedSquare.getChessman()).setIsMoved();
				((Rook) boardSquare[y][x].getChessman()).setIsMoved();
				boardSquare[0][6].setChessmanOnSquare(selectedSquare.getChessman());
				selectedSquare.removeChessmanFromSquare();
				boardSquare[0][5].setChessmanOnSquare(boardSquare[y][x].getChessman());
				boardSquare[y][x].removeChessmanFromSquare();
			} else if (selectedSquare.getChessman() instanceof King && selectedSquare.getChessman().isWhite()
					&& (x == 0 && y == 0)) {
				((King) selectedSquare.getChessman()).setIsMoved();
				((Rook) boardSquare[y][x].getChessman()).setIsMoved();
				boardSquare[0][2].setChessmanOnSquare(selectedSquare.getChessman());
				selectedSquare.removeChessmanFromSquare();
				boardSquare[0][3].setChessmanOnSquare(boardSquare[y][x].getChessman());
				boardSquare[y][x].removeChessmanFromSquare();
			} else {
				boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
			}
		} else {
			if (selectedSquare.getChessman() instanceof King && !selectedSquare.getChessman().isWhite()
					&& (x == 7 && y == 7)) {
				((King) selectedSquare.getChessman()).setIsMoved();
				((Rook) boardSquare[y][x].getChessman()).setIsMoved();
				boardSquare[7][6].setChessmanOnSquare(selectedSquare.getChessman());
				selectedSquare.removeChessmanFromSquare();
				boardSquare[7][5].setChessmanOnSquare(boardSquare[y][x].getChessman());
				boardSquare[y][x].removeChessmanFromSquare();
			} else if (selectedSquare.getChessman() instanceof King && !selectedSquare.getChessman().isWhite()
					&& (x == 0 && y == 7)) {
				((King) selectedSquare.getChessman()).setIsMoved();
				((Rook) boardSquare[y][x].getChessman()).setIsMoved();
				boardSquare[7][2].setChessmanOnSquare(selectedSquare.getChessman());
				selectedSquare.removeChessmanFromSquare();
				boardSquare[7][3].setChessmanOnSquare(boardSquare[y][x].getChessman());
				boardSquare[y][x].removeChessmanFromSquare();
			} else {
				boardSquare[y][x].setChessmanOnSquare(selectedSquare.getChessman());
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
//			setPawnOnBoard(ChessmanType.PAWN);
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
				if (coordinate != null)
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
			if (coordinate != null) {
				// �̵������ ��ǥ�� �������� ������
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

					boardSquare[coordinate.getY()][coordinate.getX()].setSquareEventColor();
				}
			}
		}
	}

	
	///////////////////////üũ����Ʈ////////////////////////////
	private void checkmateChecker(boolean isWhite, Map<Direction, Coordinate[]> moveableSquare) {
		System.out.println("üũ����Ʈ üĿ");
//		for (Direction direction : moveableSquare.keySet()) {
//			checkmateJudger(isWhite, moveableSquare.get(direction));
//		}
		for (Direction direction : moveableSquare.keySet()) {
			int count = 0;
			for (Coordinate coordinate : moveableSquare.get(direction)) {
				++count;
				if (coordinate != null) {
					// �̵������ ��ǥ�� �������� ������
					if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
							&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() != isWhite) {
						System.out.println("��ο� ���� �� ���� �־��!" + isWhite);
						if (!(selectedSquare.getChessman() instanceof Knight))
							break;
						// ���õ� ���� ����Ʈ�̰� �̵���ο� ������������
					} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
							&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite
							&& selectedSquare.getChessman() instanceof Knight) {
						if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)
							System.out.println("üũ����Ʈ!1");

					} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
							&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite) {

						if (selectedSquare.getChessman() instanceof Pawn)
							break;
						
						if(!(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)) {
							System.out.println("�տ� �⹰�� �ֳ׿�!2");
							break;
						} else if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) {
							System.out.println("üũ����Ʈ!3");
							System.out.println(direction.name());
							/*�׽�Ʈ ���� ����?*/
							switch(direction) {
							case UP : checkmateSquare = moveableSquare.get(Direction.UP); break;
							case DOWN : checkmateSquare = moveableSquare.get(Direction.DOWN); break;
							case LEFT :  checkmateSquare = moveableSquare.get(Direction.LEFT); break;
							case RIGHT :  checkmateSquare = moveableSquare.get(Direction.RIGHT); break;
							case UP_LEFT :  checkmateSquare = moveableSquare.get(Direction.UP_LEFT); break;
							case UP_RIGHT :  checkmateSquare = moveableSquare.get(Direction.UP_RIGHT); break;
							case DOWN_LEFT :  checkmateSquare = moveableSquare.get(Direction.DOWN_LEFT); break;
							case DOWN_RIGHT :  checkmateSquare = moveableSquare.get(Direction.DOWN_RIGHT); break;
								default : break; 
							}
							break;
						}

					} else {
						if (selectedSquare.getChessman() instanceof Pawn
								&& boardSquare[coordinate.getY()][coordinate.getX()].isContain())
							break;

					}
				}
			}
		}
		
	}

//	private void checkmateJudger(boolean isWhite, Coordinate[] moveableCoordinate) {
//		for (Coordinate coordinate : moveableCoordinate) {
//			if (coordinate != null) {
//				// �̵������ ��ǥ�� �������� ������
//				if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
//						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() != isWhite) {
//					System.out.println("��ο� ���� �� ���� �־��!" + isWhite);
//					if (!(selectedSquare.getChessman() instanceof Knight))
//						break;
//					// ���õ� ���� ����Ʈ�̰� �̵���ο� ������������
//				} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
//						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite
//						&& selectedSquare.getChessman() instanceof Knight) {
//					if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)
//						System.out.println("üũ����Ʈ!1");
//
//				} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
//						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite) {
//
//					if (selectedSquare.getChessman() instanceof Pawn)
//						break;
//					
//					if(!(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)) {
//						System.out.println("�տ� �⹰�� �ֳ׿�!2");
//						break;
//					} else if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) {
//						System.out.println("üũ����Ʈ!3");
//						break;
//					}
//
//				} else {
//					if (selectedSquare.getChessman() instanceof Pawn
//							&& boardSquare[coordinate.getY()][coordinate.getX()].isContain())
//						break;
//
//				}
//			}
//		}
//	}
}

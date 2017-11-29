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

// x축 y축 8칸의 square을 가진 Board의 생성 및 Board위에서 일어나는 이벤트들(체스말을 놓아준다던가 체스말이 죽어서 제거한다던가 체스말의 이동가능범위를 보여준다던가...)을
// 처리하는 역할을 할 것이다.
@SuppressWarnings("serial")
public class BoardPanel extends FlatPanel {
	// 체스 판의 하나하나의 square로써 체스말을 놓아준다던가 체스말을 제외해준다거나 이동가능범위를 표현해줄 최소단위의 칸이다.
	private BoardSquare[][] boardSquare = new BoardSquare[8][8];
	private BoardSquare selectedSquare = null;
	private Map<Direction, Coordinate[]> moveableSquare;
	private ArrayList<BoardSquare> kingCastlingSquare = new ArrayList<>();
	private ArrayList<BoardSquare> pawnAtackableSquare = new ArrayList<>();
	private BoardSquare enPassantSquare = null;
	private Coordinate[] checkmateSquare = null;
	boolean isWhite = true;

	// 8 X 8의 square를 가진 체스판을 만들어준다.
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

	// 입력된 무게와 부모의 크기만큼 영역을 차지하는 제약조건을 생성하여 반환한다.
	private LinearConstraints createMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}

	// 정해진 좌표의 하나의 칸을 생성해서 하나의 square가 배열로써 저장되어 좌표의 의미를 갖도록 해서 추후 클래스의 설명에 적혀있는
	// 이벤트를 처리할때
	// 좌표의 값을 이용해서 square에 이벤트를 처리 할 수 있도록 하려고한다.
	private BoardSquare createBoardSquare(int x, int y) {
		boardSquare[y][x] = new BoardSquare(
				(x + y) % 2 == 0 ? Theme.BOARD_DARK_SQUARE_COLOR : Theme.BOARD_LIGHT_SQUARE_COLOR);
		boardSquare[y][x].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Component component) {
				// 처음눌렀을때
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

					/* 원래있던 부분 */
					moveableSquare = MoveableRouteCalculator.selectChessman(boardSquare[y][x].getChessman(), x, y);
					/* 원래있던 부분 */
					/*체크메이트 테스팅 코드*/
					if(checkmateSquare != null) {
						for(Coordinate coordinate : checkmateSquare) {
							if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) break;
							boardSquare[coordinate.getY()][coordinate.getX()].setSquareCheckmateColor();
						}
//						boardSquare[y][x].setSquareEventColor();
					}
					/*체크메이트 테스팅 코드*/
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
					// 다시 눌렀을때
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
					// 이동경로로 이동했을 때
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
//						움직인 칸이 말을 가지고있을때 만 체크메이트 계산을 하도록 하는 것이다. 이유는 앙파상의 경우에는 클릭한 칸에는 이미 말이 없을것이기 때문이다.
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
						// System.out.println("폰 프로모션가능");
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
			// 만약에 폰의 이동이 기존위치에서 차가 2일때 만 앙파상 체크를 한다... 하면 괜찮을거같은ㄷ?
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
			// boardSquare[y][x-1] 또는 boardSquare[y][x+1] 있는게 검정폰이냐? 를 검사하고
			if (Coordinate.isValidate(x - 1, y))
				if ((boardSquare[y][x - 1].isContain() && boardSquare[y][x - 1].getChessman() instanceof Pawn
						&& !boardSquare[y][x - 1].getChessman().isWhite())) {
					enPassantSquare = boardSquare[y - 1][x];
				}
			// 맞으면 boardSquare[y-1][x]를 앙파상 스퀘어에 저장
			if (Coordinate.isValidate(x + 1, y))
				if ((boardSquare[y][x + 1].isContain() && boardSquare[y][x + 1].getChessman() instanceof Pawn
						&& !boardSquare[y][x + 1].getChessman().isWhite())) {
					enPassantSquare = boardSquare[y - 1][x];
				}

		} else {
			// boardSquare[y][x-1] 또는 boardSquare[y][x+1] 있는게 흰색폰이냐? 를 검사하고
			if (Coordinate.isValidate(x - 1, y))
				if ((boardSquare[y][x - 1].isContain() && boardSquare[y][x - 1].getChessman() instanceof Pawn
						&& boardSquare[y][x - 1].getChessman().isWhite())) {
					enPassantSquare = boardSquare[y + 1][x];
				}
			// 맞으면 boardSquare[y+1][x]를 앙파상 스퀘어에 저장
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

	// chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)의 square에
	// 올려준다.
	// 올려주는 역할을 BoardSquare class의 메서드를 이용해서 하는데 이를통해 원하는 로직을 따라서 체스말이 추가된다.
	public void setChessmanOnSquare(Chessman chessman, int x, int y) {
		boardSquare[y][x].setChessmanOnSquare(chessman);
	}

	// 모든 체스말을 보드위에 셋팅한다.
	private void setWholeChessmanOnBoard() {
		for (ChessmanType type : ChessmanType.values()) {
			setChessmanOnBoard(type);
		}
	}

	// 체스말의 타입별로 보드의 초기위치에 설정해주기위한 메서드인데 각 타입에 따라 보드위 초기위치에 설정해준다.
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

	// 초기 갯수가 1개인 말(킹, 퀸) 의 경우 enum의 ordinal이 0, 1인데 여기서 3을 더한 값이 실제 말이 놓일 x로써 의미를
	// 갖게 된다는 것을 알고
	// 이러한 규칙을 이용해서 초기 갯수가 1개인 말을 한꺼번에 처리해서 Square에 올려주게되었다.
	private void setSingleChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 7);
	}

	// 초기 갯수가 2개인 말(비숍, 나이트, 룩) 의 경우 enum의 ordinal이 2, 3, 4인데 여기서 3을 더한 값과 7에서 이 값을
	// 뺀 값이
	// 실제 말이 놓일 x로써 의미를 갖게 된다는 것을 알고 이러한 규칙을 이용해서 초기 갯수가 2개인 말을 한꺼번에 처리해서 Square에
	// 올려주게되었다.
	private void setPairChessmanOnBoard(ChessmanType type) {
		setChessmanOnSquare(type.createChessman(true), (7 - (type.ordinal() + 3)), 0);
		setChessmanOnSquare(type.createChessman(true), (type.ordinal() + 3), 0);
		setChessmanOnSquare(type.createChessman(false), (7 - (type.ordinal() + 3)), 7);
		setChessmanOnSquare(type.createChessman(false), (type.ordinal() + 3), 7);
	}

	// 폰들을 보드위 정해진(초기) 위치에 셋팅해주기위해서 작성하였다. 폰의 경우 초기 갯수만큼 반복해서 생성해서 Square위에 올려주는데
	// 흰색과 검정색이 대칭으로 갯수가 똑같음을 이용했다.
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
				// 이동경로의 좌표에 같은편이 있을때
				if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite) {

					if (!(selectedSquare.getChessman() instanceof Knight))
						break;
					// 선택된 말이 나이트이고 이동경로에 적군이있을때
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

	
	///////////////////////체크메이트////////////////////////////
	private void checkmateChecker(boolean isWhite, Map<Direction, Coordinate[]> moveableSquare) {
		System.out.println("체크메이트 체커");
//		for (Direction direction : moveableSquare.keySet()) {
//			checkmateJudger(isWhite, moveableSquare.get(direction));
//		}
		for (Direction direction : moveableSquare.keySet()) {
			int count = 0;
			for (Coordinate coordinate : moveableSquare.get(direction)) {
				++count;
				if (coordinate != null) {
					// 이동경로의 좌표에 같은편이 있을때
					if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
							&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() != isWhite) {
						System.out.println("경로에 같은 색 말이 있어요!" + isWhite);
						if (!(selectedSquare.getChessman() instanceof Knight))
							break;
						// 선택된 말이 나이트이고 이동경로에 적군이있을때
					} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
							&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite
							&& selectedSquare.getChessman() instanceof Knight) {
						if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)
							System.out.println("체크메이트!1");

					} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
							&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite) {

						if (selectedSquare.getChessman() instanceof Pawn)
							break;
						
						if(!(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)) {
							System.out.println("앞에 기물이 있네요!2");
							break;
						} else if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) {
							System.out.println("체크메이트!3");
							System.out.println(direction.name());
							/*테스트 문구 흠흠?*/
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
//				// 이동경로의 좌표에 같은편이 있을때
//				if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
//						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() != isWhite) {
//					System.out.println("경로에 같은 색 말이 있어요!" + isWhite);
//					if (!(selectedSquare.getChessman() instanceof Knight))
//						break;
//					// 선택된 말이 나이트이고 이동경로에 적군이있을때
//				} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
//						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite
//						&& selectedSquare.getChessman() instanceof Knight) {
//					if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)
//						System.out.println("체크메이트!1");
//
//				} else if (boardSquare[coordinate.getY()][coordinate.getX()].isContain()
//						&& boardSquare[coordinate.getY()][coordinate.getX()].getChessman().isWhite() == isWhite) {
//
//					if (selectedSquare.getChessman() instanceof Pawn)
//						break;
//					
//					if(!(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King)) {
//						System.out.println("앞에 기물이 있네요!2");
//						break;
//					} else if(boardSquare[coordinate.getY()][coordinate.getX()].getChessman() instanceof King) {
//						System.out.println("체크메이트!3");
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

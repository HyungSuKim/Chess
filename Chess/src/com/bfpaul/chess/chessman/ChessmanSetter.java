package com.bfpaul.chess.chessman;

import com.bfpaul.chess.board.BoardPanel;
// ü������ ���忡 �÷��ִ�(�������ִ�) ���� �����Ѵ�.
public class ChessmanSetter {
	
// �ʱ� ü������ �������� �����Ѵ�.
	public ChessmanSetter(BoardPanel board) {
		for (ChessmanType type : ChessmanType.values()) {
			setInitChessmanOnBoard(type, board);
		}
	}
//	Ÿ�Կ� �´� ���� ������ �ʱ� ��ġ�� �������ش�. 
	private void setInitChessmanOnBoard(ChessmanType type, BoardPanel board) {
		switch (type) {
		case KING:
			setInitKingOnBoard(type, board);
			break;
		case QUEEN:
			setInitQueenOnBoard(type, board);
			break;
		case BISHOP:
			setInitBishopOnBoard(type, board);
			break;
		case KNIGHT:
			setInitKnightOnBoard(type, board);
			break;
		case ROOK:
			setInitRookOnBoard(type, board);
			break;
		case PAWN:
			setInitPawnOnBoard(type, board);
			break;
		default:
		}
	}
//	�ʱ� ŷ�� ������ ������ ��ġ�� �������ش�.
	private void setInitKingOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 3, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 3, 7);
	}
//	�ʱ� ���� ������ ������ ��ġ�� �������ش�.
	private void setInitQueenOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 4, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 4, 7);
	}
//	�ʱ� ����� ������ ������ ��ġ�� �������ش�.
	private void setInitBishopOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 2, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 5, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 2, 7);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 5, 7);
	}
//	�ʱ� ����Ʈ�� ������ ������ ��ġ�� �������ش�.
	private void setInitKnightOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 1, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 6, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 1, 7);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 6, 7);
	}
//	�ʱ� ���� ������ ������ ��ġ�� �������ش�.
	private void setInitRookOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 0, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 7, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 0, 7);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 7, 7);
	}
//	�ʱ� ���� ������ ������ ��ġ�� �������ش�.
	private void setInitPawnOnBoard(ChessmanType type, BoardPanel board) {
		int initCount = type.getInitCount();
		for (int counter = 0; counter < initCount; counter++) {
			board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), counter, 1);
			board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), counter, 6);
		}
	}
}

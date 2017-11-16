package com.bfpaul.chess.chessman;

import com.bfpaul.chess.board.BoardPanel;
// 체스말을 보드에 올려주는(셋팅해주는) 것을 전담한다.
public class ChessmanSetter {
	
// 초기 체스말을 보드위에 셋팅한다.
	public ChessmanSetter(BoardPanel board) {
		for (ChessmanType type : ChessmanType.values()) {
			setInitChessmanOnBoard(type, board);
		}
	}
//	타입에 맞는 말을 보드의 초기 위치에 셋팅해준다. 
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
//	초기 킹을 보드위 정해진 위치에 셋팅해준다.
	private void setInitKingOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 3, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 3, 7);
	}
//	초기 퀸을 보드위 정해진 위치에 셋팅해준다.
	private void setInitQueenOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 4, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 4, 7);
	}
//	초기 비숍을 보드위 정해진 위치에 셋팅해준다.
	private void setInitBishopOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 2, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 5, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 2, 7);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 5, 7);
	}
//	초기 나이트를 보드위 정해진 위치에 셋팅해준다.
	private void setInitKnightOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 1, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 6, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 1, 7);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 6, 7);
	}
//	초기 룩을 보드위 정해진 위치에 셋팅해준다.
	private void setInitRookOnBoard(ChessmanType type, BoardPanel board) {
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 0, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), 7, 0);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 0, 7);
		board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), 7, 7);
	}
//	초기 폰을 보드위 정해진 위치에 셋팅해준다.
	private void setInitPawnOnBoard(ChessmanType type, BoardPanel board) {
		int initCount = type.getInitCount();
		for (int counter = 0; counter < initCount; counter++) {
			board.setChessmanOnSquare(ChessmanCreator.createChessman(type, true), counter, 1);
			board.setChessmanOnSquare(ChessmanCreator.createChessman(type, false), counter, 6);
		}
	}
}

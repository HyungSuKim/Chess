package com.bfpaul.chess.game.manager;

import com.bfpaul.chess.board.BoardView;
import com.bfpaul.chess.chessman.ChessmanType;

public class Manager {

	public BoardView board = new BoardView();

	public Manager() {
		createInitChessSet(true);
		createInitChessSet(false);
	}

	private void createInitChessSet(boolean isWhite) {
		ChessmanType[] chessmanType = ChessmanType.values();
		for (ChessmanType type : chessmanType) {
			switch(type) {
			case PAWN : createInitPawnOnBoard(type, isWhite);
			break;
			case ROOK : createInitRookOnBoard(type, isWhite);
			break;
			case KNIGHT : createInitKnightOnBoard(type, isWhite);
			break;
			case BISHOP : createInitBishopOnBoard(type, isWhite);
			break;
			case QUEEN : createInitQueenOnBoard(type, isWhite);
			break;
			case KING : createInitKingOnBoard(type, isWhite);
			break;
			default : createInitPawnOnBoard(type, isWhite);
			break;
			}
		}
	}

	private void createInitPawnOnBoard(ChessmanType type, boolean isWhite) {
		int pawnInitCount = type.getInitCount();
		for (int counter = 0; counter < pawnInitCount; counter++) {
			if (isWhite) {
				board.setChessmanOnSquare(type.createChessman(isWhite, type), counter, 1);
			} else {
				board.setChessmanOnSquare(type.createChessman(isWhite, type), counter, 6);
			}
		}
	}
	
	private void createInitRookOnBoard(ChessmanType type, boolean isWhite) {
		if(isWhite) {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 0, 0);
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 7, 0);
		} else {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 0, 7);
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 7, 7);
		}
	}
	
	private void createInitKnightOnBoard(ChessmanType type, boolean isWhite) {
		if(isWhite) {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 1, 0);
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 6, 0);
		} else {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 1, 7);
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 6, 7);
		}
	}
	
	private void createInitBishopOnBoard(ChessmanType type, boolean isWhite) {
		if(isWhite) {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 2, 0);
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 5, 0);
		} else {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 2, 7);
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 5, 7);
		}
	}
	
	private void createInitQueenOnBoard(ChessmanType type, boolean isWhite) {
		if(isWhite) {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 4, 0);
		} else {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 4, 7);
		}
	}
	
	private void createInitKingOnBoard(ChessmanType type, boolean isWhite) {
		if(isWhite) {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 3, 0);
		} else {
			board.setChessmanOnSquare(type.createChessman(isWhite, type), 3, 7);
		}
	}
}

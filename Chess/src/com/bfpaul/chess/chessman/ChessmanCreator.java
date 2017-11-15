package com.bfpaul.chess.chessman;

import com.bfpaul.chess.chessman.bishop.Bishop;
import com.bfpaul.chess.chessman.king.King;
import com.bfpaul.chess.chessman.knight.Knight;
import com.bfpaul.chess.chessman.pawn.Pawn;
import com.bfpaul.chess.chessman.queen.Queen;
import com.bfpaul.chess.chessman.rook.Rook;
// ü������ ������ �����Ѵ�. 
public class ChessmanCreator {
	
	private ChessmanCreator() {	}
//	���ǿ� �´� ü������ �����Ѵ�.
	static Chessman createChessman(ChessmanType type, boolean isWhite) {
		switch(type) {
		case KING : return new King(isWhite);
		case QUEEN : return new Queen(isWhite);
		case BISHOP : return new Bishop(isWhite);
		case KNIGHT : return new Knight(isWhite);
		case ROOK : return new Rook(isWhite);
		case PAWN : return new Pawn(isWhite);
		default : return new King(isWhite);
		}
	}
}

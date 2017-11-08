package com.bfpaul.chess.chessman;

import com.bfpaul.chess.chessman.bishop.Bishop;
import com.bfpaul.chess.chessman.king.King;
import com.bfpaul.chess.chessman.knight.Knight;
import com.bfpaul.chess.chessman.pawn.Pawn;
import com.bfpaul.chess.chessman.queen.Queen;
import com.bfpaul.chess.chessman.rook.Rook;
import com.mommoo.flat.image.FlatImagePanel;

public enum ChessmanType{
	KING(1),
	QUEEN(1),
	BISHOP(2),
	KNIGHT(2),
	ROOK(2),
	PAWN(8);
	
	private final int MAX_COUNT;
	
	private ChessmanType(int maxCount) {
		this.MAX_COUNT = maxCount;
	}
	
	public int getMaxCount() {
		return MAX_COUNT;
	}
	
	public FlatImagePanel createChessman(boolean isWhite) {
		switch(this) {
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

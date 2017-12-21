package com.bfpaul.renewal.chess.game;

import javax.swing.JDialog;

public class GameResultManager {
	private static int whiteCount = 0;
	private static int blackCount = 0;
	
	private GameResultManager() {	}
	
	public static JDialog checkmateManager(boolean isWhite) {
		return new GameResultView(isWhite, GameResultType.CHECKMATE);
	}
	
	public static JDialog stalemateManager() {
		return new GameResultView(true, GameResultType.STALEMATE);
	}
	
	public static void increaseCount(boolean isWhite) {
		if(isWhite) {
			whiteCount += 1;
		} else {
			blackCount += 1;
		}		
	}
	
	public static JDialog fiftyCountManager(boolean isWhite) {
		increaseCount(isWhite);
		
		if(whiteCount == 50 || blackCount == 50 ) {
			return new GameResultView(isWhite, GameResultType.FIFTY_COUNT);
		} else {
			return new JDialog();
		}
		
	}
	
	public static JDialog threeFoldReptitionManager() {
		return new GameResultView(true, GameResultType.THREE_FOLD_REPETITION);
	}
	
	public static JDialog resignManager(boolean isWhite) {
		return new GameResultView(isWhite, GameResultType.RESIGN);
	}
	
	public static JDialog timerOutManager(boolean isWhite) {
		return new GameResultView(isWhite, GameResultType.TIMER_OUT);
	}
}

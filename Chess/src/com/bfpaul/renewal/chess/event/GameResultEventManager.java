package com.bfpaul.renewal.chess.event;

import javax.swing.JDialog;

public class GameResultEventManager {
	private static int whiteCount = 0;
	private static int blackCount = 0;
	
	private GameResultEventManager() {	}
	
	public static JDialog checkmateManager(boolean isWhite) {
		return new GameResultEventView(isWhite, GameResultEventType.CHECKMATE);
	}
	
	public static JDialog stalemateManager() {
		return new GameResultEventView(true, GameResultEventType.STALEMATE);
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
			return new GameResultEventView(isWhite, GameResultEventType.FIFTY_COUNT);
		} else {
			return new JDialog();
		}
		
	}
	
	public static JDialog threeFoldReptitionManager() {
		return new GameResultEventView(true, GameResultEventType.THREE_FOLD_REPETITION);
	}
	
	public static JDialog resignManager(boolean isWhite) {
		return new GameResultEventView(isWhite, GameResultEventType.RESIGN);
	}
	
	public static JDialog timerOutManager(boolean isWhite) {
		return new GameResultEventView(isWhite, GameResultEventType.TIMER_OUT);
	}
}

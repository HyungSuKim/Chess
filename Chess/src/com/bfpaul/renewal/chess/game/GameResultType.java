package com.bfpaul.renewal.chess.game;

/**
 *  체스게임에서 승리/패배/무승부의 경우의 수는 6가지이다.
 *  이 GameResultType는 이 승리/패배/무승부의 6가지 경우를 표현하는 지표로써 필요하다.
 *  GameResultView와 연관되어 게임의 승리/패배/무승부의 각각의 경우를 표현하는데 지표로써 도움을 준다. 
 * 
 *  @see GameResultView
 *	@author 김형수
 */

public enum GameResultType {
	CHECKMATE("체크메이트"),
	STALEMATE("스테일메이트"),
	RESIGN("기권"),
	FIFTY_COUNT("50수 무승부"),
	THREE_FOLD_REPETITION("쓰리폴드 레피테이션"),
	TIMER_OUT("시간 초과");
	
	private final String RESULT;
	
	private GameResultType(String result) {
		this.RESULT = result;
	}
	
	public String getResult() {
		return RESULT;
	}
}
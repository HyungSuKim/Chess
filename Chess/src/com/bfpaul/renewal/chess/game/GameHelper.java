package com.bfpaul.renewal.chess.game;

import com.bfpaul.renewal.chess.board.BoardPanel;
import com.bfpaul.renewal.chess.chessman.ChessmanType;

/**
 * GameHelper 클래스는 게임을 진행하는데 도움을 주기위해서 필요한 클래스이다
 * GameFrame 생성시 생성되어 체스게임을 진행하기위해 필요한 구성요소인 
 * CurrentChessmanView, GameTimerView, BoardPanel의 관계를 맺어주기 위해서 필요하다.
 * 따라서 멤버변수로 CurrentChessmanView, GameTimerView를 가지고 있다. (수정 가능성 있음)
 * 
 * BoardPanel의 말이 잡히거나 / 폰이 프로모션 하는경우 CurrentChessmanView에 반영해야 하는데 
 * BoardPanel이 CurrentChessmanView를 직접 가지고 데이터를 변경하는 것은 맞지 않다고 생각했다.
 * 따라서 GameHelper의 메서드를 사용해서 CurrentChessmanView의 메서드에 접근해서
 * 체스말의 갯수를 줄이거나 늘리거나 할 수 있게 구성하였다.
 * 
 * GameTimerView 는 아직 미구현...
 * 
 * GameHelper는 게임진행에 도움을 주는 Wrapper Class라는 관점에서 작성하였다.
 * @author 김형수
 *
 */
public class GameHelper {
	private CurrentChessmanView currentChessmanView = new CurrentChessmanView();
	private BoardPanel boardPanel;
	
	public GameHelper(CurrentChessmanView currentChessmanView) {
		this.currentChessmanView = currentChessmanView;
	}
	
	public void decreaseCurrentChessmanCount(boolean isWhite, ChessmanType type) {
		currentChessmanView.decreaseCurrentChessmanCount(isWhite, type);
	}
	
	public void increaseCurrentChessmanCount(boolean isWhite, ChessmanType type) {
		currentChessmanView.increaseCurrentChessmanCount(isWhite, type);
	}
	
	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}
	
	public void changePhaseOnBoardPanel() {
		boardPanel.changePhase();
	}
	
	public boolean getPlayerColor() {
		return boardPanel.getPlayerColor();
	}
}

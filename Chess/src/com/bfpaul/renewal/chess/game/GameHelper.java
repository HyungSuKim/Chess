package com.bfpaul.renewal.chess.game;

import com.bfpaul.renewal.chess.board.BoardPanel;
import com.bfpaul.renewal.chess.chessman.ChessmanType;

/**
 * GameHelper Ŭ������ ������ �����ϴµ� ������ �ֱ����ؼ� �ʿ��� Ŭ�����̴�
 * GameFrame ������ �����Ǿ� ü�������� �����ϱ����� �ʿ��� ��������� 
 * CurrentChessmanView, GameTimerView, BoardPanel�� ���踦 �ξ��ֱ� ���ؼ� �ʿ��ϴ�.
 * ���� ��������� CurrentChessmanView, GameTimerView�� ������ �ִ�. (���� ���ɼ� ����)
 * 
 * BoardPanel�� ���� �����ų� / ���� ���θ�� �ϴ°�� CurrentChessmanView�� �ݿ��ؾ� �ϴµ� 
 * BoardPanel�� CurrentChessmanView�� ���� ������ �����͸� �����ϴ� ���� ���� �ʴٰ� �����ߴ�.
 * ���� GameHelper�� �޼��带 ����ؼ� CurrentChessmanView�� �޼��忡 �����ؼ�
 * ü������ ������ ���̰ų� �ø��ų� �� �� �ְ� �����Ͽ���.
 * 
 * GameTimerView �� ���� �̱���...
 * 
 * GameHelper�� �������࿡ ������ �ִ� Wrapper Class��� �������� �ۼ��Ͽ���.
 * @author ������
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

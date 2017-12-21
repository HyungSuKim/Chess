package com.bfpaul.renewal.chess.game;

/**
 *  ü�����ӿ��� �¸�/�й�/���º��� ����� ���� 6�����̴�.
 *  �� GameResultType�� �� �¸�/�й�/���º��� 6���� ��츦 ǥ���ϴ� ��ǥ�ν� �ʿ��ϴ�.
 *  GameResultView�� �����Ǿ� ������ �¸�/�й�/���º��� ������ ��츦 ǥ���ϴµ� ��ǥ�ν� ������ �ش�. 
 * 
 *  @see GameResultView
 *	@author ������
 */

public enum GameResultType {
	CHECKMATE("üũ����Ʈ"),
	STALEMATE("�����ϸ���Ʈ"),
	RESIGN("���"),
	FIFTY_COUNT("50�� ���º�"),
	THREE_FOLD_REPETITION("�������� �������̼�"),
	TIMER_OUT("�ð� �ʰ�");
	
	private final String RESULT;
	
	private GameResultType(String result) {
		this.RESULT = result;
	}
	
	public String getResult() {
		return RESULT;
	}
}
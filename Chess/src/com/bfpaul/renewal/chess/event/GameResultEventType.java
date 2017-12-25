package com.bfpaul.renewal.chess.event;

/**
 *  ü�����ӿ��� �¸�/�й�/���º��� ����� ���� 6�����̴�.
 *  �� GameResultType�� �� �¸�/�й�/���º��� 6���� ��츦 ǥ���ϴ� ��ǥ�ν� �ʿ��ϴ�.
 *  GameResultView�� �����Ǿ� ������ �¸�/�й�/���º��� ������ ��츦 ǥ���ϴµ� ��ǥ�ν� ������ �ش�. 
 * 
 *  @see GameResultEventView
 *	@author ������
 */

public enum GameResultEventType {
	CHECKMATE("üũ����Ʈ"),
	STALEMATE("�����ϸ���Ʈ"),
	RESIGN("���"),
	FIFTY_COUNT("50�� ���º�"),
	THREE_FOLD_REPETITION("�������� �������̼�"),
	TIMER_OUT("�ð� �ʰ�");
	
	private final String RESULT;
	
	private GameResultEventType(String result) {
		this.RESULT = result;
	}
	
	public String getResult() {
		return RESULT;
	}
}
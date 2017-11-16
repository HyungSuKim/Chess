package com.bfpaul.chess.board;

import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
// ü������ �ϳ��� �׸�ĭ���ν� ü������ �̵���γ� ü������ ���ų� ġ��ų� �ϴ� �̺�Ʈ�� ǥ���Ǵ� ���� ���������� ĭ�̴�.
@SuppressWarnings("serial")
public class BoardSquare extends FlatPanel {
//	ü������ �������ִ��� �ƴ���
	private boolean isContain;
	
	BoardSquare() {	}
	
//	�������ִ� ü������ ������ ü������ �׸�ĭ���� �÷��ش�.
	void setChessmanOnSquare(Chessman chessman) {
		if(!isContain) {
			isContain = true;
			add(chessman, new LinearConstraints(1, LinearSpace.MATCH_PARENT));	
		}
	}
//	ü������ ������������ ü������ �׸�ĭ������ �����Ѵ�.
	void removeChessmanFromSquare() {
		if(isContain) {
			isContain = false;
			remove(0);
		}
	}
	
}

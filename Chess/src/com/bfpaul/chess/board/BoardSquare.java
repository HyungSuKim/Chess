package com.bfpaul.chess.board;

import java.awt.Color;

import com.bfpaul.chess.Theme;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// ü������ �ϳ��� �׸�ĭ���ν� ü������ �̵���γ� ü������ ���ų� ġ��ų� �ϴ� �̺�Ʈ�� ǥ���Ǵ� ���� ���������� ĭ�̴�.
@SuppressWarnings("serial")
public class BoardSquare extends FlatPanel {
	// ü������ �������ִ��� �ƴ���
	private boolean isContain;
	private Color originalColor;

	BoardSquare() {	}

	// �������ִ� ü������ ������ ü������ �׸�ĭ���� �÷��ش�.
	void setChessmanOnSquare(Chessman chessman) {
		if (!isContain) {
			isContain = true;
			setEnabled(isContain);
			add(chessman, new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		}
	}
	
	void setOriginalColor() {
		originalColor = getBackground();
		setEnabled(isContain);
	}
	
	Chessman getChessman() {
		return (Chessman)getComponent(0);
	}
	
	boolean isWhite() {
		return ((Chessman)getComponent(0)).getIsWhite();
	}
	
	void setSquareOriginalColor() {
		setBackground(originalColor);
		setAlpha(1.0f);
	}
	
	void setSquareEventColor() {
		setBackground(Theme.LIGHT_BLUE_COLOR);
		setAlpha(0.6f);
	}

	// ü������ ������������ ü������ �׸�ĭ������ �����Ѵ�.
	void removeChessmanFromSquare() {
		if (isContain) {
			isContain = false;
			setEnabled(isContain);
			remove(0);
		}
	}
}

package com.bfpaul.chess.board;

import java.awt.Color;

import com.bfpaul.chess.Theme;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// ü������ �ϳ��� �׸�ĭ���ν� ü������ �̵���γ� ü������ ���ų� ġ��ų� �ϴ� �̺�Ʈ�� ǥ���Ǵ� ���� ���������� ĭ�̴�.
@SuppressWarnings("serial")
public class BoardSquare extends FlatPanel {
	private final Color originalColor;

	BoardSquare(Color originalColor) {	
		this.originalColor = originalColor;
		setEnableClickEvent(false);
		setLayout(new LinearLayout(0));
		setOpaque(true);
	}

	// �������ִ� ü������ ������ ü������ �׸�ĭ���� �÷��ش�.
	void setChessmanOnSquare(Chessman chessman) {
		setEnableClickEvent(true);
		add(chessman, new LinearConstraints(1, LinearSpace.MATCH_PARENT));
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
		setEnableClickEvent(false);
		if(getComponentCount() >0) remove(0);
	}
	
	boolean isContain() {
		return isEnabled();
	}
	
	private void setEnableClickEvent(boolean enable) {
		setEnabled(enable);
	}
}

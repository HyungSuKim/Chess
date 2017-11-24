package com.bfpaul.renewal.chess.board;

import java.awt.Color;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;

// ü������ �ϳ��� �׸�ĭ���ν� ü������ �̵���γ� ü������ ���ų� ġ��ų� �ϴ� �̺�Ʈ�� ǥ���Ǵ� ���� ���������� ĭ�̴�.
@SuppressWarnings("serial")
public class BoardSquare extends FlatImagePanel {
	private final Color originalColor;
	private Chessman chessman;
	private boolean isContain;

	BoardSquare(Color originalColor) {	
		this.originalColor = originalColor;
		setEnableClickEvent(false);
		setLayout(new LinearLayout(0));
		setOpaque(true);
		setBackground(originalColor);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	// �������ִ� ü������ ������ ü������ �׸�ĭ���� �÷��ش�.
	void setChessmanOnSquare(Chessman chessman) {
		this.chessman = chessman;
		isContain = true;
		setEnableClickEvent(true);
		setImage(chessman.getChessmanImage(), ImageOption.MATCH_PARENT);
	}

	
	void setSquareOriginalColor() {
		setBackground(originalColor);
		setAlpha(1.0f);
		if(isContain) {
			setEnableClickEvent(true);
		} else {
			setEnableClickEvent(false);
		}
	}
	
	void setSquareEventColor() {
		setBackground(Theme.LIGHT_BLUE_COLOR);
		setEnableClickEvent(true);
		setAlpha(0.6f);
	}

	// ü������ ������������ ü������ �׸�ĭ������ �����Ѵ�.
	void removeChessmanFromSquare() {
		chessman = null;
		isContain = false;
		setImage(null); 
	}
	
	boolean isContain() {
		return isContain;
	}
	
	private void setEnableClickEvent(boolean enable) {
		setEnabled(enable);
	}
	
	Chessman getChessman() {
		return chessman;
	}
}

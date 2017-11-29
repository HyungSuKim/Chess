package com.bfpaul.renewal.chess.board;

import java.awt.Color;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.King;
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
	public void setChessmanOnSquare(Chessman chessman) {
		if(getBackground().equals(Color.ORANGE) && chessman instanceof King) {
			System.out.println("üũ����Ʈ �� ĭ���� �ű�� �����");
		} else {
			this.chessman = chessman;
			isContain = true;
			setEnableClickEvent(true);
			setImage(chessman.getChessmanImage(), ImageOption.MATCH_PARENT);
		}
	}

	void setSquareOriginalColor() {
		setBackground(originalColor);
		setAlpha(1.0f);
		if (isContain) {
			setEnableClickEvent(true);
		} else {
			setEnableClickEvent(false);
		}
	}

	void setSquareEventColor() {
		if(getBackground().equals(Color.ORANGE)) {
			setBackground(Color.ORANGE);
			setEnableClickEvent(true);
		} else {
			setBackground(Theme.LIGHT_BLUE_COLOR);
			setEnableClickEvent(true);
			setAlpha(0.6f);
		}
	}

	void setSquareAttackableColor() {
		setBackground(Color.RED);
		setEnableClickEvent(true);
		setAlpha(0.6f);
	}
	
	void setSquareCheckmateColor() {
		setBackground(Color.ORANGE);
		setEnableClickEvent(false);
		setAlpha(0.6f);
	}
	
	void setSquareCastlingColor() {
		setBackground(Color.YELLOW);
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

	void setEnableClickEvent(boolean enable) {
		setEnabled(enable);
	}

	public Chessman getChessman() {
		return chessman;
	}
}

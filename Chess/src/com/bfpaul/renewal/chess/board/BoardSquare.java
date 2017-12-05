package com.bfpaul.renewal.chess.board;

import java.awt.Color;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.King;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;

// �� BoardSquare Ŭ������ ü�����ӿ� �ʿ��� ü������ �ϳ��� ĭ���ν� ������ �ϰ��� �������.
// ���� BoardPanel���� BoardSquare�� �и� ��Ų ������ 
// 1. BoardSquare�� �������� ���¸� BoardPanel���� ������ ���� �ϴ� ���� BoardPanel�� �ӹ�/������ ���߽�Ų�ٰ� �����Ͽ���.
//	   ���� ������ �̸� Ŭ������ �и��Ͽ� ĭ���� ���� �ϳ��� BoardSquare�ν� ���� �ϵ��� �ϱ� ���� �и��Ͽ���.  
//    (ü������ �����ִ� / �������� �ʴ� / �������̴� / ��ο���̴� / �̵� �� �� �ִ� ĭ�̴� / ���� �� �� �ִ� ĭ�̴� / üũ�� ������ ĭ�̴� )
// 2. BoardSquare�� �ϳ��� ��ν� �׸��� �����ͷν� ���� �ϱ⸦ ����Ͽ���. 
//	 ���� ������ ���ҵ��� �ַ� �� �ν� BoardSqure�� ������ ������ ���̴�. �̷��� ���� ������ �ϱ� ���ؼ��� BoardPanel���� BoardSquare�� 
//   �����ͷ� ������ �ִ� chessman, isContain�� �̿��Ͽ� �����͸� ó���ϰ� ��� ǥ���� �ִ� ������ �ʿ��ϴٰ� �Ǵ��Ͽ���.
// final Color ORIGINAL_COLOR; -> BoardSquare�� ź�� �� �� �����Ǵ� �������� �̺�Ʈ �߻� ���� ������ �������� ���ư��� ���� �ʿ��ϴٰ� �Ǵ��Ͽ���.
// Chessman chessman; -> BoardSquare�� ���� �ִ� ���� �����͸� ���� �ִٰ� ���� ���� �̺�Ʈ�� ó���ϱ� ���ؼ� �ʿ��ϴٰ� �Ǵ��Ͽ���.
//	boolean isContain; -> BoardSquare�� ���� �����ִ��� �ƴ����� �˷��ִ� �����̴�. �ش� ������ ���Ÿ� �� �� ������̴�. ������ chessman ������ ���� �����ִ���
//  					�ƴ����� �ߺ��ؼ� ǥ�� �� �� �ִٰ� �ǴܵǱ� �����̴�.
@SuppressWarnings("serial")
public class BoardSquare extends FlatImagePanel {
	private final Color ORIGINAL_COLOR;
	private Chessman chessman;
	private boolean isContain;

	BoardSquare(Color originalColor) {
		ORIGINAL_COLOR = originalColor;
		
		setLayout(new LinearLayout(0));
		setBackground(ORIGINAL_COLOR);
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		setEnableClickEvent(false);
	}

	// ü������ �׸�ĭ���� �÷��ش�.
	public void setChessmanOnSquare(Chessman chessman) {
//		���� üũ����Ʈ ���� ��ηδ� �̵����� ���Ѵ�.
		if(getBackground().equals(Color.GREEN) && chessman instanceof King) {
		} else {
			this.chessman = chessman;
			isContain = true;
			setEnableClickEvent(true);
			setImage(chessman.getChessmanImage(), ImageOption.MATCH_PARENT);
		}
	}
// ĭ�� ���� ������� �����ִµ� ���� ���� ���� Ŭ���� �Ǹ� �ȵȴٰ� �����߱� ������ ���� ������ Ŭ�� �����ϰ��ϰ� �ƴϸ� �Ұ����ϰ� �ϵ��� �ߴ�. 
	void setSquareOriginalColor() {
		setBackground(ORIGINAL_COLOR);
		setAlpha(1.0f);
		if (isContain) {
			setEnableClickEvent(true);
		} else {
			setEnableClickEvent(false);
		}
	}
	
// �ش� ĭ�� �ܼ� �̵��� ������ ĭ�̶�� ǥ�����ش�.
//	�̶� üũ����Ʈ ������� Ȯ���ϴ� ������ �ٸ����� �̵���ΰ�
//	üũ����Ʈ ��ο� ��ĥ��� üũ����Ʈ ��� ���� �̵��Ͽ� ��θ� �����ϴ� ���� �����߱� �����̴�.
	void setSquareMoveableColor() {
		if(getBackground().equals(Color.GREEN)) {
			setBackground(Color.GREEN);
			setEnableClickEvent(true);
			setAlpha(0.6f);
		} else {
			setBackground(Theme.LIGHT_BLUE_COLOR);
			setEnableClickEvent(true);
			setAlpha(0.6f);
		}
	}
//	�ش� ĭ�� ������ �����ϴٰ� ǥ�����ش�
	void setSquareAttackableColor() {
		setBackground(Color.RED);
		setEnableClickEvent(true);
		setAlpha(0.6f);
	}
//	�ش� ĭ�� üũ����Ʈ ������� ǥ�����ش�.	
	void setSquareCheckmateColor() {
		setBackground(Color.GREEN);
		setEnableClickEvent(false);
		setAlpha(0.6f);
	}
//	�ش� ĭ�� ĳ������ �����ϴٰ� ǥ�����ش�
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
//	ĭ ���� ü������ �ִ��� �������� ��ȯ�Ѵ�.
	boolean isContain() {
		return isContain;
	}

	void setEnableClickEvent(boolean enable) {
		setEnabled(enable);
	}
//	ĭ�� �������ִ� ü������ ��ȯ�ϴ� �޼���ν� ü������ ���� �̺�Ʈ��(�̵� ����)�� ó���Ҷ� ����Ѵ�.
//	��εǴ� �κ��� chessman�� �������� null�� ��ȯ�ϴµ�
//	�׷��ٺ��� BoardPanel���� �̸� �˻��Ҷ� 1. isContain�� true����, 2. chessman�� null�� �ƴ���
//	�Ѵܰ� �� ���ư��� 3. ü������ ŷ���� ������ ���... �⺻ 3�ܰ��� ���˻縦 �����ؾߵǼ� �̸� ó���ϴµ� ����� �ȴ�.
	public Chessman getChessman() {
		return chessman;
	}
}

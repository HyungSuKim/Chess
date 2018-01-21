package com.bfpaul.renewal.chess.board;

import java.awt.Color;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.ChessmanType;
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

	BoardSquare(Color originalColor) {
		ORIGINAL_COLOR = originalColor;

		setLayout(new LinearLayout(0));
		setBackground(ORIGINAL_COLOR);
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	// ü������ �׸�ĭ���� �÷��ش�.
	public void setChessmanOnSquare(Chessman chessman) {
		this.chessman = chessman;
		setImage(chessman.getChessmanImage(), ImageOption.MATCH_PARENT);
	}

	// ĭ�� ���� ������� �����ִµ� ���� ���� ���� Ŭ���� �Ǹ� �ȵȴٰ� �����߱� ������ ���� ������ Ŭ�� �����ϰ��ϰ� �ƴϸ� �Ұ����ϰ� �ϵ���
	// �ߴ�.
	void setSquareOriginalColor() {
		setBackground(ORIGINAL_COLOR);
		setAlpha(1.0f);
	}

	// �ش� ĭ�� �ܼ� �̵��� ������ ĭ�̶�� ǥ�����ش�.
	// �̶� üũ����Ʈ ������� Ȯ���ϴ� ������ �ٸ����� �̵���ΰ�
	// üũ����Ʈ ��ο� ��ĥ��� üũ����Ʈ ��� ���� �̵��Ͽ� ��θ� �����ϴ� ���� �����߱� �����̴�.
	void setSquareMoveableColor() {
		setBackgroundColorWithAlpha(Theme.LIGHT_BLUE_COLOR);
	}

	// �ش� ĭ�� ������ �����ϴٰ� ǥ�����ش�
	void setSquareAttackableColor() {
		setBackground(Color.RED);
	}

	// �ش� ĭ�� üũ����Ʈ ������� ǥ�����ش�.
	void setSquareCheckColor() {
		setBackgroundColorWithAlpha(Color.GREEN);
	}
	
	// �ش� ĭ�� ������ �� �ִ� üũ����Ʈ ĭ���� ǥ�����ش�.
	void setSquareMoveableCheckColor() {
		setBackgroundColorWithAlpha(Color.GREEN);
	}

	// �ش� ĭ�� ĳ������ �����ϴٰ� ǥ�����ش�
	void setSquareCastlingColor() {
		setBackgroundColorWithAlpha(Color.YELLOW);
	}

	// ü������ ������������ ü������ �׸�ĭ������ �����Ѵ�.
	void removeChessmanFromSquare() {
		chessman = null;
		setImage(null);
	}
	
	BoardSquareType getType() {
		if(chessman == null) return BoardSquareType.EMPTY;
		
		if(chessman.isWhite()) {
			return BoardSquareType.CONTAIN_CHESSMAN_WHITE;
		} else {
			return BoardSquareType.CONTAIN_CHESSMAN_BLACK;
		}
	}
	
	ChessmanType getChessmanType() {
		return chessman.getChessmanType();
	}

	void setEnableClickEvent(boolean enable) {
		setEnabled(enable);
	}

	// ĭ�� �������ִ� ü������ ��ȯ�ϴ� �޼���ν� ü������ ���� �̺�Ʈ��(�̵� ����)�� ó���Ҷ� ����Ѵ�.
	// ��εǴ� �κ��� chessman�� �������� null�� ��ȯ�ϴµ�
	// �׷��ٺ��� BoardPanel���� �̸� �˻��Ҷ� 1. isContain�� true����, 2. chessman�� null�� �ƴ���
	// �Ѵܰ� �� ���ư��� 3. ü������ ŷ���� ������ ���... �⺻ 3�ܰ��� ���˻縦 �����ؾߵǼ� �̸� ó���ϴµ� ����� �ȴ�.
	public Chessman getChessman() {
		return chessman;
	}
	
	// TODO : repaint ����
	private void setBackgroundColorWithAlpha(Color color) {
		int red = (color.getRed() + ORIGINAL_COLOR.getRed()) / 2;
		int green = (color.getGreen() + ORIGINAL_COLOR.getGreen()) / 2;
		int blue = (color.getBlue() + ORIGINAL_COLOR.getBlue()) / 2;
		setBackground(new Color(red, green, blue));
	}
}

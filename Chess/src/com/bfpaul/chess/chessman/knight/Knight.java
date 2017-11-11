package com.bfpaul.chess.chessman.knight;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� Knight�� ���� ���ְ� �� Knight�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Knight extends Chessman {
//	����Ʈ�� ��� 2ĭ �������� �����̰� 1ĭ �밢������ �����δ� ��� �������� 3ĭ�� �����ϼ� ������ ����ν� ǥ�����־���.
	private final int MOVEABLE_SQUARE = 3;
	
//	����Ʈ�� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����Ʈ�̹����� �����ͼ� ��������ش�.
	public Knight(boolean isWhite) {
//	�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ����
		super(isWhite);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_KNIGHT, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_KNIGHT, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
//	����Ʈ�� ������ �� �ִ� ĭ���� ��ȯ�ϴ� �޼���
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
}

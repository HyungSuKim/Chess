package com.bfpaul.chess.chessman.knight;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� Knight�� ���� ���ְ� �� Knight�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Knight extends Chessman {

//	����Ʈ�� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����Ʈ�̹����� �����ͼ� ��������ش�.
	public Knight(boolean isWhite) {
//	�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ���� ����Ʈ�� ��� 2ĭ �������� �����̰� 1ĭ �밢������ �����δ� ���
//	�������� 3ĭ�� �����ϼ� ������ ����Ŭ������ MOVEABLE_SQUARE�� 3���� �ʱ�ȭ ���־���.
		super(isWhite, 3);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_KNIGHT, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_KNIGHT, ImageOption.MATCH_PARENT);
		}
	}

}

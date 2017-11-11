package com.bfpaul.chess.chessman.bishop;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� bishop�� ���ְ� �� bishop�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
	
//	����� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����̹����� �����ͼ� ��������ش�.
	public Bishop(boolean isWhite) {
//	�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� �� ����� �̵������� square�� ������ �����Ѵ�.
//	����� �밢������ �̵��ϱ�� ������ �ִ�� ������ 7ĭ�� ������ �� �ִٴ� �������� 7�� ������ �����ڿ� �Ķ���ͷ� �����Ͽ���.
		super(isWhite, 7);
		
		if(isWhite) {
			setImage(Images.WHITE_BISHOP, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_BISHOP, ImageOption.MATCH_PARENT);
		}
	}
}

package com.bfpaul.chess.chessman.queen;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman ��Ű�� ������ Queen�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Queen extends Chessman {
	
//	���� �����ڷν� ����̸� ��� �� �̹����� ���� ���� �����ϰ� �ƴϸ� ���� �� �̹����� ���� ���� �����Ѵ�.
	public Queen(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ���� ���� ĭ�� ���� Ȥ�� �밢������ �̵��Ҽ��ִµ�
//		�̶� ���� �ִ�� �̵������� ĭ���� 7�̶� �Ǵ��ؼ� ������ MOVEABLE_SQUARE�� 7�� �ʱ�ȭ���־���.
		super(isWhite, 7);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_QUEEN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_QUEEN, ImageOption.MATCH_PARENT);
		}
	}
}

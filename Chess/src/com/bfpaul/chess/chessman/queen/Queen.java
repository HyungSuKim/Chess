package com.bfpaul.chess.chessman.queen;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman ��Ű�� ������ Queen�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Queen extends Chessman {
//	���� ĭ�� ���� Ȥ�� �밢������ �̵��Ҽ��ִµ� �̶� ���� �ִ�� �̵������� ĭ���� 7�̶� �Ǵ��ؼ� ����ν� 7�� ���������.
	private final int MOVEABLE_SQUARE = 7;
	
//	���� �����ڷν� ����̸� ��� �� �̹����� ���� ���� �����ϰ� �ƴϸ� ���� �� �̹����� ���� ���� �����Ѵ�.
	public Queen(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ����
		super(isWhite);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_QUEEN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_QUEEN, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
//	������ �� �ִ� ĭ ���� ��ȯ�ϴ� �޼���
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
}

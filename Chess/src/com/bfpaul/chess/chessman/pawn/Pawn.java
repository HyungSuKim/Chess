package com.bfpaul.chess.chessman.pawn;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
// chessman ��Ű�� ������ pawn�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Pawn extends Chessman {

//	���� ���������� �ƴ��� ������ �������ִ� ����
	private boolean isMoved;
	
//	���� �����ڷν� ����̸� ��� �� �̹����� ���� ���� �����ϰ� �ƴϸ� ���� �� �̹����� ���� ���� �����Ѵ�.
	public Pawn(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ����
//		���� ù��° �������� �����ϰ� 1�� �����ϼ� �����Ƿ� ����Ŭ������ MOVEABLE_SQUARE�� 1�� �ʱ�ȭ���־���.
		super(isWhite, 1);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_PAWN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_PAWN, ImageOption.MATCH_PARENT);
		}
	}

//	���� ������ �� �ִ� square�� ���� ��ȯ�ϴ� �޼���ν� ����Ŭ������ getMoveableSquare �޼��带 �������̵��ؼ� �����ߴ�. 
//	���࿡ �������� �������� ù�����ӿ��� �����ϼ� �ִ� ĭ���� ��ȯ�ϰ� ù �������� �־��ٸ� �⺻���� �����ϼ� �ִ� 1�� ��ȯ���ش�.
	@Override
	public int getMoveableSquare() {
		if(!isMoved) {
			return super.getMoveableSquare() * 2;
		} else {
			return super.getMoveableSquare();
		}
	}
	
//	ù ������ ���� ���������� �������ֱ����� �޼���
	public void setIsMoved() {
		isMoved = true;
	}
	
//	isMoved�� ��ȯ�޾Ƽ� �������� �־����� �������� Ȯ���ϱ� ���� �޼���
	public boolean getIsMoved() {
		return isMoved;
	}
}
package com.bfpaul.chess.chessman.rook;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.image.ImageOption;
//chessman ��Ű�� ������ Rook�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Rook extends Chessman {
//	���� ��� ��������(�����¿�) �ִ� 7ĭ�� �����ϼ� �ִٴ� �������� ����� ���
	final int MOVEABLE_SQUARE = 7;
//	rook�� ĳ����(�� ŷ �Ѵ� ������ ���� ����ߵ�)�� �����ϱ� ���� ������ �־�� �� �Ӽ��̶� �ǴܵǾ� ������ ����(�⺻�� false)
	private boolean isMoved;
//	���� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ���̹����� �����ͼ� ��������ش�.
	public Rook(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ����
		super(isWhite);
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_ROOK, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_ROOK, ImageOption.MATCH_PARENT);
		}
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
//	�����ϼ� �ִ� ĭ�� ���� �������ִ� get�޼���
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
//	�������� �߻������� ���������� ǥ���ϱ����� �޼���
	public void setIsMoved() {
		isMoved = true;
	}
//	���������� �ƴ��� ��ȯ�ϴ� �޼���
	public boolean getIsMoved() {
		return isMoved;
	}

}

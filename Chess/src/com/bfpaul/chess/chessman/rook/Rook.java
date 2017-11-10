package com.bfpaul.chess.chessman.rook;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
//chessman ��Ű�� ������ Rook�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Rook extends FlatImagePanel {
//	���� ��� ��������(�����¿�) �ִ� 7ĭ�� �����ϼ� �ִٴ� �������� ����� ���
	final int MOVEABLE_SQUARE = 7;
//	���� ������� �ƴ����� ��Ÿ���ִ� ����
	private boolean isWhite;
//	rook�� ĳ����(�� ŷ �Ѵ� ������ ���� ����ߵ�)�� �����ϱ� ���� ������ �־�� �� �Ӽ��̶� �ǴܵǾ� ������ ����(�⺻�� false)
	private boolean isMoved;
//	���� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ���̹����� �����ͼ� ��������ش�.
	public Rook(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_ROOK, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_ROOK, ImageOption.MATCH_PARENT);
		}
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
//	���� ������� �ƴ��� Ȯ���ϱ� ���ؼ� isWhite�� ��ȯ�޾Ƽ� Ȯ���Ѵ�.
	public boolean getIsWhite() {
		return isWhite;
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

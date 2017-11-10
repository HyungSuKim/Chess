package com.bfpaul.chess.chessman.king;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� King�� ���� ���ְ� �� King�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class King extends FlatImagePanel {
//	���� ��� ��������(�����¿�,�밢��) 1ĭ�� �����ϼ� �ִٴ� �������� ����� ���
	final int MOVEABLE_SQUARE = 1;
//	���� ������� �ƴ����� ��Ÿ���ִ� ����
	private boolean isWhite;
//	���� king�� rook�� ĳ����(�Ѵ� ������ ���� ����ߵ�)�� �����ϱ� ���� ������ �־�� �� �Ӽ��̶� �ǴܵǾ� ������ ����(�⺻�� false)
	private boolean isMoved;
	
//	ŷ�� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ŷ�̹����� �����ͼ� ��������ش�.
	public King(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_KING, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_KING, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	�����ϼ� �ִ� ĭ�� ���� �������ִ� get�޼���
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
	
//	���� ������� �ƴ��� Ȯ���ϱ� ���ؼ� isWhite�� ��ȯ�޾Ƽ� Ȯ���Ѵ�.
	public boolean getIsWhite() {
		return isWhite;
	}
	
//	�������� �߻������� ���������� ǥ���ϱ����� �޼���
	public void setIsMoved() {
		isMoved = true;
	}
	
//	���������� �ƴ��� �˼��ִ� �޼���
	public boolean getIsMoved() {
		return isMoved;
	}
}

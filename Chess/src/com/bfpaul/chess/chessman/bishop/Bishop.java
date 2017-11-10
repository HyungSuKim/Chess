package com.bfpaul.chess.chessman.bishop;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� bishop�� ���ְ� �� bishop�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Bishop extends FlatImagePanel {
//	����� �̵������� square�� ������ ��Ÿ�� ����̴�. ����� �밢������ �̵��ϴ��� �ƴ�����
//	��� ��ü�� �˾ƾ� �� �ʿ�� ���ٰ� �Ǵ��Ͽ���. �� ����Ǿ��� �ִ� 7ĭ�� ������ ������ �� �ִٴ� �������� �ۼ��� �����̴�.
	private final int MOVEABLE_SQUARE = 7;
//	����� �Ӽ��� ��������� �ƴ����� ������ �ֱ����ؼ� �ۼ��� ��������ε� ���� �������� ���� �ľ��Ҷ��� ������� ���� �����Ҷ� �������ؼ� �ۼ��ߴ�. 
	private boolean isWhite;
	
//	����� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����̹����� �����ͼ� ��������ش�.
	public Bishop(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		if(isWhite) {
			setImage(Images.WHITE_BISHOP, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_BISHOP, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	������ �� �ִ� ĭ ���� �������� �޼����̴�.
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
	
//	������� �ƴ����� �������� �޼����̴�.
	public boolean getIsWhite() {
		return isWhite;
	}
	
}

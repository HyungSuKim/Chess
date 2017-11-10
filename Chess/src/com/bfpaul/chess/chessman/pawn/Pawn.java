package com.bfpaul.chess.chessman.pawn;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Images;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
// chessman ��Ű�� ������ pawn�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Pawn extends FlatImagePanel {
//	ù �������� �����ϰ� ���� �����ϼ� �ִ� ĭ���� ��Ÿ���� ���, �̴� ���ݵ� �밢�� 1ĭ �̵����� �����ؼ� 1�� �������־���. 
	private final int MOVEABLE_SQUARE = 1;
//	ù �����ӿ��� �����ϼ� �ִ� ĭ���� 2ĭ�̹Ƿ� �̸� ��Ÿ�������Ͽ� ������ ���
	private final int FIRST_MOVEABLE_SQUARE = 2;
	
//	���� ������� �ƴ��� ������ �������ִ� ����
	private boolean isWhite;
//	���� ���������� �ƴ��� ������ �������ִ� ����
	private boolean isMoved;
	
//	���� �����ڷν� ����̸� ��� �� �̹����� ���� ���� �����ϰ� �ƴϸ� ���� �� �̹����� ���� ���� �����Ѵ�.
	public Pawn(boolean isWhite) {
		
		this.isWhite = isWhite;
		
		// image panel setting
		if(isWhite) {
			setImage(Images.WHITE_PAWN, ImageOption.MATCH_PARENT);
		} else {
			setImage(Images.BLACK_PAWN, ImageOption.MATCH_PARENT);
		}
		
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	���� ������ �� �ִ� square�� ���� ��ȯ�ϴ� �޼���ν� ���࿡ �������� �������� ù�����ӿ��� �����ϼ� �ִ� ĭ���� ��ȯ�ϰ�
//	ù �������� �־��ٸ� �� ���Ŀ� �����ϼ��ִ� ĭ���� ��ȯ�Ѵ�.
	public int getMoveableSquare() {
		if(!isMoved) {
			return FIRST_MOVEABLE_SQUARE;
		} else {
			return MOVEABLE_SQUARE;
		}
	}
	
//	������� �ƴ��� ��ȯ�ϴ� �޼���
	public boolean getIsWhite() {
		return isWhite;
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
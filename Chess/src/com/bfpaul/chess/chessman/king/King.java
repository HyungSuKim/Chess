package com.bfpaul.chess.chessman.king;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanImage;
import com.bfpaul.chess.chessman.ChessmanType;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� King�� ���� ���ְ� �� King�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class King extends Chessman {

//	���� king�� rook�� ĳ����(�Ѵ� ������ ���� ����ߵ�)�� �����ϱ� ���� ������ �־�� �� �Ӽ��̶� �ǴܵǾ� ������ ����(�⺻�� false)
	private boolean isMoved;
	
//	ŷ�� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ŷ�̹����� �����ͼ� ��������ش�.
	public King(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� �� ���� ��� ��������(�����¿�,�밢��)
//		1ĭ�� �����ϼ� �ִٴ� �������� ������ MOVEABLE_SQUARE ����� 1�� �ʱ�ȭ���ش�.
		super(isWhite, 1);
		
		setImage(ChessmanImage.getChessmanImage(isWhite, ChessmanType.KING), ImageOption.MATCH_PARENT);
		
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

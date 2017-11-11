package com.bfpaul.chess.chessman.rook;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanImage;
import com.bfpaul.chess.chessman.ChessmanType;
import com.mommoo.flat.image.ImageOption;
//chessman ��Ű�� ������ Rook�� ImagePanel�ν� �����ϰ� ���� ���� �Ӽ��� �������ִ� Ŭ����
@SuppressWarnings("serial")
public class Rook extends Chessman {
//	rook�� ĳ����(�� ŷ �Ѵ� ������ ���� ����ߵ�)�� �����ϱ� ���� ������ �־�� �� �Ӽ��̶� �ǴܵǾ� ������ ����(�⺻�� false)
	private boolean isMoved;
//	���� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ���̹����� �����ͼ� ��������ش�.
	public Rook(boolean isWhite) {
//		�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ����
//		���� ��� ��������(�����¿�) �ִ� 7ĭ�� �����ϼ� �ִٴ� �������� ������ MOVEABLE_SQUARE�� 7�� �ʱ�ȭ���־���.
		super(isWhite, 7);
		
		setImage(ChessmanImage.getChessmanImage(isWhite, ChessmanType.ROOK), ImageOption.MATCH_PARENT);
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

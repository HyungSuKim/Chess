package com.bfpaul.chess.chessman.knight;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanImage;
import com.bfpaul.chess.chessman.ChessmanType;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� Knight�� ���� ���ְ� �� Knight�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Knight extends Chessman {

//	����Ʈ�� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����Ʈ�̹����� �����ͼ� ��������ش�.
	public Knight(boolean isWhite) {
//	�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� ���� ����Ʈ�� ��� 2ĭ �������� �����̰� 1ĭ �밢������ �����δ� ���
//	�������� 3ĭ�� �����ϼ� ������ ����Ŭ������ MOVEABLE_SQUARE�� 3���� �ʱ�ȭ ���־���.
		super(isWhite, 3);
		
		setImage(ChessmanImage.getChessmanImage(isWhite, ChessmanType.KNIGHT), ImageOption.MATCH_PARENT);
		
	}

}

package com.bfpaul.chess.chessman.bishop;

import com.bfpaul.chess.chessman.Chessman;
import com.bfpaul.chess.chessman.ChessmanImage;
import com.bfpaul.chess.chessman.ChessmanType;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� bishop�� ���ְ� �� bishop�� �Ӽ��� �������ִ� Ŭ�����̴�.
@SuppressWarnings("serial")
public class Bishop extends Chessman {
	
//	����� �������ε� ����̸�(true) �������̸� (false)�� �޾Ƽ� �̸� �̿��ؼ� ��� �Ǵ� ������ ����̹����� �����ͼ� ��������ش�.
	public Bishop(boolean isWhite) {
//	�θ�Ŭ���� Chessman�� ������ ȣ���� ���� �������� �� ����� �̵������� square�� ������ �����Ѵ�.
//	����� �밢������ �̵��ϱ�� ������ �ִ�� ������ 7ĭ�� ������ �� �ִٴ� �������� 7�� ������ �����ڿ� �Ķ���ͷ� �����Ͽ���.
		super(isWhite, 7);
		
		setImage(ChessmanImage.getChessmanImage(isWhite, ChessmanType.BISHOP), ImageOption.MATCH_PARENT);
		
	}
}

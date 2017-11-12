package com.bfpaul.chess.chessman;

import javax.swing.BorderFactory;

import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

//ü������ �Ӽ��� ������ �ִ�.
//������� �ƴ���(����) �׸��� ü������ ������ �� �ִ� ĭ ���� ������ ������ ü������ �̹��� �� �ϳ��� ���õȴ�.
@SuppressWarnings("serial")
public class Chessman extends FlatImagePanel {
//	ü������ ������� �ƴ��� = ü������ ��� �ƴϸ� ������	
	private boolean isWhite;
//	ü������ �̵������� square�� ����
	private final int MOVEABLE_SQUARE_COUNT;
	
//	ü������ �����Ѵ�.
//	ü������ ������� �ƴ���, �̵������� square�� ������ ���� ������ ���� 
//	������� �ƴ��� �׸��� ü����Ÿ�� ������ �̿��ؼ� �׿� �´� �̹����� ���´�.
	protected Chessman(boolean isWhite, int moveableSquareCount, ChessmanType chessmanType) {
		this.isWhite = isWhite;
		MOVEABLE_SQUARE_COUNT = moveableSquareCount;
		
		setImage(ChessmanImage.getChessmanImage(isWhite, chessmanType), ImageOption.MATCH_PARENT);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	ü������ ������� �ƴ����� ��ȯ�Ѵ�.
	public boolean getIsWhite() {
		return isWhite;
	}

//	ü������ �̵������� square�� ������ ��ȯ�Ѵ�
	public int getMoveableSquareCount() {
		return MOVEABLE_SQUARE_COUNT;
	}
}

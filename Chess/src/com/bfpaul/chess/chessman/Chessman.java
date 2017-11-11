package com.bfpaul.chess.chessman;

import javax.swing.BorderFactory;

import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
//chessman(ü����) ��Ű���� ���Ե� ���� Ŭ����(King, Queen, Bishop, Knight, Rook, Pawn)�� �ν��Ͻ� ���� ��
//ü������ �Ӽ� �� ������� �ƴ���(����) ������ ������ �ִ� �θ� Ŭ�����̴�.
//�� �θ�Ŭ������ �̹��� �г��� ��ӹ޵��� �����Ǿ��µ� ������ ü������ ���������� �ϳ��� �̹����гην� �����ϵ��� �ϱ� �����̴�.
@SuppressWarnings("serial")
public class Chessman extends FlatImagePanel {
//	�ڼ� Ŭ������ �ν��Ͻ� ������ ������� �ƴ���(����) ������ �޾Ƽ� �����ϱ� ���� ��������̴�.	
	private boolean isWhite;
//	ü������ �̵������� square�� ������ ��Ÿ�� ����̴�. �ڼ��� �ν��Ͻ��� ������ �� �Էµ� ������ �ʱ�ȭ���ش�.
	private final int MOVEABLE_SQUARE;
	
//	�ڼ� Ŭ������ �ν��Ͻ� ������ ������� �ƴ���(����) ������ �޾Ƽ� ��������� �����Ѵ�.
//	�׸��� �ڼ�Ŭ�������� �� �ڼ��� �����ϼ� �ִ� ĭ���� �޾� ��������� ��� MOVEABLE_SQUARE�� �ʱ�ȭ���ش�.
//	���� �Ķ���ͷν� ü����Ÿ���� �޾Ƽ� �̹����� ������ �̹����� �̹����гο� �������ش�.
	protected Chessman(boolean isWhite, int moveableSquare, ChessmanType type) {
		this.isWhite = isWhite;
		MOVEABLE_SQUARE = moveableSquare;
		
		setImage(ChessmanImage.getChessmanImage(isWhite, type), ImageOption.MATCH_PARENT);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	������� �ƴ����� �������� �޼����̴�.
	public boolean getIsWhite() {
		return isWhite;
	}

//	ü������ �̵������� square�� ������ ��ȯ�Ѵ�
	public int getMoveableSquare() {
		return MOVEABLE_SQUARE;
	}
}

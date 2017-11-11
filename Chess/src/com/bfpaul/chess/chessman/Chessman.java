package com.bfpaul.chess.chessman;

import javax.swing.BorderFactory;

import com.mommoo.flat.image.FlatImagePanel;
//chessman(ü����) ��Ű���� ���Ե� ���� Ŭ����(King, Queen, Bishop, Knight, Rook, Pawn)�� �ν��Ͻ� ���� ��
//ü������ �Ӽ� �� ������� �ƴ���(����) ������ ������ �ִ� �θ� Ŭ�����̴�.
//�� �θ�Ŭ������ �̹��� �г��� ��ӹ޵��� �����Ǿ��µ� ������ ü������ ���������� �ϳ��� �̹����гην� �����ϵ��� �ϱ� �����̴�.
@SuppressWarnings("serial")
public class Chessman extends FlatImagePanel {
//	�ڼ� Ŭ������ �ν��Ͻ� ������ ������� �ƴ���(����) ������ �޾Ƽ� �����ϱ� ���� ��������̴�.	
	private boolean isWhite;
	
//	�ڼ� Ŭ������ �ν��Ͻ� ������ ������� �ƴ���(����) ������ �޾Ƽ� ��������� �����Ѵ�.
	protected Chessman(boolean isWhite) {
		this.isWhite = isWhite;
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
//	������� �ƴ����� �������� �޼����̴�.
	public boolean getIsWhite() {
		return isWhite;
	}

}

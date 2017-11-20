package com.bfpaul.chess.chessman;

import java.util.ArrayList;

import javax.swing.BorderFactory;

import com.bfpaul.chess.board.Coordinate;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

//ü������ �Ӽ��� ������ �ִ�.
//������� �ƴ���(����) �׸��� ü������ ������ �� �ִ� ĭ ���� ������ ������ ü������ �̹��� �� �ϳ��� ���õȴ�.
@SuppressWarnings("serial")
public class Chessman extends FlatImagePanel {
	// ü������ ������� �ƴ��� = ü������ ��� �ƴϸ� ������
	private boolean isWhite;
	// ü������ �̵������� square�� ����
	private final int MOVEABLE_SQUARE_COUNT;
	// ü������ ������ �� �ִ� square�� ��ǥ��ν� ü������ ���� ������ �� �ִ� ��ǥ�� ǥ���ϰ��� �ߴ�
	private ArrayList<Coordinate> moveableSquareCoordinate = new ArrayList<>();

	// ü������ �����Ѵ�.
	// ü������ ������� �ƴ���, �̵������� square�� ������ ���� ������ ����
	// ������� �ƴ��� �׸��� ü����Ÿ�� ������ �̿��ؼ� �׿� �´� �̹����� ���´�.
	protected Chessman(boolean isWhite, int moveableSquareCount, ChessmanType chessmanType) {
		this.isWhite = isWhite;
		MOVEABLE_SQUARE_COUNT = moveableSquareCount;

		setImage(ChessmanImage.getChessmanImage(isWhite, chessmanType), ImageOption.MATCH_PARENT);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}

	// ü������ ������� �ƴ����� ��ȯ�Ѵ�.
	public boolean getIsWhite() {
		return isWhite;
	}

	// ü������ �̵������� square�� ������ ��ȯ�Ѵ�
	public int getMoveableSquareCount() {
		return MOVEABLE_SQUARE_COUNT;
	}
	
	// ü������ �̵������� �׸�ĭ ��ǥ���� �������ش�
	public void setMoveableSquareCoordinate(int x, int y) {
		moveableSquareCoordinate.add(new Coordinate(x, y));
	}
	// ü������ �̵������� �׸�ĭ ��ǥ���� �ʱ�ȭ�Ѵ�.
	public void refreshMoveableSquareCoordinate() {
		moveableSquareCoordinate.clear();
	}
	public ArrayList<Coordinate> getMoveableSquareCoordinate() {
		return moveableSquareCoordinate;
	}
}

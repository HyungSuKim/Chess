package com.bfpaul.renewal.chess.controller.chessman;

import java.util.ArrayList;

import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.controller.Coordinate;

/* 
 * ü�������� �̿��ؼ� �������� �������� �Ͱ� ������� ��, �̵��ϴ� ���� �������� ǥ���ϱ� ���� �����Ѵ�. 
 * ChessmanController�� ĭ ���� �ִ� ���� �̵������� ��θ� ����Ͽ� ���忡 ���������ν� ü������ �̵� �� ���ִ� ��θ� ǥ�� �� �� �ְ� ���ش�. 
 * 
 * �ۼ���...
 */
public class ChessmanController {
	
	private ChessmanController() { }
	
	public static ArrayList<Coordinate> pickUpChessman(Chessman chessman, int x, int y) {
		return AvailableMoveOperator.availableMoveCoordinate(chessman, x, y);
	}
}

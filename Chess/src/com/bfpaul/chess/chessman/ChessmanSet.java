package com.bfpaul.chess.chessman;

import java.util.ArrayList;
// ü������ �������� �ϳ��� ������ �����ϱ����� �ۼ��� Ŭ����
// ex) Pawn 8���� �ϳ��� set���� ����
public class ChessmanSet<T extends Chessman> {
	private ArrayList<T> set = new ArrayList<>();
//	������ ���۵ɶ� ���� �����ֱ� ����
	public void add(T t) {
		set.add(t);
	}
//	���� �׾����� ���� �����ϱ� ����
	public void remove(T t) {
		set.remove(t);
	}
//	������ ���� �ε����ν� �����´�
	public T get(int index) {
		return set.get(index);
	}
//	�ش� ������ ���� ��� �ִ��� ��ȯ �޴´�
	public int size() {
		return set.size();
	}
}

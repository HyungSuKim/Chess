package com.bfpaul.chess.chessman;

import java.util.ArrayList;
// 체스말을 종류별로 하나의 셋으로 관리하기위해 작성된 클래스
// ex) Pawn 8개를 하나의 set으로 관리
public class ChessmanSet<T extends Chessman> {
	private ArrayList<T> set = new ArrayList<>();
//	게임이 시작될때 말을 더해주기 위함
	public void add(T t) {
		set.add(t);
	}
//	말이 죽었을때 말을 제거하기 위함
	public void remove(T t) {
		set.remove(t);
	}
//	정해진 말을 인덱스로써 가져온다
	public T get(int index) {
		return set.get(index);
	}
//	해당 종류의 말이 몇개가 있는지 반환 받는다
	public int size() {
		return set.size();
	}
}

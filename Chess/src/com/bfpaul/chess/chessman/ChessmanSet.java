package com.bfpaul.chess.chessman;

import java.util.ArrayList;

import com.mommoo.flat.image.FlatImagePanel;

public class ChessmanSet<T extends FlatImagePanel> {
	private ArrayList<T> set = new ArrayList<>();
	
	public void add(T t) {
		set.add(t);
	}
	
	public void remove(T t) {
		set.remove(t);
	}
	
	public T get(int index) {
		return set.get(index);
	}
	
	public int size() {
		return set.size();
	}
}

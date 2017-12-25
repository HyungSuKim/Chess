package com.bfpaul.renewal.chess.main;

import javax.swing.SwingUtilities;

import com.bfpaul.renewal.chess.game.IntroFrame;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new IntroFrame();
			}
		});
	}
}

package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.util.Timer;

import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

public class BoardEventInfoView extends Thread {
	BoardEventInfoView(Image image) {
		FlatFrame frame = new FlatFrame();
		FlatImagePanel imagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		imagePanel.setOpaque(true);
		frame.setSize(400, 200);
		frame.setLocationOnScreenCenter();
		frame.setTitleBarVisible(false);
		frame.getContainer().setOpaque(true);
		frame.getContainer().setLayout(new BorderLayout());
		frame.getContainer().add(imagePanel);
		frame.show();

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			
		} finally {
			frame.hide();
		}
	}
}

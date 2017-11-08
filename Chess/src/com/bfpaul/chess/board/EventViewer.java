package com.bfpaul.chess.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

@SuppressWarnings("serial")
class EventViewer extends FlatPanel {
	private static FlatPanel[][] square = new FlatPanel[8][8];
	
//	private static Properties properties = new Properties();
	
	public EventViewer() {
		setLayout(new GridLayout(8, 8));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		createBoard();
	}
	
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
	private void createBoard() {
		for(int y = 8; y > 0; y--) {
			for(int x = 0; x < 8; x++) {
				this.add(createSquare(x, y-1), createCommonConstraints(1));
			}
		}	
	}
	
	private FlatPanel createSquare(int x, int y) {
		square[y][x] = new FlatPanel(new LinearLayout(Orientation.HORIZONTAL));
		square[y][x].setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Theme.DARK_BLUE_COLOR));
		square[y][x].setOnClickListener(getListenerToShowAvailMovement(x, y));
		
		return square[y][x];
	}
	
	private OnClickListener getListenerToShowAvailMovement(int x, int y) {
		return new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				if(square[y][x].getBackground() == Theme.LIGHT_BLUE_COLOR) {
					System.out.println("투명하게 실행");
					square[y][x].setBackground(Color.ORANGE);
					square[y][x].setOpaque(false);
					square[y][x].repaint();
				} else {
					square[y][x].setBackground(Theme.LIGHT_BLUE_COLOR);
					square[y][x].setOpaque(true);
				}
			}
			
		};
	}
	
	void setChessman(FlatImagePanel chessman, int x, int y) {
		square[y][x].add(chessman, createCommonConstraints(1));
	}
}

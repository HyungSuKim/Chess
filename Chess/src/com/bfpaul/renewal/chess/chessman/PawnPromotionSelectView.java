package com.bfpaul.renewal.chess.chessman;

import java.awt.Component;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.board.BoardSquare;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.frame.FlatDialog;
import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class PawnPromotionSelectView {
	FlatFrame frame = createFrame();
	
	public PawnPromotionSelectView(BoardSquare boardSquare) {
		
		frame.getContainer().add(createImagePanel(boardSquare, ChessmanType.QUEEN),
				new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		frame.getContainer().add(createImagePanel(boardSquare, ChessmanType.BISHOP),
				new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		frame.getContainer().add(createImagePanel(boardSquare, ChessmanType.KNIGHT),
				new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		frame.getContainer().add(createImagePanel(boardSquare, ChessmanType.ROOK),
				new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		frame.show();
	}
	
	private FlatFrame createFrame() {
		FlatFrame frame = new FlatFrame();
		frame.setTitle("폰 프로모션");
		frame.setSize(400, 200);
		frame.setIconImage(Images.ICON);
		frame.setProcessIconImage(Images.ICON);
		frame.setTitleBarColor(Theme.TITLE_BAR_COLOR);
		frame.getContainer().setBackground(Theme.DARK_BLUE_COLOR);
		frame.getContainer().setLayout(new LinearLayout(10));
		frame.setLocationOnScreenCenter();
		return frame;
	}
	
	private FlatImagePanel createImagePanel(BoardSquare boardSquare, ChessmanType type) {
		FlatImagePanel imagePanel
		= new FlatImagePanel(ChessmanImage.getChessmanImage(boardSquare.getChessman().isWhite(), type)
				, ImageOption.MATCH_PARENT);
		imagePanel.setBackground(Theme.LIGHT_BLUE_COLOR);
		imagePanel.setOpaque(true);
		imagePanel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				System.out.println(type.name());
				switch(type) {
				case QUEEN : boardSquare.setChessmanOnSquare(new Queen(boardSquare.getChessman().isWhite()));
				break;
				case BISHOP : boardSquare.setChessmanOnSquare(new Bishop(boardSquare.getChessman().isWhite()));
				break;
				case KNIGHT : boardSquare.setChessmanOnSquare(new Knight(boardSquare.getChessman().isWhite()));
				break;
				case ROOK : boardSquare.setChessmanOnSquare(new Rook(boardSquare.getChessman().isWhite()));
				break;
				default :
				}
				boardSquare.setEnabled(false);
				
				new FlatDialog.Builder()
				.setLocationRelativeTo(frame.getContainer())
				.setTitle("알림")
				.setContent(type.name() + "프로모션 성공")
				.build().show();
				
				frame.hide();
			}
		});
		return imagePanel;
	}
}

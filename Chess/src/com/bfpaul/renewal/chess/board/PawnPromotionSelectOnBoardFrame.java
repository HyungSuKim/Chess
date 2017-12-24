package com.bfpaul.renewal.chess.board;

import java.awt.Component;

import javax.swing.JPanel;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Bishop;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.ChessmanImage;
import com.bfpaul.renewal.chess.chessman.ChessmanType;
import com.bfpaul.renewal.chess.chessman.Knight;
import com.bfpaul.renewal.chess.chessman.Queen;
import com.bfpaul.renewal.chess.chessman.Rook;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.frame.FlatDialog;
import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

/**
 * BoardPanel ������ Pawn�� ������ y��ǥ�� �������� ��(���θ�� �� �� �ִ� ��ǥ) �����Ǵ� Frame�̴�.
 * ���� �� ���� ��ġ�ϰ��ִ� BoardSquare�� �޾ƿͼ� ���� ���θ�� �� �� �ֵ��� �����ش�.
 * �켱 BoardSquare�� ���� ���������� �̿��ؼ� ���θ�� �� ���ִ� ����(��/���/����Ʈ/��)�� Frame�� �̹����ν� ǥ�����ش�.
 * ����ڰ� ǥ���� �̹����� ���������ν� Ŭ������ BoardSquare�� �ִ� ���� ���õ� �̹����� ���� �����ؼ� �������ش�. 
 * ������ ����Ǹ� �� Frame�� ����ȴ�.
 * @author ������
 */
/* Ŭ���� �ۼ� �� ���˿��
 * 1. �ùٸ� Ŭ���� �̸��ΰ�? : ���� ���θ�� �ϱ����� ����(�̹�����)�� ����ְ� BoardSquare�� ���θ�� �� ������ �������ֹǷ� View & Controller�� ����ߴ�.
 * ������ �� Ŭ������ ������ �켱 ���θ���� �ϴ� Frame�� ���� ���̶� �����ؼ� Frame���� �̸��� �����ߴ�. 
 * 2. ��������� ���� ���� : 2��
 * 		frame -> ��� ���� �ʿ������� �ʴ�... �̹����� ���������� â�� ������ �ϰ�; �ۼ��� ������� �ε� ���ְ� �ʹ�.
 * 		callBack -> 
 * 3. Ŭ������ ����(Data/Controller/View) : View + Controller
 * 4. Data ������ ��������� �и� : private OnClickListener getListenerToPromotion ����
 * 5. ����������/������/�ڷ��� : 
 * 6. inner class / abstract / interface : ������ �ش���� ����
 * 7. ���α׷��� ���� : �ش���� ����.
 * 8. class���� �������� �� �ʿ����� : �� Ŭ������ BoardPanel�� �����Ǿ��ִ�. ������ �� Ŭ������ ��ġ�� ����ִµ� GameFrame�� Width/Height��
 * ���Ͽ� ��ġ�� ����ְ� ������ �ش�κ��� ���� �ڵ��� ���� ���Ͽ��� ������̴�.
 * 9. ���� : BoardPanel���� ���� ������ ��ǥ�� �����ϸ� �����ǰ� ���θ�� �� ���� �����ϸ� �������.
 * 10.�ߺ� ���� ������ �ݺ����� �ʴ°� : �ش����
 * */
class PawnPromotionSelectOnBoardFrame {
	
	private FlatFrame frame = createFrame();

	private OnClickListener callBack = comp -> {};
	
	PawnPromotionSelectOnBoardFrame(BoardSquare boardSquare) {
		
		JPanel container = frame.getContainer(); 

		container.add(createImagePanel(boardSquare, ChessmanType.QUEEN), getMatchParentConstraints(1));
		container.add(createImagePanel(boardSquare, ChessmanType.BISHOP), getMatchParentConstraints(1));
		container.add(createImagePanel(boardSquare, ChessmanType.KNIGHT), getMatchParentConstraints(1));
		container.add(createImagePanel(boardSquare, ChessmanType.ROOK), getMatchParentConstraints(1));
		
		frame.show();
	}
	
	private LinearConstraints getMatchParentConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
	void setCallBack(OnClickListener onClickListener) {
		this.callBack = onClickListener;
	}
	
	private FlatFrame createFrame() {
		FlatFrame frame = new FlatFrame();
		frame.setTitle("�� ���θ��");
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
		imagePanel.setOnClickListener(getListenerToPromotion(boardSquare, type));
		return imagePanel;
	}
	
	private OnClickListener getListenerToPromotion(BoardSquare boardSquare, ChessmanType type) {
		return new OnClickListener() {
			Chessman promotionChessman;
			@Override
			public void onClick(Component component) {

				switch(type) {
				case QUEEN : promotionChessman = new Queen(boardSquare.getChessman().isWhite());
				break;
				case BISHOP : promotionChessman = new Bishop(boardSquare.getChessman().isWhite());
				break;
				case KNIGHT : promotionChessman = new Knight(boardSquare.getChessman().isWhite());
				break;
				case ROOK : promotionChessman = new Rook(boardSquare.getChessman().isWhite());
				break;
				default :
				}
				
				boardSquare.setChessmanOnSquare(promotionChessman);
				boardSquare.setEnabled(false);
				
				callBack.onClick(component);
				
				new FlatDialog.Builder()
				.setLocationRelativeTo(frame.getContainer())
				.setTitle("�˸�")
				.setContent(type.name() + "���θ�� ����")
				.build().show();
				
				frame.hide();
			}
		};
	}
}

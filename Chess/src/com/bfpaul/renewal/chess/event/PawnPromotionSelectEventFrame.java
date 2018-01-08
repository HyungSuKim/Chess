package com.bfpaul.renewal.chess.event;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;

import com.bfpaul.chess.Images;
import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.board.BoardSquare;
import com.bfpaul.renewal.chess.chessman.data.Bishop;
import com.bfpaul.renewal.chess.chessman.data.Chessman;
import com.bfpaul.renewal.chess.chessman.data.ChessmanImage;
import com.bfpaul.renewal.chess.chessman.data.ChessmanType;
import com.bfpaul.renewal.chess.chessman.data.Knight;
import com.bfpaul.renewal.chess.chessman.data.Queen;
import com.bfpaul.renewal.chess.chessman.data.Rook;
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
public class PawnPromotionSelectEventFrame {
	public interface OnPromotionSelectedListener {
		public void onSelected(ChessmanType chessmanType);
	}
	private FlatFrame frame = createFrame();
	
	private OnPromotionSelectedListener onPromotionSelectedListener = chessmanType -> {};
	
	public PawnPromotionSelectEventFrame(boolean isWhite) {
		
		JPanel container = frame.getContainer(); 

		container.add(createImagePanel(isWhite, ChessmanType.QUEEN), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		container.add(createImagePanel(isWhite, ChessmanType.BISHOP), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		container.add(createImagePanel(isWhite, ChessmanType.KNIGHT), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		container.add(createImagePanel(isWhite, ChessmanType.ROOK), new LinearConstraints(1, LinearSpace.MATCH_PARENT));
		
		frame.show();
	}
	
	public void setOnPromotionSelectedListener(OnPromotionSelectedListener onPromotionSelectedListener) {
		this.onPromotionSelectedListener = onPromotionSelectedListener;
	}
	
	private static FlatFrame createFrame() {
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
	
	private FlatImagePanel createImagePanel(boolean isWhite, ChessmanType type) {
		FlatImagePanel imagePanel
		= new FlatImagePanel(ChessmanImage.getChessmanImage(isWhite, type), ImageOption.MATCH_PARENT);
		imagePanel.setBackground(Theme.LIGHT_BLUE_COLOR);
		imagePanel.setOpaque(true);
		imagePanel.setOnClickListener(comp -> {
			onPromotionSelectedListener.onSelected(type);
			
			new FlatDialog.Builder()
			.setLocationRelativeTo(frame.getContainer())
			.setTitle("�˸�")
			.setContent(type.name() + "���θ�� ����")
			.build().show();
			
			frame.hide();
		});
		return imagePanel;
	}
}

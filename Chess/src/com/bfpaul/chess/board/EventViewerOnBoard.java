package com.bfpaul.chess.board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
//Board���� ��ġ�ϴ� ������ Square�� ���� ������ Board�ν� �̺�Ʈ �߻��� ������ Square���� �������ϰ� ��Ÿ���־�
//�̺�Ʈ�� �߻��� ��Ÿ���ִ� Ŭ����
@SuppressWarnings("serial")
public class EventViewerOnBoard extends FlatPanel {
//	BoardŬ������ �����ϰ� ĭĭ�� �̺�Ʈ�� ó���ؼ� ��Ÿ�� �־�� �Ѵٰ� �Ǵ��ؼ� ��������� �ۼ��Ͽ���.
	private FlatPanel[][] square = new FlatPanel[8][8];
	
//	�̺�Ʈ�� ��Ÿ���� Ȯ���ϱ� ���� ��������ν� ���� ��� ��� ĭ�� �̺�Ʈ�� �߻��Ǿ� �������ϰ� �Ǿ����� �̸� ����ϰ��ִٰ� �̺�Ʈ�� �ٽ� �߻���Ű��
//	�ٽ� �����ϰ� �ٲپ��ֱ����� ��������̴�. ���� ��������ν� �ٸ�ĭ�鿡 �߻��� �̺�Ʈ�鵵 �Ѳ����� ó�� �� �� ������ ����ϰ� �ۼ��� ������.
	private boolean isShown;
	
//	�� Ŭ������ �����ڷν� �⺻������ ����� ������ ���̾ƿ��� ������ ���´�. �� ������ �������� �÷��� ������ ĭ���ν� ���� ��ġ�� square�� �̺�Ʈ�� ǥ�����ֱ� �����̴�.
//	���� Ŭ������ �����ϰ� ������ ����� ���� ������ ���带 ����� �ٿ��ش�.
	public EventViewerOnBoard() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(createEventViewer(), createCommonConstraints(1));
	}
	
//	�ַ� ����ϴ� LinearLayout�� �Ϲ����� ���������� ��ȯ���ִ� �޼���ν� �Ķ���ͷ� ���Ը� �޾Ƽ� ���Ը� �������ְ� �θ��� ũ�⸸ŭ ������ ü���ش�. 
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	�̺�Ʈ���带 �����ؼ� �гη� ��ȯ���ִ� �޼���ν� �׸��� ���̾ƿ��� ����ؼ� 8 X 8�� square�� ���� ���带 ������ִµ� �̸� ���ؼ� ����������
//	��ø�� for���� �̿��ؼ� x, y�� �Ķ���ͷ� ���� createSquare �޼��带 ȣ�����ش�.
//	�̶� y�� 8���� �����ϰ� x�� 0���� �����ϵ��� ������ ������ GridLayout Ư���� ���� ��ܺ��� ���������� ��ġ�ϱ� ������
//	(x=0, y=7)�� �Ӽ��� ���� squre�� ���� ��ġ�� �Ǿ�� ���� squre�� �̿��� �ٸ� �̺�Ʈ���� ó���� ������ ���̶� �Ǵ��߱� �����̴�.
	private FlatPanel createEventViewer() {
		FlatPanel eventViewerBoard = new FlatPanel(new GridLayout(8, 8));
		for(int y = 8; y > 0; y--) {
			for(int x = 0; x < 8; x++) {
				eventViewerBoard.add(createSquare(x, y-1), createCommonConstraints(1));
			}
		}	
		return eventViewerBoard;
	}
	
//	�̺�Ʈ �� ������ square�� �������ִ� �޼���ν� �⺻������ ���õǾ��ִ� setOpaque(false)�� �̿��ؼ� ������ square�� �������ش�.
//	�� ������� 5�ȼ��� ��ο� ������ ������ �ְ� ����� ���������̰� 0.4�� �������� ������. ������ ���Ŀ� �̺�Ʈ�� �߻���������
//	setOpaque(true)�� ���� ��Ÿ���� ���� ���ε� �̶� ���� ������ ���빰�� �������ϰ� �����ֱ� �����̴�.
	private FlatPanel createSquare(int x, int y) {
		square[y][x] = new FlatPanel(new LinearLayout(Orientation.HORIZONTAL));
		square[y][x].setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Theme.DARK_BLUE_COLOR));
		square[y][x].setBackground(Theme.LIGHT_BLUE_COLOR);
		square[y][x].setAlpha(0.4f);
		
		return square[y][x];
	}
//	Ŭ���� ����� square�� ���ؼ� �̺�Ʈ �߻��� �ǵ��Ѵ�� �Ǵ��� Ȯ���ϱ� ���ؼ� �ۼ��ߴ� �޼����̴�.
//	private OnClickListener getListenerToShowAvailMovement(int x, int y) {
//		return new OnClickListener() {
//
//			@Override
//			public void onClick(Component component) {
//				// TODO Auto-generated method stub
//				if(isShown) {
//					square[y][x].setOpaque(false);
//	                isShown = false;
//				} else {
//					square[y][x].setOpaque(true);
//	                isShown = true;
//				}
//			}
//			
//		};
//	}
	
//	������ ���� �������� ��Ÿ���ֱ� ���ؼ� �ۼ��س��� �޼���ν� ���� ���ɼ��� ���� �޼����̴�.
//	�׷����� �ۼ��س��� ������ �Է¹��� x y�� ���ؼ� �� ���̱��� �̺�Ʈ�� ǥ�����شٴ� ���̵� ���ܳ������ؼ� �ۼ��س��Ҵ�. 
	public void showAvailMovement(int inputX, int inputY) {
		if(!isShown) {
			for(int y = 0; y <= inputY; y++) {
				for(int x = 0; x <= inputX; x++) {
					square[y][x].setOpaque(true);
				}
			}
			isShown = true;
		} else {
			isShown = false;
		}
	}
}

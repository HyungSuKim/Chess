package com.bfpaul.renewal.chess.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JDialog;

import com.bfpaul.renewal.chess.game.GameFrame;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;

/**
 * BoardPanel ������ Ư���� ������/����(EnPassant, Castling, Check)�� �ð������� ǥ�����ֱ����� ���̴�.
 * �� Ŭ������ �Ǵٸ� �ϳ��� Frame���ν��� ���� ���ٴ� BoardPanel���� �������� Dialog �� ������ �������ִ�.
 * �� Ŭ������ ���������� ���¿����� �̹����� �޾� �ش� �̹����� ���� ������ Dialog�� �����Ѵ�. 
 * 
 * �� ���̾�α׸� �������ʰ�(�ݴ�) ������� 2������ �����ߴµ�
 * 1. ���̾�α׸� Ŭ���ϸ� �ݴ´�
 * 2. ���̾�αװ� �����ð��� ������ �ڵ����� ������
 * 
 * 2�� ����� �����ϴµ� ���� ���� �� ����� �ʿ��� �� ����.
 * @author ������
 */

/* Ŭ���� �ۼ� �� ���˿��
 * 1. �ùٸ� Ŭ���� �̸��ΰ�? : Board���� �߻��ϴ� 3���� �̺�Ʈ ������ �̹����� �̿��ؼ� �����ְ��� �ۼ��� Ŭ����
 * ���������� ������ ������ ������ �Ͼ�� �ʰ� �ٸ� Ŭ�������� ������ ���� �������� �ʴ´�. ���� View�ν� �����ϴٰ� �Ǵ��ߴ�.
 * 2. ��������� ���� ���� : ����.
 * 3. Ŭ������ ����(Data/Controller/View) : View
 * 4. Data ������ ��������� �и� : �ش���� ����.
 * 5. ����������/������/�ڷ��� : �ش� Ŭ������ ������Ű�� (board) ���� BoardPanel���� ���� �ȴ�. ���� ���������ڴ� default�� �Ǵ��ߴ�.
 * ��������� ���ξ����Ƿ� �����ڴ� �ش����� �ʴ´�. �ڷ����� View�� �´� �ڷ����� ����Ͽ��ٰ� �����Ѵ�.
 * 6. inner class / abstract / interface : ������ �ش���� ����
 * 7. ���α׷��� ���� : �ش���� ����. ( ����� )
 * 8. class���� �������� �� �ʿ����� : �� Ŭ������ BoardPanel�� �����Ǿ��ִ�. ������ �� Ŭ������ ��ġ�� ����ִµ� GameFrame�� Width/Height��
 * ���Ͽ� ��ġ�� ����ְ� ������ �ش�κ��� ���� �ڵ��� ���� ���Ͽ��� ������̴�.
 * 9. ���� : BoardPanel���� ó���ؾ��� �̺�Ʈ��(EnPassant/Castling/Check)�� �߻��ϸ� �����ǰ� �������. (����� Ŭ���� ������� 
 * ���� �ð� �� ������� �� �߰�����)
 * 10.�ߺ� ���� ������ �ݺ����� �ʴ°� : �ش����
 * */


@SuppressWarnings("serial")
public class BoardEventInfoView extends JDialog {
	
	BoardEventInfoView(Image image) {
		FlatImagePanel imagePanel = new FlatImagePanel(image, ImageOption.MATCH_PARENT);
		
		setSize(400, 200);
		setUndecorated(true);
		
		setBackground(new Color(0, 0, 0, 0));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(imagePanel, BorderLayout.CENTER);
		
		imagePanel.setOnClickListener( component -> { setVisible(false); } );
		
		setVisible(true);
		
		System.out.println(getSize().toString());
		System.out.println(getLocation().toString());
		System.out.println(getBounds().toString());
//		this.run();
	}
	
//	@SuppressWarnings("static-access")
//	void run() {
//		try {
//			Thread timerThread = new Thread();
//			timerThread.sleep(500);
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			setVisible(false);
//		}
//	}
}

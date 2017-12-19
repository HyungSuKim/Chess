package com.bfpaul.renewal.chess;

import java.awt.BorderLayout;
import java.awt.Component;

import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.frame.FlatFrame;

public class ButtonTest {
	OnClickListener onClickListener = c -> {};
	
	ButtonTest() {
		FlatFrame frame = new FlatFrame();
		FlatButton btn = new FlatButton();
		
		frame.setSize(300, 300);
		frame.getContainer().setLayout(new BorderLayout());
		frame.getContainer().add(btn);
		
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				System.out.println("버튼클릭1");
				onClickListener.onClick(component);
			}
			
		});
		
		frame.show();
	}
	
	public void setOnClickListener2(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	public static void main(String[] arg) {
		ButtonTest buttonTest = new ButtonTest();
		buttonTest.setOnClickListener2(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				System.out.println("버튼클릭2");
				System.out.println("버튼클릭3");
			}
			
		});
	}
}

interface ButtonListener extends OnClickListener { }

class ButtonListener2 implements ButtonListener {

	@Override
	public void onClick(Component component) {
		System.out.println("버튼클릭2");
	}
	
}

class ButtonListener3 implements ButtonListener {

	@Override
	public void onClick(Component component) {
		System.out.println("버튼클릭3");
	}
	
}
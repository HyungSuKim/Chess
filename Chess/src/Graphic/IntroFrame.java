package Graphic;

import java.awt.Component;

import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.OnClickListener;
import com.mommoo.flat.frame.FlatFrame;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.util.ScreenManager;

public class IntroFrame {
	private static Images images = new Images();
	private static Theme theme = new Theme();
	
	public IntroFrame() {
		FlatFrame frame = createFrame();
		
		frame.getContainer().add(createImageView(), createCommonConstraints(3));
		
		frame.getContainer().add(createSinglePlayButton(), createCommonConstraints(1));
		
		frame.getContainer().add(createDoublePlayButton(), createCommonConstraints(1));
		
		frame.show();
	}
	
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
	private FlatFrame createFrame() {
		FlatFrame frame = new FlatFrame();
		ScreenManager screenManager = ScreenManager.getInstance();
		frame.setTitle("Chess Intro");
//		frame.setSize(screenManager.dip2px(400), screenManager.dip2px(500));
		frame.setSize(screenManager.dip2px(250), screenManager.dip2px(350));
		frame.getContainer().setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		frame.setLocationOnScreenCenter();
		frame.setIconImage(images.getIconImage());
		frame.setProcessIconImage(images.getIconImage());
		frame.setTitleBarColor(theme.getTitleBarColor());
		frame.setResizable(false);
		frame.setWindowExit(true);
		return frame;
	}
	
	private FlatImagePanel createImageView() {
		return new FlatImagePanel(images.getIntroImage(), ImageOption.MATCH_PARENT);
	}
	
	private FlatButton createSinglePlayButton() {
		FlatButton singlePlayButton = new FlatButton("혼자하기");
		singlePlayButton.setBackground(theme.getLightBlueColor());
		return singlePlayButton;
	}
	
	private FlatButton createDoublePlayButton() {
		FlatButton doublePlayButton = new FlatButton("둘이하기");
		doublePlayButton.setBackground(theme.getDarkBlueColor());
		doublePlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(Component component) {
				// TODO Auto-generated method stub
				new GameFrame();
			}
			
		});
		return doublePlayButton;
	}
}

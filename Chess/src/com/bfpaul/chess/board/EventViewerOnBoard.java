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
//Board위에 위치하는 투명한 Square을 가진 투명한 Board로써 이벤트 발생시 투명한 Square들을 불투명하게 나타내주어
//이벤트의 발생을 나타내주는 클래스
@SuppressWarnings("serial")
public class EventViewerOnBoard extends FlatPanel {
//	Board클래스와 동일하게 칸칸에 이벤트를 처리해서 나타내 주어야 한다고 판단해서 멤버변수로 작성하였다.
	private FlatPanel[][] square = new FlatPanel[8][8];
	
//	이벤트의 나타남을 확인하기 위한 멤버변수로써 예를 들어 어떠한 칸에 이벤트가 발생되어 불투명하게 되었을때 이를 기록하고있다가 이벤트를 다시 발생시키면
//	다시 투명하게 바꾸어주기위한 멤버변수이다. 또한 멤버변수로써 다른칸들에 발생한 이벤트들도 한꺼번에 처리 할 수 있음을 기대하고 작성된 변수다.
	private boolean isShown;
	
//	본 클래스의 생성자로써 기본적으로 보드와 동일한 레이아웃과 여백을 갖는다. 이 이유는 보드위에 올려진 투명한 칸으로써 같은 위치의 square에 이벤트를 표시해주기 위함이다.
//	보드 클래스와 동일하게 투명한 스퀘어를 가진 투명한 보드를 만들어 붙여준다.
	public EventViewerOnBoard() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(createEventViewer(), createCommonConstraints(1));
	}
	
//	주로 사용하는 LinearLayout의 일반적인 제약조건을 반환해주는 메서드로써 파라미터로 무게를 받아서 무게를 지정해주고 부모의 크기만큼 영역을 체워준다. 
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	이벤트뷰어보드를 생성해서 패널로 반환해주는 메서드로써 그리드 레이아웃을 사용해서 8 X 8의 square을 가진 보드를 만들어주는데 이를 위해서 내부적으로
//	중첩된 for문을 이용해서 x, y를 파라미터로 갖는 createSquare 메서드를 호출해준다.
//	이때 y는 8부터 시작하고 x는 0부터 시작하도록 지정한 이유는 GridLayout 특성상 좌측 상단부터 순차적으로 배치하기 때문에
//	(x=0, y=7)의 속성을 갖는 squre가 먼저 배치가 되어야 추후 squre을 이용한 다른 이벤트들의 처리시 유용할 것이라 판단했기 때문이다.
	private FlatPanel createEventViewer() {
		FlatPanel eventViewerBoard = new FlatPanel(new GridLayout(8, 8));
		for(int y = 8; y > 0; y--) {
			for(int x = 0; x < 8; x++) {
				eventViewerBoard.add(createSquare(x, y-1), createCommonConstraints(1));
			}
		}	
		return eventViewerBoard;
	}
	
//	이벤트 뷰어에 더해줄 square를 생성해주는 메서드로써 기본적으로 셋팅되어있는 setOpaque(false)를 이용해서 투명한 square를 생성해준다.
//	이 스퀘어는 5픽셀의 어두운 여백을 가지고 있고 배경은 밝은색상이고 0.4의 불투명도를 가진다. 이유는 추후에 이벤트를 발생시켰을때
//	setOpaque(true)를 통해 나타나게 해줄 것인데 이때 기존 보드의 내용물을 반투명하게 보여주기 위함이다.
	private FlatPanel createSquare(int x, int y) {
		square[y][x] = new FlatPanel(new LinearLayout(Orientation.HORIZONTAL));
		square[y][x].setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Theme.DARK_BLUE_COLOR));
		square[y][x].setBackground(Theme.LIGHT_BLUE_COLOR);
		square[y][x].setAlpha(0.4f);
		
		return square[y][x];
	}
//	클릭이 실행된 square에 대해서 이벤트 발생이 의도한대로 되는지 확인하기 위해서 작성했던 메서드이다.
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
	
//	추후의 말의 움직임을 나타내주기 위해서 작성해놓은 메서드로써 변동 가능성이 많은 메서드이다.
//	그럼에도 작성해놓은 이유는 입력받은 x y에 대해서 그 길이까지 이벤트를 표현해준다는 아이디어를 남겨놓기위해서 작성해놓았다. 
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

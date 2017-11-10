package com.bfpaul.chess.board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

// 클래스의 역할... x축 y축 8칸의 square을 가진 Chess Board를 생성한다.
@SuppressWarnings("serial")
public class Board extends FlatPanel {
//	체스 판의 하나하나의 squre을 나타내는 멤버변수로써  체스말을 보드에 더해주거나 추가 이벤트를 처리 할 경우의 수가 있다고 판단해서 멤버변수로 지정 
	private FlatPanel[][] square = new FlatPanel[8][8];
	
// Board의 생성자로써 BorderLayout을 레이아웃으로 설정해준 이유는 추후 EventViewer 클래스를 이 보드위에 겹처서 붙이기 위함이었는데
//	이는 변동가능성이 있는 부분이다. 보드를 생성해서 자기자신에게 붙여준다. 여기서 board를 변수로써 처리하지 않고 메서드로써 처리한 이유는
//	보드 자체를 생성한 이후 참조할 경우의 수가 없다고 판단했기 때문이다.
	public Board() { 
		
		setLayout(new BorderLayout());
		
		add(createBoard());
	}
	
//	주로 사용하는 LinearLayout의 일반적인 제약조건을 반환해주는 메서드로써 파라미터로 무게를 받아서 무게를 지정해주고 부모의 크기만큼 영역을 체워준다. 
	private LinearConstraints createCommonConstraints(int weight) {
		return new LinearConstraints().setWeight(weight).setLinearSpace(LinearSpace.MATCH_PARENT);
	}
	
//	보드를 생성해서 패널로 반환해주는 메서드로써 그리드 레이아웃을 사용해서 8 X 8의 square을 가진 보드를 만들어주는데 이를 위해서 내부적으로
//	중첩된 for문을 이용해서 x, y를 파라미터로 갖는 createSquare 메서드를 호출해준다.
//	이때 y는 8부터 시작하고 x는 0부터 시작하도록 지정한 이유는 GridLayout 특성상 좌측 상단부터 순차적으로 배치하기 때문에
//	(x=0, y=7)의 속성을 갖는 squre가 먼저 배치가 되어야 추후 squre을 이용한 다른 이벤트들의 처리시 유용할 것이라 판단했기 때문이다.
	private FlatPanel createBoard() {
		FlatPanel board = new FlatPanel(new GridLayout(8, 8));
		
		board.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		board.setBackground(Theme.BOARD_BORDER_COLOR);
		
		board.setOpaque(true);
		
		for(int y = 8; y > 0; y--) {
			for(int x = 0; x < 8; x++) {
				board.add(createSquare(x, (y-1)), createCommonConstraints(1));
			}
		}	
		
		return board;
	}
	
//	파라미터로 x, y를 받아서 체스판의 square을 생성해주는 메서드로써
//	체스판의 특성상 x, y가 2로 나누었을때 나머지가 0이거나 1인경우에는 어두운 색깔을 가지고
//	그렇지 않은 경우에는 밝은 색깔을 가진다는 성격을 파악해서 if-else문을 사용해서 맞는 색상을 지정해주게 해주었다
	private FlatPanel createSquare(int x, int y) {
		square[y][x] = new FlatPanel(new LinearLayout(Orientation.HORIZONTAL));
		square[y][x].setOpaque(true);
		
		if(((y%2==0) && (x%2==0))||((y%2==1)&&(x%2==1))) {
			square[y][x].setBackground(Theme.BOARD_DARK_SQUARE_COLOR);
		} else {
			square[y][x].setBackground(Theme.BOARD_LIGHT_SQUARE_COLOR);
		}
		
		return square[y][x];
	}
	
//	FlatImagePanel인 chessman(King, Queen, Bishop, Knight, Rook, Pawn)을 원하는 좌표값(x,y)을 갖는 위치에 더해주기위한 메서드
	public void setChessmanOnSquare(FlatImagePanel chessman, int x, int y) {
		
		square[y][x].add(chessman, createCommonConstraints(1));
		
	}
}

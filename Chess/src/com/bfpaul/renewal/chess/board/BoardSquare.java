package com.bfpaul.renewal.chess.board;

import java.awt.Color;

import javax.swing.BorderFactory;

import com.bfpaul.chess.Theme;
import com.bfpaul.renewal.chess.chessman.Chessman;
import com.bfpaul.renewal.chess.chessman.King;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;

// 이 BoardSquare 클래스는 체스게임에 필요한 체스판의 하나의 칸으로써 역할을 하고자 만들었다.
// 굳이 BoardPanel에서 BoardSquare로 분리 시킨 이유는 
// 1. BoardSquare의 여러가지 상태를 BoardPanel에서 일일히 관리 하는 것이 BoardPanel의 임무/역할을 가중시킨다고 생각하였다.
//	   따라서 별도의 이를 클래스로 분리하여 칸들이 각각 하나의 BoardSquare로써 역할 하도록 하기 위해 분리하였다.  
//    (체스말이 놓여있다 / 놓여있지 않다 / 밝은색이다 / 어두운색이다 / 이동 할 수 있는 칸이다 / 공격 할 수 있는 칸이다 / 체크가 가능한 칸이다 )
// 2. BoardSquare을 하나의 뷰로써 그리고 데이터로써 역할 하기를 기대하였다. 
//	 위에 설명한 역할들은 주로 뷰 로써 BoardSqure의 역할을 설명한 것이다. 이러한 뷰의 역할을 하기 위해서는 BoardPanel에서 BoardSquare가 
//   데이터로 가지고 있는 chessman, isContain를 이용하여 데이터를 처리하고 뷰로 표현해 주는 과정이 필요하다고 판단하였다.
// final Color ORIGINAL_COLOR; -> BoardSquare가 탄생 할 때 지정되는 색상으로 이벤트 발생 이후 원래의 색상으로 돌아가기 위해 필요하다고 판단하였다.
// Chessman chessman; -> BoardSquare에 놓여 있는 말의 데이터를 갖고 있다가 말에 대한 이벤트를 처리하기 위해서 필요하다고 판단하였다.
//	boolean isContain; -> BoardSquare에 말이 놓여있는지 아닌지를 알려주는 변수이다. 해당 변수는 제거를 할 지 고민중이다. 이유는 chessman 변수가 말이 놓여있는지
//  					아닌지를 중복해서 표현 할 수 있다고 판단되기 때문이다.
@SuppressWarnings("serial")
public class BoardSquare extends FlatImagePanel {
	private final Color ORIGINAL_COLOR;
	private Chessman chessman;
	private boolean isContain;

	BoardSquare(Color originalColor) {
		ORIGINAL_COLOR = originalColor;
		
		setLayout(new LinearLayout(0));
		setBackground(ORIGINAL_COLOR);
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		setEnableClickEvent(false);
	}

	// 체스말을 네모칸위에 올려준다.
	public void setChessmanOnSquare(Chessman chessman) {
//		왕은 체크메이트 당한 경로로는 이동하지 못한다.
		if(getBackground().equals(Color.GREEN) && chessman instanceof King) {
		} else {
			this.chessman = chessman;
			isContain = true;
			setEnableClickEvent(true);
			setImage(chessman.getChessmanImage(), ImageOption.MATCH_PARENT);
		}
	}
// 칸의 색을 원래대로 돌려주는데 말이 없는 곳은 클릭이 되면 안된다고 생각했기 때문에 말이 있으면 클릭 가능하게하고 아니면 불가능하게 하도록 했다. 
	void setSquareOriginalColor() {
		setBackground(ORIGINAL_COLOR);
		setAlpha(1.0f);
		if (isContain) {
			setEnableClickEvent(true);
		} else {
			setEnableClickEvent(false);
		}
	}
	
// 해당 칸이 단순 이동이 가능한 칸이라고 표현해준다.
//	이때 체크메이트 경로임을 확인하는 이유는 다른말의 이동경로가
//	체크메이트 경로와 겹칠경우 체크메이트 경로 위로 이동하여 경로를 차단하는 것을 생각했기 때문이다.
	void setSquareMoveableColor() {
		if(getBackground().equals(Color.GREEN)) {
			setBackground(Color.GREEN);
			setEnableClickEvent(true);
			setAlpha(0.6f);
		} else {
			setBackground(Theme.LIGHT_BLUE_COLOR);
			setEnableClickEvent(true);
			setAlpha(0.6f);
		}
	}
//	해당 칸이 공격이 가능하다고 표현해준다
	void setSquareAttackableColor() {
		setBackground(Color.RED);
		setEnableClickEvent(true);
		setAlpha(0.6f);
	}
//	해당 칸이 체크메이트 경로임을 표현해준다.	
	void setSquareCheckmateColor() {
		setBackground(Color.GREEN);
		setEnableClickEvent(false);
		setAlpha(0.6f);
	}
//	해당 칸이 캐슬링이 가능하다고 표현해준다
	void setSquareCastlingColor() {
		setBackground(Color.YELLOW);
		setEnableClickEvent(true);
		setAlpha(0.6f);
	}

	// 체스말을 가지고있으면 체스말을 네모칸위에서 제거한다.
	void removeChessmanFromSquare() {
		chessman = null;
		isContain = false;
		setImage(null);
	}
//	칸 위에 체스말이 있는지 없는지를 반환한다.
	boolean isContain() {
		return isContain;
	}

	void setEnableClickEvent(boolean enable) {
		setEnabled(enable);
	}
//	칸이 가지고있는 체스말을 반환하는 메서드로써 체스말에 대한 이벤트들(이동 관련)을 처리할때 사용한다.
//	고민되는 부분은 chessman이 없을때는 null을 반환하는데
//	그러다보니 BoardPanel에서 이를 검사할때 1. isContain이 true인지, 2. chessman이 null이 아닌지
//	한단계 더 나아가면 3. 체스맨이 킹인지 룩인지 등등... 기본 3단계의 논리검사를 시행해야되서 이를 처리하는데 고민이 된다.
	public Chessman getChessman() {
		return chessman;
	}
}

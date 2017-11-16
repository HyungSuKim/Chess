package com.bfpaul.chess.board;

import com.bfpaul.chess.chessman.Chessman;
import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
// 체스판의 하나의 네모칸으로써 체스말의 이동경로나 체스말을 놓거나 치우거나 하는 이벤트가 표현되는 가장 작은단위의 칸이다.
@SuppressWarnings("serial")
public class BoardSquare extends FlatPanel {
//	체스말을 가지고있는지 아닌지
	private boolean isContain;
	
	BoardSquare() {	}
	
//	가지고있는 체스말이 없으면 체스말을 네모칸위에 올려준다.
	void setChessmanOnSquare(Chessman chessman) {
		if(!isContain) {
			isContain = true;
			add(chessman, new LinearConstraints(1, LinearSpace.MATCH_PARENT));	
		}
	}
//	체스말을 가지고있으면 체스말을 네모칸위에서 제거한다.
	void removeChessmanFromSquare() {
		if(isContain) {
			isContain = false;
			remove(0);
		}
	}
	
}

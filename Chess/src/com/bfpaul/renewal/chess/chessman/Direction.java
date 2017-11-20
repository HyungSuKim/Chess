package com.bfpaul.renewal.chess.chessman;
// 체스말이 이동 할 때 아무렇게나 이동하면 안되기때문에 이동할 수 있는 특정 방향이 필요하다.
// 이 클래스는 그 특정방향을 구현한다.
enum Direction {
	UP, // 위쪽 방향
	DOWN, // 아래쪽 방향
	LEFT, // 왼쪽 방향
	RIGHT, // 오른쪽 방향
	UP_LEFT, // 왼쪽 위로 기운 대각선 방향
	UP_RIGHT, // 오른쪽위로 기운 대각선 방향
	DOWN_LEFT, // 왼쪽 아래로 기운 대각선 방향
	DOWN_RIGHT; // 오른쪽 아래로 기운 대각선 방향
}

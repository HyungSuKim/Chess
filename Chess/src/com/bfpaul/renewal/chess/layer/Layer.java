package com.bfpaul.renewal.chess.layer;

public interface Layer {
	public void execute(Object[] object);
	public boolean isFinish();
	public Object[] getDatas();
}

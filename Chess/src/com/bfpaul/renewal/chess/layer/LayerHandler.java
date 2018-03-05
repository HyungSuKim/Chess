package com.bfpaul.renewal.chess.layer;

import java.util.ArrayList;

public class LayerHandler {
	private ArrayList<Layer> layerList = new ArrayList<>();
	
	public LayerHandler addLayer(Layer layer) {
		layerList.add(layer);
		return this;
	}
	
	public LayerHandler execute() {
		new Thread(()-> {
			try {
				while(true) {
					layerList.get(0).execute(layerList.get(2).getDatas());
					while(!layerList.get(0).isFinish()) {
						Thread.sleep(100);
					}
					
					layerList.get(1).execute(layerList.get(0).getDatas());
					
					layerList.get(2).execute(layerList.get(1).getDatas());
					while(!layerList.get(2).isFinish()) {
						Thread.sleep(100);
					}
				}
			} catch(Exception e) {
				
			}
		}).start();
		
		return this;
	}
}

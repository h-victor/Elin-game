package com.mygdx.game;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class gameStage extends Overlap2DStage{
	public ResourceManager rs;
	public SceneVO sl;
			
	public gameStage(ResourceManager rs){
			
		initSceneLoader();
		sl = sceneLoader.loadScene("MainScene");
		addActor(sceneLoader.getRoot());
	
		play play_ = new play(/*rs, sl_*/ getCamera());
		sceneLoader.getRoot().addScript(play_);//getCompositeById("player").addScript(play_);
	}
}

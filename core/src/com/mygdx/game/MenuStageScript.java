package com.mygdx.game;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

public class MenuStageScript implements IScript {
	private MenuStage menuStage_;
	private CompositeItem menuSceneActors;
	private CompositeItem button;
	private SimpleButtonScript playButtonScript; 
	public static boolean startGameStage = false;
	
	public MenuStageScript(MenuStage menuStage_){
		this.menuStage_ = menuStage_;
	}

	@Override
	public void init(CompositeItem item) {
		menuSceneActors = item;	
		button = menuSceneActors.getCompositeById("button");
		playButtonScript = new SimpleButtonScript();
		playButtonScript.init(button);
	}

	@Override
	public void act(float delta) {
		playButtonScript.act(delta);
		if (playButtonScript.isDown()){
			startGameStage = true; 
			dispose();
		}
	}

	@Override
	public void dispose() {
		menuStage_.dispose();
	}
}

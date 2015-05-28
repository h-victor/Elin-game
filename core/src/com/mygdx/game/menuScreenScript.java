package com.mygdx.game;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

public class menuScreenScript implements IScript {
	
	private menuStage menuStage_;
	private CompositeItem menu;
	private CompositeItem button;
	
	private SimpleButtonScript playButtonScript; 
	
	public static boolean startGameStage = false; // boolean, true when we hit the play button
	
	public menuScreenScript(menuStage menuStage_){
		this.menuStage_ = menuStage_;
	}

	@Override
	public void init(CompositeItem item) {
		menu = item;	
		button = menu.getCompositeById("button");
		playButtonScript = new SimpleButtonScript();
		playButtonScript.init(button);
	}

	@Override
	public void act(float delta) {
		playButtonScript.act(delta);
		
		if (playButtonScript.isDown()){
			startGameStage = true;
			menuStage_.clear(); // clear the menuStage
		}
	}

	@Override
	public void dispose() {
		button.dispose();
		playButtonScript.dispose();
	}
	
}
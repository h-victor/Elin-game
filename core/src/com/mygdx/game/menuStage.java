package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.uwsoft.editor.renderer.Overlap2DStage;

public class menuStage extends Overlap2DStage {
	public menuStage(elin menu){
		super();
		
		Gdx.input.setInputProcessor(this);	

		initSceneLoader();
		
		initMenu();
	}
	
	private void initMenu(){
		clear();
		sceneLoader.loadScene("MenuScene");
		
		menuScreenScript menuScript= new menuScreenScript(this);
		sceneLoader.getRoot().addScript(menuScript);
		
		addActor(sceneLoader.getRoot());
	}

}

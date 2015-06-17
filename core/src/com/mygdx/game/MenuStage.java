package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class MenuStage extends Overlap2DStage {

	public MenuStage(ResourceManager resourceManager){
		super();
		Gdx.input.setInputProcessor(this);	
		initSceneLoader(resourceManager);
		initMenu();
	}
	
	private void initMenu(){
		clear();
		sceneLoader.loadScene("Menu"); //("MenuScene");
		MenuStageScript menuScript= new MenuStageScript(this);
		sceneLoader.getRoot().addScript(menuScript);
		addActor(sceneLoader.getRoot());
	}

}

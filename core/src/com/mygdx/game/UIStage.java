package com.mygdx.game;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class UIStage extends Overlap2DStage{

	public UIStage(ResourceManager resourceManager, GameStage gameStage_) {
		super(new FitViewport(resourceManager.getProjectVO().originalResolution.width, resourceManager.getProjectVO().originalResolution.height));
		initSceneLoader(resourceManager);
		initUI();
	}

	private void initUI() {
		clear();
		sceneLoader.loadScene("UIScene"); //("MenuScene");
//		UIScript uiScript= new UIScript(this);
//		sceneLoader.getRoot().addScript(uiScript);
		addActor(sceneLoader.getRoot());
		
	}



}

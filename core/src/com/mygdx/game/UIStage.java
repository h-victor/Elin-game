package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

public class UIStage extends Overlap2DStage{

	private GameStage gameStage;

	/**
	 * @return the gameStage
	 */
	

	public UIStage(ResourceManager resourceManager, GameStage gameStage_) {
		super(new FitViewport(resourceManager.getProjectVO().originalResolution.width, resourceManager.getProjectVO().originalResolution.height));
		
		initSceneLoader(resourceManager);
		initUI();
		gameStage=gameStage_;
	}

	private void initUI() {
		clear();
		sceneLoader.loadScene("UIScene"); 
		UIScript uiStageScript = new UIScript(this);
		sceneLoader.getRoot().addScript(uiStageScript);
		addActor(sceneLoader.getRoot());
		
	}
	
	public GameStage getGameStage() {
		return gameStage;
	}
}

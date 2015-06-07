package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class ElinApplication extends ApplicationAdapter {
	
	private GameStage gameStage_;
	private MenuStage menuStage_;
	private ResourceManager resourceManager;
	private UIStage uiStage;

	@Override
	public void create () {
		resourceManager = new ResourceManager();
		resourceManager.initAllResources();
		
		menuStage_ = new MenuStage(resourceManager);
		gameStage_ = new GameStage(resourceManager);
		uiStage= new UIStage(resourceManager,gameStage_);
	
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		menuStage_.act(Gdx.graphics.getDeltaTime());
		menuStage_.draw();
		
		if(MenuStageScript.startGameStage == true){ // if true, draw the MenuStage
		gameStage_.act(Gdx.graphics.getDeltaTime());
		gameStage_.draw();
		uiStage.act(Gdx.graphics.getDeltaTime());
		uiStage.draw();
		}
	}

}

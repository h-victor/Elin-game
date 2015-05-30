package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class elin extends ApplicationAdapter {
	
	private gameStage gameStage_;
	private menuStage menuStage_;

	public ResourceManager rs;

	@Override
	public void create () {
		menuStage_ = new menuStage(this);
		gameStage_ = new gameStage(rs);
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		menuStage_.act(Gdx.graphics.getDeltaTime());
		menuStage_.draw();
		
		if(menuScreenScript.startGameStage == true){ // if true, draw the MenuStage
			gameStage_.act(Gdx.graphics.getDeltaTime());
			gameStage_.draw();
		}
	}

}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class elin extends ApplicationAdapter {
	
	private gameStage stage2;
	private menuStage stage;

	public ResourceManager rs;

	@Override
	public void create () {
		stage = new menuStage(this);
		stage2 = new gameStage(rs);
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		if(menuScreenScript.startGameStage == true){ // if true, draw the MenuStage
			stage2.act(Gdx.graphics.getDeltaTime());
			stage2.draw();
		}
	}

}

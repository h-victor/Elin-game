package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class elin extends ApplicationAdapter {
	private gameStage stage;
	
	public ResourceManager rs;

	@Override
	public void create () {
		rs = new ResourceManager();
		rs.initAllResources();
		
		stage = new gameStage(rs);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}
}

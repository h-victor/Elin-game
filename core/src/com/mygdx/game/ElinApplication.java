package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.functionality.MyGestureListener;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class ElinApplication extends ApplicationAdapter {
	private GameStage gameStage_;
	private MenuStage menuStage_; 
	

	private UIStage uiStage; 
	private DialogStage dialogStage;

	private ResourceManager resourceManager;
	private InputMultiplexer inputMultiplexer;
	

	@Override
	public void create () {
		resourceManager = new ResourceManager();
		resourceManager.initAllResources();

		// Gesture Detector
		//		myGestureListener = new MyGestureListener();

		menuStage_ = new MenuStage(resourceManager);
		gameStage_ = new GameStage(resourceManager/*, myGestureListener*/);
		dialogStage = new DialogStage(gameStage_/*, myGestureListener*/);
		//        uiStage = new UIStage(resourceManager,gameStage_);


		        inputMultiplexer = new InputMultiplexer();
		        inputMultiplexer.addProcessor(gameStage_);
		        inputMultiplexer.addProcessor(new GestureDetector(new MyGestureListener(this)));
		        inputMultiplexer.addProcessor(dialogStage);
		//        inputMultiplexer.addProcessor(uiStage);
		        inputMultiplexer.addProcessor(menuStage_);
		        Gdx.input.setInputProcessor(inputMultiplexer);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		if(MenuStageScript.startGameStage == true){ // if true, draw the MenuStage
			gameStage_.act(Gdx.graphics.getDeltaTime());
			gameStage_.draw();
						dialogStage.act();
						dialogStage.draw();
			//			uiStage.act(Gdx.graphics.getDeltaTime());
			//			uiStage.draw();


		}
		else {
			menuStage_.act(Gdx.graphics.getDeltaTime());
			menuStage_.draw();}
	}
	
	/**
	 * @return the gameStage_
	 */
	public GameStage getGameStage_() {
		return gameStage_;
	}

	/**
	 * @return the dialogStage
	 */
	public DialogStage getDialogStage() {
		return dialogStage;
	}
}

package com.mygdx.elin;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.SceneLoader;

public class GameStage extends Stage {

	public GameStage() {
		// TODO Auto-generated constructor stub
		SceneLoader sl = new SceneLoader();
		sl.loadScene("MainScene");
		addActor(sl.getRoot());
	}
}

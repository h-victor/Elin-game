package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.uwsoft.editor.renderer.actor.SpriteAnimation;
import com.uwsoft.editor.renderer.Overlap2DStage;
	import com.uwsoft.editor.renderer.actor.CompositeItem;
	//import com.uwsoft.ed

public class player {
		private Overlap2DStage stage;
		//private SpriteAnimation animation;
	
	public player(Overlap2DStage stage){
		this.stage = stage;

		//CompositeItem item = item;
		//animation = item.getSpriteAnimationById("elin");
		//animation.pause();
		//sceneLoader.LoadScene("MainScene").getRoot().getSpriteAnimationById("elin");
	}

	public void handleInput(){
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			
		}
	}
}

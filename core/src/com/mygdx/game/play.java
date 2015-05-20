package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriteAnimation;
import com.uwsoft.editor.renderer.script.IScript;

public class play implements IScript{
	private CompositeItem item;
	private SpriteAnimation animation;
	
	public boolean plan2 = false; // boolean, is true when player in the second plan

	//public ResourceManager rs;
	//public SceneVO sl;
	
	public play(/*ResourceManager rs, SceneVO sl*/){
		//this.rs = rs;
		//this.sl = sl;
	}

	@Override
	public void init(CompositeItem item) {
		this.item = item;
		animation = item.getCompositeById("player").getSpriteAnimationById("elin");//item.getSpriteAnimationById("elin");
		animation.pause();
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void act(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			//item.getCompositeById("player").getBody().applyForceToCenter(20, 0, false);
			item.getCompositeById("player").getBody().applyLinearImpulse(1, 0, item.getX(), item.getY(), true);
			animation.start();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			//item.getCompositeById("player").getBody().applyForceToCenter(-20, 0, false);
			item.getCompositeById("player").getBody().applyLinearImpulse(-1, 0, item.getX(), item.getY(), true);
			animation.start();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){ //isKeyPressed(Input.Keys.SPACE)){
			if(plan2 == false){
				/*item.getCompositeById("player").getSpriteAnimationById("elin").setY( //.getBody().setTransform(0, 20);
						item.getCompositeById("ground2").getY() //+
						//item.getCompositeById("ground2").getHeight() / 2
						//, 0
						);
				*/
				item.getCompositeById("player").getBody().setTransform(
					item.getCompositeById("player").getX() / 10
					, 20, 0);

				plan2 = true;
			}
			else if(plan2 == true){
				/*item.getCompositeById("player").getSpriteAnimationById("elin").setY( //.getBody().setTransform(0, 20);
						item.getCompositeById("ground1").getY() //+
						//item.getCompositeById("ground2").getHeight() / 2
						//, 0
						);
				*/
				item.getCompositeById("player").getBody().setTransform(
						item.getCompositeById("player").getX() / 10
						, 4, 0);
				plan2 = false;
			}			

		}
	}
}

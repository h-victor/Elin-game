package com.mygdx.functionality;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;

public class touchScreen {

	private boolean isWalking = false;
	private float moveSpeed;
	private float verticalSpeed = 0;
	private SpriterActor spriterActor; 
	private float delta = Gdx.graphics.getDeltaTime();

	private CompositeItem item;
	public Camera camera;
	
	public touchScreen(Camera camera, CompositeItem item){
		this.camera = camera;
		
		this.item = item;
		moveSpeed = 150f * item.mulX;
		spriterActor = item.getSpriterActorById("animation");
	}
	
	public void moveMarten(){
		/* Touch-screen */
		if(Gdx.input.isTouched()){
			if(Gdx.input.getX() > camera.viewportWidth / 2){
				item.getCompositeById("marten").getBody().applyLinearImpulse(0.5f, 0, item.getX(), item.getY(), true);
				item.getCompositeById("marten").setScaleX(1);
				//animation.start();
			}
			else if(Gdx.input.getX() < camera.viewportWidth / 2){
				item.getCompositeById("marten").getBody().applyLinearImpulse(-0.5f, 0, item.getX(), item.getY(), true);
				item.getCompositeById("marten").setOrigin(0);
				item.getCompositeById("marten").setScaleX(-1);
				//animation.start();
			}
		}
	}
	
	public void elinFollowMarten(){ // based on Overlap2D tutorial
		boolean wasWalking = isWalking;

		isWalking = false;
		if(item.getCompositeById("elin").getX()<item.getCompositeById("marten").getX()+150) {
			// Go right
			item.getCompositeById("elin").setX(item.getCompositeById("elin").getX() + delta*moveSpeed);
			item.getCompositeById("elin").setScaleX(1f);
			isWalking = true;
			//spriterActor.setAnimation(spriterActor.getAnimations().indexOf("marche"));
		}
		if(item.getCompositeById("elin").getX()>item.getCompositeById("marten").getX()-150) {
			// Go left
			item.getCompositeById("elin").setX(item.getCompositeById("elin").getX() - delta*moveSpeed);
			item.getCompositeById("elin").setScaleX(-1f);
			isWalking = true;
			//spriterActor.setAnimation(spriterActor.getAnimations().indexOf("marche"));
		}

		if(wasWalking == true && isWalking == false) {
			// Not walking anymore stop the animation;
			spriterActor.looping=false;
			spriterActor.setAnimation(spriterActor.getAnimations().indexOf("debout"));
			
		}
		if(wasWalking == false && isWalking == true) {
			// just started to walk, start animation
			spriterActor.looping=false;
			spriterActor.setAnimation(spriterActor.getAnimations().indexOf("toucher"));
			spriterActor.setAnimation(spriterActor.getAnimations().indexOf("marche"));
		}
		
		// set the position
		item.getCompositeById("elin").setY(item.getCompositeById("elin").getY() + verticalSpeed*delta);

	}
}

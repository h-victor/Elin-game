
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.actor.CompositeItem;
//import com.uwsoft.editor.renderer.actor.SpriteAnimation;
import com.uwsoft.editor.renderer.script.IScript;

public class play implements IScript{
	private CompositeItem item;
	//private SpriteAnimation animation;
    //private float verticalSpeed = 0;
    
	public boolean plan2 = false; // boolean, is true when player in the second plan
	public boolean landscape = true; // boolean, is true when camera is in landscape mode

	//public ResourceManager rs;
	//public SceneVO sl;
	public Camera camera;

	public int accelerometerX, accelerometerY, accelerometerZ;
	public boolean smartphoneFlat, smartphoneLandscape, smartphonePortrait;
	
	public play(Camera camera/*ResourceManager rs, SceneVO sl*/){
		//this.rs = rs;
		//this.sl = sl;
		this.camera = camera;
	}
	
	@Override
	public void init(CompositeItem item) {
		this.item = item;
		//animation = item.getCompositeById("player").getSpriteAnimationById("elin");//item.getSpriteAnimationById("elin");
		//animation.pause();
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void act(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
			//item.getCompositeById("player").getBody().applyForceToCenter(20, 0, false);
			item.getCompositeById("player").getBody().applyLinearImpulse(1, 0, item.getX(), item.getY(), true);
			item.getCompositeById("player").setScaleX(1);
			//animation.start();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
			//item.getCompositeById("player").getBody().applyForceToCenter(-20, 0, false);
			item.getCompositeById("player").getBody().applyLinearImpulse(-1, 0, item.getX(), item.getY(), true);
			item.getCompositeById("player").setOrigin(0);
			item.getCompositeById("player").setScaleX(-1);
			//animation.start();
		}
		
		/* Touch-screen */
		if(Gdx.input.isTouched()){
			if(Gdx.input.getX() > camera.viewportWidth / 2){
				item.getCompositeById("player").getBody().applyLinearImpulse(0.5f, 0, item.getX(), item.getY(), true);
				item.getCompositeById("player").setScaleX(1);
				//animation.start();
			}
			else if(Gdx.input.getX() < camera.viewportWidth / 2){
				item.getCompositeById("player").getBody().applyLinearImpulse(-0.5f, 0, item.getX(), item.getY(), true);
				item.getCompositeById("player").setOrigin(0);
				item.getCompositeById("player").setScaleX(-1);
				//animation.start();
			}
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
					item.getCompositeById("player").getX() / 10,
					item.getCompositeById("ground2").getY() / 10, 0
				);

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
						item.getCompositeById("player").getX() / 10,
						item.getCompositeById("ground1").getY() / 10, 0);
				plan2 = false;
			}						
		}
		
		/* Set camera center to player */
		camera.position.x =
				item.getCompositeById("player").getX();
		camera.position.y =
				item.getCompositeById("player").getY();
		camera.update();
		
		/* Rotate the camera to landscape to portrait */
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
			if(landscape == true){
				camera.rotate(camera.direction, 90f); 
				camera.update();
				landscape = false;
			}
			else if(landscape == false){
				camera.rotate(camera.direction, -90f); 
				camera.update();
				landscape = true;
			}
		}			
	
		/* Accelerometer to landscape to portrait */
		accelerometerX = (int) Gdx.input.getAccelerometerX();
		accelerometerY = (int) Gdx.input.getAccelerometerY();
		accelerometerZ = (int) Gdx.input.getAccelerometerZ();
	
		if(accelerometerX == 0 && accelerometerY == 0 && accelerometerZ == 10){
			smartphoneFlat = true;
			smartphoneLandscape = false;
			smartphonePortrait = false;
		}
		else if(accelerometerX == 10 && accelerometerY == 0 && accelerometerZ == 0){
			smartphoneFlat = false;
			smartphoneLandscape = true;
			smartphonePortrait = false;
		}
		else if(accelerometerX == 0 && accelerometerY == 10 && accelerometerZ == 0){
			smartphoneFlat = false;
			smartphoneLandscape = false;
			smartphonePortrait = true;			
		}
		
		if(smartphoneLandscape){
			camera.rotate(camera.direction, 90f); 
			camera.update();
		}
		else if(smartphonePortrait){
			camera.rotate(camera.direction, -90f); 
			camera.update();
		}
		
	}
}
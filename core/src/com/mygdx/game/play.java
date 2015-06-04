
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.functionality.accelerometer;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
//import com.badlogic.gdx.math.Vector2;
//import com.uwsoft.editor.renderer.actor.SpriteAnimation;

public class play implements IScript{
	private CompositeItem item;
	//private SpriteAnimation animation;
    //private float verticalSpeed = 0;
    
	public boolean plan2 = false; // boolean, is true when player in the second plan
	public boolean landscape = true; // boolean, is true when camera is in landscape mode

	//public ResourceManager rs;
	//public SceneVO sl;
	public Camera camera;
	
	private accelerometer accelerometer_;

		String message = "no";
	
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

		/*accelerometer */accelerometer_ = new accelerometer(camera);
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
		
		/* accelerometer rotation camera to landscape to portrait */
		accelerometer_.getAccelerometerPosition();
		accelerometer_.rotateCamera();

		if(accelerometer_.returnSmartphoneNormal()){
			item.getCompositeById("player").getBody().setTransform(
					item.getCompositeById("player").getX() / 10,
					item.getCompositeById("ground2").getY() / 10, 0);
			Gdx.input.vibrate(1000);
		}
		
		if(accelerometer_.returnSmartphoneinverse()){
			item.getCompositeById("player").getBody().setTransform(
					item.getCompositeById("player").getX() / 10,
					item.getCompositeById("ground1").getY() / 10, 0);
			Gdx.input.vibrate(1000);
		}
		
		
		
		/* Text input */
		//if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
		if(Gdx.input.justTouched()){
			Gdx.input.getTextInput(new TextInputListener() {
					@Override
					public void input(String text){
						message = text;
						if(message.equals("aspirateur")){Gdx.input.vibrate(1000);}
					}
					
					@Override
					public void canceled(){
						message = "no";
					}
				}, "Je ne respire jamais, mais j'ai beaucoup de soufle. Qui suis-je ?", "", "");
		}
	
		/* raise a little the smartphone */
		if(accelerometer_.raiseALittleSmartphone()){
			if(plan2 == false){
				item.getCompositeById("player").getBody().setTransform(
					item.getCompositeById("player").getX() / 10,
					item.getCompositeById("ground2").getY() / 10, 0);
				plan2 = true;
			}
			else if(plan2 == true){
				item.getCompositeById("player").getBody().setTransform(
						item.getCompositeById("player").getX() / 10,
						item.getCompositeById("ground1").getY() / 10, 0);
				plan2 = false;
			}		
		}
		
	}
	
	
}
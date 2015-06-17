package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.functionality.MyGestureListener;
import com.mygdx.functionality.accelerometer;
import com.mygdx.functionality.touchScreen;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/*
 * contient la logique de la scene : changer de plan, tourner la camera, remonter dans le temps, dialogue
 */

public class GameStageScript implements IScript{
	private CompositeItem item;
	public boolean plan2 = false; // boolean, is true when player in the second plan
	public boolean landscape = true; // boolean, is true when camera is in landscape mode
	public OrthographicCamera camera;
	private accelerometer accelerometer_;
	String message = "no";
	private GameStage stage;
	
	MyGestureListener myGestureListener;
	boolean first = false;
	
	public GameStageScript(GameStage stage){
		this.stage= stage;
		this.camera=(OrthographicCamera) stage.getCamera();
	}

	@Override
	public void init(CompositeItem item) {
		this.item = item;
		accelerometer_ = new accelerometer(camera);
		
		touchScreen touchScreen_ = new touchScreen(camera, item);
		touchScreen_.elinFollowMarten();

/* (A supprimer)
		Dialog dialog = new Dialog(this.item);
		dialog.readFile();
		dialog.readLine("#beginIntroduction", "endIntroduction");
		dialog.readLine("#beginCalin", "#endCalin");
		item.essentials.rm.getSceneVO("DialogScreen").composite.sLabels.get(0).text = "hello";
*/
		// Gesture Detector
		myGestureListener = new MyGestureListener();
	}

	@Override
	public void dispose() {
		item.dispose();
		stage.dispose();
	}

	@Override
	public void act(float delta) {
		camera.position.x = item.getCompositeById("elin").getX();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)||accelerometer_.raiseALittleSmartphone()){
			changementPlan();						
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
			/* Rotate the camera to landscape to portrait */
			cameraRotation();
		}			

		/* accelerometer rotation camera to landscape to portrait */
		accelerometer_.getAccelerometerPosition();
		accelerometer_.rotateCamera();

		if(accelerometer_.returnSmartphoneNormal()){
			item.getCompositeById("elin").getBody().setTransform(
					item.getCompositeById("elin").getX() / 10,
					item.getCompositeById("ground2").getY() / 10, 0);
			Gdx.input.vibrate(1000);
		}

		if(accelerometer_.returnSmartphoneinverse()){
			item.getCompositeById("elin").getBody().setTransform(
					item.getCompositeById("elin").getX() / 10,
					item.getCompositeById("ground1").getY() / 10, 0);
			Gdx.input.vibrate(1000);
		}

		/* Text input */
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
			showEnigma();
		}

		//zoom et dezoom
/*		if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)||Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
			if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)){
				camera.zoom -= 0.1f; 
				camera.update();
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)){
				camera.zoom +=0.1f; 
				camera.update();
			}
		}
*/					
		
		/* Creation of bridge */
		if (Gdx.input.isKeyJustPressed(Input.Keys.J)){
			item.setLayerVisibilty("pont", true);
			item.setLayerLock("pont", false);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.Y)){
			item.setLayerVisibilty("pont", true);
			item.getItemsByLayerName("pont").get(0).getBody().setActive(false);
		}

		/* Move Elin */
		float moveSpeed = 220f * this.item.getCompositeById("elin").mulX;

		if(Gdx.input.isTouched()){
			if(Gdx.input.getX() > camera.viewportWidth / 2){
				item.getCompositeById("elin").setX(item.getCompositeById("elin").getX() + Gdx.graphics.getDeltaTime()*moveSpeed);
				if(item.getCompositeById("elin").getScaleX()<0){
					item.getCompositeById("elin").setScaleX(item.getCompositeById("elin").getScaleX()*-1f);
				}
			}
			else if(Gdx.input.getX() < camera.viewportWidth / 2 ){
				item.getCompositeById("elin").setX(item.getCompositeById("elin").getX() - Gdx.graphics.getDeltaTime()*moveSpeed);
				if(item.getCompositeById("elin").getScaleX()>0){
					item.getCompositeById("elin").setScaleX(item.getCompositeById("elin").getScaleX()*-1f);
				}
			}
		}
	}

	private void showEnigma() {
		Gdx.input.getTextInput(new TextInputListener() {
			@Override
			public void input(String text){
				message = text;
				if(message/*.equals("aspirateur")*/ == "aspirateur"){Gdx.input.vibrate(1000);}
			}

			@Override
			public void canceled(){
				message = "no";
			}
		}, "Je ne respire jamais, mais j'ai beaucoup de soufle. Qui suis-je ?", "", "");
	}

	private void cameraRotation() {
		if(landscape == true){
			camera.rotate(camera.direction, 90f); 
			//camera.position.y = item.getCompositeById("player").getY();
			camera.update();
			landscape = false;
		}
		else if(landscape == false){
			camera.rotate(camera.direction, -90f); 
			//camera.position.y = item.getCompositeById("player").getY()+500;
			camera.update();
			landscape = true;
		}
	}

	private void changementPlan() {
		if(plan2 == false){
			item.getCompositeById("elin").setScale(.4f);
			item.getCompositeById("marten").setScale(.4f);
			item.getCompositeById("elin").setPosition(
					item.getCompositeById("elin").getX(),
					item.getCompositeById("ground2").getTop() );
			item.getCompositeById("marten").setPosition(
					item.getCompositeById("marten").getX(),
					item.getCompositeById("ground2").getTop() );
			plan2 = true;
		}
		else if(plan2 == true){
			item.getCompositeById("elin").setScale(.5f);
			item.getCompositeById("elin").setPosition(
					item.getCompositeById("elin").getX(),
					item.getCompositeById("ground1").getTop());
			item.getCompositeById("marten").setScale(.5f);
			item.getCompositeById("marten").setPosition(
					item.getCompositeById("marten").getX(),
					item.getCompositeById("ground1").getTop());
			plan2 = false;
		}
	}
}

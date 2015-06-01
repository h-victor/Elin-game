package com.projet.elin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;


public class GameStage extends Overlap2DStage {

	PlatformerResourceManager rm;

	private PlayerController player;
	private PlayerController2 player2;
	private CompositeItem Actor1;
	private CompositeItem Actor2;

	private OrthographicCamera camera;

	private boolean landscape;

	public boolean isLandscape() {
		return landscape;
	}



	public void setLandscape(boolean landscape) {
		this.landscape = landscape;
	}

	private boolean isPast;

	public GameStage(PlatformerResourceManager rm) {
		super(new StretchViewport(rm.stageWidth, rm.currentResolution.height));

		this.rm = rm;
		isPast=false;
		// This will create SceneLoader instance and configure all things like default resource manager, physics e.t.c
		initSceneLoader(rm);
		sceneLoader.setResolution(rm.currentResolution.name);

		// This will load MainScene data from json and make Actors from it
		sceneLoader.loadScene("MainScene");

		// Get the root actor and add it to stage
		addActor(sceneLoader.getRoot());
		Actor1 = sceneLoader.getRoot().getCompositeById("player");
		Actor2 = sceneLoader.getRoot().getCompositeById("player2");

		player = new PlayerController(this);
		player2 = new PlayerController2(this);

		Actor1.addScript(player);
		Actor2.addScript(player2);

		for(IBaseItem item: sceneLoader.getRoot().getItems()) {
			if(item.getCustomVariables().getFloatVariable("platformSpeed") != null && item.isComposite()) {
				((CompositeItem)item).addScript(new MovingPlatform());
			}
			if(item.getCustomVariables().getStringVariable("arbre")!=null&&item.isComposite()){
				((CompositeItem)item).addScript(new TreeScript());
			}
			if(item.getCustomVariables().getStringVariable("bougie")!=null&&item.isComposite()){
				((CompositeItem)item).addScript(new CandleScript());
			}
		}
	}



public void act(float delta) {
	super.act(delta);
	camera = (OrthographicCamera)getCamera();
	camera.position.x = player.getActor().getX();
	//pr pc
	if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
		if(landscape == true){
			camera.rotate(camera.direction, 90f); 
			camera.position.y = player.getActor().getY();
			camera.update();
			landscape = false;
		}
		else if(landscape == false){
			camera.rotate(camera.direction, -90f); 
			camera.position.y = player.getActor().getY()+500;
			camera.update();
			landscape = true;
		}}
	//pr smartphone
	if(Gdx.input.getRotation()>60)
		if(Gdx.input.getNativeOrientation()== Input.Orientation.Landscape){
			camera.rotate(camera.direction, 90f); 
			camera.position.x = player.getActor().getY();
			camera.update();
			landscape = false;
		}
		else if(Gdx.input.getNativeOrientation()== Input.Orientation.Portrait){
			camera.rotate(camera.direction, -90f); 
			camera.position.x = player.getActor().getY()-50;
			camera.update();
			landscape = true;
		}

	//zoom et dezoom
	if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)||Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
		if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)){
			camera.zoom -= 0.1f; 
			camera.update();
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)){
			camera.zoom +=0.1f; 
			camera.update();
		}}

	if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
		if(isPast==false)
			PastStage();
		else if(isPast==true)
			PresentStage();
	}
	if (Gdx.input.isKeyJustPressed(Input.Keys.L)){
		sceneLoader.getRoot().setLayerVisibilty("pont", true);
		sceneLoader.getRoot().setLayerLock("pont", false);
	}
	if (Gdx.input.isKeyJustPressed(Input.Keys.B)){
		sceneLoader.getRoot().setLayerVisibilty("pont", true);
		sceneLoader.getRoot().getItemsByLayerName("pont").get(0).getBody().setActive(false);
	}
}

private void PresentStage() {
	clear();
	sceneLoader.loadScene("MainScene");

	// Get the root actor and add it to stage
	addActor(sceneLoader.getRoot());
	Actor1 = sceneLoader.getRoot().getCompositeById("player");
	Actor2 = sceneLoader.getRoot().getCompositeById("player2");

	Actor1.addScript(player);
	Actor2.addScript(player2);

	for(IBaseItem item: sceneLoader.getRoot().getItems()) {
		if(item.getCustomVariables().getFloatVariable("platformSpeed") != null && item.isComposite()) {
			((CompositeItem)item).addScript(new MovingPlatform());
		}
	}
	isPast=false;

}

public void restart() {
	player.reset();
	player2.reset();
}

public void PastStage(){
	clear();
	//    initSceneLoader(rm);
	//    sceneLoader.setResolution(rm.currentResolution.name);
	sceneLoader.loadScene("passe");
	Actor1.addScript(player);
	Actor2.addScript(player2);
	addActor(sceneLoader.getRoot());
	addActor(Actor1);
	addActor(Actor2);
	isPast=true;

}

}

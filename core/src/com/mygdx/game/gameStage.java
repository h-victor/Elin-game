package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class GameStage extends Overlap2DStage{
	public SceneVO mainScene;
	public CompositeItem elin;
	public CompositeItem marten;
	public Array<CompositeItem> pigs;
	public Array<CompositeItem> questItems;
	public Array<CompositeItem> food;
	public CompositeItem presentSave;
	
	private MartenScript martenScript;
	private ElinScript elinScript;
	private GameStageScript gameStageScript;
	
	
	public GameStage(ResourceManager resourceManager){
		 super(new FitViewport(resourceManager.getProjectVO().originalResolution.width, resourceManager.getProjectVO().originalResolution.height));
		initSceneLoader(resourceManager);
		initMainScene();
	}
	
	private void initMainScene() {
		SceneVO mainScene = sceneLoader.loadScene("MainScene");//Load scene data: world physic , resolution, light 
		addActor(sceneLoader.sceneActor);
		martenScript=new MartenScript(this);
		elinScript= new ElinScript(this);
		elin=sceneLoader.sceneActor.getCompositeById("elin");
		marten= sceneLoader.sceneActor.getCompositeById("marten");
		elin.addScript(elinScript);
		marten.addScript(martenScript);
		gameStageScript = new GameStageScript(this);
		sceneLoader.sceneActor.addScript(gameStageScript);
		
        for(IBaseItem item: sceneLoader.sceneActor.getItems()) {
            if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null && item.isComposite()) {
                ((CompositeItem)item).addScript(new MovingPigScript());
            }
        }
//    		monster monster_ = new monster();
//    		sceneLoader.getRoot().addScript(monster_);
//    		
//    		Music music = Gdx.audio.newMusic(Gdx.files.internal("Celestial_Aeon_Project_-_Children.mp3"));
//    		music.play();
//    		music.setLooping(true);
//        }
	}
	/* Loading another scene -> go back in time */
	public void goToPast(){
		presentSave=sceneLoader.getRoot();
		clear();
		sceneLoader.loadScene("PastScene");
		addActor(sceneLoader.sceneActor);
		addActor(elin);
		addActor(marten);
		sceneLoader.sceneActor.addScript(gameStageScript);
		gameStageScript.isPast = true;
	}

	
	public void returnToPresent() {
		clear();
		presentSave.removeActor(presentSave.findActor("elin"));
		presentSave.removeActor(presentSave.findActor("marten"));
		addActor(presentSave);
		addActor(elin);
		addActor(marten);
		gameStageScript.isPast = false;
	}        




	



	public Array<CompositeItem> getMonsters() {
		return pigs;
	}
	
}

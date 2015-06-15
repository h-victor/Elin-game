package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
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

	private MartenScript martenScript;
	/**
	 * @return the martenScript
	 */
	public MartenScript getMartenScript() {
		return martenScript;
	}




	private ElinScript elinScript;
	private GameStageScript gameStageScript;
	private boolean isPast = false;
	public int itemNb;
	private Save save;


	public GameStage(ResourceManager resourceManager){
		super(new FitViewport(resourceManager.getProjectVO().originalResolution.width, resourceManager.getProjectVO().originalResolution.height));
		initSceneLoader(resourceManager);
		//		dialog();
		initMainScene();
	}

	private void initMainScene() {
		@SuppressWarnings("unused")
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
		save=new Save();
		for(IBaseItem item: sceneLoader.sceneActor.getItems()) {
			if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null && item.isComposite()) {
				((CompositeItem)item).addScript(new MovingPigScript(this));//item.getParentItem().getLabelById(null).setText(newText);
			}
			if(item.getCustomVariables().getFloatVariable("item") != null && item.isComposite()) {
				((CompositeItem)item).addScript(new ItemScript(this));//item.getParentItem().getLabelById(null).setText(newText);
			}
		}

		Music music = Gdx.audio.newMusic(Gdx.files.internal("Celestial_Aeon_Project_-_Children.mp3"));
		music.play();
		music.setLooping(true);

	}

	/* (non-Javadoc)
	 * @see com.uwsoft.editor.renderer.Overlap2DStage#act(float)
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
			if(isPast==false)
				goToPast();
			else if(isPast==true)
				returnToPresent();
		}
	}


	/* Loading another scene -> go back in time */
	public void goToPast(){
		save.setPresentSave(sceneLoader.sceneActor);
		clear();
		sceneLoader.loadScene("PastScene");
		elin.addScript(elinScript);
		marten.addScript(martenScript);
		addActor(sceneLoader.getRoot());
		addActor(elin);
		addActor(marten);
		isPast=true;
	}


	

	public void returnToPresent() {
		//sceneLoader.loadScene("MainScene");
		clear();
		System.out.println("3"+save.getPresentSave()+save.getPresentSave());
		save.getPresentSave().removeActor(save.getPresentSave().getCompositeById("elin"));
		save.getPresentSave().removeActor(save.getPresentSave().getCompositeById("marten"));
		addActor(save.getPresentSave());
		addActor(elin);
		addActor(marten);
		isPast=false;
	}        


	/*	public void dialog(){
		//clear();
		sceneLoader.loadScene("DialogScreen");
	}
	 */




}

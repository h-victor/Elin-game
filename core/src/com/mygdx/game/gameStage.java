package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.ResourceManager;
//import com.uwsoft.editor.renderer.actor.CompositeItem;
//import com.uwsoft.editor.renderer.actor.IBaseItem;

public class gameStage extends Overlap2DStage{
	public ResourceManager rs;
	public SceneVO sl;
			
	public boolean isPast = false;
	
	public gameStage(ResourceManager rs){		
		initSceneLoader();
		sl = sceneLoader.loadScene("MainScene");
		addActor(sceneLoader.getRoot());
	
		play play_ = new play(/*rs, sl_*/ getCamera());
		sceneLoader.getRoot().addScript(play_);//getCompositeById("player").addScript(play_);
		
/*        for(IBaseItem item: sceneLoader.getRoot().getItems()) {
            if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null && item.isComposite()) {
                ((CompositeItem)item).addScript(new movingPig());
            }
        }
*/	
		/*monster monster_ = new monster();
		sceneLoader.getRoot().addScript(monster_);*/
		
		Music music = Gdx.audio.newMusic(Gdx.files.internal("Celestial_Aeon_Project_-_Children.mp3"));
		music.play();
		music.setLooping(true);
		
		
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
			if(isPast==false)
				PastStage();
			else if(isPast==true)
				PresentStage();
	    	}
		
	}
	

	/* Loading another scene -> go back in time */
	private void PresentStage() {
		clear();
		sceneLoader.loadScene("MainScene");

		addActor(sceneLoader.getRoot());
		isPast = false;
	}        

	public void PastStage(){
		clear();
		sceneLoader.loadScene("PastScene");

		addActor(sceneLoader.getRoot());
		isPast = true;

	}
	
}

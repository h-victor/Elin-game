package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class gameStage extends Overlap2DStage{
	public ResourceManager rs;
	public SceneVO sl;
			
	public gameStage(ResourceManager rs){		
		initSceneLoader();
		sl = sceneLoader.loadScene("MainScene");
		addActor(sceneLoader.getRoot());
	
		play play_ = new play(/*rs, sl_*/ getCamera());
		sceneLoader.getRoot().addScript(play_);//getCompositeById("player").addScript(play_);
		
        for(IBaseItem item: sceneLoader.getRoot().getItems()) {
            if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null && item.isComposite()) {
                ((CompositeItem)item).addScript(new movingPig());
            }
        }
	
		/*monster monster_ = new monster();
		sceneLoader.getRoot().addScript(monster_);*/
		
		Music music = Gdx.audio.newMusic(Gdx.files.internal("Celestial_Aeon_Project_-_Children.mp3"));
		music.play();
		music.setLooping(true);
	}
	
}

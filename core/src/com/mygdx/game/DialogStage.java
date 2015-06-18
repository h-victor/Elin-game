package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.functionality.MyGestureListener;
import com.uwsoft.editor.renderer.SceneLoader;

public class DialogStage extends Stage{
	
//	MyGestureListener myGestureListener;
	
    public DialogStage(final GameStage gameStage/*, MyGestureListener myGestureListener*/){
//    	this.myGestureListener = myGestureListener;
    	
        //Creating a scene loader and passing it a resource manager of game stage
        SceneLoader sl = new SceneLoader(gameStage.essentials.rm);

        // loading UI scene
        sl.loadScene("DialogScreen");

        // adding it's root composite item to the stage       
        //	        addActor(sl.getRoot());

        DialogStageScript dialogStageScript = new DialogStageScript(this, gameStage/*, myGestureListener*/);
        sl.getRoot().addScript(dialogStageScript);
        addActor(sl.getRoot());	 
    }
}

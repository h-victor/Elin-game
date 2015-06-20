package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.functionality.MyGestureListener;
import com.uwsoft.editor.renderer.SceneLoader;

public class DialogStage extends Stage{
	
//	MyGestureListener myGestureListener;
	 private DialogStageScript dialogStageScript;
	

	public DialogStage(final GameStage gameStage){
    	super();
    	
        //Creating a scene loader and passing it a resource manager of game stage
        SceneLoader sl = new SceneLoader(gameStage.essentials.rm);

        // loading UI scene
        sl.loadScene("DialogScreen");

        dialogStageScript = new DialogStageScript(this, gameStage);
        sl.getRoot().addScript(dialogStageScript);
        addActor(sl.getRoot());	 
    }
	
    /**
	 * @return the dialogStageScript
	 */
	public DialogStageScript getDialogStageScript() {
		return dialogStageScript;
	}
}

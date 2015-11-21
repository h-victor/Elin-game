package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.SceneLoader;

public class DialogStage extends Stage{
    private DialogStageScript dialogStageScript;

    public DialogStage(final GameStage gameStage){
        super();
        
        //Creating a scene loader and passing it a resource manager of game stage
        SceneLoader sl = new SceneLoader(gameStage.essentials.rm);

        // loading UI scene
        sl.loadScene("DialogScreen");

        dialogStageScript = new DialogStageScript(/*this, */gameStage);
        sl.getRoot().addScript(dialogStageScript);
        addActor(sl.getRoot());	 
    
        // force the camera to be at this resolution (and be the same for all device) in all stage
        ((OrthographicCamera) this.getCamera()).setToOrtho(false, 1280, 720);
        this.getCamera().update();
    }

    /**
     * @return the dialogStageScript
     */
    public DialogStageScript getDialogStageScript() {
        return dialogStageScript;
    }
}
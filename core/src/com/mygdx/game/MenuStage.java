package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class MenuStage extends Overlap2DStage {
    public MenuStage(ResourceManager resourceManager){
        super();
        Gdx.input.setInputProcessor(this);	
        initSceneLoader(resourceManager);
        initMenu();

        // force the camera to be at this resolution (and be the same for all device) in all stage        
        ((OrthographicCamera) this.getCamera()).setToOrtho(false, 1280, 720);
        this.getCamera().update();
    }

    private void initMenu(){
        clear();
        sceneLoader.loadScene("Menu"); 
        MenuStageScript menuScript= new MenuStageScript(this);
        sceneLoader.getRoot().addScript(menuScript);
        addActor(sceneLoader.getRoot());
    }
}

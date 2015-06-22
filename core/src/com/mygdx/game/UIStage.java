package com.mygdx.game;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class UIStage extends Overlap2DStage{
    private GameStage gameStage;
    
    public UIStage(ResourceManager resourceManager, GameStage gameStage_) {
        super();
        initSceneLoader(resourceManager);
        initUI();
        gameStage=gameStage_;
    }

    private void initUI() {
        clear();
        sceneLoader.loadScene("UIScene"); 
        sceneLoader.getRoot().addScript(new UIScript(this));
        addActor(sceneLoader.getRoot());
    }

    public GameStage getGameStage() {
        return gameStage;
    }
}

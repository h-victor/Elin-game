package com.mygdx.game;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

public class UIScript implements IScript {
    private UIStage stage;
    private CompositeItem item;
    private CompositeItem button;
    private SimpleButtonScript playButtonScript; 

    private GameStage gameStage;
    
    public UIScript(UIStage uiStage, GameStage gameStage) {
        this.stage=uiStage;
        
        this.gameStage = gameStage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item=item;
        button=item.getCompositeById("restartBtn");
        playButtonScript=new SimpleButtonScript();
        playButtonScript.init(button);
        
        item.getLabelById("label").setVisible(false);
    }

    @Override
    public void dispose() {
    	stage.dispose();
    	item.dispose();
    	button.dispose();
    	playButtonScript.dispose();
    }

    @Override
    public void act(float delta) {
        playButtonScript.act(delta);
        item.getLabelById("HP").setText("HP= " + MartenScript.HP);
        stage.getGameStage().getMartenScript();
        if(MartenScript.isDead || stage.getGameStage().getMarten().getY() < -1000){
            button.setVisible(true);
            
            item.getLabelById("label").setVisible(true);
        }
/*        else if(gameStage.getElinScript().endGame()){
            button.setVisible(true);

            item.getLabelById("label").setText("Thank you for playing to this demo version of Elin-game");
            item.getLabelById("label").setVisible(true);
        }*/
        else{
            button.setVisible(false);
            
            item.getLabelById("label").setVisible(false);
        }
        if(playButtonScript.isDown()){
            stage.getGameStage().getMartenScript().restart();
        }
    }
}

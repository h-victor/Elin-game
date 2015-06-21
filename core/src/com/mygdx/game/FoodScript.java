package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

public class FoodScript implements IScript{
    private GameStage stage;
    private CompositeItem item;
    private CompositeItem marten;
    private Vector2 martenPos;

    public FoodScript(GameStage stage){
        this.stage=stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item=item;
        marten=stage.getMarten();
        martenPos= new Vector2();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void act(float delta) {
        if(martenTouchObject()){
            stage.getMartenScript();
            MartenScript.HP +=item.getCustomVariables().getFloatVariable("food");
            if(MartenScript.HP>3)MartenScript.HP=3;
            item.remove();
        }
    }

    private boolean martenTouchObject() {
        martenPos.set(marten.getX()+marten.getWidth()/2,marten.getY()+marten.getHeight()/2);
        return (martenPos.x>item.getX()&&martenPos.x<item.getRight())&&(martenPos.y>item.getY()&&martenPos.y<item.getTop());
    }
}

package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;

public class MovingPigScript implements IScript {
	
	private CompositeItem item;
    private float originalPosX;
    private int direction = 1;
	private float moveSpeed = 50f;
	private float margin = 100f;
	private GameStage stage;
	private CompositeItem marten;
	
	public MovingPigScript(GameStage gameStage){
		
	this.stage=gameStage;
		
	}
	@Override
	public void init(CompositeItem item) {
		// TODO Auto-generated method stub
		this.item = item;
		originalPosX = item.getX();
		marten=stage.marten;
		this.item.setOrigin(this.item.getWidth()/2, 0);
        if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null)
            moveSpeed = item.getCustomVariables().getFloatVariable("cochonSpeed");
        if(item.getCustomVariables().getFloatVariable("cochonMargin") != null)
            margin = item.getCustomVariables().getFloatVariable("cochonMargin");
        item.setScaleX(item.getScaleX()*-1);
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
        setX(getX()+direction*delta*moveSpeed);
        if(getX() > originalPosX + margin || getX() < originalPosX - margin) {direction*= -1;
        item.setScaleX(item.getScaleX()*-1);
        }
        item.setX(getX());
        
        if(martenHitMonster()){
        	item.addAction(Actions.sequence(Actions.run(new Runnable(){

				@Override
				public void run() {
					setSpriterAnimationByName("mort");
					
				}
        		
        	}),Actions.delay(.5f),Actions.run(new Runnable(){
				@Override
				public void run() {
					item.remove();
					item.getBody().setActive(false);
				}
        	})));
        }
	}
	
    
	public float getX() {
        Vector2 currPos = item.getBody().getPosition();
        return currPos.x/ PhysicsBodyLoader.SCALE;
    }
    public void setX(float x) {
        Vector2 currPos = item.getBody().getPosition();
        item.getBody().setTransform(x*PhysicsBodyLoader.SCALE, currPos.y, item.getBody().getAngle());
    }
    
    
	private boolean martenHitMonster() {
		Vector2 martenPos = new Vector2(marten.getX()+marten.getWidth()/2,marten.getY()+marten.getHeight()/2);
		return (martenPos.x>item.getX()&&martenPos.x<item.getRight())&&(martenPos.y>item.getY()&&martenPos.y<item.getTop())&&MartenScript.isAttacking;
	}		
		
	private void setSpriterAnimationByName(final String string) {
		item.getSpriterActorById("animation").setAnimation(item.getSpriterActorById("animation").getAnimations().indexOf(string));
	}
}
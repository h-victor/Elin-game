package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;

public class MovingPigScript implements IScript {
	
	private CompositeItem item;
    private float originalPosX;
    private int direction = 1;
	private float moveSpeed = 50f;
	private float margin = 100f;
	@Override
	public void init(CompositeItem item) {
		// TODO Auto-generated method stub
		this.item = item;
		originalPosX = item.getX();
		
        if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null)
            moveSpeed = item.getCustomVariables().getFloatVariable("cochonSpeed");
        if(item.getCustomVariables().getFloatVariable("cochonMargin") != null)
            margin = item.getCustomVariables().getFloatVariable("cochonMargin");
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
        setX(getX()+direction*delta*moveSpeed);
        if(getX() > originalPosX + margin || getX() < originalPosX - margin) direction*= -1;
        item.setX(getX());
		
	}
	
    public float getX() {
        Vector2 currPos = item.getBody().getPosition();
        return currPos.x/ PhysicsBodyLoader.SCALE;
    }
    public void setX(float x) {
        Vector2 currPos = item.getBody().getPosition();
        item.getBody().setTransform(x*PhysicsBodyLoader.SCALE, currPos.y, item.getBody().getAngle());
    }
			
		
}
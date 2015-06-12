package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

public class ItemScript implements IScript{
	
	private GameStage stage;
	private CompositeItem item;
	private CompositeItem marten;

	public ItemScript(GameStage stage){
		this.stage=stage;
	}

	@Override
	public void init(CompositeItem item) {
		this.item=item;
		marten=stage.marten;
		
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void act(float delta) {
		if(martenTouchObject()){
			stage.itemNb ++;
			item.remove();
			
			System.out.println("marten a obtenu "+" nb item= "+stage.itemNb);
		}
		
		
	}

	private boolean martenTouchObject() {
		Vector2 martenPos = new Vector2(marten.getX()+marten.getWidth()/2,marten.getY()+marten.getHeight()/2);
		return (martenPos.x>item.getX()&&martenPos.x<item.getRight())&&(martenPos.y>item.getY()&&martenPos.y<item.getTop());
	}

}

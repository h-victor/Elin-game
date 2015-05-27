package com.mygdx.game;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

public class monster implements IScript{
	private CompositeItem item;
	
	private float oldX =0, x;
	private boolean left = true;//false;
		private int i = 0;

	@Override
	public void init(CompositeItem item) {
		this.item = item;
		//oldX = item.getCompositeById("monster")/*.getItemById("pig")*/.getBody().getPosition().x;
		//item.getCompositeById("monster")/*.getItemById("pig")*/.getBody().applyForceToCenter(-10, 0, true);
		//item.findItemsByName("pig").get(1).getBody().applyForceToCenter(-10, 0, true);
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void act(float delta) {
		x = item.getCompositeById("monster")./*getItemById("pig").*/getBody().getPosition().x;
		if(oldX == x){i ++;}
		if(oldX == x && i == 100){ //|| (oldX > x - 20 && oldX < x + 20)){
			if(left == true){ 
				left = false;
				item.getCompositeById("monster").setScaleX(-1);
				item.getCompositeById("monster")/*.getItemById("pig")*/.getBody().applyForceToCenter(1, 0, true);
				//item.getCompositeById("monster").setOrigin(0);
			}
			else if(left == false){ 
				left = true;
				item.getCompositeById("monster")/*.getItemById("pig")*/.getBody().applyForceToCenter(-1, 0, true);
				item.getCompositeById("monster").setScaleX(1);
				//item.getCompositeById("monster").setOrigin(0);
			}
			//oldX = x;
			item.getCompositeById("monster").setOrigin(0);
			i = 0;
		}
		else{// if(oldX != x){
			oldX = x;
			if(left == true){
				item.getCompositeById("monster")/*.getItemById("pig")*/.getBody().applyForceToCenter(-1, 0, true);
				item.getCompositeById("monster").setScaleX(1);
				//item.getCompositeById("monster").setOrigin(0);
			}
			else if(left == false){
				item.getCompositeById("monster")/*.getItemById("pig")*/.getBody().applyForceToCenter(1, 0, true);
				item.getCompositeById("monster").setScaleX(-1);
				//item.getCompositeById("monster").setOrigin(0);
			}
			item.getCompositeById("monster").setOrigin(0);
		}
		//item.getCompositeById("monster").getBody().applyForceToCenter(10, 0, true);
	}

}

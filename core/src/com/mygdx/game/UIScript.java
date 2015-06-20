package com.mygdx.game;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import com.uwsoft.editor.renderer.script.SimpleButtonScript;

public class UIScript implements IScript {

	
	private UIStage stage;
	private CompositeItem item;
	private CompositeItem button;
	private SimpleButtonScript playButtonScript; 
	
	public UIScript(UIStage uiStage) {
		this.stage=uiStage;
	}

	@Override
	public void init(CompositeItem item) {
		this.item=item;
		button=item.getCompositeById("restartBtn");
		playButtonScript=new SimpleButtonScript();
		playButtonScript.init(button);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void act(float delta) {
		playButtonScript.act(delta);
		item.getLabelById("HP").setText("HP= "+MartenScript.HP);
		stage.getGameStage().getMartenScript();
		if(MartenScript.isDead||stage.getGameStage().getMarten().getY()<-1000)
			button.setVisible(true);
		else
			button.setVisible(false);
		if(playButtonScript.isDown()){
			stage.getGameStage().getMartenScript().restart();
		}
		
	}

}

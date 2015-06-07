package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;

public class ElinScript implements IScript {

	@SuppressWarnings("unused")
	private GameStage gameStage;
	private CompositeItem item;
	private SpriterActor spriterAnimation;
	private Vector2 initialCoordinates;
	private float moveSpeed;

	public ElinScript(GameStage gameStage) {
		this.gameStage=gameStage;
	}

	@Override
	public void init(CompositeItem item) {
		this.item=item;

		moveSpeed = 220f * this.item.mulX;

		spriterAnimation=item.getSpriterActorById("animation");
		this.item.setOrigin(item.getWidth()/2, 0);
		initialCoordinates = new Vector2(item.getX(), item.getY());
	}

	@Override
	public void dispose() {
		spriterAnimation.dispose();
		item.dispose();

	}

	@Override
	public void act(float delta) {

		elinMove(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.B))
			elinTransformToBridge();
		if(Gdx.input.isKeyPressed(Input.Keys.L))
			elinTransformToLadder();
	}

	private void elinTransformToBridge() {
		// TODO Auto-generated method stub

	}

	private void elinTransformToLadder() {
		// TODO Auto-generated method stub

	}

	private void elinMove(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			item.setX(item.getX() + delta*moveSpeed);
			if(item.getScaleX()<0){
				item.setScaleX(item.getScaleX()*-1f);
			}

		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			item.setX(item.getX() - delta*moveSpeed);
			if(item.getScaleX()>0){
				item.setScaleX(item.getScaleX()*-1f);
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
			item.setY(item.getY() + delta*moveSpeed);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			item.setY(item.getY() - delta*moveSpeed);
		}
	}

	public void reset(){
		item.setPosition(initialCoordinates.x,initialCoordinates.y);
	}

}

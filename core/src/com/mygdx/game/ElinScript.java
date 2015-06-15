package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;

public class ElinScript implements IScript {

	private final GameStage gameStage;

	private float moveSpeed;

	private CompositeItem item;
	private SpriterActor spriterActor;
	private CompositeItem marten;
	private Vector2 initialCoordinates;


	public static boolean isBridge = false;
	public static boolean isLadder = false;
	public static boolean goMarten = false;

	public ElinScript(final GameStage gameStage) {
		this.gameStage=gameStage;
	}

	@Override
	public void init(final CompositeItem item) {
		this.item=item;

		moveSpeed = 220f * this.item.mulX;
		marten = item.getParentItem().getCompositeById("marten");
		spriterActor=item.getSpriterActorById("animation");
		this.item.setOrigin(item.getX()+spriterActor.getWidth()/2, 0);
		initialCoordinates = new Vector2(item.getX(), item.getY());
	}

	@Override
	public void dispose() {
		spriterActor.dispose();
		item.dispose();

	}

	@Override
	public void act(final float delta) {
		elinMove(delta);
		if(Gdx.input.isKeyJustPressed(Input.Keys.B)&&MartenScript.isCloseEnough()){
			elinTransformToBridge();

		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.L)&&MartenScript.isCloseEnough())
			elinTransformToLadder();
		if(MartenScript.isActionFinished)
			elinTransformBack();
	}



	public void reset(){
		item.setPosition(initialCoordinates.x,initialCoordinates.y);
	}

	private void elinTransformBack() {
		MartenScript.isActionFinished=false;
		item.getImageById("bridge").setVisible(false);
		item.getImageById("ladder").setVisible(false);
		spriterActor.setVisible(true);
		if(isLadder){
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {

					setSpriterAnimationByName("transformation Echelle 2");
				}}),Actions.delay(1.5f),Actions.run(new Runnable(){

					@Override
					public void run() {
						setSpriterAnimationByName("marche");
					}

				}),Actions.run(new Runnable(){

					@Override
					public void run() {
						if(marten.getScaleX()>0){
							if(item.getScaleX()<0)
								item.setScaleX(item.getScaleX()*-1);
							item.addAction(Actions.sequence(Actions.moveTo(marten.getRight()-spriterActor.getX(), marten.getY(),1f),
									Actions.run(new Runnable(){
										@Override
										public void run() {
											isLadder=false;
										}
									})));
						}
						else {
							if(item.getScaleX()>0)
								item.setScaleX(item.getScaleX()*-1);
							item.addAction(Actions.sequence(Actions.moveTo(marten.getX()-spriterActor.getRight(), marten.getY(),1f),
									Actions.run(new Runnable(){
										@Override
										public void run() {
											isLadder=false;
										}
									})));
						}
					}
				})));
		}else if (isBridge){
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("transformation Pont 2");
				}}),Actions.delay(1.5f),Actions.run(new Runnable(){
					@Override
					public void run() {
						setSpriterAnimationByName("marche");

					}

				}),Actions.run(new Runnable(){

					@Override
					public void run() {
						if(marten.getScaleX()>0){
							if(item.getScaleX()<0)
								item.setScaleX(item.getScaleX()*-1);
							item.addAction(Actions.sequence(Actions.moveTo(marten.getRight()-spriterActor.getX(), marten.getY(),1f),
									Actions.run(new Runnable(){
										@Override
										public void run() {
											isBridge=false;
										}
									})));
						}
						else {
							if(item.getScaleX()>0)
								item.setScaleX(item.getScaleX()*-1);
							item.addAction(Actions.sequence(Actions.moveTo(marten.getX()-spriterActor.getRight(), marten.getY(),1f),
									Actions.run(new Runnable(){
										@Override
										public void run() {
											isBridge=false;
										}
									})));
						}
					}
				})));
		}
		else System.out.println("error");


	}

	private void elinTransformToBridge() {
		item.addAction(Actions.sequence(Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("transformation Pont");
			}
		}), Actions.delay(1.5f,Actions.run(new Runnable(){

			@Override
			public void run() {
				spriterActor.setVisible(false);
				item.getImageById("bridge").setVisible(true);
				item.getImageById("ladder").setVisible(false);
				isBridge=true;
				goMarten=true;
			}

		}))));


	}

	private void elinTransformToLadder() {
		item.addAction(Actions.sequence(Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("transformation Echelle");
			}
		}), Actions.delay(1.5f),Actions.run(new Runnable(){

			@Override
			public void run() {
				spriterActor.setVisible(false);
				item.getImageById("bridge").setVisible(false);
				item.getImageById("ladder").setVisible(true);
				isLadder=true;
				goMarten=true;
			}
		})));

	}

	private void elinMove(final float delta) {
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
	
	/***** Coding Tools  *******/
	private void setSpriterAnimationByName(final String string) {
		spriterActor.setAnimation(spriterActor.getAnimations().indexOf(string));
	}


}

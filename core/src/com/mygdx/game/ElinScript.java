package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;
																																																																																																																																																												
public class ElinScript implements IScript{
	private GameStage gameStage;
	private float moveSpeed;

	private CompositeItem item;
	private SpriterActor spriterActor;
	private CompositeItem marten;																																																																																								
	private Vector2 initialCoordinates;
	private Vector2 elinPos;
	private Vector2 mousePos;
	
	public static boolean isBridge = false;
	public static boolean isLadder = false;
	public static boolean goMarten = false;
	
	private Camera camera;

	public ElinScript(final GameStage gameStage, Camera camera) {
		this.gameStage=gameStage;
		this.camera = camera;
	}

	@Override
	public void init(final CompositeItem item) {
		this.item=item;
		this.item.getImageById("ladder").setVisible(false);
		this.item.getImageById("bridge").setVisible(false);
		moveSpeed = 220f * this.item.mulX;
		marten = gameStage.getMarten();
		spriterActor=item.getSpriterActorById("animation");
		this.item.setOrigin(this.item.getWidth()/2, 0);
		initialCoordinates = new Vector2(item.getX(), item.getY());
		elinPos = new Vector2();
		mousePos = new Vector2();
	}

	@Override
	public void dispose() {
		spriterActor.dispose();
		item.dispose();
		marten.dispose();
	}

	@Override
	public void act(final float delta) {
		elinPos.set(item.getX()+spriterActor.getX()+spriterActor.getWidth()/2,item.getY()+spriterActor.getY()+spriterActor.getHeight()/2);
		mousePos.set(Gdx.input.getX(),Gdx.input.getY());
		if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
			System.out.println("Gdx.input.pos  ="+gameStage.screenToStageCoordinates(mousePos)+
					" \n elinPos ="+elinPos );
		}
		/****elin Move****/
		if(Gdx.input.isKeyPressed(Input.Keys.D)) 
			elinMoveRight(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) 
			elinMoveLeft(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.Z)) 
			elinMoveTop(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.S)) 
			elinMoveBot(delta);

		/******elin Transform******/
		if((Gdx.input.isKeyJustPressed(Input.Keys.B))&&MartenScript.isCloseEnough())
			elinTransformToBridge();
		if(Gdx.input.isKeyJustPressed(Input.Keys.L)&&MartenScript.isCloseEnough())
			elinTransformToLadder();
		if(MartenScript.isActionFinished)
			elinTransformBack();

		/******elin Move******/
				float moveSpeed = 220f * this.item.mulX;
		
				if(Gdx.input.isTouched()){
					if(Gdx.input.getX() > camera.viewportWidth / 2){
						item.setX(item.getX() + Gdx.graphics.getDeltaTime()*moveSpeed);
						if(item.getScaleX()<0){
							item.setScaleX(item.getScaleX()*-1f);
						}
					}
					else if(Gdx.input.getX() < camera.viewportWidth / 2 ){
						item.setX(item.getX() - Gdx.graphics.getDeltaTime()*moveSpeed);
						if(item.getScaleX()>0){
							item.setScaleX(item.getScaleX()*-1f);
						}
					}
				}

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
				}}),Actions.delay(1f),Actions.run(new Runnable(){
					@Override
					public void run() {setSpriterAnimationByName("marche");}
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
						}}})));
		}
		else System.out.println("error");
	}

	public void elinTransformToBridge() {
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
				isBridge=true;
				goMarten=true;
			}
		}))));
	}

	public void elinTransformToLadder() {
		item.addAction(Actions.sequence(Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("transformation Echelle");
			}
		}), Actions.delay(1.5f),Actions.run(new Runnable(){

			@Override
			public void run() {
				spriterActor.setVisible(false);
				item.getImageById("ladder").setVisible(true);
				isLadder=true;
				goMarten=true;
			}
		})));
	}

	public void elinMoveBot(final float delta) {
		item.setY(item.getY() - delta*moveSpeed);
	}

	public void elinMoveTop(final float delta) {
		item.setY(item.getY() + delta*moveSpeed);
	}

	public void elinMoveLeft(final float delta) {
		item.setX(item.getX() - delta*moveSpeed);
		if(item.getScaleX()>0){
			item.setScaleX(item.getScaleX()*-1f);
		}
	}

	public void elinMoveRight(final float delta) {
		item.setX(item.getX() + delta*moveSpeed);
		if(item.getScaleX()<0){
			item.setScaleX(item.getScaleX()*-1f);
		}
	}

	/***** Coding Tools  *******/
	private void setSpriterAnimationByName(final String string) {
		spriterActor.setAnimation(spriterActor.getAnimations().indexOf(string));
	}

}

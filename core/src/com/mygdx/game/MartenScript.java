
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.actor.ImageItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;

/*
 * contient la logique de Marten : suivre Elin , pas suivre Elin , mourir , mal, collision, attaquer, passer pont , monter echelle
 */

public class MartenScript implements IScript {

	
	private final GameStage stage;
	
	private int verticalSpeed;
	private float moveSpeed;
	private float gravity;	
	private CompositeItem item;
	private SpriterActor spriterActor;
	private ImageItem blur;
	private CompositeItem elin;
	private Vector2 initialCoordinates;
	
	public static int HP;
	public static boolean isAttacking = false;
	public static boolean isActionFinished = false;
	
	public MartenScript(final GameStage gameStage) {
		this.stage=gameStage;
	}

	@Override
	public void init(final CompositeItem item) {
		this.item=item;
		moveSpeed = 150f * item.mulX;
		gravity = -1500f * item.mulY;
		spriterActor = item.getSpriterActorById("animation");
		elin = item.getParentItem().getCompositeById("elin");
		blur = item.getImageById("blur");
		blur.setVisible(false);
		this.item.setOrigin(this.item.getWidth()/2, 0);
		initialCoordinates = new Vector2(item.getX(), item.getY());
	}

	@Override
	public void dispose() {
		item.dispose();
	}

	@Override
	public void act(final float delta) {
		isAttacking=false;
		if(!isCloseEnough())
			goToElin(delta); 
		else if(getCurrentAnimationName().equals("marche")){
			setSpriterAnimationByName("debout");
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.A)) 
			attack();
		if(isCloseEnough()&&ElinScript.isBridge) 
			crossBrigde();
		if(isCloseEnough()&&ElinScript.isLadder)
			climbLadder(delta);

		verticalSpeed += gravity*delta;
		checkForCollisions();
		item.setY(item.getY() + verticalSpeed*delta);

	}

	/*
	 * Check if the distance between Elin and Marten if distance > 50 return false, if distance <= 50 return true
	 */
	private boolean isCloseEnough() {
		final Vector2 elinPos= new Vector2(elin.getX()+elin.getWidth()/2,elin.getY()+elin.getHeight()/2);
		final Vector2 martenPos = new Vector2(item.getX()+item.getWidth()/2,item.getY()+item.getHeight()/2);
		if (ElinScript.isLadder||ElinScript.isBridge)
			return true;
		else
			return !(elinPos.dst(martenPos)>200);
	}

	private void climbLadder(final float delta) {
		item.addAction(Actions.sequence(Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("debut monte");
			}
		}),Actions.delay(.6f),Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("monte");
			}
		}),Actions.moveTo(item.getX(),item.getTop(),2f),Actions.run(new Runnable(){
			@Override
			public void run() {
				item.setPosition(item.getX(),item.getTop());
				setSpriterAnimationByName("fin monte");
			}
		}),Actions.delay(.6f),Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("debout");
			}
		})));
		isActionFinished=true;

	}

	private void crossBrigde() {
		item.addAction(Actions.sequence(Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("marche");
			}
		}),Actions.moveBy(500, 0,3f)));
		isActionFinished=true;
	}

	private void attack() {
		isAttacking=true;
		item.addAction(Actions.sequence(Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("attaque");
			}}), Actions.delay(.6f)));
	}

	private void checkForCollisions() {
		checkBottomCollision();
		checkSideCollision();

	}

	private void goToElin(final float delta) {

		if(item.getRight()<elin.getX()-20) {
			item.setX(item.getX()+delta*moveSpeed);
			if(item.getScaleX()<0){
				item.setScaleX(item.getScaleX()*-1f);
			}

		}
		if(item.getX()>elin.getRight()+20) {
			item.setX(item.getX()-delta*moveSpeed);
			if(item.getScaleX()>0){
				item.setScaleX(item.getScaleX()*-1f);
			}
		}
		if(!(getCurrentAnimationName().equals("marche")))
			setSpriterAnimationByName("marche");
	}


	private void checkBottomCollision() {
		float raySize = -(verticalSpeed+Gdx.graphics.getDeltaTime())*Gdx.graphics.getDeltaTime();
		if(raySize < 5f) raySize = 5f;
		if(verticalSpeed > 0) return;
		final Vector2 rayFrom = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY()+item.getHeight()/2)*PhysicsBodyLoader.SCALE);
		final Vector2 rayTo = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY() - raySize)*PhysicsBodyLoader.SCALE);
		stage.getWorld().rayCast(new RayCastCallback(){
			public float reportRayFixture(final Fixture fixture, final Vector2 point, final Vector2 normal, final float fraction){
				verticalSpeed = 0;
				item.setY(point.y/PhysicsBodyLoader.SCALE+0.1f);
				return 0;
			}
		}, rayFrom, rayTo);
	}

	private void checkSideCollision() {
		final Vector2 pointA = new Vector2(((item.getX()-5)*PhysicsBodyLoader.SCALE),(item.getY()+item.getHeight()/2)*PhysicsBodyLoader.SCALE);
		final Vector2 pointB = new Vector2(((item.getRight()+5)*PhysicsBodyLoader.SCALE),(item.getY()+item.getHeight()/2)*PhysicsBodyLoader.SCALE);
		stage.getWorld().rayCast(new RayCastCallback() {
			public float reportRayFixture(final Fixture fixture, final Vector2 point,final Vector2 normal, final float fraction) {
				for(final IBaseItem item: stage.sceneLoader.sceneActor.getItems()) {
					if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null && item.isComposite()) {
						if(((CompositeItem)item).getX()*PhysicsBodyLoader.SCALE<=point.x &&((CompositeItem)item).getRight()*PhysicsBodyLoader.SCALE>=point.x){
							if(!isAttacking){
								//hurt();
								System.out.println("toucher monstre");
							}
							if(isAttacking){
								((CompositeItem)item).setVisible(false);
								((CompositeItem)item).getBody().setActive(false);
							}
						}
					}
				}  
				if(item.getScaleX()==1){
					item.setX(point.x/PhysicsBodyLoader.SCALE-spriterActor.getRight());
				}
				else if(item.getScaleX()==-1){
					item.setX(point.x/PhysicsBodyLoader.SCALE-spriterActor.getX());
				}
				return 0;
			}
		}, pointA, pointB);
	}


	protected void hurt() {
		// TODO à modifier
		HP=HP-1;
		if(item.getScaleX()>0){
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("toucher");
				}
			}),Actions.moveBy(-50, 0, .6f),Actions.run(new Runnable(){
				@Override
				public void run() {
					item.setX(item.getX()-50);
					setSpriterAnimationByName("debout");
				}
			}),Actions.delay(2f)));
		}
		else{
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("toucher");
				}
			}),Actions.moveBy(50, 0, .6f),Actions.run(new Runnable(){

				@Override
				public void run() {
					item.setX(item.getX()+50);
					setSpriterAnimationByName("debout");
				}

			}),Actions.delay(2f)));
		}
	}

	public void die(){
		item.addAction(Actions.sequence(Actions.fadeOut(2f),Actions.delay(1f),
				Actions.run(new Runnable(){
					@Override
					public void run() {
						item.setPosition(initialCoordinates.x, initialCoordinates.y);
					}
				})));
	}

	private void setSpriterAnimationByName(final String string) {
		spriterActor.setAnimation(spriterActor.getAnimations().indexOf(string));
	}

	private String getCurrentAnimationName(){
		
		return spriterActor.getAnimations().get(spriterActor.getCurrentAnimationIndex());
	}
}

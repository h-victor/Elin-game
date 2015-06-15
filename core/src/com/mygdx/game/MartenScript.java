
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

	
	public static Vector2 elinPos;
	public static Vector2 martenPos;

	public static int HP;
	public static boolean isAttacking = false;
	public static boolean isActionFinished = false;
	private static boolean isDead = false;
	private static boolean isHurt;

	public MartenScript(final GameStage gameStage) {
		this.stage=gameStage;
	}

	@Override
	public void init(final CompositeItem item) {
		this.item=item;
		HP=3;
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
		elinPos= new Vector2(elin.getX()+elin.getWidth()/2,elin.getY()+elin.getHeight()/2);
		martenPos = new Vector2(item.getX()+item.getWidth()/2,item.getY()+item.getHeight()/2);
		if(!isCloseEnough())
			goToElin(delta); 
		else if(getCurrentAnimationName().equals("marche")&&!(ElinScript.isBridge||ElinScript.isLadder||isHurt||isDead)){
			setSpriterAnimationByName("debout");
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.A)&&getCurrentAnimationName().equals("debout")) 
			attack();
		if(isCloseEnough()&&ElinScript.isBridge&&ElinScript.goMarten) 
			crossBrigde();
		if(isCloseEnough()&&ElinScript.isLadder&&ElinScript.goMarten)
			climbLadder(delta);
		if(HP<1)
			isDead=true;
		
		verticalSpeed += gravity*delta;
		checkForCollisions();
		item.setY(item.getY() + verticalSpeed*delta);
	}

	/*
	 * Check if the distance between Elin and Marten if distance > 50 return false, if distance <= 50 return true
	 */
	public static boolean isCloseEnough() {
		if (ElinScript.isLadder||ElinScript.isBridge||isHurt||isDead)
			return true;
		else
			return !(elinPos.dst(martenPos)>200);
	}

	private void climbLadder(final float delta) {
		ElinScript.goMarten=false;
		System.out.println("climb Ladder1");
		if(elinPos.x>martenPos.x){
			if(item.getScaleX()<0)
				item.setScaleX(item.getScaleX()*-1);
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("debut monte");
					System.out.println("climb Ladder2");
				}
			}),Actions.delay(.6f),Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("monte");
					System.out.println("climb Ladder3");
				}
			}),Actions.moveTo(item.getX(),elin.getTop(),2f),Actions.run(new Runnable(){
				@Override
				public void run() {
					//item.setPosition(item.getX(),item.getTop());
					setSpriterAnimationByName("fin monte");
					System.out.println("climb Ladder4");
				}
			}),Actions.delay(.6f),Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("debout");
					System.out.println("climb Ladder4");
					isActionFinished=true;
				}
			})));
		}
		else {
			if(item.getScaleX()>0)
				item.setScaleX(item.getScaleX()*-1);
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("debut monte");
					System.out.println("climb Ladder2");
				}
			}),Actions.delay(.6f),Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("monte");
					System.out.println("climb Ladder3");
				}
			}),Actions.moveTo(item.getX(),elin.getTop(),2f),Actions.run(new Runnable(){
				@Override
				public void run() {
					//item.setPosition(item.getX(),item.getTop());
					setSpriterAnimationByName("fin monte");
					System.out.println("climb Ladder4");
				}
			}),Actions.delay(.6f),Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("debout");
					System.out.println("climb Ladder4");
					isActionFinished=true;
				}
			})));
		}

	}

	private void crossBrigde() {
		ElinScript.goMarten=false;

		if(elinPos.x>martenPos.x){
			if(item.getScaleX()<0)
				item.setScaleX(item.getScaleX()*-1);
			item.addAction(Actions.sequence(Actions.parallel(Actions.sequence(Actions.run(new Runnable(){

				@Override
				public void run() {
					setSpriterAnimationByName("marche");
				}

			}))),Actions.moveBy(200, 0,5f),Actions.run(new Runnable(){

				@Override
				public void run() {
					setSpriterAnimationByName("debout");
					isActionFinished=true;
				}

			})));
		}
		if(elinPos.x<martenPos.x){
			if(item.getScaleX()>0)
				item.setScaleX(item.getScaleX()*-1);
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("marche");
				}
			}),Actions.moveBy(-200, 0,5f),Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("debout");
					isActionFinished=true;
				}
			})));
		}
	}

	private void attack() {
		item.addAction(Actions.sequence(Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("attaque");
				isAttacking=true;
			}}), Actions.delay(.6f),Actions.run(new Runnable(){
				@Override
				public void run() {
					isAttacking=false;
					setSpriterAnimationByName("debout");
				}})));
	}

	private void checkForCollisions() {
		checkBottomCollision();
		if(!isHurt)
		checkSideCollision();

	}

	private void goToElin(final float delta) {
		if(martenPos.x<elinPos.x-20) {
			item.setX(item.getX()+delta*moveSpeed);
			if(item.getScaleX()<0){
				item.setScaleX(item.getScaleX()*-1f);
			}

		}
		if(martenPos.x>elinPos.x+20) {
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
						if(((CompositeItem)item).getX()*PhysicsBodyLoader.SCALE<=point.x &&((CompositeItem)item).getRight()*PhysicsBodyLoader.SCALE>=point.x&&
								((CompositeItem)item).getY()*PhysicsBodyLoader.SCALE<=point.y &&((CompositeItem)item).getTop()*PhysicsBodyLoader.SCALE>=point.y){
							if(!isAttacking){
								System.out.println("toucher monstre");
								hurt();

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
		HP=HP-1;
		System.out.println(HP);
		isHurt=true;
		if(item.getScaleX()>0){
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("toucher");
				}
			}),Actions.moveTo(item.getX()-50, item.getY(), .6f),Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("debout");
				}
			}),Actions.delay(2f),Actions.run(new Runnable(){

				@Override
				public void run() {
					isHurt=false;

				}

			})));
		}
		else{
			item.addAction(Actions.sequence(Actions.run(new Runnable(){
				@Override
				public void run() {
					setSpriterAnimationByName("toucher");
				}
			}),Actions.moveTo(item.getX()+50, item.getY(), .6f),Actions.run(new Runnable(){

				@Override
				public void run() {
					//item.setX(item.getX()+50);
					setSpriterAnimationByName("debout");
				}

			}),Actions.delay(2f),Actions.run(new Runnable(){

				@Override
				public void run() {
					isHurt=false;

				}

			})));
		}
	}
	

	public void restart(){
		item.addAction(Actions.sequence(Actions.fadeOut(2f),Actions.delay(1f),
				Actions.run(new Runnable(){
					@Override
					public void run() {
						item.setPosition(initialCoordinates.x, initialCoordinates.y);
						HP=3;
						isDead=false;
						verticalSpeed=0;
						elin.setPosition(initialCoordinates.x, initialCoordinates.y);
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

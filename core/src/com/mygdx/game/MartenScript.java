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

	private GameStage stage;
	private CompositeItem item;
	private float moveSpeed;
	private float gravity;
	private SpriterActor spriterActor;
	private ImageItem blur;
	private CompositeItem elin;
	private Vector2 initialCoordinates;
	private int verticalSpeed;
	private boolean isWalking;
	protected boolean isAttacking = false;
	private boolean isFollowingElin=true;
	private boolean wasWalking;
	public int HP;

	public MartenScript(GameStage gameStage) {
		this.stage=gameStage;
	}

	@Override
	public void init(CompositeItem item) {
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
	public void act(float delta) {
		followElin(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
			attack();
		if(Gdx.input.isKeyPressed(Input.Keys.B)) 
			crossBrigde();
		if(Gdx.input.isKeyJustPressed(Input.Keys.O))
			climbLadder(delta);
		verticalSpeed += gravity*delta;
		checkForCollisions();
		item.setY(item.getY() + verticalSpeed*delta);


	}

	private void climbLadder(float delta) {

		if(item.getRight()<elin.getX()) {
			isWalking=true;
			item.addAction(Actions.moveTo(elin.getX()-item.getWidth(), elin.getY()));
			item.setX(elin.getX()-item.getWidth());
			isWalking=false;}
		if(item.getX()>elin.getRight()) {
			isWalking=true;
			item.addAction(Actions.moveTo(elin.getRight(), elin.getY()));
			item.setX(elin.getRight());
			isWalking=false;
		}
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
		}),Actions.moveTo(item.getX(),item.getTop()+100f,2f),Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("fin monte");
			}
		}),Actions.delay(.6f),Actions.run(new Runnable(){
			@Override
			public void run() {
				setSpriterAnimationByName("debout");
			}
		})));
		item.setPosition(item.getX(),item.getTop()+100f);
	}

	private void crossBrigde() {
		stopFollowElin();
		if(item.getRight()<elin.getX()) {
			isWalking=true;
			item.addAction(Actions.moveTo(elin.getX()-item.getWidth(), elin.getY(),1f));
			item.addAction(Actions.moveTo(elin.getRight(), elin.getY(),1f));
			item.setX(elin.getRight());
			isWalking=false;
		}
		if(item.getX()>elin.getRight()) {
			isWalking=true;
			item.addAction(Actions.moveTo(elin.getRight(), elin.getY(),1f));
			item.addAction(Actions.moveTo(elin.getX()-item.getWidth(), elin.getY(),1f));
			item.setX(elin.getX()-item.getWidth());
			isWalking=false;
		}

	}

	private void attack() {

		isAttacking=true;
		setSpriterAnimationByName("attaque");


	}

	private void checkForCollisions() {
		checkBottomCollision();
		checkSideCollision();

	}

	private void followElin(float delta) {
		wasWalking = isWalking;
		isWalking = false;
		//if(isFollowingElin){
		if(item.getRight()<elin.getX()-50) {
			item.setX(item.getX()+delta*moveSpeed);
			item.setScaleX(1f);
			isWalking = true;
		}
		if(item.getX()>elin.getRight()+50) {
			item.setX(item.getX()-delta*moveSpeed);
			item.setScaleX(-1f);
			isWalking = true;
		}
		//}
		walkingAnimation();
	}

	private void walkingAnimation() {
		if(wasWalking == true && isWalking == false) {
			setSpriterAnimationByName("debout");
		}
		if(wasWalking == false && isWalking == true) {
			setSpriterAnimationByName("marche");
		}
	}

	private void checkBottomCollision() {
		float raySize = -(verticalSpeed+Gdx.graphics.getDeltaTime())*Gdx.graphics.getDeltaTime();
		if(raySize < 5f) raySize = 5f;
		if(verticalSpeed > 0) return;
		Vector2 rayFrom = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY()+item.getHeight()/2)*PhysicsBodyLoader.SCALE);
		Vector2 rayTo = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY() - raySize)*PhysicsBodyLoader.SCALE);
		stage.getWorld().rayCast(new RayCastCallback(){
			public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction){
				verticalSpeed = 0;
				item.setY(point.y/PhysicsBodyLoader.SCALE+0.1f);
				return 0;
			}
		}, rayFrom, rayTo);
	}

	private void checkSideCollision() {
		if(isWalking==false)return;
		Vector2 pointA = new Vector2(((item.getX()-5)*PhysicsBodyLoader.SCALE),(item.getY()+item.getHeight()/2)*PhysicsBodyLoader.SCALE);
		Vector2 pointB = new Vector2(((item.getRight()+5)*PhysicsBodyLoader.SCALE),(item.getY()+item.getHeight()/2)*PhysicsBodyLoader.SCALE);
		stage.getWorld().rayCast(new RayCastCallback() {
			public float reportRayFixture(Fixture fixture, Vector2 point,Vector2 normal, float fraction) {
				for(IBaseItem item: stage.sceneLoader.sceneActor.getItems()) {
					if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null && item.isComposite()) {
						if(((CompositeItem)item).getX()*PhysicsBodyLoader.SCALE<=point.x &&((CompositeItem)item).getRight()*PhysicsBodyLoader.SCALE>=point.x){
							if(!isAttacking){
								hurt();
								System.out.println("toucher monstre");
							}
							if(isAttacking){
								((CompositeItem)item).setVisible(false);
								((CompositeItem)item).getBody().setActive(false);
							}

						}
					}
				}  stopFollowElin();
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

	protected void stopFollowElin() {
		isWalking=false;
		isFollowingElin=false;
		item.setX(item.getX());

	}

	protected void hurt() {
		HP=HP-1;
		setSpriterAnimationByName("toucher");
		if(item.getScaleX()==1)
			item.setX(item.getX()-50);
		if(item.getScaleX()==-1)
			item.setX(item.getX()+50);

	}

	private void die(){
		item.addAction(Actions.fadeOut(1f));
		item.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				item.setPosition(initialCoordinates.x, initialCoordinates.y);

			}

		}));

	}

	private void setSpriterAnimationByName(String string) {
		spriterActor.setAnimation(spriterActor.getAnimations().indexOf(string));
	}
}

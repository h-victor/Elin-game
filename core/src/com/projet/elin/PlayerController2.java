package com.projet.elin;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriteAnimation;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by CyberJoe on 10/18/2014.
 */
public class PlayerController2 implements IScript {

	// Keep GameStage to access world later
	private Overlap2DStage stage;

	//Player
	private CompositeItem item;

	// Player Animation
	// private SpriteAnimation animation;
	private SpriterActor spriterActor;
	private Actor actor;

	private boolean isWalking = false;
	private boolean isGrounded = false;

	private float moveSpeed;
	private float gravity;
	private float jumpSpeed;

	private float verticalSpeed = 0;
	private int jumpCounter = 0;

	private Vector2 initialCoordinates;



	public PlayerController2(Overlap2DStage stage) {
		this.stage = stage;
	}

	@Override
	public void init(CompositeItem item) {
		this.item = item;


		moveSpeed = 150f * item.mulX;
		gravity = -1500f * item.mulY;
		jumpSpeed = 700f * item.mulY;


		spriterActor = item.getSpriterActorById("animation");
		// animation = item.getSpriteAnimationById("animation");
		// animation.pause();
		spriterActor.setAnimation(spriterActor.getAnimations().indexOf("debout"));
		// Setting item origin at the center
		item.setOrigin(item.getWidth()/2, 0);

		//setting initial position for later reset
		initialCoordinates = new Vector2(item.getX(), item.getY());
	}

	@Override
	public void act(float delta) {
		// Check for control events

		boolean wasWalking = isWalking;

		isWalking = false;
		if(item.getX()<item.getParentItem().getCompositeById("player").getX()+150) {
			// Go right
			item.setX(item.getX() + delta*moveSpeed);
			item.setScaleX(1f);
			isWalking = true;
			//spriterActor.setAnimation(spriterActor.getAnimations().indexOf("marche"));
		}
		if(item.getX()>item.getParentItem().getCompositeById("player").getX()-150) {
			// Go left
			item.setX(item.getX() - delta*moveSpeed);
			item.setScaleX(-1f);
			isWalking = true;
			//spriterActor.setAnimation(spriterActor.getAnimations().indexOf("marche"));
		}
		

		if(wasWalking == true && isWalking == false) {
			// Not walking anymore stop the animation;
			spriterActor.looping=false;
			spriterActor.setAnimation(spriterActor.getAnimations().indexOf("debout"));
			
		}
		if(wasWalking == false && isWalking == true) {
			// just started to walk, start animation
			spriterActor.looping=false;
			spriterActor.setAnimation(spriterActor.getAnimations().indexOf("toucher"));
			spriterActor.setAnimation(spriterActor.getAnimations().indexOf("marche"));
		}

		
		// Gravity
		verticalSpeed += gravity*delta;

		// ray-casting for collision detection
		checkForCollisions();
		// set the position
		item.setY(item.getY() + verticalSpeed*delta);

	}

	public void reset() {
		item.setX(initialCoordinates.x);
		item.setY(initialCoordinates.y);
		verticalSpeed = 0;
	}

	/*
	    Ray cast down, and if collision is happening stop player and reposition to closest point of collision
	 */
	private void checkForCollisions() {

		float rayGap = item.getHeight()/2;

		// Ray size is the exact size of the deltaY change we plan for this frame
		float raySize = -(verticalSpeed+Gdx.graphics.getDeltaTime())*Gdx.graphics.getDeltaTime();

		if(raySize < 5f) raySize = 5f;

		// only check for collisions when moving down
		if(verticalSpeed > 0) return;
	

		// Vectors of ray from middle middle
		Vector2 rayFrom = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY()+rayGap)*PhysicsBodyLoader.SCALE);
		Vector2 rayTo = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY() - raySize)*PhysicsBodyLoader.SCALE);

		// Cast the ray
		stage.getWorld().rayCast(new RayCastCallback() {
			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
				// Stop the player
				verticalSpeed = 0;

				// reposition player slightly upper the collision point
				item.setY(point.y/PhysicsBodyLoader.SCALE+0.1f);

				// make sure it is grounded, to allow jumping again
				isGrounded = true;
				jumpCounter = 0;

				return 0;
			}
		}, rayFrom, rayTo);
		if(isWalking ==false)return;
		Vector2 pointA = new Vector2((item.getX()*PhysicsBodyLoader.SCALE),(item.getY()+rayGap)*PhysicsBodyLoader.SCALE);
		Vector2 pointB = new Vector2((item.getRight()*PhysicsBodyLoader.SCALE),(item.getY()+rayGap)*PhysicsBodyLoader.SCALE);

		stage.getWorld().rayCast(new RayCastCallback() {
			
			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point,
					Vector2 normal, float fraction) {
				spriterActor.setAnimation(spriterActor.getAnimations().indexOf("toucher"));
				isWalking = false;
				if(item.getScaleX()==-1f){
					item.setX(item.getX()+item.getWidth()/2);
				}
				else if(item.getScaleX()==1f){
					item.setX(item.getX()-item.getWidth()/2);
				}
				
				return 0;
			}
		}, pointA, pointB);
	}
	
	

	public CompositeItem getActor() {
		return item;
	}

	@Override
	public void dispose() {

	}


}




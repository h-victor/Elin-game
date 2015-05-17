package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriteAnimation;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import com.uwsoft.editor.renderer.script.IScript;

public class play implements IScript{
	private CompositeItem item;
	private SpriteAnimation animation;
	
		public ResourceManager rs;
		public SceneVO sl;
		public CompositeItem player2_, ground2_;
		public int plan = 1;
		public play(ResourceManager rs, SceneVO sl, CompositeItem player2_, CompositeItem ground2_){
			this.rs = rs;
			this.sl = sl;
			this.player2_ = player2_;
		}
	
	@Override
	public void init(CompositeItem item) {
		this.item = item;
		animation = item.getSpriteAnimationById("elin");
		//animation.pause();
			player2_.setVisible(false);
			FixtureDef fdef = new FixtureDef();
				//BodyDef bdef = new BodyDef();
				//player2_.setBody(player2_.getBody().getWorld().createBody(bdef));
				//PolygonShape shape = new PolygonShape();
				//shape.setAsBox(100, 100);
				//fdef.shape = shape;
			
			fdef.filter.categoryBits = b2dVars.BIT_PLAYER2;
			fdef.filter.maskBits = b2dVars.BIT_GROUND1;
			//player2_.getBody().createFixture(fdef).setUserData("player2");
			
			//player2_.getBody().getFixtureList().first().getFilterData();

	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void act(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			System.out.println("hello");
			//item.getCompositeById("player").getSpriteAnimationById("elin").getBody().applyLinearImpulse(100, 0, item.getCompositeById("player").getX(), item.getCompositeById("player").getY(), true);
			//item.getCompositeById("player").getSpriteAnimationById("elin").getBody().applyLinearImpulse(10, 0, item.getX(), item.getY(), true);
			//item.getCompositeById("player").getBody().setLinearVelocity(10, 0);
			
			
			//item.getBody().applyForceToCenter(0, 250, true); //applyForce(10, 0, item.getX(), item.getY(), false);
			//item.getBody().setLinearVelocity(0.1f, 0);
			//animation.start();
			if(plan == 1)
				item.getBody().applyLinearImpulse(1, 0, item.getBody().getPosition().x, item.getBody().getPosition().y, true);
			//else
				//player2_.getBody().applyLinearImpulse(1, 0, player2_.getBody().getPosition().x, player2_.getBody().getPosition().y, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			if(plan == 1)
				item.getBody().applyLinearImpulse(-1, 0, item.getBody().getPosition().x, item.getBody().getPosition().y, true);
			//else
				//player2_.getBody().applyLinearImpulse(-1, 0, player2_.getBody().getPosition().x, player2_.getBody().getPosition().y, true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			System.out.println(sl.libraryItems.get("ground1").y);
			//System.out.println(sl.libraryItems.get("ground1").scissorHeight / 2);
			//System.out.println(item.getBody().getPosition().y);
			System.out.println();
			
/*			if(sl.libraryItems.get("ground1").y //+
			//if(sl.getCompositeElementById("ground1").getHeight() +
			//if(rs.loadSceneVO("MainScene").libraryItems.get("ground1").y +
			//if(item.getCompositeById("ground1").getOriginY() +
			//if(item.getItemById("ground1").getDataVO().y +
			//if(item.getCompositeById("ground1").getY() +
					//item.getCompositeById("ground1").getHeight() / 2
					
					//sl.libraryItems.get("ground1").
					== //item.getBody().getPosition().y){
						sl.libraryItems.get("player").y){ 
				item.getCompositeById("player").setY(item.getCompositeById("ground2").getY() +
						item.getCompositeById("ground2").getHeight() / 2);
*///			}
				//item.setY(sl.libraryItems.get("ground2").y);
				System.out.println(/*item.getY()*/ sl.libraryItems.get("player").y);
				System.out.println(sl.libraryItems.get("ground2").y);
				sl.libraryItems.get("player").y = sl.libraryItems.get("ground2").y;

					//item.getBody().applyForceToCenter(0, 100, true);
//					sl.libraryItems.get("player").scaleY = 0.2f;
					//item.getCompositeById("player2").setVisible(false);
					if(plan == 1){
					player2_.setX(item.getX());
					item.setVisible(false);
					player2_.setVisible(true);
					plan = 2;
					}
					else{
						item.setX(player2_.getX());
						player2_.setVisible(false);
						item.setVisible(true);
						plan = 1;
					}
					
					//item.setPosition(0f, 200f);
				// Problème, ne change pas de place, ne correspond pas aux bonnes coordonnées du joueur
		}
		
	}
	

}

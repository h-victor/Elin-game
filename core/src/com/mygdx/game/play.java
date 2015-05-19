package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.actor.SpriteAnimation;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import com.uwsoft.editor.renderer.script.IScript;

public class play implements IScript{
	private CompositeItem item;
	private SpriteAnimation animation;
	
		public ResourceManager rs;
		public SceneVO sl;
		public CompositeItem ground2_;
		public int plan = 1;
		public play(ResourceManager rs, SceneVO sl, CompositeItem ground2_){
			this.rs = rs;
			this.sl = sl;
	}
	
	@Override
	public void init(CompositeItem item) {
		this.item = item;
		//animation = item.getSpriteAnimationById("elin");
		//animation.pause();
/*				FixtureDef fdef = new FixtureDef();
				//BodyDef bdef = new BodyDef();
				//player2_.setBody(player2_.getBody().getWorld().createBody(bdef));
				//PolygonShape shape = new PolygonShape();
				//shape.setAsBox(100, 100);
				//fdef.shape = shape;
			
			fdef.filter.categoryBits = b2dVars.BIT_PLAYER2;
			fdef.filter.maskBits = b2dVars.BIT_GROUND1;
			//player2_.getBody().createFixture(fdef).setUserData("player2");
*/			
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
				/*item.getCompositeById("player").getItemById("elin").getBody().applyLinearImpulse(
						10, 0, item.getCompositeById("player").getItemById("elin").getBody().getPosition().x, 
						item.getCompositeById("player").getItemById("elin").getBody().getPosition().y, true); //.applyForceToCenter(10, 0, true);
				 */
				//ArrayList<IBaseItem> a = new ArrayList<IBaseItem>();
//				Object b = new Object();
				//b = 
						//System.out.println(item.getCompositeById("player").getItemById("elin").getBody().toString());
						item.getCompositeById("player").getBody().applyForceToCenter(10, 0, true);
		
						//item.getItemsByLayerName("Foreground").get(2).getBody().applyForceToCenter(10, 0, false); //toArray(/*a*/);
				//System.out.println(b);
				//System.out.println(a[0]);
			//}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			item.getCompositeById("player").getBody().applyForceToCenter(-10, 0, true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
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
//				System.out.println(/*item.getY()*/ sl.libraryItems.get("player").y);
//				System.out.println(sl.libraryItems.get("ground2").y);
//				sl.libraryItems.get("player").y = sl.libraryItems.get("ground2").y;

//					item.getItemById("player").getBody().applyForceToCenter(0, 100, true);

/*			item.getItemById("player").getBody().getPosition().set(
					item.getItemById("player").getBody().getPosition().x, 
					item.getItemById("ground2").getParentItem().getY() +  //getBody().getPosition().y +
					item.getItemById("ground2").getParentItem().getHeight());
*/

			System.out.println(item.getCompositeById("ground2").getY());
			item.getCompositeById("player");//.getBody().setTransform(0, 20);
					//item.getCompositeById("ground2").getY() +
					//item.getCompositeById("ground2").getHeight() / 2
					//, 0);
			
					//item.setPosition(0f, 200f);
				// Problème, ne change pas de place, ne correspond pas aux bonnes coordonnées du joueur
		}
		
	}

}

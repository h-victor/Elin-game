package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.data.SceneVO;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class gameStage extends Overlap2DStage{

		public Array<Body> bodies;
			public ResourceManager rs;
			public SceneVO sl;

			
	public gameStage(ResourceManager rs){
			
		initSceneLoader();
		sl = sceneLoader.loadScene("MainScene");
		addActor(sceneLoader.getRoot());
	
	
			
			//player player_ = new player(this);
			//SpriteAnimation animation = 
					//sceneLoader.getRoot().getSpriteAnimationById("player").setPosition(20, 30);/*.getCompositeById("player")*///.getSpriteAnimationById("player"); //.addScript(player_);
	
					//World world = getWorld();
					//world.getBodies(bodies);
					//bodies.first().applyLinearImpulse(10, 0, bodies.first().getPosition().x, bodies.first().getPosition().y, true);
					
					//play play_ = new play();
					//item.getCompositeById("player").getSpriteAnimationById("elin").getBody().applyLinearImpulse(10, 0, item.getX(), item.getY(), true);

					//sceneLoader.getCompositeElementById("player").getBody().applyLinearImpulse(10, 0, sceneLoader.getCompositeElementById("player").getX(), sceneLoader.getCompositeElementById("player").getY(), true);
					
						CompositeItem ground2_ = sceneLoader.getRoot().getCompositeById("ground2");
					play play_ = new play(rs, sl, ground2_);
					sceneLoader.getRoot().addScript(play_);//getCompositeById("player").addScript(play_);
						//System.out.println(sceneLoader.sceneActor.getCompositeById("player2").getBody().getFixtureList().size);
						//Array<Body> bodies = null; 
						System.out.println("Fixture ?");
						// sceneLoader.essentials.world.getBodies(bodies);
						//if(bodies.size != 0)System.out.println(bodies.size);
						
						
						//sceneLoader.getRoot().getCompositeById("ground1").addScript(play_);

					
			//animation.pause();
	}
}

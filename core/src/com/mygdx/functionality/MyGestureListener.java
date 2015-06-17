package com.mygdx.functionality;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class MyGestureListener implements GestureListener {
	float velX, velY;
	float initialScale = 1;
	OrthographicCamera camera;

	boolean flinging = false;

	public boolean longPress = false;
	private boolean tap = false;
	private boolean zoom = false;
	private boolean isLadder = false;
	
	public boolean isCamera = false;

	public MyGestureListener(OrthographicCamera camera){
		this.camera = camera;
	}

	public MyGestureListener(){
		isCamera = false;
	}

	@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			flinging = false;
			if(isCamera){
				initialScale = camera.zoom;
			}
			longPress = false;

			return false;
		}

	@Override
		public boolean tap(float x, float y, int count, int button) {
			tap = true;
			
			return false;
		}

	@Override
		public boolean longPress(float x, float y) {
			longPress = true;

			return false;
		}

	@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			if(isCamera){
				flinging = true;
				velX = camera.zoom * velocityX * 0.5f;
				velY = camera.zoom * velocityY * 0.5f;
			}
			isLadder = true;
			
			return false;
		}

	@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			if(isCamera){
				camera.position.add(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
			}

			return false;
		}

	@Override
		public boolean panStop(float x, float y, int pointer, int button) {

			return false;
		}

	@Override
		public boolean zoom(float initialDistance, float distance) {
			if(isCamera){
				float ratio = initialDistance / distance;
				camera.zoom = initialScale * ratio;
			}

			zoom = true;

			return false;
		}

	@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
		
			return false;
		}

	public void update() {
		if (flinging) {
			velX *= 0.98f;
			velY *= 0.98f;
			camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY * Gdx.graphics.getDeltaTime(), 0);
			if (Math.abs(velX) < 0.01f) velX = 0;
			if (Math.abs(velY) < 0.01f) velY = 0;
		}
	}

	/* get tap */
	public boolean getTap(){
		return tap;
	}
    /* set tap */
    public void setTap(boolean tap){
        this.tap = tap;
    }

	/* get zoom */
	public boolean getZoom(){
		return zoom;
	}
    /* set zoom */
    public void setZoom(boolean zoom){
        this.zoom = zoom;
    }
        
	/* get ladder */
	public boolean getLadder(){
		return isLadder;
	}
    /* set ladder */
    public void setLadder(boolean isLadder){
        this.isLadder = isLadder;
    }
}

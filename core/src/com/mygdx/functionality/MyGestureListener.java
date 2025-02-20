package com.mygdx.functionality;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ElinApplication;

public class MyGestureListener implements GestureListener {
	private ElinApplication app;

	float velX, velY;
	float initialScale = 1;
	OrthographicCamera camera;

	boolean flinging = false;

	private boolean isLongPress = false;
	private boolean isTap = false;
	private boolean isZoom = false;
	private boolean isLadder = false;
	private boolean isTouchdown = false;
	private boolean isFling = false;
	public boolean isCamera = false;
	private boolean isPan ;

	//	public MyGestureListener(OrthographicCamera camera){
	//		this.camera = camera;
	//	}
	//
	//	public MyGestureListener(){
	//		isCamera = false;
	//	}

	public MyGestureListener(ElinApplication elinApplication) {
		this.app=elinApplication;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		//			flinging = false;
		//			if(isCamera){
		//				initialScale = camera.zoom;
		//			}
		//			longPress = false;


		return false;
	}
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		isTap = true;
		//		System.out.println("tap ="+isTap);
		if(count==2 && !app.getGameStage_().getIsDialog()){
			app.getGameStage_().getMartenScript().attack();
		}
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		isLongPress = true;
		//		System.out.println("longpress ="+isLongPress);
		//		app.getGameStage_().getElinScript().elinTransformToLadder();
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		//			if(isCamera){
		//				flinging = true;
		//				velX = camera.zoom * velocityX * 0.5f;
		//				velY = camera.zoom * velocityY * 0.5f;
		//			}
		//			isLadder = true;
		isFling=true;
		//		System.out.println("fling ="+isFling);
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		isPan=true;
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {

		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {


		isZoom = true;

		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		System.out.println("pointer "+(pointer1.dst(pointer2)-initialPointer1.dst(initialPointer2)));


		if((pointer1.dst(pointer2)-initialPointer1.dst(initialPointer2)>150)&&(pointer1.dst(pointer2)-initialPointer1.dst(initialPointer2)<170)){
			Vector2 OX= new Vector2(4,0);
			Vector2 AB= pointer2.sub(pointer1);
			float angle =(float) Math.toDegrees(Math.acos(AB.dot(OX)/(AB.len()*OX.len())));

			if((angle>80&&angle<100))
				app.getGameStage_().getElinScript().elinTransformToLadder();
			if(angle<20||angle>160)
				app.getGameStage_().getElinScript().elinTransformToBridge();

		}

		return true;
	}

	//	public void update() {
	//		if (flinging) {
	//			velX *= 0.98f;
	//			velY *= 0.98f;
	//			camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY * Gdx.graphics.getDeltaTime(), 0);
	//			if (Math.abs(velX) < 0.01f) velX = 0;
	//			if (Math.abs(velY) < 0.01f) velY = 0;
	//		}
	//	}

	/**
	 * @return the isLongPress
	 */
	public boolean isLongPress() {
		return isLongPress;
	}

	/**
	 * @param isLongPress the isLongPress to set
	 */
	public void setLongPress(boolean isLongPress) {
		this.isLongPress = isLongPress;
	}

	/**
	 * @return the isTap
	 */
	public boolean isTap() {
		System.out.println("GestureListener isTap() = "+ isTap);
		return isTap;
	}

	/**
	 * @param isTap the isTap to set
	 */
	public void setTap(boolean isTap) {
		this.isTap = isTap;
	}

	/**
	 * @return the isZoom
	 */
	public boolean isZoom() {
		return isZoom;
	}

	/**
	 * @param isZoom the isZoom to set
	 */
	public void setZoom(boolean isZoom) {
		this.isZoom = isZoom;
	}

	/**
	 * @return the isLadder
	 */
	public boolean isLadder() {
		return isLadder;
	}

	/**
	 * @param isLadder the isLadder to set
	 */
	public void setLadder(boolean isLadder) {
		this.isLadder = isLadder;
	}

	/**
	 * @return the isTouchdown
	 */
	public boolean isTouchdown() {
		return isTouchdown;
	}

	/**
	 * @param isTouchdown the isTouchdown to set
	 */
	public void setTouchdown(boolean isTouchdown) {
		this.isTouchdown = isTouchdown;
	}

	/**
	 * @return the isFling
	 */
	public boolean isFling() {
		return isFling;
	}

	/**
	 * @param isFling the isFling to set
	 */
	public void setFling(boolean isFling) {
		this.isFling = isFling;
	}
	/**
	 * @return the isPan
	 */
	public boolean isPan() {
		System.out.println("GestureListener isPan() = "+ isPan);
		return isPan;
	}

	/**
	 * @param isPan the isPan to set
	 */
	public void setPan(boolean isPan) {
		this.isPan = isPan;
	}
}

package com.mygdx.functionality;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;

public class accelerometer {
	public int accelerometerX, accelerometerY, accelerometerZ;
	public boolean smartphoneFlatNormal, smartphoneLandscapeNormal, smartphonePortraitNormal;
	public boolean smartphoneFlatInverse, smartphoneLandscapeInverse, smartphonePortraitInverse;
	public boolean wasSmartphonePortraitInverse, wasSmartphonePortraitNormal;
	public boolean wasSmartphoneLandscapeInverse, wasSmartphoneLandscapeNormal;
	public boolean isRotated;

	/* return smartphone */
	public boolean state1LandscapeNormal, state2FlatNormal, state3LandscapeInverse, state4FlatInverse, state5LandscapeNormal;
	public boolean state2FlatInverse, state4FlatNormal, state5LandscapeNormalInverse; // inverse return smartphone
	
	/* raise a little the smartphone */
	public boolean isRaise;
	
	public Camera camera;
	
	public accelerometer(Camera camera){
		this.camera = camera;
		isRotated = false;
		
		/* return smartphone */
		state1LandscapeNormal = false;
		state2FlatNormal = false;
		state3LandscapeInverse = false;
		state4FlatInverse = false;
		state5LandscapeNormal = false;
		
		/* Inverse return smartphone */
		state2FlatInverse = false;
		state4FlatNormal = false;
		state5LandscapeNormalInverse = false;

		/* raise a little the smartphone */
		isRaise = false;
	}
	
	public void getAccelerometerPosition(){
		/* Accelerometer to landscape to portrait */
		accelerometerX = (int) Gdx.input.getAccelerometerX();
		accelerometerY = (int) Gdx.input.getAccelerometerY();
		accelerometerZ = (int) Gdx.input.getAccelerometerZ();
	
		/* Initialize boolean at false */
		smartphoneFlatNormal = false;
		smartphoneLandscapeNormal = false;
		smartphonePortraitNormal = false;
		
		smartphoneFlatInverse = false;
		smartphoneLandscapeInverse = false;
		smartphonePortraitInverse = false;
		
		/* Normal mode */
		if(accelerometerX == 0 && accelerometerY == 0 && (accelerometerZ == 10 || accelerometerZ == 9 || accelerometerZ == 8)){
			smartphoneFlatNormal = true;
		}
		else if((accelerometerX == 10 || accelerometerX == 9 || accelerometerX == 8) && accelerometerY == 0 && accelerometerZ == 0){
			smartphoneLandscapeNormal = true;
	
			wasSmartphoneLandscapeNormal = true;
			wasSmartphoneLandscapeInverse = false;
		}
		else if(accelerometerX == 0 && (accelerometerY == 10 || accelerometerY == 9 || accelerometerY == 8) && accelerometerZ == 0){
			smartphonePortraitNormal = true;			
	
			wasSmartphonePortraitInverse = true;
			wasSmartphonePortraitNormal= false;
		}
	
		/* Inverse mode */
		else if(accelerometerX == 0 && accelerometerY == 0 && (accelerometerZ == -10 || accelerometerZ == -9 || accelerometerZ == -8)){
			smartphoneFlatInverse = true;
		}
		else if((accelerometerX == -10 || accelerometerX == -9 || accelerometerX == -8) && accelerometerY == 0 && accelerometerZ == 0){
			smartphoneLandscapeInverse = true;
			
			wasSmartphoneLandscapeNormal = false;
			wasSmartphoneLandscapeInverse = true;
		}
		else if(accelerometerX == 0 && (accelerometerY == -10 || accelerometerY == -9 || accelerometerY == -8) && accelerometerZ == 0){
			smartphonePortraitInverse = true;			
			
			wasSmartphonePortraitInverse = false;
			wasSmartphonePortraitNormal= true;
		}
	}
	
	/* rotate camera to landscape to portrait */
	public void rotateCamera(){
		/* Activate the rotation of the camera Normal Mode*/
		if(smartphoneLandscapeNormal && isRotated){
			if(wasSmartphonePortraitNormal)
				this.camera.rotate(camera.direction, 90f); 
			else if(wasSmartphonePortraitInverse)
				this.camera.rotate(camera.direction, -90f); 	
			
			this.camera.update();
			isRotated = false;
		}
		else if(smartphonePortraitNormal && !isRotated){
			if(wasSmartphoneLandscapeNormal)
				camera.rotate(camera.direction, 90f); 
			else if(wasSmartphoneLandscapeInverse)
				camera.rotate(camera.direction, -90f);
			
			camera.update();
			isRotated = true;
		}

		/* Activate the rotation of the camera Inverse Mode*/
		else if(smartphoneLandscapeInverse && isRotated){
			if(wasSmartphonePortraitInverse)
				camera.rotate(camera.direction, 90f); 
			else if(wasSmartphonePortraitNormal)
				camera.rotate(camera.direction, -90f);
			
			camera.update();
			isRotated = false;
		}
		else if(smartphonePortraitInverse && !isRotated){
			if(wasSmartphoneLandscapeNormal)
				camera.rotate(camera.direction, -90f); 
			else if(wasSmartphoneLandscapeInverse)
				camera.rotate(camera.direction, 90f);
				
			camera.update();
			isRotated = true;
		}
	}
	
	/*******Function Return the smartphone*******/
	public boolean returnSmartphoneNormal(){
		if(smartphoneLandscapeNormal && !state4FlatInverse){
			state1LandscapeNormal = true;
			
			state5LandscapeNormal = false;
		}
		
		else if(smartphoneFlatNormal && state1LandscapeNormal)			
			state2FlatNormal = true;
		
		else if(smartphoneLandscapeInverse && state2FlatNormal)
			state3LandscapeInverse = true;
		
		else if(smartphoneFlatInverse && state3LandscapeInverse)
			state4FlatInverse = true;
		
		else if(smartphoneLandscapeNormal && state4FlatInverse){
			state5LandscapeNormal = true;
		
			state2FlatNormal = false;
			state3LandscapeInverse = false;
			state4FlatInverse = false;
		}
		
		return state5LandscapeNormal;
	}

	
	public boolean returnSmartphoneinverse(){
		if(smartphoneLandscapeNormal && !state4FlatNormal){
			state1LandscapeNormal = true;
			
			state5LandscapeNormalInverse = false;
		}
		
		else if(smartphoneFlatInverse && state1LandscapeNormal)			
			state2FlatInverse = true;
		
		else if(smartphoneLandscapeInverse && state2FlatInverse)
			state3LandscapeInverse = true;
		
		else if(smartphoneFlatNormal && state3LandscapeInverse)
			state4FlatNormal = true;
		
		else if(smartphoneLandscapeNormal && state4FlatNormal){
			state5LandscapeNormalInverse = true;
		
			state2FlatInverse = false;
			state3LandscapeInverse = false;
			state4FlatNormal = false;
		}
		
		return state5LandscapeNormalInverse;
	}

	
	/* raise a little the smartphone */
	boolean raise = false;
	public boolean raiseALittleSmartphone(){
		if(wasSmartphoneLandscapeNormal && accelerometerX == 5){
			raise = true;
			//isRaise = true;
		}
		else{
			if(raise && smartphoneLandscapeNormal){isRaise = true; raise = false;}
			else{isRaise = false;}
		}
		
		return isRaise;
	}
}

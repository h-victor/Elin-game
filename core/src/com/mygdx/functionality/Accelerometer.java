package com.mygdx.functionality;
/*esto es una prueba de commit*/
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;

public class Accelerometer {
    public int accelerometerX, accelerometerY, accelerometerZ;
    public boolean smartphoneFlatNormal, smartphoneLandscapeNormal, smartphonePortraitNormal;
    public boolean smartphoneFlatInverse, smartphoneLandscapeInverse, smartphonePortraitInverse;
    public boolean wasSmartphonePortraitInverse, wasSmartphonePortraitNormal;
    public boolean wasSmartphoneLandscapeInverse, wasSmartphoneLandscapeNormal;
    public boolean isRotated;
    //past and present status
    public boolean s0 = false,s1=false,s2=false,s3=false,s4=false,def=true;
    public boolean s0i = false,s1i=false,s2i=false,s3i=false,s4i=false;

   

    /* boolean to move the player in the good way in Elin Script */
    /* don't work with the actual boolean because it implies conflicts in rotation*/
    public boolean smartphoneLandscapeNormal2, smartphonePortraitNormal2;
    public boolean smartphoneLandscapeInverse2, smartphonePortraitInverse2;

    /* raise a little the smartphone */
    public boolean isRaise;

    public Camera camera;

    public Accelerometer(Camera camera){
        this.camera = camera;
        isRotated = false;

       

        /* raise a little the smartphone */
        isRaise = false;

        wasSmartphoneLandscapeNormal = true;
        smartphoneLandscapeNormal2 = true;
    }

    public void getAccelerometerPosition(){
        /* Accelerometer to landscape to portrait */
        accelerometerX = (int) Gdx.input.getAccelerometerX();
        accelerometerY = (int) Gdx.input.getAccelerometerY();
        accelerometerZ = (int) Gdx.input.getAccelerometerZ();

        /* Initialize boolean at false */
        /* can't be in each function because it cause conflict in the rotation */
        smartphoneFlatNormal = false;
        smartphoneLandscapeNormal = false;
        smartphonePortraitNormal = false;

        smartphoneFlatInverse = false;
        smartphoneLandscapeInverse = false;
        smartphonePortraitInverse = false;

        /* Normal mode */
        if(accelerometerX == 0 && accelerometerY == 0 && accelerometerZ > 6){
            smartphoneFlatNormal = true;
        }
        else if(accelerometerX > 6 && (accelerometerY >= -1 && accelerometerY <= 1) && accelerometerZ < 8/*== 0*/) {
            smartphoneLandscapeNormal = true;

            smartphoneLandscapeNormal2 = true; smartphonePortraitNormal2 = false;
            smartphoneLandscapeInverse2 = false; smartphonePortraitInverse2= false;
        }
        else if((accelerometerX >= -1 && accelerometerX <= 1) && accelerometerY > 6 && accelerometerZ < 8/*== 0*/){
            smartphonePortraitNormal = true;			

            smartphonePortraitNormal2 = true; smartphoneLandscapeNormal2 = false;
            smartphonePortraitInverse2= false; smartphoneLandscapeInverse2 = false;            
        }

        /* Inverse mode */
        else if(accelerometerX == 0 && accelerometerY == 0 && accelerometerZ < -6){
            smartphoneFlatInverse = true;
        }
        else if(accelerometerX < -6 && (accelerometerY >= -1 && accelerometerY <= 1) && accelerometerZ < 8/*== 0*/){
            smartphoneLandscapeInverse = true;

            smartphoneLandscapeInverse2 = true; smartphonePortraitInverse2 = false;
            smartphoneLandscapeNormal2 = false; smartphonePortraitNormal2 = false;
        }
        else if((accelerometerX >= -1 && accelerometerX <= 1) && accelerometerY < -6 && accelerometerZ < 8/*== 0*/){
            smartphonePortraitInverse = true;			

            smartphonePortraitInverse2 = true; smartphoneLandscapeInverse2 = false;
            smartphonePortraitNormal2 = false; smartphoneLandscapeNormal2 = false;
        }
        
        
        
        
        
        
        
        
        
        //detect status for going to past
        
        
        if ((accelerometerZ > 7 && accelerometerZ < 11) && accelerometerX <= 0 && (s4 == true || s0 == true || def == true)) {
        	System.out.println("STATES INFO: 0");	
        	s0 = true;
        		s1 = false; s2 = false; s3 = false; s4 = false; def=false;
        }
        else if ((accelerometerZ > -1 && accelerometerZ < 8) && accelerometerX < 0 && (s0 == true || s1 == true)) {
        	System.out.println("STATES INFO: 1");
        	s1 = true;
    		s0 = false; s2 = false; s3 = false; s4 = false; 
        }
        else if ((accelerometerZ  >  -11 && accelerometerZ < 1) && accelerometerX < 0 && (s1 == true || s2 == true)) {
        	System.out.println("STATES INFO: 2");
        	s2 = true;
    		s0 = false; s1 = false; s3 = false; s4 = false;
        }
        else if ((accelerometerZ  >  -11 && accelerometerZ < 1) && accelerometerX >= 0 && (s2 == true || s3 == true)) {
        	System.out.println("STATES INFO: 3");
        	s3 = true;
    		s0 = false; s1 = false; s2 = false; s4 = false;
        }
        else if ((accelerometerZ  <  11 && accelerometerZ > 0) && accelerometerX > 0 && (s3 == true || s4 == true)) {
        	System.out.println("STATES INFO: 4");
        	s4 = true;
    		s0 = false; s1 = false; s2 = false; s3 = false;
        }
        else {
        	s0 = false; s1 = false; s2 = false; s3 = false; s4 = false; def = true;
        }
        
        //detect status for returning to present
        
        
        if ((accelerometerZ  <  11 && accelerometerZ > 0) && accelerometerX > 0 && (s4i == true || s0i == true  || def == true)) {
        	System.out.println("STATES INFO: 0 i");	
        	    s0i = true;
        		s1i = false; s2i = false; s3i = false; s4i = false; def=false;
        }
        else if ((accelerometerZ  >  -11 && accelerometerZ < 1) && accelerometerX >= 0 && (s0i == true || s1i == true)) {
        	System.out.println("STATES INFO: 1 i");
        	s1i = true;
    		s0i = false; s2i = false; s3i = false; s4i = false; 
        }
        else if ((accelerometerZ  >  -11 && accelerometerZ < 1) && accelerometerX < 0 && (s1i == true || s2i == true)) {
        	System.out.println("STATES INFO: 2 i");
        	s2i = true;
    		s0i = false; s1i = false; s3i = false; s4i = false;
        }
        else if ((accelerometerZ > -1 && accelerometerZ < 8) && accelerometerX < 0 && (s2i == true || s3i == true)) {
        	System.out.println("STATES INFO: 3 i");
        	s3i = true;
    		s0i = false; s1i = false; s2i = false; s4i = false;
        }
        else if ((accelerometerZ > 7 && accelerometerZ < 11) && accelerometerX <= 0 && (s3i == true || s4i == true)) {
        	System.out.println("STATES INFO: 4 i");
        	s4i = true;
    		s0i = false; s1i = false; s2i = false; s3i = false;
        }
        else {
        	s0i = false; s1i = false; s2i = false; s3i = false; s4i = false; def = true;
        }
        
        
        
        
    }
    
    private void clearStatus() {
    	s0 = false; s1 = false; s2 = false; s3 = false; s4 = false; def = true;
    	s0i = false; s1i = false; s2i = false; s3i = false; s4i = false;
    }

    /* rotate camera to landscape to portrait */
    public void rotateCamera(){
        /* Activate the rotation of the camera Normal Mode*/
        if(smartphoneLandscapeNormal /*&& isRotated*/){
            if(wasSmartphonePortraitNormal){
                this.camera.translate(0, -0.4f * camera.viewportHeight,0);
                this.camera.rotate(camera.direction, -90f);//90f); 
                wasSmartphonePortraitNormal = false;
            }
            else if(wasSmartphonePortraitInverse){
                this.camera.translate(0, -0.4f * camera.viewportHeight,0);
                this.camera.rotate(camera.direction, 90f);//-90f);
                wasSmartphonePortraitInverse = false;
            }
            else if(wasSmartphoneLandscapeInverse){
                this.camera.rotate(camera.direction, 180f);
                wasSmartphoneLandscapeInverse = false;
            }
            wasSmartphoneLandscapeNormal = true;
            this.camera.update();
            // isRotated = false;
        }
        else if(smartphonePortraitNormal /*&& !isRotated*/){
            if(wasSmartphoneLandscapeNormal){
                this.camera.translate(0, 0.4f * camera.viewportHeight,0);
                camera.rotate(camera.direction, 90f); 
                wasSmartphoneLandscapeNormal = false;
            }
            else if(wasSmartphoneLandscapeInverse){
                this.camera.translate(0, 0.4f * camera.viewportHeight,0);
                camera.rotate(camera.direction, -90f);
                wasSmartphoneLandscapeInverse = false;
            }
            else if(wasSmartphonePortraitInverse){
                camera.rotate(camera.direction, 180f);
                wasSmartphonePortraitInverse = false;
            }
            wasSmartphonePortraitNormal = true;
            camera.update();
            // isRotated = true;
        }

        /* Activate the rotation of the camera Inverse Mode*/
        else if(smartphoneLandscapeInverse /*&& isRotated*/){
            if(wasSmartphonePortraitInverse){
                this.camera.translate(0, -0.4f * camera.viewportHeight,0);
                camera.rotate(camera.direction, -90f);//90f);
                wasSmartphonePortraitInverse = false;
            }
            else if(wasSmartphonePortraitNormal){
                this.camera.translate(0, -0.4f * camera.viewportHeight,0);
                camera.rotate(camera.direction, 90f);//-90f);
                wasSmartphonePortraitNormal = false;
            }
            else if(wasSmartphoneLandscapeNormal){
                camera.rotate(camera.direction, 180f);
                wasSmartphoneLandscapeNormal = false;
            }
            wasSmartphoneLandscapeInverse = true;
            camera.update();
            // isRotated = false;
        }
        else if(smartphonePortraitInverse /*&& !isRotated*/){
            if(wasSmartphoneLandscapeNormal){
                this.camera.translate(0, 0.4f * camera.viewportHeight,0);
                camera.rotate(camera.direction, -90f); 
                wasSmartphoneLandscapeNormal = false;
            }
            else if(wasSmartphoneLandscapeInverse){
                this.camera.translate(0, 0.4f * camera.viewportHeight,0);
                camera.rotate(camera.direction, 90f);
                wasSmartphoneLandscapeInverse = false;
            }
            else if(wasSmartphonePortraitNormal){
                camera.rotate(camera.direction, 180f);
                wasSmartphonePortraitNormal = false;
            }
            wasSmartphonePortraitInverse = true;
            camera.update();
            // isRotated = true;
        }
    }

    /*******Function Return the smartphone*******/
    public boolean returnSmartphoneNormal(){
      
    	if (s4 == true) {
    		clearStatus();
    		return true;
    	}
    	return false;
    }


    public boolean returnSmartphoneinverse(){
       
    	if (s4i == true) {
    		clearStatus();
    		return true;
    	}
    	return false;
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

    public boolean isPortraitNormal() {
        return smartphonePortraitNormal2;
    }
    public boolean isLandscapeNormal() {
        return smartphoneLandscapeNormal2;
    }
    public boolean isPortraitInverse() {
        return smartphonePortraitInverse2;
    }
    public boolean isLandscapeInverse() {
        return smartphoneLandscapeInverse2;
    }
}

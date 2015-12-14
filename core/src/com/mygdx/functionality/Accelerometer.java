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

    /* return smartphone */
    public boolean state1LandscapeNormal, state2FlatNormal, state3LandscapeInverse, state4FlatInverse, state5LandscapeNormal;
    public boolean state2FlatInverse, state4FlatNormal, state5LandscapeNormalInverse; // inverse return smartphone

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

    public boolean isPortraitNormal() {
        return smartphonePortraitNormal2;
    }
    public boolean isLandscapeNormal() {
        return smartphoneLandscapeNormal2;
    }
    public boolean isPortraitInverse() {
        return smartphonePortraitInverse2;
    }
    public boolean isLandscapeInvese() {
        return smartphoneLandscapeInverse2;
    }
}

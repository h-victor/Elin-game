package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.functionality.Accelerometer;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/*
 * contient la logique de la scene : changer de plan, tourner la camera, remonter dans le temps, dialogue
 */

public class GameStageScript implements IScript{
    private GameStage stage;
    private CompositeItem item;
    private OrthographicCamera camera;
    private Accelerometer accelerometer_;
//    private String message = "no";
    private boolean plan2 = false; // boolean, is true when player in the second plan
    private boolean landscape = true; // boolean, is true when camera is in landscape mode
    private CompositeItem marten;
    private CompositeItem elin;

    private DialogStageScript dialogStageScript;
    
    public GameStageScript(GameStage stage, CompositeItem elin, CompositeItem marten){
        this.marten=marten;
        this.elin=elin;
        this.stage= stage;
        this.camera=(OrthographicCamera) stage.getCamera();
    }

    @Override
    public void init(CompositeItem item) {    	
        this.item = item;
        accelerometer_ = new Accelerometer(camera);

        dialogStageScript = new DialogStageScript(stage);       
        stage.getMarten().getParentItem().getCompositeById("volund").setVisible(false);
    }

    @Override
    public void dispose() {
        item.dispose();
        stage.dispose();
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)||accelerometer_.raiseALittleSmartphone())
            changementPlan();		

        if(Gdx.input.isKeyJustPressed(Input.Keys.P))
            cameraRotation();

//        if(Gdx.input.isKeyJustPressed(Input.Keys.E))
//            showEnigma();

        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)||Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)){
                camera.zoom -= 0.1f; 
                camera.update();
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)){
                camera.zoom +=0.1f; 
                camera.update();
            }
        }

        /* accelerometer rotation camera to landscape to portrait */
        accelerometer_.getAccelerometerPosition();
        accelerometer_.rotateCamera();

        /* show enigma */
//        if(dialogStageScript.enigma)
//        	showEnigma();
    }

    /***** NE PAS TOUCHER********/
    private void cameraRotation() {
        if(landscape == true){
            camera.rotate(camera.direction, 90f); 
            camera.update();
            landscape = false;
        }
        else if(landscape == false){
            camera.rotate(camera.direction, -90f); 
            camera.update();
            landscape = true;
        }
    }

    private void changementPlan() {
        if(plan2 == false){
            elin.setScale(.9f);
            elin.setPosition(
                    elin.getX(),
                    item.getCompositeById("ground2").getTop() );

            marten.setScale(.9f);
            marten.setPosition(
                    marten.getX(),
                    item.getCompositeById("ground2").getTop() );
            plan2 = true;
        }
        else if(plan2 == true){
            elin.setScale(1);
            elin.setPosition(
                    elin.getX(),
                    item.getCompositeById("ground1").getTop());

            marten.setScale(1);
            marten.setPosition(
                    marten.getX(),
                    item.getCompositeById("ground1").getTop());
            plan2 = false;
        }
    }
    
    public CompositeItem getItem(){
    	return item;
    }
}
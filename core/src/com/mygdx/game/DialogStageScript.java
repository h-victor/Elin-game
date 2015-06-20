package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.functionality.MyGestureListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

public class DialogStageScript implements IScript{
    private DialogStage dialogStage;
    private GameStage gameStage;
    private MyGestureListener myGestureListener;
    boolean first = false;
    
    private CompositeItem item;

    private Dialog dialog;
    int wait = 0;

    boolean readLineWait = false;
    boolean readLine = false;

    public DialogStageScript(DialogStage dialogStage, GameStage gameStage/*, MyGestureListener myGestureListener*/){
        this.dialogStage = dialogStage;
        this.gameStage = gameStage;
        
 //       this.myGestureListener = myGestureListener;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        /*Dialog*/ dialog = new Dialog(this.item);
        dialog.readFile();
        
       // myGestureListener = new MyGestureListener();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void act(float delta) {
	     // Gesture Detector
//        if(!first){
//            Gdx.input.setInputProcessor(new GestureDetector(20, 0.5f, .5f, 0.15f, myGestureListener));
//            first = true;
//        }
        
    	//readDialog();

    //	myGestureListener.update();
    }

    public void readDialog(){  	
        if(wait == 0){
            readAndShowDialog("#beginIntroduction", "#endIntroduction");
        }
        else if( wait == 1){
            readAndShowDialog("#beginRetrouvaille", "#endRetrouvaille");
        }
        else if( wait == 2){
            readAndShowDialog("#beginDialog1", "#endDialog1");
        }
        else if( wait == 3){
            readAndShowDialog("#beginDialog2", "#endDialog2");
        }
        else if( wait == 4){
            readAndShowDialog("#beginCalin", "#endCalin");
        }
        else if( wait == 5){
            readAndShowDialog("#beginDialog3", "#endDialog3");
        }
        /* Monster */
        else if(isNearObject("dialogMonster") && wait == 6){
            readAndShowDialog("#beginDialog4", "#endDialog4");
        }
        /* Meet Volund */
        else if(isNearObject("dialogVolund") && wait == 7){
            readAndShowDialog("#beginDialog5", "#endDialog5");
        }
        /* Meet Volund + butterfly wings */
        else if(isNearObject("dialogVolund") && gameStage.getItemNb() == 3 && wait == 8){
            readAndShowDialog("#beginDialog6", "#endDialog6");
        }
        else if( wait == 9){
            readAndShowDialog("#beginJourSuivant", "#endJourSuivant");
        }
        else if( wait == 10){
            readAndShowDialog("#beginDialog7", "#endDialog7");
        } 
//		System.out.println(gameStage.sceneLoader.getRoot().getCompositeById("dialogMonster").getX());
//		System.out.println(gameStage.marten.getX()+100);       
       
    }

    public boolean readDialogLine(String beginDialog, String endDialog){
        if(!readLine){
            dialog.readLine(beginDialog, endDialog);
            readLineWait = dialog.readLineWait();
            readLine = true;
        }
        if(!readLineWait){
//            if(/*Gdx.input.isTouched()*/myGestureListener.isTap()){//isKeyJustPressed(Input.Keys.V)){
                readLineWait = dialog.readLineWait();
                //myGestureListener.setTap(false);
        //    }			
        }
        return readLineWait;
    }
    
    public void readAndShowDialog(String beginDialog, String endDialog){
        if(!readLineWait) 
            readLineWait = readDialogLine(beginDialog, endDialog);

        else if(readLineWait){
            wait++;
            readLineWait = false;
            readLine = false;
            
           // myGestureListener.setTap(false);
        }    	
    }
    
    public boolean isNearObject(String object){
    	if(((int)gameStage.sceneLoader.getRoot().getCompositeById(object).getX() +100 > (int)gameStage.getMarten().getX()
        		&& (int)gameStage.sceneLoader.getRoot().getCompositeById(object).getX()- 100 < (int)gameStage.getMarten().getX())){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
}

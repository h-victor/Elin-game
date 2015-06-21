package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

public class DialogStageScript implements IScript{
    private GameStage gameStage;
    boolean first = false;

    private CompositeItem item;

    private Dialog dialog;
    int wait = 0;

    boolean readLineWait = false;
    boolean readLine = false;

    public boolean enigma = false; // true, when we want to show the enigma
    
    public DialogStageScript(GameStage gameStage){
        this.gameStage = gameStage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        /*Dialog*/ dialog = new Dialog(this.item);
        dialog.readFile();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void act(float delta) {
        readDialog();
    }

    public void readDialog(){  	
        if(wait == 0){
            readAndShowDialog("#beginIntroduction", "#endIntroduction");
        }
        else if(wait == 1){
            readAndShowDialog("#beginRetrouvaille", "#endRetrouvaille");
        }
        else if(wait == 2){
            readAndShowDialog("#beginDialog1", "#endDialog1");
        }
        else if(wait == 3){
            readAndShowDialog("#beginDialog2", "#endDialog2");
        }
        else if(wait == 4){
            readAndShowDialog("#beginCalin", "#endCalin");
        }
        else if(wait == 5){
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
        else if(wait == 9){
            readAndShowDialog("#beginJourSuivant", "#endJourSuivant");
        }
        else if(wait == 10){
            readAndShowDialog("#beginDialog7", "#endDialog7");
        }
        /* Enigma before going to past */
        else if(isNearObject("dialogEnigma") && wait == 11){
        	readAndShowDialog("beginEnigma", "endEnigma");
        	enigma = true;
        }
        /* Enigma after returning to present */
        else if(isNearObject("dialogResolveEnigma") && wait == 12){
        	readAndShowDialog("beginResolveEnigma", "endResolveEnigma");
        }
    }

    public boolean readDialogLine(String beginDialog, String endDialog){
        if(!readLine){
            dialog.readLine(beginDialog, endDialog);
            readLineWait = dialog.readLineWait();
            readLine = true;
        }
        if(!readLineWait){
            if(Gdx.input.justTouched() && Gdx.input.getY() < 200){
                readLineWait = dialog.readLineWait();
            }			
        }
        return readLineWait;
    }

    public void readAndShowDialog(String beginDialog, String endDialog){
        if(!readLineWait){ 
        	readLineWait = readDialogLine(beginDialog, endDialog);

        	item.getLabelById("dialog").setVisible(true);
            item.getImageById("frame").setVisible(true);
        }
        else if(readLineWait){
            wait++;
            readLineWait = false;
            readLine = false;
            
            item.getLabelById("dialog").setVisible(false);
            item.getImageById("frame").setVisible(false);
        }    	
    }

    public boolean isNearObject(String object){
        if(gameStage.sceneLoader.getRoot().getCompositeById(object) != null){	
            if(((int)gameStage.sceneLoader.getRoot().getCompositeById(object).getX() + 100 > (int)gameStage.getMarten().getX()
                        && (int)gameStage.sceneLoader.getRoot().getCompositeById(object).getX()- 100 < (int)gameStage.getMarten().getX())){
                return true;
            }
            else{return false;}
        }
        else{
            return false;
        }
    }
}
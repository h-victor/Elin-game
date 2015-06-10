package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

public class DialogStageScript implements IScript{
    private DialogStage dialogStage;
    private CompositeItem item;

    private Dialog dialog;
    int wait = 0;

    boolean readLineWait = false;
    boolean readLine = false;

    public DialogStageScript(DialogStage dialogStage){
        this.dialogStage = dialogStage;
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
            if(!readLineWait) 
                readLineWait = readDialogLine("#beginIntroduction", "#endIntroduction");

            else if(readLineWait){
                wait++;
                readLineWait = false;
                readLine = false;
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.N) && wait == 1){
            if(!readLineWait) 
                readLineWait = readDialogLine("#beginCalin", "#endCalin");
            else if(readLineWait){
                wait++;
                readLineWait = false;
                readLine = false;
            }
        }

    }

    public boolean readDialogLine(String beginDialog, String endDialog){
        if(!readLine){
            dialog.readLine(beginDialog, endDialog);
            readLineWait = dialog.readLineWait();
            readLine = true;
        }
        if(!readLineWait){
            if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
                readLineWait = dialog.readLineWait();
            }			
        }
        return readLineWait;
    }
}

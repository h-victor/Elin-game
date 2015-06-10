package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.uwsoft.editor.renderer.actor.CompositeItem;

public class Dialog {
    FreeTypeFontGenerator generator;

    List<String> lines;
    private boolean begin = false;

    Camera camera;
    private CompositeItem item;

    // Variable for the first and the end of line of a dialog
    int firstI, lastI;
    int i; // Take the value of variable firstI, to be utilize in readLineWait

    public Dialog(CompositeItem item){
        this.item = item;
    }

    public void dispose(){
        generator.dispose();
    }

    public void readFile(){
        FileHandle file = Gdx.files.internal("DialogScreen.txt");
        BufferedReader reader = new BufferedReader(file.reader());
        /*List<String>*/ lines = new ArrayList<String>();
        String line = null;

        try {
            line = reader.readLine();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        while(line != null){
            lines.add(line);
            try {
                line = reader.readLine();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readLine(String beginDialog, String endDialog){
        i = 0; // the global variable
        firstI = 1000; lastI = -1000; // Initialise variables

        begin = false;
        while(!lines.get(i).contains(endDialog)){
            if(lines.get(i).contains(beginDialog)){
                begin = true;
            }
            else if(begin == true){
                System.out.println(lines.get(i));

                if(firstI > i)
                    firstI = i;
                if(lastI < i)
                    lastI = i;
            }
            i++;
        }
        
        i = firstI;
    }

    public boolean readLineWait() {
        boolean lastLine = false;

        if(i != lastI + 1){
            item.getLabelById("dialog").setText(	
                    lines.get(i));
            i++;
            lastLine = false;
        }

        else if(i == lastI + 1){
            lastLine = true;
        }

        return lastLine;
    }
}

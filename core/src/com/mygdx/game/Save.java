package com.mygdx.game;

import com.uwsoft.editor.renderer.actor.CompositeItem;

public class Save {
    private CompositeItem presentSave;

    public Save() {
        presentSave=null;
    }

    public CompositeItem getPresentSave() {
        return presentSave;
    }

    public void setPresentSave(CompositeItem presentSave) {
        this.presentSave=presentSave;
    }
}

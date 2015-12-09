package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.functionality.Accelerometer;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.IBaseItem;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class GameStage extends Overlap2DStage{
    private CompositeItem elin;
    private CompositeItem marten;
    private MartenScript martenScript;
    private ElinScript elinScript;
    private Save save;
    private boolean isPast = false;
    private boolean isTimeTravaler = false;
    private int itemNb;
    private GameStageScript gameStageScript;

    private Accelerometer accelerometer_;

    private boolean isDialog = true;
    
    public GameStage(ResourceManager resourceManager){
        super();
        initSceneLoader(resourceManager);
        initMainScene();    

        accelerometer_ = new Accelerometer((OrthographicCamera)this.getCamera());
        
        // force the camera to be at this resolution (and be the same for all device) in all stage        
        ((OrthographicCamera) this.getCamera()).setToOrtho(false, 1280, 720);
        this.getCamera().update();
    }

    private void initMainScene() {
    	sceneLoader.loadScene("MainScene");//Load scene data: world physic , resolution, light 
        addActor(sceneLoader.sceneActor);
        elin=sceneLoader.sceneActor.getCompositeById("elin");
        marten= sceneLoader.sceneActor.getCompositeById("marten");
        gameStageScript = new GameStageScript(this, elin , marten);
        sceneLoader.sceneActor.addScript(gameStageScript);
        save=new Save();
        martenScript=new MartenScript(this);
        elinScript= new ElinScript(this, this.getCamera());
        
        elin.addScript(elinScript);
        marten.addScript(martenScript);
        for(IBaseItem item: sceneLoader.sceneActor.getItems()) {
            if(item.getCustomVariables().getFloatVariable("cochonSpeed") != null && item.isComposite()) {
                ((CompositeItem)item).addScript(new MovingPigScript(this));
            }
            if(item.getCustomVariables().getFloatVariable("item") != null && item.isComposite()) {
                ((CompositeItem)item).addScript(new ItemScript(this));
            }
            if(item.getCustomVariables().getFloatVariable("food") != null && item.isComposite()) {
                ((CompositeItem)item).addScript(new FoodScript(this));
            }
        }

        Music music = Gdx.audio.newMusic(Gdx.files.internal("Celestial_Aeon_Project_-_Children.mp3"));
        music.play();
        music.setLooping(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.getCamera().position.x = elin.getX();
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)&&!isTimeTravaler) {
            if(isPast==false)
                goToPast();
            else if(isPast==true)
                returnToPresent();
        }

        accelerometer_.getAccelerometerPosition();
        
        /* Go to past */
        if(accelerometer_.returnSmartphoneNormal() && !isTimeTravaler){
            goToPast();
            Gdx.input.vibrate(1000);
        }
        /* Return to present */
        if(accelerometer_.returnSmartphoneinverse() && !isTimeTravaler){
            returnToPresent();
            Gdx.input.vibrate(1000);
        }
    }

    public void goToPast(){
        save.setPresentSave(sceneLoader.sceneActor);
        getActors().clear();
        sceneLoader.loadScene("PastScene");
        sceneLoader.getRoot().addActor(elin);
        sceneLoader.getRoot().addActor(marten);
        addActor(sceneLoader.getRoot());
        sceneLoader.getRoot().addScript(gameStageScript);
        isPast=true;
    }

    public void returnToPresent() {
        isTimeTravaler=true;
        isPast=false;
        getActors().clear();
        save.getPresentSave().removeActor(save.getPresentSave().getCompositeById("elin"));
        save.getPresentSave().removeActor(save.getPresentSave().getCompositeById("marten"));
        addActor(save.getPresentSave());
        addActor(elin);
        addActor(marten);
    }        

    /**
     * @return the itemNb
     */
    public int getItemNb() {
        return itemNb;
    }

    /**
     * @param itemNb the itemNb to set
     */
    public void setItemNb(int itemNb) {
        this.itemNb = itemNb;
    }

    /**
     * @return the marten
     */
    public CompositeItem getMarten() {
        return marten;
    }

    /**
     * @return the martenScript
     */
    public MartenScript getMartenScript() {
        return martenScript;
    }

    /**
     * @return the elinScript
     */
    public ElinScript getElinScript() {
        return elinScript;
    }
    
    // know if there is a dialog
    public boolean getIsDialog(){
    	return isDialog;
    }
    public void setIsDialog(boolean isDialog){
    	this.isDialog = isDialog;
    }
    
    public boolean isPortrait() {
    	accelerometer_.getAccelerometerPosition();
    	return  accelerometer_.isPortrait();
    }
    
    public boolean isInverse() {
    	accelerometer_.getAccelerometerPosition();
    	return accelerometer_.isInverse();
    }
}
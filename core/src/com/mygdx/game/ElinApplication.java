package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.functionality.MyGestureListener;
import com.uwsoft.editor.renderer.resources.ResourceManager;

public class ElinApplication extends ApplicationAdapter {
    private GameStage gameStage_;
    private MenuStage menuStage_; 

    private UIStage uiStage; 
    private DialogStage dialogStage;

    private ResourceManager resourceManager;
    private InputMultiplexer inputMultiplexer;
    
    	//public ActionResolver actionResolver;
	private final LightSensorInterface sensor;
    	public ElinApplication(/*ActionResolver actionResolver*/LightSensorInterface sensor){
        	/*this.actionResolver = actionResolver;
        	actionResolver.showToast("Hello");
        	actionResolver.showPressure();*/
        	this.sensor= sensor;
    	}
    	
    	
    @Override
    public void create () {
        resourceManager = new ResourceManager();
        resourceManager.initAllResources();

        menuStage_ = new MenuStage(resourceManager);
        gameStage_ = new GameStage(resourceManager);
        dialogStage = new DialogStage(gameStage_);
        uiStage = new UIStage(resourceManager,gameStage_);

        inputMultiplexer = new InputMultiplexer(); 
        inputMultiplexer.addProcessor(menuStage_); 
        inputMultiplexer.addProcessor(dialogStage); 
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(gameStage_);
        inputMultiplexer.addProcessor(new GestureDetector(new MyGestureListener(this)));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.5f, 0.8f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //actionResolver.showPressure();
        //comentadas dos lineas
        //System.out.println("Lux = " + sensor.getCurrentLux());
        //System.out.println("Proximity = " + sensor.getCurrentProximity());

/*        System.out.println("Gyroscope X = " + sensor.getGyroscopeX());
        System.out.println("Gyroscope Y = " + sensor.getGyroscopeY());
        System.out.println("Gyroscope Z = " + sensor.getGyroscopeZ());
*/        
        
        if(MenuStageScript.startGameStage == true){ // if true, draw the MenuStage
            gameStage_.act(Gdx.graphics.getDeltaTime());
            gameStage_.draw();
            dialogStage.act();
            dialogStage.draw();
            uiStage.act(Gdx.graphics.getDeltaTime());
            uiStage.draw();
        }
        else {
            menuStage_.act(Gdx.graphics.getDeltaTime());
            menuStage_.draw();
        }
    }

    /**
     * @return the gameStage_
     */
    public GameStage getGameStage_() {
        return gameStage_;
    }

    /**
     * @return the dialogStage
     */
    public DialogStage getDialogStage() {
        return dialogStage;
    }
}
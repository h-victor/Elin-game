/*package com.mygdx.game.android;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.mygdx.game.LightSensorInterface;

public class AndroidLightSensor extends Activity implements LightSensorInterface, SensorEventListener {
    private float currentLux = 0;
    	String message;
    	  private SensorManager mSensorManager;
    	  private Sensor mPressure;


    	  //@Override
    	  public final void onCreate(Bundle savedInstanceState) {
    	    super.onCreate(savedInstanceState);
//    	    setContentView(R.layout.main);

    	    // Get an instance of the sensor service, and use that to get an instance of
    	    // a particular sensor.
    	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    	    mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);//TYPE_PRESSURE);
    	  }
    	
    @Override
    public float getCurrentLux() {
        return currentLux;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.currentLux = event.values[0];
*/        
//			Gdx.input.getTextInput(new TextInputListener() {
//			@Override
//			public void input(String text){
//				message = text;
//				if(message/*.equals("aspirateur")*/ == "aspirateur"){Gdx.input.vibrate(1000);}
//			}
//	
//			@Override
//			public void canceled(){
//				message = "no";
//			}
//		}, "Je ne respire jamais, mais j'ai beaucoup de soufle. Qui suis-je ?", "", "");
//
//			System.out.println("hello");
//			System.out.println(currentLux);
//			Gdx.input.vibrate(1000);
/*    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	  @Override
	  public void onResume() {
	    // Register a listener for the sensor.
	    super.onResume();
	    mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    // Be sure to unregister the sensor when the activity pauses.
	    super.onPause();
	    mSensorManager.unregisterListener(this);
	  }	
*/	  
	  
/*		public SensorEventListener lightListener = new SensorEventListener() {
			public void onAccuracyChanged(Sensor sensor, int acc) { }
	 
			public void onSensorChanged(SensorEvent event) {
				float currentLux = event.values[0];

				//textLight.setText((int)x + " lux");
			}
		};*/
//}
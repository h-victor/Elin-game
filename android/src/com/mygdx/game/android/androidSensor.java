package com.mygdx.game.android;

import com.mygdx.game.android.R;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class androidSensor extends Activity implements SensorEventListener{

		SensorManager sm;
		Sensor proxSensor;
		
		float proximity;
		
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			//setContentView(R.layout.main);
			
			sm = (SensorManager)getSystemService(SENSOR_SERVICE);
			proxSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY); //TYPE_LIGHT
			
			sm.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
		}



		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
		
		}
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			System.out.println(String.valueOf(event.values[0]));
			proximity = event.values[0];
		}
		
		public float getProximity(){
			return proximity;
		}
}

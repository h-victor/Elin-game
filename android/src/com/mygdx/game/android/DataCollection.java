package com.mygdx.game.android;

import com.mygdx.game.LightSensorInterface;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

// http://stackoverflow.com/questions/18759849/how-to-write-a-class-to-read-the-sensor-value-in-android

public class DataCollection implements SensorEventListener, LightSensorInterface {

    SensorManager sm;
    Sensor s,p,g;
    private float currentLux, currentProximity;// = 0;
private float gyroscopeX,gyroscopeY,gyroscopeZ;
    
    public DataCollection(Context context){
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        p = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        g = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        ////////////////////////////////////////////////
        // important part (without this it won't work //
        sm.registerListener(
                this,//LightSensorListener, 
                s, 
                SensorManager.SENSOR_DELAY_NORMAL);
        
        sm.registerListener(
                this,//LightSensorListener, 
                p, 
                SensorManager.SENSOR_DELAY_NORMAL);

/*        sm.registerListener(
                this,//LightSensorListener, 
                g, 
                SensorManager.SENSOR_DELAY_NORMAL);
*/        
        /////////////////////////////////////////////////
    }

    @Override
        public float getCurrentLux() {
            return currentLux;
        }

    @Override
    public float getCurrentProximity() {
        return currentProximity;
    }

    @Override
    public float getGyroscopeX() {
        return gyroscopeX;
    }

    @Override
    public float getGyroscopeY() {
        return gyroscopeY;
    }

    @Override
    public float getGyroscopeZ() {
        return gyroscopeZ;
    }

    
    @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }

    @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                currentLux = event.values[0];
            }		
            else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
                currentProximity = event.values[0];
            }		
/*            else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
                gyroscopeX = event.values[0];
                gyroscopeY = event.values[1];
                gyroscopeZ = event.values[2];
            }		
*/    	}

    public void register(){
        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, p, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sm.unregisterListener(this);
    }
}

package com.mygdx.functionality;

import sun.management.Sensor;

public interface ActionResolver/*<Bundle, SensorEvent>*/ {
	public void showToast(CharSequence text);
	
/*    public void onCreate(Bundle savedInstanceState);
    public void onAccuracyChanged(Sensor sensor, int accuracy);
    public void onSensorChanged(SensorEvent event);
    public void onResume();
    public void onPause();
*/    
    public void showPressure();
}

package com.mygdx.functionality;

public interface proximitySensorAndroid<Bundle, Sensor, SensorEvent> {
	// based on http://developer.android.com/guide/topics/sensors/sensors_position.html
	// and https://code.google.com/p/libgdx-users/wiki/IntegratingAndroidNativeUiElements3TierProjectSetup
	
	  public void onCreate(Bundle savedInstanceState);
	  public void onAccuracyChanged(Sensor sensor, int accuracy);
	  public void onSensorChanged(SensorEvent event);
	  public void onResume();
	  public void onPause();
}

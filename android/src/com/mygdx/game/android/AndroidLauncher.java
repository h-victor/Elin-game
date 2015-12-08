package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.ElinApplication;

// http://android-er.blogspot.com.es/2012/08/determine-light-level-sensortypelight.html

public class AndroidLauncher extends AndroidApplication /*implements SensorEventListener, LightSensorInterface*/{
    DataCollection mDataCollection = null;

    @Override
        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

            mDataCollection = new DataCollection(this);
            initialize(new ElinApplication(mDataCollection), config);
        }
    
    @Override
    protected void onResume(){
        super.onResume();
        mDataCollection.register();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mDataCollection.unregister();
    }
}
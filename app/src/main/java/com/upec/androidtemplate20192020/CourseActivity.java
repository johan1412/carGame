package com.upec.androidtemplate20192020;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class CourseActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor capteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        capteur = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Ecran ecran = findViewById(R.id.ecran);
        float x = event.values[0];
        //float y = event.values[1];
       // float z = event.values[2];
        ecran.setCarPos(x*100);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, capteur, SensorManager.SENSOR_DELAY_NORMAL);
    }
}

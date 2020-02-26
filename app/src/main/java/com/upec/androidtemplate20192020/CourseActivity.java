package com.upec.androidtemplate20192020;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;


public class CourseActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor capteur;
    private Ecran ecran;
    private Chronometer chrono;
    private Boolean finished = false;
    private int SPEED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        capteur = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        TextView tv = findViewById(R.id.cmp_depart);
        CmpTask task = new CmpTask(tv);
        task.execute();

        this.chrono = findViewById(R.id.timer);
        TimerRunnable timer = new TimerRunnable(chrono);
        new Thread(timer).start();
    }

    public boolean isFinish() {
        return this.finished;
    }

    public void setFinish(boolean b) {
        this.finished = b;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ecran = findViewById(R.id.ecran);
        View v = ecran.getView();
        int w = v.getWidth();
        float x = event.values[1];
        ecran.setCarPos(x*(w/20));
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
        sensorManager.registerListener(this, capteur, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        } else {
            showSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}




class CmpTask extends AsyncTask<Void, Integer, Void> {
    TextView tv;

    public CmpTask(TextView tv) {
        super();
        this.tv = tv;
    }

    @Override
    protected Void doInBackground(Void... v) {
        for(int i=3; i >= 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... i) {
        tv.setText(Integer.toString(i[0]));
    }

    @Override
    protected void onPostExecute(Void v) {
        tv.setText("PARTEZ");
        /*try {
            Thread.sleep(500);
            tv.setVisibility(View.INVISIBLE);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}

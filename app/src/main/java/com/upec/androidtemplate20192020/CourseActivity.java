package com.upec.androidtemplate20192020;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;


public class CourseActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor capteur;
    private Thread courseThread;
    private Thread timerThread;
    private Ecran ecran;
    private Chronometer chrono;
    private int SPEED;
    private boolean firstMove;
    private boolean fin;
    private TextView tv;
    private ProgressBar pb;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        capteur = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.pb = findViewById(R.id.progressBar);
        this.SPEED = 1;
        ecran = findViewById(R.id.ecran);
        this.view = ecran.getView();
        this.firstMove = true;

        Button speedB = findViewById(R.id.moveButton);
        speedB.setEnabled(false);
        speedB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                speedClick();
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    SPEED++;
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    SPEED--;
                }
                return true;
            }
        });

        this.tv = findViewById(R.id.cmp_depart);
        CmpTask task = new CmpTask(tv);
        task.execute();

        this.chrono = findViewById(R.id.timer);
        TimerRunnable timer = new TimerRunnable(chrono, this, speedB);
        this.timerThread = new Thread(timer);
        timerThread.start();
    }



    public boolean isFinish() {
        return this.fin;
    }



    public void setFinish() {
        timerThread.interrupt();
        chrono.stop();
        courseThread.interrupt();
        tv.setText("Finish");
        this.fin = true;
    }



    public int getSPEED() {
        return this.SPEED;
    }



    public void setProgressBar(int i) {
        pb.setProgress(i);
    }



    public void printObstacles(ArrayList<Obstacle> listO) {
        ecran.drawObs(listO);
    }



    public void speedClick() {
        if(this.firstMove) {
            ThreadCourse course = new ThreadCourse(this, view.getWidth(), view.getHeight());
            this.courseThread = new Thread(course);
            courseThread.start();
            this.firstMove = false;
        }
        this.SPEED++;
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[1];
        ecran.setCarPos(x);
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

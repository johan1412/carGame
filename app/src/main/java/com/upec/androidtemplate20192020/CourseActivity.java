package com.upec.androidtemplate20192020;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;


public class CourseActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor capteur;
    private Thread courseThread;
    private Thread timerThread;
    private int lives;
    private Ecran ecran;
    private Chronometer chrono;
    private int SPEED;
    private boolean firstMove;
    private boolean fin;
    private TextView tv;
    private TextView speedView;
    private ProgressBar pb;
    private View view;
    private ImageView img;
    private int posPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        capteur = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        img = findViewById(R.id.mainCar);
        img.setImageResource(R.drawable.main_car);
        img.setMaxHeight(150);
        img.setMaxWidth(80);

        this.pb = findViewById(R.id.progressBar);
        this.SPEED = 0;
        this.lives = 3;
        this.ecran = findViewById(R.id.ecran);
        this.speedView = findViewById(R.id.viewSpeed);
        this.view = ecran.getView();
        this.firstMove = true;

        Button speedB = findViewById(R.id.moveButton);
        speedB.setEnabled(false);
        speedB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(firstMove) {
                    newCourse();
                    firstMove = false;
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    /*if (SPEED < 5) {
                        SPEED++;
                        speedView.setText("Speed : " + SPEED);
                    }*/
                    SPEED = 3;
                    speedView.setText("Speed : 1");
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    /*if(SPEED > 0) {
                        SPEED--;
                        speedView.setText("Speed : " + SPEED);
                    }*/
                    SPEED = 0;
                    speedView.setText("Speed : 0");
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



    public void newCourse() {
        ThreadCourse course = new ThreadCourse(this, view.getWidth(), view.getHeight(), getResources());
        courseThread = new Thread(course);
        courseThread.start();
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



    public void collision() {
        this.lives--;
        tv.setText("Accident ! \n -1 vie");
        TextView livesView = findViewById(R.id.viewLives);
        livesView.setText("Lives : " + lives);
        TextView speedView = findViewById(R.id.viewSpeed);
        speedView.setText("Speed : " + SPEED);
        try {
            courseThread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(this.lives > 0) {
            relance();
        } else {
            gameOver();
        }
    }



    public void gameOver() {
        tv.setTextColor(Color.RED);
        tv.setText("GAME OVER");
        timerThread.interrupt();
        chrono.stop();
        courseThread.interrupt();
        this.fin = true;
    }



    public void relance() {
        tv.setText("");
    }



    public void setProgressBar(int i) {
        pb.setProgress(i);
    }



    public void printObstacles(ArrayList<Obstacle> listO) {
        ecran.drawObs(listO);
    }



    public int getPosPlayer() {
        return this.posPlayer;
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        int x = (int)event.values[1];
        int posX = (int)img.getX();
        int new_pos = (posX + (x * 20));
        this.posPlayer = new_pos;
        if((posX > (view.getWidth()/4) + 40) && (posX < (view.getHeight()/4) - 40)) {
            img.setX(new_pos);
        }
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

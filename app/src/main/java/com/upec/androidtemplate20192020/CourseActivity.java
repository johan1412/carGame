package com.upec.androidtemplate20192020;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
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
    private ThreadCourse course;
    private ProgressBar pb;
    private View view;
    private int level;
    private ImageView img;
    private int posPlayer;
    private int tourMap;
    private boolean testMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Bundle b = getIntent().getExtras();
        if(b.getInt("test") == 1) {
            level = 0;
            testMode = true;
        } else {
            level = b.getInt("level", 1);
            testMode = false;
        }

        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        capteur = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        img = findViewById(R.id.mainCar);
        img.setImageResource(R.drawable.main_car);

        this.pb = findViewById(R.id.progressBar);
        this.SPEED = 0;
        this.lives = 3;
        this.tourMap = 1;
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
                    SPEED = level;
                    speedView.setText("Speed : 1");
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    SPEED = 0;
                    speedView.setText("Speed : 0");
                }
                return true;
            }
        });

        this.tv = findViewById(R.id.cmp_depart);
        TextView tvLevel = findViewById(R.id.textLevel);
        CmpTask task = new CmpTask(tv, tvLevel, level);
        task.execute();

        this.chrono = findViewById(R.id.timer);
        TimerRunnable timer = new TimerRunnable(chrono, this, speedB);
        this.timerThread = new Thread(timer);
        timerThread.start();
    }



    public void newCourse() {
        this.course = new ThreadCourse(this, view.getWidth(), view.getHeight(), getResources(), testMode);
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
        String s = "Finish \n Gagné";
        tv.setText(s);
        this.fin = true;
    }



    public int getSPEED() {
        return this.SPEED;
    }



    public void collision() {
        this.lives--;
        TextView livesView = findViewById(R.id.viewLives);
        String s1 = "Lives : " + lives;
        livesView.setText(s1);
        TextView speedView = findViewById(R.id.viewSpeed);
        String s2 = "Speed : " + SPEED;
        speedView.setText(s2);
        if(lives > 0) {
            relance();
        } else {
            gameOver();
        }
    }



    public void gameOver() {
        tv.setTextColor(Color.RED);
        String s = "GAME OVER";
        tv.setText(s);
        timerThread.interrupt();
        chrono.stop();
        courseThread.interrupt();
        this.fin = true;
    }



    public void relance() {
        courseThread.interrupt();
        tv.setText("Accident ! \n -1 vie");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                tv.setText("");
                courseThread = new Thread(course);
                courseThread.start();
            }
        }, 2000);
    }



    public void setProgressBar(int i) {
        if (testMode) {
            pb.setProgress(i * 10);
        } else {
            pb.setProgress(i);
        }
    }



    public void printObstacles(ArrayList<Obstacle> obstacles) {
        ecran.drawObs(obstacles);
    }



    public int getPosPlayer() {
        return this.posPlayer;
    }



    public void changeBackground() {
        switch (tourMap) {
            case 1 : ecran.setBackgroundResource(R.drawable.map_course1); tourMap++; break;
            case 2 : ecran.setBackgroundResource(R.drawable.map_course2); tourMap++; break;
            case 3 : ecran.setBackgroundResource(R.drawable.map_course3); tourMap = 1; break;
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        float width = view.getWidth();
        float x = event.values[1];
        int posX = (int)img.getX();
        float new_pos = (posX + (x * 20));
        this.posPlayer = (int)new_pos;
        if((posX < (width/4) + 40) && (new_pos <= posX)) {
            img.setX(width/4);
        } else if((posX > (width - (width/4)) - 40) && (new_pos >= posX)) {
            img.setX(width - (width/4));
        } else {
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

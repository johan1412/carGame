package com.upec.androidtemplate20192020;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private int level;
    private int width;
    private boolean firstChange;
    private Button easyButton, mediumButton, difficultButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.level = 0;
        this.easyButton = findViewById(R.id.buttonEasy);
        this.mediumButton = findViewById(R.id.buttonMedium);
        this.difficultButton = findViewById(R.id.buttonDifficult);
        this.firstChange = true;
    }

    public void clickStart(View v) {
        if(level == 0) {
            Button b = findViewById(R.id.startButton);
            b.setError("Il faut choisir un niveau de difficulté");
        } else {
            Intent intent = new Intent(MainActivity.this, CourseActivity.class);
            intent.putExtra("level", level);
            intent.putExtra("test", 0);
            startActivity(intent);
        }
    }

    public void clickEasyButton(View v) {
        this.level = 1;
        changeButton(v);
        originButton(mediumButton);
        originButton(difficultButton);
    }

    public void clickMediumButton(View v) {
        this.level = 2;
        changeButton(v);
        originButton(easyButton);
        originButton(difficultButton);
    }

    public void clickDifficultButton(View v) {
        this.level = 3;
        changeButton(v);
        originButton(easyButton);
        originButton(mediumButton);
    }

    public void clickButtonTest(View v) {
        Intent intent = new Intent(MainActivity.this, CourseActivity.class);
        intent.putExtra("test", 1);
        startActivity(intent);
    }

    public void changeButton(View v) {
        if(firstChange) {
            this.width = v.getWidth();
            firstChange = false;
        }
        v.setBackgroundColor(Color.rgb(253, 156, 34));
        v.setMinimumWidth(width + 100);
    }

    public void originButton(Button b) {
        b.setBackgroundColor(Color.WHITE);
        b.setMinimumWidth(width);
        b.setBackgroundResource(R.drawable.rounded_button);
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

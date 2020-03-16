package com.upec.androidtemplate20192020;

import android.os.AsyncTask;
import android.widget.TextView;

public class CmpTask extends AsyncTask<Void, Integer, Void> {
    private TextView tv;
    private TextView textLevel;
    private int level;

    public CmpTask(TextView tv, TextView textLevel, int level) {
        super();
        this.tv = tv;
        this.textLevel = textLevel;
        this.level = level;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String s = "";
        if(level == 0) {
            s = "Test mode";
            textLevel.setText(s);
        } else if(level == 1) {
            s = "Easy level";
            textLevel.setText(s);
        } else if(level == 2) {
            s = "Medium level";
            textLevel.setText(s);
        } else {
            s = "Difficult level";
            textLevel.setText(s);
        }
    }

    @Override
    protected Void doInBackground(Void... v) {
        for(int i=3; i >= -1; i--) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... i) {
        if(i[0] == 0 || i[0] == -1) {
            String s = "PARTEZ";
            tv.setText(s);
        } else {
            String s = Integer.toString(i[0]);
            tv.setText(s);
        }
    }

    @Override
    protected void onPostExecute(Void v) {
        tv.setText("");
        textLevel.setText("");
    }
}

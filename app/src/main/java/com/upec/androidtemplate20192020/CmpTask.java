package com.upec.androidtemplate20192020;

import android.os.AsyncTask;
import android.widget.TextView;

public class CmpTask extends AsyncTask<Void, Integer, Void> {
    TextView tv;

    public CmpTask(TextView tv) {
        super();
        this.tv = tv;
    }

    @Override
    protected Void doInBackground(Void... v) {
        for(int i=3; i >= -1; i--) {
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
        if(i[0] == 0 || i[0] == -1) {
            tv.setText("PARTEZ");
        } else {
            tv.setText(Integer.toString(i[0]));
        }
    }

    @Override
    protected void onPostExecute(Void v) {
        tv.setText("");
    }
}

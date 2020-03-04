package com.upec.androidtemplate20192020;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CourseManager {
    private static  CourseManager sInstance;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private final ExecutorService mExecutorService;
    private final BlockingQueue<Runnable> mTaskQueue;
    private Handler handler;
    private List<Future> mRunningTaskList;

    static {
        sInstance = new CourseManager();
    }

    private CourseManager() {
        mTaskQueue = new LinkedBlockingQueue<Runnable>();
        mRunningTaskList = new ArrayList<>();
        mExecutorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES*2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mTaskQueue);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {

            }
        };
    }
}

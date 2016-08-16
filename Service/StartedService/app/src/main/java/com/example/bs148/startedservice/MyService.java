package com.example.bs148.startedservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by BS148 on 8/5/2016.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service started",Toast.LENGTH_LONG).show();
        Thread thread=new Thread(new MyThreadClass(startId));
        thread.start();
        return START_STICKY;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"On Create Called",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Stopped",Toast.LENGTH_LONG).show();
    }

    final class MyThreadClass implements Runnable {
        int startId;
        public MyThreadClass(int startId) {
            this.startId=startId;
        }

        @Override
        public void run() {
            int i=0;
            synchronized (this){
                while (i<10){
                    try {
                        wait(1500);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf(startId);
            }
        }
    }
}

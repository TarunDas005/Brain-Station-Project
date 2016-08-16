package com.example.bs148.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by BS148 on 8/5/2016.
 */
public class MyService extends IntentService {

    public MyService() {
        super("My_Worker_Thread");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service Startted",Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            int count=0;
            while (count<10){
                try {
                    wait(1500);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service Stoped",Toast.LENGTH_LONG).show();
    }
}

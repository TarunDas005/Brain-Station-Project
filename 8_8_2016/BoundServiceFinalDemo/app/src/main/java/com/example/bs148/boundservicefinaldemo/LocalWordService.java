package com.example.bs148.boundservicefinaldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by BS148 on 8/8/2016.
 */
public class LocalWordService extends Service {
    private final IBinder mBinder=new MyBinder();
    private ArrayList<String> list=new ArrayList<String>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"--onBind---",Toast.LENGTH_LONG).show();
        Random random = new Random();
        if (random.nextBoolean()) {
            list.add("Linux");
        }
        if (random.nextBoolean()) {
            list.add("Android");
        }
        if (random.nextBoolean()) {
            list.add("iPhone");
        }
        if (random.nextBoolean()) {
            list.add("Windows7");
        }
        if (list.size() >= 20) {
            list.remove(0);
        }
        return mBinder;
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Random random = new Random();
        if (random.nextBoolean()) {
            list.add("Linux");
        }
        if (random.nextBoolean()) {
            list.add("Android");
        }
        if (random.nextBoolean()) {
            list.add("iPhone");
        }
        if (random.nextBoolean()) {
            list.add("Windows7");
        }
        if (list.size() >= 20) {
            list.remove(0);
        }
        return START_NOT_STICKY;
    }*/

    public class MyBinder extends Binder {
        LocalWordService getService(){
            return LocalWordService.this;
        }
    }

    public List<String> getWordList(){
        return list;
    }
}

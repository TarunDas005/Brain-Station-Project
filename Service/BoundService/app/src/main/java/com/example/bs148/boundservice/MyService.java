package com.example.bs148.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by BS148 on 8/5/2016.
 */
public class MyService extends Service {
    private final IBinder mBinder=new LocalService();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"----OnBind-----",Toast.LENGTH_LONG).show();
        return mBinder;
    }

    public class LocalService extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
    public String getFirstMesage(){
        return "Hello Welcome All";
    }
    public String getSecondMesage(){
        return "This is bound service example";
    }

}

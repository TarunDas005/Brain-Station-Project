package com.example.bs148.boundserviceusingmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by BS148 on 8/5/2016.
 */
public class MyService extends Service{
    static final int Job_1=1;
    static final int Job_2=2;
    Messenger messenger=new Messenger(new IncomingHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(),"Service Binding",Toast.LENGTH_LONG).show();
        return messenger.getBinder();
    }

    class IncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Job_1:
                    Toast.makeText(getApplicationContext(),"Hello From Job1",Toast.LENGTH_LONG).show();
                    break;
                case Job_2:
                    Toast.makeText(getApplicationContext(),"Hello From Job2",Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
}

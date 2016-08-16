package com.example.bs148.boundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    MyService myService;
    boolean isBind=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);
        Intent intent=new Intent(this,MyService.class);
        bindService(intent,Mconnection, Context.BIND_AUTO_CREATE);
    }

    public void GetFirstMessage(View view){
        String msg=myService.getFirstMesage();
        textView.setText(msg);
    }
    public void GetSecondMessage(View view){
        String msg=myService.getSecondMesage();
        textView.setText(msg);
    }

    ServiceConnection Mconnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getApplicationContext(),"..onServiceConnected..",Toast.LENGTH_LONG).show();
            MyService.LocalService localService= (MyService.LocalService) service;
            myService=localService.getService();
            isBind=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(),"..onServiceDisconnected..",Toast.LENGTH_LONG).show();
            isBind=false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"----onStop-----",Toast.LENGTH_LONG).show();
        if(isBind){
            unbindService(Mconnection);
            isBind=false;
        }
    }
}

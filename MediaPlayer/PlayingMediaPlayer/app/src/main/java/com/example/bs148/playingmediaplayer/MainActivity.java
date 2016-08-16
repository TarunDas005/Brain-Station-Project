package com.example.bs148.playingmediaplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String strAudioLink="10.mp3";
    Intent serviceIntent;
    Button playStopbutton;
    private boolean boolMusicPlaying=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            serviceIntent=new Intent(this,MyPlayService.class);
            initView();
            setListner();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
    }

    private void setListner() {
        playStopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPlayStopClick();
            }
        });

    }

    private void initView() {
        playStopbutton= (Button) findViewById(R.id.playPauseButton);
        playStopbutton.setBackgroundResource(R.drawable.play);
    }

    private void buttonPlayStopClick() {
        if(!boolMusicPlaying){
            playStopbutton.setBackgroundResource(R.drawable.pause);
            playAudio();
            boolMusicPlaying=true;
        }else{
            if(boolMusicPlaying){
                playStopbutton.setBackgroundResource(R.drawable.play);
                stopMyPlayService();
                boolMusicPlaying=false;
            }
        }
    }

    private void stopMyPlayService() {
        try {
            stopService(serviceIntent);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,e.getClass().getName()+" "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        boolMusicPlaying=false;
    }

    private void playAudio() {
        try {
            startService(serviceIntent);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,e.getClass().getName()+" "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}

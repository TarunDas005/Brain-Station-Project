package com.example.bs148.mediaplayerapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener{
    ArrayList<File> mySongs;
    static MediaPlayer mp;
    SeekBar seekBar;
    Thread updateSeekbar;
    Button btPrevious,btFB,btPlay,btFF,btNext;
    int position;
    Uri u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        btPlay= (Button) findViewById(R.id.btPlay);
        btFB= (Button) findViewById(R.id.btFB);
        btPrevious= (Button) findViewById(R.id.btPrevious);
        btFF= (Button) findViewById(R.id.btFF);
        btNext= (Button) findViewById(R.id.btNext);
        seekBar= (SeekBar) findViewById(R.id.seekBar);

        btPlay.setOnClickListener(this);
        btFB.setOnClickListener(this);
        btPrevious.setOnClickListener(this);
        btFF.setOnClickListener(this);
        btNext.setOnClickListener(this);
        if(mp!=null){ // jodi first time ase tahole mp te null thakbe but second bar asle null thakbe na because static
            mp.stop();
            mp.release();
        }
        updateSeekbar=new Thread(){
            @Override
            public void run() {
                int totalDuration=mp.getDuration();
                int currentPosition=0;
                while (currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition=mp.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        mySongs=(ArrayList) bundle.getParcelableArrayList("songlist");
        position=bundle.getInt("pos",0);
        u=Uri.parse(mySongs.get(position).toString());   //if we read file from external storage then we create uri
        mp=MediaPlayer.create(this,u);
        mp.start();
        seekBar.setMax(mp.getDuration());
        updateSeekbar.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
                position=(position+1)%mySongs.size();
                u=Uri.parse(mySongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                seekBar.setMax(mp.getDuration());
            }
        });
    }

    public void PreviousButtonClicked(View view){
        Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btPlay:
                if(mp.isPlaying())
                {
                    btPlay.setBackgroundResource(R.drawable.play);
                    mp.pause();
                }else{
                    btPlay.setBackgroundResource(R.drawable.pause);
                    mp.start();
                }
                break;
            case R.id.btFF:
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;
            case R.id.btFB:
                mp.seekTo(mp.getCurrentPosition()-5000);
                break;
            case R.id.btNext:
                mp.stop();
                mp.release();
                position=(position+1)%mySongs.size();
                u=Uri.parse(mySongs.get(position).toString());   //if we read file from external storage then we create uri
                mp=MediaPlayer.create(this,u);
                mp.start();
                seekBar.setMax(mp.getDuration());
                break;
            case R.id.btPrevious:
                mp.stop();
                mp.release();
                position=(position-1<0)?mySongs.size()-1:position-1;
                u=Uri.parse(mySongs.get(position).toString());   //if we read file from external storage then we create uri
                mp=MediaPlayer.create(this,u);
                mp.start();
                seekBar.setMax(mp.getDuration());
                break;
        }
    }

}

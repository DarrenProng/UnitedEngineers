package com.engineers.united.unitedengineers;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by darren on 2017-10-15.
 */

public class Activity5 extends Activity {

    Button b_play;
    MediaPlayer mediaPlayer;
    boolean prepared = false;
    boolean started = false;

    String stream = "http://65.19.131.138/mzmedia-cfmzfmaac-ibc2?session-id=1361203433&source=tunein";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

        b_play = (Button) findViewById(R.id.b_play);
        b_play.setEnabled(false);
        b_play.setText(R.string.LOADING);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new Activity5.PlayerTask().execute(stream);

        b_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(started){
                    started = false;
                    mediaPlayer.pause();
                    b_play.setText(R.string.PLAY);
                } else {
                    started = true;
                    mediaPlayer.start();
                    b_play.setText(R.string.PAUSE);
                }
            }
        });
    }


    class PlayerTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String...strings){

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean){
            super.onPostExecute(aBoolean);
            b_play.setEnabled(true);
            b_play.setText(R.string.PLAY);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(started){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(started){
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(prepared){
            mediaPlayer.release();
        }
    }
}
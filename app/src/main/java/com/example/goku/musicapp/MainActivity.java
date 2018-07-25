package com.example.goku.musicapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.helpers.LocatorImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private ImageView mImageViewNext;
    private ImageView mImageViewPre;
    private ImageView mImageViewPause;
    private boolean check=false;
    private MyService mMyService;
    private Intent mIntent;
    private SeekBar mSeekBar;
    private int total=0;
    private static final String ACTION_STOP_SERVICE = "action.STOP_SERVICE";
    public static final int POSITION=10000;
    public static final int DU=1000;
    public static final int NUMBER_ZE=0;
    private TextView mTextViewCurrent;
    private TextView mTextViewTotal;
    private TextView mTextViewSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initNotification();
    }
    @Override
    protected void onStart() {
        mIntent=new Intent(MainActivity.this,MyService.class);
        bindService(mIntent,connection, Context.BIND_AUTO_CREATE);
        super.onStart();
    }
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyBinder myBinder=(MyService.MyBinder) iBinder;
            mMyService=myBinder.getService();
            check=true;
            total=mMyService.getDuration();
            SimpleDateFormat hour=new SimpleDateFormat("mm:ss");
            mTextViewTotal.setText(hour.format(total));
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            check=false;
        }
    };

    private void init() {

        mImageViewNext =findViewById(R.id.imageview_next);
        mImageViewPause =findViewById(R.id.imageview_stop);
        mImageViewPre =findViewById(R.id.imageview_pre);
        mSeekBar=findViewById(R.id.seekbar);
        mTextViewSong=findViewById(R.id.textSong);

        mSeekBar.setOnSeekBarChangeListener(this);
        mImageViewPause.setOnClickListener(this);
        mImageViewPre.setOnClickListener(this);
        mImageViewNext.setOnClickListener(this);
        mTextViewCurrent=findViewById(R.id.text_current);
        mTextViewTotal=findViewById(R.id.text_total);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imageview_next:
                if (mMyService.isPlaying()) {
                    mMyService.SeekTo(mMyService.getCurrentPosition() + POSITION);
                }
                break;
            case R.id.imageview_pre:
                if (mMyService.isPlaying()) {
                    mMyService.SeekTo(mMyService.getCurrentPosition() - POSITION);
                }
                break;
            case R.id.imageview_stop:
                if(mMyService.isPlaying()){
                    mMyService.pause();
                    mImageViewPause.setImageResource(R.drawable.ic_play);
                }else {
                    mMyService.play();
                    mImageViewPause.setImageResource(R.drawable.ic_pause);
                }
                updateProgess();
                break;
        }
    }
    public void updateProgess(){
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat hour=new SimpleDateFormat("mm:ss");
                mTextViewCurrent.setText(hour.format(mMyService.getCurrentPosition()));
                mSeekBar.setProgress(mMyService.getCurrentPosition());
                handler.postDelayed(this,DU);

            }
        },NUMBER_ZE);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
         mSeekBar.setMax(mMyService.getDuration());
         if (b){
             mMyService.SeekTo(mSeekBar.getProgress());
         }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
    public void initNotification(){
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Intent switchIntent = new Intent("com.example.app.ACTION_PLAY");
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 100, switchIntent, 0);

        Notification.Builder noBuilder=new Notification.Builder(this);
        noBuilder.setContentTitle("");
        noBuilder.setContentText("");
        noBuilder.setSmallIcon(R.mipmap.ic_launcher);
        noBuilder.setAutoCancel(true);
        noBuilder.setContentIntent(pIntent);
        noBuilder.addAction(R.drawable.ic_pause,"PAUSE",pendingSwitchIntent);
        noBuilder.addAction((R.drawable.ic_skip_next),"NEXT",null);
        Notification notification = noBuilder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(3452376,notification);

    }
}



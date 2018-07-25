package com.example.goku.musicapp;

import android.app.Service;
import android.content.Intent;

import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class MyService extends Service {
    private MyPlayer mMyPlayer;
    private IBinder mIBinder;

    @Override
    public void onCreate() {
        Log.d("ServiceDemo", "Đã gọi onCreate()");
        mMyPlayer = new MyPlayer(this);
        mIBinder = new MyBinder();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMyPlayer.stop();
        return super.onUnbind(intent);
    }

    public void SeekTo(int position) {
        mMyPlayer.SeekTo(position);
    }

    public void play() {
        mMyPlayer.play();
    }

    public void pause() {
        mMyPlayer.pause();
    }

    public boolean isPlaying() {
        return mMyPlayer.isPlaying();
    }

    public int getCurrentPosition() {
        return mMyPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mMyPlayer.getDuration();
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

}

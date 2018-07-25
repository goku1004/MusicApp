package com.example.goku.musicapp;

import android.content.Context;
import android.media.MediaPlayer;

public class MyPlayer  {
    private MediaPlayer mMediaPlayer;
    private Song song;
    public static final String NAME="choi voi trong con dau";
    public MyPlayer(Context context) {
        song=new Song(NAME,R.raw.choi_voi_trong_con_dau);
        mMediaPlayer=MediaPlayer.create(context,song.getFile());
        mMediaPlayer.setLooping(true);
    }
    public void SeekTo(int pos){
        mMediaPlayer.seekTo(pos);
    }
    public void play(){
        if (mMediaPlayer!=null) {
            mMediaPlayer.start();
        }
    }
    public void stop(){
        if (mMediaPlayer!=null) {
            mMediaPlayer.stop();
        }
    }
    public void pause(){
        if (mMediaPlayer!=null) {
            mMediaPlayer.pause();
        }
    }
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }
    public int getCurrentPosition(){
        if (mMediaPlayer!=null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration(){
        if (mMediaPlayer!=null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }
}

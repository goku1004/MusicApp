package com.example.goku.musicapp;

public class Song {
    private String mName;
    private int File;

    public Song(String mName, int file) {
        this.mName = mName;
        File = file;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}

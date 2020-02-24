package com.gameapps.testingproject;

import android.net.Uri;

public class ModelVideoFolder {

    private String id;
    private String name;
    private int so_file;
    private Uri uri;

    public ModelVideoFolder(String id, String name, int count) {
        this.id = id;
        this.so_file = count;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return so_file;
    }

    public void setCount(int count) {
        this.so_file = count;
    }


}

package com.gameapps.testingproject;

import android.net.Uri;

public class Media {
    private String id_folder;
    private Uri uri;

    public Media(String id_folder, Uri uri) {
        this.id_folder = id_folder;
        this.uri = uri;
    }
}
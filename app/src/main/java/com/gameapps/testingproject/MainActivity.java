package com.gameapps.testingproject;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @NonNull
    public static HashMap<String, ModelVideoFolder> get(@NonNull final Context context) {
        final HashMap<String, ModelVideoFolder> output = new HashMap<>();
        final HashMap<String, Media> mapMedia = new HashMap<>();
//        final Uri contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        final Uri queryUri = MediaStore.Files.getContentUri("external");

        final String[] projection = {MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns._ID,
                MediaStore.Video.Media.BUCKET_ID, MediaStore.Files.FileColumns.MEDIA_TYPE, MediaStore.Files.FileColumns.MIME_TYPE};
        final String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=1" + " or " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=3";
        String selection1 = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


//        final String []selectionArgs = {"MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE"/*, "MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO"*/};

        String selection2 = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        try (final Cursor cursor = context.getContentResolver().query(queryUri,
                projection, selection, null, null)) {
            if ((cursor != null) && (cursor.moveToFirst())) {
                final int columnBucketName = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
                final int columnBucketId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID);
                final int id_ = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);

                do {
                    final String bucketName = cursor.getString(columnBucketName);
                    final String bucketId = cursor.getString(columnBucketId);

                    if (!output.containsKey(bucketId)) {
                        final int so_file = getCount(context, queryUri, bucketId);

                        final ModelVideoFolder item = new ModelVideoFolder(
                                bucketName, bucketId, so_file);

                        output.put(bucketId, item);

                        Log.d("tuanvn", "so_file :" + so_file);
                        Log.d("tuanvn", "so_file :" + so_file);
                    }

                } while (cursor.moveToNext());

                Log.d("tuanvn", "size :" + output.size());

/*
                do {
                    final String id = cursor.getString(id_);
                    final String bucketId   = cursor.getString(columnBucketId);

                    if (output.containsKey(bucketId)){
                        Uri uri = ContentUris.withAppendedId(queryUri, Long.valueOf(id));
                        final Media media2 = new Media(bucketId,uri);
                        mapMedia.put(bucketId, media2);
                    }

                }while (cursor.moveToNext());*/
            }
        }


        try (final Cursor cursor = context.getContentResolver().query(queryUri,
                projection, selection, null, null)) {
            if ((cursor != null) && (cursor.moveToFirst())) {
                final int columnBucketId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID);
                final int id_ = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);


                do {
                    final String id = cursor.getString(id_);
                    final String bucketId = cursor.getString(columnBucketId);

                    if (output.containsKey(bucketId)) {
                        Uri uri = ContentUris.withAppendedId(queryUri, Long.valueOf(id));
                        final Media media2 = new Media(bucketId, uri);
                        mapMedia.put(bucketId, media2);
                    }

                } while (cursor.moveToNext());
            }
        }

        Log.d("tuanvn", mapMedia.size() + "mapsize");

        return output;
    }

    private static int getCount(@NonNull final Context context, @NonNull final Uri contentUri,
                                @NonNull final String bucketId) {
        try (final Cursor cursor = context.getContentResolver().query(contentUri,
                null, MediaStore.Video.Media.BUCKET_ID + "=?", new String[]{bucketId}, null)) {
            return ((cursor == null) || (!cursor.moveToFirst())) ? 0 : cursor.getCount();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get(this);
    }
}

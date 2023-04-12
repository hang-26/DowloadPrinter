package com.example.dowloadprinter.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.dowloadprinter.Fragment.molde.MyMedia;
import com.example.dowloadprinter.Fragment.molde.MyMediaStorage;

import java.util.ArrayList;
import java.util.List;

public class FileFolderStoreHelper {

    public static List<MyMedia> getVideoFolder(Context context, String path) {
        ArrayList<MyMedia> listMedia = new ArrayList<>();
        try {
            String[] projVideo = {
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATE_ADDED,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.SIZE
            };

            String selection = MediaStore.Video.Media.DATA + " like?";
            String[] selectionArgs = {
                    "%" + path + "%"
            };
            Cursor videoCursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projVideo,
                    selection,
                    selectionArgs,
                    MediaStore.Video.Media.DATE_ADDED + " DESC");
            if (videoCursor != null) {
                if (videoCursor.moveToNext()){
                    int videoIdIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                    int videoIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                    int durationIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                    int uriIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                    int dateIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);
                    int sizeIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                    do {
                        listMedia.add(
                                createMedia(
                                        videoCursor.getString(videoIdIndex),
                                        videoCursor.getString(videoIndex),
                                        Long.valueOf(videoCursor.getString(durationIndex)),
                                        videoCursor.getString(uriIndex),
                                        videoCursor.getLong(dateIndex),
                                        videoCursor.getLong(sizeIndex)
                                )
                        );
                    } while (videoCursor.moveToNext());
                }
            }
            videoCursor.close();

        } catch (Exception error) {
            error.getCause();
        }
        return listMedia;
    }

    public static List<MyMedia> getImageFolder(Context context, String path){
        ArrayList<MyMedia> listMedia = new ArrayList<>();
        try {
            String[] projImage ={
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE
            };
            String selection = MediaStore.Images.Media.DATA + " like?";
            String[] selectionArgs = {
                    "%" + path + "%"
            };
            Cursor videoCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projImage,
                    selection,
                    selectionArgs,
                    MediaStore.Images.Media.DATE_ADDED + " DESC");
            if(videoCursor!=null){
                if(videoCursor.moveToNext()){
                    int videoIdIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                    int videoIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                    int uriIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    int dateIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
                    int sizeIndex = videoCursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
                    do{
                        listMedia.add(
                                createMedia(
                                        videoCursor.getString(videoIdIndex),
                                        videoCursor.getString(videoIndex),
                                        (long) -1,
                                        videoCursor.getString(uriIndex),
                                        videoCursor.getLong(dateIndex),
                                        videoCursor.getLong(sizeIndex)
                                )
                        );
                    }while (videoCursor.moveToNext());
                }
            }
            videoCursor.close();
        }catch (Exception error){
            error.getCause();
        }
        return listMedia;
    }



    private static MyMedia createMedia(String idIndex, String name, Long duration, String path, Long date, Long size) {
        MyMediaStorage media = new MyMediaStorage();
        media.idIndex = idIndex;
        media.title = name;
        media.duration = duration;
        media.link = path;
        media.date = date;
        media.size = size;
        return media;
    }
}


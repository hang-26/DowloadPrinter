package com.example.dowloadprinter.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class DownloadUtil {
    public interface DownloadUtilCallBackImpl {
        void onDownLoadSuccess();

        void onDownLoadError();
    }

    public static void startDownload(
            Context mContext,
            String urlMedia,
            String nameMedia,
            String typeTail,
            DownloadUtilCallBackImpl onResultCallback
    ) {
        Uri uri = Uri.parse(urlMedia);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Pinterest  Downloading...");
        request.setDescription(nameMedia);
        request.setDestinationUri(
                Uri.fromFile(
                        new File(getFolderSaveStorage(), nameMedia + "." + typeTail
                        )
                )
        );

        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Long downloadReference = downloadManager.enqueue(request);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (referenceId != -1L) {
                    onResultCallback.onDownLoadSuccess();
                    mContext.unregisterReceiver(this);
                } else {
                    onResultCallback.onDownLoadError();

                }
            }
        };
        mContext.registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    public static File getFolderSaveStorage() {
        return new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                ), "Pinter Download"
        );

    }
}

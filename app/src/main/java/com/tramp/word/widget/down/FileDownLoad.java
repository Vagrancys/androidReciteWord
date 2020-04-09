package com.tramp.word.widget.down;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.tramp.word.port.DownProgressListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/06
 * version:1.0
 */
public class FileDownLoad  implements Runnable {
    private static final String LOG="FileDownLoad";
    private retrofit2.Response<ResponseBody> mResponseBody;
    private DownProgressListener mProgressListener;
    private String mPath;
    private Handler mHandler;
    private int mProgress;
    private int mNow;
    private long mTotal;
    private int mBook_id;

    public FileDownLoad(retrofit2.Response<ResponseBody> responseBody,int book_id,
                        String path,
                        DownProgressListener progressListener,
                        Handler handler,
                        int progress,int now,long total) {
        mResponseBody = responseBody;
        mBook_id=book_id;
        mProgressListener = progressListener;
        mPath = path;
        mHandler=handler;
        mProgress=progress;
        mNow=now;
        mTotal=total;
    }

    @Override
    public void run() {
        if (mProgressListener != null) {
            mHandler.post(()->{
                mProgressListener.onStartProgress();
            });
        }
        try {
            Log.e(LOG,File.separator + mPath);
            File file = new File(Environment.getExternalStorageDirectory(), File.separator + mPath);
            if (file.exists()) {
                file.delete();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = mResponseBody.body().contentLength();
                int Downloaded=0;
                inputStream = mResponseBody.body().byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    Downloaded += read;
                    if (mProgressListener != null) {
                        mProgress=(int) (100 *Downloaded / fileSize);
                        mNow=Downloaded;
                        mTotal=fileSize;
                        mHandler.post(()->{
                            mProgressListener.onProgress( mProgress,mNow,mTotal);
                        });
                    }
                }
                if (mProgressListener != null) {
                    mHandler.post(()->{
                        mProgressListener.onEndProgress(mBook_id,file.getPath(),fileSize);
                    });
                }
                outputStream.flush();
            } catch (IOException e) {
                if (mProgressListener != null) {
                    mHandler.post(()->{
                        mProgressListener.onErrorProgress(e.getMessage());
                    });
                }
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            if (mProgressListener != null) {
                mHandler.post(()->{
                    mProgressListener.onErrorProgress(e.getMessage());
                });
            }
        }
    }

}

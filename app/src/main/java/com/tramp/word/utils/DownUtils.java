package com.tramp.word.utils;

import android.os.Handler;
import android.util.Log;

import com.tramp.word.port.DownProgressListener;

import java.io.File;
import java.io.FileOutputStream;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/30
 * version:1.0
 */
public class DownUtils {
    public static void RarDp (int book_id,String startfile, String unrarPath,
                              DownProgressListener progressListener, Handler mHandler){
        Log.e("RarDp","starPath="+startfile+"endPath="+unrarPath);
        File srcFile=new File(startfile);
        if(null==unrarPath||"".equals(unrarPath)){
            unrarPath=srcFile.getParentFile().getPath();
        }
        if(progressListener !=null){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressListener.onStartSolve();
                }
            });
        }
        FileOutputStream fileOut;
        Archive rarfile;
        File file;
        try {
            rarfile=new Archive(srcFile);
            FileHeader entry=rarfile.nextFileHeader();
            final int total=rarfile.getFileHeaders().size();
            for (int i=0;i<total;i++){
                String entrypath;
                if(entry.isUnicode()){
                    entrypath=entry.getFileNameW().trim();
                }else{
                    entrypath=entry.getFileNameString().trim();
                }
                entrypath=entrypath.replaceAll("\\\\","/");
                file=new File(unrarPath+"/"+entrypath);
                if(entry.isDirectory()){
                    file.mkdirs();
                }else{
                    File parent=file.getParentFile();
                    if(parent !=null&&!parent.exists()){
                        parent.mkdirs();
                    }
                    fileOut=new FileOutputStream(file);
                    rarfile.extractFile(entry,fileOut);
                    fileOut.close();
                }
                if(progressListener !=null){
                    final int finalI=i;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressListener.onProgressSolve((int) (100 * finalI / total));
                        }
                    });
                }
            }
            rarfile.close();
            if(progressListener !=null){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressListener.onEndSolve(book_id);
                    }
                });
            }
        }catch (Exception e){
            if(progressListener !=null){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressListener.onErrorSolve(e.getMessage());
                    }
                });
            }
            e.printStackTrace();
        }
    }
}

package com.tramp.word.port;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/29
 * version:1.0
 */
public interface DownProgressListener {
    void onStartProgress();
    void onEndProgress(int book_id,String path,long total);
    void onErrorProgress(String msg);
    void onProgress(int p,long now,long total);
    void onStartSolve();
    void onProgressSolve(int progress);
    void onEndSolve(int book_id);
    void onErrorSolve(String msg);
    void onStartRead(int total);
    void onProgressRead(int now);
    void onEndRead(int book_id);
}

package com.tramp.word.thread;

/**
 * Created by Administrator on 2019/2/9.
 */

public class MainMusicProgressThread extends Thread {
    private final Object lock=new Object();
    private boolean isStop=false;

    public void pauseThread(){
        isStop=true;
    }

    public void resumeThread(){
        isStop=false;
        synchronized (lock){
            lock.notifyAll();
        }
    }
}
















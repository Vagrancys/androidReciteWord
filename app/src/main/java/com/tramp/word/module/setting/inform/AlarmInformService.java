package com.tramp.word.module.setting.inform;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.tramp.word.R;
import com.tramp.word.module.common.MainActivity;

/**
 * Created by Administrator on 2019/3/2.
 */

public class AlarmInformService extends Service{
    private static final int NOTIFICATION_ID=1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent2=new Intent(AlarmInformService.this, MainActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getApplication(), 0,intent2,0);
                Notification notification=new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(R.drawable.group_icon_default)
                        .setTicker("您的时间即将耗尽,请尽快充值!")
                        .setContentTitle("时间通知")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("此处注明的是有关时间的充值示项内容!"))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setNumber(1).build();
                manager.notify(NOTIFICATION_ID,notification);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);

    }
}



















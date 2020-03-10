package com.tramp.word.module.setting.inform;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.tramp.word.R;
import com.tramp.word.module.common.MainActivity;

/**
 * Created by Administrator on 2019/3/2.
 */

public class AlarmInformReceiver extends BroadcastReceiver{
    private static final int NOTIFICATION_ID=1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("NOTIFICATION")){
            NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent2=new Intent(context, MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context, 0,intent2,0);
            Notification notification=new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.group_icon_default)
                    .setTicker("您的寿命即将耗尽,请尽快充值!")
                    .setContentTitle("寿命通知")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("此处注明的是有关寿命的充值示项内容!"))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setNumber(1).build();
            manager.notify(NOTIFICATION_ID,notification);
        }
    }
}






















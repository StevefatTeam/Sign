package com.gisroad.sign.signappwidget.signservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.gisroad.sign.bean.Users;
import com.gisroad.sign.interfaces.ResultRequest;
import com.gisroad.sign.mvp.model.MainModel;
import com.gisroad.sign.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Created by stevefat on 2017/2/9.
 */

public class SignService extends Service implements ResultRequest<Object> {

    private final String ACTION_UPDATE= "com.stevefat.UPDATE";

    private static final int ALARM_DURATION  = 5 * 60 * 1000; // service 自启间隔
    private static final int UPDATE_DURATION = 10 * 1000;     // Widget 更新间隔
    private static final int UPDATE_MESSAGE  = 1000;

    private UpdateHandler updateHandler; // 更新 Widget 的 Handler



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 开始数据
        Message message =Message.obtain();
        message.what = UPDATE_MESSAGE;
        updateHandler = new UpdateHandler();
        updateHandler.sendMessage(message);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 每个 ALARM_DURATION 自启一次
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getBaseContext(), SignService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + ALARM_DURATION, pendingIntent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSuccess(Object o) {
//        Logger.e("这个是数据返回了。。。"+o.toString());
        List<Users> usersList = (List<Users>) o;
        Collections.reverse(usersList);

        Intent updateIntent = new Intent(ACTION_UPDATE);
        updateIntent.putExtra("userlist", JSON.toJSONString(usersList));
        sendBroadcast(updateIntent);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSuccess(Object o1, Object o2) {

    }


    protected final class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MESSAGE:
                    getData();
                    break;
                default:
                    break;
            }
        }
    }

    private void getData(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String userUrl = (String) SPUtils.get(getApplicationContext(), "userUrl", "");
//                Logger.e("这是用户的url---"+userUrl);

                if (!userUrl.isEmpty()) {
                    new MainModel(SignService.this).getUserItem(userUrl);
                }
            }
        });

        // 发送下次更新的消息
        Message message = updateHandler.obtainMessage();
        message.what = UPDATE_MESSAGE;
        updateHandler.sendMessageDelayed(message, UPDATE_DURATION);
    }

}

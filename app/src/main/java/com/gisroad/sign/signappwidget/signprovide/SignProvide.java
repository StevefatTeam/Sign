package com.gisroad.sign.signappwidget.signprovide;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.alibaba.fastjson.JSON;
import com.gisroad.sign.R;
import com.gisroad.sign.bean.Users;
import com.gisroad.sign.signappwidget.signservice.ListViewService;
import com.gisroad.sign.signappwidget.signservice.SignService;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by stevefat on 2017/2/9.
 */

public class SignProvide extends AppWidgetProvider {

    private final String ACTION_UPDATE= "com.stevefat.UPDATE";
    private static Set idsSet = new HashSet();
    /**
     * 第一个widget 被创建时
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent = new Intent(context, SignService.class);
        context.startService(intent);
    }


    /**
     * 当widget更新时
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            idsSet.add(Integer.valueOf(appWidgetId));
        }
    }

    /**
     * 第一次创建appWidget 的时候
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     * @param newOptions
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 最后一个appweiget被删除时调用
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        Intent intent = new Intent(context, SignService.class);
        context.stopService(intent);
        super.onDisabled(context);
    }

    /**
     * 当appwidget 被删除时
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // 当 widget 被删除时，对应的删除set中保存的widget的id
        for (int appWidgetId : appWidgetIds) {
            idsSet.remove(Integer.valueOf(appWidgetId));
        }
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 数据接受
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action= intent.getAction();
        if(ACTION_UPDATE.equals(action)){
            String userlist = intent.getStringExtra("userlist");
            List<Users>  usersList = JSON.parseArray(userlist,Users.class);
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sign_appwidget_layout);
//            remoteViews.setTextViewText(R.id.app_name,usersList.get(0).getName());
//
//            Intent intent1 = new Intent(context, ListViewService.class);
//            intent.putExtra("userlist", (Serializable) usersList);
//            remoteViews.setRemoteAdapter(R.id.app_recylist,intent1);
//            AppWidgetManager.getInstance(context).updateAppWidget(0,remoteViews);
            updateView(context,AppWidgetManager.getInstance(context),idsSet,usersList);
        }
    }

    private void updateView(Context context, AppWidgetManager appWidgetManager, Set set,List<Users>  usersList) {

        // widget 的id
        int appID;
        // 迭代器，用于遍历所有保存的widget的id
        Iterator it = set.iterator();

        while (it.hasNext()) {
            appID = ((Integer) it.next()).intValue();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sign_appwidget_layout);
            remoteViews.setTextViewText(R.id.app_name,usersList.get(0).getName());

            Intent intent = new Intent(context, ListViewService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appID);
            intent.putExtra("userlist", JSON.toJSONString(usersList));
            remoteViews.setRemoteAdapter(R.id.app_recylist,intent);

            appWidgetManager.updateAppWidget(appID, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appID, R.id.app_recylist);
        }
    }

}

package com.gisroad.sign.signappwidget.signservice;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.alibaba.fastjson.JSON;
import com.gisroad.sign.R;
import com.gisroad.sign.bean.Users;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by stevefat on 2017/2/28.
 */

public class ListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this, intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private int mAppWidgetId;
        List<Users> usersList;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            String userlist = intent.getStringExtra("userlist");
            List<Users> usersList = JSON.parseArray(userlist, Users.class);
            this.usersList = usersList;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            usersList.clear();
        }

        @Override
        public int getCount() {
            return usersList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recy_item);
            Users users = usersList.get(position);
            Logger.e(users.getName()+"---"+ users.getDate_time()+"----"+users.getMorning_to());
            //时间
            rv.setTextViewText(R.id.data_time, users.getDate_time());
            //上午到
            if (TextUtils.isEmpty(users.getMorning_to())) {
                rv.setViewVisibility(R.id.morning_to, View.INVISIBLE);
            } else {
                rv.setViewVisibility(R.id.morning_to, View.VISIBLE);
                rv.setTextViewText(R.id.morning_to, "上午到：\t" + users.getMorning_to());
            }
            //上午退
            if (TextUtils.isEmpty(users.getMorning_retreat())) {
                rv.setViewVisibility(R.id.morning_retreat, View.INVISIBLE);
            } else {
                rv.setViewVisibility(R.id.morning_retreat, View.VISIBLE);
                rv.setTextViewText(R.id.morning_retreat, "上午退:\t" + users.getMorning_retreat());
            }
            //下午到
            if (TextUtils.isEmpty(users.getAfternoon_to())) {
                rv.setViewVisibility(R.id.afternoon_to, View.INVISIBLE);
            } else {
                rv.setViewVisibility(R.id.afternoon_to, View.VISIBLE);
                rv.setTextViewText(R.id.afternoon_to, "下午到：\t" + users.getAfternoon_to());
            }
            //下午退
            if (TextUtils.isEmpty(users.getAfternoon_retreat())) {
                rv.setViewVisibility(R.id.afternoon_retreat, View.INVISIBLE);
            } else {
                rv.setViewVisibility(R.id.afternoon_retreat, View.VISIBLE);
                rv.setTextViewText(R.id.afternoon_retreat, "下午退：\t" + users.getAfternoon_retreat());
            }
            //晚上退
            if (TextUtils.isEmpty(users.getNight_retreat())) {
                rv.setViewVisibility(R.id.night_retreat, View.INVISIBLE);
            } else {
                rv.setViewVisibility(R.id.night_retreat, View.VISIBLE);
                rv.setTextViewText(R.id.night_retreat, "晚上退：\t" + users.getNight_retreat());
            }
            //出差
            if (TextUtils.isEmpty(users.getLeave())) {
                rv.setViewVisibility(R.id.leave, View.INVISIBLE);
            } else {
                rv.setViewVisibility(R.id.leave, View.VISIBLE);
                rv.setTextViewText(R.id.leave, "出差/请假：" + users.getLeave());
            }


            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}

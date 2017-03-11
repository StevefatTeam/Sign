package com.gisroad.sign.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gisroad.sign.R;
import com.gisroad.sign.bean.DepartmentManager;
import com.umeng.analytics.MobclickAgent;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class WelcomeActivity extends BaseActivity {


    @Override
    protected void initView() {
        askMultiplePermission();

    }

    @Override
    protected int getLayoutView() {
        return R.layout.welcome_activity;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void getData() {
        super.getData();
        //提示欢迎使用这个查询系统
        //如果数据库有数据，就直接显示 ---------MainActivity
        //没有数据走更新数据   ----LoginActivity

        int countsize = DepartmentManager.getInstance().select().size();

        if (countsize > 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        //关闭界面
        finish();
    }
}

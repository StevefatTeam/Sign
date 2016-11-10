package com.gisroad.sign.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gisroad.sign.bean.DepartmentManager;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提示欢迎使用这个查询系统
        //如果数据库有数据，就直接显示 ---------MainActivity
        //没有数据走更新数据   ----LoginActivity

        int countsize = DepartmentManager.getInstance().select().size();

        if(countsize>0){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        //关闭界面
        finish();
    }

}

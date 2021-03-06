package com.gisroad.sign.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gisroad.sign.R;
import com.gisroad.sign.activitys.adapters.ExpanListAdapter;
import com.gisroad.sign.activitys.adapters.RecycleAdapter;
import com.gisroad.sign.bean.DataType;
import com.gisroad.sign.bean.DepartBean;
import com.gisroad.sign.bean.DepartUser;
import com.gisroad.sign.bean.MyData;
import com.gisroad.sign.bean.Users;
import com.gisroad.sign.utils.DialogUtils;
import com.orhanobut.logger.Logger;
import com.gisroad.sign.mvp.presenter.MainPresenter;
import com.gisroad.sign.mvp.view.MainView;
import com.gisroad.sign.utils.SPUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.expand_list)
    ExpandableListView expandableListView;
    @BindView(R.id.recylist)
    RecyclerView recyclerView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.no_datas)
    TextView noDatas;
    @BindView(R.id.swipeRefre)
    SwipeRefreshLayout swipeRefre;

    DialogUtils dialogUtils;

    private MainPresenter presenter;
    ExpanListAdapter adapter;
    RecycleAdapter recycleAdapter;
    private DepartUser departUser;


    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        String userUrl = (String) SPUtils.get(this, "userUrl", "");
        if (!userUrl.isEmpty()) {
            presenter.getUserItem(userUrl);
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyData myData) {
        String userUrl = (String) SPUtils.get(this, "userUrl", "");
        switch (myData.getType()) {
            case DataType.LOGIN:
                if (!userUrl.isEmpty()) {
                    presenter.getUserItem(userUrl);
                }
                break;
            case DataType.LOGOUT:
                name.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                noDatas.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    protected void initView() {
        dialogUtils = new DialogUtils(MainActivity.this, "加载数据中。。。");
//        initView();
        presenter = new MainPresenter(this);
        presenter.initListAll();

        //点击事件的注册
        setListen();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                presenter.initListAll();
//                drawer.openDrawer(GravityCompat.START);
                DialogUtils dialogUtils = new DialogUtils(MainActivity.this);
                dialogUtils.loginDialog();
                dialogUtils.show();
            }
        });
        swipeRefre.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefre.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefre.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (departUser != null) {
                    presenter.getUserItem(departUser.getUrl());
                } else {
                    swipeRefre.setRefreshing(false);
                }
            }
        });

    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void showProgress() {
        if (!swipeRefre.isRefreshing()) {
            dialogUtils.show();

        }
    }

    @Override
    public void hideProgress() {
        dialogUtils.dismiss();
    }

    @Override
    public void onSuccess(Object object) {
        List<Users> usersList = (List<Users>) object;
        Collections.reverse(usersList);


        if (swipeRefre != null) {
            swipeRefre.setRefreshing(false);

        }
        if (usersList.size() > 0) {
            name.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            noDatas.setVisibility(View.GONE);
            name.setText(usersList.get(0).getName());
            recycleAdapter = new RecycleAdapter(this, usersList);
            recyclerView.setAdapter(recycleAdapter);
            recyclerView.scrollTo(0, 0);  //默认滚动到顶部
        } else {
            recyclerView.setVisibility(View.GONE);
            noDatas.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void setAdapter(Object o1, Object o2) {
        adapter = new ExpanListAdapter(MainActivity.this, (List<DepartBean>) o1, (List<List<DepartUser>>) o2);
        expandableListView.setAdapter(adapter);
        //显示无打卡记录，提示用户
        recyclerView.setVisibility(View.GONE);
        noDatas.setVisibility(View.VISIBLE);

    }

    public void setListen() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                onBackPressed();
                departUser = (DepartUser) adapter.getChild(groupPosition, childPosition);
//                Logger.e(departUser.toString());
                presenter.getUserItem(departUser.getUrl());
                name.setText(departUser.getName());

                //保存选中的信息到配置文件
                DepartBean departBean = (DepartBean) adapter.getGroup(groupPosition);
                SPUtils.put(MainActivity.this, "departName", departBean.getD_name());
                SPUtils.put(MainActivity.this, "userName", departUser.getName());
                SPUtils.put(MainActivity.this, "userUrl", departUser.getUrl());
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.refresh:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
        }
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}

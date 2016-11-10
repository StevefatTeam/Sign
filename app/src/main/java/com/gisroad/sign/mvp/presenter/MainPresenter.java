package com.gisroad.sign.mvp.presenter;

import com.gisroad.sign.interfaces.ResultRequest;
import com.gisroad.sign.mvp.model.MainModel;
import com.gisroad.sign.mvp.view.MainView;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Author: ngh
 * date: 2016/9/26
 */

public class MainPresenter implements ResultRequest<Object> {
    private MainModel mainModel;
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainModel = new MainModel(this);
    }

    public void initListAll() {
        mainView.showProgress();
        mainModel.getAll();
    }

    public void getUserItem(String userid) {
        mainView.showProgress();
        mainModel.getUserItem(userid);
    }


    @Override
    public void onSuccess(Object str) {
        mainView.hideProgress();
        //解析数据
        mainView.onSuccess(str);
    }

    @Override
    public void onError(Throwable e) {
        mainView.onError(e.getMessage());
    }

    @Override
    public void onSuccess(Object o1, Object o2) {
        mainView.hideProgress();
        mainView.setAdapter(o1, o2);

    }
}

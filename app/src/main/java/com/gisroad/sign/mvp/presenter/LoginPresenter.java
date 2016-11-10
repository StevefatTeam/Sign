package com.gisroad.sign.mvp.presenter;

import com.gisroad.sign.interfaces.ResultRequest;
import com.gisroad.sign.mvp.model.LoginModel;
import com.gisroad.sign.mvp.view.LoginView;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class LoginPresenter implements ResultRequest<String> {
    LoginModel loginModel;
    LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginModel =new LoginModel(this);
        this.loginView = loginView;
    }

    public void init(String html){
        //解析数据
        loginModel.saxHtml(html);

    }


    @Override
    public void onSuccess(String s) {
        loginView.setData();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSuccess(Object o1, Object o2) {

    }
}

package com.gisroad.sign.mvp.view;

/**
 * Author: ngh
 * date: 2016/9/26
 */

public interface MainView {
    void showProgress();

    void hideProgress();

    void onSuccess(Object object);

    void onError(String msg);

    void setAdapter(Object o1, Object o2);
}

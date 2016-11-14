package com.gisroad.sign.mvp.view;

/**
 * Author: ngh
 * date: 2016/9/26
 */

public interface MainView {
    /**
     * 显示dialog
     */
    void showProgress();

    /**
     * 隐藏dialog
     */
    void hideProgress();

    /**
     * 加载数据陈宫
     *
     * @param object
     */
    void onSuccess(Object object);

    /**
     * 加载数据失败
     *
     * @param msg
     */
    void onError(String msg);

    /**
     * 设置左侧的列表数据
     *
     * @param o1 父级数据集
     * @param o2 子级数据集
     */
    void setAdapter(Object o1, Object o2);
}

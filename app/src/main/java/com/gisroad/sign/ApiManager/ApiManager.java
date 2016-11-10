package com.gisroad.sign.ApiManager;

import com.gisroad.sign.constant.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Author: ngh
 * date: 2016/9/26
 */

public class ApiManager {

    //服务提供者
    private static ApiManager instance;
    //网络接口服务提供
    private ApiManagerServices apiManagerServices;

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }


    public ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        this.apiManagerServices = retrofit.create(ApiManagerServices.class);
    }

    /**
     * 获取提供服务的接口
     * @return
     */
    public ApiManagerServices getApiManagerServices() {
        return apiManagerServices;
    }
}

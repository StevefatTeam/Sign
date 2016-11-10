package com.gisroad.sign.ApiManager;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author: ngh
 * date: 2016/9/26
 * 网络服务提供者
 */

public interface ApiManagerServices {

    /**
     * 获取每个人的考勤数据接口
     *
     * @param userid 人员的id
     * @return
     */
    @GET("kqcx/KQlist.aspx")
    //KQlist.aspx?Userid=0060073
    Observable<ResponseBody> getUserItem(@Query("Userid") String userid);
}

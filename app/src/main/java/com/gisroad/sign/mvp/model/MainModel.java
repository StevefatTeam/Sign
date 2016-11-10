package com.gisroad.sign.mvp.model;

import com.gisroad.sign.ApiManager.ApiManager;
import com.gisroad.sign.ApiManager.ApiManagerServices;
import com.gisroad.sign.base.BaseSubscriber;
import com.gisroad.sign.bean.DepartBean;
import com.gisroad.sign.bean.DepartUser;
import com.gisroad.sign.bean.DepartUserManager;
import com.gisroad.sign.bean.DepartmentManager;
import com.gisroad.sign.bean.Users;
import com.gisroad.sign.interfaces.ResultRequest;
import com.gisroad.sign.utils.RxSubUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Author: ngh
 * date: 2016/9/26
 */

public class MainModel {

    List<DepartBean> departBeanList;
    List<List<DepartUser>> departUserList;
    List<Users> usersList;

    private ResultRequest resultRequest;

    public MainModel(ResultRequest resultRequest) {
        this.resultRequest = resultRequest;
    }

    public void getAll() {
        departUserList = new ArrayList<>();
        departBeanList = DepartmentManager.getInstance().select();
        for (int i = 0; i < departBeanList.size(); i++) {
            List<DepartUser> departUsers = DepartUserManager.getInstance().select(departBeanList.get(i));
            departUserList.add(departUsers);

        }

        resultRequest.onSuccess(departBeanList, departUserList);
    }

    /**
     * 获取单个用户的数据
     *
     * @param userid
     */
    public void getUserItem(String userid) {
        //获取数据
        Observable<ResponseBody> obs = ApiManager.getInstance().getApiManagerServices().getUserItem(userid);
        RxSubUtils.toSuber(obs, new MainSubs());
    }

    private class MainSubs extends BaseSubscriber {
        @Override
        public void onCompleted() {
            super.onCompleted();

            resultRequest.onSuccess(usersList);
        }

        @Override
        public void onError(Throwable e) {
            resultRequest.onError(e);

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String html = responseBody.string();
                usersList = new ArrayList<>();
                //开始解析这些数据
                Document doc = Jsoup.parse(html);
                Elements elements = doc.getElementsByTag("tr");
                for (int i = 0; i < elements.size(); i++) {
                    Elements tds = elements.get(i).getElementsByTag("td");
                    //过滤掉头部的th 标签
                    if (tds.size() > 0) {
                        Users user = new Users();
                        user.setName(tds.get(0).text());
                        user.setDate_time(tds.get(1).text());
                        user.setMorning_to(tds.get(2).text());
                        user.setMorning_retreat(tds.get(3).text());
                        user.setAfternoon_to(tds.get(4).text());
                        user.setAfternoon_retreat(tds.get(5).text());
                        user.setNight_retreat(tds.get(6).text());
                        user.setLeave(tds.get(7).text());
                        usersList.add(user);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}


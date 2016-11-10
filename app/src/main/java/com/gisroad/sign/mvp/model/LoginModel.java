package com.gisroad.sign.mvp.model;

import com.orhanobut.logger.Logger;
import com.gisroad.sign.bean.DepartBean;
import com.gisroad.sign.bean.DepartUser;
import com.gisroad.sign.bean.DepartUserManager;
import com.gisroad.sign.bean.DepartmentManager;
import com.gisroad.sign.interfaces.ResultRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class LoginModel {
    private ResultRequest resultRequest;

    public LoginModel(ResultRequest resultRequest) {
        this.resultRequest = resultRequest;
    }


    public void saxHtml(String html) {
        //解析数据
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("a.zzTree_0");
        Long d_id = null;
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String href = element.attr("href");
            if (href.indexOf("javascript:") != -1) {
                String name = element.text();
                DepartBean department = new DepartBean();
                department.setD_name(name);
                DepartmentManager.getInstance().inster(department);
                d_id = department.getD_id();

            } else {
                DepartUser departUser = new DepartUser();
                String name = element.text();
                departUser.setName(name);

                departUser.setUrl(href.substring(href.indexOf("=")+1));
                departUser.setD_id(d_id);
                DepartUserManager.getInstance().inster(departUser);
            }
        }

        resultRequest.onSuccess("");
    }
}

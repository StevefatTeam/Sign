package com.gisroad.sign.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gisroad.sign.R;
import com.gisroad.sign.activitys.adapters.DepartAdapter;
import com.gisroad.sign.activitys.adapters.UserAdapter;
import com.gisroad.sign.bean.DataType;
import com.gisroad.sign.bean.DepartBean;
import com.gisroad.sign.bean.DepartUser;
import com.gisroad.sign.bean.DepartUserManager;
import com.gisroad.sign.bean.DepartmentManager;
import com.gisroad.sign.bean.MyData;
import com.gisroad.sign.interfaces.AdapterOnClick;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class DialogUtils {
    Dialog dialog;
    Context context;

    public DialogUtils(Context context, String msg) {
        this.context = context;
        initDialog(msg);
    }

    /**
     * @param context
     */
    public DialogUtils(Context context) {
        this.context = context;
    }

    public void initDialog(String msg) {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ProgressWheel progressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
        TextView tv = (TextView) view.findViewById(R.id.txt_msg);
        tv.setText(msg);
    }

    private List<DepartBean> beanList;//部门列表
    private List<DepartUser> userList;//成员列表
    private UserAdapter userAdapter;
    private String userUrl;

    public void loginDialog() {

        dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        final AutoCompleteTextView tvDepart = (AutoCompleteTextView) view.findViewById(R.id.tv_department);
        final AutoCompleteTextView tvName = (AutoCompleteTextView) view.findViewById(R.id.tv_name);
        Button cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
        Button okBtn = (Button) view.findViewById(R.id.ok_btn);
        String dName = (String) SPUtils.get(context,"departName","");
        final String uName = (String) SPUtils.get(context,"userName","");
        tvDepart.setText(dName);
        tvName.setText(uName);
        beanList = DepartmentManager.getInstance().select();
        userList = DepartUserManager.getInstance().selectByName(uName);
        DepartAdapter departAdapter = new DepartAdapter(beanList, context);
        tvDepart.setAdapter(departAdapter);
        tvDepart.setThreshold(0);
        departAdapter.setAdapterOnClick(new AdapterOnClick<DepartBean>() {
            @Override
            public void onClick(View view, DepartBean departBean) {

                tvDepart.setText(departBean.getD_name());
                userList = DepartUserManager.getInstance().select(departBean);
                userAdapter.setmList(userList);
                userAdapter.notifyDataSetChanged();
                tvDepart.dismissDropDown();
            }
        });

        userAdapter = new UserAdapter(context);
        userAdapter.setmList(userList);
        tvName.setAdapter(userAdapter);
        tvName.setThreshold(0);
        userAdapter.setAdapterOnClick(new AdapterOnClick<DepartUser>()  {
            @Override
            public void onClick(View view, DepartUser user) {
                userUrl = user.getUrl();
                tvName.setText(user.getName());
                tvName.dismissDropDown();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(context,"departName","");
                SPUtils.put(context,"userName","");
                SPUtils.put(context,"userUrl","");

                MyData myData = new MyData();
                myData.type = DataType.LOGOUT;
                EventBus.getDefault().post(myData);
                dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(context,"departName",tvDepart.getText().toString().trim());
                SPUtils.put(context,"userName",tvName.getText().toString().trim());
                SPUtils.put(context,"userUrl",userUrl);
                MyData myData = new MyData();
                myData.type = DataType.LOGIN;
                EventBus.getDefault().post(myData);
                dismiss();
            }
        });


    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}

package com.gisroad.sign.activitys.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gisroad.sign.R;
import com.gisroad.sign.bean.DepartBean;
import com.gisroad.sign.bean.DepartUser;

import java.util.List;

/**
 * Author: ngh
 * date: 2016/9/28
 */

public class ExpanListAdapter extends BaseExpandableListAdapter {

    Context mContext;
    List<DepartBean> departBeanList;
    List<List<DepartUser>> departUserList;

    LayoutInflater inflater;

    public ExpanListAdapter(Context mContext, List<DepartBean> departBeanList, List<List<DepartUser>> departUserList) {
        this.mContext = mContext;
        this.departBeanList = departBeanList;
        this.departUserList = departUserList;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public int getGroupCount() {
        return departBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return departUserList.get(groupPosition).size() > 0 ? departUserList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return departBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return departUserList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.expan_item, parent, false);
        convertView.setPadding(40, 10, 10, 10);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.exp_img);
        TextView catalog_name = (TextView) convertView.findViewById(R.id.depart_name);


        if (departUserList.get(groupPosition).size() > 0) {
            imageView.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imageView.setBackgroundResource(R.mipmap.exp_up);
            } else {
                imageView.setBackgroundResource(R.mipmap.exp_down);
            }

        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

        catalog_name.setText(departBeanList.get(groupPosition).getD_name());
        catalog_name.setTextSize(20);
        catalog_name.setPadding(0, 10, 10, 10);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.expan_item, parent, false);
        convertView.setPadding(70, 10, 10, 10);
        TextView catalog_name = (TextView) convertView.findViewById(R.id.depart_name);

        catalog_name.setText(departUserList.get(groupPosition).get(childPosition).getName());
        catalog_name.setTextSize(18);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

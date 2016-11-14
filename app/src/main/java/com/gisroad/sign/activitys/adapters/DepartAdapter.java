package com.gisroad.sign.activitys.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gisroad.sign.R;
import com.gisroad.sign.bean.DepartBean;
import com.gisroad.sign.interfaces.AdapterOnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglin on 2016/11/8.
 */

public class DepartAdapter extends BaseAdapter implements Filterable {

    private ArrayFilter mFilter;
    private List<DepartBean> mList;
    private Context context;
    private ArrayList<DepartBean> mUnfilteredData;
    private AdapterOnClick adapterOnClick;

    public void setAdapterOnClick(AdapterOnClick adapterOnClick) {
        this.adapterOnClick = adapterOnClick;
    }

    public DepartAdapter(List<DepartBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {

        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_textview, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_item_name);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final DepartBean pc = mList.get(position);

        holder.tv_name.setText(pc.getD_name());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnClick.onClick(v, pc);
            }
        });

        return view;
    }

    static class ViewHolder {
        public TextView tv_name;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<DepartBean>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<DepartBean> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<DepartBean> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<DepartBean> newValues = new ArrayList<DepartBean>(count);

                for (int i = 0; i < count; i++) {
                    DepartBean pc = unfilteredValues.get(i);
                    if (pc != null) {

                        if (pc.getD_name() != null && pc.getD_name().startsWith(prefixString)) {

                            newValues.add(pc);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            mList = (List<DepartBean>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}

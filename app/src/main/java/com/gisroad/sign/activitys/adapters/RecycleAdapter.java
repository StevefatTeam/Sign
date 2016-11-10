package com.gisroad.sign.activitys.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gisroad.sign.R;
import com.gisroad.sign.bean.Users;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: ngh
 * date: 2016/9/29
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    Context mContext;
    private List<Users> usersList;

    public RecycleAdapter(Context mContext, List<Users> usersList) {
        this.mContext = mContext;
        this.usersList = usersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recy_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Users users = usersList.get(position);
        //时间
        holder.dataTime.setText(users.getDate_time());
        //上午到
        if (TextUtils.isEmpty(users.getMorning_to())) {
            holder.morningTo.setVisibility(View.INVISIBLE);
        } else {
            holder.morningTo.setVisibility(View.VISIBLE);
            holder.morningTo.setText("上午到：\t" + users.getMorning_to());
        }
        //上午退
        if (TextUtils.isEmpty(users.getMorning_retreat())) {
            holder.morningRetreat.setVisibility(View.INVISIBLE);
        } else {
            holder.morningRetreat.setVisibility(View.VISIBLE);
            holder.morningRetreat.setText("上午退:\t" + users.getMorning_retreat());
        }
        //下午到
        if (TextUtils.isEmpty(users.getAfternoon_to())) {
            holder.afternoonTo.setVisibility(View.INVISIBLE);
        } else {
            holder.afternoonTo.setVisibility(View.VISIBLE);
            holder.afternoonTo.setText("下午到：\t" + users.getAfternoon_to());
        }
        //下午退
        if (TextUtils.isEmpty(users.getAfternoon_retreat())) {
            holder.afternoonRetreat.setVisibility(View.INVISIBLE);
        } else {
            holder.afternoonRetreat.setVisibility(View.VISIBLE);
            holder.afternoonRetreat.setText("下午退：\t" + users.getAfternoon_retreat());
        }
        //晚上退
        if (TextUtils.isEmpty(users.getNight_retreat())) {
            holder.nightRetreat.setVisibility(View.INVISIBLE);
        } else {
            holder.nightRetreat.setVisibility(View.VISIBLE);
            holder.nightRetreat.setText("晚上退：\t" + users.getNight_retreat());
        }
        //出差
        if (TextUtils.isEmpty(users.getLeave())) {
            holder.leave.setVisibility(View.INVISIBLE);
        } else {
            holder.leave.setVisibility(View.VISIBLE);
            holder.leave.setText("出差/请假：" + users.getLeave());
        }


    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.data_time)
        TextView dataTime;
        @BindView(R.id.morning_to)
        TextView morningTo;
        @BindView(R.id.morning_retreat)
        TextView morningRetreat;
        @BindView(R.id.afternoon_to)
        TextView afternoonTo;
        @BindView(R.id.afternoon_retreat)
        TextView afternoonRetreat;
        @BindView(R.id.night_retreat)
        TextView nightRetreat;
        @BindView(R.id.leave)
        TextView leave;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

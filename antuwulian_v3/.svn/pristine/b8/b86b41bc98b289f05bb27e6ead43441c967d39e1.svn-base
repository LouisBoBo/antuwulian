package com.slxk.gpsantu.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.AlarmClockBean;

import java.util.List;


/**
 * 闹钟模式
 */
public class AlarmClockAdapter extends BaseAdapter {

    private Context mContext;
    List<AlarmClockBean> timeBeans;

    public AlarmClockAdapter(Context mContext, List<AlarmClockBean> list) {
        this.mContext = mContext;
        this.timeBeans = list;
    }

    @Override
    public int getCount() {
        return timeBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return timeBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_alarm_clock, null);
        TextView tvMobile = convertView.findViewById(R.id.tv_name);
        AlarmClockBean item = timeBeans.get(position);
        tvMobile.setText(mContext.getString(R.string.txt_alarm_clock) + (position + 1) + ":");
        TextView tvTime = convertView.findViewById(R.id.tv_time);
        TextView tvDelete = convertView.findViewById(R.id.tv_delete);
        if (!TextUtils.isEmpty(item.getTime())) {
            tvTime.setText(item.getTime());
        } else {
            tvTime.setHint(mContext.getString(R.string.txt_please_set_clock));
        }

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCount() == 1) {
                    ToastUtils.showShort(mContext.getString(R.string.txt_at_least_one_clock));
                    return;
                }
                timeBeans.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}

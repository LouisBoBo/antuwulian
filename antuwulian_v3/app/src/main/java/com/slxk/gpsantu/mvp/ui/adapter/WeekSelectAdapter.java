package com.slxk.gpsantu.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.WeekValueBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


/**
 * 星期模式
 */
public class WeekSelectAdapter extends CommonAdapter<WeekValueBean> {

    public WeekSelectAdapter(Context context, int layoutId, List<WeekValueBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, WeekValueBean item, int position) {
        TextView tvValue = viewHolder.getView(R.id.tv_value);
        tvValue.setText(item.getWeekName());
        if (item.isSelect()){
            tvValue.setBackground(mContext.getResources().getDrawable(R.drawable.week_select));
            tvValue.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else{
            tvValue.setBackgroundColor(mContext.getResources().getColor(R.color.color_F4F4F4));
            tvValue.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
    }
}

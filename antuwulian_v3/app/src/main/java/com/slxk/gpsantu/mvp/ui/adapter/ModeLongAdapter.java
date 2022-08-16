package com.slxk.gpsantu.mvp.ui.adapter;

import android.content.Context;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.SavePowerBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 设置定位模式-省电模式，选择间隔时长
 */
public class ModeLongAdapter extends CommonAdapter<SavePowerBean> {

    public ModeLongAdapter(Context context, int layoutId, List<SavePowerBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SavePowerBean item, int position) {
        viewHolder.setText(R.id.tv_long, item.getDes());
    }
}

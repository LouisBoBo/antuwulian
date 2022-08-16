package com.slxk.gpsantu.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.DataCenterBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 首页功能菜单
 */
public class FunctionAdapter extends CommonAdapter<DataCenterBean> {

    public FunctionAdapter(Context context, int layoutId, List<DataCenterBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, DataCenterBean item, int position) {
        ImageView ivMenu = viewHolder.getView(R.id.iv_menu);
        ivMenu.setImageResource(item.getDrawableId());
        viewHolder.setText(R.id.tv_menu, item.getFunction());
    }
}

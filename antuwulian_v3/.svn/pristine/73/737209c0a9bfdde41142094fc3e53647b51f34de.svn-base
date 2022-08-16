package com.slxk.gpsantu.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;


import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.ShareBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 分享adapter
 */
public class ShareAdapter extends CommonAdapter<ShareBean> {

    public ShareAdapter(Context context, int layoutId, List<ShareBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ShareBean item, int position) {
        ImageView ivPath = viewHolder.getView(R.id.iv_path);
        ivPath.setImageResource(item.getDrawableId());
        viewHolder.setText(R.id.tv_share, item.getShare());
    }
}

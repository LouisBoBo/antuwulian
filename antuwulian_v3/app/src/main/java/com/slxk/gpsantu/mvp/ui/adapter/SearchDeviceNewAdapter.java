package com.slxk.gpsantu.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;

/**
 * 搜索设备
 */
public class SearchDeviceNewAdapter extends BaseQuickAdapter<FindDeviceResultBean.ItemsBean, BaseViewHolder> {

    public SearchDeviceNewAdapter(int layoutResId, @Nullable List<FindDeviceResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FindDeviceResultBean.ItemsBean item) {
        helper.setText(R.id.tv_search_imei, "IMEI：" + item.getImei());
        helper.setText(R.id.tv_search_name, mContext.getString(R.string.txt_name) + item.getCar_number());
        helper.setText(R.id.tv_search_version, mContext.getString(R.string.txt_type) + item.getVersion());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}

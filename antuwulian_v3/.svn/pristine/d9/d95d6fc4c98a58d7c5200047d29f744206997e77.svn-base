package com.slxk.gpsantu.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.DeviceListForManagerResultBean;

import java.util.List;

/**
 * 设备列表
 */
public class DeviceListManagementAdapter extends BaseQuickAdapter<DeviceListForManagerResultBean.ItemsBean, BaseViewHolder> {


    public DeviceListManagementAdapter(int layoutResId, @Nullable List<DeviceListForManagerResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceListForManagerResultBean.ItemsBean item) {
        ImageView ivSelect = helper.getView(R.id.iv_select);
        if (item.getCar_number().length() != 0) {
            helper.setText(R.id.tv_name, String.valueOf(item.getCar_number()));
        } else {
            helper.setText(R.id.tv_name, String.valueOf(item.getImei()));
        }
        if (item.isSelect()) {
            ivSelect.setImageResource(R.drawable.icon_manage_select);
        } else {
            ivSelect.setImageResource(R.drawable.icon_manage_unselect);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}

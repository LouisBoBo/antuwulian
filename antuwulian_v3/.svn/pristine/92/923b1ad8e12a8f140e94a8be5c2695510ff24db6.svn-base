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
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceListForManagerResultBean;

import java.util.List;

/**
 * 设备列表
 */
public class GroupManagementAdapter extends BaseQuickAdapter<DeviceGroupResultBean.GaragesBean, BaseViewHolder> {


    public GroupManagementAdapter(int layoutResId, @Nullable List<DeviceGroupResultBean.GaragesBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceGroupResultBean.GaragesBean item) {
        ImageView ivSelect = helper.getView(R.id.iv_select);
        helper.setText(R.id.tv_name, item.getSname() + "(" + item.getImei_count()  + ")");
        if (item.isSelect()) {
            ivSelect.setImageResource(R.drawable.icon_manage_select);
        } else {
            ivSelect.setImageResource(R.drawable.icon_manage_unselect);
        }
        helper.addOnClickListener(R.id.iv_select);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}

package com.slxk.gpsantu.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 分组列表
 */
public class GroupListAdapter extends BaseQuickAdapter<DeviceGroupResultBean.GaragesBean, BaseViewHolder> {

    public GroupListAdapter(int layoutResId, @Nullable List<DeviceGroupResultBean.GaragesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceGroupResultBean.GaragesBean item) {
        TextView tvName = helper.getView(R.id.tv_group_name);
        tvName.setText(item.getSname());
        if (item.isSelect()){
            tvName.setTextColor(mContext.getResources().getColor(R.color.color_2E7BEC));
        }else{
            tvName.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}

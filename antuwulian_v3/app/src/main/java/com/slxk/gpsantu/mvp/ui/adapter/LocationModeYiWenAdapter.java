package com.slxk.gpsantu.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.LocationModeBean;

import java.util.List;

/**
 * 定位模式
 */
public class LocationModeYiWenAdapter extends BaseQuickAdapter<LocationModeBean, BaseViewHolder> {

    public LocationModeYiWenAdapter(int layoutResId, @Nullable List<LocationModeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LocationModeBean item) {
        TextView tvMode = helper.getView(R.id.tv_mode);
        TextView tvModeValue = helper.getView(R.id.tv_value);
        TextView tvDescription = helper.getView(R.id.tv_des);
        ImageView ivSelect = helper.getView(R.id.iv_select);
        if (item.getModeNamePlus().length() != 0) {
            tvMode.setText(item.getModeName() + "(" + item.getModeNamePlus() + ")");
        } else {
            tvMode.setText(item.getModeName());
        }
        tvDescription.setText(item.getDescription());
        ivSelect.setImageResource(item.isSelect() ? R.drawable.icon_select : R.drawable.icon_unselected);
        tvMode.setTextColor(item.isSelect() ? mContext.getResources().getColor(R.color.color_2E7BEC) : mContext.getResources().getColor(R.color.color_333333));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}

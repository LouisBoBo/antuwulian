package com.slxk.gpsantu.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.LocationModeBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 定位模式
 */
public class LocationModeAdapter extends BaseQuickAdapter<LocationModeBean, BaseViewHolder> {

    public LocationModeAdapter(int layoutResId, @Nullable List<LocationModeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LocationModeBean item) {
        LinearLayout llMode = helper.getView(R.id.ll_location_mode);
        TextView tvMode = helper.getView(R.id.tv_mode);
        TextView tvDescription = helper.getView(R.id.tv_description);
        tvDescription.setText(item.getDescription());
        if (item.getModeNamePlus().length() != 0) {
            tvMode.setText(item.getModeName() + "(" + item.getModeNamePlus() + ")");
        } else {
            tvMode.setText(item.getModeName());
        }
        if (item.isSelect()){
            llMode.setBackgroundResource(R.drawable.bg_2e7bec_10);
            tvMode.setTextColor(mContext.getResources().getColor(R.color.color_FFFFFF));
            tvDescription.setTextColor(mContext.getResources().getColor(R.color.color_FFFFFF));
        }else{
            llMode.setBackgroundResource(R.drawable.bg_ffffff_10);
            tvMode.setTextColor(mContext.getResources().getColor(R.color.color_4B4B4B));
            tvDescription.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}

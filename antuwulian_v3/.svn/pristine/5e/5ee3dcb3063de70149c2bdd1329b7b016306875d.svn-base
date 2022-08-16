package com.slxk.gpsantu.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.FenceResultBean;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 围栏列表
 */
public class FenceAdapter extends BaseQuickAdapter<FenceResultBean.ItemsBean, BaseViewHolder> {

    public FenceAdapter(int layoutResId, @Nullable List<FenceResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FenceResultBean.ItemsBean item) {
        helper.setText(R.id.tv_fence_name, mContext.getString(R.string.txt_name) + item.getName());
        String type = mContext.getString(R.string.txt_type_fence);
        switch (item.getType()) {
            case ResultDataUtils.Fence_Round:
                type = type + mContext.getString(R.string.txt_round_fence);
                break;
            case ResultDataUtils.Fence_City:
                type = type + mContext.getString(R.string.txt_city_fence);
                break;
            case ResultDataUtils.Fence_Polygonal:
                type = type + mContext.getString(R.string.txt_polygonal_fence);
                break;
        }
        helper.setText(R.id.tv_fence_type, type);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}

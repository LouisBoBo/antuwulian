package com.slxk.gpsantu.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.AlarmScreenBean;

import java.util.List;

/**
 * 报警消息筛选bean
 */
public class AlarmScreenAdapter extends BaseQuickAdapter<AlarmScreenBean.ItemsBean, BaseViewHolder> {

    public AlarmScreenAdapter(int layoutResId, @Nullable List<AlarmScreenBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AlarmScreenBean.ItemsBean item) {
        helper.setText(R.id.tv_alarm, item.getAlarm_name());
        ImageView ivSelect = helper.getView(R.id.iv_select);
        if (item.isSelect()){
            ivSelect.setImageResource(R.drawable.icon_select_small);
        }else{
            ivSelect.setImageResource(R.drawable.icon_unselected_small);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}

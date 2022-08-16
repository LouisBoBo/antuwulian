package com.slxk.gpsantu.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.PenetrateHistoryResultBean;
import com.slxk.gpsantu.mvp.model.bean.PenetrateParamResultBean;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.slxk.gpsantu.mvp.utils.DeviceUtils;

import java.util.List;

/**
 * 透传-历史回复记录
 */
public class PenetrateHistoryAdapter extends BaseQuickAdapter<PenetrateHistoryResultBean.ItemsBean, BaseViewHolder> {

    public PenetrateHistoryAdapter(int layoutResId, @Nullable List<PenetrateHistoryResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PenetrateHistoryResultBean.ItemsBean item) {
        if (item.getType() == 20000){
            PenetrateParamResultBean bean = DeviceUtils.paramPenetrateParamData(item.getParam());
            helper.setText(R.id.tv_instruction, mContext.getString(R.string.txt_instruction) + bean.getContent());
        }else {
            helper.setText(R.id.tv_instruction, mContext.getString(R.string.txt_instruction) + item.getParam());
        }
        helper.setText(R.id.tv_reply, mContext.getString(R.string.txt_reply) + item.getBack_result());
        helper.setText(R.id.tv_time, mContext.getString(R.string.txt_time) + DateUtils.timeConversionDate_three(String.valueOf(item.getTime())));
        helper.setText(R.id.tv_account, mContext.getString(R.string.txt_account) + item.getAccount());

        helper.addOnClickListener(R.id.tv_copy);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}

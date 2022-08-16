package com.slxk.gpsantu.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.DeviceListForManagerResultBean;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.slxk.gpsantu.mvp.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 设备列表
 */
public class DeviceListAdapter extends BaseQuickAdapter<DeviceListForManagerResultBean.ItemsBean, BaseViewHolder> {


    public DeviceListAdapter(int layoutResId, @Nullable List<DeviceListForManagerResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceListForManagerResultBean.ItemsBean item) {
        TextView tvState = helper.getView(R.id.tv_device_state);
        TextView tvName = helper.getView(R.id.tv_device_name);
        TextView tvImei = helper.getView(R.id.tv_device_imei);

        String name = item.getCar_number();
        tvName.setText(String.valueOf(name != null && name.length() > 0 ? name : item.getImei()));

        if (item.getDay_distance() == 0){
            tvState.setText("0km");
        }else{
            double mileage = item.getDay_distance() / 1000;
            tvState.setText(Utils.formatValue(mileage) + "km");
        }

        // 1.start_dev_time设备开始使用时间=0或者没有这个字段的话，就是未激活
        // 2.expire首先判断是否过期
        // 3.state_begin_time=0或者没有这个字段的话就直接显示离线（不显示离线时间）
        if (item.getStart_dev_time() == 0){
            tvImei.setText(R.string.txt_inactivated);
            tvImei.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }else if (item.isExpire()){
            tvImei.setText(R.string.txt_device_expire_hint);
            tvImei.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }else if (item.getState_begin_time() == 0){
            tvImei.setText(R.string.txt_state_line_down);
            tvImei.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }else{
            if (item.getState_time() == 0){
                tvImei.setText("");
            }else{
                String str = ResultDataUtils.getDeviceStateTime(item.getState_time(), item.getState(), mContext);
                switch (item.getState()){
                    case ResultDataUtils.Device_State_Line_On:
                        tvImei.setText(mContext.getString(R.string.txt_state_line_on) + str);
                        tvImei.setTextColor(mContext.getResources().getColor(R.color.color_2ABA5A));
                        break;
                    case ResultDataUtils.Device_State_Line_Sleep:
                        tvImei.setText(mContext.getString(R.string.txt_state_line_sleep) + str);
                        tvImei.setTextColor(mContext.getResources().getColor(R.color.color_FA6A50));
                        break;
                    case ResultDataUtils.Device_State_Line_Down:
                        tvImei.setText(mContext.getString(R.string.txt_state_line_down) + str);
                        tvImei.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                        break;
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}

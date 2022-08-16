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
import com.slxk.gpsantu.mvp.model.bean.AlarmRecordResultBean;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 报警消息个人版
 */
public class AlarmRecordUserAdapter extends BaseQuickAdapter<AlarmRecordResultBean.ItemsBean, BaseViewHolder> {

    public AlarmRecordUserAdapter(int layoutResId, @Nullable List<AlarmRecordResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AlarmRecordResultBean.ItemsBean item) {
        ImageView ivSelect = helper.getView(R.id.iv_select);
        if (item.isEdit()){
            ivSelect.setVisibility(View.VISIBLE);
            if (item.isSelect()) {
                ivSelect.setImageResource(R.drawable.icon_select_small);
            } else {
                ivSelect.setImageResource(R.drawable.icon_unselected_small);
            }
        }else{
            ivSelect.setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_device_name, item.getAlarm_name());
        helper.setText(R.id.tv_device_number, mContext.getString(R.string.txt_device) + (!TextUtils.isEmpty(item.getNumber()) ? item.getNumber() : item.getImei()));
        helper.setText(R.id.tv_alarm_time, mContext.getString(R.string.txt_time) + DateUtils.timeConversionDate_two(String.valueOf(item.getTime())));
        helper.setText(R.id.tv_device_speed, mContext.getString(R.string.txt_speed) + ((double) item.getDevspeed() / 10) + "km/h");
        TextView tvAddress = helper.getView(R.id.tv_alarm_address);
        String address = item.getAddr();
        String addressShow = item.getAddrShow();
        if (!TextUtils.isEmpty(addressShow)) {
            address = addressShow;
        }
        tvAddress.setText(mContext.getString(R.string.txt_address) + address);
        // 开始判断，如果是经纬度，则启动app自解析地址
        address = address.replace(".", "");
        address = address.replace(",", "");
        address = address.replace("-", "");
        address = address.replace(" ", "");
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(address);
        if (!isNum.matches()) {
            tvAddress.setTextColor(mContext.getResources().getColor(R.color.color_000000));
        } else {
            tvAddress.setTextColor(mContext.getResources().getColor(R.color.color_2E7BEC));
        }

//        helper.addOnClickListener(R.id.iv_alarm);
        helper.addOnClickListener(R.id.iv_select);
        helper.addOnClickListener(R.id.tv_detail);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
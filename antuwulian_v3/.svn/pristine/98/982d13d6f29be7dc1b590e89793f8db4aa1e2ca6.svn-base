package com.slxk.gpsantu.mvp.weiget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.view.data.haibin.Calendar;
import com.slxk.gpsantu.mvp.ui.view.data.haibin.CalendarView;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.slxk.gpsantu.mvp.utils.Utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TrackDatePopupWindow extends PopupWindow implements CalendarView.OnCalendarMultiSelectListener,
        CalendarView.OnYearChangeListener, CalendarView.OnMonthChangeListener, View.OnClickListener {

    private Context mContext;
    private TextView tvData; // 日期
    private CalendarView calendarView; // 日期组件
    private Button btnConfirm; // 确认
    private ImageView ivBeforeMonth; // 上一个月
    private ImageView ivAfterMonth; // 下一个月

    private Map<String, Calendar> calendarMap = new HashMap<>();
    private Calendar currentDay;
    private onTrackDataChange dataChange;
    private long fiveMonthBeforeDateTime; //  五个月前日期 时间戳
    private int fiveDateYear; // 五个月前 的年份
    private int fiveDateMonth; // 五个月前 的月份

    public TrackDatePopupWindow(final Context context, ArrayList<Calendar> trackDateList, Calendar currentDay) {
        super(context);
        this.mContext = context;
        View root = View.inflate(context, R.layout.popup_window_date_select_new, null);
        tvData = root.findViewById(R.id.tv_data);
        ivBeforeMonth = root.findViewById(R.id.iv_left);
        ivAfterMonth = root.findViewById(R.id.iv_after);
        calendarView = root.findViewById(R.id.calendarView);
        btnConfirm = root.findViewById(R.id.btn_confirm);
        fiveMonthBeforeDateTime = getFiveBeforeTime();

        calendarView.setOnCalendarMultiSelectListener(this);
        calendarView.setOnYearChangeListener(this);
        calendarView.setOnMonthChangeListener(this);
        this.currentDay = currentDay;
        if (trackDateList.size() > 0) {
            for (Calendar calendar : trackDateList) {
                calendarMap.put(calendar.toString(), calendar);
            }
        }

        //设置SelectPicPopupWindow的View
        this.setContentView(root);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.color_black_40)));

        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        calendarView.setSchemeDate(calendarMap);
        calendarView.scrollToCalendar(currentDay.getYear(), currentDay.getMonth(), currentDay.getDay());
        if (fiveDateYear != 0 && fiveDateMonth != 0)
            calendarView.setRange(fiveDateYear, fiveDateMonth, 1, 2050, 12, -1);
        tvData.setText(currentDay.getYear() + mContext.getString(R.string.txt_year)
                + currentDay.getMonth() + mContext.getString(R.string.txt_month));

        ivBeforeMonth.setOnClickListener(this);
        ivAfterMonth.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                calendarView.scrollToPre();
                break;
            case R.id.iv_after:
                calendarView.scrollToNext();
                break;
            case R.id.btn_confirm:
                onConfirm();
                break;
        }
    }

    private void onConfirm() {
        List<Calendar> calendarList = calendarView.getMultiSelectCalendars();
        if(calendarList.size() > 0){
            java.util.Calendar startCalendar = java.util.Calendar.getInstance();
            startCalendar.set(
                    calendarList.get(0).getYear(),
                    calendarList.get(0).getMonth() - 1,
                    calendarList.get(0).getDay(),
                    0,
                    0,
                    0);
            String startTime = calendarList.get(0).getYear() + "-" +
                    (calendarList.get(0).getMonth()) + "-" + calendarList.get(0).getDay() + " 00:00:00";

            java.util.Calendar endCalendar = java.util.Calendar.getInstance();
            endCalendar.set(
                    calendarList.get(calendarList.size() - 1).getYear(),
                    calendarList.get(calendarList.size() - 1).getMonth() - 1,
                    calendarList.get(calendarList.size() - 1).getDay(),
                    23,
                    59,
                    59);

            if (DateUtils.data_2(startTime) < fiveMonthBeforeDateTime) {
                ToastUtils.showShort(mContext.getString(R.string.txt_six_month_max));
                return;
            }

            if(endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis() > 1000 * 60 * 60 * 24 * 7){
                ToastUtils.showShort(mContext.getString(R.string.txt_max_7));
                return;
            }
            if (dataChange != null){
                dataChange.OnSelectDate(calendarList);
            }
        }else{
            ToastUtils.showShort(mContext.getString(R.string.txt_query_date));
        }
    }

    /**
     * 兼容 android 7.0之后设置showAsDropDown失效问题
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom + Utils.getStatusBarHeight(mContext);
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, a[1] + anchor.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchor, xoff, yoff);
        }
    }

    @Override
    public void onYearChange(int year) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMonthChange(int year, int month) {
        tvData.setText(year + mContext.getString(R.string.txt_year) + month + mContext.getString(R.string.txt_month));
    }

    @Override
    public void onCalendarMultiSelectOutOfRange(Calendar calendar) {

    }

    @Override
    public void onMultiSelectOutOfSize(Calendar calendar, int maxSize) {
        ToastUtils.showShort(mContext.getString(R.string.txt_max_7));
    }

    @Override
    public void onCalendarMultiSelect(Calendar calendar, int curSize, int maxSize) {

    }

    public void setTrackDataChange(onTrackDataChange change){
        this.dataChange = change;
    }

    public interface onTrackDataChange {

        void OnSelectDate(List<Calendar> calendarList);

    }

    // 当前月，往前推5个月 轨迹限制查询6个月
    private long getFiveBeforeTime() {
        java.util.Calendar fiveMonthBeforeDay = java.util.Calendar.getInstance(Locale.ENGLISH);
        fiveMonthBeforeDay.add(java.util.Calendar.MONTH, -5);
        fiveDateYear = fiveMonthBeforeDay.get(java.util.Calendar.YEAR);
        fiveDateMonth = fiveMonthBeforeDay.get(java.util.Calendar.MONTH) + 1;
        String time = fiveMonthBeforeDay.get(java.util.Calendar.YEAR) + "-" +
                (fiveMonthBeforeDay.get(java.util.Calendar.MONTH) + 1) + "-1" + " 00:00:00";
        return DateUtils.data_2(time);
    }

}

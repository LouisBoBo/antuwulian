package com.slxk.gpsantu.mvp.weiget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.utils.Utils;

import java.util.Calendar;


/**
 * 时间选择dialog
 */
public class TimeSelectDialog extends Dialog {
    private Context context = null;
    private String strStart = null;
    private String strEnd = null;
    private boolean isCompare = false;

    private TextView btnModify = null;
    private TextView btnCancel = null;
    private TextView txtStartTime = null;
    private TimePicker tpStartTime = null;
    private TextView txtEndTime = null;
    private TimePicker tpEndTime = null;


    private DialogCallback dialogCallback = null;

    public TimeSelectDialog(Context context) {
        super(context);
        this.context = context;
    }

    public TimeSelectDialog(Context context, int themeResId, String start, String end, boolean isCompareTmp) {
        super(context, themeResId);
        this.context = context;
        this.strStart = start;
        this.strEnd = end;
        this.isCompare = isCompareTmp;
    }

    protected TimeSelectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public interface DialogCallback{
        void EventOK(String startTime, String endTime);
        void EventCancel();
    }

    public void setDialogCallback(DialogCallback dialogCallback){
        this.dialogCallback = dialogCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_time_select,null);
        setContentView(view);

        setCanceledOnTouchOutside(true);

        btnModify = view.findViewById(R.id.id_confirm);
        btnCancel = view.findViewById(R.id.id_cancel);
        txtStartTime = findViewById(R.id.id_start_time);
        txtEndTime = findViewById(R.id.id_end_time);
        tpStartTime = findViewById(R.id.id_start);
        tpEndTime = findViewById(R.id.id_end);

        tpStartTime.setIs24HourView(true);
        tpEndTime.setIs24HourView(true);

        tpStartTime.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        tpEndTime.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        txtStartTime.setText(strStart);
        txtEndTime.setText(strEnd);

        String[] arrStr = strStart.split(":");
        tpStartTime.setCurrentHour(Integer.valueOf(arrStr[0]));
        tpStartTime.setCurrentMinute(Integer.valueOf(arrStr[1]));

        String[] arrStrEnd = strEnd.split(":");
        tpEndTime.setCurrentHour(Integer.valueOf(arrStrEnd[0]));
        tpEndTime.setCurrentMinute(Integer.valueOf(arrStrEnd[1]));

        int[] displayPx = Utils.getDisplayPx(context);
        Window window = getWindow();
        window.getDecorView().setPadding(10, 0, 10, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int)(displayPx[0] * 1);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startHour = tpStartTime.getCurrentHour();
                int startMinute = tpStartTime.getCurrentMinute();
                int endHour = tpEndTime.getCurrentHour();
                int endMinute = tpEndTime.getCurrentMinute();
                boolean isOK = false;
                if(endHour > startHour){
                    isOK = true;
                }else if(endHour == startHour && endMinute > startMinute){
                    isOK = true;
                }

                if(!isOK){
                    ToastUtils.showShort(context.getString(R.string.txt_begin_gt_end));
                    return;
                }

                if(isCompare){
                    Calendar current = Calendar.getInstance();
                    int currentHour = current.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = current.get(Calendar.MINUTE);
                    boolean isGt = false;
                    if(currentHour > startHour){
                        isGt = true;
                    }else if(currentHour == startHour && currentMinute > startMinute){
                        isGt = true;
                    }
                    if(!isGt){
                        ToastUtils.showShort(context.getString(R.string.txt_start_gt_current));
                        return;
                    }
                }


                dialogCallback.EventOK(txtStartTime.getText().toString().trim(), txtEndTime.getText().toString().trim());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallback.EventCancel();
            }
        });

        tpStartTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                txtStartTime.setText(String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
            }
        });

        tpEndTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                txtEndTime.setText(String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
            }
        });
    }
}

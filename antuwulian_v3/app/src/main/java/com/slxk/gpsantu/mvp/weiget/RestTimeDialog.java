package com.slxk.gpsantu.mvp.weiget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.slxk.gpsantu.R;
import com.blankj.utilcode.util.ToastUtils;

/**
 * 免打扰时间设置
 */
public class RestTimeDialog extends DialogFragment implements View.OnClickListener {

    private TextView tvDismiss;
    private TextView tvConfirm;
    private TimePicker timePickerStart; // 开始时间
    private TimePicker timePickerEnd; // 结束时间
    private String restTime; // 免打扰时间，格式：23:00-08:00
    private onSelectRestTimeChange timeChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_rest_time, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog){
        try{
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }catch (Exception e){
            e.printStackTrace();
        }

        tvDismiss = dialog.findViewById(R.id.tv_cancel);
        tvConfirm = dialog.findViewById(R.id.tv_confirm);
        timePickerStart = dialog.findViewById(R.id.time_picker_start);
        timePickerEnd = dialog.findViewById(R.id.time_picker_end);
        tvConfirm.setOnClickListener(this);
        tvDismiss.setOnClickListener(this);

        timePickerStart.setIs24HourView(true);
        timePickerStart.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timePickerEnd.setIs24HourView(true);
        timePickerEnd.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        if (!TextUtils.isEmpty(restTime)){
            String[] time = restTime.split("-");
            String[] start = time[0].split(":");
            timePickerStart.setCurrentHour(Integer.valueOf(start[0]));
            timePickerStart.setCurrentMinute(Integer.valueOf(start[1]));

            String[] end = time[1].split(":");
            timePickerEnd.setCurrentHour(Integer.valueOf(end[0]));
            timePickerEnd.setCurrentMinute(Integer.valueOf(end[1]));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                onConfirm();
                break;
        }
    }

    @SuppressLint("DefaultLocale")
    private void onConfirm(){
        String startTime = String.format("%02d:%02d", timePickerStart.getCurrentHour(), timePickerStart.getCurrentMinute());
        String endTime = String.format("%02d:%02d", timePickerEnd.getCurrentHour(), timePickerEnd.getCurrentMinute());
        if (startTime.equals(endTime)){
            ToastUtils.showShort(getString(R.string.txt_time_error));
            return;
        }
        restTime = startTime + ":00" + "-" + endTime + ":00";
        if (timeChange != null){
            timeChange.onSelectTime(restTime);
        }
        dismiss();
    }

    public void show(FragmentManager manager, String time, onSelectRestTimeChange change){
        if (isAdded()){
            dismiss();
        }
        this.restTime = time;
        this.timeChange = change;
        super.show(manager, "RestTimeDialog");
    }

    public interface onSelectRestTimeChange{

        /**
         * 提交选中的时间
         * @param time
         */
        void onSelectTime(String time);

    }
}

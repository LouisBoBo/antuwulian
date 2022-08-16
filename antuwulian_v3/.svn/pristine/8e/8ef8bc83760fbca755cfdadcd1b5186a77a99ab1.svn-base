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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.WeekValueBean;
import com.slxk.gpsantu.mvp.ui.adapter.WeekSelectAdapter;
import com.slxk.gpsantu.mvp.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 设置星期模式
 */
public class SelectTimeWeekDialog extends DialogFragment {

    private TextView tvDismiss;
    private TextView tvConfirm;
    private TimePicker timePicker;
    private String tmpTime; //
    private onSelectTimeChange timeChange;
    private ArrayList<WeekValueBean> weeks;
    private GridView gvWeek;
    private WeekSelectAdapter mAdapter;
    private List<Integer> loopList = new ArrayList<>();
    private List<Integer> loopUpdateList = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_set_week_mode, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        weeks = new ArrayList<>();
        boolean isMon = false;
        boolean isTue = false;
        boolean isWed = false;
        boolean isThu = false;
        boolean isFri = false;
        boolean isSat = false;
        boolean isSun = false;
        for (int i = 0; i < loopList.size(); i++) {
            switch (loopList.get(i)) {
                case 0:
                    isSun = true;
                    break;
                case 1:
                    isMon = true;
                    break;
                case 2:
                    isTue = true;
                    break;
                case 3:
                    isWed = true;
                    break;
                case 4:
                    isThu = true;
                    break;
                case 5:
                    isFri = true;
                    break;
                case 6:
                    isSat = true;
                    break;
            }
        }
        gvWeek = dialog.findViewById(R.id.gv_week);
        mAdapter = new WeekSelectAdapter(getContext(), R.layout.item_week_mode, weeks);
        gvWeek.setAdapter(mAdapter);
        gvWeek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isSelect = weeks.get(position).isSelect();
                weeks.get(position).setSelect(!isSelect);
                mAdapter.notifyDataSetChanged();
            }
        });

        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvDismiss = dialog.findViewById(R.id.tv_cancel);
        timePicker = dialog.findViewById(R.id.time_picker);
        tvConfirm = dialog.findViewById(R.id.tv_confirm);

        weeks.add(new WeekValueBean(1, getString(R.string.txt_mon), isMon));
        weeks.add(new WeekValueBean(2, getString(R.string.txt_tue), isTue));
        weeks.add(new WeekValueBean(3, getString(R.string.txt_wed), isWed));
        weeks.add(new WeekValueBean(4, getString(R.string.txt_thu), isThu));
        weeks.add(new WeekValueBean(5, getString(R.string.txt_fri), isFri));
        weeks.add(new WeekValueBean(6, getString(R.string.txt_sat), isSat));
        weeks.add(new WeekValueBean(0, getString(R.string.txt_sun), isSun));


        timePicker.setIs24HourView(true);
        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        if (!TextUtils.isEmpty(tmpTime)) {
            String[] arrStr = tmpTime.split(":");
            if (arrStr.length == 2) {
                timePicker.setCurrentHour(Integer.valueOf(arrStr[0]));
                timePicker.setCurrentMinute(Integer.valueOf(arrStr[1]));
            }
        }

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                loopUpdateList.clear();
                for (WeekValueBean bean : weeks) {
                    if (bean.isSelect()) {
                        loopUpdateList.add(bean.getWeek());
                    }
                }
                if (loopUpdateList.size() == 0) {
                    ToastUtils.showShort(getString(R.string.txt_at_least_one_day));
                    return;
                }
                tmpTime = String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                tmpTime = DateUtils.GetOthersStrFromENGLISHStr(tmpTime, "HH:mm", Locale.getDefault());
                if (timeChange != null) {
                    timeChange.onSelectTime(tmpTime, loopUpdateList);
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, String time, List<Integer> loopLoc, onSelectTimeChange change) {
        if (isAdded()) {
            dismiss();
        }
        this.tmpTime = time;
        loopList.addAll(loopLoc);
        this.timeChange = change;
        super.show(manager, "SelectTimeDialog");
    }

    public interface onSelectTimeChange {

        /**
         * 提交选中的时间
         *
         * @param time
         */
        void onSelectTime(String time, List<Integer> loopLoc);

    }


}

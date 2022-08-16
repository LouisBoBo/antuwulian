package com.slxk.gpsantu.mvp.weiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.utils.Utils;


/**
 * 省电模式-自定义时间选择
 */
public class CustomizeTimeDialog extends DialogFragment {

    private EditText edtTime;
    private Button btnCancel;
    private Button btnConfirm;
    private onCustomizeTimeChange timeChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_customize_time, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
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

        edtTime = dialog.findViewById(R.id.edt_time);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(edtTime);
                dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    onConfirm();
                }
            }
        });
    }

    private void onConfirm(){
        String strTime = edtTime.getText().toString();
        if (TextUtils.isEmpty(strTime)){
            ToastUtils.showShort(getString(R.string.txt_customize_input_error));
            return;
        }
        int minTime = Integer.parseInt(strTime);
        if (minTime <= 0 || minTime > 1080){
            ToastUtils.showShort(getString(R.string.txt_input_error));
            return;
        }
        hideKeyboard(edtTime);
        int secondTime = minTime * 60;
        if (timeChange != null){
            timeChange.onCustomizeTime(secondTime);
        }
        dismiss();
    }

    /**
     * 收起软键盘
     *
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void show(FragmentManager manager, onCustomizeTimeChange change){
        if (isAdded()){
            dismiss();
        }
        this.timeChange = change;
        super.show(manager, "CustomizeTimeDialog");
    }

    public interface onCustomizeTimeChange{

        /**
         * 填写时间
         * @param time 秒
         */
        void onCustomizeTime(int time);

    }

}

package com.slxk.gpsantu.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ScreenUtils;

import com.slxk.gpsantu.R;

/**
 * 绑定手机号，手机号已绑定其他账号或设备号
 */
public class PhoneHasBindDialog extends DialogFragment implements View.OnClickListener {

    private Button btnCancel;
    private Button btnRegister;
    private Button btnUnbind;
    private onPhoneHasBindChange phoneHasBindChange;
    private TextView tvTitle;
    private int type;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_phone_has_bind, null);
        dialog.setContentView(viewGroup);
        setCancelable(false); // 点击屏幕或物理返回键，true：dialog消失，false：不消失
        dialog.setCanceledOnTouchOutside(false); // 点击屏幕，dialog不消失；点击物理返回键dialog消失
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ScreenUtils.getScreenWidth() * 4 / 5;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnRegister = dialog.findViewById(R.id.btn_register);
        btnUnbind = dialog.findViewById(R.id.btn_unbind);
        tvTitle = dialog.findViewById(R.id.tv_title);
        if (type == 0) { //手机号已绑定其他账号
            tvTitle.setText(getString(R.string.txt_register_error_tip));
        } else { //手机号已绑定其他设备
            tvTitle.setText(getString(R.string.txt_bind_error_tip));
        }
        btnCancel.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.btn_cancel:
                if (phoneHasBindChange != null) {
                    phoneHasBindChange.onLogin();
                }
                break;
            case R.id.btn_register:
                if (phoneHasBindChange != null) {
                    phoneHasBindChange.onRegister();
                }
                break;
            case R.id.btn_unbind:
                if (phoneHasBindChange != null) {
                    phoneHasBindChange.onUnbind();
                }
                break;
        }
    }

    public void show(FragmentManager manager,int type, onPhoneHasBindChange change){
        if (isAdded()){
            dismiss();
        }
        this.phoneHasBindChange = change;
        this.type = type ;
        super.show(manager, "PhoneHasBindDialog");
    }

    public interface onPhoneHasBindChange{

        void onRegister();

        void onUnbind();

        void onLogin();

    }

}

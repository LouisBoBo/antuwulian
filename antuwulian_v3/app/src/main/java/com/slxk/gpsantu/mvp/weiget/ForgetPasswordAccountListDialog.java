package com.slxk.gpsantu.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.adapter.ForgetPasswordAccountListAdapter;

import java.util.List;



/**
 * 手机号登录 - 账号列表
 */
public class ForgetPasswordAccountListDialog extends DialogFragment {

    private ImageView ivClose;
    private RecyclerView recyclerView;
    private List<String> accountInfos;
    private ForgetPasswordAccountListAdapter mAdapter;
    private onLoginAccountChange loginAccountChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_account_forget_password_list, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = ScreenUtils.getScreenHeight() / 2;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ivClose = dialog.findViewById(R.id.iv_close);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ForgetPasswordAccountListAdapter(R.layout.item_login_account_list, accountInfos);
        recyclerView.setAdapter(mAdapter);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (loginAccountChange != null){
                    loginAccountChange.onAccountInfo(accountInfos.get(position));
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, List<String> infos, onLoginAccountChange change){
        if (isAdded()){
            dismiss();
        }
        this.accountInfos = infos;
        this.loginAccountChange = change;
        super.show(manager, "ForgetPasswordAccountListDialog");
    }

    public interface onLoginAccountChange{

        /**
         * 选择登录类型
         * @param accountInfo
         */
        void onAccountInfo(String accountInfo);

    }

}

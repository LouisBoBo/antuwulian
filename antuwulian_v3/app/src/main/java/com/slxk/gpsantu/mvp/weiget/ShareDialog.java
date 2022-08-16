package com.slxk.gpsantu.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.model.bean.ShareBean;
import com.slxk.gpsantu.mvp.ui.adapter.ShareAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享dialog
 */
public class ShareDialog extends DialogFragment {

    private GridView gridView;
    private TextView tvCancel; // 取消

    private List<ShareBean> shareBeans;
    private ShareAdapter mAdapter;
    private onShareChange shareChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_share, null);
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

        shareBeans = new ArrayList<>();
        shareBeans.add(new ShareBean(0, R.drawable.icon_wechat_friends, getString(R.string.txt_wechat)));

        gridView = dialog.findViewById(R.id.gridView);
        tvCancel = dialog.findViewById(R.id.tv_cancel);

        mAdapter = new ShareAdapter(getContext(), R.layout.item_share, shareBeans);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (shareChange != null){
                    shareChange.onShare(shareBeans.get(position).getId());
                }
                dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, onShareChange change){
        if (isAdded()){
            dismiss();
        }
        this.shareChange = change;
        super.show(manager, "ShareDialog");
    }

    public interface onShareChange{

        /**
         * 分享
         * @param id
         */
        void onShare(int id);

    }

}

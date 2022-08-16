package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerSimDetailInfoComponent;
import com.slxk.gpsantu.mvp.contract.SimDetailInfoContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.SimDetailResultBean;
import com.slxk.gpsantu.mvp.presenter.SimDetailInfoPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppDialog;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2021 17:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SimDetailInfoActivity extends BaseActivity<SimDetailInfoPresenter> implements SimDetailInfoContract.View {

    @BindView(R.id.btn_recharge)
    Button btnRecharge; // 充值
    @BindView(R.id.tv_flow)
    TextView tvRemainingFlow; // 剩余流量
    @BindView(R.id.tv_total_flow)
    TextView tvFlowTotal; // 总流量
    @BindView(R.id.tv_use_flow)
    TextView tvFlowUse; // 已用流量
    @BindView(R.id.tv_total_sms)
    TextView tvSmsTotal; // 总短信
    @BindView(R.id.tv_use_sms)
    TextView tvSmsUse; // 已用短信
//    @BindView(R.id.tv_remain_sms)
//    TextView tvSmsSurplus; //剩余短信
    @BindView(R.id.tv_state)
    TextView tvStatus; // 卡在线状态 在线、离线、关机
    @BindView(R.id.tv_balance)
    TextView tvBalance; // 余额
    @BindView(R.id.tv_sim_state)
    TextView tvSimStatus; // sim
    @BindView(R.id.tv_iccid)
    TextView tvIccidNumber; // iccid
    @BindView(R.id.tv_sim_no)
    TextView tvSimNumber; // sim卡号
    @BindView(R.id.tv_open_time)
    TextView tvActivationTime; // 激活时间
    @BindView(R.id.tv_close_time)
    TextView tvExpireDate; // 到期时间

    private String mIccid; // iccid号
    private boolean isRecharge = false; // 是否可充值

    private final static String Detail_Info = "DetailInfo";
    public static Intent newInstance(SimDetailResultBean simDetailResultBean) {
        Intent intent = new Intent(MyApplication.getMyApp(), SimDetailInfoActivity.class);
        intent.putExtra(Detail_Info, simDetailResultBean);
        return intent;
    }
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSimDetailInfoComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sim_detail_info;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_sim));
        SimDetailResultBean simDetailResultBean = (SimDetailResultBean)getIntent().getSerializableExtra(Detail_Info);
        if (simDetailResultBean != null)
            showDetailView(simDetailResultBean);
    }

    @SuppressLint("SetTextI18n")
    private void showDetailView(SimDetailResultBean simDetailResultBean){
        mIccid = simDetailResultBean.getIccid();
        isRecharge = simDetailResultBean.isIs_recharge();
        tvRemainingFlow.setText(Utils.formatValue((double) simDetailResultBean.getLeft_flow() / 100) + "");
        tvFlowUse.setText(Utils.formatValue((double) simDetailResultBean.getShow_flow() / 100) + "M");
        tvFlowTotal.setText(Utils.formatValue((double) simDetailResultBean.getTflow() / 100) + "M");
        if (simDetailResultBean.getDstate() != null && simDetailResultBean.getDstate().length() > 0) {
            tvSimStatus.setText(simDetailResultBean.getDstate());
        }
        int remSms = simDetailResultBean.getSsms() - simDetailResultBean.getRsms(); //剩余短信
        tvSmsTotal.setText(remSms + getString(R.string.txt_sms_unit));
        tvSmsUse.setText(simDetailResultBean.getRsms() + getString(R.string.txt_sms_unit));

        if (simDetailResultBean.getStatus() != null && simDetailResultBean.getStatus().length() > 0) {
            tvStatus.setText(simDetailResultBean.getStatus());
        }
        tvIccidNumber.setText(simDetailResultBean.getIccid());
        tvSimNumber.setText(simDetailResultBean.getPhone());
        tvActivationTime.setText(simDetailResultBean.getSperiod());
        tvExpireDate.setText(simDetailResultBean.getEperiod());
        if (!isRecharge) { // 不可充值就隐藏充值按钮
            btnRecharge.setVisibility(View.GONE);
        }
    }

    @OnClick({ R.id.btn_recharge,R.id.txt_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge: // 充值
                onSimRecharge();
                break;
            case R.id.txt_copy:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getString(R.string.txt_status)).append(tvStatus.getText().toString()).append("\n")
                        .append(getString(R.string.txt_balance)).append(tvBalance.getText().toString()).append("\n")
                        .append(getString(R.string.txt_sim_status)).append(tvSimStatus.getText().toString()).append("\n")
                        .append(getString(R.string.txt_iccid)).append(tvIccidNumber.getText().toString()).append("\n")
                        .append(getString(R.string.txt_sim_number)).append(tvSimNumber.getText().toString()).append("\n")
                        .append(getString(R.string.txt_activation_time)).append(tvActivationTime.getText().toString()).append("\n")
                        .append(getString(R.string.txt_expire_date)).append(tvExpireDate.getText().toString());
                onCopyToClipboard(stringBuilder.toString());
        }
    }

    /**
     * 跳转充值页面
     */
    private void onSimRecharge() {
        if (isRecharge) {
            launchActivity(PayWebViewActivity.newInstance(0, getString(R.string.txt_sim_recharge), Api.Pay_Sim_Recharge + ConstantValue.getPaySimRechargeValue(mIccid)));
        } else {
            ToastUtils.showShort(getString(R.string.txt_sim_recharge_error));
        }
    }

    /**
     * 复制文本到粘贴管理器
     *
     * @param content 复制内容
     */
    private void onCopyToClipboard(String content) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）,其他的还有
        ClipData clipData = ClipData.newPlainText("Antrip", content);

        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
        ToastUtils.showShort(getString(R.string.txt_copy_success));
    }
    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}

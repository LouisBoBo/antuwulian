package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerAlarmSettingNewComponent;
import com.slxk.gpsantu.mvp.contract.AlarmSettingNewContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.DataCenterBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceConfigResultBean;
import com.slxk.gpsantu.mvp.model.bean.SetConfigResultBean;
import com.slxk.gpsantu.mvp.model.bean.ShakeValueBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceConfigPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceConfigSetPutBean;
import com.slxk.gpsantu.mvp.presenter.AlarmSettingNewPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.adapter.ShakeSensitivityAdapter;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.slxk.gpsantu.mvp.utils.Utils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:报警消息设置新交互
 * <p>
 * Created by MVPArmsTemplate on 05/19/2022 09:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmSettingNewActivity extends BaseActivity<AlarmSettingNewPresenter> implements AlarmSettingNewContract.View {


    @BindView(R.id.gridView_shake)
    GridView gridViewShake; // 震动报警
    @BindView(R.id.rl_shake_alarm)
    RelativeLayout rlShakeAlarm; // 震动报警
    @BindView(R.id.iv_sms_shake_alarm)
    ImageView ivSMSShakeAlarm;
    @BindView(R.id.iv_phone_shake_alarm)
    ImageView ivPhoneShakeAlarm;
    @BindView(R.id.iv_app_shake_alarm)
    ImageView ivAppShakeAlarm;
    @BindView(R.id.tv_shake_alarm)
    TextView tvShakeAlarm;
    @BindView(R.id.tv_sms_shake_alarm)
    TextView tv_sms_shake_alarm;
    @BindView(R.id.tv_phone_shake_alarm)
    TextView tv_phone_shake_alarm;
    @BindView(R.id.tv_app_shake_alarm)
    TextView tv_app_shake_alarm;


    @BindView(R.id.edt_speed)
    EditText edtSpeed; // 超速报警值
    @BindView(R.id.rl_over_speed_alarm)
    RelativeLayout rlOverSpeedAlarm; // 超速报警
    @BindView(R.id.iv_sms_over_speed)
    ImageView ivSMSOverSpeed;
    @BindView(R.id.iv_phone_over_speed)
    ImageView ivPhoneOverSpeed;
    @BindView(R.id.iv_app_over_speed)
    ImageView ivAppOverSpeed;
    @BindView(R.id.tv_over_speed_alarm)
    TextView tvOverSpeed;
    @BindView(R.id.tv_sms_over_speed)
    TextView tv_sms_over_speed;
    @BindView(R.id.tv_phone_over_speed)
    TextView tv_phone_over_speed;
    @BindView(R.id.tv_app_over_speed)
    TextView tv_app_over_speed;

    @BindView(R.id.rl_off_alarm)
    RelativeLayout rlOffAlarm; // 脱落报警
    @BindView(R.id.iv_sms_off_alarm)
    ImageView ivSMSOffAlarm;
    @BindView(R.id.iv_phone_off_alarm)
    ImageView ivPhoneOffAlarm;
    @BindView(R.id.iv_app_off_alarm)
    ImageView ivAppOffAlarm;
    @BindView(R.id.tv_off_alarm)
    TextView tvOffAlarm;
    @BindView(R.id.tv_sms_off_alarm)
    TextView tv_sms_off_alarm;
    @BindView(R.id.tv_phone_off_alarm)
    TextView tv_phone_off_alarm;
    @BindView(R.id.tv_app_off_alarm)
    TextView tv_app_off_alarm;

    @BindView(R.id.rl_low_power_alarm)
    RelativeLayout rlLowPowerAlarm; // 低电报警
    @BindView(R.id.iv_sms_low_power_alarm)
    ImageView ivSMSLowPowerAlarm;
    @BindView(R.id.iv_phone_low_power_alarm)
    ImageView ivPhonePowerAlarm;
    @BindView(R.id.iv_app_low_power_alarm)
    ImageView ivAppPowerAlarm;
    @BindView(R.id.tv_low_power_alarm)
    TextView tvPowerAlarm;
    @BindView(R.id.tv_sms_low_power_alarm)
    TextView tv_sms_low_power_alarm;
    @BindView(R.id.tv_phone_low_power_alarm)
    TextView tv_phone_low_power_alarm;
    @BindView(R.id.tv_app_low_power_alarm)
    TextView tv_app_low_power_alarm;

    @BindView(R.id.rl_fence_alarm)
    RelativeLayout rlFenceAlarm; // 进出围栏报警
    @BindView(R.id.iv_sms_fence_alarm)
    ImageView ivSMSFenceAlarm;
    @BindView(R.id.iv_phone_fence_alarm)
    ImageView ivPhoneFenceAlarm;
    @BindView(R.id.iv_app_fence_alarm)
    ImageView ivAppFenceAlarm;
    @BindView(R.id.tv_fence_alarm)
    TextView tvFenceAlarm;
    @BindView(R.id.tv_sms_fence_alarm)
    TextView tv_sms_fence_alarm;
    @BindView(R.id.tv_phone_fence_alarm)
    TextView tv_phone_fence_alarm;
    @BindView(R.id.tv_app_fence_alarm)
    TextView tv_app_fence_alarm;

    @BindView(R.id.iv_sounds_witch)
    ImageView ivSoundsWitch; // 声音开关
    @BindView(R.id.rl_sounds_witch)
    RelativeLayout rlSoundsWitch; // 声音开关

    @BindView(R.id.iv_indicator_light_alarm)
    ImageView ivIndicatorLightAlarm; // 指示灯开关
    @BindView(R.id.rl_indicator_light_alarm)
    RelativeLayout rlLightAlarm; // 指示灯开关

    @BindView(R.id.rl_offline_alarm)
    RelativeLayout rlOfflineAlarm; // 离线报警
    @BindView(R.id.iv_sms_offline_alarm)
    ImageView ivSMSOfflineAlarm;
    @BindView(R.id.iv_phone_offline_alarm)
    ImageView ivPhoneOfflineAlarm;
    @BindView(R.id.iv_app_offline_alarm)
    ImageView ivAppOfflineAlarm;
    @BindView(R.id.tv_offline_alarm)
    TextView tvOfflineAlarm;
    @BindView(R.id.tv_sms_offline_alarm)
    TextView tv_sms_offline_alarm;
    @BindView(R.id.tv_phone_offline_alarm)
    TextView tv_phone_offline_alarm;
    @BindView(R.id.tv_app_offline_alarm)
    TextView tv_app_offline_alarm;

    @BindView(R.id.rl_shutdown_alarm)
    RelativeLayout rlShutdownAlarm; // 关机报警
    @BindView(R.id.iv_sms_shutdown_alarm)
    ImageView ivSMSShutdownAlarm;
    @BindView(R.id.iv_phone_shutdown_alarm)
    ImageView ivPhoneShutdownAlarm;
    @BindView(R.id.iv_app_shutdown_alarm)
    ImageView ivAppShutdownAlarm;
    @BindView(R.id.tv_shutdown_alarm)
    TextView tvShutdownAlarm;
    @BindView(R.id.tv_sms_shutdown_alarm)
    TextView tv_sms_shutdown_alarm;
    @BindView(R.id.tv_phone_shutdown_alarm)
    TextView tv_phone_shutdown_alarm;
    @BindView(R.id.tv_app_shutdown_alarm)
    TextView tv_app_shutdown_alarm;

    @BindView(R.id.rl_reboot_alarm)
    RelativeLayout rlRebootAlarm; // 启动报警
    @BindView(R.id.iv_sms_reboot_alarm)
    ImageView ivSMSRebootAlarm;
    @BindView(R.id.iv_phone_reboot_alarm)
    ImageView ivPhoneRebootAlarm;
    @BindView(R.id.iv_app_reboot_alarm)
    ImageView ivAppRebootAlarm;
    @BindView(R.id.tv_reboot_alarm)
    TextView tvRebootAlarm;
    @BindView(R.id.tv_sms_reboot_alarm)
    TextView tv_sms_reboot_alarm;
    @BindView(R.id.tv_phone_reboot_alarm)
    TextView tv_phone_reboot_alarm;
    @BindView(R.id.tv_app_reboot_alarm)
    TextView tv_app_reboot_alarm;

    @BindView(R.id.rl_sos_alarm)
    RelativeLayout rlSOSAlarm; // SOS报警
    @BindView(R.id.iv_sms_sos_alarm)
    ImageView ivSMSSOSAlarm;
    @BindView(R.id.iv_phone_sos_alarm)
    ImageView ivPhoneSOSAlarm;
    @BindView(R.id.iv_app_sos_alarm)
    ImageView ivAppSOSAlarm;
    @BindView(R.id.tv_sos_alarm)
    TextView tvSOSAlarm;
    @BindView(R.id.tv_sms_sos_alarm)
    TextView tv_sms_sos_alarm;
    @BindView(R.id.tv_phone_sos_alarm)
    TextView tv_phone_sos_alarm;
    @BindView(R.id.tv_app_sos_alarm)
    TextView tv_app_sos_alarm;

    @BindView(R.id.btn_save)
    Button btnSave; // 保存
    @BindView(R.id.ll_speed_value)
    LinearLayout llSpeedValue; // 超速报警值
    @BindView(R.id.ll_other_set)
    LinearLayout llOtherSet;// 其他设置
    @BindView(R.id.edt_mobile)
    EditText edtMobile; // 短信报警、电话报警电话
    @BindView(R.id.ll_increment)
    LinearLayout llIncrement;
    @BindView(R.id.tv_phone_tip)
    TextView tvPhoneTip;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;


    private List<ShakeValueBean> shakeValueBeans;
    private ShakeSensitivityAdapter mAdapter;

    private String mSimei;
    private List<String> mSimeiBeas;

    // 获取到的报警开关信息
    private int mLowpower; // 低电报警,0关闭，1开启，-1不支持
    private int mIndicatorlight; // 指示灯 0关闭，1开启，-1不支持
    private int mSoundswitch; // 声音提示开关 0关闭，1开启，-1不支持
    private int mOpenswitch; // 开机报警 启动报警 0关闭，1开启，-1不支持
    private int mOffswitch; // 离线报警0关闭，1开启，-1不支持
    private int mCloseswitch; // 关机报警0关闭，1开启，-1不支持
    private int mDropalarm; // 脱落报警0关闭，1开启，-1不支持
    private int mFenceswitch; // 围栏开关 0关闭，1开启，-1不支持
    private int mShakeswitch;// 震动报警开关
    private int mSpeedswitch;// 超速报警开关
    private int mSOSswitch;// SOS报警开关
    private int mShakealue; // 震动报警程度0-关闭,(18,28,48)开启，-1不支持,
    private int mSpeedValue; // 超速阈值 0关闭，>0开启，-1不支持
    private String phoneNum = ""; //短信、电话报警电话号码

    // 用户设置的报警开关信息
    private int mLowpowerSet; // 低电报警,0关闭，1开启，-1不支持
    private int mIndicatorlightSet; // 指示灯 0关闭，1开启，-1不支持
    private int mSoundswitchSet; // 声音提示开关 0关闭，1开启，-1不支持
    private int mOpenswitchSet; // 开机报警 启动报警 0关闭，1开启，-1不支持
    private int mOffswitchSet; // 离线报警0关闭，1开启，-1不支持
    private int mCloseswitchSet; // 关机报警0关闭，1开启，-1不支持
    private int mDropalarmSet; // 脱落报警0关闭，1开启，-1不支持
    private int mFenceswitchSet; // 围栏开关 0关闭，1开启，-1不支持
    private int mShakeswitchSet;// 震动报警开关
    private int mSpeedswitchSet;// 超速报警开关
    private int mSOSswitchSet;// SOS报警开关
    private int mShakealueSet; // 震动报警程度0-关闭,(18,28,48)开启，-1不支持,
    private int mSpeedValueSet; // 超速阈值 0关闭，>0开启，-1不支持
    private String phoneNumSet = ""; //短信、电话报警电话号码

    private boolean app_offline_alarm, sms_offline_alarm, phone_offline_alarm;
    private boolean app_reboot_alarm, sms_reboot_alarm, phone_reboot_alarm;
    private boolean app_shutdown_alarm, sms_shutdown_alarm, phone_shutdown_alarm;
    private boolean app_fence_alarm, sms_fence_alarm, phone_fence_alarm;
    private boolean app_low_power_alarm, sms_low_power_alarm, phone_low_power_alarm;
    private boolean app_off_alarm, sms_off_alarm, phone_off_alarm;
    private boolean app_over_speed, sms_over_speed, phone_over_speed;
    private boolean app_shake_alarm, sms_shake_alarm, phone_shake_alarm;
    private boolean app_sos_alarm, sms_sos_alarm, phone_sos_alarm;
    private boolean isSupportIncrement = true; //是否支持增值服务,默认支持

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), AlarmSettingNewActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmSettingNewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm_setting_new; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_message_setting));
        List<Integer> list = MyApplication.getMyApp().getIncrement();
        if (list == null || list.size() == 0) { //不支持则隐藏入口
            isSupportIncrement = false;
            llIncrement.setVisibility(View.GONE);
        }
        shakeValueBeans = new ArrayList<>();
        mSimeiBeas = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();
        shakeValueBeans.add(new ShakeValueBean(18, getString(R.string.txt_higher), false));
        shakeValueBeans.add(new ShakeValueBean(28, getString(R.string.txt_medium), false));
        shakeValueBeans.add(new ShakeValueBean(48, getString(R.string.txt_inferior), false));
        mAdapter = new ShakeSensitivityAdapter(this, R.layout.item_shake_sensitivity, shakeValueBeans);
        gridViewShake.setAdapter(mAdapter);
        gridViewShake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (ShakeValueBean bean : shakeValueBeans) {
                    bean.setSelect(false);
                }
                shakeValueBeans.get(position).setSelect(true);
                mAdapter.notifyDataSetChanged();
                mShakealueSet = shakeValueBeans.get(position).getSensitivity();
            }
        });

        if (!TextUtils.isEmpty(mSimei)) {
            mSimeiBeas.add(mSimei);
        }
        getDeviceConfig();

    }

    /**
     * 获取设备的配置信息，支持的功能等
     */
    private void getDeviceConfig() {
        DeviceConfigPutBean.ParamsBean paramsBean = new DeviceConfigPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setType(ResultDataUtils.Config_Alarm);

        DeviceConfigPutBean bean = new DeviceConfigPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Get);
        bean.setModule(ModuleValueService.Module_For_Config_Get);

        if (getPresenter() != null) {
            getPresenter().getDeviceConfig(bean);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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


    @OnClick({R.id.iv_sms_shake_alarm, R.id.tv_sms_shake_alarm, R.id.iv_phone_shake_alarm, R.id.tv_phone_shake_alarm, R.id.iv_app_shake_alarm, R.id.tv_app_shake_alarm,
            R.id.iv_sms_over_speed, R.id.tv_sms_over_speed, R.id.iv_phone_over_speed, R.id.tv_phone_over_speed, R.id.iv_app_over_speed, R.id.tv_app_over_speed,
            R.id.iv_sms_off_alarm, R.id.tv_sms_off_alarm, R.id.iv_phone_off_alarm, R.id.tv_phone_off_alarm, R.id.iv_app_off_alarm, R.id.tv_app_off_alarm,
            R.id.iv_sms_low_power_alarm, R.id.tv_sms_low_power_alarm, R.id.iv_phone_low_power_alarm, R.id.tv_phone_low_power_alarm, R.id.iv_app_low_power_alarm, R.id.tv_app_low_power_alarm,
            R.id.iv_sms_fence_alarm, R.id.tv_sms_fence_alarm, R.id.iv_phone_fence_alarm, R.id.tv_phone_fence_alarm, R.id.iv_app_fence_alarm, R.id.tv_app_fence_alarm,
            R.id.iv_sms_shutdown_alarm, R.id.tv_sms_shutdown_alarm, R.id.iv_phone_shutdown_alarm, R.id.tv_phone_shutdown_alarm, R.id.iv_app_shutdown_alarm, R.id.tv_app_shutdown_alarm,
            R.id.iv_sms_reboot_alarm, R.id.tv_sms_reboot_alarm, R.id.iv_phone_reboot_alarm, R.id.tv_phone_reboot_alarm, R.id.iv_app_reboot_alarm, R.id.tv_app_reboot_alarm,
            R.id.iv_sms_offline_alarm, R.id.tv_sms_offline_alarm, R.id.iv_phone_offline_alarm, R.id.tv_phone_offline_alarm, R.id.iv_app_offline_alarm, R.id.tv_app_offline_alarm,
            R.id.iv_sms_sos_alarm, R.id.tv_sms_sos_alarm, R.id.iv_phone_sos_alarm, R.id.tv_phone_sos_alarm, R.id.iv_app_sos_alarm, R.id.tv_app_sos_alarm,
            R.id.iv_sounds_witch, R.id.iv_indicator_light_alarm,
            R.id.ll_increment, R.id.tv_fence_alarm_set, R.id.btn_save})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.ll_increment:
                    launchActivity(PayWebViewActivity.newInstance(2, getString(R.string.menu_8), Api.Pay_Increment_Recharge + ConstantValue.getPayIncrementValue()));
                    break;
                case R.id.tv_fence_alarm_set:
                    launchActivity(FenceActivity.newInstance());
                    break;

                case R.id.iv_sms_offline_alarm:
                case R.id.tv_sms_offline_alarm:
                    sms_offline_alarm = !sms_offline_alarm;
                    setViewChange(ivSMSOfflineAlarm, tv_sms_offline_alarm, sms_offline_alarm);
                    setSwitchChange(tvOfflineAlarm, app_offline_alarm, sms_offline_alarm, phone_offline_alarm);
                    break;
                case R.id.iv_phone_offline_alarm:
                case R.id.tv_phone_offline_alarm:
                    phone_offline_alarm = !phone_offline_alarm;
                    setViewChange(ivPhoneOfflineAlarm, tv_phone_offline_alarm, phone_offline_alarm);
                    setSwitchChange(tvOfflineAlarm, app_offline_alarm, sms_offline_alarm, phone_offline_alarm);
                    break;
                case R.id.iv_app_offline_alarm:
                case R.id.tv_app_offline_alarm:
                    app_offline_alarm = !app_offline_alarm;
                    setViewChange(ivAppOfflineAlarm, tv_app_offline_alarm, app_offline_alarm);
                    setSwitchChange(tvOfflineAlarm, app_offline_alarm, sms_offline_alarm, phone_offline_alarm);
                    break;

                case R.id.iv_sms_reboot_alarm:
                case R.id.tv_sms_reboot_alarm:
                    sms_reboot_alarm = !sms_reboot_alarm;
                    setViewChange(ivSMSRebootAlarm, tv_sms_reboot_alarm, sms_reboot_alarm);
                    setSwitchChange(tvRebootAlarm, app_reboot_alarm, sms_reboot_alarm, phone_reboot_alarm);
                    break;
                case R.id.iv_phone_reboot_alarm:
                case R.id.tv_phone_reboot_alarm:
                    phone_reboot_alarm = !phone_reboot_alarm;
                    setViewChange(ivPhoneRebootAlarm, tv_phone_reboot_alarm, phone_reboot_alarm);
                    setSwitchChange(tvRebootAlarm, app_reboot_alarm, sms_reboot_alarm, phone_reboot_alarm);
                    break;
                case R.id.iv_app_reboot_alarm:
                case R.id.tv_app_reboot_alarm:
                    app_reboot_alarm = !app_reboot_alarm;
                    setViewChange(ivAppRebootAlarm, tv_app_reboot_alarm, app_reboot_alarm);
                    setSwitchChange(tvRebootAlarm, app_reboot_alarm, sms_reboot_alarm, phone_reboot_alarm);
                    break;

                case R.id.iv_sms_shutdown_alarm:
                case R.id.tv_sms_shutdown_alarm:
                    sms_shutdown_alarm = !sms_shutdown_alarm;
                    setViewChange(ivSMSShutdownAlarm, tv_sms_shutdown_alarm, sms_shutdown_alarm);
                    setSwitchChange(tvShutdownAlarm, app_shutdown_alarm, sms_shutdown_alarm, phone_shutdown_alarm);
                    break;
                case R.id.iv_phone_shutdown_alarm:
                case R.id.tv_phone_shutdown_alarm:
                    phone_shutdown_alarm = !phone_shutdown_alarm;
                    setViewChange(ivPhoneShutdownAlarm, tv_phone_shutdown_alarm, phone_shutdown_alarm);
                    setSwitchChange(tvShutdownAlarm, app_shutdown_alarm, sms_shutdown_alarm, phone_shutdown_alarm);
                    break;
                case R.id.iv_app_shutdown_alarm:
                case R.id.tv_app_shutdown_alarm:
                    app_shutdown_alarm = !app_shutdown_alarm;
                    setViewChange(ivAppShutdownAlarm, tv_app_shutdown_alarm, app_shutdown_alarm);
                    setSwitchChange(tvShutdownAlarm, app_shutdown_alarm, sms_shutdown_alarm, phone_shutdown_alarm);
                    break;

                case R.id.iv_sms_fence_alarm:
                case R.id.tv_sms_fence_alarm:
                    sms_fence_alarm = !sms_fence_alarm;
                    setViewChange(ivSMSFenceAlarm, tv_sms_fence_alarm, sms_fence_alarm);
                    setSwitchChange(tvFenceAlarm, app_fence_alarm, sms_fence_alarm, phone_fence_alarm);
                    break;
                case R.id.iv_phone_fence_alarm:
                case R.id.tv_phone_fence_alarm:
                    phone_fence_alarm = !phone_fence_alarm;
                    setViewChange(ivPhoneFenceAlarm, tv_phone_fence_alarm, phone_fence_alarm);
                    setSwitchChange(tvFenceAlarm, app_fence_alarm, sms_fence_alarm, phone_fence_alarm);
                    break;
                case R.id.iv_app_fence_alarm:
                case R.id.tv_app_fence_alarm:
                    app_fence_alarm = !app_fence_alarm;
                    setViewChange(ivAppFenceAlarm, tv_app_fence_alarm, app_fence_alarm);
                    setSwitchChange(tvFenceAlarm, app_fence_alarm, sms_fence_alarm, phone_fence_alarm);
                    break;

                case R.id.iv_sms_low_power_alarm:
                case R.id.tv_sms_low_power_alarm:
                    sms_low_power_alarm = !sms_low_power_alarm;
                    setViewChange(ivSMSLowPowerAlarm, tv_sms_low_power_alarm, sms_low_power_alarm);
                    setSwitchChange(tvPowerAlarm, app_low_power_alarm, sms_low_power_alarm, phone_low_power_alarm);
                    break;
                case R.id.iv_phone_low_power_alarm:
                case R.id.tv_phone_low_power_alarm:
                    phone_low_power_alarm = !phone_low_power_alarm;
                    setViewChange(ivPhonePowerAlarm, tv_phone_low_power_alarm, phone_low_power_alarm);
                    setSwitchChange(tvPowerAlarm, app_low_power_alarm, sms_low_power_alarm, phone_low_power_alarm);
                    break;
                case R.id.iv_app_low_power_alarm:
                case R.id.tv_app_low_power_alarm:
                    app_low_power_alarm = !app_low_power_alarm;
                    setViewChange(ivAppPowerAlarm, tv_app_low_power_alarm, app_low_power_alarm);
                    setSwitchChange(tvPowerAlarm, app_low_power_alarm, sms_low_power_alarm, phone_low_power_alarm);
                    break;

                case R.id.iv_sms_off_alarm:
                case R.id.tv_sms_off_alarm:
                    sms_off_alarm = !sms_off_alarm;
                    setViewChange(ivSMSOffAlarm, tv_sms_off_alarm, sms_off_alarm);
                    setSwitchChange(tvOffAlarm, app_off_alarm, sms_off_alarm, phone_off_alarm);
                    break;
                case R.id.iv_phone_off_alarm:
                case R.id.tv_phone_off_alarm:
                    phone_off_alarm = !phone_off_alarm;
                    setViewChange(ivPhoneOffAlarm, tv_phone_off_alarm, phone_off_alarm);
                    setSwitchChange(tvOffAlarm, app_off_alarm, sms_off_alarm, phone_off_alarm);
                    break;
                case R.id.iv_app_off_alarm:
                case R.id.tv_app_off_alarm:
                    app_off_alarm = !app_off_alarm;
                    setViewChange(ivAppOffAlarm, tv_app_off_alarm, app_off_alarm);
                    setSwitchChange(tvOffAlarm, app_off_alarm, sms_off_alarm, phone_off_alarm);
                    break;

                case R.id.iv_sms_over_speed:
                case R.id.tv_sms_over_speed:
                    sms_over_speed = !sms_over_speed;
                    setViewChange(ivSMSOverSpeed, tv_sms_over_speed, sms_over_speed);
                    setSwitchOverSpeedChange(tvOverSpeed, app_over_speed, sms_over_speed, phone_over_speed);
                    break;
                case R.id.iv_phone_over_speed:
                case R.id.tv_phone_over_speed:
                    phone_over_speed = !phone_over_speed;
                    setViewChange(ivPhoneOverSpeed, tv_phone_over_speed, phone_over_speed);
                    setSwitchOverSpeedChange(tvOverSpeed, app_over_speed, sms_over_speed, phone_over_speed);
                    break;
                case R.id.iv_app_over_speed:
                case R.id.tv_app_over_speed:
                    app_over_speed = !app_over_speed;
                    setViewChange(ivAppOverSpeed, tv_app_over_speed, app_over_speed);
                    setSwitchOverSpeedChange(tvOverSpeed, app_over_speed, sms_over_speed, phone_over_speed);
                    break;
                case R.id.iv_sms_shake_alarm:
                case R.id.tv_sms_shake_alarm:
                    sms_shake_alarm = !sms_shake_alarm;
                    setViewChange(ivSMSShakeAlarm, tv_sms_shake_alarm, sms_shake_alarm);
                    setSwitchShakeChange(tvShakeAlarm, app_shake_alarm, sms_shake_alarm, phone_shake_alarm);
                    break;
                case R.id.iv_phone_shake_alarm:
                case R.id.tv_phone_shake_alarm:
                    phone_shake_alarm = !phone_shake_alarm;
                    setViewChange(ivPhoneShakeAlarm, tv_phone_shake_alarm, phone_shake_alarm);
                    setSwitchShakeChange(tvShakeAlarm, app_shake_alarm, sms_shake_alarm, phone_shake_alarm);
                    break;
                case R.id.iv_app_shake_alarm:
                case R.id.tv_app_shake_alarm:
                    app_shake_alarm = !app_shake_alarm;
                    setViewChange(ivAppShakeAlarm, tv_app_shake_alarm, app_shake_alarm);
                    setSwitchShakeChange(tvShakeAlarm, app_shake_alarm, sms_shake_alarm, phone_shake_alarm);
                    break;

                case R.id.iv_sms_sos_alarm:
                case R.id.tv_sms_sos_alarm:
                    sms_sos_alarm = !sms_sos_alarm;
                    setViewChange(ivSMSSOSAlarm, tv_sms_sos_alarm, sms_sos_alarm);
                    setSwitchChange(tvSOSAlarm, app_sos_alarm, sms_sos_alarm, phone_sos_alarm);
                    break;
                case R.id.iv_phone_sos_alarm:
                case R.id.tv_phone_sos_alarm:
                    phone_sos_alarm = !phone_sos_alarm;
                    setViewChange(ivPhoneSOSAlarm, tv_phone_sos_alarm, phone_sos_alarm);
                    setSwitchChange(tvSOSAlarm, app_sos_alarm, sms_sos_alarm, phone_sos_alarm);
                    break;
                case R.id.iv_app_sos_alarm: // sos 暂不支持app设置
                case R.id.tv_app_sos_alarm:
                    break;
                case R.id.iv_sounds_witch: // 声音开关
                    setSoundsWitchValue();
                    break;
                case R.id.iv_indicator_light_alarm: // 指示灯开关
                    setIndicatorLightAlarm();
                    break;
                case R.id.btn_save: // 保存
                    onSetAlarmSave();
                    break;
            }
        }
    }

    /**
     * 设置声音开关报警
     */
    private void setSoundsWitchValue() {
        if (mSoundswitchSet > 0) {
            mSoundswitchSet = 0;
        } else {
            mSoundswitchSet = 1;
        }
        ivSoundsWitch.setImageResource(mSoundswitchSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置指示灯
     */
    private void setIndicatorLightAlarm() {
        if (mIndicatorlightSet > 0) {
            mIndicatorlightSet = 0;
        } else {
            mIndicatorlightSet = 1;
        }
        ivIndicatorLightAlarm.setImageResource(mIndicatorlightSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    private void setSwitchChange(TextView tv, boolean app, boolean sms, boolean phone) {
        if (!app && !sms && !phone) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }
    }

    private void setSwitchOverSpeedChange(TextView tv, boolean app, boolean sms, boolean phone) {
        if (!app && !sms && !phone) {
            tv.setVisibility(View.GONE);
            mSpeedValueSet = 0;
            edtSpeed.setText("");
            llSpeedValue.setVisibility(View.INVISIBLE);
        } else {
            String speed = edtSpeed.getText().toString().trim();
            if (TextUtils.isEmpty(speed)) {
                mSpeedValueSet = 120;
            } else {
                mSpeedValueSet = Integer.parseInt(speed);
            }
            edtSpeed.setText(String.valueOf(mSpeedValueSet));
            tv.setVisibility(View.VISIBLE);
            llSpeedValue.setVisibility(View.VISIBLE);
        }
    }

    private void setSwitchShakeChange(TextView tv, boolean app, boolean sms, boolean phone) {
        if (!app && !sms && !phone) {
            tv.setVisibility(View.GONE);
            mShakealueSet = 0;
//            gridViewShake.setVisibility(View.INVISIBLE);
        } else {
            tv.setVisibility(View.VISIBLE);
            if (mShakealueSet == 0) {
                //先复原都未选状态
                for (ShakeValueBean bean : shakeValueBeans) {
                    bean.setSelect(false);
                }
                mShakealueSet = 28;
            }
            for (ShakeValueBean bean : shakeValueBeans) {
                if (bean.getSensitivity() == mShakealueSet) {
                    bean.setSelect(true);
                }
            }
            mAdapter.notifyDataSetChanged();
//            gridViewShake.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置震动报警
     */
    private void setShakeValue() {
        //先复原都未选状态
        for (ShakeValueBean bean : shakeValueBeans) {
            bean.setSelect(false);
        }
        if (mShakealueSet > 0) {
            mShakealueSet = 0;
        } else {
            mShakealueSet = 28;
        }
        for (ShakeValueBean bean : shakeValueBeans) {
            if (bean.getSensitivity() == mShakealueSet) {
                bean.setSelect(true);
            }
        }
        mAdapter.notifyDataSetChanged();
//        gridViewShake.setVisibility(mShakealueSet > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void setViewChange(ImageView iv, TextView tv, boolean isSelect) {
        if (isSelect) {
            iv.setImageResource(R.drawable.icon_select);
            tv.setTextColor(getResources().getColor(R.color.color_2E7BEC));
        } else {
            iv.setImageResource(R.drawable.icon_unselected);
            tv.setTextColor(getResources().getColor(R.color.color_B3B3B3));
        }
    }


    /**
     * 保存报警消息设置
     */
    private void onSetAlarmSave() {
        String speed = edtSpeed.getText().toString().trim();
        if (TextUtils.isEmpty(speed)) {
            mSpeedValueSet = 0;
        } else {
            mSpeedValueSet = Integer.parseInt(speed);
        }

        if (!app_over_speed && !sms_over_speed && !phone_over_speed) {
            mSpeedValueSet = 0;
        } else { //app 、短信 、电话报警  至少是选中了一个
            if (mSpeedValueSet < 10 || mSpeedValueSet > 190) {
                ToastUtils.showShort(getString(R.string.txt_over_speed_alarm_hint));
                return;
            }
        }

        DeviceConfigSetPutBean.ParamsBean.ConfigBean.CarSwitchBean carSwitchBean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean.CarSwitchBean();
        if (mLowpowerSet != -1) {
            mLowpowerSet = getSetValue(app_low_power_alarm, sms_low_power_alarm, phone_low_power_alarm);
            if (mLowpowerSet != mLowpower) {
                carSwitchBean.setLowpower(mLowpowerSet);
            }
        }

        if (mIndicatorlightSet != mIndicatorlight) {
            carSwitchBean.setIndicatorlight(mIndicatorlightSet);
        }
        if (mSoundswitchSet != mSoundswitch) {
            carSwitchBean.setSoundswitch(mSoundswitchSet);
        }

        if (mOpenswitchSet != -1) {
            mOpenswitchSet = getSetValue(app_reboot_alarm, sms_reboot_alarm, phone_reboot_alarm);
            if (mOpenswitchSet != mOpenswitch) {
                carSwitchBean.setOpenswitch(mOpenswitchSet);
            }
        }

        if (mOffswitchSet != -1) {
            mOffswitchSet = getSetValue(app_offline_alarm, sms_offline_alarm, phone_offline_alarm);
            if (mOffswitchSet != mOffswitch) {
                carSwitchBean.setOffswitch(mOffswitchSet);
            }
        }
        if (mCloseswitchSet != -1) {
            mCloseswitchSet = getSetValue(app_shutdown_alarm, sms_shutdown_alarm, phone_shutdown_alarm);
            if (mCloseswitchSet != mCloseswitch) {
                carSwitchBean.setCloseswitch(mCloseswitchSet);
            }
        }
        if (mDropalarmSet != -1) {
            mDropalarmSet = getSetValue(app_off_alarm, sms_off_alarm, phone_off_alarm);
            if (mDropalarmSet != mDropalarm) {
                carSwitchBean.setDropalarm(mDropalarmSet);
            }
        }

        if (mFenceswitchSet != -1) {
            mFenceswitchSet = getSetValue(app_fence_alarm, sms_fence_alarm, phone_fence_alarm);
            if (mFenceswitchSet != mFenceswitch) {
                carSwitchBean.setFenceswitch(mFenceswitchSet);
            }
        }
        if (mShakeswitchSet != -1) {
            mShakeswitchSet = getSetValue(app_shake_alarm, sms_shake_alarm, phone_shake_alarm);
        }
        if (mSpeedswitchSet != -1) {
            mSpeedswitchSet = getSetValue(app_over_speed, sms_over_speed, phone_over_speed);
        }

        //sos报警
        if (mSOSswitchSet != -1) {
            mSOSswitchSet = getSetValue(app_sos_alarm, sms_sos_alarm, phone_sos_alarm);
            if (mSOSswitchSet != mSOSswitch) {
                carSwitchBean.setSosswitch(mSOSswitchSet);
            }
        }

        //短信、电话报警号码
        String phone = edtMobile.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            if (mLowpowerSet > 1 || mOpenswitchSet > 1 || mOffswitchSet > 1 || mCloseswitchSet > 1
                    || mDropalarmSet > 1 || mFenceswitchSet > 1 || mShakeswitchSet > 1 || mSpeedswitchSet > 1
                    || mSOSswitchSet > 1) { //有短信或电话报警必须输入手机号
                ToastUtils.showShort(R.string.txt_input_phone);
                return;
            } else {
                phoneNumSet = "";
            }
        } else {
            phoneNumSet = phone;
        }

        DeviceConfigSetPutBean.ParamsBean.ConfigBean configBean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean();
        if (!phoneNumSet.equals(phoneNum)) {
            List<String> nums = new ArrayList<>();
            nums.add(phoneNumSet);
            configBean.setNotice_phones(nums);
        }
        configBean.setCar_switch(carSwitchBean);
        if (mShakealueSet != mShakealue || mShakeswitchSet != mShakeswitch) { //震动值变化或者开关变化，两个一起传
            configBean.setShake_value(mShakealueSet);
            carSwitchBean.setShakeswicth(mShakeswitchSet);
        }
        if (rlOverSpeedAlarm.getVisibility() == View.VISIBLE) {
            if (mSpeedValueSet != mSpeedValue || mSpeedswitchSet != mSpeedswitch) { //超速值变化或者开关变化，两个一起传
                configBean.setSpeed_value(mSpeedValueSet);
                carSwitchBean.setSpeedswitch(mSpeedswitchSet);
                if (mSpeedValueSet > 0) {
                    if (mSpeedValueSet < 10 || mSpeedValueSet > 190) {
                        ToastUtils.showShort(getString(R.string.txt_over_speed_alarm_hint));
                        return;
                    }
                }
            }
        }

        DeviceConfigSetPutBean.ParamsBean paramsBean = new DeviceConfigSetPutBean.ParamsBean();
        paramsBean.setConfig(configBean);
        paramsBean.setSimei(mSimeiBeas);

        DeviceConfigSetPutBean bean = new DeviceConfigSetPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Set);
        bean.setModule(ModuleValueService.Module_For_Config_Set);

        if (getPresenter() != null) {
            getPresenter().setDeviceConfig(bean);
        }
    }

    private int getSetValue(boolean app, boolean sms, boolean phone) {
        if (app && !sms && !phone) {
            return 1;
        } else if (!app && sms && !phone) {
            return 2;
        } else if (app && sms && !phone) {
            return 3;
        } else if (!app && !sms && phone) {
            return 4;
        } else if (app && !sms) {
            return 5;
        } else if (!app && sms) {
            return 6;
        } else if (app) {
            return 7;
        } else {
            return 0;
        }
    }

    /**
     * 更新UI
     */
    @SuppressLint("SetTextI18n")
    private void onUpdateAlarmUI() {
        // 震动报警
        for (ShakeValueBean bean : shakeValueBeans) {
            bean.setSelect(false);
        }
//        gridViewShake.setVisibility(mShakealueSet > 0 ? View.VISIBLE : View.INVISIBLE);
        if (mShakealue > 0) {
            for (ShakeValueBean bean : shakeValueBeans) {
                if (bean.getSensitivity() == mShakealue) {
                    bean.setSelect(true);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        // 超速报警
        llSpeedValue.setVisibility(mSpeedValueSet > 0 ? View.VISIBLE : View.INVISIBLE);
        if (mSpeedValue != -1) {
            if (mSpeedValue > 0) {
                edtSpeed.setText(String.valueOf(mSpeedValue));
            }
        }
        //震动报警
        if (mShakeswitch > 0) {
            tvShakeAlarm.setVisibility(View.VISIBLE);
            setViewSelect(mShakeswitch, ivAppShakeAlarm, ivSMSShakeAlarm, ivPhoneShakeAlarm, tv_app_shake_alarm, tv_sms_shake_alarm, tv_phone_shake_alarm);
            switch (mShakeswitch) {
                case 1:
                    app_shake_alarm = true;
                    break;
                case 2:
                    sms_shake_alarm = true;
                    break;
                case 3:
                    app_shake_alarm = true;
                    sms_shake_alarm = true;
                    break;
                case 4:
                    phone_shake_alarm = true;
                    break;
                case 5:
                    app_shake_alarm = true;
                    phone_shake_alarm = true;
                    break;
                case 6:
                    sms_shake_alarm = true;
                    phone_shake_alarm = true;
                    break;
                case 7:
                    app_shake_alarm = true;
                    sms_shake_alarm = true;
                    phone_shake_alarm = true;
                    break;
            }
        } else {
            tvShakeAlarm.setVisibility(View.GONE);
        }
        // 超速报警
        if (mSpeedswitch > 0) {
            tvOverSpeed.setVisibility(View.VISIBLE);
            setViewSelect(mSpeedswitch, ivAppOverSpeed, ivSMSOverSpeed, ivPhoneOverSpeed, tv_app_over_speed, tv_sms_over_speed, tv_phone_over_speed);
            switch (mSpeedswitch) {
                case 1:
                    app_over_speed = true;
                    break;
                case 2:
                    sms_over_speed = true;
                    break;
                case 3:
                    app_over_speed = true;
                    sms_over_speed = true;
                    break;
                case 4:
                    phone_over_speed = true;
                    break;
                case 5:
                    app_over_speed = true;
                    phone_over_speed = true;
                    break;
                case 6:
                    sms_over_speed = true;
                    phone_over_speed = true;
                    break;
                case 7:
                    app_over_speed = true;
                    sms_over_speed = true;
                    phone_over_speed = true;
                    break;
            }
        } else {
            tvOverSpeed.setVisibility(View.GONE);
        }

        // 脱落报警
        if (mDropalarm > 0) {
            tvOffAlarm.setVisibility(View.VISIBLE);
            setViewSelect(mDropalarm, ivAppOffAlarm, ivSMSOffAlarm, ivPhoneOffAlarm, tv_app_off_alarm, tv_sms_off_alarm, tv_phone_off_alarm);
            switch (mDropalarm) {
                case 1:
                    app_off_alarm = true;
                    break;
                case 2:
                    sms_off_alarm = true;
                    break;
                case 3:
                    app_off_alarm = true;
                    sms_off_alarm = true;
                    break;
                case 4:
                    phone_off_alarm = true;
                    break;
                case 5:
                    app_off_alarm = true;
                    phone_off_alarm = true;
                    break;
                case 6:
                    sms_off_alarm = true;
                    phone_off_alarm = true;
                    break;
                case 7:
                    app_off_alarm = true;
                    sms_off_alarm = true;
                    phone_off_alarm = true;
                    break;
            }
        } else {
            tvOffAlarm.setVisibility(View.GONE);
        }
        // 低电报警
        if (mLowpower > 0) {
            tvPowerAlarm.setVisibility(View.VISIBLE);
            setViewSelect(mLowpower, ivAppPowerAlarm, ivSMSLowPowerAlarm, ivPhonePowerAlarm, tv_app_low_power_alarm, tv_sms_low_power_alarm, tv_phone_low_power_alarm);
            switch (mLowpower) {
                case 1:
                    app_low_power_alarm = true;
                    break;
                case 2:
                    sms_low_power_alarm = true;
                    break;
                case 3:
                    app_low_power_alarm = true;
                    sms_low_power_alarm = true;
                    break;
                case 4:
                    phone_low_power_alarm = true;
                    break;
                case 5:
                    app_low_power_alarm = true;
                    phone_low_power_alarm = true;
                    break;
                case 6:
                    sms_low_power_alarm = true;
                    phone_low_power_alarm = true;
                    break;
                case 7:
                    app_low_power_alarm = true;
                    sms_low_power_alarm = true;
                    phone_low_power_alarm = true;
                    break;
            }
        } else {
            tvPowerAlarm.setVisibility(View.GONE);
        }
        // 进出围栏报警
        if (mFenceswitch > 0) {
            tvFenceAlarm.setVisibility(View.VISIBLE);
            setViewSelect(mFenceswitch, ivAppFenceAlarm, ivSMSFenceAlarm, ivPhoneFenceAlarm, tv_app_fence_alarm, tv_sms_fence_alarm, tv_phone_fence_alarm);
            switch (mFenceswitch) {
                case 1:
                    app_fence_alarm = true;
                    break;
                case 2:
                    sms_fence_alarm = true;
                    break;
                case 3:
                    app_fence_alarm = true;
                    sms_fence_alarm = true;
                    break;
                case 4:
                    phone_fence_alarm = true;
                    break;
                case 5:
                    app_fence_alarm = true;
                    phone_fence_alarm = true;
                    break;
                case 6:
                    sms_fence_alarm = true;
                    phone_fence_alarm = true;
                    break;
                case 7:
                    app_fence_alarm = true;
                    sms_fence_alarm = true;
                    phone_fence_alarm = true;
                    break;
            }
        } else {
            tvFenceAlarm.setVisibility(View.GONE);
        }
        // 声音开关
        ivSoundsWitch.setImageResource(mSoundswitch > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 指示灯开关
        ivIndicatorLightAlarm.setImageResource(mIndicatorlight > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);

        // 离线报警
        if (mOffswitch > 0) {
            tvOfflineAlarm.setVisibility(View.VISIBLE);
            setViewSelect(mOffswitch, ivAppOfflineAlarm, ivSMSOfflineAlarm, ivPhoneOfflineAlarm, tv_app_offline_alarm, tv_sms_offline_alarm, tv_phone_offline_alarm);
            switch (mOffswitch) {
                case 1:
                    app_offline_alarm = true;
                    break;
                case 2:
                    sms_offline_alarm = true;
                    break;
                case 3:
                    app_offline_alarm = true;
                    sms_offline_alarm = true;
                    break;
                case 4:
                    phone_offline_alarm = true;
                    break;
                case 5:
                    app_offline_alarm = true;
                    phone_offline_alarm = true;
                    break;
                case 6:
                    sms_offline_alarm = true;
                    phone_offline_alarm = true;
                    break;
                case 7:
                    app_offline_alarm = true;
                    sms_offline_alarm = true;
                    phone_offline_alarm = true;
                    break;
            }
        } else {
            tvOfflineAlarm.setVisibility(View.GONE);
        }
        // 关机报警
        if (mCloseswitch > 0) {
            tvShutdownAlarm.setVisibility(View.VISIBLE);
            setViewSelect(mCloseswitch, ivAppShutdownAlarm, ivSMSShutdownAlarm, ivPhoneShutdownAlarm, tv_app_shutdown_alarm, tv_sms_shutdown_alarm, tv_phone_shutdown_alarm);
            switch (mCloseswitch) {
                case 1:
                    app_shutdown_alarm = true;
                    break;
                case 2:
                    sms_shutdown_alarm = true;
                    break;
                case 3:
                    app_shutdown_alarm = true;
                    sms_shutdown_alarm = true;
                    break;
                case 4:
                    phone_shutdown_alarm = true;
                    break;
                case 5:
                    app_shutdown_alarm = true;
                    phone_shutdown_alarm = true;
                    break;
                case 6:
                    sms_shutdown_alarm = true;
                    phone_shutdown_alarm = true;
                    break;
                case 7:
                    app_shutdown_alarm = true;
                    sms_shutdown_alarm = true;
                    phone_shutdown_alarm = true;
                    break;
            }
        } else {
            tvShutdownAlarm.setVisibility(View.GONE);
        }
        // 启动报警
        if (mOpenswitch > 0) {
            tvRebootAlarm.setVisibility(View.VISIBLE);
            setViewSelect(mOpenswitch, ivAppRebootAlarm, ivSMSRebootAlarm, ivPhoneRebootAlarm, tv_app_reboot_alarm, tv_sms_reboot_alarm, tv_phone_reboot_alarm);
            switch (mOpenswitch) {
                case 1:
                    app_reboot_alarm = true;
                    break;
                case 2:
                    sms_reboot_alarm = true;
                    break;
                case 3:
                    app_reboot_alarm = true;
                    sms_reboot_alarm = true;
                    break;
                case 4:
                    phone_reboot_alarm = true;
                    break;
                case 5:
                    app_reboot_alarm = true;
                    phone_reboot_alarm = true;
                    break;
                case 6:
                    sms_reboot_alarm = true;
                    phone_reboot_alarm = true;
                    break;
                case 7:
                    app_reboot_alarm = true;
                    sms_reboot_alarm = true;
                    phone_reboot_alarm = true;
                    break;
            }
        } else {
            tvRebootAlarm.setVisibility(View.GONE);
        }

        // sos报警
        if (mSOSswitch > 0) {
            tvSOSAlarm.setVisibility(View.VISIBLE);
            ivSMSSOSAlarm.setImageResource(R.drawable.icon_unselected);
            ivPhoneSOSAlarm.setImageResource(R.drawable.icon_unselected);
            tv_sms_sos_alarm.setTextColor(getResources().getColor(R.color.color_B3B3B3));
            tv_phone_sos_alarm.setTextColor(getResources().getColor(R.color.color_B3B3B3));
            switch (mSOSswitch) {
                case 1:
                    app_sos_alarm = true;
                    ivAppSOSAlarm.setImageResource(R.drawable.icon_sos_select);
                    break;
                case 2:
                    sms_sos_alarm = true;
                    ivSMSSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_sms_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    break;
                case 3:
                    app_sos_alarm = true;
                    sms_sos_alarm = true;
                    ivAppSOSAlarm.setImageResource(R.drawable.icon_sos_select);
                    ivSMSSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_sms_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    break;
                case 4:
                    phone_sos_alarm = true;
                    ivPhoneSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_phone_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    break;
                case 5:
                    app_sos_alarm = true;
                    phone_sos_alarm = true;
                    ivPhoneSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_phone_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    ivAppSOSAlarm.setImageResource(R.drawable.icon_sos_select);
                    break;
                case 6:
                    sms_sos_alarm = true;
                    phone_sos_alarm = true;
                    ivPhoneSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_phone_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    ivSMSSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_sms_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    break;
                case 7:
                    app_sos_alarm = true;
                    sms_sos_alarm = true;
                    phone_sos_alarm = true;
                    ivAppSOSAlarm.setImageResource(R.drawable.icon_sos_select);
                    ivPhoneSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_phone_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    ivSMSSOSAlarm.setImageResource(R.drawable.icon_select);
                    tv_sms_sos_alarm.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                    break;
            }
        } else {
            tvSOSAlarm.setVisibility(View.GONE);
        }

        if (!isSupportIncrement) {
            tvPhoneTip.setVisibility(View.GONE);
            llPhone.setVisibility(View.GONE);
            ivSMSShakeAlarm.setVisibility(View.GONE); tv_sms_shake_alarm.setVisibility(View.GONE);  ivPhoneShakeAlarm.setVisibility(View.GONE); tv_phone_shake_alarm.setVisibility(View.GONE);
            ivSMSOverSpeed.setVisibility(View.GONE); tv_sms_over_speed.setVisibility(View.GONE);  ivPhoneOverSpeed.setVisibility(View.GONE); tv_phone_over_speed.setVisibility(View.GONE);
            ivSMSOffAlarm.setVisibility(View.GONE); tv_sms_off_alarm.setVisibility(View.GONE);  ivPhoneOffAlarm.setVisibility(View.GONE); tv_phone_off_alarm.setVisibility(View.GONE);
            ivSMSLowPowerAlarm.setVisibility(View.GONE); tv_sms_low_power_alarm.setVisibility(View.GONE);  ivPhonePowerAlarm.setVisibility(View.GONE); tv_phone_low_power_alarm.setVisibility(View.GONE);
            ivSMSFenceAlarm.setVisibility(View.GONE); tv_sms_fence_alarm.setVisibility(View.GONE);  ivPhoneFenceAlarm.setVisibility(View.GONE); tv_phone_fence_alarm.setVisibility(View.GONE);
            ivSMSOfflineAlarm.setVisibility(View.GONE); tv_sms_offline_alarm.setVisibility(View.GONE);  ivPhoneOfflineAlarm.setVisibility(View.GONE); tv_phone_offline_alarm.setVisibility(View.GONE);
            ivSMSShutdownAlarm.setVisibility(View.GONE); tv_sms_shutdown_alarm.setVisibility(View.GONE);  ivPhoneShutdownAlarm.setVisibility(View.GONE); tv_phone_shutdown_alarm.setVisibility(View.GONE);
            ivSMSRebootAlarm.setVisibility(View.GONE); tv_sms_reboot_alarm.setVisibility(View.GONE);  ivPhoneRebootAlarm.setVisibility(View.GONE); tv_phone_reboot_alarm.setVisibility(View.GONE);
            ivSMSSOSAlarm.setVisibility(View.GONE); tv_sms_sos_alarm.setVisibility(View.GONE);  ivPhoneSOSAlarm.setVisibility(View.GONE); tv_phone_sos_alarm.setVisibility(View.GONE);
        }
    }

    private void setViewSelect(int value, ImageView ivApp, ImageView ivSMS, ImageView ivCall, TextView tvAPP, TextView tvSMS, TextView tvCall) {
        ivApp.setImageResource(R.drawable.icon_unselected);
        ivSMS.setImageResource(R.drawable.icon_unselected);
        ivCall.setImageResource(R.drawable.icon_unselected);
        tvAPP.setTextColor(getResources().getColor(R.color.color_B3B3B3));
        tvSMS.setTextColor(getResources().getColor(R.color.color_B3B3B3));
        tvCall.setTextColor(getResources().getColor(R.color.color_B3B3B3));
        switch (value) {
            case 1:
                ivApp.setImageResource(R.drawable.icon_select);
                tvAPP.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                break;
            case 2:
                ivSMS.setImageResource(R.drawable.icon_select);
                tvSMS.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                break;
            case 3:
                ivApp.setImageResource(R.drawable.icon_select);
                tvAPP.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                ivSMS.setImageResource(R.drawable.icon_select);
                tvSMS.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                break;
            case 4:
                ivCall.setImageResource(R.drawable.icon_select);
                tvCall.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                break;
            case 5:
                ivCall.setImageResource(R.drawable.icon_select);
                tvCall.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                ivApp.setImageResource(R.drawable.icon_select);
                tvAPP.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                break;
            case 6:
                ivCall.setImageResource(R.drawable.icon_select);
                tvCall.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                ivSMS.setImageResource(R.drawable.icon_select);
                tvSMS.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                break;
            case 7:
                ivApp.setImageResource(R.drawable.icon_select);
                tvAPP.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                ivCall.setImageResource(R.drawable.icon_select);
                tvCall.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                ivSMS.setImageResource(R.drawable.icon_select);
                tvSMS.setTextColor(getResources().getColor(R.color.color_2E7BEC));
                break;
        }
    }


    /**
     * 开关值表示
     * -1不支持
     * 0 关闭
     * 1 仅支持APP报警
     * 2 仅支持短信报警
     * 3 仅支持短信+APP报警
     * 4 仅支持电话报警
     * 5 仅支持电话+APP报警
     * 6 仅支持电话+短信报警
     * 7 支持电话+短信+APP报警
     */
    @Override
    public void getDeviceConfigSuccess(DeviceConfigResultBean deviceConfigResultBean) {
        //震动报警
        rlShakeAlarm.setVisibility(deviceConfigResultBean.getConfig().getShake_value() == -1 ? View.GONE : View.VISIBLE);
        // 超速报警
        rlOverSpeedAlarm.setVisibility(deviceConfigResultBean.getConfig().getSpeed_value() == -1 ? View.GONE : View.VISIBLE);
        // 脱落报警
        rlOffAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getDropalarm() == -1 ? View.GONE : View.VISIBLE);
        // 低电报警
        rlLowPowerAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getLowpower() == -1 ? View.GONE : View.VISIBLE);
        // 进出围栏报警
        rlFenceAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getFenceswitch() == -1 ? View.GONE : View.VISIBLE);
        // 声音开关
        rlSoundsWitch.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getSoundswitch() == -1 ? View.GONE : View.VISIBLE);
        // 指示灯开关
        rlLightAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getIndicatorlight() == -1 ? View.GONE : View.VISIBLE);
        // 离线报警
        rlOfflineAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getOffswitch() == -1 ? View.GONE : View.VISIBLE);
        // 关机报警
        rlShutdownAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getCloseswitch() == -1 ? View.GONE : View.VISIBLE);
        // 启动报警
        rlRebootAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getOpenswitch() == -1 ? View.GONE : View.VISIBLE);
        // SOS报警
        rlSOSAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getSosswitch() == -1 ? View.GONE : View.VISIBLE);

        //指示灯和声音开关都不支持情况下
        if (deviceConfigResultBean.getConfig().getCar_switch().getSoundswitch() == -1 && deviceConfigResultBean.getConfig().getCar_switch().getIndicatorlight() == -1) {
            llOtherSet.setVisibility(View.GONE);
        }
        List<String> nums = deviceConfigResultBean.getConfig().getNotice_phones();
        if (nums != null && nums.size() > 0) {
            phoneNum = nums.get(0);
            edtMobile.setText(phoneNum);
        }
        mLowpower = deviceConfigResultBean.getConfig().getCar_switch().getLowpower(); // 低电报警,0关闭，-1不支持
        mIndicatorlight = deviceConfigResultBean.getConfig().getCar_switch().getIndicatorlight(); // 指示灯 0关闭，-1不支持
        mSoundswitch = deviceConfigResultBean.getConfig().getCar_switch().getSoundswitch(); // 声音提示开关 0关闭，-1不支持
        mOpenswitch = deviceConfigResultBean.getConfig().getCar_switch().getOpenswitch(); // 开机报警 启动报警 0关闭，，-1不支持
        mOffswitch = deviceConfigResultBean.getConfig().getCar_switch().getOffswitch(); // 离线报警0关闭，-1不支持
        mCloseswitch = deviceConfigResultBean.getConfig().getCar_switch().getCloseswitch(); // 关机报警0关闭，-1不支持
        mDropalarm = deviceConfigResultBean.getConfig().getCar_switch().getDropalarm(); // 脱落报警0关闭，-1不支持
        mFenceswitch = deviceConfigResultBean.getConfig().getCar_switch().getFenceswitch(); // 围栏开关 0关闭，-1不支持
        mShakealue = deviceConfigResultBean.getConfig().getShake_value(); // 震动报警程度0-关闭,(18,28,48)开启，-1不支持,
        mSpeedValue = deviceConfigResultBean.getConfig().getSpeed_value(); // 超速阈值 0关闭，>0开启，-1不支持
        mShakeswitch = deviceConfigResultBean.getConfig().getCar_switch().getShakeswicth(); //震动报警开关
        mSpeedswitch = deviceConfigResultBean.getConfig().getCar_switch().getSpeedswitch(); //超速报警开关
        mSOSswitch = deviceConfigResultBean.getConfig().getCar_switch().getSosswitch();//SOS报警开关

        mLowpowerSet = mLowpower;
        mIndicatorlightSet = mIndicatorlight;
        mSoundswitchSet = mSoundswitch;
        mOpenswitchSet = mOpenswitch;
        mOffswitchSet = mOffswitch;
        mCloseswitchSet = mCloseswitch;
        mDropalarmSet = mDropalarm;
        mFenceswitchSet = mFenceswitch;
        mShakealueSet = mShakealue;
        mSpeedValueSet = mSpeedValue;
        mShakeswitchSet = mShakeswitch;
        mSpeedswitchSet = mSpeedswitch;
        mSOSswitchSet = mSOSswitch;
        phoneNumSet = phoneNum;

        onUpdateAlarmUI();
    }

    @Override
    public void setDeviceConfigSuccess(SetConfigResultBean baseBean) {
        if (baseBean.getFail_items() != null && baseBean.getFail_items().size() > 0) {
            //{"error_message":"设备不支持","imei":23777777069,"simei":"SyqPbQe7an9TIOMz-gl57O8KDU9sVjHPG3VKqjIGluY"}
            ToastUtils.showShort(baseBean.getFail_items().get(0).getError_messageX());
            return;
        }
        ToastUtils.showShort(getString(R.string.txt_set_success));
        mLowpower = mLowpowerSet;
        mIndicatorlight = mIndicatorlightSet;
        mSoundswitch = mSoundswitchSet;
        mOpenswitch = mOpenswitchSet;
        mOffswitch = mOffswitchSet;
        mCloseswitch = mCloseswitchSet;
        mDropalarm = mDropalarmSet;
        mFenceswitch = mFenceswitchSet;
        mShakealue = mShakealueSet;
        mSpeedValue = mSpeedValueSet;
        mShakeswitch = mShakeswitchSet;
        mSpeedswitch = mSpeedswitchSet;
        mSOSswitch = mSOSswitchSet;
        phoneNum = phoneNumSet ;
    }
}

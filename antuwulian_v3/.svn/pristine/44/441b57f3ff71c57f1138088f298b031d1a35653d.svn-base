package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonArray;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerAlarmSetSpecialComponent;
import com.slxk.gpsantu.mvp.contract.AlarmSetSpecialContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.DeviceConfigResultBean;
import com.slxk.gpsantu.mvp.model.bean.SetConfigResultBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceConfigPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceConfigSetPutBean;
import com.slxk.gpsantu.mvp.presenter.AlarmSetSpecialPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.view.ClearEditText;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.blankj.utilcode.util.ToastUtils;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 特殊报警设置
 * <p>
 * Created by MVPArmsTemplate on 09/27/2021 09:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmSetSpecialActivity extends BaseActivity<AlarmSetSpecialPresenter> implements AlarmSetSpecialContract.View {

    @BindView(R.id.iv_alarm_shake)
    ImageView iv_alarm_shake;
    @BindView(R.id.iv_alarm_off)
    ImageView iv_alarm_off;
    @BindView(R.id.iv_alarm_acc)
    ImageView iv_alarm_acc;

    @BindView(R.id.tv_alarm_shake)
    TextView tv_alarm_shake;
    @BindView(R.id.tv_alarm_off)
    TextView tv_alarm_off;
    @BindView(R.id.tv_alarm_acc)
    TextView tv_alarm_acc;

    @BindView(R.id.iv_sms_notify)
    ImageView iv_sms_notify;
    @BindView(R.id.iv_call_notify)
    ImageView iv_call_notify;
    @BindView(R.id.iv_no_notify)
    ImageView iv_no_notify;
    @BindView(R.id.iv_sms_call_notify)
    ImageView iv_sms_call_notify;

    @BindView(R.id.edt_alarm_times)
    ClearEditText edt_alarm_times;
    @BindView(R.id.edt_mobile_phone_1)
    ClearEditText edt_mobile_phone_1;
    @BindView(R.id.edt_mobile_phone_2)
    ClearEditText edt_mobile_phone_2;
    @BindView(R.id.edt_mobile_phone_3)
    ClearEditText edt_mobile_phone_3;

    @BindView(R.id.ll_edit)
    LinearLayout ll_edit;
    @BindView(R.id.alarm_times)
    LinearLayout alarm_times;

    private int alarmType; // 1-震动报警 2-断电报警 3-ACC接通报警(非法启动)
    private int times; //通知次数
    private String phoneBook;
    private String shakeNotify; //震动报警通知方式 0-不通知，1-短信通知，2-电话通知，3-短信加电话通知
    private String outNotify; // 断电报警通知方式 同上
    private String accNotify; //acc报警通知方式  同上

    private int timesResponse; //通知次数返回值
    private String phoneBookResponse; //电话返回值
    private String shakeNotifyResponse = ResultDataUtils.Alarm_NONE; //震动报警返回值
    private String outNotifyResponse = ResultDataUtils.Alarm_NONE;//断电报警返回值
    private String accNotifyResponse = ResultDataUtils.Alarm_NONE;//acc报警返回值
    private String mSimei;
    private List<String> mSimeiBeas;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), AlarmSetSpecialActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmSetSpecialComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm_set_special; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_alarm_special_set);
        mSimei = MyApplication.getMyApp().getSimei();
        mSimeiBeas = new ArrayList<>();
        if (!TextUtils.isEmpty(mSimei)) {
            mSimeiBeas.add(mSimei);
        }
        getConfig();

    }


    private void getConfig() {
        DeviceConfigPutBean.ParamsBean paramsBean = new DeviceConfigPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setType(ResultDataUtils.Config_Phone);

        DeviceConfigPutBean bean = new DeviceConfigPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Get);
        bean.setModule(ModuleValueService.Module_For_Config_Get);

        if (getPresenter() != null) {
            getPresenter().getPhoneBookConfig(bean);
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

    @Override
    public void getDeviceConfigSuccess(DeviceConfigResultBean deviceConfigResultBean) {
        phoneBookResponse = deviceConfigResultBean.getConfig().getPhone_book();
        if (deviceConfigResultBean.getConfig().getPhone_alarm() != null) {
            accNotifyResponse = deviceConfigResultBean.getConfig().getPhone_alarm().getAcc();
            outNotifyResponse = deviceConfigResultBean.getConfig().getPhone_alarm().getOut();
            shakeNotifyResponse = deviceConfigResultBean.getConfig().getPhone_alarm().getVibration();
            timesResponse = deviceConfigResultBean.getConfig().getPhone_alarm().getTotal();
        }
        selectAlarmType(1); //默认选择震动报警
        setTextChange();
    }

    @SuppressLint("SetTextI18n")
    private void setTextChange() {
        String shakeAlarm = "";
        String outAlarm = "";
        String accAlarm = "";
        if (ResultDataUtils.Alarm_NONE.equals(shakeNotifyResponse)) {
            shakeAlarm = "(" + getString(R.string.txt_no_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_SMS.equals(shakeNotifyResponse)) {
            shakeAlarm = "(" + getString(R.string.txt_sms_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_PHONE.equals(shakeNotifyResponse)) {
            shakeAlarm = "(" + getString(R.string.txt_call_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_SMS_PHONe.equals(shakeNotifyResponse)) {
            shakeAlarm = "(" + getString(R.string.txt_sms_call_set) + ")";
        }

        if (ResultDataUtils.Alarm_NONE.equals(outNotifyResponse)) {
            outAlarm = "(" + getString(R.string.txt_no_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_SMS.equals(outNotifyResponse)) {
            outAlarm = "(" + getString(R.string.txt_sms_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_PHONE.equals(outNotifyResponse)) {
            outAlarm = "(" + getString(R.string.txt_call_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_SMS_PHONe.equals(outNotifyResponse)) {
            outAlarm = "(" + getString(R.string.txt_sms_call_set) + ")";
        }

        if (ResultDataUtils.Alarm_NONE.equals(accNotifyResponse)) {
            accAlarm = "(" + getString(R.string.txt_no_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_SMS.equals(accNotifyResponse)) {
            accAlarm = "(" + getString(R.string.txt_sms_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_PHONE.equals(accNotifyResponse)) {
            accAlarm = "(" + getString(R.string.txt_call_alarm_set) + ")";
        }
        if (ResultDataUtils.Alarm_SMS_PHONe.equals(accNotifyResponse)) {
            accAlarm = "(" + getString(R.string.txt_sms_call_set) + ")";
        }

        tv_alarm_shake.setText(getString(R.string.txt_shake_alarm) + shakeAlarm);
        tv_alarm_off.setText(getString(R.string.txt_alarm_off_set) + outAlarm);
        tv_alarm_acc.setText(getString(R.string.txt_alarm_acc_set) + accAlarm);
    }


    @OnClick({R.id.ll_alarm_shake, R.id.ll_alarm_off, R.id.ll_alarm_acc, R.id.ll_sms_notify, R.id.ll_call_notify,
            R.id.ll_no_notify, R.id.ll_sms_call_notify, R.id.btn_confirm,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_alarm_shake:
                selectAlarmType(1);
                break;
            case R.id.ll_alarm_off:
                selectAlarmType(2);
                break;
            case R.id.ll_alarm_acc:
                selectAlarmType(3);
                break;

            case R.id.ll_no_notify:
                selectNotifyType(ResultDataUtils.Alarm_NONE);
                break;
            case R.id.ll_sms_notify:
                selectNotifyType(ResultDataUtils.Alarm_SMS);
                break;
            case R.id.ll_call_notify:
                selectNotifyType(ResultDataUtils.Alarm_PHONE);
                break;
            case R.id.ll_sms_call_notify:
                selectNotifyType(ResultDataUtils.Alarm_SMS_PHONe);
                break;
            case R.id.btn_confirm:
                submitAlarmSet();
                break;
        }
    }

    private void selectAlarmType(int i) {
        alarmType = i;
        iv_alarm_shake.setImageResource(R.mipmap.icon_unselected_small);
        iv_alarm_off.setImageResource(R.mipmap.icon_unselected_small);
        iv_alarm_acc.setImageResource(R.mipmap.icon_unselected_small);
        switch (i) {
            case 1:
                selectNotifyType(shakeNotifyResponse);
                iv_alarm_shake.setImageResource(R.mipmap.icon_save_password_select);
                break;
            case 2:
                selectNotifyType(outNotifyResponse);
                iv_alarm_off.setImageResource(R.mipmap.icon_save_password_select);
                break;
            case 3:
                selectNotifyType(accNotifyResponse);
                iv_alarm_acc.setImageResource(R.mipmap.icon_save_password_select);
                break;
        }
    }

    private void selectNotifyType(String type) {
        if (ResultDataUtils.Alarm_NONE.equals(type)) {
            ll_edit.setVisibility(View.GONE);
        } else {
            ll_edit.setVisibility(View.VISIBLE);
            if (ResultDataUtils.Alarm_SMS.equals(type)) { // 短信报警隐藏通知次数
                alarm_times.setVisibility(View.GONE);
            } else {
                alarm_times.setVisibility(View.VISIBLE);
            }
        }
        iv_sms_notify.setImageResource(R.mipmap.icon_unselected_small);
        iv_call_notify.setImageResource(R.mipmap.icon_unselected_small);
        iv_no_notify.setImageResource(R.mipmap.icon_unselected_small);
        iv_sms_call_notify.setImageResource(R.mipmap.icon_unselected_small);
        switch (type) {
            case ResultDataUtils.Alarm_NONE:
                iv_no_notify.setImageResource(R.mipmap.icon_save_password_select);
                break;
            case ResultDataUtils.Alarm_SMS:
                iv_sms_notify.setImageResource(R.mipmap.icon_save_password_select);
                break;
            case ResultDataUtils.Alarm_PHONE:
                iv_call_notify.setImageResource(R.mipmap.icon_save_password_select);
                break;
            case ResultDataUtils.Alarm_SMS_PHONe:
                iv_sms_call_notify.setImageResource(R.mipmap.icon_save_password_select);
                break;
        }
        try {
            setTextResponseChange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (alarmType == 1) {
            shakeNotify = type;
        } else if (alarmType == 2) {
            outNotify = type;
        } else {
            accNotify = type;
        }
    }

    private void setTextResponseChange() throws JSONException {
        edt_alarm_times.setText("");
        edt_mobile_phone_1.setText("");
        edt_mobile_phone_2.setText("");
        edt_mobile_phone_3.setText("");
        if (timesResponse != 0) { //报警次数赋值
            edt_alarm_times.setText(String.valueOf(timesResponse));
        }
        //设置通知号码
        if (!TextUtils.isEmpty(phoneBookResponse)) {
            JSONArray jsonArray = new JSONArray(phoneBookResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                String mobilePhone = jsonArray.getString(i);
                if (!TextUtils.isEmpty(mobilePhone) && !"null".equals(mobilePhone)) {
                    if (i == 0) {
                        edt_mobile_phone_1.setText(mobilePhone);
                    } else if (i == 1) {
                        edt_mobile_phone_2.setText(mobilePhone);
                    } else {
                        edt_mobile_phone_3.setText(mobilePhone);
                    }
                }
            }
        }
    }

    private void submitAlarmSet() {
        if (alarmType == 1 && ResultDataUtils.Alarm_NONE.equals(shakeNotify) || alarmType == 2 && ResultDataUtils.Alarm_NONE.equals(outNotify)
                || alarmType == 3 && ResultDataUtils.Alarm_NONE.equals(accNotify)) {
            if (notifyChangeCompare()) return;
            DeviceConfigSetPutBean.ParamsBean.ConfigBean.PhoneAlarm bean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean.PhoneAlarm();
            setNotifyChange(bean);

            DeviceConfigSetPutBean.ParamsBean.ConfigBean configBean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean();
            configBean.setPhone_alarm(bean);
            DeviceConfigSetPutBean.ParamsBean paramsBean = new DeviceConfigSetPutBean.ParamsBean();

            paramsBean.setConfig(configBean);
            paramsBean.setSimei(mSimeiBeas);
            DeviceConfigSetPutBean beanPut = new DeviceConfigSetPutBean();
            beanPut.setParams(paramsBean);
            beanPut.setFunc(ModuleValueService.Fuc_For_Config_Set);
            beanPut.setModule(ModuleValueService.Module_For_Config_Set);
            if (getPresenter() != null) {
                getPresenter().setDeviceConfig(beanPut);
            }
        } else {
            if (alarmType == 1 && !ResultDataUtils.Alarm_SMS.equals(shakeNotify) || alarmType == 2 && !ResultDataUtils.Alarm_SMS.equals(outNotify)
                    || alarmType == 3 && !ResultDataUtils.Alarm_SMS.equals(accNotify)) { //短信报警 不需要通知次数
                String tt = edt_alarm_times.getText().toString();
                if (tt.isEmpty()) {
                    ToastUtils.showShort(getString(R.string.txt_alarm_notify_time_set));
                    return;
                }
                times = Integer.parseInt(tt);
                if (times == 0 || times > 10) {
                    ToastUtils.showShort(getString(R.string.txt_alarm_notify_time_set));
                    return;
                }
            }
            String phoneBook1 = edt_mobile_phone_1.getText().toString();
            String phoneBook2 = edt_mobile_phone_2.getText().toString();
            String phoneBook3 = edt_mobile_phone_3.getText().toString();
            if (TextUtils.isEmpty(phoneBook1) && TextUtils.isEmpty(phoneBook2) && TextUtils.isEmpty(phoneBook3)) {
                ToastUtils.showShort(getString(R.string.txt_notify_at_least));
                return;
            }
            JsonArray jsonArray = new JsonArray();
            if (!TextUtils.isEmpty(phoneBook1)) {
                jsonArray.add(phoneBook1);
            }
            if (!TextUtils.isEmpty(phoneBook2)) {
                jsonArray.add(phoneBook2);
            }
            if (!TextUtils.isEmpty(phoneBook3)) {
                jsonArray.add(phoneBook3);
            }
            //去除重复号码
            Set<String> setPhone = new LinkedHashSet<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                String str = jsonArray.get(i).getAsString();
                setPhone.add(str);
            }
            //size 不相同表示存在重复，弹出提示
            if (setPhone.size() != jsonArray.size()) {
                com.blankj.utilcode.util.ToastUtils.showShort(getString(R.string.txt_notify_number_not_repeat));
                return;
            }
            phoneBook = jsonArray.toString();
            //未修改 不下发
            if (timesResponse == times && phoneBookResponse.equals(phoneBook)) {
                if (notifyChangeCompare()) return;
            }

            DeviceConfigSetPutBean.ParamsBean.ConfigBean configBean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean();
            if (!phoneBook.equals(phoneBookResponse))
                configBean.setPhone_book(phoneBook);

            DeviceConfigSetPutBean.ParamsBean.ConfigBean.PhoneAlarm bean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean.PhoneAlarm();
            if (times != 0 && times != timesResponse) {
                bean.setTotal(times);
            }
            setNotifyChange(bean);
            configBean.setPhone_alarm(bean);

            DeviceConfigSetPutBean.ParamsBean paramsBean = new DeviceConfigSetPutBean.ParamsBean();
            paramsBean.setConfig(configBean);
            paramsBean.setSimei(mSimeiBeas);

            DeviceConfigSetPutBean beanPut = new DeviceConfigSetPutBean();
            beanPut.setParams(paramsBean);
            beanPut.setFunc(ModuleValueService.Fuc_For_Config_Set);
            beanPut.setModule(ModuleValueService.Module_For_Config_Set);
            if (getPresenter() != null) {
                getPresenter().setDeviceConfig(beanPut);
            }
        }
    }

    private void setNotifyChange(DeviceConfigSetPutBean.ParamsBean.ConfigBean.PhoneAlarm bean) {
        if (alarmType == 1) {
            if (!shakeNotify.equals(shakeNotifyResponse))
                bean.setVibration(shakeNotify);
        } else if (alarmType == 2) {
            if (!outNotify.equals(outNotifyResponse))
                bean.setOut(outNotify);
        } else {
            if (!accNotify.equals(accNotifyResponse))
                bean.setAcc(accNotify);
        }
    }

    private boolean notifyChangeCompare() {
        if (alarmType == 1 && shakeNotify.equals(shakeNotifyResponse)) {
            ToastUtils.showShort(getString(R.string.txt_set_change_tip));
            return true;
        }
        if (alarmType == 2 && outNotify.equals(outNotifyResponse)) {
            ToastUtils.showShort(getString(R.string.txt_set_change_tip));
            return true;
        }
        if (alarmType == 3 && accNotify.equals(accNotifyResponse)) {
            ToastUtils.showShort(getString(R.string.txt_set_change_tip));
            return true;
        }
        return false;
    }


    @Override
    public void setDeviceConfigSuccess(SetConfigResultBean baseBean) {
        if (baseBean.getFail_items() != null && baseBean.getFail_items().size() > 0) {
            //{"error_message":"设备不支持","imei":23777777069,"simei":"SyqPbQe7an9TIOMz-gl57O8KDU9sVjHPG3VKqjIGluY"}
            ToastUtils.showShort(baseBean.getFail_items().get(0).getError_messageX());
            return;
        }
        ToastUtils.showShort(getString(R.string.txt_set_success));
        try {
            if (alarmType == 1 && !ResultDataUtils.Alarm_NONE.equals(shakeNotify) || alarmType == 2 && !ResultDataUtils.Alarm_NONE.equals(outNotify)
                    || alarmType == 3 && !ResultDataUtils.Alarm_NONE.equals(accNotify)) {
                if (times != 0) { //不为0时 赋值
                    timesResponse = times;
                }
                phoneBookResponse = phoneBook;
            }
            if (alarmType == 1) {
                shakeNotifyResponse = shakeNotify;
            } else if (alarmType == 2) {
                outNotifyResponse = outNotify;
            } else {
                accNotifyResponse = accNotify;
            }
            setTextChange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

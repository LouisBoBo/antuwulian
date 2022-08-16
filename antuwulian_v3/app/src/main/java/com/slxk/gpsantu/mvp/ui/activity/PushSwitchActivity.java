package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerPushSwitchComponent;
import com.slxk.gpsantu.mvp.contract.PushSwitchContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.UidInfoResultBean;
import com.slxk.gpsantu.mvp.model.putbean.ModifyPasswordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.UidInfoPutBean;
import com.slxk.gpsantu.mvp.presenter.PushSwitchPresenter;
import com.slxk.gpsantu.mvp.receiver.TagAliasOperatorHelper;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.SelectTimeDialog;

import java.util.LinkedHashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.slxk.gpsantu.mvp.receiver.TagAliasOperatorHelper.ACTION_DELETE;
import static com.slxk.gpsantu.mvp.receiver.TagAliasOperatorHelper.ACTION_SET;
import static com.slxk.gpsantu.mvp.receiver.TagAliasOperatorHelper.sequence;


/**
 * ================================================
 * Description: 推送开关
 * <p>
 * Created by MVPArmsTemplate on 03/03/2021 15:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PushSwitchActivity extends BaseActivity<PushSwitchPresenter> implements PushSwitchContract.View {

    @BindView(R.id.iv_message_push)
    ImageView ivMessagePush; // 消息推送
    @BindView(R.id.iv_do_not_disturb_push)
    ImageView ivDoNotDisturbPush; // 免打扰推送
    @BindView(R.id.tv_start_time)
    TextView tvStartTime; // 开始时间
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.view_time)
    View viewTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_do_not_disturb_mode_hint)
    TextView tvDoNotDisturbModeHint;
    @BindView(R.id.ll_do_not_disturb_mode)
    LinearLayout llDoNotDisturbMode;

    private boolean isPushSwitch = false; // 极光推送开关
    private boolean isDoNotDisturbPush = false; // 免打扰推送开关
    private String startTime; // 开始时间
    private String endTime; // 结束时间
    private boolean isStartTime = true; // 是否是选择开始时间
    private String timeValue; // 传给时间选择器的时间
    private String mSimei;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), PushSwitchActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPushSwitchComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_push_switch;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_message_push));
        mSimei = MyApplication.getMyApp().getSimei();

        onShowDoNotDisturbPush();

        getUserInfo();
    }

    /**
     * 获取单用户信息，包含推送开关，免打扰时间等
     */
    private void getUserInfo(){
        UidInfoPutBean bean = new UidInfoPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Uid_Info);
        bean.setModule(ModuleValueService.Module_For_Uid_Info);

        if (getPresenter() != null){
            getPresenter().getUidInfo(bean);
        }
    }

    /**
     * 显示推送开关状态
     */
    private void onShowPushSwitch() {
        ivMessagePush.setImageResource(isPushSwitch ?  R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 显示免打扰时间段开关状态
     */
    private void onShowDoNotDisturbPush() {
        ivDoNotDisturbPush.setImageResource(isDoNotDisturbPush ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        if (isDoNotDisturbPush) {
            llStartTime.setVisibility(View.VISIBLE);
            llEndTime.setVisibility(View.VISIBLE);
            viewTime.setVisibility(View.VISIBLE);
        } else {
            llStartTime.setVisibility(View.GONE);
            llEndTime.setVisibility(View.GONE);
            viewTime.setVisibility(View.GONE);
            startTime = "";
            endTime = "";
            tvStartTime.setText(startTime);
            tvEndTime.setText(endTime);
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_message_push, R.id.iv_do_not_disturb_push, R.id.ll_start_time, R.id.ll_end_time, R.id.btn_confirm})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.iv_message_push: // 消息推送开关
                    onPushSwitchSet();
                    break;
                case R.id.iv_do_not_disturb_push: // 免打扰时间段
                    onDoNotDisturbPushSet();
                    break;
                case R.id.ll_start_time:
                    isStartTime = true;
                    onTimeSelect();
                    break;
                case R.id.ll_end_time:
                    isStartTime = false;
                    onTimeSelect();
                    break;
                case R.id.btn_confirm:
                    submitDoNotDisturbPush();
                    break;
            }
        }
    }

    /**
     * 消息推送开关
     */
    private void onPushSwitchSet() {
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setPush_switch(!isPushSwitch);

        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null) {
            getPresenter().submitPushSwitch(bean, true);
        }
    }

    /**
     * 免打扰时间段
     */
    private void onDoNotDisturbPushSet() {
        isDoNotDisturbPush = !isDoNotDisturbPush;
        onShowDoNotDisturbPush();
    }

    /**
     * 时间选择
     */
    private void onTimeSelect() {
        if (isStartTime) {
            timeValue = startTime;
        } else {
            timeValue = endTime;
        }
        SelectTimeDialog timeDialog = new SelectTimeDialog();
        timeDialog.show(getSupportFragmentManager(), timeValue, new SelectTimeDialog.onSelectTimeChange() {
            @Override
            public void onSelectTime(String time) {
                if (isStartTime) {
                    startTime = time + ":00";
                    tvStartTime.setText(startTime);
                } else {
                    endTime = time + ":00";
                    tvEndTime.setText(endTime);
                }
            }
        });
    }

    /**
     * 提交免打扰时间段设置
     */
    private void submitDoNotDisturbPush() {
        if (isDoNotDisturbPush) {
            if (TextUtils.isEmpty(startTime)) {
                ToastUtils.showShort(getString(R.string.txt_start_time_select));
                return;
            }
            if (TextUtils.isEmpty(endTime)) {
                ToastUtils.showShort(getString(R.string.txt_end_time_select));
                return;
            }
            if (startTime.equals(endTime)){
                ToastUtils.showShort(getString(R.string.txt_time_error));
                return;
            }
        }

        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        infoBean.setSstart_time(startTime);
        infoBean.setSend_time(endTime);

        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);

        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null) {
            getPresenter().submitPushSwitch(bean, false);
        }
    }

    /**
     * 启动极光推送
     */
    @SuppressLint("LogNotTimber")
    private void initJPushAliasAndTag() {
        // 注册极光推送，设置Tag
        String familyid = ConstantValue.getPushFamily();
        if (!TextUtils.isEmpty(familyid)) {
            Set<String> tagSet = new LinkedHashSet<String>();
            tagSet.add(familyid);
//        tagSet.add("android");
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBean.action = ACTION_SET;
            sequence++;
            tagAliasBean.tags = tagSet;
            tagAliasBean.isAliasAction = false;
            TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
        } else {
            Log.e("JPush", "Jpush set error : tag = null");
        }
    }

    /**
     * 删除极光推送
     */
    private void initDeleteJPushAliasAndTag() {
        String familyid = ConstantValue.getPushFamily();
        Set<String> tagSet = new LinkedHashSet<String>();
        tagSet.add(familyid);
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_DELETE;
        sequence++;
        tagAliasBean.tags = tagSet;
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
    }

    @Override
    public void getUidInfoSuccess(UidInfoResultBean uidInfoResultBean) {
        isPushSwitch = uidInfoResultBean.isPush_switch();
        if (MyApplication.getMMKVUtils().getBoolean(ConstantValue.Push_Switch, false) != isPushSwitch){
            if (isPushSwitch) {
                initJPushAliasAndTag();
            } else {
                initDeleteJPushAliasAndTag();
            }
        }
        onShowPushSwitch();
        MyApplication.getMMKVUtils().put(ConstantValue.Push_Switch, uidInfoResultBean.isPush_switch());

        if (!TextUtils.isEmpty(uidInfoResultBean.getSstart_time())
                && !TextUtils.isEmpty(uidInfoResultBean.getSend_time())){
            if ((uidInfoResultBean.getSstart_time().equals("00:00:00") && !uidInfoResultBean.getSend_time().equals("00:00:00"))
                    || (!uidInfoResultBean.getSstart_time().equals("00:00:00") && uidInfoResultBean.getSend_time().equals("00:00:00"))
                    || (!uidInfoResultBean.getSstart_time().equals("00:00:00") && !uidInfoResultBean.getSend_time().equals("00:00:00")))
            startTime = uidInfoResultBean.getSstart_time();
            endTime = uidInfoResultBean.getSend_time();
        }
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        isDoNotDisturbPush = !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime);
        onShowDoNotDisturbPush();
    }

    @Override
    public void submitPushSwitchSuccess(boolean pushSwitch, boolean isSetPushSwitch) {
        ToastUtils.showShort(getString(R.string.txt_set_success));
        if (isSetPushSwitch){
            isPushSwitch = pushSwitch;
            if (isPushSwitch) {
                initJPushAliasAndTag();
            } else {
                initDeleteJPushAliasAndTag();
            }
            MyApplication.getMMKVUtils().put(ConstantValue.Push_Switch, isPushSwitch);
            onShowPushSwitch();
        }
    }
}

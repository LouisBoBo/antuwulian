package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerBindMobileNextComponent;
import com.slxk.gpsantu.mvp.contract.BindMobileNextContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.PhoneAreaResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneCodeResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.ModifyPasswordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneCodePutBean;
import com.slxk.gpsantu.mvp.presenter.BindMobileNextPresenter;
import com.slxk.gpsantu.mvp.ui.view.ClearEditText;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.MD5Utils;
import com.slxk.gpsantu.mvp.utils.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 绑定手机号码
 * <p>
 * Created by MVPArmsTemplate on 02/25/2021 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BindMobileNextActivity extends BaseActivity<BindMobileNextPresenter> implements BindMobileNextContract.View {

    @BindView(R.id.edt_code)
    ClearEditText edtCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.ll_mobile_code)
    LinearLayout llMobileCode;

    private String mPhoneZone = "86"; // 电话号码区号
    private Disposable disposable; // 验证码倒计时
    private String mSid;
    private String mPassword; // 密码
    private String mLoginFlag = ""; // 登录标识:用户登录or设备号登录
    private String mPhone = "";
    private String mAccount = "";
    private boolean mChangeBind;  //是否解绑
    private boolean isModifyPassword = false; // 是否需要修改密码

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindMobileNextComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_mobile_next;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mLoginFlag = MyApplication.getMMKVUtils().getString(ConstantValue.USER_LOGIN_TYPE);
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().hasExtra("phone")) {
                mPhone = getIntent().getStringExtra("phone");
            }
            if (getIntent().hasExtra("phoneZone")) {
                mPhoneZone = getIntent().getStringExtra("phoneZone");
            }
            if (getIntent().hasExtra("account")) {
                mAccount = getIntent().getStringExtra("account");
            }
            if (getIntent().hasExtra("password")) {
                mPassword = getIntent().getStringExtra("password");
            }
            if (getIntent().hasExtra("sid")) {
                mSid = getIntent().getStringExtra("sid");
            }
            if (getIntent().hasExtra("changeBind")) {
                mChangeBind = getIntent().getBooleanExtra("changeBind", false);
            }
        }
        isModifyPassword = MyApplication.getMMKVUtils().getBoolean(ConstantValue.Is_Modify_Password);
        setTitle(getString(R.string.txt_bind_mobile));
        btnBind.setText(getText(R.string.txt_bind_submit));
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

    @OnClick({R.id.tv_send_code, R.id.btn_bind})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.tv_send_code: // 发送验证码
                    onSendPhoneCode();
                    break;
                case R.id.btn_bind: // 绑定
                    hideKeyboard(edtCode);
                    if (mChangeBind) {
                        submitBindMobile(true);
                    } else {
                        submitBindMobile(false);
                    }

                    break;
            }
        }
    }

    /**
     * 发送验证码
     */
    private void onSendPhoneCode() {
        if (mPhone.length() == 0) return;
        PhoneCodePutBean.ParamsBean paramsBean = new PhoneCodePutBean.ParamsBean();
        paramsBean.setCode(Api.Mob_Module_Code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setPhone(mPhone);
        paramsBean.setZone(mPhoneZone);

        PhoneCodePutBean bean = new PhoneCodePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Phone_Code);
        bean.setModule(ModuleValueService.Module_For_Phone_Code);

        if (getPresenter() != null) {
            getPresenter().getPhoneCode(bean);
        }
    }

    /**
     * 提交绑定
     *
     * @param isChangeBind 是否更换手机号码绑定的imei号为当前imei号,默认false
     */
    private void submitBindMobile(boolean isChangeBind) {
        String code = edtCode.getText().toString().trim();
        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        infoBean.setPhone(mPhone);
        if (isModifyPassword) {
            //同时提示绑定手机号和修改密码，密码填入默认值
            infoBean.setPwd("123456");
        }
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        if (isChangeBind) {
            paramsBean.setChange_bind(true);
        }
        paramsBean.setCode(code);
        paramsBean.setZone(mPhoneZone);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setPwd_md5(MD5Utils.getMD5Encryption(mPassword));

        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null) {
            getPresenter().submitBindMobile(bean, mSid, isChangeBind);
        }
    }

    @Override
    public void getPhoneAreaSuccess(PhoneAreaResultBean phoneAreaResultBean) {
    }

    @Override
    public void getPhoneCodeSuccess(PhoneCodeResultBean phoneCodeResultBean) {
        ToastUtils.showShort(getString(R.string.errcode_success));
        countDownTime();
    }

    @Override
    public void submitBindMobileSuccess(BaseBean baseBean, boolean isChangeBind) {
        MyApplication.getMMKVUtils().put(ConstantValue.Is_Need_Check, false);
        if (isChangeBind) {
            ToastUtils.showShort(getString(R.string.txt_bind_phone_success_two));
        } else {
            ToastUtils.showShort(getString(R.string.txt_bind_success));
        }
        Intent intent = new Intent(BindMobileNextActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 获取验证码倒计时
     */
    public void countDownTime() {
        final int count = 60;//倒计时10秒
        //ui线程中进行控件更新
        Observable<Long> countDownObservable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        tvSendCode.setEnabled(false);
                    }
                });

        //回复原来初始状态
        disposable = countDownObservable.subscribe(new Consumer<Long>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void accept(Long aLong) throws Exception {
                tvSendCode.setText(aLong + "s");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //回复原来初始状态
                tvSendCode.setEnabled(true);
                tvSendCode.setText(getString(R.string.txt_get_code));
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

}

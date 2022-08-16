package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerForgetPasswordNextComponent;
import com.slxk.gpsantu.mvp.contract.ForgetPasswordNextContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.ForgetPasswordResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneAreaResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneCodeResultBean;
import com.slxk.gpsantu.mvp.model.putbean.ForgetPasswordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneAreaPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneCodePutBean;
import com.slxk.gpsantu.mvp.presenter.ForgetPasswordNextPresenter;
import com.slxk.gpsantu.mvp.ui.view.ClearEditText;

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
 * Description: 忘记密码的下一步
 * <p>
 * Created by MVPArmsTemplate on 10/21/2020 10:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ForgetPasswordNextActivity extends BaseActivity<ForgetPasswordNextPresenter> implements ForgetPasswordNextContract.View {

    @BindView(R.id.edt_code)
    ClearEditText edtCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.edt_password)
    ClearEditText edtPassword;
    @BindView(R.id.edt_password_confirm)
    ClearEditText edtPasswordConfirm;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private String mPhoneZone = "86"; // 电话号码区号
    private Disposable disposable; // 验证码倒计时
    private String mPhone = "";
    private String mFlag;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), ForgetPasswordNextActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForgetPasswordNextComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forget_password_next;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_retrieve_password));
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().hasExtra("phone")) {
                mPhone = getIntent().getStringExtra("phone");
            }
            if (getIntent().hasExtra("phoneZone")) {
                mPhoneZone = getIntent().getStringExtra("phoneZone");
            }
            if (getIntent().hasExtra("flag")) {
                mFlag = getIntent().getStringExtra("flag");
            }
        }
    }

    /**
     * 获取手机号码国际区号
     */
    private void getPhoneArea() {
        PhoneAreaPutBean.ParamsBean paramsBean = new PhoneAreaPutBean.ParamsBean();
        PhoneAreaPutBean bean = new PhoneAreaPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Phone_Area);
        bean.setModule(ModuleValueService.Module_For_Phone_Area);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getPhoneArea(bean);
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

    @OnClick({R.id.tv_send_code, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code: // 发送验证码
                onSendPhoneCode();
                break;
            case R.id.btn_confirm: // 提交
                submitForgetPassword();
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void onSendPhoneCode() {
        PhoneCodePutBean.ParamsBean paramsBean = new PhoneCodePutBean.ParamsBean();
        paramsBean.setCode(Api.Mob_Module_Code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setPhone(mPhone);
        paramsBean.setZone(mPhoneZone);

        PhoneCodePutBean bean = new PhoneCodePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Phone_Code);
        bean.setModule(ModuleValueService.Module_For_Phone_Code);

        if (getPresenter() != null){
            getPresenter().getPhoneCode(bean);
        }
    }

    /**
     * 确认提交
     */
    private void submitForgetPassword() {
        if (mPhone.length() == 0) return;
        String code = edtCode.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort(getString(R.string.txt_input_code));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort(getString(R.string.txt_input_new_password));
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            ToastUtils.showShort(getString(R.string.txt_input_new_password_confirm));
            return;
        }
        if (!passwordConfirm.equals(password)) {
            ToastUtils.showShort(getString(R.string.txt_password_error_hint));
            return;
        }

        ForgetPasswordPutBean.ParamsBean paramsBean = new ForgetPasswordPutBean.ParamsBean();
        paramsBean.setCode(code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setNew_pwd(password);
        paramsBean.setPhone(mPhone);
        paramsBean.setZone(mPhoneZone);
        paramsBean.setType(Api.App_Type);
        if (!TextUtils.isEmpty(mFlag)) {
            paramsBean.setFlag(mFlag);
        }
        ForgetPasswordPutBean bean = new ForgetPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Forget_Password);
        bean.setModule(ModuleValueService.Module_For_Forget_Password);

        if (getPresenter() != null){
            getPresenter().submitForgetPassword(bean);
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
    public void submitForgetPasswordSuccess(ForgetPasswordResultBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_modify_success));
        launchActivity(LoginActivity.newInstance(0));
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

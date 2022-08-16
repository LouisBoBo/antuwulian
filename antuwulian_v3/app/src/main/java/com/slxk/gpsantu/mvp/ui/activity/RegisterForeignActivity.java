package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.di.component.DaggerRegisterForeignComponent;
import com.slxk.gpsantu.mvp.contract.RegisterForeignContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.RegisterResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.EmailCodePutBean;
import com.slxk.gpsantu.mvp.model.putbean.RegisterPutBean;
import com.slxk.gpsantu.mvp.presenter.RegisterForeignPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.view.ClearEditText;
import com.blankj.utilcode.util.ToastUtils;
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
 * Description: 注册-国外用户
 * <p>
 * Created by MVPArmsTemplate on 08/17/2021 16:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RegisterForeignActivity extends BaseActivity<RegisterForeignPresenter> implements RegisterForeignContract.View {

    @BindView(R.id.edt_account)
    ClearEditText edtAccount;
    @BindView(R.id.edt_password)
    ClearEditText edtPassword;
    @BindView(R.id.edt_password_confirm)
    ClearEditText edtPasswordConfirm;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private Disposable disposable; // 验证码倒计时
    private String mPassword; // 密码

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterForeignComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register_foreign;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_register_account));
        if (getIntent() != null){
            if (getIntent().hasExtra("password")){
                mPassword = getIntent().getStringExtra("password");
            }
        }
        if (!TextUtils.isEmpty(mPassword)){
            edtPassword.setText(mPassword);
            edtPasswordConfirm.setText(mPassword);
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

    @OnClick({R.id.btn_register})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())){
            switch (view.getId()) {
                case R.id.btn_register:
                    submitRegister();
                    break;
            }
        }
    }

    /**
     * 提交注册
     */
    private void submitRegister() {
        String account = edtAccount.getText().toString().trim().toLowerCase();
        String password = edtPassword.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort(getString(R.string.txt_input_account_two_hint));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort(getString(R.string.txt_password_hint));
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            ToastUtils.showShort(getString(R.string.txt_input_password_confirm));
            return;
        }
        if (!passwordConfirm.equals(password)) {
            ToastUtils.showShort(getString(R.string.txt_password_error_hint));
            return;
        }

        RegisterPutBean.ParamsBean.InfoBean infoBean = new RegisterPutBean.ParamsBean.InfoBean();
        infoBean.setAccount(account);
        infoBean.setPwd(password);

        RegisterPutBean.ParamsBean paramsBean = new RegisterPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        paramsBean.setType(Api.App_Type);
        paramsBean.setCheck_phone(false);
        if (Utils.localeLanguage().length() != 0)
            paramsBean.setLang(Utils.localeLanguage());

        RegisterPutBean bean = new RegisterPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Register);
        bean.setModule(ModuleValueService.Module_For_Register);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitRegister(bean);
        }
    }



    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void submitRegisterSuccess(RegisterResultBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_register_success));
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("account",edtAccount.getText().toString().trim());
        intent.putExtra("password", edtPassword.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    @Override
    public void getEmailCodeSuccess(BaseBean baseBean) {
        ToastUtils.showShort(getString(R.string.errcode_success));
    }
}

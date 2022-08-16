package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.di.component.DaggerRegisterComponent;
import com.slxk.gpsantu.mvp.contract.RegisterContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.PhoneAreaResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneCodeResultBean;
import com.slxk.gpsantu.mvp.model.bean.RegisterResultBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneAreaPutBean;
import com.slxk.gpsantu.mvp.model.putbean.RegisterPutBean;
import com.slxk.gpsantu.mvp.presenter.RegisterPresenter;
import com.slxk.gpsantu.mvp.ui.view.ClearEditText;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.PhoneAreaDialog;
import com.slxk.gpsantu.mvp.weiget.PhoneHasBindDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2020 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.edt_mobile)
    ClearEditText edtMobile; // 手机号码
    @BindView(R.id.btn_register)
    Button btnRegister; // 注册
    @BindView(R.id.tv_phone_area)
    TextView tvPhoneArea; // 手机号码区号
    @BindView(R.id.ll_mobile_area)
    LinearLayout llMobileArea; // 手机号码区号
    @BindView(R.id.ll_phone)
    LinearLayout llPhone; // 输入手机号


    private String mPhoneZone = "86"; // 电话号码区号
    private List<PhoneAreaResultBean.ItemsBean> phoneAreaBeans; // 手机号码国际区号
    private Disposable disposable; // 验证码倒计时

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_register_account));
        phoneAreaBeans = new ArrayList<>();
        tvPhoneArea.setText("+" + mPhoneZone);
        String mPhone = "";
        if (getIntent() != null) {
            if (getIntent().hasExtra("phone")) {
                mPhone = getIntent().getStringExtra("phone");
            }
        }
        if (!TextUtils.isEmpty(mPhone)) {
            edtMobile.setText(mPhone);
        }
        getPhoneArea();
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
//        checkNotNull(message);
//        ArmsUtils.snackbarText(message);
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

    @OnClick({R.id.btn_register, R.id.ll_mobile_area})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.btn_register: // 注册
                    submitRegister();
                    break;
                case R.id.ll_mobile_area: // 选择手机号码国际区号
                    onPhoneAreaSelect();
                    break;
            }
        }
    }

    /**
     * 选择手机号码国际区号
     */
    private void onPhoneAreaSelect() {
        if (phoneAreaBeans.size() == 0) {
            getPhoneArea();
        } else {
            PhoneAreaDialog dialog = new PhoneAreaDialog();
            dialog.show(getSupportFragmentManager(), phoneAreaBeans, new PhoneAreaDialog.onPhoneAreaChange() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onPhoneArea(int zone) {
                    mPhoneZone = zone + "";
                    tvPhoneArea.setText("+" + mPhoneZone);
                }
            });
        }
    }

    /**
     * 提交注册
     */
    private void submitRegister() {
        String mobile = edtMobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
           ToastUtils.showShort(getString(R.string.txt_input_phone_hint));
            return;
        }
        RegisterPutBean.ParamsBean.InfoBean infoBean = new RegisterPutBean.ParamsBean.InfoBean();
        infoBean.setPhone(mobile);
        infoBean.setPwd("123456");
        RegisterPutBean.ParamsBean paramsBean = new RegisterPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        paramsBean.setCode("000000");
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setType(Api.App_Type);
        paramsBean.setZone(mPhoneZone);

        RegisterPutBean bean = new RegisterPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Register);
        bean.setModule(ModuleValueService.Module_For_Register);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitRegister(bean);
        }
    }

    @Override
    public void submitRegisterSuccess(RegisterResultBean baseBean) {
        if (baseBean.getErrcode() == Api.Mobile_Code_Error) {
            Intent intent = new Intent(this, CreatePasswordActivity.class);
            intent.putExtra("phone", edtMobile.getText().toString().trim());
            intent.putExtra("phoneZone", mPhoneZone);
            startActivity(intent);
        } else if (baseBean.getErrcode() == Api.Mobile_Bind_Used) { //手机号已绑定
            onPhoneHasBinding();
        }

    }

    /**
     * 该手机号码已经绑定了账号
     */
    private void onPhoneHasBinding() {
        PhoneHasBindDialog dialog = new PhoneHasBindDialog();
        dialog.show(getSupportFragmentManager(), 0, new PhoneHasBindDialog.onPhoneHasBindChange() {
            @Override
            public void onRegister() {
                edtMobile.setText("");
            }

            @Override
            public void onUnbind() {
//                submitBindMobile(true);
            }

            @Override
            public void onLogin() {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("account", edtMobile.getText().toString().trim());
                intent.putExtra("password", "");
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void getPhoneAreaSuccess(PhoneAreaResultBean phoneAreaResultBean) {
        phoneAreaBeans.clear();
        if (phoneAreaResultBean.getItems() != null)
            phoneAreaBeans.addAll(phoneAreaResultBean.getItems());
    }

    @Override
    public void getPhoneCodeSuccess(PhoneCodeResultBean phoneCodeResultBean) {
       ToastUtils.showShort(getString(R.string.errcode_success));
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

}

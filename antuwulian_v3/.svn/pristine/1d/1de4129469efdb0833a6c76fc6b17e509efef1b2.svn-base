package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerBindMobileComponent;
import com.slxk.gpsantu.mvp.contract.BindMobileContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.PhoneAreaResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneCodeResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.ModifyPasswordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneAreaPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneCodePutBean;
import com.slxk.gpsantu.mvp.presenter.BindMobilePresenter;
import com.slxk.gpsantu.mvp.ui.view.ClearEditText;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.MD5Utils;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.PhoneAreaDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import com.slxk.gpsantu.mvp.weiget.PhoneHasBindDialog;

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
public class BindMobileActivity extends BaseActivity<BindMobilePresenter> implements BindMobileContract.View {


    @BindView(R.id.tv_phone_area)
    TextView tvPhoneArea;
    @BindView(R.id.ll_mobile_area)
    LinearLayout llMobileArea;
    @BindView(R.id.edt_mobile)
    ClearEditText edtMobile;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.ll_mobile)
    LinearLayout llMobile;


    private String mPhoneZone = "86"; // 电话号码区号
    private List<PhoneAreaResultBean.ItemsBean> phoneAreaBeans; // 手机号码国际区号
    private String mSid;
    private String mPassword; // 密码
    private String mLoginFlag = ""; // 登录标识:用户登录or设备号登录
    private boolean isModifyPassword = false; // 是否需要修改密码
    private String mobilePhone;
    private String mAccount;



    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindMobileComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_mobile;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_bind_mobile));
        phoneAreaBeans = new ArrayList<>();
        tvPhoneArea.setText("+" + mPhoneZone);
        mSid = getIntent().getStringExtra("sid");
        mPassword = getIntent().getStringExtra("password");
        mAccount = getIntent().getStringExtra("account");
        mLoginFlag = MyApplication.getMMKVUtils().getString(ConstantValue.USER_LOGIN_TYPE);
        isModifyPassword = MyApplication.getMMKVUtils().getBoolean(ConstantValue.Is_Modify_Password);
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

    @OnClick({R.id.ll_mobile_area, R.id.btn_bind})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.ll_mobile_area: // 选择区号
                    hideKeyboard(edtMobile);
                    onPhoneAreaSelect();
                    break;
                case R.id.btn_bind: // 绑定
                    hideKeyboard(edtMobile);
                    submitBindMobile(false);
                    break;
            }
        }
    }

    /**
     * 提交绑定
     *
     * @param isChangeBind 是否更换手机号码绑定的imei号为当前imei号,默认false
     */
    private void submitBindMobile(boolean isChangeBind) {
        mobilePhone  = edtMobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobilePhone)) {
            ToastUtils.showShort(getString(R.string.txt_input_phone_hint));
            return;
        }
        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        infoBean.setPhone(mobilePhone);
        if (isModifyPassword) {
            //同时提示绑定手机号和修改密码，密码填入默认值
            infoBean.setPwd("123456");
        }
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        if (isChangeBind) {
            paramsBean.setChange_bind(true);
        }
        paramsBean.setCode("000000");
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
    public void submitBindMobileSuccess(BaseBean baseBean, boolean isChangeBind) {
        if (baseBean.getErrcode() == Api.Mobile_Code_Error) { //验证码错误，则进入下一页正式绑定页面
            toBindNextActivity(false);
        } else if (baseBean.getErrcode() == Api.Mobile_Bind_Used) { //已绑定
            if (mLoginFlag.equals(ResultDataUtils.Login_type_Device) || mLoginFlag.equals(ResultDataUtils.Login_type_Phone_Device)) {
                onPhoneHasBinding();
            } else {
                ToastUtils.showShort(baseBean.getError_message());
            }
        }
    }

    /**
     * 绑定手机号的下一页 或者解绑设备，并绑定新的设备
     * @param isChanged
     */
    private void toBindNextActivity(boolean isChanged) {
        Intent intent = new Intent(this, BindMobileNextActivity.class);
        if (isModifyPassword) {
            intent.putExtra("password", "123456");
        } else {
            intent.putExtra("password", mPassword);
        }
        intent.putExtra("sid", mSid);
        intent.putExtra("account", mAccount);
        intent.putExtra("phone", mobilePhone);
        intent.putExtra("changeBind", isChanged);
        startActivity(intent);
    }

    /**
     * 该手机号码已经绑定了设备
     */
    private void onPhoneHasBinding() {
        PhoneHasBindDialog dialog = new PhoneHasBindDialog();
        dialog.show(getSupportFragmentManager(), 1, new PhoneHasBindDialog.onPhoneHasBindChange() {
            @Override
            public void onRegister() {
                onToRegister();
            }

            @Override
            public void onUnbind() {
                toBindNextActivity(true);
            }

            @Override
            public void onLogin() {
                Intent intent = new Intent(BindMobileActivity.this, LoginActivity.class);
                intent.putExtra("account", edtMobile.getText().toString().trim());
                intent.putExtra("password", "");
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 去注册
     */
    private void onToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

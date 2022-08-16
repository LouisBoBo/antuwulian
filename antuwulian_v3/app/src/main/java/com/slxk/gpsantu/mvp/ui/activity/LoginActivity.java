package com.slxk.gpsantu.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mob.MobSDK;
import com.mob.secverify.GetTokenCallback;
import com.mob.secverify.OAuthPageEventCallback;
import com.mob.secverify.PageCallback;
import com.mob.secverify.PreVerifyCallback;
import com.mob.secverify.SecVerify;
import com.mob.secverify.UiLocationHelper;
import com.mob.secverify.common.exception.VerifyException;
import com.mob.secverify.datatype.VerifyResult;
import com.mob.secverify.exception.VerifyErr;
import com.mob.secverify.ui.component.CommonProgressDialog;
import com.mob.tools.utils.DeviceHelper;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerLoginComponent;
import com.slxk.gpsantu.mvp.contract.LoginContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.CheckAppUpdateBean;
import com.slxk.gpsantu.mvp.model.bean.LoginResultBean;
import com.slxk.gpsantu.mvp.model.bean.RegisterResultBean;
import com.slxk.gpsantu.mvp.model.putbean.CheckAppUpdatePutBean;
import com.slxk.gpsantu.mvp.model.putbean.LoginPutBean;
import com.slxk.gpsantu.mvp.model.putbean.ModifyPasswordPutBean;
import com.slxk.gpsantu.mvp.presenter.LoginPresenter;
import com.slxk.gpsantu.mvp.ui.view.ClearEditText;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.CustomizeUtils;
import com.slxk.gpsantu.mvp.utils.IPUtils;
import com.slxk.gpsantu.mvp.utils.MD5Utils;
import com.slxk.gpsantu.mvp.utils.MacAddressUtils;
import com.slxk.gpsantu.mvp.utils.ManufacturerUtil;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.BindForeignDialog;
import com.slxk.gpsantu.mvp.weiget.BindMobileDialog;
import com.slxk.gpsantu.mvp.weiget.LoginAccountListDialog;
import com.slxk.gpsantu.mvp.weiget.PhoneHasBindDialog;
import com.slxk.gpsantu.mvp.weiget.PhoneModifyDialog;
import com.slxk.gpsantu.mvp.weiget.PrivacyPolicyDialog;
import com.slxk.gpsantu.mvp.weiget.ServiceIPCheckDialog;
import com.slxk.gpsantu.mvp.weiget.UploadAppDialog;
import com.umeng.commonsdk.UMConfigure;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 用户登录
 * <p>
 * Created by MVPArmsTemplate on 10/17/2020 17:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.edt_account)
    ClearEditText edtAccount; // 账号或设备号
    @BindView(R.id.edt_password)
    ClearEditText edtPassword; // 密码
    @BindView(R.id.iv_remember_password)
    ImageView ivRememberPassword; // 记住密码
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword; // 忘记密码
    @BindView(R.id.btn_login)
    Button btnLogin; // 登录
    @BindView(R.id.tv_register)
    TextView tvRegister; // 注册
    @BindView(R.id.tv_agreement)
    TextView tvAgreement; // 查看用户协议
    @BindView(R.id.tv_version)
    TextView tvVersion; // 版本号
    @BindView(R.id.iv_sqr)
    ImageView ivSqr; // 扫码
    @BindView(R.id.iv_agreement)
    ImageView ivAgreement; // 隐私政策协议
    @BindView(R.id.cb_password_visible)
    AppCompatCheckBox cbPsdVisible;

    private static final int INTENT_SCAN_QR = 10; // 扫描二维码/条形码回调
    private static final int Intent_Register = 11; // 注册
    private static final int Intent_Bind_Mobile = 12; // 绑定手机
    private static final int PERMISSON_REQUESTCODE = 1; // 获取权限回调码
    // 判断是否需要检测权限，防止不停的弹框
    private boolean isNeedCheck = true;
    private boolean isAuth = true; // 权限不足弹窗只弹一次，防止重复弹出

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

    // 百度定位
    private LocationClient mLocationClient;
    private Double mLatitude = 0.0;
    private Double mLongitude = 0.0;
    private String mobileIpAddress; // 手机当前网络ip地址
    private String mobileCName; // wifi网络当前所属区域
    private String mobileImei; // 手机的imei号，唯一编码
    private String mobileIccid; // 获取手机的iccid
    private String mobileInfo = ""; // 手机信息，品牌，Android版本等等
    private String wifiMacAddress = ""; // 如果手机连接了wifi，获取路由器的mac地址

    private String mLoginInfo; // 登录信息，包含定位信息，手机信息，当前网络ip信息
    private String mLoginFlag = ""; // 登录标识:用户登录or设备号登录
    private String mPushMpm = ResultDataUtils.Push_XiaoMi; // 推送通道 0-极光(默认) 1-华为
    private int isAgreePrivacy = 0; //  是否是首次进入app，0：是，1：否
    private boolean isSavePassword = false; // 是否保存密码
    private boolean isAgreement = false; // 是否勾选了同意用户隐私政策协议
    private String mPassword = ""; // 输入的密码
    private String mAccount = ""; // 输入的账号
    private boolean isBeforeAccount = false; // 当前登录账号是否和上一个登录账号相同
    private boolean isMergeAccount = false; // 是否需要合并账号

    private MyLocationListener myLocationListener; // 定位监听

    // 版本更新
    private static final int INSTALL_PERMISS_CODE = 101; // 允许安装位置应用回调码
    private static final int INSTALL_APK_RESULT = 102; // apk安装情况，是否完成安装回调
    private String mFilePath; // 版本更新下载url地址
    private boolean isForceApp = false; // 是否需要强制更新App，false-不强制更新 true-强制更新
    private boolean isGetUpdateApp = false; // 是否请求过版本更新
    private int versionCode; // 当前版本标识
    private static final String Login_Key = "login.key";
    private int loginIntent = 0; // 0:默认跳转，1：启动页跳转，从哪个位置跳转过来的，如果是启动页，则判断请求版本更新接口

    // 华为推送
    private String mHuaweiToken = ""; // 华为推送token
    private final static String CODELABS_ACTION = "com.slxk.gpsantu.intent";
    private MyReceiver receiver;
    private int mapType; //0：百度地图，1：高德地图，2：谷歌地图(int)

    private boolean isVerifySupport = false;
    private boolean isPreVerifyDone = false; // 预登录
    private int verifyTo = 0 ; // 0 注册 1 一键绑定手机号
    private String mSid;
    private String opToken = "";
    private String operator = "";
    private String token = "";
    private String md5 = "";

    private static final String Modify_Phone = "modifyPhone";
    private static final String Phone_Num = "phoneNum";
    private boolean mModifyPhone;
    private String  mPhoneNum;

    public static Intent newInstance(int type) {
        Intent intent = new Intent(MyApplication.getMyApp(), LoginActivity.class);
        intent.putExtra(Login_Key, type);
        return intent;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login_at;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (Utils.isCN()) {
            mapType = MyApplication.getMMKVUtils().getInt(ConstantValue.MAP_TYPE_NEW, 0);
        } else {
            if (Utils.isNotInstallService(this)) {
                mapType = MyApplication.getMMKVUtils().getInt(ConstantValue.MAP_TYPE_NEW, 0);
            } else {
                mapType = MyApplication.getMMKVUtils().getInt(ConstantValue.MAP_TYPE_NEW, 2);
            }
        }
        tvAgreement.setText(Html.fromHtml(getString(R.string.txt_privacy_policy_login)));
        tvRegister.setText(Html.fromHtml(getString(R.string.txt_register_account_login)));
        tvVersion.setText("V" + AppUtils.getAppVersionName());
        cbPsdVisible.setOnCheckedChangeListener(this);
        MyApplication.getMyApp().clearData();
        if (getIntent() != null) {
            loginIntent = getIntent().getIntExtra(Login_Key, 0);
            mModifyPhone = getIntent().getBooleanExtra(Modify_Phone, false);
            mPhoneNum = getIntent().getStringExtra(Phone_Num);
        }

        versionCode = AppUtils.getAppVersionCode();
        isGetUpdateApp = MyApplication.getMMKVUtils().getBoolean(ConstantValue.Is_Get_Update_App, false);
        isAgreePrivacy = MyApplication.getMMKVUtils().getInt(ConstantValue.IS_FIRST, 0);
        isAgreement = MyApplication.getMMKVUtils().getBoolean(ConstantValue.IS_USER_AGREEMENT, false);
        isSavePassword = ConstantValue.isSavePassword();
        mobileInfo = Utils.getMobilePackageInfo(this);
        edtAccount.setText(ConstantValue.getAccount());
        if (isSavePassword) {
            edtPassword.setText(ConstantValue.getPassword());
        }

        if (isAgreePrivacy == 1) {
            checkPermissions(needPermissions);
            //写权限通过之后再请求更新
            if (!isGetUpdateApp && loginIntent == 1) {
                checkAppUpdate();
            }
        } else {
            onShowPrivacyPolicy();
        }

        onSaveAgreementClick();
        onSavePasswordClick();
        onBaiduMapShow();
        getIPAddress();

        if (ManufacturerUtil.isHuaWeiPush(this)) {
            receiver = new MyReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(CODELABS_ACTION);
            registerReceiver(receiver, filter);
            mPushMpm = ResultDataUtils.Push_HuaWei;
            getToken();
        } else if (ManufacturerUtil.isXiaomi()) {
            mPushMpm = ResultDataUtils.Push_XiaoMi;
        }

        if (mModifyPhone) { //有修改手机号，弹框提示
            PhoneModifyDialog phoneModifyDialog = new PhoneModifyDialog();
            String phoneText = String.format(getString(R.string.txt_modify_phone_txt), mPhoneNum);
            phoneModifyDialog.show(getSupportFragmentManager(), phoneText);
        }
    }

    //检查更新所需权限
    private boolean checkPermissionUpdate() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 隐私政策协议告知
     */
    private void onShowPrivacyPolicy() {
        final PrivacyPolicyDialog dialog = new PrivacyPolicyDialog();
        dialog.show(getSupportFragmentManager(), new PrivacyPolicyDialog.onPrivacyPolicyChange() {
            @Override
            public void onPrivacyPolicy(boolean isPrivacy) {
                // 0：不同意，1：同意
                MyApplication.getMMKVUtils().put(ConstantValue.IS_FIRST, isPrivacy ? 1 : 0);
                dialog.dismiss();
                if (!isPrivacy) {
                    finish();
                } else {
                    checkPermissions(needPermissions);
                    MyApplication.getMMKVUtils().put(ConstantValue.Umeng_Init, true);
                    // 友盟隐私政策提交
                    UMConfigure.submitPolicyGrantResult(getApplicationContext(), true);
                }
            }

            @Override
            public void onSeePrivacyPolicy() {
                onSeePrivacyAgreement();
            }
        });
    }

    /**
     * 查看用户隐私政策协议
     */
    private void onSeePrivacyAgreement() {
        String url;
        if (Locale.getDefault().toString().contains("zh")) {
            url = Api.Privacy_Policy;
        } else if (Locale.getDefault().toString().contains("vi")) {
            url = Api.Privacy_Policy_VI;
        } else {
            url = Api.Privacy_Policy_EN;
        }
        launchActivity(WebViewActivity.newInstance(getString(R.string.txt_privacy_policy_user), url));
    }


    /**
     * 检测app版本更新
     */
    private void checkAppUpdate() {
        CheckAppUpdatePutBean.ParamsBean paramsBean = new CheckAppUpdatePutBean.ParamsBean();
        paramsBean.setVersion(versionCode);
        paramsBean.setApp_type(Api.App_Update_Type);
        if (Utils.localeLanguage().length() != 0)
            paramsBean.setLang(Utils.localeLanguage());

        CheckAppUpdatePutBean bean = new CheckAppUpdatePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_App_Version);
        bean.setModule(ModuleValueService.Module_For_App_Version);

        if (getPresenter() != null) {
            getPresenter().getAppUpdate(bean);
        }
    }

    /**
     * 获取手机imei号
     */
    @SuppressLint({"HardwareIds"})
    private void getMobileImei() {
        // 获取手机imei号
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mobileImei = telephonyManager.getDeviceId();
        mobileIccid = telephonyManager.getSimSerialNumber();  //取出 ICCID
        if (TextUtils.isEmpty(mobileImei)) {
            mobileImei = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

    /**
     * 初始化百度地图
     */
    private void onBaiduMapShow() {
        //定位初始化
        mLocationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("gcj02"); // 设置坐标类型
        option.setScanSpan(60000);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            mLocationClient.stop();
        }
    }

    @Override
    protected void onPause() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(myLocationListener);
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
        }

        super.onDestroy();
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

    @OnClick({R.id.iv_remember_password, R.id.tv_remember_password, R.id.tv_forget_password, R.id.btn_login,
            R.id.tv_register, R.id.tv_agreement, R.id.iv_sqr, R.id.iv_agreement, R.id.login_logo})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.iv_remember_password: // 记住密码
                case R.id.tv_remember_password: // 记住密码
                    isSavePassword = !isSavePassword;
                    onSavePasswordClick();
                    break;
                case R.id.tv_forget_password: // 忘记密码
                    hideKeyboard(edtAccount);
                    launchActivity(ForgetPasswordActivity.newInstance());
                    break;
                case R.id.btn_login: // 登录
                    hideKeyboard(edtAccount);
                    onLoginConfirm();
                    break;
                case R.id.tv_register: // 注册
                    hideKeyboard(edtAccount);
                    if (!isAgreement) {
                        ToastUtils.showShort(getString(R.string.txt_agreement_hint));
                        return;
                    }
                    boolean cn = Utils.isFromCN(this, mobileCName);
                    MyApplication.getMMKVUtils().put(ConstantValue.FromCN, cn);
                    if (cn) {
                        //国内先使用秒验 预登录
                        RegisterNew();
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(this, RegisterForeignActivity.class);
                        startActivity(intent);
                    }

                    break;
                case R.id.tv_agreement: // 查看用户协议
                    onSeePrivacyAgreement();
                    break;
                case R.id.login_logo:
                    onCheckServiceIP();
                    break;
                case R.id.iv_sqr:
                    Intent intent_qr = new Intent(this, HMSScanQrCodeActivity.class);
                    intent_qr.putExtra("type", 1);
                    startActivityForResult(intent_qr, INTENT_SCAN_QR);
                    break;
                case R.id.iv_agreement: // 是否同意隐私政策协议
                    isAgreement = !isAgreement;
                    onSaveAgreementClick();
                    break;

            }
        }
    }

    private void RegisterNew() {
        MobSDK.submitPolicyGrantResult(true, null);
        isVerifySupport = SecVerify.isVerifySupport();
        if (isVerifySupport) {
            verifyTo = 0;
            preVerify();
        } else {
            LogUtils.e("当前环境不支持");
            startRegister();
        }
    }


    /**
     * 预登录
     * <p>
     * 建议提前调用预登录接口，可以加快免密登录过程，提高用户体验
     */
    private void preVerify() {

        //设置在1000-10000之内
        SecVerify.setTimeOut(5000);
        //移动的debug tag 是CMCC-SDK,电信是CT_ 联通是PriorityAsyncTask
        SecVerify.setDebugMode(!Api.isRelease);
        SecVerify.preVerify(new PreVerifyCallback() {
            @Override
            public void onComplete(Void data) {
                isPreVerifyDone = true;
                if (!Api.isRelease)
                    LogUtils.e("预登录成功");
                //添加自定义view
                if (verifyTo == 0) {
                    SecVerify.setUiSettings(CustomizeUtils.customizeUi());
                } else {
                    SecVerify.setUiSettings(CustomizeUtils.customizeUiBind());
                }
                verify();
                SecVerify.autoFinishOAuthPage(false);
            }
            @Override
            public void onFailure(VerifyException e) {
                //预取号错误
                if (verifyTo == 0) {
                    startRegister();
                } else {
                    Intent intent = new Intent(LoginActivity.this, BindMobileActivity.class);
                    toBindActivity(intent);
                }
                // Nothing to do
                Throwable t = e.getCause();
                String errDetail = null;
                if (t != null) {
                    errDetail = t.getMessage();
                }
                if (!Api.isRelease) {
                    // 登录失败
                    LogUtils.e("preVerify failed");
                    // 错误码
                    int errCode = e.getCode();
                    // 错误信息
                    String errMsg = e.getMessage();
                    // 更详细的网络错误信息可以通过t查看，请注意：t有可能为null
                    String msg = "错误码: " + errCode + "\n错误信息: " + errMsg;
                    if (!TextUtils.isEmpty(errDetail)) {
                        msg += "\n详细信息: " + errDetail;
                    }
                    LogUtils.e(msg);
                }
            }
        });
    }

    private  void startRegister(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private long starttime;
    /**
     * 免密注册
     */
    private void verify() {
        CommonProgressDialog.showProgressDialog(this);
        //需要在verify之前设置
        SecVerify.OtherOAuthPageCallBack(new OAuthPageEventCallback() {
            @Override
            public void initCallback(OAuthPageEventResultCallback cb) {
                cb.pageOpenCallback(new PageOpenedCallback() {
                    @Override
                    public void handle() {
                        LogUtils.e(TAG,(System.currentTimeMillis() - starttime) + "ms is the time pageOpen take ");
                    }
                });
                cb.loginBtnClickedCallback(new LoginBtnClickedCallback() {
                    @Override
                    public void handle() {
                        LogUtils.i(TAG, System.currentTimeMillis() + " loginBtnClicked");
                    }
                });
                cb.agreementPageClosedCallback(new AgreementPageClosedCallback() {
                    @Override
                    public void handle() {
                        LogUtils.i(TAG, System.currentTimeMillis() + " agreementPageClosed");
                    }
                });
                cb.agreementPageOpenedCallback(new AgreementClickedCallback() {
                    @Override
                    public void handle() {
                        LogUtils.i(TAG, System.currentTimeMillis() + " agreementPageOpened");
                    }
                });
                cb.cusAgreement1ClickedCallback(new CusAgreement1ClickedCallback() {
                    @Override
                    public void handle() {
                        LogUtils.i(TAG, System.currentTimeMillis() + " cusAgreement1ClickedCallback");
                    }
                });
                cb.cusAgreement2ClickedCallback(new CusAgreement2ClickedCallback() {
                    @Override
                    public void handle() {
                        LogUtils.i(TAG, System.currentTimeMillis() + " cusAgreement2ClickedCallback");
                    }
                });
                cb.checkboxStatusChangedCallback(new CheckboxStatusChangedCallback() {
                    @Override
                    public void handle(boolean b) {
                        // 是否 勾选协议
                        LogUtils.i(TAG,System.currentTimeMillis() + " current status is " + b);
                    }
                });
                cb.pageCloseCallback(new PageClosedCallback() {
                    @Override
                    public void handle() {
                        LogUtils.i(TAG, System.currentTimeMillis() + " pageClosed");
                        HashMap<String, List<Integer>> map = UiLocationHelper.getInstance().getViewLocations();
                        if (map == null) {
                            return;
                        }
                        for (String key : map.keySet()) {
                            List<Integer> locats = map.get(key);
                            if (locats != null && locats.size() > 0) {
                                for (int i : locats) {
                                    LogUtils.i(TAG, i + " xywh");
                                }
                            }
                        }
                    }
                });
            }
        });
        starttime = System.currentTimeMillis();
        SecVerify.verify(new PageCallback() {
            @Override
            public void pageCallback(int code, String desc) {
                LogUtils.e(code  + desc);
                if (code != 6119140) {
                    CommonProgressDialog.dismissProgressDialog();
                    if (!Api.isRelease) {
                        LogUtils.e(code + " " + desc);
                    }
                    //6119152 其他方式登录
                    if(code == 6119152){
                        if (verifyTo == 0) {
                            startRegister();
                        } else {
                            Intent intent = new Intent(LoginActivity.this,BindMobileActivity.class);
                            toBindActivity(intent);
                        }
                    }
                }
            }
        }, new GetTokenCallback() {
            @Override
            public void onComplete(VerifyResult data) {
                //点击登录获取到 Token
                tokenToPhone(data);
                LogUtils.e("token="+ data.getToken());
            }

            @Override
            public void onFailure(VerifyException e) {
                showExceptionMsg(e);
            }
        });

    }

    public void showExceptionMsg(VerifyException e) {
        CommonProgressDialog.dismissProgressDialog();
        // 错误码
        int errCode = e.getCode();
        // 错误信息
        String errMsg = e.getMessage();
        // 更详细的网络错误信息可以通过t查看，请注意：t有可能为null
        Throwable t = e.getCause();
        String errDetail = null;
        if (t != null) {
            errDetail = t.getMessage();
        }
        String msg = "错误码: " + errCode + "\n错误信息: " + errMsg;
        if (!TextUtils.isEmpty(errDetail)) {
            msg += "\n详细信息: " + errDetail;
        }
        if (!Api.isRelease) {
            msg = "当前网络不稳定";
            if (errCode == VerifyErr.C_NO_SIM.getCode()
                    || errCode == VerifyErr.C_UNSUPPORTED_OPERATOR.getCode()
                    || errCode == VerifyErr.C_CELLULAR_DISABLED.getCode()) {
                msg = errMsg;
            }
        }
        LogUtils.e("error msg=" + msg);
        ToastUtils.showShort(getString(R.string.txt_network_error));
    }

    //获取token 成功之后跳转到创建密码页面 、绑定手机号
    public void tokenToPhone(VerifyResult result){
        CommonProgressDialog.dismissProgressDialog();
        opToken = result.getOpToken();
        operator = result.getOperator();
        token = result.getToken();
        md5 = DeviceHelper.getInstance(MobSDK.getContext()).getSignMD5();
        if (TextUtils.isEmpty(opToken) || TextUtils.isEmpty(operator) || TextUtils.isEmpty(token)) {
            ToastUtils.showShort(getString(R.string.txt_network_error));
            return;
        }
        if (verifyTo == 0) {
            Intent intent = new Intent(this, CreatePasswordActivity.class);
            intent.putExtra("opToken", opToken);
            intent.putExtra("operator", operator);
            intent.putExtra("phoneOperator", operator);
            intent.putExtra("token", token);
            startActivity(intent);
        } else {
            //进行 一键绑定
            submitBindMobile(false);
        }
    }

    //绑定
    private void submitBindMobile(boolean isChange) {
        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        boolean isModifyPassword = MyApplication.getMMKVUtils().getBoolean(ConstantValue.Is_Modify_Password);
        if (isModifyPassword) {
            //同时提示绑定手机号和修改密码，密码填入默认值
            infoBean.setPwd("123456");
        }
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        if (isChange) {  //一键绑定  默认true，默认解绑上一个设备号，绑定当前手机号
            paramsBean.setChange_bind(true);
        }
        paramsBean.setAuto_md5(md5);
        paramsBean.setAuto_operator(operator);
        paramsBean.setAuto_optoken(opToken);
        paramsBean.setAuto_phone_operator(operator);
        paramsBean.setAuto_token(token);
        paramsBean.setPwd_md5(MD5Utils.getMD5Encryption(mPassword));
        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);
        if (getPresenter() != null) {
            getPresenter().submitBindMobile(bean, mSid, isChange);
        }
    }


    //launchMode 为SingleTask 时会调用
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.e("onNewIntent");
        if (intent != null && intent.getExtras() != null) {
            if (intent.hasExtra("account")) {
                String account = intent.getStringExtra("account");
                edtAccount.setText(account);
            }
            if (intent.hasExtra("password")) {
                String password = intent.getStringExtra("password");
                edtPassword.setText(password);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_SCAN_QR) {
                String imei = data.getStringExtra("imei");
                if (!TextUtils.isEmpty(imei)) {
//                    if (imei.length() == 11 || imei.length() == 15) {
                    edtAccount.setSelection(edtAccount.getText().toString().length());
                    edtAccount.setText(imei);
//                    } else {
//                        ToastUtils.showShort(getString(R.string.txt_scan_format_error));
//                    }
                }
            } else if (requestCode == Intent_Register) {
                String account = data.getStringExtra("account");
                String password = data.getStringExtra("password");
                edtAccount.setText(account);
                edtPassword.setText(password);
            } else if (requestCode == Intent_Bind_Mobile) {
                String password = data.getStringExtra("password");
                if (!TextUtils.isEmpty(password))
                    edtPassword.setText(password);
            }
        }
        if (requestCode == INSTALL_PERMISS_CODE) {
            // 发起安装应用
            installApk();
        } else if (requestCode == INSTALL_APK_RESULT) {
            if (isForceApp) {
                onBackPressed();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 切换域名或ip
     */
    private void onCheckServiceIP() {
        ServiceIPCheckDialog dialog = new ServiceIPCheckDialog();
        dialog.show(getSupportFragmentManager(), new ServiceIPCheckDialog.onServiceIPCheckChange() {
            @Override
            public void onCheck() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restartApplication();
                    }
                }, 500);
            }
        });
    }

    //重启应用
    private void restartApplication() {
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //杀掉以前进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 保存密码操作
     */
    private void onSavePasswordClick() {
        ivRememberPassword.setImageResource(isSavePassword ? R.drawable.icon_select : R.drawable.icon_unselected);
    }

    /**
     * 同意用户隐私政策协议
     */
    private void onSaveAgreementClick() {
        ivAgreement.setImageResource(isAgreement ? R.drawable.icon_select : R.drawable.icon_unselected);
    }

    /**
     * 确认登录
     */
    private void onLoginConfirm() {
        mAccount = edtAccount.getText().toString().trim().toLowerCase();
        mPassword = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mAccount)) {
            ToastUtils.showShort(getString(R.string.txt_edit_user_account));
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            ToastUtils.showShort(getString(R.string.txt_password_hint));
            return;
        }
        if (!isAgreement) {
            ToastUtils.showShort(getString(R.string.txt_agreement_hint));
            return;
        }

        mLoginInfo = "IP:" + mobileIpAddress + ";IMEI:" + mobileImei + ";IMSI:null" + ";ICCID:" + mobileIccid
                + ";Address:" + mLatitude + "," + mLongitude + mobileInfo + ";WifiMac:" + wifiMacAddress + ";Operator:" + Utils.getOperator(this);

        if (mPushMpm.equals(ResultDataUtils.Push_HuaWei) && TextUtils.isEmpty(mHuaweiToken)) {
            mPushMpm = ResultDataUtils.Push_JiGuang;
        }

        LoginPutBean.ParamsBean paramsBean = new LoginPutBean.ParamsBean();
        paramsBean.setAccount(mAccount);
        paramsBean.setPwd_md5(MD5Utils.getMD5Encryption(mPassword));
        paramsBean.setMpm(mPushMpm);
        if (mPushMpm.equals(ResultDataUtils.Push_HuaWei)) {
            paramsBean.setMpm_identify(mHuaweiToken);
        }
        paramsBean.setPlatform(ModuleValueService.Login_Platform_Type);
        paramsBean.setInfo(mLoginInfo);
        if (Utils.localeLanguage().length() != 0)
            paramsBean.setLang(Utils.localeLanguage());

        if (!TextUtils.isEmpty(mLoginFlag)) {
            paramsBean.setFlag(mLoginFlag);
        }
        paramsBean.setType(Api.App_Type);

        LoginPutBean bean = new LoginPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Login);
        bean.setModule(ModuleValueService.Module_For_Login);

        if (getPresenter() != null) {
            getPresenter().submitLogin(bean);
        }
    }

    /**
     * 绑定手机号码
     *
     * @param sid
     */
    private void onBindMobile(String sid, boolean isBindPhone, boolean isLackPsd) {
        mSid = sid;
        Intent intent;
        if (isBindPhone) { //国内
            if (isLackPsd) {
                intent = new Intent(this, BindForeignActivity.class); //国内登录修改初始密码
                intent.putExtra("foreign", false);
                toBindActivity(intent);
            } else {
                MobSDK.submitPolicyGrantResult(true, null);
                isVerifySupport = SecVerify.isVerifySupport();
                if (isVerifySupport) {
                    verifyTo = 1;
                    preVerify();
                } else {
                    LogUtils.e("当前环境不支持");
                    intent = new Intent(this, BindMobileActivity.class);
                    toBindActivity(intent);
                }
            }
        } else {
            intent = new Intent(this, BindForeignActivity.class); //设备号国外登录
            intent.putExtra("foreign", true);
            toBindActivity(intent);
        }
    }

    //
    private void toBindActivity(Intent intent) {
        intent.putExtra("sid", mSid);
        intent.putExtra("password", mPassword);
        intent.putExtra("account", mAccount);
        startActivity(intent);
    }


    @Override
    public void submitLoginSuccess(LoginResultBean loginResultBean) {
        if (loginResultBean.isSuccess()) {
            if (!TextUtils.isEmpty(loginResultBean.getSid())) {
                onLoginSuccess(loginResultBean);
            } else {
                onLoginAccountSelect(loginResultBean);
            }
        } else {
            mLoginFlag = "";
        }
    }

    private void onLoginAccountSelect(LoginResultBean loginResultBean) {
        if (loginResultBean.getFlag().size() >= 2) {
            int bindPhone = 0;
            for (String str : loginResultBean.getFlag()) {
                if (str.equals(ResultDataUtils.Login_type_Phone_Account)) {
                    bindPhone++;
                }
                if (str.equals(ResultDataUtils.Login_type_Phone_Device)) {
                    bindPhone++;
                }
            }
            isMergeAccount = bindPhone == 2; //是否符号合并要求
            LoginAccountListDialog dialog = new LoginAccountListDialog();
            dialog.show(getSupportFragmentManager(), loginResultBean.getFlag(), new LoginAccountListDialog.onLoginAccountChange() {
                @Override
                public void onAccountInfo(String accountInfo) {
                    mLoginFlag = accountInfo;
                    onLoginConfirm();
                }
            });
        } else {
            isMergeAccount = false;
            mLoginFlag = "";
            ToastUtils.showShort(loginResultBean.getError_message());
        }
    }

    private void onLoginSuccess(LoginResultBean loginResultBean) {
        mLoginFlag = loginResultBean.getFlag().get(0);
        MyApplication.getMMKVUtils().put(ConstantValue.USER_LOGIN_TYPE, mLoginFlag);
        MyApplication.getMMKVUtils().put(ConstantValue.HUAWEI_TOKEN, mHuaweiToken);
        MyApplication.getMMKVUtils().put(ConstantValue.Push_mpm, mPushMpm);

        boolean cn = Utils.isFromCN(this, mobileCName);
        MyApplication.getMMKVUtils().put(ConstantValue.FromCN, cn);
        boolean isModifyPassword = false; // 是否需要修改密码
        boolean isBindMobile = false; // 是否需要绑定手机号码
        if (loginResultBean.isIs_need_check()) {
            if (loginResultBean.getLack() != null) {
                for (String lack : loginResultBean.getLack()) {
                    if (lack.equals(ResultDataUtils.Lack_Password)) {
                        isModifyPassword = true;
                    }
                    if (lack.equals(ResultDataUtils.Lack_Phone)) {
                        isBindMobile = true;
                    }
                }
                MyApplication.getMMKVUtils().put(ConstantValue.Is_Modify_Password, isModifyPassword);
                MyApplication.getMMKVUtils().put(ConstantValue.Is_Bind_Mobile, isBindMobile);
            }
            if (!cn && mLoginFlag.equals(ResultDataUtils.Login_type_Device)) { //设备号、国外登录、缺少密码
                if (isModifyPassword) {
                    //提示修改密码
                    showModifyPsd(loginResultBean, false);
                } else {
                    //提示绑定手机号
                    BindMobileDialog dialog = new BindMobileDialog();
                    dialog.show(getSupportFragmentManager(), new BindMobileDialog.onBindMobileChange() {
                        @Override
                        public void onConfirm() {
                            mLoginFlag = "";
                            onBindMobile(loginResultBean.getSid(), true,false);
                        }

                        @Override
                        public void onCancel() {
                            mLoginFlag = "";
                        }
                    });
                }
            } else {
                if (!isBindMobile) { //已绑定手机号，密码为空
                    //提示修改密码
                    showModifyPsd(loginResultBean, true);
                } else {
                    BindMobileDialog dialog = new BindMobileDialog();
                    dialog.show(getSupportFragmentManager(), new BindMobileDialog.onBindMobileChange() {
                        @Override
                        public void onConfirm() {
                            mLoginFlag = "";
                            onBindMobile(loginResultBean.getSid(), true,false);
                        }

                        @Override
                        public void onCancel() {
                            mLoginFlag = "";
                        }
                    });
                }
            }
        } else {
            successToMain(loginResultBean);
        }
    }

    private void showModifyPsd(LoginResultBean loginResultBean, boolean bindPhone) {
        BindForeignDialog dialog = new BindForeignDialog();
        dialog.show(getSupportFragmentManager(), new BindForeignDialog.onBindMobileChange() {
            @Override
            public void onConfirm() {
                mLoginFlag = "";
                if (bindPhone) { // 国内登录 重置密码后 只提示修改密码
                    onBindMobile(loginResultBean.getSid(), true, true);
                } else {
                    onBindMobile(loginResultBean.getSid(), false, false);
                }
            }

            @Override
            public void onCancel() {
                mLoginFlag = "";
            }
        });
    }

    private void successToMain(LoginResultBean loginResultBean) {
        ToastUtils.showShort(getString(R.string.txt_login_success));
        if (!TextUtils.isEmpty(ConstantValue.getAccount()) && ConstantValue.getAccount().equals(edtAccount.getText().toString().trim())) {
            isBeforeAccount = true;
        }
        // 如果达到合并账号的条件，再判断是不是账号登录，判断账号是否有e_ua_add_dev
        if (isMergeAccount) {
            if (mLoginFlag.equals(ResultDataUtils.Login_type_Account)
                    || mLoginFlag.equals(ResultDataUtils.Login_type_Phone_Account)) {
                if (loginResultBean.getFamilys() != null && loginResultBean.getFamilys().size() > 0) {
                    isMergeAccount = loginResultBean.getFamilys().get(0).getAuth().toString().contains(ResultDataUtils.Family_Auth_2);
                } else {
                    isMergeAccount = false;
                }
                if (loginResultBean.getFamilys() != null && loginResultBean.getFamilys().size() > 0) {
                    isMergeAccount = loginResultBean.getFamilys().get(0).getType().equals(ResultDataUtils.Account_User);
                }
            }
        }
        MyApplication.getMMKVUtils().put(ConstantValue.Is_Modify_Password, false);
        MyApplication.getMMKVUtils().put(ConstantValue.Phone_Zone, loginResultBean.getZone());
        MyApplication.getMMKVUtils().put(ConstantValue.IS_USER_AGREEMENT, isAgreement);
        MyApplication.getMMKVUtils().put(ConstantValue.USER_SID, loginResultBean.getSid());
        MyApplication.getMMKVUtils().put(ConstantValue.ACCOUNT, edtAccount.getText().toString().trim());
        MyApplication.getMMKVUtils().put(ConstantValue.IS_SAVE_PASSWORD_NEW, isSavePassword);
        MyApplication.getMMKVUtils().put(ConstantValue.Is_Need_Check, loginResultBean.isIs_need_check());
        MyApplication.getMMKVUtils().put(ConstantValue.Push_Family, loginResultBean.getJpush());
        MyApplication.getMMKVUtils().put(ConstantValue.Is_Check_Phone, loginResultBean.isIs_check_phone());
        MyApplication.getMMKVUtils().put(ConstantValue.MAP_TYPE_NEW, mapType);
        if (loginResultBean.getFamilys() != null && loginResultBean.getFamilys().size() > 0) {
            MyApplication.getMMKVUtils().put(ConstantValue.Family_Sid, loginResultBean.getFamilys().get(0).getSid());
            MyApplication.getMMKVUtils().put(ConstantValue.Family_Sid_Login, loginResultBean.getFamilys().get(0).getSid());
            MyApplication.getMMKVUtils().put(ConstantValue.Family_Sname, loginResultBean.getFamilys().get(0).getSname());
            MyApplication.getMMKVUtils().put(ConstantValue.Family_Sname_Login, loginResultBean.getFamilys().get(0).getSname());
            MyApplication.getMMKVUtils().put(ConstantValue.IS_ROOT, loginResultBean.getFamilys().get(0).isIs_root());

            if (loginResultBean.getFamilys().get(0).getAuth() != null && loginResultBean.getFamilys().get(0).getAuth().size() > 0) {
                MyApplication.getMMKVUtils().put(ConstantValue.Family_Auth, loginResultBean.getFamilys().get(0).getAuth().toString());
            }
        }

        //登录的时候返回e_ua_show_tool，有返回就显示售后工具，反之，不显示出来
        if(loginResultBean.getAuth() != null && loginResultBean.getAuth().size() > 0){
            MyApplication.getMMKVUtils().put(ConstantValue.IS_SHOW_AFTER_SALE, loginResultBean.getAuth().contains("e_ua_show_tool"));
        }

        if (isSavePassword) {
            MyApplication.getMMKVUtils().put(ConstantValue.PASSWORD, edtPassword.getText().toString().trim());
        }
        MyApplication.getMyApp().setBeforeAccount(isBeforeAccount);
        MyApplication.getMyApp().setMergeAccount(isMergeAccount);
        launchActivity(MainActivity.newInstance());
        finish();
    }

    @Override
    public void getAppUpdateSuccess(CheckAppUpdateBean checkAppUpdateBean) {
        MyApplication.getMMKVUtils().put(ConstantValue.Is_Get_Update_App, true);
        if (!TextUtils.isEmpty(checkAppUpdateBean.getUrl())) {
            isForceApp = checkAppUpdateBean.isIs_force();
            UploadAppDialog dialog = new UploadAppDialog();
            dialog.show(getSupportFragmentManager(), checkAppUpdateBean, new UploadAppDialog.onAlertDialogChange() {
                @Override
                public void onAppDownLoad(String path) {
                    mFilePath = path;
                    dialog.dismiss();
                    applyInstallCheckApp();
                }

                @Override
                public void onThreeDownLoad() {
                    if (!TextUtils.isEmpty(checkAppUpdateBean.getUrl())) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(checkAppUpdateBean.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                    if (!isForceApp) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancel() {
                    if (isForceApp) {
                        onBackPressed();
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    /**
     * 检测是否有安装权限，判断当前安卓版本是否大于等于8.0，8.0以上系统设置安装未知来源权限
     */
    private void applyInstallCheckApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 是否设置了安装权限
            boolean isInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (isInstallPermission) {
                installApk();
            } else {
                installApkSetting();
            }
        } else {
            installApk();
        }
    }

    /**
     * 跳转应用详情页打开安装权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void installApkSetting() {
        Uri packageUrl = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUrl);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }

    /**
     * 下载完成，提示用户安装，获取安装App(支持7.0)的意图
     */
    private void installApk() {
        if (mFilePath == null) return;
        //apk文件的本地路径
        File file = new File(mFilePath);
        if (!file.exists()) {
            return;
        }
        try {
            Uri uri;
            //调用系统安装程序
            Intent intent = new Intent();
            intent.addCategory("android.intent.category.DEFAULT");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, "com.slxk.gpsantu.fileprovider", file);
                intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//7.0以后，系统要求授予临时uri读取权限，安装完毕以后，系统会自动收回权限，该过程没有用户交互
            } else {
                uri = Uri.fromFile(file);
                intent.setAction("android.intent.action.VIEW");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivityForResult(intent, INSTALL_APK_RESULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.finishAllActivities();
        super.onBackPressed();
    }

    // ------------------- 分割线：发起动态权限申请  ------------------------

    /**
     * @param permissions
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            isNeedCheck = !verifyPermissions(paramArrayOfInt);
            if (isAuth && isNeedCheck) {
                showMissingPermissionDialog();
            }
            isAuth = false;
            if (!isGetUpdateApp) {
                checkAppUpdate();
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.txt_tip);
        builder.setMessage(R.string.txt_notify_msg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.txt_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setPositiveButton(R.string.txt_setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                        dialog.dismiss();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    // ------------------------ 分割线：获取手机ip地址信息 ----------------------

    /**
     * 获取当前手机网络ip地址
     *
     * @return
     */
    private void getIPAddress() {
        new Thread(new Runnable() {
            @SuppressLint("ServiceCast")
            @Override
            public void run() {
                NetworkInfo info = ((ConnectivityManager) LoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                        try {
                            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                                NetworkInterface intf = en.nextElement();
                                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                                    InetAddress inetAddress = enumIpAddr.nextElement();
                                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                        mobileIpAddress = inetAddress.getHostAddress();
                                    }
                                }
                            }
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                    } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                        wifiMacAddress = MacAddressUtils.getConnectedWifiMacAddress(MyApplication.getMyApp());
                        getMobileNetIp();
                    }
                } else {
                    //当前无网络连接,请在设置中打开网络
                }
            }
        }).start();
    }

    /**
     * 获取外网地址
     *
     * @return
     */
    private void getMobileNetIp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL infoUrl = null;
                InputStream inStream = null;
                try {
                    infoUrl = new URL(Api.App_GetIPUrl);
                    URLConnection connection = infoUrl.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    httpConnection.setReadTimeout(5000);//读取超时
                    httpConnection.setConnectTimeout(5000);//连接超时
                    httpConnection.setDoInput(true);
                    httpConnection.setUseCaches(false);

                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                        StringBuilder strber = new StringBuilder();
                        while ((mobileIpAddress = reader.readLine()) != null)
                            strber.append(mobileIpAddress + "\n");
                        inStream.close();
                        // 从反馈的结果中提取出IP地址
                        if (!TextUtils.isEmpty(strber.toString()) && strber.toString().contains("{") && strber.toString().contains("}")) {
                            int start = strber.indexOf("{");
                            int end = strber.indexOf("}");
                            String json = strber.substring(start, end + 1);
                            if (json != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(json);
                                    mobileIpAddress = jsonObject.optString("cip");
                                    mobileCName = jsonObject.optString("cname");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (TextUtils.isEmpty(mobileIpAddress)) {
                            mobileIpAddress = IPUtils.getWifiIPAddress(LoginActivity.this);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 获取华为推送token
     */
    private void getToken() {
        new Thread() {
            @SuppressLint("LogNotTimber")
            @Override
            public void run() {
                try {
                    // read from agconnect-services.json
//                    String appId = AGConnectServicesConfig.fromContext(LoginActivity.this).getString("client/app_id");
                    String appId = "102922271";
                    mHuaweiToken = HmsInstanceId.getInstance(LoginActivity.this).getToken(appId, "HCM");
                } catch (ApiException e) {
                    Log.e("kang", "get token failed, " + e);
                }
            }
        }.start();
    }


    /**
     * MyReceiver
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.getString("msg") != null) {
                mHuaweiToken = bundle.getString("msg");
            }
        }
    }

    /**
     * 显示密码
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = edtPassword.getText();
            Selection.setSelection(etable, etable.length());
        } else {
            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable editable = edtPassword.getText();
            Selection.setSelection(editable, editable.length());
        }
    }

    @Override
    public void submitBindMobileSuccess(RegisterResultBean baseBean, boolean isChangeBind) {
        SecVerify.finishOAuthPage();
        if (baseBean.isSuccess()) {
            MyApplication.getMMKVUtils().put(ConstantValue.Is_Need_Check, false);
            ToastUtils.showShort(getString(R.string.txt_bind_success));
        } else if (baseBean.getErrcode() == Api.Mobile_Bind_Used) {
            String flag =  MyApplication.getMMKVUtils().getString(ConstantValue.USER_LOGIN_TYPE);
            if (flag.equals(ResultDataUtils.Login_type_Device) || flag.equals(ResultDataUtils.Login_type_Phone_Device)) {
                onPhoneHasBinding();
            } else {
                ToastUtils.showShort(baseBean.getError_message());
            }
        }
    }

    /**
     * 该手机号码已经绑定了设备
     */
    private void onPhoneHasBinding() {
        PhoneHasBindDialog dialog = new PhoneHasBindDialog();
        dialog.show(getSupportFragmentManager(), 1, new PhoneHasBindDialog.onPhoneHasBindChange() {
            @Override
            public void onRegister() {
                startRegister();
            }

            @Override
            public void onUnbind() {
//                submitBindMobile(true);
            }

            @Override
            public void onLogin() {

            }
        });
    }

}

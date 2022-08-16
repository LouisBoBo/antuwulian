package com.slxk.gpsantu.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerUserSettingComponent;
import com.slxk.gpsantu.mvp.contract.UserSettingContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.CheckAppUpdateBean;
import com.slxk.gpsantu.mvp.model.bean.DataCenterBean;
import com.slxk.gpsantu.mvp.model.bean.LogoutAccountResultBean;
import com.slxk.gpsantu.mvp.model.putbean.CheckAppUpdatePutBean;
import com.slxk.gpsantu.mvp.model.putbean.LogoutAccountPutBean;
import com.slxk.gpsantu.mvp.presenter.UserSettingPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppDialog;
import com.slxk.gpsantu.mvp.weiget.UploadAppDialog;


import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 用户设置
 * <p>
 * Created by MVPArmsTemplate on 05/17/2022 10:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserSettingActivity extends BaseActivity<UserSettingPresenter> implements UserSettingContract.View {
    @BindView(R.id.rv_delete_account)
    RelativeLayout rvDelete;

    private static final int INSTALL_PERMISS_CODE = 101; // 允许安装位置应用回调码
    private static final int INSTALL_APK_RESULT = 102; // apk安装情况，是否完成安装回调
    private String mFilePath; // 版本更新下载url地址


    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), UserSettingActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_setting));
        if (!ConstantValue.isLoginForAccount()) {
            rvDelete.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.rv_check_update, R.id.rv_delete_account})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            int id = view.getId();
            switch (id) {
                case R.id.rv_delete_account:
                    onLogoutAccount();
                    break;
                case R.id.rv_check_update:
                    checkAppUpdate();
                    break;
            }
        }
    }

    /**
     * 注销账号
     */
    private void onLogoutAccount() {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_logout_account_hint));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitLogoutAccount();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 提交注销账号
     */
    private void submitLogoutAccount() {
        LogoutAccountPutBean bean = new LogoutAccountPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Logout_Account);
        bean.setModule(ModuleValueService.Module_For_Logout_Account);

        showProgressDialog();

        getPresenter().submitLogoutAccount(bean);
    }

    /**
     * 检测app版本更新
     */
    private void checkAppUpdate() {
        CheckAppUpdatePutBean.ParamsBean paramsBean = new CheckAppUpdatePutBean.ParamsBean();
        paramsBean.setVersion(AppUtils.getAppVersionCode());
        paramsBean.setApp_type(Api.App_Update_Type);
        if (Utils.localeLanguage().length() != 0)
            paramsBean.setLang(Utils.localeLanguage());

        CheckAppUpdatePutBean bean = new CheckAppUpdatePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_App_Version);
        bean.setModule(ModuleValueService.Module_For_App_Version);

        showProgressDialog();

        getPresenter().getAppUpdate(bean);
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
    public void getAppUpdateSuccess(CheckAppUpdateBean checkAppUpdateBean) {
        MyApplication.getMMKVUtils().put(ConstantValue.Is_Get_Update_App, true);
        // 判断当前版本标识是不是小于服务器返回的版本标识
        if (!TextUtils.isEmpty(checkAppUpdateBean.getUrl())) {
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
                    dialog.dismiss();
                }

                @Override
                public void onCancel() {
                    dialog.dismiss();
                }
            });
        } else {
            ToastUtils.showShort(getString(R.string.txt_latest_version));
        }
    }


    @Override
    public void submitLogoutAccountSuccess(LogoutAccountResultBean logoutAccountResultBean) {
        ToastUtils.showShort(getString(R.string.txt_logout_account_success));
        MyApplication.getMMKVUtils().remove(ConstantValue.ACCOUNT);
        MyApplication.getMMKVUtils().remove(ConstantValue.PASSWORD);
        MyApplication.getMMKVUtils().remove(ConstantValue.IS_SAVE_PASSWORD_NEW);
        onClearData();
    }

    /**
     * 清除缓存信息
     */
    private void onClearData(){
        MyApplication.getMyApp().clearData();
        //清除缓存信息
        MyApplication.getMMKVUtils().remove(ConstantValue.USER_SID);
        MyApplication.getMMKVUtils().remove(ConstantValue.Push_Switch);
        MyApplication.getMMKVUtils().remove(ConstantValue.USER_LOGIN_TYPE);
        MyApplication.getMMKVUtils().remove(ConstantValue.Family_Sid);
        MyApplication.getMMKVUtils().remove(ConstantValue.Push_Family);
        MyApplication.getMMKVUtils().remove(ConstantValue.Family_Auth);
        MyApplication.getMMKVUtils().remove(ConstantValue.Is_Check_Phone);

        ActivityUtils.finishAllActivities();
        launchActivity(LoginActivity.newInstance(0));
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }

    /**
     * 检测是否有安装权限，判断当前安卓版本是否大于等于8.0，8.0以上系统设置安装未知来源权限
     */
    private void applyInstallCheckApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 是否设置了安装权限
            boolean isInstallPermission = MyApplication.getMyApp().getPackageManager().canRequestPackageInstalls();
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
        Uri packageUrl = Uri.parse("package:" + MyApplication.getMyApp().getPackageName());
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
                uri = FileProvider.getUriForFile(MyApplication.getMyApp(), "com.slxk.gpsantu.fileprovider", file);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 发起安装应用
        if (requestCode == INSTALL_PERMISS_CODE) {
            // 发起安装应用
            installApk();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

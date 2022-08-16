package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerSimDetailComponent;
import com.slxk.gpsantu.mvp.contract.SimDetailContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.SimDetailResultBean;
import com.slxk.gpsantu.mvp.model.putbean.SimDetailPutBean;
import com.slxk.gpsantu.mvp.presenter.SimDetailPresenter;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: sim卡查询-详情
 * <p>
 * Created by MVPArmsTemplate on 10/28/2020 16:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SimDetailActivity extends BaseActivity<SimDetailPresenter> implements SimDetailContract.View {

    @BindView(R.id.edt_sim_number)
    EditText edtSimNumber; // SIM卡查询号码
    @BindView(R.id.btn_request)
    Button btnRequest; // 查询
    private String mIccid; // iccid号
    private String mSimei; // simei号
    private String deviceIccid; // 设备的iccid号

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), SimDetailActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSimDetailComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sim_detail;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_sim));
        mSimei = MyApplication.getMyApp().getSimei();
        mIccid = MyApplication.getMyApp().getmIccid();
        deviceIccid = mIccid;
        if (!TextUtils.isEmpty(mIccid)){
            edtSimNumber.setText(mIccid);
            edtSimNumber.setSelection(mIccid.length());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取sim卡详细信息
     */
    private void getSimDetail(){
        mIccid = edtSimNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mIccid)){
            ToastUtils.showShort(getString(R.string.txt_input_sim_number));
            return;
        }

        SimDetailPutBean.ParamsBean paramsBean = new SimDetailPutBean.ParamsBean();
        if (!TextUtils.isEmpty(deviceIccid) && mIccid.equals(deviceIccid)){
            if (!TextUtils.isEmpty(mSimei)){
                paramsBean.setSimei(mSimei);
            }
        }else{
            paramsBean.setIccid(mIccid);
        }
        SimDetailPutBean bean = new SimDetailPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_SIM_Detail);
        bean.setModule(ModuleValueService.Module_For_SIM_Detail);
        bean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().getSimDetail(bean);
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

    @OnClick({R.id.btn_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request: // 查询
                getSimDetail();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getSimDetailSuccess(SimDetailResultBean simDetailResultBean) {
        launchActivity(SimDetailInfoActivity.newInstance(simDetailResultBean));
    }
}

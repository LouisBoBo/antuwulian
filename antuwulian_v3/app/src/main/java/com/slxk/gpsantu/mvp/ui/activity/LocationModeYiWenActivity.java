package com.slxk.gpsantu.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerLocationModeYiWenComponent;
import com.slxk.gpsantu.mvp.contract.LocationModeYiWenContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.DeviceDetailResultBean;
import com.slxk.gpsantu.mvp.model.bean.LocationModeBean;
import com.slxk.gpsantu.mvp.model.bean.LocationModeGetResultBean;
import com.slxk.gpsantu.mvp.model.bean.SavePowerBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceBatchCmdPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceModeSetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LocationModeGetPutBean;
import com.slxk.gpsantu.mvp.presenter.LocationModeYiWenPresenter;
import com.slxk.gpsantu.mvp.ui.adapter.LocationModeYiWenAdapter;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.SelectModelLongDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 溢文设备定位模式
 * <p>
 * Created by MVPArmsTemplate on 10/30/2020 17:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LocationModeYiWenActivity extends BaseActivity<LocationModeYiWenPresenter> implements LocationModeYiWenContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_right)
    TextView tvRight;
    @BindView(R.id.iv_select_wifi)
    ImageView ivWifi;
    @BindView(R.id.iv_select_gps)
    ImageView ivGps;

    private ArrayList<LocationModeBean> modeBeans;
    private LocationModeYiWenAdapter mAdapter;
    private String mSimei; // 加密的imei号
    private int modeId; // 定位模式，设置成功后的定位模式
    private int modeType = 0; // 值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
    private String mPowerSavingShowLong; // 省电模式时长，用于显示在UI上

    private ArrayList<SavePowerBean> savePowerBeans; //  模式下可选的时间
    private int deviceModeSelect; // 选择的定位模式
    private String devPriority = ""; //定位优先级
    private String selectPriority;
    private ArrayList<String> mSimeiBeans;
    private int uid; //1、界面没有任何处理 2、弹出周期定位设置界面 3、弹出飞行模式设置界面 4、弹出提示框 5、单下拉框 6、弹出时间选择框 7、多项下拉框
    private LocationModeBean selectModeBean;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), LocationModeYiWenActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLocationModeYiWenComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_location_mode_yiwen; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_location_mode));
        tvRight.setText(R.string.txt_save);
        tvRight.setTextColor(getResources().getColor(R.color.color_333333));
        tvRight.setVisibility(View.VISIBLE);

        mSimeiBeans = new ArrayList<>();
        modeBeans = new ArrayList<>();
        savePowerBeans = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LocationModeYiWenAdapter(R.layout.item_location_priority, modeBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    onLocationModeClick(position);
                }
            }
        });

        getLocationMode();
        getDeviceDetail(); //获取定位优先级
    }


    /**
     * 获取定位模式数据
     */
    private void getLocationMode() {
        LocationModeGetPutBean.ParamsBean paramsBean = new LocationModeGetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);

        LocationModeGetPutBean bean = new LocationModeGetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Location_Mode);
        bean.setModule(ModuleValueService.Module_For_Location_Mode);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getLocationMode(bean);
        }
    }


    /**
     * 此处设备定位优先级
     */
    private void getDeviceDetail() {
        DeviceDetailPutBean.ParamsBean paramsBean = new DeviceDetailPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        DeviceDetailPutBean bean = new DeviceDetailPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_Detail);
        bean.setModule(ModuleValueService.Module_For_Device_Detail);
        bean.setParams(paramsBean);
        if (getPresenter() != null) {
            getPresenter().getDeviceDetailInfo(bean);
        }
    }

    /**
     * 设置定位模式
     *
     * @param modeId       定位模式
     * @param mode_type  值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
     * @param mode_value 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
     */
    private void submitLocationMode(int modeId, int mode_type, String mode_value) {
        DeviceModeSetPutBean.ParamsBean paramsBean = new DeviceModeSetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setMode_id(modeId);
        if (mode_type != -1) {
            paramsBean.setMode_type(mode_type);
        }
        if (!TextUtils.isEmpty(mode_value)) {
            paramsBean.setSmode_value(mode_value);
        }
        DeviceModeSetPutBean bean = new DeviceModeSetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Location_Mode_Set);
        bean.setModule(ModuleValueService.Module_For_Location_Mode_Set);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitLocationMode(bean);
        }
    }

    /**
     * 设置定位优先级
     */
    private void setDevicePriority() {
        DeviceBatchCmdPutBean.ParamsBean paramsBean = new DeviceBatchCmdPutBean.ParamsBean();
        mSimeiBeans.clear();
        if (!TextUtils.isEmpty(mSimei))
            mSimeiBeans.add(mSimei);

        paramsBean.setSimei(mSimeiBeans);
        paramsBean.setCmd(ConstantValue.Device_Priority);

        paramsBean.setStr_value(selectPriority);
        DeviceBatchCmdPutBean bean = new DeviceBatchCmdPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_Priority);
        bean.setModule(ModuleValueService.Module_For_Device);
        bean.setParams(paramsBean);
        if (getPresenter() != null) {
            getPresenter().setDevicePriority(bean);
        }
    }

    /**
     * 设置定位模式
     *
     * @param position
     */
    private void onLocationModeClick(int position) {
        selectModeBean = modeBeans.get(position);
        deviceModeSelect = selectModeBean.getModeId();
        uid = selectModeBean.getUid();
        if (uid == 5) {
            setSaveBeans(selectModeBean);
            setPowerSavingModeYi();
        }
    }


    //设置时间间隔列表
    private void setSaveBeans(LocationModeBean bean) {
        savePowerBeans.clear();
        if (!TextUtils.isEmpty(bean.getParam()) && bean.getParam().startsWith("[{")) {
            try {
                JSONArray jsonArray = new JSONArray(bean.getParam());
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                    int v = jsonObject.getInt("v");
                    String s = jsonObject.getString("s");
                    SavePowerBean saveBean = new SavePowerBean();
                    saveBean.setTime(v);
                    saveBean.setDes(s);
                    savePowerBeans.add(saveBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置省电模式
     */
    private void setPowerSavingModeYi() {
        SelectModelLongDialog dialog = new SelectModelLongDialog();
        dialog.show(getSupportFragmentManager(), savePowerBeans, new SelectModelLongDialog.onSelectModeLongChange() {
            @Override
            public void onSelectModeLong(int mode_long, String showLong) {
                if (mode_long == -1) { //-1 为自定义
//                    tvRight.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            setPowerSavingModeForCustomize();
//                        }
//                    }, 600);
                } else {
                    modeType = mode_long;
                    setLocationModeData(deviceModeSelect);
                }
            }
        });
    }

    /**
     * 根据获取到的设备数据，设备类型，展示对应的
     */
    private void setLocationModeData(int modeId) {
        for (LocationModeBean bean : modeBeans) {
            bean.setModeNamePlus("");
            if (bean.getModeId() == modeId) {
                bean.setSelect(true);
                if (bean.getUid() == 5) { //时间间隔列表， 例如省电模式
                    mPowerSavingShowLong = getSavingShowLong(bean);
                    bean.setModeNamePlus(mPowerSavingShowLong);
                } else if (bean.getUid() == 6) {  //弹出时间选项 例如睡眠模式
//                    if (!TextUtils.isEmpty(modeValue)) {
//                        bean.setModeNamePlus(modeValue);
//                    }
                }
            } else {
                bean.setSelect(false);
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    //获取时间间隔列表中选项 具体名称表示
    private String getSavingShowLong(LocationModeBean bean) {
        String strSaving = "";
        if (!TextUtils.isEmpty(bean.getParam()) && bean.getParam().startsWith("[{")) {
            try {
                boolean isExist = false;
                JSONArray jsonArray = new JSONArray(bean.getParam());
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                    int v = jsonObject.getInt("v");
                    if (v == modeType) {
                        isExist = true;
                        strSaving = jsonObject.getString("s");
                        break;
                    }
                }
                if (!isExist) { //自定义的时间
                    strSaving = getCustomizeTime(modeType);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strSaving;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar_right, R.id.rl_wifi, R.id.rl_gps})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.toolbar_right:
                    onLocationModeSubmit();
                    if (selectPriority != null && selectPriority.length() > 0) {
                        setDevicePriority();
                    }
                    break;
                case R.id.rl_wifi:
                    selectPriority = "WGL";
                    setImageSelect(selectPriority);
                    break;
                case R.id.rl_gps:
                    selectPriority = "GWL";
                    setImageSelect(selectPriority);
                    break;
            }

        }
    }

    private void setImageSelect(String priority) {
        ivWifi.setImageResource(R.drawable.icon_unselected);
        ivGps.setImageResource(R.drawable.icon_unselected);
        if ("WGL".equals(priority)) {
            ivWifi.setImageResource(R.drawable.icon_select);
        }
        if ("GWL".equals(priority)) {
            ivGps.setImageResource(R.drawable.icon_select);
        }
    }

    /**
     * 提交设置定位模式
     */
    private void onLocationModeSubmit() {
        switch (uid) {
            case 1:
            case 5:
            case 6:
                submitLocationMode(deviceModeSelect, modeType, "");
                break;
        }
    }


    @Override
    public void getLocationModeSuccess(LocationModeGetResultBean locationModeGetResultBean) {
        modeId = locationModeGetResultBean.getMode_id();
        deviceModeSelect = modeId;
        MyApplication.getMyApp().setModeId(modeId);
        modeType = locationModeGetResultBean.getMode_type();
        mPowerSavingShowLong = "";
        modeBeans.clear();
        for (int i = 0; i < locationModeGetResultBean.getMode_list().size(); i++) {
            LocationModeGetResultBean.ModeListBean bean = locationModeGetResultBean.getMode_list().get(i);
            modeBeans.add(new LocationModeBean(bean.getMid(), bean.getName(), bean.getMsg(), bean.getParam(), bean.getUid()));
        }
        setLocationModeData(modeId);
    }

    @Override
    public void submitLocationModeSuccess(BaseBean baseBean, DeviceModeSetPutBean deviceModeSetPutBean) {
        ToastUtils.showShort(getString(R.string.txt_set_success));
        modeId = deviceModeSetPutBean.getParams().getMode_id();
        deviceModeSelect = modeId;
        MyApplication.getMyApp().setModeId(modeId);
    }


    @Override
    public void getDeviceDetailInfoSuccess(DeviceDetailResultBean deviceDetailResultBean) {
        devPriority = deviceDetailResultBean.getPriority();
        selectPriority = devPriority;
        if (devPriority != null && devPriority.length() > 0) {
            setImageSelect(devPriority);
        }
    }

    @Override
    public void getDeviceDetailError() {
        ToastUtils.showShort(R.string.error_no_data);
    }

    @Override
    public void setDevicePriority(BaseBean baseBean) {

    }

    /**
     * 省电模式时间转换
     *
     * @param time
     * @return
     */
    private String getCustomizeTime(int time) {
        String customizeTime = "";
        int hour = time / 3600;
        int min = (time - hour * 3600) / 60;
        if (hour > 0) {
            customizeTime = hour + getString(R.string.txt_hour_two);
        }
        if (min > 0) {
            customizeTime = customizeTime + min + getString(R.string.txt_minute_two);
        }
        return customizeTime;
    }
}
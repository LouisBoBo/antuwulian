package com.slxk.gpsantu.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ViewUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerLocationModeComponent;
import com.slxk.gpsantu.mvp.contract.LocationModeContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.LocationModeBean;
import com.slxk.gpsantu.mvp.model.bean.LocationModeGetResultBean;
import com.slxk.gpsantu.mvp.model.bean.LoopLocationModeResultBean;
import com.slxk.gpsantu.mvp.model.bean.SavePowerBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceModeSetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LocationModeGetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LoopLocationModePutBean;
import com.slxk.gpsantu.mvp.model.putbean.LoopModeModifyPutBean;
import com.slxk.gpsantu.mvp.presenter.LocationModePresenter;
import com.slxk.gpsantu.mvp.ui.adapter.LocationModeAdapter;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppRightDialog;
import com.slxk.gpsantu.mvp.weiget.CustomizeTimeDialog;
import com.slxk.gpsantu.mvp.weiget.SelectModelLongDialog;
import com.slxk.gpsantu.mvp.weiget.SelectTimeDialog;
import com.slxk.gpsantu.mvp.weiget.SelectTimeWeekDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2020 17:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LocationModeActivity extends BaseActivity<LocationModePresenter> implements LocationModeContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<LocationModeBean> modeBeans;
    private LocationModeAdapter mAdapter;
    private String mSimei; // 加密的imei号
    private int modeId; // 定位模式，设置成功后的定位模式

    private int modeType = 0; // 值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
    private String modeValue = ""; // 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
    private String mPowerSavingShowLong; // 省电模式时长，用于显示在UI上
    private List<SavePowerBean> savePowerBeans = new ArrayList<>(); // 省电模式下可选的时间

    private int deviceModeSelect; // 选择的定位模式
    private int uid; //1、界面没有任何处理 2、弹出周期定位设置界面 3、弹出飞行模式设置界面 4、弹出提示框 5、单下拉框 6、弹出时间选择框 7、多项下拉框
    private LocationModeBean selectModeBean;
    private ArrayList<LoopLocationModeResultBean.ItemsBean> loopLocationBeans;

    private final static int Intent_Set_Mode = 10; // 设置飞行模式，周期定位模式回调
    private String devType = ""; // 设备类型

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), LocationModeActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLocationModeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_location_mode; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_location_mode));
        modeBeans = new ArrayList<>();
        loopLocationBeans = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();
        devType = MyApplication.getMyApp().getVersion();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LocationModeAdapter(R.layout.item_location_mode, modeBeans);
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
     * 设置定位模式
     *
     * @param modeId     定位模式
     * @param mode_type  值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
     * @param mode_value 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
     */
    private void submitLocationMode(int modeId, int mode_type, String mode_value) {
        DeviceModeSetPutBean.ParamsBean paramsBean = new DeviceModeSetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setMode_id(modeId);
        if (mode_type != 0) {
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
                    if (!TextUtils.isEmpty(modeValue)) {
                        bean.setModeNamePlus(modeValue);
                    }
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

    /**
     * 设置定位模式
     *
     * @param position
     */
    private void onLocationModeClick(int position) {
        selectModeBean = modeBeans.get(position);
        deviceModeSelect = selectModeBean.getModeId();
        uid = selectModeBean.getUid();
        switch (uid) {
            case 1:
            case 4:
                modeType = 0;
                modeValue = "";
                setLocationModeData(deviceModeSelect);
                break;
            case 2:
                onSetLoopLocationMode();
                break;
            case 3:
                onSetFlyMode();
                break;
            case 5:
                setSaveBeans(selectModeBean);
                setPowerSavingMode();
                break;
            case 6:
                setSleepMode();
                break;
            case 19:  // 闹钟模式
                onSetAlarmClockMode();
                break;
            case 20: // 星期模式
                getLoopLocationMode();
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            getLocationMode();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置飞行模式
     */
    private void onSetFlyMode() {
        Intent intent = new Intent(this, FlyModeActivity.class);
        intent.putExtra("mode_id", deviceModeSelect);
        intent.putExtra("mode_type", modeType);
        intent.putExtra("mode_value", modeValue);
        startActivityForResult(intent, Intent_Set_Mode);
    }

    /**
     * 设置周期定位模式
     */
    private void onSetLoopLocationMode() {
        Intent intent = new Intent(this, LoopLocationModeActivity.class);
        startActivityForResult(intent, Intent_Set_Mode);
    }

    /**
     * 设置闹钟模式
     */
    private void onSetAlarmClockMode() {
        Intent intent = new Intent(this, AlarmClockActivity.class);
        intent.putExtra("title", selectModeBean.getModeName());
        intent.putExtra("value", modeValue);
        startActivityForResult(intent, Intent_Set_Mode);
    }

    /**
     * 设置睡眠模式
     */
    private void setSleepMode() {
        SelectTimeDialog timeDialog = new SelectTimeDialog();
        timeDialog.show(getSupportFragmentManager(), modeValue, new SelectTimeDialog.onSelectTimeChange() {
            @Override
            public void onSelectTime(String time) {
                modeValue = time;
                modeType = 0;
                setLocationModeData(deviceModeSelect);
            }
        });
    }

    /**
     * 获取星期模式
     */
    private void getLoopLocationMode() {
        LoopLocationModePutBean.ParamsBean paramsBean = new LoopLocationModePutBean.ParamsBean();
        if (mSimei != null && mSimei.length() > 0)
            paramsBean.setSimei(mSimei);
        LoopLocationModePutBean bean = new LoopLocationModePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Loop_Location_Mode_Get);
        bean.setModule(ModuleValueService.Module_For_Loop_Location_Mode_Get);

        if (getPresenter() != null) {
            getPresenter().getLoopLocationMode(bean);
        }
    }

    /**
     * 设置星期模式
     */
    private void setWeekMode(LoopLocationModeResultBean.ItemsBean.InfoBean infoBean) {
        SelectTimeWeekDialog timeDialog = new SelectTimeWeekDialog();
        String locValue = infoBean.getLoc_value();
        List<Integer> loopLoc = infoBean.getRp_loop_loc();
        timeDialog.show(getSupportFragmentManager(), locValue, loopLoc, new SelectTimeWeekDialog.onSelectTimeChange() {
            @Override
            public void onSelectTime(String time, List<Integer> loopList) {
                submitLoopModify(time, loopList);
            }
        });
    }

    /**
     * 修改周期定位
     */
    private void submitLoopModify(String time, List<Integer> loopList) {
        if (loopLocationBeans.size() > 0) {
            LoopLocationModeResultBean.ItemsBean bean = loopLocationBeans.get(0);
            LoopLocationModeResultBean.ItemsBean.InfoBean infoBean = loopLocationBeans.get(0).getInfo();
            LoopModeModifyPutBean.ParamsBean.ItemBean itemBean = new LoopModeModifyPutBean.ParamsBean.ItemBean();
            itemBean.setLoc_switch(infoBean.isLoc_switch());
            itemBean.setLoc_value(time);
            itemBean.setLoop_loc_name(infoBean.getLoop_loc_name());
            itemBean.setRp_loop_loc(loopList);
            LoopModeModifyPutBean.ParamsBean paramsBean = new LoopModeModifyPutBean.ParamsBean();
            paramsBean.setItem(itemBean);
            paramsBean.setLid(bean.getLid());
            LoopModeModifyPutBean loopModeModifyPutBean = new LoopModeModifyPutBean();
            loopModeModifyPutBean.setParams(paramsBean);
            loopModeModifyPutBean.setFunc(ModuleValueService.Fuc_For_Loop_Location_Mode_Modify);
            loopModeModifyPutBean.setModule(ModuleValueService.Module_For_Loop_Location_Mode_Modify);
            if (getPresenter() != null) {
                getPresenter().submitLoopModify(loopModeModifyPutBean);
            }
        }
    }

    /**
     * 设置省电模式
     */
    private void setPowerSavingMode() {
        SelectModelLongDialog dialog = new SelectModelLongDialog();
        dialog.show(getSupportFragmentManager(), savePowerBeans, new SelectModelLongDialog.onSelectModeLongChange() {
            @Override
            public void onSelectModeLong(int mode_long, String showLong) {
                if (mode_long == -1) { //-1 为自定义
                    ViewUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setPowerSavingModeForCustomize();
                        }
                    }, 500);
                } else {
                    modeType = mode_long;
                    modeValue = "";
                    setLocationModeData(deviceModeSelect);
                }

            }
        });
    }

    /**
     * 自定义省电模式时间
     */
    private void setPowerSavingModeForCustomize() {
        CustomizeTimeDialog dialog = new CustomizeTimeDialog();
        dialog.show(getSupportFragmentManager(), new CustomizeTimeDialog.onCustomizeTimeChange() {
            @Override
            public void onCustomizeTime(int time) {
                modeType = time;
                modeValue = "";
                setLocationModeData(deviceModeSelect);
            }
        });
    }


    /**
     * 二次确认设置定位模式
     *
     * @param modeBean 定位模式
     * @param time     睡眠模式定位时间
     */
    private void onAlertForSetMode(LocationModeBean modeBean, String time) {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        String tip = modeBean.getParam();
        if (!TextUtils.isEmpty(tip)) {
            bean.setAlert(tip);
        }
        AlertAppRightDialog dialog = new AlertAppRightDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppRightDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitLocationMode(modeBean.getModeId(), modeType, time);
            }

            @Override
            public void onCancel() {

            }
        });
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

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            if (selectModeBean == null) return;
            onLocationModeSubmit();
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
                if (deviceModeSelect == 6) { //睡眠模式需要再次弹框
                    onAlertForSetMode(selectModeBean, modeValue);
                } else {
                    submitLocationMode(deviceModeSelect, modeType, modeValue);
                }
                break;
            case 4:// 二次弹框提示
                onAlertForSetMode(selectModeBean, modeValue);
                break;
        }
    }

    @Override
    public void getLocationModeSuccess(LocationModeGetResultBean locationModeGetResultBean) {
        modeId = locationModeGetResultBean.getMode_id(); // 模式id
        deviceModeSelect = modeId;
        MyApplication.getMyApp().setModeId(modeId);
        modeValue = locationModeGetResultBean.getSmode_value();
        modeType = locationModeGetResultBean.getMode_type();
        mPowerSavingShowLong = "";
        modeBeans.clear();
        if (locationModeGetResultBean.getMode_list() != null) {
            for (int i = 0; i < locationModeGetResultBean.getMode_list().size(); i++) {
                LocationModeGetResultBean.ModeListBean bean = locationModeGetResultBean.getMode_list().get(i);
                modeBeans.add(new LocationModeBean(bean.getMid(), bean.getName(), bean.getMsg(), bean.getParam(), bean.getUid()));
            }
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
    public void getLoopLocationModeSuccess(LoopLocationModeResultBean loopLocationModeResultBean) {
        if (loopLocationModeResultBean.isSuccess()) {
            if (loopLocationModeResultBean.getItems() != null) {
                loopLocationBeans.clear();
                loopLocationBeans.addAll(loopLocationModeResultBean.getItems());
                LoopLocationModeResultBean.ItemsBean.InfoBean infoBean = loopLocationBeans.get(0).getInfo();
                setWeekMode(infoBean);
            } else {
                // 初始值
                LoopLocationModeResultBean.ItemsBean.InfoBean infoBean = new LoopLocationModeResultBean.ItemsBean.InfoBean();
                List<Integer> data = new ArrayList<>();
                infoBean.setRp_loop_loc(data);
                infoBean.setLoc_value(DateUtils.getTodayDateTime_5());
                setWeekMode(infoBean);
            }
        } else {
            ToastUtils.showShort(loopLocationModeResultBean.getError_message());
        }
    }

    @Override
    public void submitLoopModifySuccess(BaseBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_set_success));
        ViewUtils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                getLocationMode();
            }
        }, 200);
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

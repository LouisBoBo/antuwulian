package com.slxk.gpsantu.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerAlarmClockComponent;
import com.slxk.gpsantu.mvp.contract.AlarmClockContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlarmClockBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceModeSetPutBean;
import com.slxk.gpsantu.mvp.presenter.AlarmClockPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.adapter.AlarmClockAdapter;
import com.slxk.gpsantu.mvp.weiget.SelectTimeDialog;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/06/2022 17:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmClockActivity extends BaseActivity<AlarmClockPresenter> implements AlarmClockContract.View {
    @BindView(R.id.toolbar_right)
    TextView tvRight;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.btn_add)
    Button btnAdd;


    private ArrayList<AlarmClockBean> timeBeans;
    private AlarmClockAdapter mAdapter;


    private static final String TITLE = "title";
    private static final String VALUE = "value";
    private static final int MODE_ID = 19;
    private String mSimei;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmClockComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm_clock; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        timeBeans = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            setTitle(intent.getStringExtra(TITLE));
            String modeValue = intent.getStringExtra(VALUE);
            if (modeValue == null || modeValue.length() == 0) {
                timeBeans.add(new AlarmClockBean(timeBeans.size() + 1));
            } else {
                String[] times = modeValue.split("\\|");
                for (int i = 0; i < times.length; i++) {
                    AlarmClockBean bean = new AlarmClockBean(i + 1);
                    bean.setTime(times[i]);
                    timeBeans.add(bean);
                }
            }
        }

        mSimei = MyApplication.getMyApp().getSimei();
        tvRight.setText(getString(R.string.txt_save));
        tvRight.setTextColor(getResources().getColor(R.color.color_2E7BEC));
        tvRight.setVisibility(View.VISIBLE);

        mAdapter = new AlarmClockAdapter(this, timeBeans);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlarmClockBean bean = timeBeans.get(position);
                setTimeMode(bean);
            }
        });
    }

    /**
     * 设置时间
     */
    private void setTimeMode(AlarmClockBean bean) {
        String time = bean.getTime();
        SelectTimeDialog timeDialog = new SelectTimeDialog();
        timeDialog.show(getSupportFragmentManager(), time, new SelectTimeDialog.onSelectTimeChange() {
            @Override
            public void onSelectTime(String time) {
                for (AlarmClockBean bean1 : timeBeans) {
                    if (bean1.getTime().equals(time)) {
                        ToastUtils.showShort(getString(R.string.txt_set_repeat_clock));
                        return;
                    }
                }
                bean.setTime(time);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.toolbar_right, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right:
                hideKeyboard(view);
                submitMode();
                break;
            case R.id.btn_add:
                onAddMobileNumber();
                break;
        }
    }

    /**
     * 添加输入框
     */
    private void onAddMobileNumber() {
        if (timeBeans.size() >= 4) {
            ToastUtils.showShort(R.string.txt_four_clock);
            return;
        }
        timeBeans.add(new AlarmClockBean(timeBeans.size() + 1));
        mAdapter.notifyDataSetChanged();
    }

    private void submitMode() {
        DeviceModeSetPutBean.ParamsBean paramsBean = new DeviceModeSetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setMode_id(MODE_ID);

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<AlarmClockBean> timeFinalBeans = new ArrayList<>();
        for (int i = 0; i < timeBeans.size(); i++) {
            String time = timeBeans.get(i).getTime();
            if (time.length() != 0) {
                timeFinalBeans.add(timeBeans.get(i));
            }
        }
        if (timeFinalBeans.size() == 0) {
            ToastUtils.showShort(getString(R.string.txt_please_set_clock));
            return;
        }

        for (int i = 0; i < timeFinalBeans.size(); i++) {
            String time = timeFinalBeans.get(i).getTime();
            if (timeFinalBeans.size() == 1) {
                stringBuilder.append(time);
            } else {
                if (i == timeFinalBeans.size() - 1) {
                    stringBuilder.append(time);
                } else {
                    stringBuilder.append(time).append("|");
                }
            }
        }
        String value = stringBuilder.toString();
        paramsBean.setSmode_value(value);
        DeviceModeSetPutBean bean = new DeviceModeSetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Location_Mode_Set);
        bean.setModule(ModuleValueService.Module_For_Location_Mode_Set);
        bean.setParams(paramsBean);
        if (getPresenter() != null) {
            getPresenter().submitLocationMode(bean);
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
    public void submitLocationModeSuccess(BaseBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_set_success));
        setResult(Activity.RESULT_OK);
        finish();
    }

}

package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.db.RecordModel;
import com.slxk.gpsantu.di.component.DaggerAlarmScreenComponent;
import com.slxk.gpsantu.mvp.contract.AlarmScreenContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlarmScreenBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmScreenTypePutBean;
import com.slxk.gpsantu.mvp.presenter.AlarmScreenPresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.adapter.AlarmScreenAdapter;
import com.blankj.utilcode.util.ToastUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2021 14:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmScreenActivity extends BaseActivity<AlarmScreenPresenter> implements AlarmScreenContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_select_all)
    Button btnSelectAll; // 全选
    @BindView(R.id.btn_confirm)
    Button btnConfirm; // 确定

    private ArrayList<AlarmScreenBean.ItemsBean> screenBeans;
    private AlarmScreenAdapter mAdapter;
    private boolean isAllSelect = false; // 是否全选
    private String messageType; // 报警消息类型

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmScreenComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm_screen;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_screen_type_select);
        screenBeans = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageType = getIntent().getStringExtra("type");

        mAdapter = new AlarmScreenAdapter(R.layout.item_alarm_screen, screenBeans);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean isSelect = !screenBeans.get(position).isSelect();
                screenBeans.get(position).setSelect(isSelect);
                mAdapter.notifyDataSetChanged();
                onSelectNumber();
            }
        });

        getAlarmScreenType();
    }

    /**
     * 获取报警消息筛选类型
     */
    private void getAlarmScreenType(){
        AlarmScreenTypePutBean.ParamsBean paramsBean = new AlarmScreenTypePutBean.ParamsBean();

        AlarmScreenTypePutBean bean = new AlarmScreenTypePutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Alarm_Screen_Type);
        bean.setModule(ModuleValueService.Module_For_Alarm_Screen_Type);
        bean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().getAlarmScreenType(bean);
        }
    }

    /**
     * 报警数量
     */
    @SuppressLint("SetTextI18n")
    private void onSelectNumber() {
        // 选择的数量
        int selectNumber = 0;
        for (AlarmScreenBean.ItemsBean bean : screenBeans) {
            if (bean.isSelect()) {
                selectNumber++;
            }
        }
        if (selectNumber > 0) {
            btnConfirm.setText(getString(R.string.txt_confirm) + "(" + selectNumber + ")");
        } else {
            btnConfirm.setText(getString(R.string.txt_confirm));
        }
        if (selectNumber == screenBeans.size()) {
            isAllSelect = true;
            btnSelectAll.setText(getString(R.string.txt_select_all_cancel));
        } else {
            isAllSelect = false;
            btnSelectAll.setText(getString(R.string.txt_select_all));
        }
    }

    @OnClick({R.id.btn_select_all, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select_all:
                onSelectAll();
                break;
            case R.id.btn_confirm:
                onConfirm();
                break;
        }
    }

    /**
     * 全选
     */
    private void onSelectAll() {
        isAllSelect = !isAllSelect;
        for (AlarmScreenBean.ItemsBean bean : screenBeans) {
            bean.setSelect(isAllSelect);
        }
        mAdapter.notifyDataSetChanged();
        onSelectNumber();
    }

    /**
     * 提交
     */
    private void onConfirm() {
        String type = "";
        for (AlarmScreenBean.ItemsBean bean : screenBeans) {
            if (bean.isSelect()) {
                if (TextUtils.isEmpty(type)) {
                    type = type + bean.getAlarm_type();
                } else {
                    type = type + "," + bean.getAlarm_type();
                }
            }
        }
        Intent intent = new Intent();
        intent.putExtra("type", type);
        setResult(Activity.RESULT_OK, intent);
        finish();
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
    public void getAlarmScreenTypeSuccess(AlarmScreenBean alarmScreenBean) {
        if (alarmScreenBean.getItems() != null){
            screenBeans.addAll(alarmScreenBean.getItems());
        }

        if (!TextUtils.isEmpty(messageType)){
            String[] strType = messageType.split(",");
            for (AlarmScreenBean.ItemsBean bean : screenBeans){
                for (String type : strType) {
                    if (bean.getAlarm_type().equals(type)) {
                        bean.setSelect(true);
                        break;
                    }
                }
            }
        }
        onListSort();
        mAdapter.notifyDataSetChanged();
        onSelectNumber();
    }

    /**
     * 冒泡排序法排序
     */
    private void onListSort() {
        if (screenBeans != null) {
            AlarmScreenBean.ItemsBean tmpObj = null;
            for (int i = 0; i < screenBeans.size(); ++i) {
                for (int j = i + 1; j < screenBeans.size(); ++j) {
                    long iTick = screenBeans.get(i).getWeight();
                    long jTick = screenBeans.get(j).getWeight();
                    if (jTick < iTick) {
                        tmpObj = screenBeans.get(i);
                        screenBeans.set(i, screenBeans.get(j));
                        screenBeans.set(j, tmpObj);
                    }
                }
            }
        }
    }
}

package com.slxk.gpsantu.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerIconCheckComponent;
import com.slxk.gpsantu.mvp.contract.IconCheckContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.IconCheckBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailModifyPutBean;
import com.slxk.gpsantu.mvp.presenter.IconCheckPresenter;
import com.slxk.gpsantu.mvp.ui.adapter.IconCheckAdapter;
import com.slxk.gpsantu.mvp.utils.BroadcastReceiverUtil;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/02/2020 10:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class IconCheckActivity extends BaseActivity<IconCheckPresenter> implements IconCheckContract.View {

    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<IconCheckBean> iconCheckBeans;
    private IconCheckAdapter mAdapter;
    private int carImageId = 0; // 设备的图标id
    private String mSimei; // 设备的simei号

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), IconCheckActivity.class);
    }
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIconCheckComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_icon_check; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_device_icon));
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.txt_save));
        iconCheckBeans = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();
        carImageId = MyApplication.getMyApp().getCarImageId();

        iconCheckBeans.add(new IconCheckBean(0, R.mipmap.icon_car_online, getString(R.string.txt_device_car_0), false));
        iconCheckBeans.add(new IconCheckBean(1, R.mipmap.icon_car_online_1, getString(R.string.txt_device_car_1), false));
        iconCheckBeans.add(new IconCheckBean(2, R.mipmap.icon_car_online_2,getString(R.string.txt_device_car_2),  false));
        iconCheckBeans.add(new IconCheckBean(3, R.mipmap.icon_car_online_3,getString(R.string.txt_device_car_3),  false));
        iconCheckBeans.add(new IconCheckBean(4, R.mipmap.icon_car_online_4,getString(R.string.txt_device_car_4),  false));
        iconCheckBeans.add(new IconCheckBean(5, R.mipmap.icon_car_online_5,getString(R.string.txt_device_car_5),  false));
        iconCheckBeans.add(new IconCheckBean(6, R.mipmap.icon_car_online_6,getString(R.string.txt_device_car_6),  false));
        iconCheckBeans.add(new IconCheckBean(7, R.mipmap.icon_car_online_7,getString(R.string.txt_device_car_7),  false));
        iconCheckBeans.add(new IconCheckBean(8, R.mipmap.icon_car_online_8,getString(R.string.txt_device_car_8),  false));
        iconCheckBeans.add(new IconCheckBean(9, R.mipmap.icon_car_online_9,getString(R.string.txt_device_car_9),  false));

        for (IconCheckBean bean : iconCheckBeans){
            if (bean.getId() == carImageId){
                bean.setSelect(true);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IconCheckAdapter(R.layout.item_icon_device, iconCheckBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (IconCheckBean bean : iconCheckBeans){
                    bean.setSelect(false);
                }
                iconCheckBeans.get(position).setSelect(true);
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

    @OnClick(R.id.toolbar_right)
    public void onViewClicked() {
        for (IconCheckBean bean : iconCheckBeans){
            if (bean.isSelect()){
                carImageId = bean.getId();
                break;
            }
        }

        DeviceDetailModifyPutBean.ParamsBean paramsBean = new DeviceDetailModifyPutBean.ParamsBean();
        paramsBean.setCar_image(carImageId);
        paramsBean.setSimei(mSimei);

        DeviceDetailModifyPutBean bean = new DeviceDetailModifyPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Modify_Device_Detail);
        bean.setModule(ModuleValueService.Module_For_Modify_Device_Detail);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitDetailModify(bean);
        }
    }

    @Override
    public void submitDetailModifySuccess(BaseBean baseBean) {
        MyApplication.getMyApp().setCarImageId(carImageId);
        ToastUtils.showShort(getString(R.string.txt_set_success));
        setResult(Activity.RESULT_OK);
        finish();
    }
}

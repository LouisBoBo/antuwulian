package com.slxk.gpsantu.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerAlarmMessageUserComponent;
import com.slxk.gpsantu.mvp.contract.AlarmMessageUserContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlarmRecordResultBean;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeleteBatchPutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmRecordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FindDevicePutBean;
import com.slxk.gpsantu.mvp.presenter.AlarmMessageUserPresenter;
import com.slxk.gpsantu.mvp.ui.activity.AlarmDetailAmapActivity;
import com.slxk.gpsantu.mvp.ui.activity.AlarmDetailBaiduActivity;
import com.slxk.gpsantu.mvp.ui.activity.AlarmDetailGoogleActivity;
import com.slxk.gpsantu.mvp.ui.activity.AlarmScreenActivity;
import com.slxk.gpsantu.mvp.ui.activity.AlarmSettingActivity;
import com.slxk.gpsantu.mvp.ui.activity.AlarmSettingNewActivity;
import com.slxk.gpsantu.mvp.ui.adapter.AlarmRecordUserAdapter;
import com.slxk.gpsantu.mvp.ui.adapter.SearchDeviceAdapter;
import com.slxk.gpsantu.mvp.ui.view.MyLoadMoreView;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.GpsUtils;
import com.slxk.gpsantu.mvp.utils.LocationAddressParsed;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppDialog;
import com.slxk.gpsantu.mvp.weiget.AlertSelectDatePopupWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 报警消息-个人版
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 09:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmMessageUserFragment extends BaseFragment<AlarmMessageUserPresenter> implements AlarmMessageUserContract.View {

    @BindView(R.id.iv_setting)
    ImageView ivSetting; // 设置报警消息
    @BindView(R.id.iv_data)
    ImageView ivData; // 时间筛选
    @BindView(R.id.tv_input_keyword)
    TextView tvInputKeyword; // 输入关键词搜索
    @BindView(R.id.iv_screen)
    ImageView ivScreen; // 筛选
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.rl_alarm_head)
    RelativeLayout alarmHead;
    @BindView(R.id.edt_search_imei)
    EditText edtSearchImei; // 搜索
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel; // 搜索
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch; // 搜索
    @BindView(R.id.ll_search)
    LinearLayout llSearch; // 搜索
    @BindView(R.id.iv_delete)
    ImageView ivDelete; // 编辑
    @BindView(R.id.tv_delete_cancel)
    TextView tvDeleteCancel; // 取消删除
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll; // 全选
    @BindView(R.id.tv_delete)
    TextView tvDelete; // 删除
    @BindView(R.id.ll_delete)
    LinearLayout llDelete; // 删除

    private TextView tvPrompt;
    private List<AlarmRecordResultBean.ItemsBean> alarmRecordBeans;
    private AlarmRecordUserAdapter mAdapter;
    private List<AlarmRecordResultBean.ItemsBean> deleteAlarmBeans;

    private int mLimitSize = 20; // 数据的限制条数，默认20条,最高不超过100
    private long startTime = 0L; // 筛选开始时间
    private long endTime = 0L; // 筛选结束时间
    private long lastImei = 0; // 上次请求返回的最后一个的imei，首次请求可以不填
    private long lastTime = 0; // 上次请求返回的最后一个的时间，首次请求可以不填
    private String lastType; // 上次请求返回的最后一个的类型，首次请求可以不填
    private List<String> screenTypeLists; // 筛选的类型值
    private List<String> mSimeiBeas; // 设备列表,选填，如果设备登入不填,注意只能筛选同一个sfamilyid下的设备
    private int mPosition; // 选中设备索引位置
    private String userFamilyId; // 用户的familyId
    private String mSimei = ""; // 选中设备的simei号

    private boolean isAllSelect = false; // 是否全选
    private boolean isEdit = false; // 是否是编辑状态
    private int mSelectCount = 0; // 选中的文件个数

    private ChangePageReceiver receiver; // 注册广播接收器
    private static final int INTENT_TYPE = 11; // 报警类型筛选
    private AlertSelectDatePopupWindow mPopupWindow; // 日期选择弹框
    private int mAddressPosition; // 选中的item索引位置


    private List<FindDeviceResultBean.ItemsBean> searchDeviceResultBeans; // 筛选结果设备列表
    private SearchDeviceAdapter mSearchAdapter; // 搜索
    private static final int mSearchCountDownTime = 1; // 倒计时固定时长
    private int mSearchCountDown = mSearchCountDownTime; // 倒计时递减时长
    private String strKeyword = ""; // 搜索关键词
    private long mSearchLastImei = 0; // 上一次调用该接口返回的最后一条imei
    private int mSearchLimitSize = 10; // 限制个数，默认10个，越多，速度越慢

    private String mAlermMessageType = ""; // 报警消息类型
    private long requestTime; // 请求成功时间戳

    public static AlarmMessageUserFragment newInstance() {
        AlarmMessageUserFragment fragment = new AlarmMessageUserFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmMessageUserComponent //如找不到该类,请编译一下项�?
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_message_user, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        alarmRecordBeans = new ArrayList<>();
        mSimeiBeas = new ArrayList<>();
        screenTypeLists = new ArrayList<>();
        deleteAlarmBeans = new ArrayList<>();
        userFamilyId = ConstantValue.getFamilySid();
        searchDeviceResultBeans = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        srlView.setColorSchemeResources(R.color.color_2E7BEC, R.color.color_2E7BEC);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastImei = 0;
                lastTime = 0;
                getAlarmRecord(false, true);
            }
        });

        View statusView = LayoutInflater.from(getContext()).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.error_no_data_alarm));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new AlarmRecordUserAdapter(R.layout.item_alarm_record_for_user, alarmRecordBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (System.currentTimeMillis() - requestTime < 500) {
                    mAdapter.loadMoreComplete();
                    return;
                }
                getAlarmRecord(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    switch (view.getId()) {
//                        case R.id.iv_alarm:
//                            onDeleteAlarmConfirm(position);
//                            break;
                        case R.id.iv_select:
                            boolean isSelect = !alarmRecordBeans.get(position).isSelect();
                            alarmRecordBeans.get(position).setSelect(isSelect);
                            mAdapter.notifyItemChanged(position);
                            if (isSelect) {
                                mSelectCount++;
                            } else {
                                mSelectCount--;
                            }
                            onComputeDeleteCount();
                            break;
                        case R.id.tv_detail: // 报警详情
                            Intent intent = new Intent();
                            switch (ConstantValue.getMapType()) {
                                case 0:
                                    intent.setClass(getContext(), AlarmDetailBaiduActivity.class);
                                    break;
                                case 1:
                                    intent.setClass(getContext(), AlarmDetailAmapActivity.class);
                                    break;
                                case 2:
                                    intent.setClass(getContext(), AlarmDetailGoogleActivity.class);
                                    break;
                            }
                            intent.putExtra("bean", alarmRecordBeans.get(position));
                            startActivity(intent);
                            break;
                    }
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    mAddressPosition = position;
                    // 如果是经纬度，点击解析详细地址
                    getAddressLocation(alarmRecordBeans.get(position).getAddr());
                }
            }
        });

        onSearchDevice();

        // 注册一个广播接收器，用于接收从首页跳转到报警消息页面的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("alarm_message");
        receiver = new ChangePageReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(receiver, filter);
    }

    /**
     * 页面切换广播，广播接收器
     */
    private class ChangePageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (llSearch != null && llSearch.getVisibility() == View.VISIBLE) {
                llSearch.setVisibility(View.GONE);
                edtSearchImei.setText("");
                searchDeviceResultBeans.clear();
                mSearchAdapter.notifyDataSetChanged();
            }
            mSimei = MyApplication.getMyApp().getSimei();
            onResetScreenData();
            onResetDeviceData(false);
        }
    }

    /**
     * 搜索设备
     */
    private void onSearchDevice() {
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));

        mSearchAdapter = new SearchDeviceAdapter(R.layout.item_search_device, searchDeviceResultBeans);
        recyclerViewSearch.setAdapter(mSearchAdapter);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mSearchAdapter.setLoadMoreView(loadMoreView);
        mSearchAdapter.setEnableLoadMore(true);
        mSearchAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (!TextUtils.isEmpty(strKeyword)) {
                    getFindDeviceList();
                }
            }
        }, recyclerViewSearch);

        edtSearchImei.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                strKeyword = s.toString();
                if (searchHandler.hasMessages(10)) {
                    searchHandler.removeMessages(10);
                    mSearchCountDown = mSearchCountDownTime;
                }
                searchHandler.sendEmptyMessageDelayed(10, 1000);
            }
        });
        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                llSearch.setVisibility(View.GONE);
                hideKeyboard(edtSearchImei);
                mSimei = searchDeviceResultBeans.get(position).getSimei();
                onResetScreenData();
                onResetDeviceData(true);
                onResetSearchData();
            }
        });
    }

    /**
     * 重置搜索列表数据
     */
    private void onResetSearchData() {
        edtSearchImei.setText("");
        searchDeviceResultBeans.clear();
        mSearchAdapter.notifyDataSetChanged();
    }

    /**
     * 显示搜索设备
     */
    private void onShowSearchDevice() {
        if (TextUtils.isEmpty(strKeyword)) {
            mSearchLastImei = 0;
            searchDeviceResultBeans.clear();
            mSearchAdapter.notifyDataSetChanged();
        } else {
            mSearchLastImei = 0;
            getFindDeviceList();
        }
    }

    /**
     * 搜索设备
     */
    private void getFindDeviceList() {
        if (TextUtils.isEmpty(strKeyword)) {
            return;
        }
        FindDevicePutBean.ParamsBean paramsBean = new FindDevicePutBean.ParamsBean();
        paramsBean.setImei(strKeyword);
        paramsBean.setLimit_size(mSearchLimitSize);
        if (mSearchLastImei != 0) {
            paramsBean.setLast_imei(mSearchLastImei);
        }
        if (!TextUtils.isEmpty(userFamilyId)) {
            paramsBean.setSfid(userFamilyId);
        }
        FindDevicePutBean bean = new FindDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Find_Device);
        bean.setModule(ModuleValueService.Module_For_Find_Device);

        getPresenter().getFindDeviceData(bean);
    }

    @SuppressLint("HandlerLeak")
    private Handler searchHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSearchCountDown--;
            if (mSearchCountDown <= 0) {
                mSearchCountDown = mSearchCountDownTime;
                onShowSearchDevice();
            } else {
                searchHandler.sendEmptyMessageDelayed(10, 1000);
            }
        }
    };

    /**
     * 显示无数据提示
     */
    private void onShowNoData() {
        tvPrompt.setVisibility(alarmRecordBeans.size() > 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 重置筛选条件值
     */
    private void onResetScreenData() {
        startTime = 0;
        endTime = 0;
        lastImei = 0;
        lastTime = 0;
        lastType = "";
        screenTypeLists.clear();
        mAlermMessageType = "";
        mSimeiBeas.clear();
        if (!TextUtils.isEmpty(mSimei)) {
            mSimeiBeas.add(mSimei);
        }
    }

    /**
     * 重置相关请求信息
     */
    private void onResetDeviceData(boolean isShow) {
        getAlarmRecord(isShow, true);
    }

    /**
     * 获取报警消息列表
     *
     * @param isShow    是否弹出加载看
     * @param isRefresh 是否是刷新数据
     */
    private void getAlarmRecord(boolean isShow, boolean isRefresh) {
        AlarmRecordPutBean.ParamsBean paramsBean = new AlarmRecordPutBean.ParamsBean();
        paramsBean.setLimit_size(mLimitSize);
        if (lastImei != 0) {
            paramsBean.setLast_imei(lastImei);
        }
        if (lastTime != 0) {
            paramsBean.setLast_time(lastTime);
        }
        if (startTime != 0) {
            paramsBean.setStart_time(startTime);
        }
        if (endTime != 0) {
            paramsBean.setEnd_time(endTime);
        }
        if (!TextUtils.isEmpty(lastType)) {
            paramsBean.setLast_type(lastType);
        }
        if (mSimeiBeas.size() > 0) {
            paramsBean.setSimei(mSimeiBeas);
        } else {
            if (ConstantValue.isLoginForAccount()) {
                paramsBean.setSfamilyid(userFamilyId);
            }
        }
        if (screenTypeLists.size() > 0) {
            paramsBean.setType(screenTypeLists);
        }
        AlarmRecordPutBean bean = new AlarmRecordPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Alarm_Record);
        bean.setModule(ModuleValueService.Module_For_Alarm_Record);
        bean.setParams(paramsBean);

        if (isShow) {
            showProgressDialog();
        }

        getPresenter().getAlarmRecord(bean, isShow, isRefresh);
    }

    /**
     * 二次确认是否删除报警消息
     *
     * @param position 选中位置，多选删除默认为0
     */
    private void onDeleteAlarmConfirm(int position) {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_alarm_delete_hint));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitAlarmDelete(position);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 删除报警消息
     *
     * @param position
     */
    private void submitAlarmDelete(int position) {
        mPosition = position;
        AlarmRecordResultBean.ItemsBean itemsBean = alarmRecordBeans.get(mPosition);

        AlarmDeletePutBean.ParamsBean paramsBean = new AlarmDeletePutBean.ParamsBean();
        paramsBean.setTime(itemsBean.getTime());
        paramsBean.setType(itemsBean.getType());
        paramsBean.setFence(itemsBean.getFencename());
        if (!TextUtils.isEmpty(itemsBean.getSimei())) {
            paramsBean.setSimei(itemsBean.getSimei());
        }

        AlarmDeletePutBean bean = new AlarmDeletePutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Alarm_Delete);
        bean.setModule(ModuleValueService.Module_For_Alarm_Delete);
        bean.setParams(paramsBean);

        showProgressDialog();

        getPresenter().submitAlarmDelete(bean);
    }

    /**
     * 获取地址信息
     *
     * @param addressLocation
     */
    private void getAddressLocation(String addressLocation) {
        // 开始判断，如果是经纬度，则启动app自解析地址
        String address = addressLocation;
        address = address.replace(".", "");
        address = address.replace(",", "");
        address = address.replace("-", "");
        address = address.replace(" ", "");
        if (!TextUtils.isEmpty(address)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(address);
            if (isNum.matches()) {
                String[] location = addressLocation.split(",");
                //返回的经纬度为 wgs84 需要转换为 火星坐标
                double[] locationTo = GpsUtils.toGCJ02Point(Double.parseDouble(location[0]), Double.parseDouble(location[1]), 6);
                LocationAddressParsed.getLocationParsedInstance().Parsed(locationTo[0], locationTo[1]).setAddressListener(str -> {
                    if (!TextUtils.isEmpty(str)) {
                        alarmRecordBeans.get(mAddressPosition).setAddrShow(str);
                        mAdapter.notifyItemChanged(mAddressPosition);
                    } else {
                        if (getContext() != null)
                            ToastUtils.showShort(getString(R.string.txt_address_error));
                    }
                });
            }
        }
    }

    @Override
    public void setData(@Nullable Object data) {
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
    }

    @OnClick({R.id.iv_setting, R.id.iv_data, R.id.tv_input_keyword, R.id.iv_screen, R.id.tv_search_cancel, R.id.rl_delete, R.id.tv_select_all,
            R.id.tv_delete})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.iv_setting:
                    if (TextUtils.isEmpty(MyApplication.getMyApp().getImei() + "") || MyApplication.getMyApp().getImei() == 0) {
                        ToastUtils.showShort(getString(R.string.txt_device_select_location));
                        return;
                    }
                    launchActivity(AlarmSettingNewActivity.newInstance());
                    break;
                case R.id.iv_data:
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    } else {
                        showDateSelectPopupWindow();
                    }
                    break;
                case R.id.tv_input_keyword: // 设备号筛选
                    llSearch.setVisibility(View.VISIBLE);
                    break;
                case R.id.iv_screen:
                    intent.setClass(getContext(), AlarmScreenActivity.class);
                    intent.putExtra("type", mAlermMessageType);
                    startActivityForResult(intent, INTENT_TYPE);
                    break;
                case R.id.tv_search_cancel:
                    hideKeyboard(edtSearchImei);
                    llSearch.setVisibility(View.GONE);
                    onResetSearchData();
                    break;
                case R.id.rl_delete: // 编辑
                    onMessageEdit();
                    break;
                case R.id.tv_select_all: // 全选
                    onMessageAllSelect();
                    break;
                case R.id.tv_delete: // 删除
                    onAlarmDeleteConfirm();
                    break;
            }
        }
    }

    /**
     * 多选删除报警消息二次确认
     */
    private void onAlarmDeleteConfirm() {
        deleteAlarmBeans.clear();
        for (AlarmRecordResultBean.ItemsBean bean : alarmRecordBeans) {
            if (bean.isSelect()) {
                deleteAlarmBeans.add(bean);
            }
        }
        if (deleteAlarmBeans.size() == 0) {
            ToastUtils.showShort(getString(R.string.txt_alarm_delete_error));
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_alarm_delete_hint));
        if (isAllSelect) {
            bean.setAlert(getString(R.string.txt_alarm_delete_all));
        } else {
            bean.setAlert(getString(R.string.txt_alarm_delete_select_number));
        }
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitAlarmDeleteBatch();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 提交批量删除报警消息
     */
    private void submitAlarmDeleteBatch() {
        AlarmDeleteBatchPutBean.ParamsBean paramsBean = new AlarmDeleteBatchPutBean.ParamsBean();
        if (isAllSelect) {
            if (!TextUtils.isEmpty(mSimei)) {
                List<String> simeis = new ArrayList<>();
                simeis.add(mSimei);
                paramsBean.setSimei(simeis);
            } else {
                if (ConstantValue.isLoginForAccount()) {
                    paramsBean.setSfamily(userFamilyId);
                }
            }
        } else {
            List<AlarmDeleteBatchPutBean.ParamsBean.ItemsBean> itemsBeans = new ArrayList<>();
            for (AlarmRecordResultBean.ItemsBean bean : deleteAlarmBeans) {
                AlarmDeleteBatchPutBean.ParamsBean.ItemsBean item = new AlarmDeleteBatchPutBean.ParamsBean.ItemsBean();
                item.setFencename(bean.getFencename());
                item.setTime(bean.getTime());
                item.setType(bean.getType());
                item.setSimei(bean.getSimei());
                itemsBeans.add(item);
            }
            paramsBean.setItems(itemsBeans);
        }

        AlarmDeleteBatchPutBean bean = new AlarmDeleteBatchPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Alarm_Batch_Delete);
        bean.setModule(ModuleValueService.Module_For_Alarm_Batch_Delete);

        showProgressDialog();

        getPresenter().submitAlarmDeleteBatch(bean);
    }

    /**
     * 编辑状态
     */
    private void onMessageEdit() {
        if (alarmRecordBeans.size() == 0) {
            ToastUtils.showShort(getString(R.string.error_no_data_alarm));
            return;
        }
        isEdit = !isEdit;
        for (AlarmRecordResultBean.ItemsBean bean : alarmRecordBeans) {
            bean.setEdit(isEdit);
            if (!isEdit) {
                bean.setSelect(false);
            }
        }
        mAdapter.notifyDataSetChanged();
        ivDelete.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        tvDeleteCancel.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        llDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        if (!isEdit) {
            isAllSelect = false;
            mSelectCount = 0;
            tvDelete.setText(getString(R.string.txt_delete));
            tvSelectAll.setText(getString(R.string.txt_select_all));
        }
    }

    /**
     * 全选
     */
    @SuppressLint("SetTextI18n")
    private void onMessageAllSelect() {
        isAllSelect = !isAllSelect;
        for (AlarmRecordResultBean.ItemsBean bean : alarmRecordBeans) {
            bean.setSelect(isAllSelect);
        }
        mAdapter.notifyDataSetChanged();
        if (isAllSelect) {
            mSelectCount = alarmRecordBeans.size();
        } else {
            mSelectCount = 0;
        }
        onComputeDeleteCount();
    }

    /**
     * 计算选中的录音数量
     */
    @SuppressLint("SetTextI18n")
    private void onComputeDeleteCount() {
        if (mSelectCount < 0) {
            mSelectCount = 0;
        }
        if (mSelectCount == 0) {
            tvDelete.setText(getString(R.string.txt_delete));
        } else {
            tvDelete.setText(getString(R.string.txt_delete) + "(" + mSelectCount + ")");
        }
        if (mSelectCount != 0 && mSelectCount == alarmRecordBeans.size()) {
            tvSelectAll.setText(getString(R.string.txt_select_all_cancel));
            isAllSelect = true;
        } else {
            tvSelectAll.setText(getString(R.string.txt_select_all));
            isAllSelect = false;
        }
    }

    /**
     * 日期选择弹框
     */
    private void showDateSelectPopupWindow() {
        mPopupWindow = new AlertSelectDatePopupWindow(getContext(), startTime, endTime);
        mPopupWindow.showAsDropDown(alarmHead);
        mPopupWindow.setSelectTimeChange(new AlertSelectDatePopupWindow.onSelectTimeChange() {
            @Override
            public void onSelectTime(long start_Time, long end_Time) {
                //返回选择的开始时间 结束时间
                startTime = start_Time;
                endTime = end_Time;
                lastImei = 0;
                lastTime = 0;
                onResetDeviceData(true);
            }
        });
    }

    @Override
    public void getAlarmRecordSuccess(AlarmRecordResultBean alarmRecordResultBean, boolean isRefresh) {
        requestTime = System.currentTimeMillis();
        if (isRefresh) {
            alarmRecordBeans.clear();
        }
        lastImei = alarmRecordResultBean.getLast_imei();
        lastTime = alarmRecordResultBean.getLast_time();
        lastType = alarmRecordResultBean.getLast_type();
        if (alarmRecordResultBean.getItems() != null && alarmRecordResultBean.getItems().size() > 0) {
            alarmRecordBeans.addAll(alarmRecordResultBean.getItems());
        }
        for (AlarmRecordResultBean.ItemsBean bean : alarmRecordBeans) {
            bean.setEdit(isEdit);
            if (isAllSelect) {
                bean.setSelect(true);
            }
        }
        mAdapter.notifyDataSetChanged();
        onShowNoData();
        // 判断是否全选，是的话刷新选中数量
        if (isAllSelect) {
            mSelectCount = alarmRecordBeans.size();
            onComputeDeleteCount();
        }
    }

    @Override
    public void finishRefresh() {
        srlView.setRefreshing(false);
    }

    @Override
    public void endLoadMore(int type) {
        if (type == 0) {
            mAdapter.loadMoreComplete();
        } else {
            mSearchAdapter.loadMoreComplete();
        }
    }

    @Override
    public void setNoMore(int type) {
        if (type == 0) {
            mAdapter.loadMoreEnd();
        } else {
            mSearchAdapter.loadMoreEnd();
        }
    }

    @Override
    public void endLoadFail(int type) {
        if (type == 0) {
            mAdapter.loadMoreFail();
        } else {
            mSearchAdapter.loadMoreFail();
        }
    }

    @Override
    public void submitAlarmDeleteSuccess(BaseBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_delete_success));
        alarmRecordBeans.remove(mPosition);
        mAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    @Override
    public void submitAlarmDeleteBatchSuccess(BaseBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_delete_success));
        if (isAllSelect) {
            alarmRecordBeans.clear();
        } else {
            alarmRecordBeans.removeAll(deleteAlarmBeans);
        }
        mAdapter.notifyDataSetChanged();
        if (alarmRecordBeans.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
            tvDeleteCancel.setVisibility(View.GONE);
            isEdit = false;
            llDelete.setVisibility(View.GONE);
        }
        isAllSelect = false;
        mSelectCount = 0;
        onComputeDeleteCount();
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void getFindDeviceDataSuccess(FindDeviceResultBean bean) {
        if (mSearchLastImei == 0) {
            searchDeviceResultBeans.clear();
        }
        if (bean.getItems() != null && bean.getItems().size() > 0) {
            mSearchLastImei = bean.getItems().get(bean.getItems().size() - 1).getImei();
            searchDeviceResultBeans.addAll(bean.getItems());
        } else {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.showShort(getString(R.string.txt_search_no_data));
            ToastUtils.setGravity(-1, -1, -1); //恢复默认
        }
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_TYPE) {
                mAlermMessageType = data.getStringExtra("type");
                screenTypeLists.clear();
                lastImei = 0;
                lastTime = 0;
                lastType = "";
                if (!TextUtils.isEmpty(mAlermMessageType)) {
                    String[] strType = mAlermMessageType.split(",");
                    for (String typeString : strType) {
                        if (typeString.contains(";")) {
                            String[] lightOff = typeString.split(";");
                            screenTypeLists.addAll(Arrays.asList(lightOff));
                        } else {
                            screenTypeLists.add(typeString);
                        }
                    }
                }
                onResetDeviceData(true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(receiver);
        searchHandler.removeCallbacksAndMessages(null);
        searchHandler = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        MobclickAgent.onPageStart("Alarm");//统计页面("MainScreen"为页面名称，可自定义)
        super.onResume();
    }

    @Override
    public void onPause() {
        MobclickAgent.onPageEnd("Alarm");
        super.onPause();
    }
}

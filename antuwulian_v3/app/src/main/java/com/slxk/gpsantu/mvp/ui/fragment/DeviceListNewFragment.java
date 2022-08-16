package com.slxk.gpsantu.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.huawei.secure.android.common.util.ScreenUtil;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerGroupManagementComponent;
import com.slxk.gpsantu.mvp.contract.GroupManagementContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceBaseResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceDetailResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceListForManagerResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceLocationInfoBean;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;
import com.slxk.gpsantu.mvp.model.bean.GroupAddResultBean;
import com.slxk.gpsantu.mvp.model.bean.TaskProgressResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceListForManagerPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FindDevicePutBean;
import com.slxk.gpsantu.mvp.model.putbean.FreezeEquipmentPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupAddPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupEditPutBean;
import com.slxk.gpsantu.mvp.model.putbean.RemoveDevicePutBean;
import com.slxk.gpsantu.mvp.model.putbean.TaskProgressPubBean;
import com.slxk.gpsantu.mvp.model.putbean.TransferDevicePutBean;
import com.slxk.gpsantu.mvp.presenter.GroupManagementPresenter;
import com.slxk.gpsantu.mvp.ui.activity.AddDeviceActivity;
import com.slxk.gpsantu.mvp.ui.activity.AddDeviceNewActivity;
import com.slxk.gpsantu.mvp.ui.activity.DeviceListActivity;
import com.slxk.gpsantu.mvp.ui.activity.GroupManagementActivity;
import com.slxk.gpsantu.mvp.ui.activity.MainActivity;
import com.slxk.gpsantu.mvp.ui.activity.UserSwitchActivity;
import com.slxk.gpsantu.mvp.ui.adapter.DeviceListAdapter;
import com.slxk.gpsantu.mvp.ui.adapter.GroupListManageAdapter;
import com.slxk.gpsantu.mvp.ui.adapter.SearchDeviceNewAdapter;
import com.slxk.gpsantu.mvp.ui.view.MyLoadMoreView;
import com.slxk.gpsantu.mvp.utils.BroadcastReceiverUtil;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.DeviceUtils;
import com.slxk.gpsantu.mvp.utils.MyDisplayMetrics;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.slxk.gpsantu.mvp.utils.ScreenUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppDialog;
import com.slxk.gpsantu.mvp.weiget.DeviceFailDialog;
import com.slxk.gpsantu.mvp.weiget.GroupAddDialog;
import com.slxk.gpsantu.mvp.weiget.GroupEditDialog;
import com.slxk.gpsantu.mvp.weiget.GroupManagePopupwindow;
import com.slxk.gpsantu.mvp.weiget.GroupTransferDeviceDialog;
import com.slxk.gpsantu.mvp.weiget.TaskProgressDialog;
import com.yinglan.scrolllayout.ScrollLayout;
import com.yinglan.scrolllayout.content.ContentRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DeviceListNewFragment extends BaseFragment <GroupManagementPresenter> implements GroupManagementContract.View{

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_device_add)
    TextView ivAddDevice;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.ll_search_layout)
    LinearLayout llSearch;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.scroll_down_layout)
    ScrollLayout mScrollDownLayout; // 上下滑动view
    @BindView(R.id.recyclerView_device)
    ContentRecyclerView recyclerViewDevice; // 设备列表
    @BindView(R.id.recyclerView_head)
    View recyclerViewHead;
    @BindView(R.id.switch_user_head)
    LinearLayout switchUserHead;
    @BindView(R.id.user_name)
    TextView tvUserName;
    @BindView(R.id.iv_page_fresh)
    ImageView ivPageFresh;

    private TextView tvPrompt;
    private GroupListManageAdapter mAdapter;
    private DeviceListAdapter deviceListAdapter;
    private ArrayList<DeviceGroupResultBean.GaragesBean> groupBeans; // 设备分组
    private String mLastGroupId; // 最后获取到的gid，没有可以为空
    private String mSid; // 分组id
    private String mGroupName; // 分组名称

    private GroupManagePopupwindow mPopupWindow; // 菜单栏
    private final static int Intent_Add_Device = 10; // 添加设备
    private final static int Intent_Device_Manager = 11; // 设备管理
    private final static int Intent_Switch_User = 12; //切换用户

    private static final int mCountDownTime = 2; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private String taskId; // 任务id
    private TaskProgressDialog taskProgressDialog;

    private String familyAuth; // 用户权限

    private List<FindDeviceResultBean.ItemsBean> searchDeviceResultBeans; // 筛选结果设备列表
    private SearchDeviceNewAdapter mSearchAdapter; // 搜索
    private static final int mSearchCountDownTime = 1; // 倒计时固定时长
    private int mSearchCountDown = mSearchCountDownTime; // 倒计时递减时长
    private String strKeyword = ""; // 搜索关键词
    private long mSearchLastImei = 0; // 上一次调用该接口返回的最后一条imei
    private int mSearchLimitSize = 10; // 限制个数，默认10个，越多，速度越慢

    private int mLimitSizeForDevice = 100; // 限定设备数量,默认100
    private String mLastSimeiForDevice; // 最后获取到的simei
    private String mLastSgidForDevice; // 上一次请求的最后一个sgid
    private String userFamilyId; // 用来请求设备数据的对应的组织id，默认指向当前登录用户
    private String deviceStatus; // 查询在线状态，不传入，表示查询全部
    private List<DeviceListForManagerResultBean.ItemsBean> deviceBeans;
    private DeviceListForManagerResultBean.ItemsBean del_deviceBeans;

    private TextView tvAll; // 全部设备
    private TextView tvOnLine; // 在线设备
    private TextView tvStatic; // 静止设备
    private TextView tvOffLine; // 离线设备
    private ImageView viewDown; // 退出滑动视图

    private int all_count;      //全部设备数量
    private int onLine_count;   //在线设备数量
    private int static_count;   //静止设备数量
    private int offLine_count;  //离线设备数量

    public static DeviceListNewFragment newInstance() {
        DeviceListNewFragment fragment = new DeviceListNewFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGroupManagementComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        familyAuth = ConstantValue.getFamilyAuth();
        groupBeans = new ArrayList<>();
        searchDeviceResultBeans = new ArrayList<>();

        tvUserName.setText(ConstantValue.getFamilySName());

        tvAll = recyclerViewHead.findViewById(R.id.tv_device_all);
        tvOnLine = recyclerViewHead.findViewById(R.id.tv_device_line_on);
        tvOffLine = recyclerViewHead.findViewById(R.id.tv_device_line_down);
        tvStatic = recyclerViewHead.findViewById(R.id.tv_device_line_static);
        viewDown = mScrollDownLayout.findViewById(R.id.iv_device_down);

        createGroupListView();
        createDeviceListView();

        onSearchDevice();
        getDeviceGroupList(true, false);


        /**设置 setting*/
        mScrollDownLayout.setMinOffset(0);
        mScrollDownLayout.setMaxOffset(MyDisplayMetrics.dpToPxInt(500));
        mScrollDownLayout.setExitOffset(0);
        mScrollDownLayout.setIsSupportExit(true);
        mScrollDownLayout.setAllowHorizontalScroll(true);
        mScrollDownLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollDownLayout.getBackground().setAlpha(0);
        mScrollDownLayout.setToExit();
    }

    //分组列表
    public void createGroupListView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        srlView.setColorSchemeResources(R.color.color_2E7BEC, R.color.color_2E7BEC);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSid = "";
                mLastGroupId = "";
                mGroupName = "";
                getDeviceGroupList(false, true);
            }
        });

        View statusView = LayoutInflater.from(mContext).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.txt_group_no_data));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new GroupListManageAdapter(R.layout.item_group_manage_new, groupBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDeviceGroupList(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (DeviceGroupResultBean.GaragesBean groupBean : groupBeans) {
                    groupBean.setSelect(false);
                }
                groupBeans.get(position).setSelect(true);
                mAdapter.notifyDataSetChanged();

                mSid = groupBeans.get(position).getSid();
                onDeviceListScreen("");

                if (mScrollDownLayout.getCurrentStatus().equals(ScrollLayout.Status.EXIT)) {
                    // 滑动view打开的情况下
                    mScrollDownLayout.setToOpen();
                    //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
                    final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                            TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
                    ctrlAnimation.setDuration(400l);     //设置动画的过渡时间
                    mScrollDownLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mScrollDownLayout.setVisibility(View.VISIBLE);
                            mScrollDownLayout.startAnimation(ctrlAnimation);
                        }
                    }, 500);
                }
            }
        });
    }

    //设备列表
    public void createDeviceListView(){
        deviceBeans = new ArrayList<>();

        recyclerViewDevice.setLayoutManager(new LinearLayoutManager(mContext));

        View statusView = LayoutInflater.from(mContext).inflate(R.layout.layout_status, recyclerViewDevice, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.txt_device_no_data));
        tvPrompt.setVisibility(View.GONE);
        deviceListAdapter = new DeviceListAdapter(R.layout.item_new_device_list, deviceBeans);
        recyclerViewDevice.setAdapter(deviceListAdapter);
        deviceListAdapter.setEmptyView(statusView);
        LoadMoreView loadMoreView = new MyLoadMoreView();
        deviceListAdapter.setLoadMoreView(loadMoreView);
        deviceListAdapter.setEnableLoadMore(true);
        deviceListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDeviceListForGroup(false, false);
            }
        }, recyclerViewDevice);
        deviceListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(deviceBeans.get(position).getLast_pos());
                if (TextUtils.isEmpty(deviceBeans.get(position).getLast_pos()) || infoBean.getLat() == 0 || infoBean.getLon() == 0) {
                    ToastUtils.showShort(getString(R.string.txt_device_location_no_data));
                    return;
                }
                long imei = deviceBeans.get(position).getImei();
                String smei = deviceBeans.get(position).getSimei();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("imei",imei);
                intent.putExtra("smei", smei);
                intent.setAction(ConstantValue.DEVICE_ACTION);
                startActivity(intent);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

                BroadcastReceiverUtil.showMainPage(mContext, 0);//切换tarbar
            }
        });
        deviceListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                deviceBeans.get(position).setSelect(!deviceBeans.get(position).isSelect());
                deviceListAdapter.notifyItemChanged(position);
            }
        });
        deviceListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                AlertBean bean = new AlertBean();
                bean.setTitle(getString(R.string.txt_tip));
                bean.setAlert(getString(R.string.txt_device_delete_hint_two));
                bean.setType(0);
                AlertAppDialog dialog = new AlertAppDialog();
                dialog.show(getActivity().getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                    @Override
                    public void onConfirm() {
                        del_deviceBeans = deviceBeans.get(position);
                        submitDeleteDevice(deviceBeans.get(position).getSimei());
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                return false;
            }
        });
    }

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollDownLayout.getBackground().setAlpha(255 - (int) precent);
                viewDown.setImageResource(currentProgress > 0.5 ? R.drawable.device_list_chevron_up:R.drawable.device_list_chevron_down);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                // 滑动view关闭的情况下
                mScrollDownLayout.setVisibility(View.INVISIBLE);
            }
            mScrollDownLayout.clearAnimation();
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    @Override
    public void setData(@Nullable Object data) {

    }

    /**
     * 搜索设备
     */
    private void onSearchDevice() {
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(mContext));

        mSearchAdapter = new SearchDeviceNewAdapter(R.layout.item_search_device, searchDeviceResultBeans);
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

        edtSearch.addTextChangedListener(new TextWatcher() {
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
                hideKeyboard(edtSearch);
                long imei = searchDeviceResultBeans.get(position).getImei();
                String smei = searchDeviceResultBeans.get(position).getSimei();
                getDeviceHasLocation(imei, smei);
            }
        });
    }

    /**
     * 获取设备是否有有效定位数据
     *
     * @param imei  设备imei号
     * @param simei simei
     */
    private void getDeviceHasLocation(long imei, String simei) {
        DeviceDetailPutBean.ParamsBean paramsBean = new DeviceDetailPutBean.ParamsBean();
        paramsBean.setSimei(simei);

        DeviceDetailPutBean bean = new DeviceDetailPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_Detail);
        bean.setModule(ModuleValueService.Module_For_Device_Detail);
        bean.setParams(paramsBean);

        showProgressDialog();

        getPresenter().getDeviceHasLocation(bean, imei, simei);
    }

    /**
     * 重置搜索列表数据
     */
    private void onResetSearchData() {
        edtSearch.setText("");
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

        FindDevicePutBean bean = new FindDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Find_Device);
        bean.setModule(ModuleValueService.Module_For_Find_Device);

        if (getPresenter() != null) {
            getPresenter().getFindDeviceData(bean);
        }
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
        if (groupBeans.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        mSid = "";
        mLastGroupId = "";
        mGroupName = "";
        getDeviceGroupList(false, true);
    }

    /**
     * 获取车组织列表和车组列表
     *
     * @param isShow    是否显示加载框
     * @param isRefresh 是否是刷新设备分组
     */
    private void getDeviceGroupList(boolean isShow, boolean isRefresh) {
        if (ConstantValue.isAccountLogin()) {
            DeviceGroupPutBean.ParamsBean paramsBean = new DeviceGroupPutBean.ParamsBean();
            paramsBean.setF_limit_size(0);
            // 限制分组获取数量
            int mLimitSize = 100;
            paramsBean.setG_limit_size(mLimitSize);
            paramsBean.setFamilyid(ConstantValue.getFamilySid());
            paramsBean.setGet_all(true);
            if (!TextUtils.isEmpty(mLastGroupId)) {
                paramsBean.setLast_gid(mLastGroupId);
            }
            DeviceGroupPutBean bean = new DeviceGroupPutBean();
            bean.setParams(paramsBean);
            bean.setFunc(ModuleValueService.Fuc_For_Device_Group_List);
            bean.setModule(ModuleValueService.Module_For_Device_Group_List);

            if (getPresenter() != null) {
                getPresenter().getDeviceGroupList(bean, isShow, isRefresh);
            }
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

    }

    /**
     * 设备列表针对设备状态筛选
     */
    private void onDeviceListScreen(String status) {
        mLastSgidForDevice = "";
        mLastSimeiForDevice = "";
        deviceStatus = status;
        getDeviceListForGroup(true, true);
    }

    @OnClick({R.id.iv_search, R.id.iv_device_add, R.id.iv_back, R.id.tv_search_cancel, R.id.tv_device_all, R.id.tv_device_line_on,R.id.tv_device_line_static,R.id.tv_device_line_down,R.id.iv_device_down,R.id.switch_user_head,R.id.iv_page_fresh})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.iv_device_add:
                    addNewDevice();
                    break;
                case R.id.iv_search:
                    llSearch.setVisibility(View.VISIBLE);
                    edtSearch.setFocusable(true);
                    edtSearch.setFocusableInTouchMode(true);
                    edtSearch.requestFocus();
                    showKeyboard(edtSearch);
                    break;
                case R.id.tv_search_cancel:
                    llSearch.setVisibility(View.GONE);
                    hideKeyboard(edtSearch);
                    onResetSearchData();
                    break;
                case R.id.tv_device_all:
                    onDeviceListScreen("");
                    break;
                case R.id.tv_device_line_on:
                    onDeviceListScreen(ResultDataUtils.Device_State_Line_On);
                    break;
                case R.id.tv_device_line_static:
                    onDeviceListScreen(ResultDataUtils.Device_State_Line_Sleep);
                    break;
                case R.id.tv_device_line_down:
                    onDeviceListScreen(ResultDataUtils.Device_State_Line_Down);
                    break;
                case R.id.iv_device_down:

                    //设置动画，从自身位置的最下端向上滑动了自身的高度，持续时间为500ms
                    final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 1);
                    ctrlAnimation.setDuration(400l);     //设置动画的过渡时间
                    mScrollDownLayout.startAnimation(ctrlAnimation);

                    mScrollDownLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mScrollDownLayout.setToExit();
                        }
                    }, 300);

                    break;
                case R.id.switch_user_head:
                    switchUser();
                    break;
                case R.id.iv_page_fresh:
                    reFreshUI();
                    break;

            }
        }
    }

    /**
     * 刷新分组列表、设备列表
     */
    public void reFreshUI(){
        mLastGroupId = "";
        //刷新分组列表
        getDeviceGroupList(false, true);

        //如果设备列表弹出 同时刷新
        if (mScrollDownLayout.getCurrentStatus().equals(ScrollLayout.Status.OPENED)) {
            onDeviceListScreen(deviceStatus);
        }

        //动画
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.image_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);

        //给图片添加动画
        ivPageFresh.startAnimation(animation);
        ivPageFresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivPageFresh.clearAnimation();
            }
        }, 1000);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Intent_Add_Device || requestCode == Intent_Device_Manager) {
                mSid = "";
                mLastGroupId = "";
                mGroupName = "";
                getDeviceGroupList(true, true);
            }else if(requestCode == Intent_Switch_User){
                mSid = "";
                mLastGroupId = "";
                mGroupName = "";

                //切换tarbar
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                BroadcastReceiverUtil.showMainPage(mContext, 0);

                //刷新列表界面
                tvUserName.setText(ConstantValue.getFamilySName());
                getDeviceGroupList(true, true);

                if (mScrollDownLayout.getCurrentStatus().equals(ScrollLayout.Status.OPENED)) {
                    mScrollDownLayout.setToExit();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 删除设备
     */
    private void submitDeleteDevice(String simei) {
        List<String> simeis = new ArrayList<>();
        simeis.add(simei);

        RemoveDevicePutBean.ParamsBean paramsBean = new RemoveDevicePutBean.ParamsBean();
        paramsBean.setSimei(simeis);
        paramsBean.setDel_type(ResultDataUtils.Device_Delete_Parent);

        RemoveDevicePutBean bean = new RemoveDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Device_Remove);
        bean.setModule(ModuleValueService.Module_For_Device_Remove);

        if (getPresenter() != null) {
            getPresenter().submitDeleteDevice(bean);
        }
    }

    /**
     * 添加设备
     */
    private void addNewDevice() {
        if (groupBeans.size() == 0) {
            return;
        }
        Intent intent = new Intent(mContext, AddDeviceNewActivity.class);
        intent.putParcelableArrayListExtra("groupAll",groupBeans);
        startActivityForResult(intent, Intent_Add_Device);
    }

    /**
     * 切换用户
     */
    private void switchUser(){
        Intent intent = new Intent(mContext, UserSwitchActivity.class);
        startActivityForResult(intent, Intent_Switch_User);
    }
    /**
     * 获取任务进度
     */
    private void getTaskProgress() {
        TaskProgressPubBean.ParamsBean paramsBean = new TaskProgressPubBean.ParamsBean();
        paramsBean.setTask_id(taskId);

        TaskProgressPubBean bean = new TaskProgressPubBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Task_Progress);
        bean.setModule(ModuleValueService.Module_For_Task_Progress);

        if (getPresenter() != null) {
            getPresenter().getTaskProgress(bean);
        }
    }


    /**
     * 获取分组下的设备列表
     *
     * @param isShow    是否显示加载框
     * @param isRefresh 是否刷新数据
     */
    private void getDeviceListForGroup(boolean isShow, boolean isRefresh) {
        DeviceListForManagerPutBean.ParamsBean paramsBean = new DeviceListForManagerPutBean.ParamsBean();
        paramsBean.setLimit_size(mLimitSizeForDevice);
        paramsBean.setGet_total(true);

        if (!TextUtils.isEmpty(mSid)) {
            paramsBean.setGroupid(mSid);
        } else {
            paramsBean.setFamilyid(ConstantValue.getFamilySid());
        }
        if (!TextUtils.isEmpty(mLastSgidForDevice)) {
            paramsBean.setLast_sgid(mLastSgidForDevice);
        }
        if (!TextUtils.isEmpty(mLastSimeiForDevice)) {
            paramsBean.setLast_simei(mLastSimeiForDevice);
        }
        if (!TextUtils.isEmpty(deviceStatus)) {
            paramsBean.setState(deviceStatus);
        }
        DeviceListForManagerPutBean bean = new DeviceListForManagerPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Device_List_For_Group);
        bean.setModule(ModuleValueService.Module_For_Device_List_For_Group);

        if (getPresenter() != null) {
            getPresenter().getDeviceListForGroup(bean, isShow, isRefresh);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            mCountDown--;
            if (mCountDown <= 0) {
                mCountDown = mCountDownTime;
                getTaskProgress();
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };


    @Override
    public void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh) {
        if (isRefresh) {
            groupBeans.clear();
        }
        Parcel mParcel = Parcel.obtain();
        DeviceGroupResultBean.GaragesBean garagesBean = new DeviceGroupResultBean.GaragesBean(mParcel);
        garagesBean.setImei_count(deviceGroupResultBean.getImei_count());
        garagesBean.setSid("");
        garagesBean.setSname(getString(R.string.txt_total_group));
        groupBeans.add(garagesBean);
        if (deviceGroupResultBean.getGarages() != null && deviceGroupResultBean.getGarages().size() > 0) {
            mLastGroupId = deviceGroupResultBean.getGarages().get(deviceGroupResultBean.getGarages().size() - 1).getSid();
            groupBeans.addAll(deviceGroupResultBean.getGarages());
        }
        mAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    @Override
    public void finishRefresh() {
        srlView.setRefreshing(false);
    }

    @Override
    public void endLoadMore(int type) {
        if (type == 0)
            mAdapter.loadMoreComplete();
        else if (type == 1)
            deviceListAdapter.loadMoreComplete();
        else
            mSearchAdapter.loadMoreComplete();
    }

    @Override
    public void setNoMore(int type) {
        if (type == 0)
            mAdapter.loadMoreEnd();
        else if (type == 1)
            deviceListAdapter.loadMoreEnd();
        else
            mSearchAdapter.loadMoreEnd();
    }

    @Override
    public void endLoadFail(int type) {
        if (type == 0)
            mAdapter.loadMoreFail();
        else if (type == 1)
            deviceListAdapter.loadMoreFail();
        else
            mSearchAdapter.loadMoreFail();
    }

    @Override
    public void submitGroupAddSuccess(GroupAddResultBean groupAddResultBean) {
        ToastUtils.showShort(getString(R.string.txt_add_group_success));
        getData();
    }

    @Override
    public void submitGroupEditSuccess(BaseBean baseBean, String name, String gid) {
        ToastUtils.showShort(getString(R.string.txt_edit_group_success));
        mGroupName = name;
        for (DeviceGroupResultBean.GaragesBean bean : groupBeans) {
            if (bean.getSid().equals(gid)) {
                bean.setSname(name);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void submitTransferDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getActivity().getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.showShort(getString(R.string.txt_transfer_device_success));
                        } else {
                            ToastUtils.showShort(return_message);
                        }
                        ivSearch.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.showShort(getString(R.string.txt_transfer_device_success));
            getData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getActivity().getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 1);
            }
        }
    }

    @Override
    public void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getActivity().getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.showShort(getString(R.string.txt_delete_success));
                        } else {
                            ToastUtils.showShort(return_message);
                        }
                        ivSearch.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();//刷新分组
                                freshDeviceList();//刷新列表
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.showShort(getString(R.string.txt_delete_success));
            getData();//刷新分组
            freshDeviceList();//刷新列表

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getActivity().getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 3);
            }
        }
    }

    public void freshDeviceList(){

        all_count--;
        tvAll.setText(getString(R.string.txt_all) + "(" + all_count + ")");

        switch (del_deviceBeans.getState()) {
            case ResultDataUtils.Device_State_Line_Down:
                offLine_count--;
                tvOffLine.setText(getString(R.string.txt_state_line_down) + "(" + offLine_count + ")");
                break;
            case ResultDataUtils.Device_State_Line_On:
                onLine_count--;
                tvOnLine.setText(getString(R.string.txt_state_line_on) + "(" + onLine_count + ")");
                break;
            case ResultDataUtils.Device_State_Line_Sleep:
                static_count--;
                tvStatic.setText(getString(R.string.txt_state_line_static) + "(" + static_count + ")");
                break;
        }

        //刷新设备列表界面
        deviceBeans.remove(del_deviceBeans);
        deviceListAdapter.notifyDataSetChanged();
    }

    @Override
    public void submitDeleteGroupSuccess(BaseBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_delete_success));
        getData();
    }

    @Override
    public void submitFreezeEquipmentSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getActivity().getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.showShort(getString(R.string.txt_successful_operation));
                        } else {
                            ToastUtils.showShort(return_message);
                        }
                        ivSearch.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.showShort(getString(R.string.txt_successful_operation));
            getData();
            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getActivity().getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 4);
            }
        }
    }

    @Override
    public void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean) {
        if (taskProgressDialog != null) {
            double progress = ((double) taskProgressResultBean.getCurrent_count() / taskProgressResultBean.getTotal()) * 100;
            int intProgress = (int) progress;
            if (intProgress > 100) {
                intProgress = 100;
            }
            taskProgressDialog.setProgressBar(intProgress, taskProgressResultBean.isIs_finish(),
                    taskProgressResultBean.getReturn_msg(), taskProgressResultBean.getReturn_code());
        }
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
    public void getDeviceListForGroupSuccess(DeviceListForManagerResultBean deviceListForManagerResultBean, boolean isRefresh) {
        if (isRefresh) {
            deviceBeans.clear();
            if (deviceListForManagerResultBean.getAll_total() > 0) {
                all_count = deviceListForManagerResultBean.getAll_total();
                tvAll.setText(getString(R.string.txt_all) + "(" + deviceListForManagerResultBean.getAll_total() + ")");
            } else {
                tvAll.setText(getString(R.string.txt_all) + "(0)");
            }
            if (deviceListForManagerResultBean.getLine_total() > 0) {
                onLine_count = deviceListForManagerResultBean.getLine_total();
                tvOnLine.setText(getString(R.string.txt_state_line_on) + "(" + deviceListForManagerResultBean.getLine_total() + ")");
            } else {
                tvOnLine.setText(getString(R.string.txt_state_line_on) + "(0)");
            }
            if (deviceListForManagerResultBean.getDown_total() > 0) {
                offLine_count = deviceListForManagerResultBean.getDown_total();
                tvOffLine.setText(getString(R.string.txt_state_line_down) + "(" + deviceListForManagerResultBean.getDown_total() + ")");
            } else {
                tvOffLine.setText(getString(R.string.txt_state_line_down) + "(0)");
            }
            if (deviceListForManagerResultBean.getSleep_total() > 0) {
                static_count = deviceListForManagerResultBean.getSleep_total();
                tvStatic.setText(getString(R.string.txt_state_line_static) + "(" + deviceListForManagerResultBean.getSleep_total() + ")");
            } else {
                tvStatic.setText(getString(R.string.txt_state_line_static) + "(0)");
            }
            onDeviceStatusShow();
        }
        if (deviceListForManagerResultBean.getItems() != null && deviceListForManagerResultBean.getItems().size() > 0) {
            mLastSgidForDevice = deviceListForManagerResultBean.getItems().get(deviceListForManagerResultBean.getItems().size() - 1).getSgid();
            mLastSimeiForDevice = deviceListForManagerResultBean.getItems().get(deviceListForManagerResultBean.getItems().size() - 1).getSimei();
            deviceBeans.addAll(deviceListForManagerResultBean.getItems());
        }
        deviceListAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void onRefreshData() {
        mSid = "";
        mLastGroupId = "";
        mGroupName = "";
        getDeviceGroupList(false, true);
    }

    @Override
    public void getDeviceHasLocationSuccess(DeviceDetailResultBean deviceDetailResultBean, long imei, String simei) {
        if (deviceDetailResultBean.getState_begin_time() != 0) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("imei", imei);
            intent.putExtra("smei", simei);
            intent.setAction(ConstantValue.DEVICE_ACTION);
            startActivity(intent);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

            hideKeyboard(edtSearch);
            BroadcastReceiverUtil.showMainPage(mContext, 0);//切换tarbar
        } else {
            ToastUtils.showShort(getString(R.string.txt_device_location_no_data));
        }
    }

    /**
     * 切换显示状态类型
     */
    private void onDeviceStatusShow() {
        int colorUnSelect = getResources().getColor(R.color.color_2E7BEC);
        int colorSelect = getResources().getColor(R.color.color_FFFFFF);
        tvAll.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_2_left_2));
        tvAll.setTextColor(colorUnSelect);
        tvOnLine.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_5_2));
        tvOnLine.setTextColor(colorUnSelect);
        tvStatic.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_5_2));
        tvStatic.setTextColor(colorUnSelect);
        tvOffLine.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_2_right_2));
        tvOffLine.setTextColor(colorUnSelect);
        if (TextUtils.isEmpty(deviceStatus)) {
            tvAll.setTextColor(colorSelect);
            tvAll.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_2_left));
        } else {
            switch (deviceStatus) {
                case ResultDataUtils.Device_State_Line_Down:
                    tvOffLine.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_2_right));
                    tvOffLine.setTextColor(colorSelect);
                    break;
                case ResultDataUtils.Device_State_Line_On:
                    tvOnLine.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_5_3));
                    tvOnLine.setTextColor(colorSelect);
                    break;
                case ResultDataUtils.Device_State_Line_Sleep:
                    tvStatic.setBackground(getResources().getDrawable(R.drawable.bg_2e7bec_5_3));
                    tvStatic.setTextColor(colorSelect);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        searchHandler.removeCallbacksAndMessages(null);
        searchHandler = null;
        super.onDestroy();
    }
}

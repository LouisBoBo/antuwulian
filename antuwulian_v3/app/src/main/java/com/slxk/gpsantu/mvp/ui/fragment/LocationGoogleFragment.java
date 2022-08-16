package com.slxk.gpsantu.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerLocationGoogleComponent;
import com.slxk.gpsantu.mvp.contract.LocationGoogleContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.DataCenterBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceConfigResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceDetailResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceListForManagerResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceListResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceLocationInfoBean;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;
import com.slxk.gpsantu.mvp.model.bean.MergeAccountResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneCodeResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceConfigPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailIntentBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceListPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FindDevicePutBean;
import com.slxk.gpsantu.mvp.model.putbean.MergeAccountPutBean;
import com.slxk.gpsantu.mvp.model.putbean.OnKeyFunctionPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneCodePutBean;
import com.slxk.gpsantu.mvp.presenter.LocationGooglePresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.ui.activity.AddDeviceNewActivity;
import com.slxk.gpsantu.mvp.ui.activity.DeviceDetailActivity;
import com.slxk.gpsantu.mvp.ui.activity.FenceActivity;
import com.slxk.gpsantu.mvp.ui.activity.GroupManagementActivity;
import com.slxk.gpsantu.mvp.ui.activity.LocationModeActivity;
import com.slxk.gpsantu.mvp.ui.activity.LocationModeYiWenActivity;
import com.slxk.gpsantu.mvp.ui.activity.MainActivity;
import com.slxk.gpsantu.mvp.ui.activity.NavigationGoogleActivity;
import com.slxk.gpsantu.mvp.ui.activity.PayWebViewActivity;
import com.slxk.gpsantu.mvp.ui.activity.RealTimeModeActivity;
import com.slxk.gpsantu.mvp.ui.activity.RealTimeTrackGoogleActivity;
import com.slxk.gpsantu.mvp.ui.activity.RecordActivity;
import com.slxk.gpsantu.mvp.ui.activity.SettingMoreActivity;
import com.slxk.gpsantu.mvp.ui.activity.SimDetailActivity;
import com.slxk.gpsantu.mvp.ui.activity.TrackGoogleActivity;
import com.slxk.gpsantu.mvp.ui.adapter.FunctionAdapter;
import com.slxk.gpsantu.mvp.ui.adapter.GroupListHomeAdapter;
import com.slxk.gpsantu.mvp.ui.adapter.SearchDeviceAdapter;
import com.slxk.gpsantu.mvp.ui.view.MyLoadMoreView;
import com.slxk.gpsantu.mvp.ui.view.guideview.MaskHintView;
import com.slxk.gpsantu.mvp.utils.BitmapHelperGoogle;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.slxk.gpsantu.mvp.utils.DeviceUtils;
import com.slxk.gpsantu.mvp.utils.FunctionType;
import com.slxk.gpsantu.mvp.utils.GoogleLocationUtils;
import com.slxk.gpsantu.mvp.utils.GoogleMapUtils;
import com.slxk.gpsantu.mvp.utils.GpsUtils;
import com.slxk.gpsantu.mvp.utils.LocationAddress;
import com.slxk.gpsantu.mvp.utils.LocationAddressParsed;
import com.slxk.gpsantu.mvp.utils.LocationGoogleAddress;
import com.slxk.gpsantu.mvp.utils.LocationGoogleAddressParsed;
import com.slxk.gpsantu.mvp.utils.PolygonalArea;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppDialog;
import com.slxk.gpsantu.mvp.weiget.GroupSelectDialog;
import com.slxk.gpsantu.mvp.weiget.MergeAccountDialog;
import com.slxk.gpsantu.mvp.weiget.MergeAccountFailDialog;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 定位首页-谷歌地图
 * <p>
 * Created by MVPArmsTemplate on 10/28/2020 10:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LocationGoogleFragment extends BaseFragment<LocationGooglePresenter> implements LocationGoogleContract.View,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener, GoogleMap.OnCameraChangeListener {
    @BindView(R.id.iv_car_people)
    ImageView ivCarPeople; // 用户位置或设备的位置
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh; // 刷新数据
    @BindView(R.id.ll_location_now)
    LinearLayout llLocationNow; // 立即定位
    @BindView(R.id.iv_map_check)
    ImageView ivCheckMap; // 切换地图
    @BindView(R.id.iv_traffic)
    ImageView ivTraffic; // 路况
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout; // 抽屉
    @BindView(R.id.iv_amap_select)
    ImageView ivAmapSelect; // 高德地图
    @BindView(R.id.iv_baidu_select)
    ImageView ivBaiduSelect; // 百度地图
    @BindView(R.id.iv_google_select)
    ImageView ivGoogleSelect; // 谷歌地图
    @BindView(R.id.ll_side_bar)
    LinearLayout llSideBar; // 侧滑栏主布局
    @BindView(R.id.iv_general_map) //2D 切换
    ImageView ivGeneralMap;
    @BindView(R.id.tv_general_map)
    TextView tvGeneralMap;
    @BindView(R.id.iv_satellite_map) //卫星图 切换
    ImageView ivSatelliteMap;
    @BindView(R.id.tv_satellite_map)
    TextView tvSatelliteMap;
    @BindView(R.id.iv_map_baidu)
    ImageView ivMapBaidu; // 百度地图
    @BindView(R.id.tv_map_baidu)
    TextView tvMapBaidu;
    @BindView(R.id.iv_map_amap)
    ImageView ivMapAMap; // 高德地图
    @BindView(R.id.tv_map_amap)
    TextView tvMapAMap;
    @BindView(R.id.iv_map_google)
    ImageView ivMapGoogle; // 谷歌地图
    @BindView(R.id.tv_map_google)
    TextView tvMapGoogle;
    @BindView(R.id.iv_info_up)
    ImageView ivInfoUp;  // 显示底部弹出框 or 关闭弹出框
    @BindView(R.id.iv_up_down)
    ImageView ivUpDown; // 显示底部弹出框 or 关闭弹出框
    @BindView(R.id.gridView_menu)
    GridView gridViewMenu; // 菜单
    @BindView(R.id.ll_device_info)
    LinearLayout llDeviceInfo; // 底部布局
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName; // 设备名称
    @BindView(R.id.tv_state)
    TextView tvState; // 设备状态
    @BindView(R.id.tv_battery)
    TextView tvBattery; // 设备电量
    @BindView(R.id.iv_battery)
    ImageView ivBattery; // 电量图标
    @BindView(R.id.tv_gsm_number)
    TextView tvGsmNumber; // gsm信号
    @BindView(R.id.iv_gsm)
    ImageView ivGsm; // gsm信号图标
    @BindView(R.id.tv_gps_number)
    TextView tvGpsNumber; // gps信号
    @BindView(R.id.tv_bd_number)
    TextView tvBDNumber; // 北斗信号
    @BindView(R.id.iv_gps_number)
    TextView ivGpsNumber; // gps信号图标
    @BindView(R.id.iv_bd_number)
    TextView ivBDNumber; // 北斗信号图标
    @BindView(R.id.iv_gps_number_all)
    ImageView ivGpsNumberAll; // 卫星总颗数图标
    @BindView(R.id.tv_gps_number_all)
    TextView tvGpsNumberAll; // 卫星总颗数
    @BindView(R.id.iv_loc_type)
    ImageView ivLocationType; // 当前处于什么信号，卫星，基站，wifi
    @BindView(R.id.tv_speed)
    TextView tvSpeed; // 速度
    @BindView(R.id.tv_voltage)
    TextView tvVoltage; // 电压
    @BindView(R.id.tv_mileage_text)
    TextView tvMileage; // 里程
    @BindView(R.id.tv_mode)
    TextView tvMode; // 定位模式
    @BindView(R.id.tv_acc)
    TextView tvAcc; // acc
    @BindView(R.id.tv_power_state)
    TextView tvPowerState; // 充电状态
    @BindView(R.id.tv_static_time)
    TextView tvStaticTime; // 静止时长
    @BindView(R.id.tv_communication)
    TextView tvCommunication;  // 通讯时间
    @BindView(R.id.tv_location_time)
    TextView tvLocationTime;  // 定位时间
    @BindView(R.id.tv_address)
    TextView tvAddress; // 定位地址
    @BindView(R.id.btn_record)
    Button btnRecord; // 录音
    @BindView(R.id.btn_track)
    Button btnTrack; // 轨迹
    @BindView(R.id.iv_static_time)
    ImageView ivStaticTime;
    @BindView(R.id.tv_static_time_zh)
    TextView tvStaticTimeZH;
    @BindView(R.id.iv_communication)
    ImageView ivCommunication;
    @BindView(R.id.tv_communication_time_zh)
    TextView tvCommunicationTimeZH;
    @BindView(R.id.iv_location_time)
    ImageView ivLocationTime;
    @BindView(R.id.tv_location_time_zh)
    TextView tvLocationTimeZH;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    @BindView(R.id.tv_address_zh)
    TextView tvAddressZH;
    @BindView(R.id.info_iv_car_people) //人车切换
    ImageView infoIvCarPeople;
    @BindView(R.id.ll_keyword_search)
    LinearLayout llKeywordSearch; // 搜索结果布局
    @BindView(R.id.edt_keyword)
    EditText edtSearchImei; // 搜索关键词
    @BindView(R.id.tv_cancel)
    TextView tvCancel; // 取消搜索
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch; // 搜索设备
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_panorama)
    ImageView ivPanorama;
    @BindView(R.id.rl_add_device_popup)
    RelativeLayout rlAddDevicePopup;

    private SupportMapFragment mapFragment; // 谷歌地图
    private GoogleMap mGoogleMap; // 谷歌地图

    private LatLng centerPoint = new LatLng(39.90923, 116.397428);
    private float mZoom = 16;
    private HashMap<String, Marker> deviceMap; // 存储任务
    private LatLng mLatLngLocation; // 设备的经纬度
    private BitmapHelperGoogle bitmapHelper;
    private LatLngBounds.Builder mBuilder; // 所有设备可视化Builder
    private Marker myLocationMarker;//我的位置

    private boolean isLocationChina = true; // 当前手机定位是否是在中国，在中国的话不显示3D地图建筑物名称，台湾显示建筑物名称

    private double mLatitudePhone = 0.0; // 定位获取到的纬度
    private double mLongitudePhone = 0.0; // 定位获取到的经度
    private String selectImei; // 选中的设备的imei号(展示使用，用来做唯一匹配)
    private DeviceListResultBean.ItemsBean selectCarBean; // 选中的设备
    private DeviceDetailResultBean selectDeviceDetailBean; // 选中的设备的详细信息
    private Marker currentMarker; // 选中的marker
    private ArrayList<DeviceListResultBean.ItemsBean> deviceDataBeans; // 设备列表数据
    private ArrayList<DeviceListResultBean.ItemsBean> deviceSwitchBeans; // 设备左右切换列表数据

    private boolean isSatelliteMap = false; // 是否是卫星地图
    private boolean isSwitchMapTraffic = false; // 是否开启了路况信息，false：否
    private boolean isClearMap = false; // 是否清理地图，用于设备列表选中设备时判断
    private boolean isDeviceCenter = true; // 设备是否显示在地图中心，用于设备号登录判断

    private long refresh_time = 0; // 主动刷新数据间隔
    private boolean isFirstData = true; // 是否是第一次加载数据
    private boolean isAccountHasDevice = false; // 账号登录，账号下是否有有效定位数据设备
    private boolean isOperationalRestrictions = false; // 是否是操作限制
    private String userFamilyId; // 用来请求设备数据的对应的组织id，默认指向当前登录用户
    // 获取设备列表数据
    private int limitSizeForDevice = 200; // 最大获取设备列表数量限制
    private boolean isAllDevice = true; // 是否拉取了账号下的所有设备
    private ArrayList<String> mSimeiLists; // 用于刷新数据的设备imei号

    private static final int mCountDownTime = 10; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private boolean isShowDeviceInfo = false; // 是否有选中设备，显示详细信息悬浮框
    private double selectLatForDevice; // 选中设备的纬度
    private double selectLonForDevice; // 选中设备的经度
    private String deviceVersion; // 选中设备的版本
    private ArrayList<String> mCallSimeis; // 需要下发位置查询的设备列表，如果非点名模式，将忽略这个参数
    private boolean isCallAllDev = true; // 如果设备处于点名模式，是否针对返回的设备集体下发一次位置查询，默认false，不下发;设置true，下发
    private int mCallTheRollMode = 0; // 当选中设备处于点名模式时，每30秒下发一次点名指令。

    private ChangePageReceiver receiver; // 注册广播接收器
    private LocationGoogleFragment.DeviceSelectReceiver deviceReceiver;//注册设备选中接受器
    private CheckUserFreshDeviceListReceiver deviceListReceiver; //注册刷新设备广播接收器
    private int fragmentPosition = 0; // 当前切换的fragment位置，只有在首页的时候，才开启请求数据
    // 分组列表
    private String mLastGroupId; // 最后获取到的gid，没有可以为空
    private int mLimitSize = 100; // 限制分组获取数量
    private ArrayList<DeviceGroupResultBean.GaragesBean> groupDataBeans; // 设备分组

    private ArrayList<FindDeviceResultBean.ItemsBean> searchDeviceResultBeans; // 筛选结果设备列表
    private SearchDeviceAdapter mSearchAdapter; // 搜索
    private static final int mSearchCountDownTime = 1; // 倒计时固定时长
    private int mSearchCountDown = mSearchCountDownTime; // 倒计时递减时长
    private String strKeyword = ""; // 搜索关键词
    private long mSearchLastImei = 0; // 上一次调用该接口返回的最后一条imei
    private int mSearchLimitSize = 10; // 限制个数，默认10个，越多，速度越慢


    private String mPhoneZone = "86"; // 电话号码区号

    private int mapType = 0; // 地图类型，0：百度地图，1：高德地图，2：谷歌地图
    private BottomSheetBehavior<LinearLayout> mBehavior;
    private List<DataCenterBean> functionBeans; // 功能菜单
    private FunctionAdapter mAdapter;
    private String modeFunction = ""; //支持的模式
    private boolean isCar = true; //车辆位置
    private boolean isFromSearch = false; // 来自首页搜索
    private MergeAccountDialog mergeAccountDialog;
    private boolean isFromDeviceList = false; // 来自设备列表搜索
    private boolean isFromCheckUser = false; // 来自切换用户
    private MaskHintView maskHintView;
    private int protocol = 1; // 设备协议
    private List<Integer> increment; //增值服务

    public static LocationGoogleFragment newInstance() {
        LocationGoogleFragment fragment = new LocationGoogleFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLocationGoogleComponent //如找不到该类,请编译一下项�?
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_google, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivPanorama.setVisibility(View.GONE); //谷歌地图 隐藏街景
        functionBeans = new ArrayList<>();
        deviceMap = new HashMap<>();
        deviceDataBeans = new ArrayList<>();
        groupDataBeans = new ArrayList<>();
        mSimeiLists = new ArrayList<>();

        userFamilyId = ConstantValue.getFamilySid();
        mCallSimeis = new ArrayList<>();
        deviceSwitchBeans = new ArrayList<>();
        searchDeviceResultBeans = new ArrayList<>();

        mapType = ConstantValue.getMapType();
        int zone = MyApplication.getMMKVUtils().getInt(ConstantValue.Phone_Zone, 86);
        mPhoneZone = String.valueOf(zone);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        llLocationNow.setVisibility(View.GONE);

        onSearchDevice();
        // 注册一个广播接收器，用于接收从首页跳转到报警消息页面的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("devicerun");
        receiver = new ChangePageReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(receiver, filter);

        // 注册一个设备广播接收器，用于接收从设备列表选中的设备
        IntentFilter filterDev = new IntentFilter();
        filterDev.addAction(ConstantValue.DEVICE_ACTION);
        deviceReceiver = new LocationGoogleFragment.DeviceSelectReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(deviceReceiver, filterDev);

        //注册一个刷新设备的广播接收器
        IntentFilter filterDeviceList = new IntentFilter();
        filterDeviceList.addAction("check_user");
        deviceListReceiver = new CheckUserFreshDeviceListReceiver();
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(deviceListReceiver, filterDeviceList);

        onMergeAccount();
        //底部弹框初始化
        mBehavior = BottomSheetBehavior.from(llDeviceInfo);
        hideBehavior();
        mBehavior.setBottomSheetCallback(mBottomSheetCallback);
        ((AnimationDrawable) ivUpDown.getDrawable()).start();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);//选择手势
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);//缩放按钮
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnMyLocationClickListener(this);
        mGoogleMap.setOnCameraChangeListener(this);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(centerPoint)      // Sets the center of the map to Mountain View
                .zoom(mZoom)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                  // Sets the tilt of the camera to 30 degrees
                .build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        bitmapHelper = new BitmapHelperGoogle(MyApplication.getMyApp());
        getLocation(true);
        onCheckMapType();
        onCheckSatelliteMap();
        getDeviceList(true, true, false, false);
    }

    /**
     * 页面切换广播，广播接收器
     */
    private class ChangePageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.hasExtra("type")) {
                    fragmentPosition = intent.getIntExtra("type", 0);
                    if (fragmentPosition != 0) {
                        handler.removeCallbacksAndMessages(null);
                    }
                }
            }
        }
    }


    /**
     * 设备列表选中广播，广播接收器
     */
    private class DeviceSelectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                long imei = 0L;
                String smei = "";
                if (intent.hasExtra("imei")) {
                    imei = intent.getLongExtra("imei", 0L);
                }
                if (intent.hasExtra("smei")) {
                    smei = intent.getStringExtra("smei");
                }
                if (imei != 0L && smei != null && !smei.isEmpty()) {
                    isFromDeviceList = true;
                    deviceClickForGroup(imei, smei);
                }
            }
        }
    }

    /**
     * 切换用户刷新列表，广播接收器
     */
    private class CheckUserFreshDeviceListReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                isFromCheckUser = true;
                userFamilyId = ConstantValue.getFamilySid();
                deviceDataBeans.clear();
                getDeviceList(true, true, true, false);
            }
        }
    }

    /**
     * 底部菜单显示回调
     */
    BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    // LogUtil.e("关闭");
                    break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    // LogUtil.e("过渡");
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    // LogUtil.e("展开");
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    // LogUtil.e("隐藏");
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    // LogUtil.e("自动打开/关闭");
                    break;
            }
        }


        /**
         * 当bottom sheets拖拽时回调
         * @param slideOffset 滑动量;从0到1时向上移动
         */
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            if (MyApplication.getMyApp().getImei() == 0) {//防止取消选中，底部菜单不收回
                return;
            }
            if (llDeviceInfo != null) {
                float height = gridViewMenu.getHeight() * 1f / (llDeviceInfo.getHeight());
                if (slideOffset < height / 2) {
                    llDeviceInfo.post(new Runnable() {
                        @Override
                        public void run() {
                            int height = 0;
                            if (getContext() != null)
                                height = SizeUtils.dp2px(16); // gridViewMenu顶部和底部距离
                            if (llDeviceInfo != null) {
                                mBehavior.setPeekHeight(llDeviceInfo.getHeight() - gridViewMenu.getHeight() - height);
                                mBehavior.setHideable(false);
                            }
                        }
                    });
                }
            }
            if (ivUpDown != null) {
                if (slideOffset >= 0) {
                    // 向上箭头处理
                    ivUpDown.setRotation(180 * slideOffset);
                } else {//向下滑动
                    ivUpDown.setRotation(0);
                }
            }
        }
    };

    /**
     * 判断是否合并账号
     */
    private void onMergeAccount() {
        if (MyApplication.getMyApp().isMergeAccount() && ConstantValue.isLoginForAccount() && !ConstantValue.isNoMoreReminders()) {
            mergeAccountDialog = new MergeAccountDialog();
            mergeAccountDialog.show(getFragmentManager(), new MergeAccountDialog.onMergeAccountChange() {
                @Override
                public void onSendCode() {
                    onSendPhoneCode();
                }

                @Override
                public void onMergeAccount() {
                    if (groupDataBeans.size() > 0) {
                        onMergeGroupSelect();
                        mergeAccountDialog.dismiss();
                    } else {
                        getDeviceGroupList(true, true);
                    }
                }
            });
        }
    }

    /**
     * 合并账号，选择分组合并
     */
    private void onMergeGroupSelect() {
        ivSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                onShowMergeGroupSelect();
            }
        }, 300);
    }

    /**
     * 合并账号，选择分组合
     */
    private void onShowMergeGroupSelect() {
        GroupSelectDialog dialog = new GroupSelectDialog();
        dialog.show(getFragmentManager(), groupDataBeans, new GroupSelectDialog.onGroupSelectferDeviceChange() {
            @Override
            public void onGroupSelect(String sid) {
                submitMergeAccount(sid);
            }

            @Override
            public void onCancel() {
                onShowMergeAccount();
            }
        });
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
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    hideKeyboard(edtSearchImei);
                    getDeviceHasLocation(searchDeviceResultBeans.get(position).getImei(), searchDeviceResultBeans.get(position).getSimei());
                }
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
        paramsBean.setGet_user(true);

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
     * 获取车组织列表和车组列表
     *
     * @param isRefresh 是否是刷新设备分组
     */
    private void getDeviceGroupList(boolean isRefresh, boolean isShow) {
        if (ConstantValue.isAccountLogin()) {
            DeviceGroupPutBean.ParamsBean paramsBean = new DeviceGroupPutBean.ParamsBean();
            paramsBean.setF_limit_size(0);
            paramsBean.setG_limit_size(mLimitSize);
            paramsBean.setFamilyid(ConstantValue.getFamilySid());
            if (isRefresh) {
                paramsBean.setGet_all(true);
            }
            if (!TextUtils.isEmpty(mLastGroupId)) {
                paramsBean.setLast_gid(mLastGroupId);
            }
            DeviceGroupPutBean bean = new DeviceGroupPutBean();
            bean.setParams(paramsBean);
            bean.setFunc(ModuleValueService.Fuc_For_Device_Group_List);
            bean.setModule(ModuleValueService.Module_For_Device_Group_List);

            getPresenter().getDeviceGroupList(bean, isRefresh);
        }
    }

    /**
     * 设备列表-选择设备
     *
     * @param imei  设备未加密的imei号
     * @param simei 设备加密的simei号
     */
    private void deviceClickForGroup(long imei, String simei) {
        selectImei = imei + "";
        if (isAllDevice) {
            selectCarBean = null;
            if (!TextUtils.isEmpty(selectImei)) {
                for (DeviceListResultBean.ItemsBean bean : deviceDataBeans) {
                    if (selectImei.equals(bean.getImei() + "")) {
                        selectCarBean = bean;
                        break;
                    }
                }
                if (selectCarBean != null) {
                    isShowDeviceInfo = true;
                    if (isFromSearch) { //来自首页搜索，则直接展示
                        isFromSearch = false;
                        setDeviceDetailData();
                    }
                } else {
                    mSimeiLists.clear();
                    mSimeiLists.add(simei);
                    getDeviceList(true, true, true, false);
                }
            }
        } else {
            mSimeiLists.clear();
            mSimeiLists.add(simei);
            isClearMap = true; // 清理地图，如果设备存在于已加载的设备列表中，则不清理地图
            isOperationalRestrictions = true; // 超过200台的 默认去选中设备
            for (DeviceListResultBean.ItemsBean bean : deviceDataBeans) {
                if (imei == bean.getImei()) {
                    isClearMap = false;
                    isOperationalRestrictions = false;
                    break;
                }
            }
            getDeviceList(true, true, isClearMap, false);
        }
    }

    /**
     * 获取设备列表
     *
     * @param isShow              是否弹出加载看
     * @param isInitiativeRefresh 是否主动刷新数据
     * @param clearMap            是否清理地图
     * @param isDataError         是否是数据错误，冻结设备，返回的是269877281，数据已变更,请刷新 返回的是269877251
     */
    private void getDeviceList(boolean isShow, boolean isInitiativeRefresh, boolean clearMap, boolean isDataError) {
        DeviceListPutBean.ParamsBean paramsBean = new DeviceListPutBean.ParamsBean();
        paramsBean.setLimit_size(limitSizeForDevice);
        if (isCallAllDev) {
            paramsBean.setCall_all_dev(true);
        }
        if (mCallTheRollMode >= 3) {
            mCallSimeis.clear();
            if (!ConstantValue.isLoginForAccount()) {
                if (deviceDataBeans.size() > 0 && !ResultDataUtils.Device_State_Line_Down.equals(MyApplication.getMyApp().getDeviceState())) {
                    if (!TextUtils.isEmpty(deviceDataBeans.get(0).getSimei())) {
                        mCallSimeis.add(deviceDataBeans.get(0).getSimei());
                    } else {
                        mCallSimeis.add("");
                    }
                    paramsBean.setCall_simei(mCallSimeis);
                }
            } else {
                if (selectCarBean != null && !ResultDataUtils.Device_State_Line_Down.equals(MyApplication.getMyApp().getDeviceState())) {
                    if (!TextUtils.isEmpty(selectCarBean.getSimei())) {
                        mCallSimeis.add(selectCarBean.getSimei());
                    } else {
                        mCallSimeis.add("");
                    }
                    paramsBean.setCall_simei(mCallSimeis);
                }
            }
            mCallTheRollMode = 0;
        }
        if (!isDataError && !isAllDevice && deviceDataBeans.size() == 1) {
            if (mSimeiLists.size() == 0) {
                for (int i = 0; i < deviceDataBeans.size(); i++) {
                    mSimeiLists.add(deviceDataBeans.get(i).getSimei());
                }
            }
        }
        if (mSimeiLists.size() > 0) {
            paramsBean.setSimei(mSimeiLists);
        } else {
            if (!TextUtils.isEmpty(userFamilyId)) {
                paramsBean.setSfamily(userFamilyId);
            }
        }
        DeviceListPutBean bean = new DeviceListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_List);
        bean.setModule(ModuleValueService.Module_For_Device_List);
        bean.setParams(paramsBean);

        if (isShow) {
            showProgressDialog();
        }

        getPresenter().getDeviceList(bean, isShow, isInitiativeRefresh, clearMap);
    }

    /**
     * 获取设备详细信息
     */
    private void getDeviceDetailInfo() {
        if (selectCarBean != null) {
            selectDeviceDetailBean = null;

            DeviceDetailPutBean.ParamsBean paramsBean = new DeviceDetailPutBean.ParamsBean();
            paramsBean.setSimei(selectCarBean.getSimei());
            paramsBean.setGet_increment(true); //获取设备的增值服务

            DeviceDetailPutBean bean = new DeviceDetailPutBean();
            bean.setFunc(ModuleValueService.Fuc_For_Device_Detail);
            bean.setModule(ModuleValueService.Module_For_Device_Detail);
            bean.setParams(paramsBean);

            getPresenter().getDeviceDetailInfo(bean);
        }
    }

//    /**
//     * 获取设备详细信息
//     *
//     * @param state 设备状态
//     * @param power 设备电量
//     * @param time  最后定位时间
//     */
//    private void getDeviceDetailInfo(String state, int power, long time) {
//        if (selectCarBean != null) {
//            selectDeviceDetailBean = null;
//
//            DeviceDetailPutBean.ParamsBean paramsBean = new DeviceDetailPutBean.ParamsBean();
//            paramsBean.setSimei(selectCarBean.getSimei());
//            paramsBean.setGet_user(true);
//
//            DeviceDetailPutBean bean = new DeviceDetailPutBean();
//            bean.setFunc(ModuleValueService.Fuc_For_Device_Detail);
//            bean.setModule(ModuleValueService.Module_For_Device_Detail);
//            bean.setParams(paramsBean);
//
//            getPresenter().getDeviceDetailInfo(bean, state, power, time);
//        }
//    }

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

    /**
     * 定位
     */
    public void getLocation(boolean isFirst) {
        String ll = GoogleLocationUtils.getLocation(getActivity());
        if (!TextUtils.isEmpty(ll)) {
            String[] location = ll.split(",");
            mLatitudePhone = Double.parseDouble(location[0]);
            mLongitudePhone = Double.parseDouble(location[1]);
            // 我的位置
            LatLng myLocation = getLatLngChange(mLatitudePhone, mLongitudePhone);
            isLocationChina = GpsUtils.isChinaLocation(mLatitudePhone, mLongitudePhone);
            if (myLocation != null) {
                addMyLocalMaker(myLocation, isFirst);
            }
        }
    }


    /**
     * 将我的位置的坐标添加进去
     *
     * @param location 我的位置经纬度
     * @param isFirst  是否是首次定位手机位置
     */
    private void addMyLocalMaker(LatLng location, boolean isFirst) {
        if (location != null && location.latitude != 0 && location.longitude != 0 && mGoogleMap != null) {
            if (myLocationMarker != null) {
                myLocationMarker.setPosition(location);
            } else {
                myLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(location)
                        .icon(bitmapHelper.getBitmapZoomForUserLocation(mZoom)));
            }
            if (isFirst) { //首次定位
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, mZoom));
            }
        }
    }


    @Override
    public void onCameraChange(@NonNull CameraPosition cameraPosition) {
        mZoom = cameraPosition.zoom;
//        centerPoint = cameraPosition.target;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (isShowDeviceInfo) {
            isDeviceCenter = false;
            if (!TextUtils.isEmpty(selectImei) || selectCarBean != null && currentMarker != null) {
                //隐藏下弹框
                hideBehavior();
                if (ConstantValue.isLoginForAccount()) {
                    selectCarBean = null;
                    selectImei = "";
                    clearData();
                }

                if (ivCarPeople.getVisibility() == View.GONE)
                    ivCarPeople.setVisibility(View.VISIBLE);
                if (llLocationNow.getVisibility() == View.VISIBLE)
                    llLocationNow.setVisibility(View.GONE);

                isShowDeviceInfo = false;
                currentMarker = null;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        selectCarBean = null;
        currentMarker = null;
        selectImei = marker.getTitle();
        if (!TextUtils.isEmpty(selectImei)) {
            currentMarker = marker;
            for (DeviceListResultBean.ItemsBean bean : deviceDataBeans) {
                if (selectImei.equals(bean.getImei() + "")) {
                    selectCarBean = bean;
                    break;
                }
            }
            isDeviceCenter = true;
            isShowDeviceInfo = true;
            setDeviceDetailData();
        } else {
            currentMarker = marker;
            isDeviceCenter = false;
            isShowDeviceInfo = false;
        }
        if (infoIvCarPeople.getVisibility() == View.GONE) {
            infoIvCarPeople.setVisibility(View.VISIBLE);
        }
        if (ivCarPeople.getVisibility() == View.VISIBLE)
            ivCarPeople.setVisibility(View.GONE);

        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    /**
     * 清理临时缓存数据
     */
    private void clearData() {
        MyApplication.getMyApp().clearData();
    }

    /**
     * 设置当前点位于地图中心
     */
    private void updateMapCenter() {
        if (mGoogleMap != null) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPoint, mZoom));
        }
    }

    /**
     * 用户从后台切换到前台，判断是否满足条件，满足的话下发定位模式指令
     */
    private void onSubmitCallTheRollMode() {
        if (ConstantValue.isActivityStatus()) {
            mCallTheRollMode = 3;
        }
        MyApplication.getMMKVUtils().put(ConstantValue.ACTIVITY_STATUS, true);
    }

    @Override
    public void onResume() {
        MobclickAgent.onPageStart("AmapLocation");//统计页面("MainScreen"为页面名称，可自定义)
        onSubmitCallTheRollMode();
        if (fragmentPosition == 0 && ivSearch != null) {
            if (!isFirstData) {
                mCountDown = 0;
            }
            handler.sendEmptyMessage(1);
        }
        if (maskHintView == null) {
            maskHintView = MaskHintView.getInstance(getContext());
            ivCarPeople.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (MyApplication.getMMKVUtils().getBoolean(ConstantValue.IS_FIRST_GUIDE, true)) {
                        if (ivCarPeople != null && ivCarPeople.getVisibility() == View.VISIBLE) {
                            maskHintView.setTargetView(ivCheckMap, ivTraffic, ivCarPeople).
                                    showMaskHint(new MaskHintView.ShowMashHintListener() {
                                        @Override
                                        public void showMaskHintNext() {
                                        }
                                    });
                        } else {
                            maskHintView.setTargetView(ivCheckMap, ivTraffic, infoIvCarPeople)
                                    .showMaskHint(new MaskHintView.ShowMashHintListener() {
                                        @Override
                                        public void showMaskHintNext() {
                                        }
                                    });
                        }
                    }
                }
            }, 1000);
        }
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && ivSearch != null) {
            if (!isFirstData || isFromDeviceList) {
                mCountDown = 0;
            }
            if(isFromCheckUser){
                isFromCheckUser = false;

                isShowDeviceInfo = false;
                selectCarBean = null;
                selectImei = "";
                hideBehavior();
            }else {
                handler.sendEmptyMessage(1);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onPause() {
        MobclickAgent.onPageEnd("AmapLocation");
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacksAndMessages(null);
        searchHandler.removeCallbacksAndMessages(null);
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(deviceReceiver);
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(deviceListReceiver);
        super.onDestroyView();
    }

    /**
     * 倒计时刷新数据
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            mCountDown--;
            if (mCountDown <= 0) {
                mCallTheRollMode++;
                mCountDown = mCountDownTime;
                if (!isAllDevice && isFromDeviceList) { //超过200设备的设备列表搜索，跳转过来之后由deviceClickForGroup 发送请求
                    isFromDeviceList = false;
                } else {
                    getDeviceList(false, false, false, false);
                }
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @SuppressLint("WrongConstant")
    @OnClick({R.id.iv_map_check, R.id.iv_panorama, R.id.iv_traffic, R.id.iv_refresh, R.id.iv_car_people, R.id.info_iv_car_people,
            R.id.ll_location_now, R.id.ll_general, R.id.ll_satellite, R.id.ll_amap_map, R.id.ll_baidu_map,
            R.id.ll_google_map, R.id.rl_info_up, R.id.btn_track, R.id.btn_record, R.id.iv_search, R.id.tv_cancel, R.id.iv_add_device_close, R.id.rl_add_device})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.iv_refresh: // 刷新数据
                    if (System.currentTimeMillis() - refresh_time > 2000) {
                        refresh_time = System.currentTimeMillis();
                        getDeviceList(true, true, true, false);
                    } else {
                        ToastUtils.showShort(getString(R.string.txt_refresh_hint));
                    }
                    break;
                case R.id.iv_traffic: // 路况
                    isSwitchMapTraffic = !isSwitchMapTraffic;
                    onSwitchMapTraffic();
                    break;
                case R.id.info_iv_car_people:
                case R.id.iv_car_people: // 用户位置 车辆位置
                    isCar = !isCar;
                    onCheckUserOrDeviceLocation();
                    break;
                case R.id.iv_map_check:
                    drawerLayout.openDrawer(GravityCompat.END);
                    break;
                case R.id.ll_location_now: // C2立即定位功能
                    onLocationC2Now();
                    break;
                case R.id.ll_baidu_map: // 百度地图
                    checkMapType(0);
                    break;
                case R.id.ll_amap_map: // 高德地图
                    checkMapType(1);
                    break;
                case R.id.ll_google_map: // 谷歌地图
                    checkMapType(2);
                    break;
                case R.id.ll_general: // 2D地图
                    isSatelliteMap = false;
                    onCheckSatelliteMap();
                    break;
                case R.id.ll_satellite: // 卫星地图
                    isSatelliteMap = true;
                    onCheckSatelliteMap();
                    break;
                case R.id.rl_info_up: // 显示底部弹出框 or 关闭弹出框
                    onShowBottomMenu();
                    break;
                case R.id.btn_track:
                    isDeviceHistory();
                    break;
                case R.id.btn_record:
                    launchActivity(RecordActivity.newInstance());
                    break;
                case R.id.iv_search: // 搜索设备
                    llKeywordSearch.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_cancel: // 取消搜索
                    llKeywordSearch.setVisibility(View.GONE);
                    hideKeyboard(edtSearchImei);
                    onResetSearchData();
                    break;
                case R.id.iv_add_device_close:
                    rlAddDevicePopup.setVisibility(View.GONE);
                    break;
                case R.id.rl_add_device:
                    rlAddDevicePopup.setVisibility(View.GONE);
                    addDevice();
                    break;
            }
        }
    }

    /**
     * 添加设备
     */
    private void addDevice() {
        Intent intent = new Intent(getActivity(), AddDeviceNewActivity.class);
        startActivity(intent);
    }

    /**
     * 是否显示底部菜单
     */
    private void onShowBottomMenu() {
        float rotation = ivUpDown.getRotation();
        mBehavior.setState(rotation == 0 ? BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED);
    }

    /**
     * 是否开启了路况
     */
    @SuppressLint("WrongConstant")
    private void onSwitchMapTraffic() {
        mGoogleMap.setTrafficEnabled(isSwitchMapTraffic);
        if (isSwitchMapTraffic) {
            ivTraffic.setImageResource(R.drawable.icon_road_condition_select);
        } else {
            ivTraffic.setImageResource(R.drawable.icon_road_condition);
        }
    }


    private void isDeviceHistory() {
        if (selectCarBean != null && selectDeviceDetailBean != null) {
            if (selectDeviceDetailBean.getDetail().getMode_id() == 8 || selectDeviceDetailBean.getDetail().getMode_id() == 9) {
                AlertBean bean = new AlertBean();
                bean.setTitle(getString(R.string.txt_tip));
                bean.setAlert(getString(R.string.txt_quest_track_hint));
                bean.setType(0);
                AlertAppDialog dialog = new AlertAppDialog();
                dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                    @Override
                    public void onConfirm() {
                        launchActivity(TrackGoogleActivity.newInstance());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            } else {
                launchActivity(TrackGoogleActivity.newInstance());
            }
        }
    }


    /**
     * 切换地图类型  0 百度地图  1 高德地图  2 谷歌地图
     */
    private void onCheckMapType() {
        ivAmapSelect.setVisibility(View.GONE);
        ivBaiduSelect.setVisibility(View.GONE);
        ivGoogleSelect.setVisibility(View.GONE);
        tvMapAMap.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
        tvMapBaidu.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
        tvMapGoogle.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
        switch (mapType) {
            case 0:
                ivBaiduSelect.setVisibility(View.VISIBLE);
                tvMapBaidu.setTextColor(ContextCompat.getColor(getContext(), R.color.color_2E7BEC));
                ivMapAMap.setImageResource(R.drawable.icon_map_amap);
                ivMapGoogle.setImageResource(R.drawable.icon_map_google);
                ivMapBaidu.setImageResource(R.drawable.icon_map_baidu_select);
                break;
            case 1:
                ivAmapSelect.setVisibility(View.VISIBLE);
                tvMapAMap.setTextColor(ContextCompat.getColor(getContext(), R.color.color_2E7BEC));
                ivMapAMap.setImageResource(R.drawable.icon_map_amap_select);
                ivMapBaidu.setImageResource(R.drawable.icon_map_baidu);
                ivMapGoogle.setImageResource(R.drawable.icon_map_google);
                break;
            case 2:
                ivGoogleSelect.setVisibility(View.VISIBLE);
                tvMapGoogle.setTextColor(ContextCompat.getColor(getContext(), R.color.color_2E7BEC));
                ivMapGoogle.setImageResource(R.drawable.icon_map_google_select);
                ivMapAMap.setImageResource(R.drawable.icon_map_amap);
                ivMapBaidu.setImageResource(R.drawable.icon_map_baidu);
                break;
        }
    }

    private void onCheckSatelliteMap() {
        drawerLayout.closeDrawer(GravityCompat.END);
        if (isSatelliteMap) {
            mGoogleMap.setMapType(isLocationChina ? GoogleMap.MAP_TYPE_SATELLITE : GoogleMap.MAP_TYPE_HYBRID);
            ivCheckMap.setImageResource(R.drawable.icon_check_map_select);
            ivGeneralMap.setImageResource(R.drawable.icon_general_map);
            ivSatelliteMap.setImageResource(R.drawable.icon_satellite_map_select);
            tvGeneralMap.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
            tvSatelliteMap.setTextColor(ContextCompat.getColor(getContext(), R.color.color_2E7BEC));
        } else {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            ivCheckMap.setImageResource(R.drawable.icon_check_map);
            ivGeneralMap.setImageResource(R.drawable.icon_general_map_select);
            ivSatelliteMap.setImageResource(R.drawable.icon_satellite_map);
            tvGeneralMap.setTextColor(ContextCompat.getColor(getContext(), R.color.color_2E7BEC));
            tvSatelliteMap.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
        }
    }

    /**
     * 切换地图
     *
     * @param type 0:百度地图，1：高德地图，2：谷歌地图
     */
    private void checkMapType(int type) {
        if (type == 2) {
            if (Utils.isNotInstallService(getActivity())) {
                ToastUtils.showShort(getString(R.string.txt_not_google_server));
                return;
            }
            drawerLayout.closeDrawer(GravityCompat.END);
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.END);
        MyApplication.getMMKVUtils().put(ConstantValue.MAP_TYPE_NEW, type);
        ActivityUtils.finishAllActivities();
        MyApplication.getMyApp().setBeforeAccount(false);
        MyApplication.getMyApp().setMergeAccount(false);
        launchActivity(MainActivity.newInstance());
    }


    /**
     * C2设备类型下发定位模式指令
     */
    private void onLocationC2Now() {
        if (selectCarBean == null) {
            ToastUtils.showShort(getString(R.string.txt_device_select_hint));
            return;
        }

        if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_Down)) {
            ToastUtils.showShort(getString(R.string.txt_location_off_line_hint));
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setType(0);
        bean.setAlert(getString(R.string.txt_location_device_now));
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitLocationC2Now();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 发起C2设备秒速定位
     */
    private void submitLocationC2Now() {
        if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_On)) {
            submitOneKeyFunction(ResultDataUtils.Function_Query_Location);
        } else {
            showLoading();
            llLocationNow.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideLoading();
                    ToastUtils.showShort(getString(R.string.txt_location_device_success));
                    isShowDeviceInfo = true;
                    setDeviceDetailData();
                }
            }, 1000);
        }
    }

    /**
     * 一键功能提交
     *
     * @param key 功能值
     */
    private void submitOneKeyFunction(String key) {
        OnKeyFunctionPutBean.ParamsBean paramsBean = new OnKeyFunctionPutBean.ParamsBean();
        paramsBean.setType(key);
        paramsBean.setSimei(selectCarBean.getSimei());

        OnKeyFunctionPutBean bean = new OnKeyFunctionPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_OnKey_Function);
        bean.setModule(ModuleValueService.Module_For_OnKey_Function);

        showProgressDialog();

        getPresenter().submitOneKeyFunction(bean);
    }

    /**
     * 切换用户和设备位置
     *
     * @param
     */
    private void onCheckUserOrDeviceLocation() {
        if (!isCar) {
            //显示手机位置
            ivCarPeople.setImageResource(R.mipmap.icon_switch_people);
            infoIvCarPeople.setImageResource(R.mipmap.icon_switch_people);
            centerPoint = getLatLngChange(mLatitudePhone, mLongitudePhone);
            updateMapCenter();
            ToastUtils.showShort(getString(R.string.txt_check_location_man));
        } else {
            ivCarPeople.setImageResource(R.mipmap.icon_switch_car);
            infoIvCarPeople.setImageResource(R.mipmap.icon_switch_car);
            if (selectCarBean == null) {
                if (deviceSwitchBeans.size() > 0) {
                    onLookAllDevice();
                }
            } else {
                centerPoint = getLatLngChange(selectLatForDevice,selectLonForDevice);
                updateMapCenter();
                ToastUtils.showShort(getString(R.string.txt_check_location_car));
            }
        }
    }

    /**
     * 查看所有设备
     */
    private void onLookAllDevice() {
        if (!isAllDevice || deviceDataBeans.size() == 1) { // 不是全部设备时 或者列表数量为一台时
            ToastUtils.showShort(getString(R.string.txt_check_location_car));
            for (int i = 0; i < deviceSwitchBeans.size(); i++) {
                DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(deviceSwitchBeans.get(i).getLast_pos());
                centerPoint = getLatLngChange((double) infoBean.getLat() / 1000000,(double) infoBean.getLon() / 1000000);
            }
            updateMapCenter();
        } else {
            mBuilder = null;
            mBuilder = new LatLngBounds.Builder();
            for (int i = 0; i < deviceSwitchBeans.size(); i++) {
                DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(deviceSwitchBeans.get(i).getLast_pos());
                mBuilder.include(getLatLngChange((double) infoBean.getLat() / 1000000,(double) infoBean.getLon() / 1000000));
            }
            ivRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mGoogleMap != null) {
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mBuilder.build(), 200));
                        mZoom = mGoogleMap.getCameraPosition().zoom;
                    }
                    ToastUtils.showShort(getString(R.string.txt_check_location_all_car));
                }
            }, 500);
        }
    }


    //卫星图需要做偏移
    private LatLng getLatLngChange(double lat,double lon) {
        LatLng latLng = new LatLng(lat, lon);
        if (isSatelliteMap) {
//            if (GpsUtils.isChinaLocation(lat, lon)) {
            // 设备经纬度，切换为卫星地图之后，坐标系要转为WGS84坐标系
            double[] deviceWGS84 = GpsUtils.toWGS84Point(lat, lon);
            latLng = new LatLng(deviceWGS84[0], deviceWGS84[1]);
//            }else
        } else {
            latLng = GpsUtils.googleGPSConverter(lat, lon);
        }
        return latLng;
    }


    /**
     * 重置一些参数
     */
    private void onResetValue() {
        isFirstData = false;
        selectImei = "";
        selectCarBean = null;
        clearData();
        if (mGoogleMap != null) {
            mGoogleMap.clear();
            deviceMap.clear();
        }
    }

    @Override
    public void getDeviceListSuccess(DeviceListResultBean deviceListResultBean, boolean isInitiativeRefresh, boolean clearMap) {
        mCountDown = mCountDownTime;
        if (isFirstData) {
            isAllDevice = deviceListResultBean.isIs_all();
        }
        isCallAllDev = false;
        if (deviceListResultBean.getItems() == null) {
            if (isFirstData) {
                if (ConstantValue.isLoginForAccount()) {
                    rlAddDevicePopup.setVisibility(View.VISIBLE);
                    ToastUtils.showShort(getString(R.string.txt_account_no_device));
                } else {
                    ToastUtils.showShort(getString(R.string.txt_device_location_no_data));
                }
            }
            onResetValue();
            return;
        }
        if (isFirstData) {
            if (deviceListResultBean.getItems().size() == 0) {
                rlAddDevicePopup.setVisibility(View.VISIBLE);
                ToastUtils.showShort(getString(R.string.txt_account_no_device));
                onResetValue();
                return;
            }
        }
        if (clearMap) {
            selectCarBean = null;
            clearData();
            if (mGoogleMap != null) {
                mGoogleMap.clear();
                deviceMap.clear();
            }
        }

        deviceSwitchBeans.clear();
        deviceDataBeans.clear();
        deviceDataBeans.addAll(deviceListResultBean.getItems());
        if (selectCarBean != null) {
            selectImei = selectCarBean.getImei() + "";
        }
        mBuilder = null;
        mBuilder = new LatLngBounds.Builder();

        isAccountHasDevice = false;
        for (int i = 0; i < deviceDataBeans.size(); i++) {
            DeviceListResultBean.ItemsBean bean = deviceDataBeans.get(i);
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(bean.getLast_pos());
            // 设备的定位信息
            if (TextUtils.isEmpty(bean.getLast_pos()) || infoBean.getLat() == 0 || infoBean.getLon() == 0) {
                if (ConstantValue.isLoginForAccount()) {
                    if (i == deviceDataBeans.size() - 1) {
                        if (isFirstData && !isAccountHasDevice){
                            ToastUtils.showShort(getString(R.string.txt_account_location_no_data));
                        }
                    }
                } else {
                    if (isFirstData && deviceDataBeans.size() == 1) {
                        ToastUtils.showShort(getString(R.string.txt_device_location_no_data));
                    }
                }
                continue;
            }
            isAccountHasDevice = true;
            deviceSwitchBeans.add(bean);

            double latDev = (double) infoBean.getLat() / 1000000;
            double lonDev = (double) infoBean.getLon() / 1000000;
            mLatLngLocation = getLatLngChange(latDev,lonDev);
            if (!TextUtils.isEmpty(selectImei) && selectImei.equals(bean.getImei() + "")) {
                selectCarBean = bean;
            }
            if (deviceMap.get(bean.getImei() + "") != null) {
                Objects.requireNonNull(deviceMap.get(bean.getImei() + "")).setPosition(mLatLngLocation);
                Objects.requireNonNull(deviceMap.get(bean.getImei() + "")).setIcon(getMarkerIcon(bean, infoBean.getDirection()));
            } else {
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(mLatLngLocation);
                markerOption.title(bean.getImei() + "");
                markerOption.icon(getMarkerIcon(bean, infoBean.getDirection()));
                markerOption.anchor(0.5f, 1);
                markerOption.draggable(false);
                markerOption.visible(true);

                Marker marker = mGoogleMap.addMarker(markerOption);
                deviceMap.put(bean.getImei() + "", marker);
            }
            mBuilder.include(mLatLngLocation);
        }
        getLocation(false); //重新获取手机位置
        if (isFirstData || isOperationalRestrictions) {
            if (deviceDataBeans.size() == 1) {
                DeviceLocationInfoBean location = DeviceUtils.parseDeviceData(deviceDataBeans.get(0).getLast_pos());
                if (location.getLat() != 0 && location.getLon() != 0) {
                    if (selectCarBean == null) {
                        selectCarBean = deviceDataBeans.get(0);
                    }
                }
            }
        }

        if (selectCarBean != null) {
            selectImei = selectCarBean.getImei() + "";
            isShowDeviceInfo = true;
            setDeviceDetailData();
        }
        if (isInitiativeRefresh) {
            ivRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (deviceDataBeans.size() > 1) {
                        if (selectCarBean == null) {
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mBuilder.build(), 200));
                            mZoom = mGoogleMap.getCameraPosition().zoom;
                        }
                    }
                }
            }, 500);
        }

        isFirstData = false;
        isOperationalRestrictions = false;

    }

    @Override
    public void getDeviceListError(int errcode) {
        isFirstData = false;
        if (errcode == Api.Operational_Restrictions) {
            isOperationalRestrictions = true;
            mZoom = 16;
        } else if (errcode == Api.Device_Freeze || errcode == Api.Data_Change) {
            getDeviceDetailError();
        }
    }

    @Override
    public void getDeviceDetailError() {
        isFirstData = true;
        mCountDown = mCountDownTime;
        mSimeiLists.clear();
        getDeviceList(true, true, true, true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getDeviceDetailInfoSuccess(DeviceDetailResultBean deviceDetailResultBean) {
        selectDeviceDetailBean = deviceDetailResultBean;
        increment = deviceDetailResultBean.getIncrement();
        // 功能支持列表
        modeFunction = "";
        getDeviceConfig(String.valueOf(selectCarBean.getSimei()));
        // 通讯时间 判断是否是C2设备
        if (!TextUtils.isEmpty(selectDeviceDetailBean.getVersion()) && selectDeviceDetailBean.getVersion().contains(FunctionType.C2)) {
            if (selectDeviceDetailBean.getState().equals(ResultDataUtils.Device_State_Line_Sleep)) {
                String mComTime = DateUtils.getTodayDateTime();
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    Date recordEndTime = df.parse(DateUtils.getTodayDateTime());
                    mComTime = df.format(new Date(recordEndTime.getTime() + 1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tvCommunication.setText(mComTime);
            } else {
                tvCommunication.setText(DateUtils.timeConversionDate_two(String.valueOf(deviceDetailResultBean.getLast_com_time())));
            }
        } else {
            tvCommunication.setText(DateUtils.timeConversionDate_two(String.valueOf(deviceDetailResultBean.getLast_com_time())));
        }
        //日里程
        if (deviceDetailResultBean.getDay_distance() != 0) {
            tvMileage.setText( Utils.formatValue(deviceDetailResultBean.getDay_distance() / 1000) + "km");
            tvMileage.setVisibility(View.VISIBLE);
        } else {
            tvMileage.setVisibility(View.GONE);
        }
        //电压
        if (!TextUtils.isEmpty(deviceDetailResultBean.getExternal_voltage())) {
            tvVoltage.setText(deviceDetailResultBean.getExternal_voltage()); //电压
            tvVoltage.setVisibility(View.VISIBLE);
        } else {
            tvVoltage.setVisibility(View.GONE);
        }

        // 判断是否在充电
        if (ResultDataUtils.Acc_Power_Charge.equals(deviceDetailResultBean.getAcc_power())) {
            tvPowerState.setVisibility(View.VISIBLE);
            if (selectDeviceDetailBean.getPower() == 100) {
                tvPowerState.setText(getString(R.string.txt_acc_hint_100));
                tvPowerState.setTextColor(getResources().getColor(R.color.color_2E7BEC));
            } else {
                tvPowerState.setText(getString(R.string.txt_acc_hint));
                tvPowerState.setTextColor(getResources().getColor(R.color.color_FF4044));
            }
        } else {
            tvPowerState.setVisibility(View.GONE);
        }
        switch (selectDeviceDetailBean.getState()) {
            case ResultDataUtils.Device_State_Line_Down:
                if (tvStaticTimeZH.getVisibility() == View.VISIBLE)
                    tvStaticTimeZH.setText(getString(R.string.txt_off_line_time));
                break;
            case ResultDataUtils.Device_State_Line_On:
                if (tvStaticTimeZH.getVisibility() == View.VISIBLE)
                    tvStaticTimeZH.setText(getString(R.string.txt_on_line_time));
                break;
            case ResultDataUtils.Device_State_Line_Sleep:
                if (tvStaticTimeZH.getVisibility() == View.VISIBLE)
                    tvStaticTimeZH.setText(getString(R.string.txt_static_time_total));
                break;
        }
        //离线时间、静止时间
        tvStaticTime.setText(ResultDataUtils.convertTimemills(deviceDetailResultBean.getState_time() * 1000, getContext()));
        if (deviceDetailResultBean.getBd_satellite() > 0) {
            ivGpsNumberAll.setVisibility(View.GONE);
            tvGpsNumberAll.setVisibility(View.GONE);

            ivBDNumber.setVisibility(View.VISIBLE);
            tvBDNumber.setVisibility(View.VISIBLE);
            tvBDNumber.setText(deviceDetailResultBean.getBd_satellite() + "");

            if (deviceDetailResultBean.getGps_satellite() > 0) {
                ivGpsNumber.setVisibility(View.VISIBLE);
                tvGpsNumber.setVisibility(View.VISIBLE);
                tvGpsNumber.setText(deviceDetailResultBean.getGps_satellite() + "");
            } else {
                ivGpsNumber.setVisibility(View.GONE);
                tvGpsNumber.setVisibility(View.GONE);
            }
        } else {
            ivBDNumber.setVisibility(View.GONE);
            tvBDNumber.setVisibility(View.GONE);
            ivGpsNumber.setVisibility(View.GONE);
            tvGpsNumber.setVisibility(View.GONE);
            ivGpsNumberAll.setVisibility(View.VISIBLE);
            tvGpsNumberAll.setVisibility(View.VISIBLE);
            tvGpsNumberAll.setText(deviceDetailResultBean.getSigna_val() + ""); //卫星总个数
        }
        tvGsmNumber.setText(deviceDetailResultBean.getSignal_rate() + ""); //信号值
        ivGsm.setImageResource(ResultDataUtils.getSignal(deviceDetailResultBean.getSignal_rate()));//信号图标
        ResultDataUtils.setAccState(getContext(), deviceDetailResultBean.getAcc_state(), tvAcc);//acc状态
        //定位模式
        if (ResultDataUtils.Mode_Stand_By_Real_Time.equals(deviceDetailResultBean.getMode_fun())) {
            tvMode.setText(getString(R.string.txt_location_mode_error));
        } else {
            tvMode.setText(deviceDetailResultBean.getDetail().getMode_name());
        }
        if (deviceDetailResultBean.getRecord() == 1) {
            btnRecord.setVisibility(View.VISIBLE);
        } else {
            btnRecord.setVisibility(View.GONE);
        }
        if (deviceDetailResultBean.getHistory() == 1) {
            btnTrack.setVisibility(View.VISIBLE);
        } else {
            btnTrack.setVisibility(View.GONE);
        }
        // 设备定位模式
        MyApplication.getMyApp().setDeviceMode(deviceDetailResultBean.getDetail().getMode());
        MyApplication.getMyApp().setSignal_rate(deviceDetailResultBean.getSignal_rate());
        MyApplication.getMyApp().setSigna_val(deviceDetailResultBean.getSigna_val());
        MyApplication.getMyApp().setmIccid(deviceDetailResultBean.getIccid());
        MyApplication.getMyApp().setProtocol(deviceDetailResultBean.getProtocol());
        MyApplication.getMyApp().setIncrement(deviceDetailResultBean.getIncrement());
    }

    @Override
    public void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh) {
        if (isRefresh) {
            groupDataBeans.clear();
        }
        if (deviceGroupResultBean.getGarages() != null && deviceGroupResultBean.getGarages().size() > 0) {
            mLastGroupId = deviceGroupResultBean.getGarages().get(deviceGroupResultBean.getGarages().size() - 1).getSid();
            groupDataBeans.addAll(deviceGroupResultBean.getGarages());
            if (groupDataBeans.size() > 0) {
                groupDataBeans.get(0).setSelect(true);
            }
            mergeAccountDialog.dismiss();
            onMergeGroupSelect();
        }
    }

    @Override
    public void finishRefresh() {
    }

    @Override
    public void endLoadMore(int type) {
        if (type == 2) mSearchAdapter.loadMoreComplete();
    }

    @Override
    public void setNoMore(int type) {
        if (type == 2) mSearchAdapter.loadMoreEnd();
    }

    @Override
    public void endLoadFail(int type) {
        if (type == 2) mSearchAdapter.loadMoreFail();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getDeviceListForGroupSuccess(DeviceListForManagerResultBean deviceListForManagerResultBean, boolean isRefresh) {
    }

    @Override
    public void submitOneKeyFunctionSuccess(BaseBean baseBean) {
        ToastUtils.showShort(getString(R.string.txt_location_device_success));
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
    public void getPhoneCodeSuccess(PhoneCodeResultBean phoneCodeResultBean) {
        ToastUtils.showShort(getString(R.string.errcode_success));
    }

    @Override
    public void submitMergeAccountSuccess(MergeAccountResultBean mergeAccountResultBean) {
        if (mergeAccountResultBean.getErrcode() == Api.Mobile_Code_Error) {
            onShowMergeAccount();
        } else if (mergeAccountResultBean.isSuccess()) {
            if (mergeAccountResultBean.getFail_item() != null && mergeAccountResultBean.getFail_item().size() > 0) {
                MergeAccountFailDialog dialog = new MergeAccountFailDialog();
                dialog.show(getFragmentManager(), mergeAccountResultBean.getFail_item());
            } else {
                ToastUtils.showShort(getString(R.string.txt_merge_success));
                getDeviceList(true, true, true, false);
            }
        }
    }

    @Override
    public void getDeviceConfigSuccess(DeviceConfigResultBean deviceConfigResultBean) {
        modeFunction = deviceConfigResultBean.getMode_fun();
        protocol = deviceConfigResultBean.getProtocol();
        functionBeans.clear();
        functionBeans.add(new DataCenterBean(0, getString(R.string.menu_0), R.drawable.icon_menu_0));
        functionBeans.add(new DataCenterBean(1, getString(R.string.menu_1), R.drawable.icon_menu_1));

        if (!modeFunction.equals(ResultDataUtils.Mode_Stand_By_Invalid)) {
            functionBeans.add(new DataCenterBean(6, getString(R.string.menu_6), R.drawable.icon_menu_6));
        }
        if (deviceConfigResultBean.getTrace() == 1) { //实时追踪
            functionBeans.add(new DataCenterBean(5, getString(R.string.txt_function_realtime_tracking), R.drawable.icon_menu_8));
        }

        functionBeans.add(new DataCenterBean(7, getString(R.string.menu_7), R.drawable.icon_menu_7));

        gridViewMenu.setNumColumns(functionBeans.size());
        if (mAdapter == null) {
            mAdapter = new FunctionAdapter(getContext(), R.layout.item_menu_home, functionBeans);
            gridViewMenu.setAdapter(mAdapter);
            gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                        onMenuClick(functionBeans.get(position).getId(), modeFunction);
                    }
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }

        //立即定位功能:Cmd_type 中是否包含（e_cmd_query_location 查询位置信息）来判断是否展示，有则展示，反之，不展示该功能
        if(deviceConfigResultBean.getCmd_type() != null && deviceConfigResultBean.getCmd_type().size() >0) {
            //账号登录选中设备才展示，设备号登录选不选中都展示
            if(deviceConfigResultBean.getCmd_type().contains("e_cmd_query_location")){
                if(ConstantValue.isAccountLogin()){
                    if(selectCarBean != null){
                        llLocationNow.setVisibility(View.VISIBLE);
                    }
                }else {
                    llLocationNow.setVisibility(View.VISIBLE);
                }
            }else {
                llLocationNow.setVisibility(View.GONE);
            }
        }
    }

    private void onMenuClick(int id, String function) {
        if (selectCarBean == null) return;
        switch (id) {
            case 0:
                launchActivity(NavigationGoogleActivity.newInstance());
                break;
            case 1:
                launchActivity(SimDetailActivity.newInstance());
                break;
            case 2: //增值服务
                launchActivity(PayWebViewActivity.newInstance(2,getString(R.string.menu_8), Api.Pay_Increment_Recharge + ConstantValue.getPayIncrementValue()));
                break;
            case 3:
                isDeviceHistory();
                break;
            case 4:
                if (selectDeviceDetailBean != null) {
                    DeviceDetailIntentBean bean = new DeviceDetailIntentBean();
                    bean.setDevice_number(selectCarBean.getCar_number());
                    bean.setImei(selectCarBean.getImei());
                    bean.setSimei(selectCarBean.getSimei());
                    bean.setCar_type(selectCarBean.getVersion());
                    bean.setState(selectCarBean.getState());
                    bean.setLast_pos(selectCarBean.getLast_pos());
                    bean.setIccid(selectDeviceDetailBean.getIccid());
                    bean.setStart_dev_time(selectDeviceDetailBean.getDetail().getStart_dev_time());
                    bean.setEnd_dev_time(selectDeviceDetailBean.getDetail().getEnd_dev_time());
                    bean.setBck_phone(selectDeviceDetailBean.getDetail().getBck_phone());
                    bean.setUser_name(selectDeviceDetailBean.getDetail().getUser_name());
                    bean.setCenter_phone(selectDeviceDetailBean.getCenter_phone());
                    bean.setBind_phone(selectDeviceDetailBean.getBind_phone());
                    launchActivity(DeviceDetailActivity.newInstance(bean));
                }
                break;
            case 5:
                isDeviceRealTimeTrack();
                break;
            case 6:
                if (!TextUtils.isEmpty(function)){
                    if (ResultDataUtils.Mode_Stand_By_Location.equals(function)){
                        if (protocol == 5) {
                            launchActivity(LocationModeYiWenActivity.newInstance());
                        } else {
                            launchActivity(LocationModeActivity.newInstance());
                        }
                    }else{
                        launchActivity(RealTimeModeActivity.newInstance());
                    }
                }
                break;
            case 7:
                launchActivity(SettingMoreActivity.newInstance(selectDeviceDetailBean,selectCarBean));
                break;
        }

    }

    /**
     * 是否允许查看实时追踪
     *
     * @return
     */
    private void isDeviceRealTimeTrack() {
        if (MyApplication.getMyApp().getVersion().toUpperCase().contains(FunctionType.C2)) {
            if (MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Down)) {
                ToastUtils.showShort(getString(R.string.txt_real_time_trace_off_line_hint));
                return;
            }
            if (MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Sleep)) {
                ToastUtils.showShort(getString(R.string.txt_real_time_trace_sleep_hint));
                return;
            }
        }

        if (MyApplication.getMyApp().getVersion().toUpperCase().contains(FunctionType.C2)) {
            AlertBean bean = new AlertBean();
            bean.setTitle(getString(R.string.txt_tip));
            bean.setAlert(getString(R.string.txt_real_time_trace_hint));
            bean.setType(0);
            AlertAppDialog dialog = new AlertAppDialog();
            dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                @Override
                public void onConfirm() {
                    launchActivity(RealTimeTrackGoogleActivity.newInstance());
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            launchActivity(RealTimeTrackGoogleActivity.newInstance());
        }
    }

    @Override
    public void getDeviceHasLocationSuccess(DeviceDetailResultBean deviceDetailResultBean, long imei, String simei) {
        if (deviceDetailResultBean.getState_begin_time() != 0) {
            ivSearch.postDelayed(new Runnable() {
                @Override
                public void run() {
                    llKeywordSearch.setVisibility(View.GONE);
                    onResetSearchData();
                    isFromSearch = true;
                    deviceClickForGroup(imei, simei);
                }
            }, 500);
        } else {
            ToastUtils.showShort(getString(R.string.txt_device_location_no_data));
        }
    }

    /**
     * 绘制设备图标等信息
     *
     * @param theDevice
     * @return
     */
    private BitmapDescriptor getMarkerIcon(DeviceListResultBean.ItemsBean theDevice, float direction) {
        View view = View.inflate(getContext(), R.layout.layout_marker_item, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        ImageView ivCar = view.findViewById(R.id.iv_car);
        if (TextUtils.isEmpty(theDevice.getCar_number())) {
            tvName.setText(String.valueOf(theDevice.getImei()));
        } else {
            tvName.setText(theDevice.getCar_number());
        }
        getDeviceState(theDevice, ivCar, tvName, direction);
        return convertViewToBitmap(view);
    }

    private static BitmapDescriptor convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return BitmapDescriptorFactory.fromBitmap(view.getDrawingCache());
    }

    private void getDeviceState(DeviceListResultBean.ItemsBean theDevice, ImageView ivView, TextView tvView, float direction) {
        if (theDevice.getState().equals(ResultDataUtils.Device_State_Line_On)) {
            tvView.setTextColor(getResources().getColor(R.color.color_73CA6C));
            tvView.setBackgroundResource(R.mipmap.icon_online_bg);
            switch (theDevice.getCar_image()) {
                case 0:
                    ivView.setImageResource(R.mipmap.icon_car_online);
                    break;
                case 1:
                    ivView.setImageResource(R.mipmap.icon_car_online_1);
                    break;
                case 2:
                    ivView.setImageResource(R.mipmap.icon_car_online_2);
                    break;
                case 3:
                    ivView.setImageResource(R.mipmap.icon_car_online_3);
                    break;
                case 4:
                    ivView.setImageResource(R.mipmap.icon_car_online_4);
                    break;
                case 5:
                    ivView.setImageResource(R.mipmap.icon_car_online_5);
                    break;
                case 6:
                    ivView.setImageResource(R.mipmap.icon_car_online_6);
                    break;
                case 7:
                    ivView.setImageResource(R.mipmap.icon_car_online_7);
                    break;
                case 8:
                    ivView.setImageResource(R.mipmap.icon_car_online_8);
                    break;
                case 9:
                    ivView.setImageResource(R.mipmap.icon_car_online_9);
                    break;
            }
        } else if (theDevice.getState().equals(ResultDataUtils.Device_State_Line_Sleep)) {
            tvView.setBackgroundResource(R.mipmap.icon_static_bg);
            tvView.setTextColor(getResources().getColor(R.color.color_4592EB));
            switch (theDevice.getCar_image()) {
                case 0:
                    ivView.setImageResource(R.mipmap.icon_car_sleep);
                    break;
                case 1:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_1);
                    break;
                case 2:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_2);
                    break;
                case 3:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_3);
                    break;
                case 4:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_4);
                    break;
                case 5:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_5);
                    break;
                case 6:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_6);
                    break;
                case 7:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_7);
                    break;
                case 8:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_8);
                    break;
                case 9:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_9);
                    break;
            }
        } else {
            tvView.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            tvView.setBackgroundResource(R.mipmap.icon_offline_bg);
            switch (theDevice.getCar_image()) {
                case 0:
                    ivView.setImageResource(R.mipmap.icon_car_offline);
                    break;
                case 1:
                    ivView.setImageResource(R.mipmap.icon_car_offline_1);
                    break;
                case 2:
                    ivView.setImageResource(R.mipmap.icon_car_offline_2);
                    break;
                case 3:
                    ivView.setImageResource(R.mipmap.icon_car_offline_3);
                    break;
                case 4:
                    ivView.setImageResource(R.mipmap.icon_car_offline_4);
                    break;
                case 5:
                    ivView.setImageResource(R.mipmap.icon_car_offline_5);
                    break;
                case 6:
                    ivView.setImageResource(R.mipmap.icon_car_offline_6);
                    break;
                case 7:
                    ivView.setImageResource(R.mipmap.icon_car_offline_7);
                    break;
                case 8:
                    ivView.setImageResource(R.mipmap.icon_car_offline_8);
                    break;
                case 9:
                    ivView.setImageResource(R.mipmap.icon_car_offline_9);
                    break;
            }
        }
        if (theDevice.getCar_image() == 0 || theDevice.getCar_image() == 1 || theDevice.getCar_image() == 2) {
            ivView.setRotation(direction);//旋转
        }
    }

    /**
     * 更新悬浮框数据
     */
    @SuppressLint({"SetTextI18n", "DefaultLocale", "SimpleDateFormat"})
    private void setDeviceDetailData() {
        if (Utils.isCN()) {
            ivStaticTime.setVisibility(View.GONE);
            ivCommunication.setVisibility(View.GONE);
            ivLocationTime.setVisibility(View.GONE);
            ivAddress.setVisibility(View.GONE);
            tvStaticTimeZH.setVisibility(View.VISIBLE);
            tvCommunicationTimeZH.setVisibility(View.VISIBLE);
            tvLocationTimeZH.setVisibility(View.VISIBLE);
            tvAddressZH.setVisibility(View.VISIBLE);
        } else {
            ivStaticTime.setVisibility(View.VISIBLE);
            ivCommunication.setVisibility(View.VISIBLE);
            ivLocationTime.setVisibility(View.VISIBLE);
            ivAddress.setVisibility(View.VISIBLE);
            tvStaticTimeZH.setVisibility(View.GONE);
            tvCommunicationTimeZH.setVisibility(View.GONE);
            tvLocationTimeZH.setVisibility(View.GONE);
            tvAddressZH.setVisibility(View.GONE);
        }
        if (selectCarBean != null) {
            MyApplication.getMyApp().setSimei(selectCarBean.getSimei());
            MyApplication.getMyApp().setLocInfo(selectCarBean.getLast_pos());
            MyApplication.getMyApp().setDeviceState(selectCarBean.getState());
            MyApplication.getMyApp().setImei(selectCarBean.getImei());
            MyApplication.getMyApp().setPower(selectCarBean.getPower());
            MyApplication.getMyApp().setVersion(selectCarBean.getVersion());
            MyApplication.getMyApp().setCarImageId(selectCarBean.getCar_image());
            MyApplication.getMyApp().setCarName(selectCarBean.getCar_number());
            DeviceLocationInfoBean bean = DeviceUtils.parseDeviceData(selectCarBean.getLast_pos());
            MyApplication.getMyApp().setLatAndLon(bean.getLat() + "," + bean.getLon());
            deviceVersion = selectCarBean.getVersion();

            selectLatForDevice = (double) bean.getLat() / 1000000;
            selectLonForDevice = (double) bean.getLon() / 1000000;
            centerPoint = getLatLngChange(selectLatForDevice,selectLonForDevice);
            // 选中设备置于地图中间位置
            if (ConstantValue.isLoginForAccount()) {
                updateMapCenter();
            } else {
                if (isDeviceCenter) {
                    updateMapCenter();
                }
            }

            if (TextUtils.isEmpty(selectCarBean.getCar_number())) {
                tvDeviceName.setText(String.valueOf(selectCarBean.getImei()));
            } else {
                tvDeviceName.setText(selectCarBean.getCar_number());
            }
            //状态
            String strState = "";
            switch (selectCarBean.getState()) {
                case ResultDataUtils.Device_State_Line_Down:
                    strState = getString(R.string.txt_state_line_down);
                    tvState.setTextColor(getResources().getColor(R.color.color_999999));
                    break;
                case ResultDataUtils.Device_State_Line_On:
                    strState = getString(R.string.txt_state_line_on);
                    tvState.setTextColor(getResources().getColor(R.color.color_1FC62E));
                    break;
                case ResultDataUtils.Device_State_Line_Sleep:
                    strState = getString(R.string.txt_state_line_static);
                    tvState.setTextColor(getResources().getColor(R.color.color_4592EB));
                    break;
            }
            tvState.setText(strState);
            // 定位时间  判断是否是C2设备
            if (FunctionType.C2.equals(selectCarBean.getVersion())) {
                if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_Sleep)) {
                    tvLocationTime.setText(DateUtils.getTodayDateTime());
                } else {
                    tvLocationTime.setText(DateUtils.timeConversionDate_two(String.valueOf(bean.getTime())));
                }
            } else {
                tvLocationTime.setText(DateUtils.timeConversionDate_two(String.valueOf(bean.getTime())));
            }
            //速度
            tvSpeed.setText(getString(R.string.txt_speed) + ((double) bean.getSpeed() / 10) + "km/h");
            //定位类型
            ivLocationType.setImageResource(ResultDataUtils.getLocationType(bean.getType()));
            //电量
            ResultDataUtils.setElectricImageData(selectCarBean.getPower(), ivBattery);
            tvBattery.setText(selectCarBean.getPower() + "%");

            // 获取设备详细信息
            getDeviceDetailInfo();

            // 判断当前点的经纬度是否在中国范围内的经纬度多边形内，判断国内和海外
            com.baidu.mapapi.model.LatLng latLng = new com.baidu.mapapi.model.LatLng(selectLatForDevice, selectLonForDevice);
            boolean isChina = SpatialRelationUtil.isPolygonContainsPoint(PolygonalArea.getBaiduGListPoint(), latLng);
            if (isChina) {
                //显示地址
                LocationAddressParsed.getLocationParsedInstance().Parsed(selectLatForDevice, selectLonForDevice)
                        .setAddressListener(str -> {
                            if (getContext() != null) {
                                if (!TextUtils.isEmpty(str)) {
                                    tvAddress.setText(str);
                                } else {
                                    tvAddress.setText(selectLatForDevice + "," + selectLonForDevice);
                                }
                            }
                        });
            } else {
                String address = LocationGoogleAddressParsed.getLocationParsedInstance().Parsed(selectLatForDevice, selectLonForDevice);
                if (getContext() != null) {
                    if (!TextUtils.isEmpty(address)) {
                        tvAddress.setText(address);
                    } else {
                        tvAddress.setText(selectLatForDevice + "," + selectLonForDevice);
                    }
                }
            }

            if (ConstantValue.isLoginForAccount()) {
                changeBottomInfo();
            } else {
                if (isDeviceCenter) {
                    changeBottomInfo();
                } else {
                    //隐藏下弹框
                    hideBehavior();
                }
            }
        }
    }

    private void changeBottomInfo() {
        if (deviceMap.get(selectCarBean.getImei() + "") != null) {
            if (isShowDeviceInfo) {
                if (ivCarPeople.getVisibility() == View.VISIBLE) {
                    ivCarPeople.setVisibility(View.GONE);
                }
                setBottomSheetBehaviorExpanded();
            } else {
                hideBehavior();
            }
        }
    }

    //隐藏下弹框
    private void hideBehavior(){
        try {
            mBehavior.setHideable(true);
            mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            if(ConstantValue.isAccountLogin()) {
                llLocationNow.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBottomSheetBehaviorExpanded() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            if (llDeviceInfo != null) {
                llDeviceInfo.post(new Runnable() {
                    @Override
                    public void run() {
                        mBehavior.setPeekHeight(llDeviceInfo.getHeight());
                    }
                });
            }
            mBehavior.setHideable(true);
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }


    /**
     * 发送验证码
     */
    private void onSendPhoneCode() {
        PhoneCodePutBean.ParamsBean paramsBean = new PhoneCodePutBean.ParamsBean();
        paramsBean.setCode(Api.Mob_Module_Code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setPhone(ConstantValue.getAccount());
        paramsBean.setZone(mPhoneZone);
        if (Utils.localeLanguage().length() != 0)
            paramsBean.setLang(Utils.localeLanguage());

        PhoneCodePutBean bean = new PhoneCodePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Phone_Code);
        bean.setModule(ModuleValueService.Module_For_Phone_Code);

        getPresenter().getPhoneCode(bean);
    }

    /**
     * 提交合并账号
     *
     * @param gid
     */
    private void submitMergeAccount(String gid) {
        MergeAccountPutBean.ParamsBean paramsBean = new MergeAccountPutBean.ParamsBean();
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setSgid(gid);

        MergeAccountPutBean bean = new MergeAccountPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Merge_Account);
        bean.setModule(ModuleValueService.Module_For_Merge_Account);

        getPresenter().submitMergeAccount(bean);
    }

    /**
     * 显示合并账号框
     */
    private void onShowMergeAccount() {
        ivSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                onMergeAccount();
            }
        }, 300);
    }

    /**
     * 获取设备的配置信息，支持的功能等
     */
    private void getDeviceConfig(String simei) {
        DeviceConfigPutBean.ParamsBean paramsBean = new DeviceConfigPutBean.ParamsBean();
        if (!TextUtils.isEmpty(simei)) paramsBean.setSimei(simei);
        paramsBean.setType(ResultDataUtils.Config_Other);
        DeviceConfigPutBean bean = new DeviceConfigPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Get);
        bean.setModule(ModuleValueService.Module_For_Config_Get);
        getPresenter().getDeviceConfig(bean);
    }

}

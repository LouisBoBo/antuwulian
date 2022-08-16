package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.baidu.mapapi.map.BaiduMap;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.slxk.gpsantu.R;
import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerTrackAmapComponent;
import com.slxk.gpsantu.mvp.contract.TrackAmapContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.AlertBean;
import com.slxk.gpsantu.mvp.model.bean.RoutePoint;
import com.slxk.gpsantu.mvp.model.bean.TrackHasDataResultBean;
import com.slxk.gpsantu.mvp.model.bean.TrackListResultBean;
import com.slxk.gpsantu.mvp.model.putbean.TrackHasDataPutBean;
import com.slxk.gpsantu.mvp.model.putbean.TrackListPutBean;
import com.slxk.gpsantu.mvp.presenter.TrackAmapPresenter;
import com.slxk.gpsantu.mvp.ui.view.data.haibin.Calendar;
import com.slxk.gpsantu.mvp.utils.BitmapHelperAmap;
import com.slxk.gpsantu.mvp.utils.ConstantValue;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.slxk.gpsantu.mvp.utils.GpsUtils;
import com.slxk.gpsantu.mvp.utils.LocationAddress;
import com.slxk.gpsantu.mvp.utils.LocationAddressParsed;
import com.slxk.gpsantu.mvp.utils.Play;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.slxk.gpsantu.mvp.utils.SmoothMarker;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.utils.Utils;
import com.slxk.gpsantu.mvp.weiget.AlertAppDialog;
import com.slxk.gpsantu.mvp.weiget.DateSelectPopupWindow;
import com.slxk.gpsantu.mvp.weiget.TimeSelectDialog;
import com.slxk.gpsantu.mvp.weiget.TrackDatePopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/04/2020 11:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TrackAmapActivity extends BaseActivity<TrackAmapPresenter> implements TrackAmapContract.View, AMap.InfoWindowAdapter,
        AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.OnCameraChangeListener {

    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight; // 日期筛选
    @BindView(R.id.mapView)
    MapView mapView; // 地图
    @BindView(R.id.iv_play)
    ImageView ivPlay; // 播放轨迹
    @BindView(R.id.seekbar_process)
    SeekBar seekbarProcess; // 进度
    @BindView(R.id.iv_play_speed)
    ImageView ivPlaySpeed; // 播放速度
    @BindView(R.id.tv_play_speed)
    TextView tvPlaySpeed; // 播放速度
    @BindView(R.id.tv_location_time)
    TextView tvLocationTime; // 定位点时间
    @BindView(R.id.tv_address)
    TextView tvAddress; // 定位点地址
    @BindView(R.id.iv_base_station)
    ImageView ivBaseStation; // 基站点
    @BindView(R.id.tv_before_day)
    TextView tvBeforeDay; // 前一天
    @BindView(R.id.tv_data)
    TextView tvData; // 日期
    @BindView(R.id.tv_after_day)
    TextView tvAfterDay; // 后一天
    @BindView(R.id.tv_speed)
    TextView tvSpeed; // 速度
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.view_title)
    View viewTitle;
    @BindView(R.id.ll_base_station)
    LinearLayout llBaseStation; // 基站
    @BindView(R.id.tv_start_time)
    TextView tvHMStartTime; // 起始时间
    @BindView(R.id.tv_end_time)
    TextView tvHMEndTime; // 起止时间
    @BindView(R.id.iv_map_check)
    ImageView ivMapCheck;
    @BindView(R.id.iv_wifi_station)
    ImageView ivWiFiStation; // wifi点

    private String mSimei; //
    private long mImei; // 设备的imei号
    private long startTimeForQuest; // 开始时间
    private long endTimeForQuest; // 结束时间
    private String startData; // 开始日期
    private String endData; // 结束日期
    private String startHour = "00:00"; // 开始时间后缀
    private String endHour = "23:59"; // 结束时间后缀
    private int mLimitSize = 2000; // 限制获取数量
    private long mLastTime = 0; // 最后时间
    private boolean isTrackDataComplete = false; // 是否获取完当前的数据
    private LatLngBounds mLatLngBounds; // 地图内可见经纬度

    private List<Long> trackDataBeans; // 轨迹日期数据

    private boolean isShowBase = false; // 是否显示基站
    private TrackDatePopupWindow popupWindow; // 日期选择

    private ArrayList<Calendar> trackDateList;
    private ArrayList<Calendar> selectTrackDateList;
    private java.util.Calendar currentSelectDay; // 请求的日期的第一个，包括多日期请求
    private Calendar currentDay; // 请求的日期的第一个，包括多日期请求，用于显示在日历上

    static class MapInfoWindow {
        public View viewInfoWindows;
        public TextView tvMarkerType; // 定位类型
        public TextView tvMarkerStartTime; // 开始时间
        public TextView tvMarkerEndTime; // 结束时间
        public TextView tvMarkerParkingTime; // 停留时长
        public TextView tvMarkerAddress; // 地址
        public TextView tvMarkerDistance; // 分段里程
        public TextView tvMarkerTime; // 当前点的
        public TextView tvMarkerName; // 设备名称
        public TextView tvMarkerSpeed; //速度、平均速度
    }

    private MapInfoWindow mapInfoWindow = new MapInfoWindow();

    private AMap mAMap;
    private float mZoom = 16;

    private List<RoutePoint> jzRouteList;//基站点
    private List<RoutePoint> jzRouteListForSegmented;//基站点
    private List<RoutePoint> wifiRoutePointList; // wifi点
    private List<RoutePoint> wifiRoutePointListForSegmented; // wifi点
    private List<RoutePoint> playData; // 总数据
    private List<RoutePoint> playDataForSegmented; // 分段数据-用来计算总数据的
    private List<RoutePoint> arrowPointList; // 方向箭头
    private List<Marker> markerList;//保存地图中所有图标的列表
    private List<Marker> jzmarkerList;//保存地图中所有图标的列表
    private boolean isMakeAllVisual = true;//一旦有图标被隐藏,那么这个变量就是false,
    private Marker currentMarker, qiMarket, zhongMarket;//当前marker   起点   终点  停车点
    private Marker jzMarker;
    private BitmapHelperAmap bitmapHelper;
    private boolean isStartAndEnd = false; // 是否是起点或者终点
    private String mStartAndEnd = "_A"; // 起点或终点的后缀，用来区分起点或者终点与P点

    private SmoothMarker smoothMoveMarker; // 播放动画
    private boolean isPauseNow = false;//是否暂停动画
    boolean isColor = true;//颜色
    private String plate;//车牌
    private int playSpeed = 50; // 播放速度
    private int processPosition = 0; // 播放进度
    private int playType = 2; // 播放速度，1：快，播放速度为90，2：中，播放速度为50，3：慢，播放速度为10，默认快速

    private int playIndex = 0; // 播放进度条上的进度

    private boolean isGetSuccess = true; // 解析地址数据是否成功
    private double selectLatForPoint; // 选中点的纬度
    private double selectLonForPoint; // 选中点的经度

    private int drawableId = R.mipmap.icon_car_online; // 播放轨迹图标
    private int carImageId; // 设备图标id
    private double deviceLatitude = 0; // 设备纬度
    private double deviceLongitude = 0; // 设备经度
    private boolean isSwitchMapType = false; // 是否开启了卫星地图，false，否
    private TimeSelectDialog timeSelectDialog;
    private String deviceName;
    private boolean isShowWifi = false; // 是否显示wifi
    private ArrayList<Marker> wifiMarkerList;//保存地图中所有wifi图标的列表

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), TrackAmapActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrackAmapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_track_amap; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        setTitle(getString(R.string.menu_3));
        toolbarIvRight.setImageResource(R.drawable.icon_data);
        toolbarIvRight.setVisibility(View.VISIBLE);
        carImageId = MyApplication.getMyApp().getCarImageId();
        trackDataBeans = new ArrayList<>();
        trackDateList = new ArrayList<>();
        selectTrackDateList = new ArrayList<>();
        jzRouteList = new ArrayList<>();
        jzRouteListForSegmented = new ArrayList<>();
        markerList = new ArrayList<>();
        jzmarkerList = new ArrayList<>();
        wifiMarkerList = new ArrayList<>();
        playData = new ArrayList<>();
        playDataForSegmented = new ArrayList<>();
        wifiRoutePointList = new ArrayList<>();
        wifiRoutePointListForSegmented = new ArrayList<>();
        arrowPointList = new ArrayList<>();
        tvData.setText(DateUtils.getTodayDateTime_3());

        MyApplication.getMMKVUtils().put(ConstantValue.ACTIVITY_STATUS, false);

        mSimei = MyApplication.getMyApp().getSimei();
        mImei = MyApplication.getMyApp().getImei();
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getCarName())) {
            deviceName =MyApplication.getMyApp().getCarName();
        } else {
            deviceName =String.valueOf(mImei);
        }

        currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
        bitmapHelper = new BitmapHelperAmap(this);
        drawableId = drawableId();

//        setDataForTrack("", "");

        initMap();
        loadMapInfoWindow();
        onSeekBarProcess();
        updateBaseStationSwitch();
        updateWifiStationSwitch();

        tvHMStartTime.setText("00:00");
        tvHMEndTime.setText("23:59");
        String today = DateUtils.getTodayDateTime_3();
        onResetData(today,today);

//        getTrackHasForData();   隐藏获取有轨迹的点
    }

    /**
     * 初始化高德地图
     */
    private void initMap() {
        if (mAMap == null) {
            mAMap = mapView.getMap();
            mAMap.getUiSettings().setScaleControlsEnabled(true);
            mAMap.getUiSettings().setRotateGesturesEnabled(false);
            mAMap.getUiSettings().setZoomControlsEnabled(false);
            mAMap.getUiSettings().setMyLocationButtonEnabled(false);

            mAMap.setInfoWindowAdapter(this);
            mAMap.setOnMarkerClickListener(this);
            mAMap.setOnMapClickListener(this);
            mAMap.setOnCameraChangeListener(this);
        }
        // 默认展示中心点为设备位置
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getLatAndLon())) {
            String[] location = MyApplication.getMyApp().getLatAndLon().split(",");
            deviceLatitude = (double) Long.parseLong(location[0]) / 1000000;
            deviceLongitude = (double) Long.parseLong(location[1]) / 1000000;
        }
        // 设备位置，仅用于展示设备当前所在位置
        LatLng carLocation = new LatLng(deviceLatitude, deviceLongitude);
        mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(carLocation, mZoom, 0, 0)));
    }

    /**
     * 初始化气泡框
     */
    @SuppressLint("InflateParams")
    private void loadMapInfoWindow() {
        mapInfoWindow.viewInfoWindows = getLayoutInflater().inflate(R.layout.layout_infowindow, null);
        mapInfoWindow.tvMarkerType = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_type);
        mapInfoWindow.tvMarkerStartTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_start_time);
        mapInfoWindow.tvMarkerEndTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_end_time);
        mapInfoWindow.tvMarkerParkingTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_parking_time);
        mapInfoWindow.tvMarkerAddress = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_address);
        mapInfoWindow.tvMarkerDistance = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_parking_distance);
        mapInfoWindow.tvMarkerTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_time);
        mapInfoWindow.tvMarkerName = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_name);
        mapInfoWindow.tvMarkerSpeed = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_speed);
    }

    /**
     * 进度条事件监听
     */
    private void onSeekBarProcess() {
        seekbarProcess.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (playData != null && playData.size() > 3) {
                    playIndex = playData.size() * seekBar.getProgress() / 100;
                    if (playIndex >= playData.size()) {
                        playIndex = playData.size() - 1;
                    }
                    onSeekBarInfoShow();
                    generateSmoothMarker(playData, playIndex);
                    isPauseNow = true;
                    smoothMoveMarker.pauseMove();
                    ivPlay.setImageResource(R.mipmap.icon_track_play);
                }
            }
        });
    }

    /**
     * 拖动结束点的信息
     */
    @SuppressLint("SetTextI18n")
    private void onSeekBarInfoShow(){
        if (playIndex < playData.size()){
            RoutePoint currentPoint = playData.get(playIndex);
            setAddress(currentPoint);
            tvLocationTime.setText(getString(R.string.txt_time) + DateUtils.timeConversionDate_two(String.valueOf(currentPoint.getTime())));
            tvSpeed.setText(getString(R.string.txt_speed) + ((double) currentPoint.getSpeed() / 10) + "km/h");
        }
    }

    /**
     * 基站定位点显示开关
     */
    private void updateBaseStationSwitch() {
        ivBaseStation.setImageResource(isShowBase ? R.mipmap.icon_lbs_on : R.mipmap.icon_lbs_off);
        //加载基站信息
        if (jzmarkerList != null) {
            for (int i = 0; i < jzmarkerList.size(); ++i) {
                jzmarkerList.get(i).setVisible(isShowBase);
            }
        }
    }

    /**
     * wifi定位点显示开关
     */
    private void updateWifiStationSwitch() {
        ivWiFiStation.setImageResource(isShowWifi ? R.mipmap.icon_wifi_on : R.mipmap.icon_wifi_off);
        //加载基站信息
        if (wifiMarkerList != null) {
            for (int i = 0; i < wifiMarkerList.size(); ++i) {
                wifiMarkerList.get(i).setVisible(isShowWifi);
            }
        }
    }

    /**
     * 初始化时间，重置筛选时间
     *
     * @param start 开始日期
     * @param end   结束日期
     */
    private void setDataForTrack(String start, String end) {
        if (TextUtils.isEmpty(start) && TextUtils.isEmpty(end)) {
            startData = DateUtils.getTodayDateTime_3();
            endData = DateUtils.getTodayDateTime_3();
            tvHMStartTime.setText("00:00");
            tvHMEndTime.setText("23:59");
        } else {
            startData = start;
            endData = end;
        }
        LogUtils.e("Time- Start=" + startData + " " + startHour);
        LogUtils.e("Time- End=" + endData + " " + endHour);
        startTimeForQuest = DateUtils.data_2(startData + " " + startHour+ ":00");
        endTimeForQuest = DateUtils.data_2(endData + " " + endHour+ ":59");
    }

    /**
     * 查询有轨迹的日期
     */
    private void getTrackHasForData() {
        TrackHasDataPutBean.ParamsBean paramsBean = new TrackHasDataPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);

        TrackHasDataPutBean bean = new TrackHasDataPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Track_Data_Get);
        bean.setModule(ModuleValueService.Module_For_Track_Data_Get);

        if (getPresenter() != null) {
            getPresenter().getTrackHasForData(bean);
        }
    }

    /**
     * 获取轨迹列表
     *
     * @param isShow      是否显示加载框
     * @param isResetData 是否是请求新的一天轨迹数据，true:请求新的一天数据，false:请求同一天的轨迹数据，后续的数据
     */
    private void getTrackList(boolean isShow, boolean isResetData) {
        TrackListPutBean.ParamsBean paramsBean = new TrackListPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setTime_end(endTimeForQuest);
        paramsBean.setTime_begin(startTimeForQuest);
        paramsBean.setLimit_size(mLimitSize);
        if (mLastTime != 0) {
            paramsBean.setLast_time(mLastTime);
        }

        TrackListPutBean bean = new TrackListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Track_List_Data);
        bean.setModule(ModuleValueService.Module_For_Track_List_Data);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getTrackList(bean, isShow, isResetData);
        }
    }

    /**
     * 重置数据
     *
     * @param start 开始时间
     * @param end   结束时间
     */
    private void onResetData(String start, String end) {
        cancelSmoothMarker();
        onClearData();
        mLastTime = 0;
        isTrackDataComplete = false;
        setDataForTrack(start, end);
        getTrackList(true, true);
    }

    /**
     * 清除数据，恢复原来的数据
     */
    private void onClearData() {
        isPauseNow = false;
        playData.clear();
        mAMap.clear();
        processPosition = 0; // 播放进度
        seekbarProcess.setProgress(1);
        ivPlay.setImageResource(R.mipmap.icon_track_play);
        handler.removeCallbacksAndMessages(null);

        if (qiMarket != null) {
            qiMarket.remove();
            qiMarket = null;
        }

        if (zhongMarket != null) {
            zhongMarket.remove();
            zhongMarket = null;
        }

        if (currentMarker != null) {
            currentMarker.remove();
            currentMarker = null;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getInfoWindow(Marker marker) {
        // 是否是P点 + 当前点的时间 + 打点开始时间 + 打点结束时间 + 停留时长 + 分段里程 + 经度 + 纬度
        currentMarker = marker;

        String titleString = marker.getTitle();
        isStartAndEnd = titleString.contains(mStartAndEnd);
        titleString = titleString.replace(mStartAndEnd, "");
        String snippetString = marker.getSnippet();
        mapInfoWindow.tvMarkerType.setText(titleString);
        mapInfoWindow.tvMarkerName.setText(getString(R.string.txt_name) + deviceName);
        String[] detailInfo = snippetString.split(",");
        if (detailInfo.length == 9) {
            if (Integer.parseInt(detailInfo[0]) == 1 && !isStartAndEnd) {
                mapInfoWindow.tvMarkerTime.setVisibility(View.GONE);
                if (Long.parseLong(detailInfo[2]) != 0) {
                    mapInfoWindow.tvMarkerStartTime.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerStartTime.setText(getString(R.string.txt_start_static_parking) + "："
                            + DateUtils.timeConversionDate_two(detailInfo[2]));
                } else {
                    mapInfoWindow.tvMarkerStartTime.setVisibility(View.GONE);
                }
                if (Long.parseLong(detailInfo[3]) != 0) {
                    mapInfoWindow.tvMarkerEndTime.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerEndTime.setText(getString(R.string.txt_end_static_parking) + "："
                            + DateUtils.timeConversionDate_two(detailInfo[3]));
                } else {
                    mapInfoWindow.tvMarkerEndTime.setVisibility(View.GONE);
                }
                if (Integer.parseInt(detailInfo[4]) != 0) {
                    mapInfoWindow.tvMarkerParkingTime.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerParkingTime.setText(getString(R.string.txt_duration_time) + "："
                            + Utils.getParkingTime(this, Integer.parseInt(detailInfo[4])));
                } else {
                    mapInfoWindow.tvMarkerParkingTime.setVisibility(View.GONE);
                }
                if (Integer.parseInt(detailInfo[5]) != 0) {
                    mapInfoWindow.tvMarkerDistance.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerDistance.setText(getString(R.string.txt_section_mileage)
                            + Utils.formatValue((double) Integer.parseInt(detailInfo[5]) / 1000) + "km");
                } else {
                    mapInfoWindow.tvMarkerDistance.setVisibility(View.GONE);
                }
                mapInfoWindow.tvMarkerSpeed.setVisibility(View.GONE);
            } else {
                mapInfoWindow.tvMarkerStartTime.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerEndTime.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerParkingTime.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerDistance.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerTime.setVisibility(View.VISIBLE);
                if (Long.parseLong(detailInfo[1]) != 0) {
                    mapInfoWindow.tvMarkerTime.setText(getString(R.string.txt_time) + DateUtils.timeConversionDate_two(detailInfo[1]));
                }
                mapInfoWindow.tvMarkerSpeed.setVisibility(View.VISIBLE);
                mapInfoWindow.tvMarkerSpeed.setText(getString(R.string.txt_speed) + (Double.parseDouble(detailInfo[8]) / 10) + "km/h");
            }
            selectLatForPoint = Double.parseDouble(detailInfo[6]);
            selectLonForPoint = Double.parseDouble(detailInfo[7]);
            new LocationAddress().Parsed(selectLatForPoint, selectLonForPoint)
                    .setAddressListener(str -> {
                        if (!TextUtils.isEmpty(str)) {
                            mapInfoWindow.tvMarkerAddress.setText(getString(R.string.txt_address) + str);
                        } else {
                            mapInfoWindow.tvMarkerAddress.setText(getString(R.string.txt_address) + selectLatForPoint + "," + selectLonForPoint);
                        }
                    });
        }
        return mapInfoWindow.viewInfoWindows;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        try {
            mZoom = cameraPosition.zoom;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (currentMarker != null) {
            currentMarker.hideInfoWindow();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            if (smoothMoveMarker != null) {
                if (smoothMoveMarker.getMarker().equals(marker)) {
                    return true;
                }
            }
            marker.showInfoWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
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

    @OnClick({R.id.toolbar_iv_right, R.id.iv_play, R.id.iv_play_speed, R.id.tv_play_speed, R.id.ll_base_station, R.id.tv_before_day,
            R.id.tv_after_day, R.id.ll_start_end_time, R.id.iv_map_check, R.id.ll_wifi_station})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.toolbar_iv_right: // 日期选择
                    onSelectData();
                    break;
                case R.id.iv_play: // 播放轨迹
                    playTrack();
                    break;
                case R.id.iv_play_speed: // 播放速度
                case R.id.tv_play_speed:
                    onPlaySpeed();
                    break;
                case R.id.ll_base_station: // 基站点
                    isShowBase = !isShowBase;
                    updateBaseStationSwitch();
                    break;
                case R.id.tv_before_day: // 前一天
                    onBeforeDayTrack();
                    break;
                case R.id.tv_after_day: // 后一天
                    onAfterDayTrack();
                    break;
                case R.id.ll_start_end_time:
                    onSelectStartAndEndTime();
                    break;
                case R.id.iv_map_check: // 切换卫星地图
                    onCheckSatelliteMap();
                    break;
                case R.id.ll_wifi_station: // wifi
                    isShowWifi = !isShowWifi;
                    updateWifiStationSwitch();
                    break;
            }
        }
    }

    /**
     * 切换2D地图和卫星地图
     */
    private void onCheckSatelliteMap() {
        isSwitchMapType = !isSwitchMapType;
        if (isSwitchMapType) {
            mAMap.setMapType(AMap.MAP_TYPE_SATELLITE);
            ivMapCheck.setImageResource(R.drawable.icon_check_map_select);
        } else {
            mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
            ivMapCheck.setImageResource(R.drawable.icon_check_map);
        }
    }

    /**
     * 选择日期
     */
    private void onSelectData() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            if (currentDay == null) {
                currentDay = new Calendar();
            }
            if (currentSelectDay == null) currentSelectDay = java.util.Calendar.getInstance();
            currentDay.setYear(currentSelectDay.get(java.util.Calendar.YEAR));
            currentDay.setMonth(currentSelectDay.get(java.util.Calendar.MONTH) + 1);
            currentDay.setDay(currentSelectDay.get(java.util.Calendar.DAY_OF_MONTH));

            popupWindow = new TrackDatePopupWindow(this, trackDateList, currentDay);
            popupWindow.setTrackDataChange(new TrackDatePopupWindow.onTrackDataChange() {
                @SuppressLint("SetTextI18n")
                @Override
                public void OnSelectDate(List<Calendar> calendarList) {
                    if (!isTrackDataComplete) {
                        ToastUtils.showShort(getString(R.string.txt_track_data_get_hint));
                        return;
                    }
                    java.util.Calendar compareCalendar = java.util.Calendar.getInstance(Locale.ENGLISH);
                    Calendar currentDate = new Calendar();
                    currentDate.setDay(compareCalendar.get(java.util.Calendar.DAY_OF_MONTH));
                    currentDate.setMonth(compareCalendar.get(java.util.Calendar.MONTH) + 1);
                    currentDate.setYear(compareCalendar.get(java.util.Calendar.YEAR));
                    boolean isRet = false;
                    for (int i = 0; i < calendarList.size(); ++i) {
                        if (calendarList.get(i).compareTo(currentDate) > 0) {
                            isRet = true;
                            break;
                        }
                    }

                    if (isRet) {
                        ToastUtils.showShort(getString(R.string.txt_max_track_date));
                        return;
                    }
                    popupWindow.dismiss();
                    selectTrackDateList.clear();
                    selectTrackDateList.addAll(calendarList);
                    initStartEndTime();
                    if (selectTrackDateList.size() == 1) {
                        //单日
                        java.util.Calendar startCalendar = java.util.Calendar.getInstance();
                        startCalendar.set(
                                selectTrackDateList.get(0).getYear(),
                                selectTrackDateList.get(0).getMonth() - 1,
                                selectTrackDateList.get(0).getDay(),
                                0,
                                0,
                                0);
                        currentSelectDay = startCalendar;

                        java.util.Calendar endCalendar = java.util.Calendar.getInstance();
                        endCalendar.set(
                                selectTrackDateList.get(0).getYear(),
                                selectTrackDateList.get(0).getMonth() - 1,
                                selectTrackDateList.get(0).getDay(),
                                23,
                                59,
                                59);
                        String dataTime = selectTrackDateList.get(0).getYear() + "-" +
                                selectTrackDateList.get(0).getMonth() + "-" +
                                selectTrackDateList.get(0).getDay();
                        tvData.setText(dataTime);
                        onResetData(dataTime, dataTime);
                    } else {
                        //多日
                        java.util.Calendar startCalendar = java.util.Calendar.getInstance();
                        startCalendar.set(
                                selectTrackDateList.get(0).getYear(),
                                selectTrackDateList.get(0).getMonth() - 1,
                                selectTrackDateList.get(0).getDay(),
                                0,
                                0,
                                0);
                        currentSelectDay = startCalendar;


                        java.util.Calendar endCalendar = java.util.Calendar.getInstance();
                        endCalendar.set(
                                selectTrackDateList.get(selectTrackDateList.size() - 1).getYear(),
                                selectTrackDateList.get(selectTrackDateList.size() - 1).getMonth() - 1,
                                selectTrackDateList.get(selectTrackDateList.size() - 1).getDay(),
                                23,
                                59,
                                59);
                        String startTime = selectTrackDateList.get(0).getYear() + "-" +
                                selectTrackDateList.get(0).getMonth() + "-" +
                                selectTrackDateList.get(0).getDay();
                        String endTime = selectTrackDateList.get(selectTrackDateList.size() - 1).getYear() + "-" +
                                selectTrackDateList.get(selectTrackDateList.size() - 1).getMonth() + "-" +
                                selectTrackDateList.get(selectTrackDateList.size() - 1).getDay();
                        tvData.setText(startTime + "\n" + endTime);
                        onResetData(startTime, endTime);
                    }
                }
            });
            popupWindow.showAsDropDown(viewTitle);
        }
    }

    /**
     * 选择起止时间进行筛选
     */
    private void onSelectStartAndEndTime() {
        java.util.Calendar calendarCompare = java.util.Calendar.getInstance();
        boolean isCompare = false;
        if (currentSelectDay == null) currentSelectDay = java.util.Calendar.getInstance();
        if (currentSelectDay.get(java.util.Calendar.YEAR) == calendarCompare.get(java.util.Calendar.YEAR)
                && currentSelectDay.get(java.util.Calendar.MONTH) == calendarCompare.get(java.util.Calendar.MONTH)
                && currentSelectDay.get(java.util.Calendar.DAY_OF_MONTH) == calendarCompare.get(java.util.Calendar.DAY_OF_MONTH)) {
            isCompare = true;
        }
        timeSelectDialog = new TimeSelectDialog(this, R.style.MsgDialogStyle, tvHMStartTime.getText().toString(),
                tvHMEndTime.getText().toString(), isCompare);
        timeSelectDialog.setDialogCallback(new TimeSelectDialog.DialogCallback() {
            @Override
            public void EventOK(String sTime, String eTime) {
                timeSelectDialog.dismiss();
                tvHMStartTime.setText(sTime);
                tvHMEndTime.setText(eTime);

                startHour = sTime;
                endHour = eTime;

                onResetData(startData,endData);
            }

            @Override
            public void EventCancel() {
                timeSelectDialog.dismiss();
            }
        });
        timeSelectDialog.show();
    }


    /**
     * 前一天的数据
     */
    @SuppressLint("SetTextI18n")
    private void onBeforeDayTrack() {
        if (!isTrackDataComplete) {
            ToastUtils.showShort(getString(R.string.txt_track_data_get_hint));
            return;
        }

        if (currentSelectDay == null) {
            currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
        }

        currentSelectDay.add(java.util.Calendar.DAY_OF_MONTH, -1);

        String beforeTime = currentSelectDay.get(java.util.Calendar.YEAR) + "-" +
                (currentSelectDay.get(java.util.Calendar.MONTH) + 1) + "-" +
                (currentSelectDay.get(java.util.Calendar.DAY_OF_MONTH));
        initStartEndTime();
        tvData.setText(beforeTime);

        onResetData(beforeTime, beforeTime);
    }

    /**
     * 后一天的数据
     */
    @SuppressLint("SetTextI18n")
    private void onAfterDayTrack() {
        if (!isTrackDataComplete) {
            ToastUtils.showShort(getString(R.string.txt_track_data_get_hint));
            return;
        }
        if (currentSelectDay == null) {
            currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
        }
        currentSelectDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
        java.util.Calendar tmpDate = java.util.Calendar.getInstance(Locale.ENGLISH);
        if (currentSelectDay.compareTo(tmpDate) > 0) {
            currentSelectDay.add(java.util.Calendar.DAY_OF_MONTH, -1);
            ToastUtils.showShort(getString(R.string.txt_max_track_date));
            return;
        }

        String afterTime = currentSelectDay.get(java.util.Calendar.YEAR) + "-" +
                (currentSelectDay.get(java.util.Calendar.MONTH) + 1) + "-" +
                (currentSelectDay.get(java.util.Calendar.DAY_OF_MONTH));
        initStartEndTime();
        tvData.setText(afterTime);

        onResetData(afterTime, afterTime);
    }

    //初始化开始、结束时间
    private void initStartEndTime() {
        startHour = "00:00";
        endHour = "23:59";
        tvHMStartTime.setText("00:00");
        tvHMEndTime.setText("23:59");
    }

    /**
     * 快进播放，播放速度，1：快，播放速度为10，2：中，播放速度为6，3：慢，播放速度为2，默认快速
     */
    private void onPlaySpeed() {
        if (playType == 1) {
            playType = 2;
            playSpeed = 50;
            tvPlaySpeed.setText(getString(R.string.txt_medium_speed));
        } else if (playType == 2) {
            playType = 3;
            playSpeed = 10;
            tvPlaySpeed.setText(getString(R.string.txt_slow));
        } else {
            playType = 1;
            playSpeed = 90;
            tvPlaySpeed.setText(getString(R.string.txt_fast));
        }

        if (smoothMoveMarker == null) {
            smoothMoveMarker = new SmoothMarker(mAMap, carImageId);
        }
        int duration = 50 + (10 * (100 - playSpeed));
        smoothMoveMarker.setTotalDuration(duration);
    }

    /**
     * 格式化有轨迹数据的日期
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    private Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(getResources().getColor(R.color.color_2EB6B9));
        return calendar;
    }

    @Override
    public void getTrackHasForDataSuccess(TrackHasDataResultBean trackHasDataResultBean) {
        trackDataBeans.clear();
        if (trackDateList != null) {
            trackDateList.clear();
        }
        if (trackHasDataResultBean.getDate() != null) {
            trackDataBeans.addAll(trackHasDataResultBean.getDate());
        }
        boolean isHasTodayTrack = false; // 是否有当天的轨迹
        String today = DateUtils.getTodayDateTime_3();
        for (int i = 0; i < trackDataBeans.size(); i++) {
            String day = DateUtils.timedate_2(String.valueOf(trackDataBeans.get(i)));
            if (day.equals(today)) {
                isHasTodayTrack = true;
            }
            String[] dayDayas = day.split("-");
            trackDateList.add(getCalendar(Integer.parseInt(dayDayas[0]), Integer.parseInt(dayDayas[1]), Integer.parseInt(dayDayas[2])));
        }


        if (trackDataBeans.size() == 0) {
            onEndMoreData();
            currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
            ToastUtils.showShort(getString(R.string.txt_no_trace_data));
        } else {
            if (isHasTodayTrack) {
                currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
                onResetData(today, today);
            } else {
                AlertBean bean = new AlertBean();
                bean.setTitle(getString(R.string.txt_tip));
                bean.setAlert(getString(R.string.txt_no_trace_data_ex));
                bean.setType(0);
                AlertAppDialog dialog = new AlertAppDialog();
                dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onConfirm() {
                        String mostRecentDate = DateUtils.timedate_2(String.valueOf(trackDataBeans.get(trackDataBeans.size() - 1))); // 最近的日期
                        String[] dayDayas = mostRecentDate.split("-");
                        currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
                        currentSelectDay.set(
                                Integer.parseInt(dayDayas[0]),
                                Integer.parseInt(dayDayas[1]) - 1,
                                Integer.parseInt(dayDayas[2]),
                                0,
                                0,
                                0);
                        tvData.setText(dayDayas[0] + "-" + dayDayas[1] + "-" + dayDayas[2]);
                        onResetData(mostRecentDate, mostRecentDate);
                    }

                    @Override
                    public void onCancel() {
                        onEndMoreData();
                        currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
                    }
                });
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getTrackListSuccess(TrackListResultBean trackListResultBean, boolean isResetData) {
        if (isResetData) {
            jzRouteList.clear();
            playData.clear();
            wifiRoutePointList.clear();
            markerList.clear();
            jzmarkerList.clear();
            wifiMarkerList.clear();
            mLatLngBounds = null;
        }
        playDataForSegmented.clear();
        jzRouteListForSegmented.clear();
        wifiRoutePointListForSegmented.clear();
        arrowPointList.clear();

        if (trackListResultBean.getData() == null || (trackListResultBean.getData().size() == 0 && playData.size() == 0)) {
            onEndMoreData();
            ToastUtils.showShort(getString(R.string.txt_no_trace_data));
            tvAddress.setVisibility(View.GONE);
            llTime.setVisibility(View.GONE);
            return;
        }
        //判断起点是否在国外，是国外就转WGS 84
        TrackListResultBean.DataBean beanStart = trackListResultBean.getData().get(0);
        double latStart = (double) beanStart.getLat() / 1000000;
        double lonStart = (double) beanStart.getLon() / 1000000;
        int where = GpsUtils.PointType(latStart, lonStart);
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < trackListResultBean.getData().size(); i++) {
            TrackListResultBean.DataBean bean = trackListResultBean.getData().get(i);
            double lat = (double) bean.getLat() / 1000000;
            double lon = (double) bean.getLon() / 1000000;
            LatLng latLng;
            if (where == 1 || where == 2) { //1 台湾   2 国外
                double[] latLon = GpsUtils.toWGS84Point(lat, lon);
                latLng = new LatLng(latLon[0], latLon[1]);
            } else {
                latLng = new LatLng(lat, lon);
            }
            RoutePoint routePoint = new RoutePoint(latLng, bean.getType(), bean.getTime(), bean.getSpeed(),
                    lat, lon, bean.getDirection(), bean.getPtype(), bean.getStart_time(), bean.getDistance(), bean.getDuration(), bean.getEnd_time());
            if (bean.getType() == ResultDataUtils.Location_Base_Station_Track || bean.getType() == ResultDataUtils.Location_Static_Base_Station_Track) {
                jzRouteListForSegmented.add(routePoint);
            } else if (bean.getType() == ResultDataUtils.Location_GPS_Track || bean.getType() == ResultDataUtils.Location_Static_Gps_Track) {
                boundsBuilder.include(routePoint.getPoint());
                playDataForSegmented.add(routePoint);
            } else {
                wifiRoutePointListForSegmented.add(routePoint);
            }
        }
        if (isResetData) {
            if (playDataForSegmented.size() == 0){
                if (wifiRoutePointListForSegmented.size() > 0) {
                    for (int i = 0; i < wifiRoutePointListForSegmented.size(); i++) {
                        boundsBuilder.include(wifiRoutePointListForSegmented.get(i).getPoint());
                    }
                }else{
                    for (int i = 0; i < jzRouteListForSegmented.size(); i++) {
                        boundsBuilder.include(jzRouteListForSegmented.get(i).getPoint());
                    }
                }
            }
            mLatLngBounds = boundsBuilder.build();
            //调整角度
            mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mLatLngBounds, 200));
            mZoom = mAMap.getCameraPosition().zoom;
        }

        if (arrowPointList.size() == 0){
            if (playDataForSegmented.size() > 0){
                arrowPointList.add(playDataForSegmented.get(0));
                for (int i = 1; i < playDataForSegmented.size(); i++){
                    RoutePoint lastPoint = arrowPointList.get(arrowPointList.size() - 1);
                    float tmpDistance = AMapUtils.calculateLineDistance(lastPoint.getPoint(), playDataForSegmented.get(i).getPoint());
                    if (tmpDistance > 500) {
                        arrowPointList.add(playDataForSegmented.get(i));
                    }
                }
            }
        }else{
            for (int i = 0; i < playDataForSegmented.size(); i++){
                RoutePoint lastPoint = arrowPointList.get(arrowPointList.size() - 1);
                float tmpDistance = AMapUtils.calculateLineDistance(lastPoint.getPoint(), playDataForSegmented.get(i).getPoint());
                if (tmpDistance > 500) {
                    arrowPointList.add(playDataForSegmented.get(i));
                }
            }
        }

        //绘制轨迹线
        List<LatLng> lineList = new ArrayList<>();
        int color = Color.rgb(50, 205, 50); //绘制轨迹线颜色
        if (playData.size() > 0) {
            playDataForSegmented.add(0, playData.get(playData.size() - 1));
        }
        if (playDataForSegmented.size() >= 2) {
            for (int i = 0; i < playDataForSegmented.size(); i++) {
                lineList.add(playDataForSegmented.get(i).getPoint());
            }
            mAMap.addPolyline(new PolylineOptions().addAll(lineList).width(12).color(color));
        }

        // 添加分段数据到总数据中 - 绘制轨迹数据
        playData.addAll(playDataForSegmented);
        // 添加分段数据到总数据中 - 基站点数据
        jzRouteList.addAll(jzRouteListForSegmented);
        // 添加分段数据到总数据中 - wifi点数据
        wifiRoutePointList.addAll(wifiRoutePointListForSegmented);

        // 展示第一个点的信息
        if (isResetData){
            if (playData.size() > 0){
                tvAddress.setVisibility(View.VISIBLE);
                llTime.setVisibility(View.VISIBLE);
                RoutePoint currentPoint = playData.get(0);
                setAddress(currentPoint);
                tvLocationTime.setText(getString(R.string.txt_time) + DateUtils.timeConversionDate_two(String.valueOf(currentPoint.getTime())));
                tvSpeed.setText(getString(R.string.txt_speed) + ((double) currentPoint.getSpeed() / 10) + "km/h");
            }
        }

        //3.绘制最后一个点   终点
        if (isTrackDataComplete) {
            // 如果是第一页就加载完了，则判断从总数据索引 1  -  size-1 中间段执行
            if (isResetData) {
                for (int i = 1; i < playData.size() - 1; i++) {
                    if (playData.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playData.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playData.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playData.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            } else {
                // 如果是第二页++ 加载完成，则判断从当前页数据的索引  0  -   size-1  中间段执行
                for (int i = 0; i < playDataForSegmented.size() - 1; i++) {
                    if (playDataForSegmented.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playDataForSegmented.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playDataForSegmented.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playDataForSegmented.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            }

            // 终点
            if (playData.size() > 1) {
                int endPosition = playData.size() - 1;
                int lastOneType = playData.get(endPosition).type;
                String lastTitleString = getTitleStringByType(lastOneType);
                zhongMarket = mAMap.addMarker(new MarkerOptions().zIndex(1)
                        .position(playData.get(endPosition).point)
                        .title(lastTitleString + mStartAndEnd)
                        .snippet(getTrackDetailInfo(playData.get(endPosition)))
                        .icon(bitmapHelper.getBitmapZoomForEnd(mZoom)));
                zhongMarket.setToTop();
                markerList.add(zhongMarket);
            }
        } else {
            if (isResetData) {
                for (int i = 1; i < playDataForSegmented.size(); i++) {
                    if (playDataForSegmented.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playDataForSegmented.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playDataForSegmented.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playDataForSegmented.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            } else {
                for (int i = 0; i < playDataForSegmented.size(); i++) {
                    if (playDataForSegmented.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playDataForSegmented.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playDataForSegmented.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playDataForSegmented.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            }
        }

        //2.绘制第一个点   起点
        if (isResetData) {
            if (playData.size() > 0) {
                int firstType = playData.get(0).type;
                String firstTitleString = getTitleStringByType(firstType);
                qiMarket = mAMap.addMarker(new MarkerOptions().zIndex(1)
                        .position(playData.get(0).point)
                        .title(firstTitleString + mStartAndEnd)
                        .snippet(getTrackDetailInfo(playData.get(0)))
                        .icon(bitmapHelper.getBitmapZoomForStart(mZoom)));
                qiMarket.setToTop();
                markerList.add(qiMarket);
            }
        }

        // 基站红点
        if (jzRouteListForSegmented.size() > 0) {
            RoutePoint routePointTmp;
            String titleStringTmp;
            for (int j = 0; j < jzRouteListForSegmented.size(); j++) {
                routePointTmp = jzRouteListForSegmented.get(j);
                titleStringTmp = getTitleStringByType(routePointTmp.getType());

                jzMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(routePointTmp.point)
                        .title(titleStringTmp)
                        .snippet(getTrackDetailInfo(routePointTmp))
                        .visible(isShowBase)
                        .icon(bitmapHelper.getBitmapZoomForBaseStation(mZoom)));
                jzmarkerList.add(jzMarker);
            }
        }

        // wifi蓝点
        if (wifiRoutePointListForSegmented.size() > 0) {
            RoutePoint routePointTmp;
            String titleStringTmp;
            for (int j = 0; j < wifiRoutePointListForSegmented.size(); j++) {
                routePointTmp = wifiRoutePointListForSegmented.get(j);
                if (routePointTmp.getType() == 2 || routePointTmp.getType() == 5) {
                    titleStringTmp = getTitleStringByType(routePointTmp.getType());
                    Marker wifiMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                            .position(routePointTmp.point)
                            .title(titleStringTmp)
                            .snippet(getTrackDetailInfo(routePointTmp))
                            .visible(isShowWifi)
                            .icon(bitmapHelper.getBitmapZoomForWifi(mZoom)));
                    wifiMarkerList.add(wifiMarker);
                }
            }
        }

        //5.绘制角度
        if (arrowPointList.size() > 1) {
            Marker rotateMarker;
            RoutePoint routePoint;
            String titleString;
            LatLng pre;
            LatLng next;
            // 再循环
            for (int i = 1; i < arrowPointList.size(); i++) {
                routePoint = arrowPointList.get(i);
                pre = arrowPointList.get(i - 1).point;
                next = routePoint.point;
                titleString = getTitleStringByType(routePoint.type);
                rotateMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(next)
                        .title(titleString)
                        .snippet(getTrackDetailInfo(routePoint))
                        .icon(bitmapHelper.getBitmapZoomForDirection(mZoom)));

                float rotate = Utils.getRotate(pre, next);
                rotateMarker.setRotateAngle(360 - rotate + mAMap.getCameraPosition().bearing);
//                rotateMarker.setRotateAngle(routePoint.getDirection());
                markerList.add(rotateMarker);
            }
        }
        if (!isTrackDataComplete) {
            mLastTime = trackListResultBean.getData().get(trackListResultBean.getData().size() - 1).getTime();
            ivPlay.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getTrackList(false, false);
                }
            }, 100);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setAddress(RoutePoint currentPoint) {
        double latForPoint = currentPoint.getLat();
        double lonForPoint = currentPoint.getLon();
        LocationAddressParsed.getLocationParsedInstance().Parsed(latForPoint, lonForPoint)
                .setAddressListener(str -> {
                    isGetSuccess = true;
                    if (tvAddress == null) return;
                    if (!TextUtils.isEmpty(str)) {
                        tvAddress.setText(getString(R.string.txt_address) + str);
                    } else {
                        tvAddress.setText(getString(R.string.txt_address) + latForPoint + "," + lonForPoint);
                    }
                });
    }

    @Override
    public void onEndMoreData() {
        isTrackDataComplete = true;
    }

    /**
     * 当前点类型
     *
     * @param type
     * @return
     */
    private String getTitleStringByType(int type) {
        switch (type) {
            case ResultDataUtils.Location_Base_Station_Track:
                return getString(R.string.txt_base_station);
            case ResultDataUtils.Location_Static_Base_Station_Track:
                return getString(R.string.txt_base_station_static);
            case ResultDataUtils.Location_GPS_Track:
                return getString(R.string.txt_location_gps);
            case ResultDataUtils.Location_Static_Gps_Track:
                return getString(R.string.txt_location_gps_static);
            case ResultDataUtils.Location_WIFI_Track:
                return getString(R.string.txt_location_wifi);
            case ResultDataUtils.Location_Static_WIFI_Track:
                return getString(R.string.txt_location_wifi_static);
        }
        return null;
    }

    /**
     * 轨迹点的详细信息
     *
     * @param routePoint
     * @return
     */
    private String getTrackDetailInfo(RoutePoint routePoint) {
        // 是否是P点 + 当前点的时间 + 打点开始时间 + 打点结束时间 + 停留时长 + 分段里程 + 经度 + 纬度
        return routePoint.getPtype() + "," + routePoint.getTime() + "," + routePoint.getStart_time() + "," + routePoint.getEnd_time() + ","
                + routePoint.getDuration() + "," + routePoint.getDistance() + "," + routePoint.getLat() + "," + routePoint.getLon() + "," + routePoint.getSpeed();
    }

    /**
     * 播放动画
     */
    private void playTrack() {
        if (playData.size() < 3) {
            ToastUtils.showShort(getString(R.string.txt_not_play));
            return;
        }

        if (smoothMoveMarker == null) {
            ivPlay.setImageResource(R.mipmap.icon_track_pause);
            //txtPlay.setText(getString(R.string.txt_track_stop));
            generateSmoothMarker(playData, 0);
        } else {
            if (smoothMoveMarker.getRemainDistance() == 0.0f) {
                //已经播放了一遍,再播放一遍
                ivPlay.setImageResource(R.mipmap.icon_track_pause);
                //txtPlay.setText(getString(R.string.txt_track_stop));
                generateSmoothMarker(playData, 0);
                return;
            }

            if (!smoothMoveMarker.isPauseAnimationNow()) {
                if (!isPauseNow) {
                    //点击暂停
                    ivPlay.setImageResource(R.mipmap.icon_track_play);
                    //txtPlay.setText(getString(R.string.txt_track_play));
                    isPauseNow = true;
                    smoothMoveMarker.pauseMove();
                } else {
                    //暂停后恢复播放
                    ivPlay.setImageResource(R.mipmap.icon_track_pause);
                    //txtPlay.setText(getString(R.string.txt_track_stop));
                    isPauseNow = false;
                    //找到真正播放的索引
                    mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            smoothMoveMarker.getPosition(),//先移动到点
                            mZoom, //新的缩放级别
                            0, //俯仰角0°~45°（垂直与地图时为0）
                            0  ////偏航角 0~360° (正北方为0)
                    )));
                    smoothMoveMarker.resumeMove();
                }
            }
        }
    }

    /***
     *  生成点平滑移动
     */
    private void generateSmoothMarker(List<RoutePoint> playData, int index) {

        try {
            if (playData == null || playData.size() == 0) {
                com.blankj.utilcode.util.ToastUtils.showShort(getString(R.string.txt_no_trace_data));
                return;
            }

            if (smoothMoveMarker != null) {
                smoothMoveMarker.destroy();
                smoothMoveMarker = null;
            }

            RoutePoint routePoint;
            //当前轨迹的播放列表
            List<LatLng> points = new ArrayList<>();
            for (int i = 0; i < playData.size(); i++) {
                routePoint = playData.get(i);
                points.add(routePoint.getPoint());
            }

            smoothMoveMarker = new SmoothMarker(mAMap, carImageId);
            smoothMoveMarker.setDescriptor(BitmapDescriptorFactory.fromBitmap(Utils.decodeSampledBitmapFromResource(getResources(),
                    drawableId, Utils.dipToPx(this, 38))));
            LatLng drivePoint = points.get(index);
            processPosition = index;
            Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
            points.set(pair.first, drivePoint);
            List<RoutePoint> subList = playData.subList(pair.first, points.size());
            // 设置滑动的轨迹左边点
            smoothMoveMarker.setPoints(subList, play);
            //CommonUtils.LogKevin("轨迹","总共长度:"+points.size(),TrackActivity.this);
            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    drivePoint,//先移动到点
                    mZoom, //新的缩放级别
                    0, //俯仰角0°~45°（垂直与地图时为0）
                    0  ////偏航角 0~360° (正北方为0)
            )));

            // 设置滑动的总时间
            int duration = 50 + (10 * (100 - playSpeed));
            smoothMoveMarker.setTotalDuration(duration);
            // 开始滑动
            smoothMoveMarker.setMoveListener(smoothListener);
            smoothMoveMarker.setFinishMoveListener(smoothFinishMoveListener);
            smoothMoveMarker.startSmoothMove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消动画
     */
    private void cancelSmoothMarker() {
        if (smoothMoveMarker != null) {
            smoothMoveMarker.destroy();
            smoothMoveMarker = null;
            isPauseNow = false;
            ivPlay.setImageResource(R.mipmap.icon_track_play);
        }
    }

    /**
     * todo 监听动画结束的监听器
     */
    private SmoothMarker.SmoothFinishMoveListener smoothFinishMoveListener = new SmoothMarker.SmoothFinishMoveListener() {
        @Override
        public void onFinishMove() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0x0100);
                    isPauseNow = false;
                }
            });

        }
    };

    /**
     * 所有图标都可见
     */
    private void allMarkerVisual() {
        for (int i = 0; i < markerList.size(); i++) {
            markerList.get(i).setVisible(true);
        }
        isMakeAllVisual = true;
    }

    /**
     * 监听动画播放的过程
     */
    private SmoothMarker.SmoothMarkerMoveListener smoothListener = new SmoothMarker.SmoothMarkerMoveListener() {
        @Override
        public void move(final double v, final RoutePoint currentPoint, final RoutePoint nextPoint) {

            runOnUiThread(new Runnable() {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void run() {
                    try {
                        if (playData == null || playData.size() < 3) {
                            return;
                        }

                        if (smoothMoveMarker != null) {
                            int process = (processPosition + smoothMoveMarker.getIndex()) * 100 / playData.size();
                            seekbarProcess.setProgress(process);
                        }

                        if (!isMakeAllVisual) {
                            //如果有图标没有显示出来,让所有图标都显示
                            allMarkerVisual();
                        }

                        mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentPoint.getPoint().latitude, currentPoint.getPoint().longitude), mZoom));
                        if (isGetSuccess) {
                            isGetSuccess = false;
                            setAddress(currentPoint);
                        }
                        tvLocationTime.setText(getString(R.string.txt_time) + DateUtils.timeConversionDate_two(String.valueOf(currentPoint.getTime())));
                        tvSpeed.setText(getString(R.string.txt_speed) + ((double) currentPoint.getSpeed() / 10) + "km/h");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    /**
     * 播放完成回调
     */
    private Play play = new Play() {
        @Override
        public void complete(int state) {
            if (state == 0) {
                handler.sendEmptyMessage(0x0100);
                seekbarProcess.setProgress(100);
                isPauseNow = false;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x0100) {
                playIndex = playData.size() - 1;
                onSeekBarInfoShow();
                ivPlay.setImageResource(R.mipmap.icon_track_play);
                //txtPlay.setText(getString(R.string.txt_track_play));
            } else if (msg.what == 0x0200) {
                updateBaseStationSwitch();
                updateWifiStationSwitch();
            }
        }
    };

    /**
     * 显示对于的图标
     *
     * @return
     */
    private int drawableId() {
        int drawable =  R.mipmap.icon_car_online;
        switch (carImageId) {
            case 0:
                drawable =  R.mipmap.icon_car_online;
                break;
            case 1:
                drawable = R.mipmap.icon_car_online_1;
                break;
            case 2:
                drawable = R.mipmap.icon_car_online_2;
                break;
            case 3:
                drawable = R.mipmap.icon_car_online_3;
                break;
            case 4:
                drawable = R.mipmap.icon_car_online_4;
                break;
        }
        return drawable;
    }

}

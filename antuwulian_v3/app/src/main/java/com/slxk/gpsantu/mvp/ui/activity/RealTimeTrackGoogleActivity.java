package com.slxk.gpsantu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.slxk.gpsantu.app.MyApplication;
import com.slxk.gpsantu.di.component.DaggerRealTimeTrackGoogleComponent;
import com.slxk.gpsantu.mvp.contract.RealTimeTrackGoogleContract;
import com.slxk.gpsantu.mvp.model.api.ModuleValueService;
import com.slxk.gpsantu.mvp.model.bean.DeviceListResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceLocationInfoBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceListPutBean;
import com.slxk.gpsantu.mvp.model.putbean.RealTimeTrackPutBean;
import com.slxk.gpsantu.mvp.presenter.RealTimeTrackGooglePresenter;

import com.slxk.gpsantu.R;
import com.slxk.gpsantu.mvp.utils.BitmapHelperGoogle;
import com.slxk.gpsantu.mvp.utils.DateUtils;
import com.slxk.gpsantu.mvp.utils.DeviceUtils;
import com.slxk.gpsantu.mvp.utils.FunctionType;
import com.slxk.gpsantu.mvp.utils.GoogleLocationUtils;
import com.slxk.gpsantu.mvp.utils.GoogleMapUtils;
import com.slxk.gpsantu.mvp.utils.GpsUtils;
import com.slxk.gpsantu.mvp.utils.ResultDataUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.slxk.gpsantu.mvp.weiget.RealTimeTrackInputDialog;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/09/2021 17:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RealTimeTrackGoogleActivity extends BaseActivity<RealTimeTrackGooglePresenter> implements RealTimeTrackGoogleContract.View,
        OnMapReadyCallback, GoogleMap.OnCameraChangeListener, GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter {

    private GoogleMap mGoogleMap; // 谷歌地图
    private Marker carMarker;
    private Marker myLocationMarker;
    private boolean firstzoom = true;
    private float mZoom = 16;
    private LatLng myLocation; // 我的位置
    // 设置一个默认经纬度，避免没有定位数据的设备登录，导致程序崩溃
    private LatLng centerPoint = new LatLng(39.90923, 116.397428);

    private ScheduledExecutorService scheduledThreadPool;
    private LatLng realLocation;

    private LatLngBounds.Builder bounds;
    private List<String> mSimeiLists;//实时请求imei号

    //绘制轨迹线
    public List<LatLng> followLat = new ArrayList<>();
    private Polyline polyline;

    private long mImei;
    private String mSimei; // 设备imei号
    private String deviceNumber; // 设备名称
    private String deviceState; // 设备状态
    private String latAndLon = ""; // 设备经纬度
    private String deviceVersion; // 设备版本
    private int carImageId; // 设备图标id

    private BitmapHelperGoogle bitmapHelperGoogle;

    private int intervalTime = 5; // 跟踪控制的设备上报时间间隔 0-取消追踪，单位s
    private int effectiveTime = 1; // 位置跟踪有效期，单位min
    private int limitSizeForDevice = 10; // 最大获取设备列表数量限制
    private double mLatitude = 0; // 设备的经纬度
    private double mLongitude = 0; // 设备的经纬度
    private int mCountDown = 0; // 实时追踪倒计时指令
    private RealTimeTrackInputDialog trackInputDialog; // 时间填写dialog
    private boolean isFirstData = true; // 是否是第一次下发实时追踪指令
    private Handler mHandler;

    private static class MapInfoWindow {
        private View viewInfoWindows;
        private TextView tvDeviceName; // 设备名称
        private TextView tvLocationType; // 设备状态
        private TextView tvLocationTime; // 定位时间
    }
    private MapInfoWindow mapInfoWindow = new MapInfoWindow();
    private boolean isShowInfoWindow = false; // 是否有选中设备，显示详细信息悬浮框
    private Marker mMarker; // 选中的设备
    private String locationType = ""; // 定位方式
    private String locationTime = ""; // 定位时间

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), RealTimeTrackGoogleActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRealTimeTrackGoogleComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_real_time_track_google;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_realtime_tracking));
        carImageId = MyApplication.getMyApp().getCarImageId();
        mHandler = new Handler();
        mImei = MyApplication.getMyApp().getImei();
        mSimei = MyApplication.getMyApp().getSimei();
        deviceState = MyApplication.getMyApp().getDeviceState();
        latAndLon = MyApplication.getMyApp().getLatAndLon();
        deviceVersion = MyApplication.getMyApp().getVersion();
        mSimeiLists = new ArrayList<>();
        if (!TextUtils.isEmpty(mSimei)) {
            mSimeiLists.add(mSimei);
        }
        if (TextUtils.isEmpty(MyApplication.getMyApp().getCarName())) {
            deviceNumber = String.valueOf(MyApplication.getMyApp().getImei());
        } else {
            deviceNumber = MyApplication.getMyApp().getCarName();
        }
        onLoadInfoWindow();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }


    /**
     * 初始化气泡框
     */
    private void onLoadInfoWindow() {
        mapInfoWindow.viewInfoWindows = getLayoutInflater().inflate(R.layout.layout_real_time_track_window, null);
        mapInfoWindow.tvDeviceName = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_device_number);
        mapInfoWindow.tvLocationType = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_location_type);
        mapInfoWindow.tvLocationTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_location_time);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);//选择手势
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);//缩放按钮
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.setOnMyLocationClickListener(this);
        mGoogleMap.setOnCameraChangeListener(this);
        mGoogleMap.setInfoWindowAdapter(this);
        mGoogleMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        mGoogleMap.setOnMapClickListener(this);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(centerPoint)      // Sets the center of the map to Mountain View
                .zoom(mZoom)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                  // Sets the tilt of the camera to 30 degrees
                .build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        initViews();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocation();
            }
        }, 500);
    }


    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return mapInfoWindow.viewInfoWindows;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (isShowInfoWindow) {
            isShowInfoWindow = false;
            if (mMarker != null) {
                mMarker.hideInfoWindow();
            }
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if (mMarker != null) {
            mMarker.hideInfoWindow();
        }
        if (!TextUtils.isEmpty(marker.getTitle())) {
            if (marker.getTitle().equals(mImei + "")){
                mMarker = marker;
                isShowInfoWindow = true;
                setDeviceDetailData();
            }
        }
        return false;
    }

    /**
     * 显示悬浮框信息
     */
    @SuppressLint("SetTextI18n")
    private void setDeviceDetailData() {
        mapInfoWindow.tvDeviceName.setText(getString(R.string.txt_name) + deviceNumber);
        mapInfoWindow.tvLocationType.setText(getString(R.string.txt_location_type) + locationType);
        mapInfoWindow.tvLocationTime.setText(getString(R.string.txt_location_time) + locationTime);

        if (isShowInfoWindow) {
            mMarker.showInfoWindow();
        } else {
            mMarker.hideInfoWindow();
        }
    }

    private void initViews() {
        bitmapHelperGoogle = new BitmapHelperGoogle(this);
        bounds = new LatLngBounds.Builder();
        if (!TextUtils.isEmpty(latAndLon)) {
            String[] location = latAndLon.split(",");
            mLatitude = (double) Long.parseLong(location[0]) / 1000000;
            mLongitude = (double) Long.parseLong(location[1]) / 1000000;
            realLocation = GpsUtils.googleGPSConverter(mLatitude, mLongitude);
            updateMapDisplay(realLocation, 0);
        }

        if (!TextUtils.isEmpty(deviceVersion)) {
            if (deviceVersion.toUpperCase().contains(FunctionType.C2)) {
                effectiveTime = 1;
                mCountDown = effectiveTime * 60;
            } else {
                onInputTime();
            }
        } else {
            onInputTime();
        }
    }

    /**
     * 输入追踪时长
     */
    private void onInputTime() {
        trackInputDialog = new RealTimeTrackInputDialog();
        trackInputDialog.show(getSupportFragmentManager(), new RealTimeTrackInputDialog.onRealTimeTrackChange() {
            @Override
            public void onRealTimeTrack(int time) {
                effectiveTime = time;
                mCountDown = effectiveTime * 60;
                submitTempTracking();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }


    /**
     * 周期线程池，刷新定位数据，下发定位指令
     */
    public void getCycleData() {
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdownNow();
        }
        scheduledThreadPool = Executors.newScheduledThreadPool(5);
        // 定时5s执行一次
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 5, TimeUnit.SECONDS);
        if (deviceVersion != null && deviceVersion.toUpperCase().contains(FunctionType.C2)) {
            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    handler.sendEmptyMessage(3);
                }
            }, 0, 60, TimeUnit.SECONDS);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                refreshDeviceLocation();
            } else if (msg.what == 2) {
                mCountDown--;
                if (mCountDown <= 0) {
                    finish();
                } else {
                    handler.sendEmptyMessageDelayed(2, 1000);
                }
            } else if (msg.what == 3) {
                submitTempTracking();
            }
        }
    };

    /**
     * 下发实时追踪指令
     */
    private void submitTempTracking() {
        RealTimeTrackPutBean.ParamsBean paramsBean = new RealTimeTrackPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setEffective_time(effectiveTime);
        paramsBean.setInterval_time(intervalTime);
        paramsBean.setIs_add_log(isFirstData);

        RealTimeTrackPutBean bean = new RealTimeTrackPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Real_Time_Track);
        bean.setModule(ModuleValueService.Module_For_Real_Time_Track);

        if (getPresenter() != null) {
            getPresenter().submitTempTracking(bean);
        }
    }

    /**
     * 刷新定位数据
     */
    private void refreshDeviceLocation() {
        DeviceListPutBean.ParamsBean paramsBean = new DeviceListPutBean.ParamsBean();
        paramsBean.setLimit_size(limitSizeForDevice);
        if (mSimeiLists.size() > 0) {
            paramsBean.setSimei(mSimeiLists);
        }
        DeviceListPutBean bean = new DeviceListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_List);
        bean.setModule(ModuleValueService.Module_For_Device_List);
        bean.setParams(paramsBean);

        getPresenter().getDeviceList(bean);
    }

    /**
     * 刷新地图界面
     */
    private void updateMapDisplay(LatLng latLngs, float direction) {
        if (mGoogleMap != null) {
            if (carMarker != null) {
                carMarker.setPosition(latLngs);
                carMarker.setIcon(getMarkerIcon(direction));
            } else {
                carMarker = mGoogleMap.addMarker(new MarkerOptions().anchor(0.5f, 0.7f)
                        .title(mImei + "")
                        .position(latLngs).icon(getMarkerIcon(direction)));
            }
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs, mZoom));
            followLat.add(latLngs);
            drawPolyline();
        }
    }

    /**
     * 绘制设备图标等信息
     *
     * @return
     */
    private BitmapDescriptor getMarkerIcon(float direction) {
        View view = View.inflate(this, R.layout.layout_marker_item, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        ImageView ivCar = view.findViewById(R.id.iv_car);
        tvName.setText(deviceNumber);
        ivCar.setImageResource(drawableId());
        if (carImageId == 0 || carImageId == 1 || carImageId == 2) {
            ivCar.setRotation(direction);//旋转
        }

        getDeviceState(tvName);
        return convertViewToBitmap(view);
    }

    private static BitmapDescriptor convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return BitmapDescriptorFactory.fromBitmap(view.getDrawingCache());
    }

    private void getDeviceState(TextView tvView) {
        if (deviceState.equals(ResultDataUtils.Device_State_Line_On)) {
            tvView.setTextColor(getResources().getColor(R.color.color_73CA6C));
            tvView.setBackgroundResource(R.mipmap.icon_online_bg);
        } else if (deviceState.equals(ResultDataUtils.Device_State_Line_Sleep)) {
            tvView.setTextColor(getResources().getColor(R.color.color_4592EB));
            tvView.setBackgroundResource(R.mipmap.icon_static_bg);
        } else {
            tvView.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            tvView.setBackgroundResource(R.mipmap.icon_offline_bg);
        }
    }

    /**
     * 缩放地图
     */
    private void zoomMap(LatLng peoLatLng, LatLng carLatLng) {
        bounds.include(peoLatLng);
        bounds.include(carLatLng);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 200));
    }

    /**
     * 绘制关注线
     */
    public void drawPolyline() {
        int color = Color.rgb(50, 205, 50);
        if (followLat != null && followLat.size() >= 2) {
            if (polyline == null) {
                polyline = mGoogleMap.addPolyline(new PolylineOptions().addAll(followLat).width(8).color(color));
            } else {
                polyline.setPoints(followLat);
            }
        }
    }

    /**
     * 取消关注
     */
    public void cancelFollow() {
        followLat.clear();
        if (polyline != null) {
            polyline.remove();
        }
    }

    @Override
    protected void onResume() {
        getCycleData();
        if (!deviceVersion.toUpperCase().contains(FunctionType.C2)) {
            if (mCountDown > 0) {
                handler.sendEmptyMessage(2);
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        //关闭定时线程池
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdownNow();
        }
        scheduledThreadPool = null;

        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;

        cancelFollow();
        //关闭定时线程池
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdownNow();
        }
        scheduledThreadPool = null;

        if (trackInputDialog != null && trackInputDialog.isAdded()) {
            trackInputDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void submitTempTrackingSuccess(BaseBean baseBean) {
        if (isFirstData) {
            ToastUtils.showShort(getString(R.string.txt_set_success));
        }
        isFirstData = false;
        if (!deviceVersion.toUpperCase().contains(FunctionType.C2)) {
            handler.sendEmptyMessage(2);
//            getCycleData();
        }
        if (trackInputDialog != null && trackInputDialog.isAdded()) {
            trackInputDialog.dismiss();
        }
    }

    @Override
    public void getDeviceListSuccess(DeviceListResultBean deviceListResultBean) {
        if (deviceListResultBean.getItems() != null && deviceListResultBean.getItems().size() > 0) {
            DeviceListResultBean.ItemsBean itemsBean = deviceListResultBean.getItems().get(0);
            MyApplication.getMyApp().setSimei(itemsBean.getSimei());
            MyApplication.getMyApp().setLocInfo(itemsBean.getLast_pos());
            MyApplication.getMyApp().setDeviceState(itemsBean.getState());
            MyApplication.getMyApp().setImei(itemsBean.getImei());
            MyApplication.getMyApp().setPower(itemsBean.getPower());
            MyApplication.getMyApp().setVersion(itemsBean.getVersion());
            DeviceLocationInfoBean bean = DeviceUtils.parseDeviceData(itemsBean.getLast_pos());
            MyApplication.getMyApp().setLatAndLon(bean.getLat() + "," + bean.getLon());
            locationType = ResultDataUtils.onShowLocationType(this, bean.getType());
            locationTime = DateUtils.timeConversionDate_two(String.valueOf(bean.getTime()));

            if (TextUtils.isEmpty(itemsBean.getCar_number())) {
                deviceNumber = String.valueOf(itemsBean.getImei());
            } else {
                deviceNumber = itemsBean.getCar_number();
            }
            deviceVersion = itemsBean.getVersion();
            mLatitude = (double) bean.getLat() / 1000000;
            mLongitude = (double) bean.getLon() / 1000000;
            deviceState = itemsBean.getState();
            realLocation =  GpsUtils.googleGPSConverter(mLatitude, mLongitude);
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(itemsBean.getLast_pos());
            updateMapDisplay(realLocation, infoBean.getDirection());

            if (isShowInfoWindow){
                setDeviceDetailData();
            }
        }
    }

    /**
     * 显示对于的图标
     *
     * @return
     */
    private int drawableId() {
        int drawable = R.mipmap.icon_car_online;
        if (deviceState.equals(ResultDataUtils.Device_State_Line_On)) {
            switch (carImageId) {
                case 0:
                    drawable =  R.mipmap.icon_car_online;
                    break;
                case 1:
                    drawable = R.mipmap.icon_car_online_1;
                    break;
                case 2:
                    drawable =  R.mipmap.icon_car_online_2;
                    break;
                case 3:
                    drawable =  R.mipmap.icon_car_online_3;
                    break;
                case 4:
                    drawable =  R.mipmap.icon_car_online_4;
                    break;
            }
        } else if (deviceState.equals(ResultDataUtils.Device_State_Line_Sleep)) {
            switch (carImageId) {
                case 0:
                    drawable = R.mipmap.icon_car_sleep;
                    break;
                case 1:
                    drawable = R.mipmap.icon_car_sleep_1;
                    break;
                case 2:
                    drawable = R.mipmap.icon_car_sleep_2;
                    break;
                case 3:
                    drawable = R.mipmap.icon_car_sleep_3;
                    break;
                case 4:
                    drawable = R.mipmap.icon_car_sleep_4;
                    break;
            }
        } else {
            switch (carImageId) {
                case 0:
                    drawable = R.mipmap.icon_car_offline;
                    break;
                case 1:
                    drawable = R.mipmap.icon_car_offline_1;
                    break;
                case 2:
                    drawable = R.mipmap.icon_car_offline_2;
                    break;
                case 3:
                    drawable = R.mipmap.icon_car_offline_3;
                    break;
                case 4:
                    drawable = R.mipmap.icon_car_offline_4;
                    break;
                case 5:
                    drawable = R.mipmap.icon_car_offline_5;
                    break;
                case 6:
                    drawable = R.mipmap.icon_car_offline_6;
                    break;
                case 7:
                    drawable = R.mipmap.icon_car_offline_7;
                    break;
                case 8:
                    drawable = R.mipmap.icon_car_offline_8;
                    break;
                case 9:
                    drawable = R.mipmap.icon_car_offline_9;
                    break;
            }
        }
        return drawable;
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
    public void onCameraChange(@NonNull CameraPosition cameraPosition) {
        mZoom = cameraPosition.zoom;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if (myLocationMarker != null) {
            myLocationMarker.setPosition(myLocation);
        }
    }

    /**
     * 定位
     */
    public void getLocation() {
        String ll = GoogleLocationUtils.getLocation(this);
        if (!TextUtils.isEmpty(ll)) {
            String[] location = ll.split(",");
            myLocation = GoogleMapUtils.getGoogleLocation(location[0], location[1]);
            if (myLocation != null) {
                addMyLocalMaker(myLocation);
            }
        }
    }

    /**
     * 将我的位置的坐标添加进去
     *
     * @param location 我的位置经纬度
     */
    private void addMyLocalMaker(LatLng location) {
        if (location != null && location.latitude != 0 && location.longitude != 0 && mGoogleMap != null) {
            if (myLocationMarker != null) {
                myLocationMarker.setPosition(location);
            } else {
                myLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(location)
                        .icon(bitmapHelperGoogle.getBitmapZoomForUserLocation(mZoom)));
            }
            if (myLocation != null && realLocation != null && firstzoom) {
                zoomMap(myLocation, realLocation);
                firstzoom = false;
            }
        }
    }

}

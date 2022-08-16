package com.slxk.gpsantu.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.slxk.gpsantu.mvp.contract.LocationModeYiWenContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.bean.DeviceDetailResultBean;
import com.slxk.gpsantu.mvp.model.bean.LocationModeGetResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceBatchCmdPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceModeSetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LocationModeGetPutBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
@ActivityScope
public class LocationModeYiWenPresenter extends BasePresenter<LocationModeYiWenContract.Model, LocationModeYiWenContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LocationModeYiWenPresenter(LocationModeYiWenContract.Model model, LocationModeYiWenContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取定位模式
     * @param bean
     */
    public void getLocationMode(LocationModeGetPutBean bean){
        mModel.getLocationMode(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LocationModeGetResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(LocationModeGetResultBean locationModeGetResultBean) {
                        if (locationModeGetResultBean.isSuccess()){
                            mRootView.getLocationModeSuccess(locationModeGetResultBean);
                        }
                    }
                });
    }

    /**
     * 设置定位模式
     * @param bean
     */
    public void submitLocationMode(DeviceModeSetPutBean bean){
        mModel.submitLocationMode(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isSuccess()){
                            mRootView.submitLocationModeSuccess(baseBean, bean);
                        }
                    }
                });
    }

    /**
     * 获取设备详情
     * @param bean
     */
    public void getDeviceDetailInfo(DeviceDetailPutBean bean){
        mModel.getDeviceDetailInfo(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceDetailResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceDetailResultBean deviceDetailResultBean) {
                        if (deviceDetailResultBean.isSuccess()){
                            mRootView.getDeviceDetailInfoSuccess(deviceDetailResultBean);
                        }else if (deviceDetailResultBean.getErrcode() == Api.Device_Freeze || deviceDetailResultBean.getErrcode() == Api.Data_Change){
                            mRootView.getDeviceDetailError();
                        }
                    }
                });
    }

    /**
     * 设置定位优先级
     * @param bean
     */
    public void setDevicePriority(DeviceBatchCmdPutBean bean){
        mModel.setDevicePriority(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isSuccess()){
                            mRootView.setDevicePriority(baseBean);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}

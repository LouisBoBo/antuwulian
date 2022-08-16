package com.slxk.gpsantu.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.slxk.gpsantu.mvp.contract.LocationModeContract;
import com.slxk.gpsantu.mvp.model.bean.LocationModeGetResultBean;
import com.slxk.gpsantu.mvp.model.bean.LoopLocationModeResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceModeSetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LocationModeGetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LoopLocationModePutBean;
import com.slxk.gpsantu.mvp.model.putbean.LoopModeModifyPutBean;

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
public class LocationModePresenter extends BasePresenter<LocationModeContract.Model, LocationModeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LocationModePresenter (LocationModeContract.Model model, LocationModeContract.View rootView) {
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
     * 获取周期定位列表
     *
     * @param bean
     */
    public void getLoopLocationMode(LoopLocationModePutBean bean) {
        mModel.getLoopLocationMode(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {

                    mRootView.showLoading();

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    mRootView.hideLoading();

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LoopLocationModeResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(LoopLocationModeResultBean loopLocationModeResultBean) {
                        mRootView.getLoopLocationModeSuccess(loopLocationModeResultBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }


    /**
     * 修改周期定位
     *
     * @param bean
     */
    public void submitLoopModify(LoopModeModifyPutBean bean) {
        mModel.submitLoopModify(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isSuccess()) {
                            mRootView.submitLoopModifySuccess(baseBean);
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

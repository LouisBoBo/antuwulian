package com.slxk.gpsantu.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.slxk.gpsantu.mvp.contract.AlarmMessageUserContract;
import com.slxk.gpsantu.mvp.model.bean.AlarmRecordResultBean;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeleteBatchPutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmRecordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FindDevicePutBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 09:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class AlarmMessageUserPresenter extends BasePresenter<AlarmMessageUserContract.Model, AlarmMessageUserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AlarmMessageUserPresenter(AlarmMessageUserContract.Model model, AlarmMessageUserContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取报警消息列表
     * @param bean
     */
    public void getAlarmRecord(AlarmRecordPutBean bean, boolean isShow, boolean isRefresh){
        mModel.getAlarmRecord(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    if (isShow){
//                        mRootView.showLoading();
//                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    if (isShow){
//                        mRootView.hideLoading();
//                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<AlarmRecordResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(AlarmRecordResultBean alarmRecordResultBean) {
                        if (!isShow && isRefresh){
                            mRootView.finishRefresh();
                        }
                        if (isShow){
                            mRootView.onDismissProgress();
                        }
                        if (alarmRecordResultBean.isSuccess()){
                            mRootView.getAlarmRecordSuccess(alarmRecordResultBean, isRefresh);
                            if (alarmRecordResultBean.getItems() != null){
                                if (alarmRecordResultBean.getItems().size() == 0 || alarmRecordResultBean.getItems().size() < bean.getParams().getLimit_size()) {
                                    mRootView.endLoadMore(0);//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore(0);
                                }else{
                                    mRootView.endLoadMore(0);
                                }
                            }else{
                                mRootView.endLoadMore(0);//隐藏上拉加载更多的进度条
                                mRootView.setNoMore(0);
                            }
                        }else{
                            mRootView.endLoadFail(0);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.endLoadFail(0);
                        if (isShow){
                            mRootView.onDismissProgress();
                        }
                    }

                });
    }

    /**
     * 删除报警消息
     * @param bean
     */
    public void submitAlarmDelete(AlarmDeletePutBean bean){
        mModel.submitAlarmDelete(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mRootView.onDismissProgress();
                        if (baseBean.isSuccess()){
                            mRootView.submitAlarmDeleteSuccess(baseBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onDismissProgress();
                    }
                });
    }

    /**
     * 批量删除报警消息
     * @param bean
     */
    public void submitAlarmDeleteBatch(AlarmDeleteBatchPutBean bean){
        mModel.submitAlarmDeleteBatch(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mRootView.onDismissProgress();
                        if (baseBean.isSuccess()){
                            mRootView.submitAlarmDeleteBatchSuccess(baseBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onDismissProgress();
                    }
                });
    }

    /**
     * 模糊，精准搜索设备
     * @param bean
     * @return
     */
    public void getFindDeviceData(FindDevicePutBean bean){
        mModel.getFindDeviceData(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<FindDeviceResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(FindDeviceResultBean findDeviceResultBean) {
                        if (findDeviceResultBean.isSuccess()){
                            mRootView.getFindDeviceDataSuccess(findDeviceResultBean);
                            if (findDeviceResultBean.getItems() != null){
                                if (findDeviceResultBean.getItems().size() == 0 || findDeviceResultBean.getItems().size() < bean.getParams().getLimit_size()) {
                                    mRootView.endLoadMore(1);//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore(1);
                                }else{
                                    mRootView.endLoadMore(1);
                                }
                            }else{
                                mRootView.endLoadMore(1);//隐藏上拉加载更多的进度条
                                mRootView.setNoMore(1);
                            }
                        }else{
                            mRootView.endLoadFail(1);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.endLoadFail(1);
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

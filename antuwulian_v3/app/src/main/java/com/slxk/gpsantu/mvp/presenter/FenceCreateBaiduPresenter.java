package com.slxk.gpsantu.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.slxk.gpsantu.mvp.contract.FenceCreateBaiduContract;
import com.slxk.gpsantu.mvp.model.bean.FenceAddResultBean;
import com.slxk.gpsantu.mvp.model.bean.FenceResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.FenceAddPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FenceListPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FenceModifyPutBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/23/2021 16:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FenceCreateBaiduPresenter extends BasePresenter<FenceCreateBaiduContract.Model, FenceCreateBaiduContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FenceCreateBaiduPresenter(FenceCreateBaiduContract.Model model, FenceCreateBaiduContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取围栏列表数据
     * @param bean 参数
     */
    public void getFenceList(FenceListPutBean bean){
        mModel.getFenceList(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<FenceResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(FenceResultBean fenceResultBean) {
                        if (fenceResultBean.isSuccess()){
                            mRootView.getFenceListSuccess(fenceResultBean);
                        }
                    }
                });
    }

    /**
     * 添加围栏
     * @param bean
     */
    public void submitFenceAdd(FenceAddPutBean bean){
        mModel.submitFenceAdd(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<FenceAddResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(FenceAddResultBean fenceAddResultBean) {
                        if (fenceAddResultBean.isSuccess()){
                            mRootView.submitFenceAddSuccess(fenceAddResultBean);
                        }
                    }
                });
    }

    /**
     * 修改围栏
     * @param bean
     */
    public void submitFenceModify(FenceModifyPutBean bean){
        mModel.submitFenceModify(bean)
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
                        if (baseBean.isSuccess()){
                            mRootView.submitFenceModifySuccess(baseBean);
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

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
import com.slxk.gpsantu.mvp.contract.UserSwitchContract;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;



/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/10/2021 17:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserSwitchPresenter extends BasePresenter<UserSwitchContract.Model, UserSwitchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserSwitchPresenter(UserSwitchContract.Model model, UserSwitchContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取车组织列表和车组列表
     *
     * @param bean
     */
    public void getDeviceGroupList(DeviceGroupPutBean bean, boolean isRefresh, int level) {
        mModel.getDeviceGroupList(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceGroupResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceGroupResultBean deviceGroupResultBean) {
                        if (isRefresh) {
                            mRootView.finishRefresh();
                        }
                        if (deviceGroupResultBean.isSuccess()) {
                            if (deviceGroupResultBean.getFamilys() != null) {
                                mRootView.getFamilyListSuccess(deviceGroupResultBean, isRefresh, bean.getParams().getFamilyid(), level);
                                if (deviceGroupResultBean.getFamilys().size() == 0 || deviceGroupResultBean.getFamilys().size() < bean.getParams().getF_limit_size()) {
                                    mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore();
                                } else {
                                    mRootView.endLoadMore();
                                }
                            } else {
                                if (level == 0) { //只剩下最顶级的时候 需要返回状态
                                    mRootView.getFamilyListSuccess(null, isRefresh, bean.getParams().getFamilyid(), 0);
                                }
                                mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                mRootView.setNoMore();
                            }
                        } else {
                            mRootView.endLoadFail();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.endLoadFail();
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

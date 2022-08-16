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
import com.slxk.gpsantu.mvp.contract.DeviceAllManagementContract;
import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.bean.DeviceBaseResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.bean.GroupAddResultBean;
import com.slxk.gpsantu.mvp.model.bean.TaskProgressResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FreezeEquipmentPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupAddPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupEditPutBean;
import com.slxk.gpsantu.mvp.model.putbean.RemoveDevicePutBean;
import com.slxk.gpsantu.mvp.model.putbean.TaskProgressPubBean;
import com.slxk.gpsantu.mvp.model.putbean.TransferDevicePutBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2021 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DeviceAllManagementPresenter extends BasePresenter<DeviceAllManagementContract.Model, DeviceAllManagementContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DeviceAllManagementPresenter (DeviceAllManagementContract.Model model, DeviceAllManagementContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取车组织列表和车组列表
     * @param bean
     */
    public void getDeviceGroupList(DeviceGroupPutBean bean, boolean isShow, boolean isRefresh){
        mModel.getDeviceGroupList(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (isShow){
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (isShow){
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceGroupResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceGroupResultBean deviceGroupResultBean) {
                        if (isRefresh){
                            mRootView.finishRefresh();
                        }
                        if (deviceGroupResultBean.isSuccess()){
                            mRootView.getDeviceGroupListSuccess(deviceGroupResultBean, isRefresh);
                            if (deviceGroupResultBean.getGarages() != null){
                                if (deviceGroupResultBean.getGarages().size() == 0 || deviceGroupResultBean.getGarages().size() < bean.getParams().getG_limit_size()) {
                                    mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore();
                                }else{
                                    mRootView.endLoadMore();
                                }
                            }else{
                                mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                mRootView.setNoMore();
                            }
                        }else{
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

    /**
     * 添加分组
     * @param bean
     */
    public void submitGroupAdd(GroupAddPutBean bean){
        mModel.submitGroupAdd(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GroupAddResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(GroupAddResultBean groupAddResultBean) {
                        if (groupAddResultBean.isSuccess()){
                            mRootView.submitGroupAddSuccess(groupAddResultBean);
                        }
                    }
                });
    }

    /**
     * 编辑分组名称
     * @param bean
     */
    public void submitGroupEdit(GroupEditPutBean bean){
        mModel.submitGroupEdit(bean)
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
                            mRootView.submitGroupEditSuccess(baseBean, bean.getParams().getGroup_name(), bean.getParams().getGid());
                        }else if (baseBean.getErrcode() == Api.Device_Freeze || baseBean.getErrcode() == Api.Data_Change){
                            mRootView.onRefreshData();
                        }
                    }
                });
    }

    /**
     * 转移设备
     * @param bean
     */
    public void submitTransferDevice(TransferDevicePutBean bean){
        mModel.submitTransferDevice(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceBaseResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceBaseResultBean transferDeviceResultBean) {
                        if (transferDeviceResultBean.isSuccess()){
                            mRootView.submitTransferDeviceSuccess(transferDeviceResultBean);
                        }else if (transferDeviceResultBean.getErrcode() == Api.Device_Freeze || transferDeviceResultBean.getErrcode() == Api.Data_Change){
                            mRootView.onRefreshData();
                        }
                    }
                });
    }

    /**
     * 删除设备
     * @param bean
     */
    public void submitDeleteDevice(RemoveDevicePutBean bean){
        mModel.submitDeleteDevice(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceBaseResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceBaseResultBean deviceBaseResultBean) {
                        if (deviceBaseResultBean.isSuccess()){
                            mRootView.submitDeleteDeviceSuccess(deviceBaseResultBean);
                        }else if (deviceBaseResultBean.getErrcode() == Api.Device_Freeze || deviceBaseResultBean.getErrcode() == Api.Data_Change){
                            mRootView.onRefreshData();
                        }
                    }
                });
    }

    /**
     * 删除分组
     * @param bean
     */
    public void submitDeleteGroup(GroupDeletePutBean bean){
        mModel.submitDeleteGroup(bean)
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
                            mRootView.submitDeleteGroupSuccess(baseBean);
                        }
                    }
                });
    }

    /**
     * 冻结设备
     * @param bean
     */
    public void submitFreezeEquipment(FreezeEquipmentPutBean bean){
        mModel.submitFreezeEquipment(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceBaseResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceBaseResultBean deviceBaseResultBean) {
                        if (deviceBaseResultBean.isSuccess()){
                            mRootView.submitFreezeEquipmentSuccess(deviceBaseResultBean);
                        }
                    }
                });
    }

    /**
     * 获取任务进度
     * @param bean
     */
    public void getTaskProgress(TaskProgressPubBean bean){
        mModel.getTaskProgress(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TaskProgressResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(TaskProgressResultBean taskProgressResultBean) {
                        if (taskProgressResultBean.isSuccess()){
                            mRootView.getTaskProgressSuccess(taskProgressResultBean);
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

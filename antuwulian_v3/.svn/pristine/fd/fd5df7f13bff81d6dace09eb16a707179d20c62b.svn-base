package com.slxk.gpsantu.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.slxk.gpsantu.mvp.contract.DeviceAllManagementContract;
import com.slxk.gpsantu.mvp.model.api.service.DeviceService;
import com.slxk.gpsantu.mvp.model.api.service.PublicService;
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
import com.slxk.gpsantu.mvp.utils.ConstantValue;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;


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
public class DeviceAllManagementModel extends BaseModel implements DeviceAllManagementContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DeviceAllManagementModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<DeviceGroupResultBean> getDeviceGroupList(DeviceGroupPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceGroupList(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceGroupResultBean>, ObservableSource<DeviceGroupResultBean>>() {
                    @Override
                    public ObservableSource<DeviceGroupResultBean> apply(Observable<DeviceGroupResultBean> deviceGroupResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceGroupList(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<GroupAddResultBean> submitGroupAdd(GroupAddPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitGroupAdd(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<GroupAddResultBean>, ObservableSource<GroupAddResultBean>>() {
                    @Override
                    public ObservableSource<GroupAddResultBean> apply(Observable<GroupAddResultBean> groupAddResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitGroupAdd(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitGroupEdit(GroupEditPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitGroupEdit(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitGroupEdit(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceBaseResultBean> submitTransferDevice(TransferDevicePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitTransferDevice(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceBaseResultBean>, ObservableSource<DeviceBaseResultBean>>() {
                    @Override
                    public ObservableSource<DeviceBaseResultBean> apply(Observable<DeviceBaseResultBean> transferDeviceResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitTransferDevice(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceBaseResultBean> submitDeleteDevice(RemoveDevicePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitRemoveDelete(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceBaseResultBean>, ObservableSource<DeviceBaseResultBean>>() {
                    @Override
                    public ObservableSource<DeviceBaseResultBean> apply(Observable<DeviceBaseResultBean> deleteDeviceResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitRemoveDelete(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitDeleteGroup(GroupDeletePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitDeleteGroup(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitDeleteGroup(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceBaseResultBean> submitFreezeEquipment(FreezeEquipmentPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitFreezeEquipment(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceBaseResultBean>, ObservableSource<DeviceBaseResultBean>>() {
                    @Override
                    public ObservableSource<DeviceBaseResultBean> apply(Observable<DeviceBaseResultBean> freezeEquipmentResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitFreezeEquipment(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<TaskProgressResultBean> getTaskProgress(TaskProgressPubBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).getTaskProgress(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<TaskProgressResultBean>, ObservableSource<TaskProgressResultBean>>() {
                    @Override
                    public ObservableSource<TaskProgressResultBean> apply(Observable<TaskProgressResultBean> taskProgressResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).getTaskProgress(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}
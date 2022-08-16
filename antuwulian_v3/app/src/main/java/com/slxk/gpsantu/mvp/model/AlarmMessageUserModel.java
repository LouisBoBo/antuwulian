package com.slxk.gpsantu.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.slxk.gpsantu.mvp.contract.AlarmMessageUserContract;
import com.slxk.gpsantu.mvp.model.api.service.AlarmService;
import com.slxk.gpsantu.mvp.model.api.service.DeviceService;
import com.slxk.gpsantu.mvp.model.bean.AlarmRecordResultBean;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeleteBatchPutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmRecordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FindDevicePutBean;
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
 * Created by MVPArmsTemplate on 10/29/2020 09:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class AlarmMessageUserModel extends BaseModel implements AlarmMessageUserContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AlarmMessageUserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<AlarmRecordResultBean> getAlarmRecord(AlarmRecordPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AlarmService.class).getAlarmRecord(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<AlarmRecordResultBean>, ObservableSource<AlarmRecordResultBean>>() {
                    @Override
                    public ObservableSource<AlarmRecordResultBean> apply(Observable<AlarmRecordResultBean> alarmRecordResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AlarmService.class).getAlarmRecord(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitAlarmDelete(AlarmDeletePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AlarmService.class).submitAlarmDelete(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AlarmService.class).submitAlarmDelete(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitAlarmDeleteBatch(AlarmDeleteBatchPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AlarmService.class).submitAlarmDeleteBatch(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AlarmService.class).submitAlarmDeleteBatch(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<FindDeviceResultBean> getFindDeviceData(FindDevicePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).getFindDeviceData(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<FindDeviceResultBean>, ObservableSource<FindDeviceResultBean>>() {
                    @Override
                    public ObservableSource<FindDeviceResultBean> apply(Observable<FindDeviceResultBean> findDeviceResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).getFindDeviceData(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}
package com.slxk.gpsantu.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.slxk.gpsantu.mvp.contract.LocationModeContract;
import com.slxk.gpsantu.mvp.model.api.service.LocationModeService;
import com.slxk.gpsantu.mvp.model.bean.LocationModeGetResultBean;
import com.slxk.gpsantu.mvp.model.bean.LoopLocationModeResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceModeSetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LocationModeGetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LoopLocationModePutBean;
import com.slxk.gpsantu.mvp.model.putbean.LoopModeModifyPutBean;
import com.slxk.gpsantu.mvp.utils.ConstantValue;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;


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
public class LocationModeModel extends BaseModel implements LocationModeContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LocationModeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<LocationModeGetResultBean> getLocationMode(LocationModeGetPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(LocationModeService.class).getLocationMode(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<LocationModeGetResultBean>, ObservableSource<LocationModeGetResultBean>>() {
                    @Override
                    public ObservableSource<LocationModeGetResultBean> apply(Observable<LocationModeGetResultBean> locationModeGetResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(LocationModeService.class).getLocationMode(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitLocationMode(DeviceModeSetPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(LocationModeService.class).submitLocationMode(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(LocationModeService.class).submitLocationMode(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<LoopLocationModeResultBean> getLoopLocationMode(LoopLocationModePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(LocationModeService.class).getLoopLocationMode(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<LoopLocationModeResultBean>, ObservableSource<LoopLocationModeResultBean>>() {
                    @Override
                    public ObservableSource<LoopLocationModeResultBean> apply(Observable<LoopLocationModeResultBean> loopLocationModeResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(LocationModeService.class).getLoopLocationMode(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitLoopModify(LoopModeModifyPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(LocationModeService.class).submitLoopModify(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(LocationModeService.class).submitLoopModify(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

}
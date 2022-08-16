package com.slxk.gpsantu.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.slxk.gpsantu.mvp.contract.AlarmScreenContract;
import com.slxk.gpsantu.mvp.model.api.service.AlarmService;
import com.slxk.gpsantu.mvp.model.bean.AlarmScreenBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmScreenTypePutBean;
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
 * Created by MVPArmsTemplate on 01/14/2021 14:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AlarmScreenModel extends BaseModel implements AlarmScreenContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AlarmScreenModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<AlarmScreenBean> getAlarmScreenType(AlarmScreenTypePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AlarmService.class).getAlarmScreenType(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<AlarmScreenBean>, ObservableSource<AlarmScreenBean>>() {
                    @Override
                    public ObservableSource<AlarmScreenBean> apply(Observable<AlarmScreenBean> alarmScreenBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AlarmService.class).getAlarmScreenType(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}
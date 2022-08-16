package com.slxk.gpsantu.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.contract.UserSwitchContract;
import com.slxk.gpsantu.mvp.model.api.service.DeviceService;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;
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
 * Created by MVPArmsTemplate on 07/10/2021 17:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserSwitchModel extends BaseModel implements UserSwitchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserSwitchModel(IRepositoryManager repositoryManager) {
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
}
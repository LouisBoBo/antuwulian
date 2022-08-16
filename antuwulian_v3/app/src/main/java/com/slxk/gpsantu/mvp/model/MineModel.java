package com.slxk.gpsantu.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.slxk.gpsantu.mvp.contract.MineContract;
import com.slxk.gpsantu.mvp.model.api.service.DeviceService;
import com.slxk.gpsantu.mvp.model.api.service.PublicService;
import com.slxk.gpsantu.mvp.model.api.service.UserService;
import com.slxk.gpsantu.mvp.model.bean.CheckAppUpdateBean;
import com.slxk.gpsantu.mvp.model.bean.LogoutAccountResultBean;
import com.slxk.gpsantu.mvp.model.bean.UnbindPhoneResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.CheckAppUpdatePutBean;
import com.slxk.gpsantu.mvp.model.putbean.LogoutAccountPutBean;
import com.slxk.gpsantu.mvp.model.putbean.SignOutPutBean;
import com.slxk.gpsantu.mvp.model.putbean.UnbindPhonePutBean;
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
 * Created by MVPArmsTemplate on 10/22/2020 16:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> submitSignOut(SignOutPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(UserService.class).submitSignOut(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(UserService.class).submitSignOut(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<CheckAppUpdateBean> getAppUpdate(CheckAppUpdatePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).getAppUpdate(requestBody))
                .flatMap(new Function<Observable<CheckAppUpdateBean>, ObservableSource<CheckAppUpdateBean>>() {
                    @Override
                    public ObservableSource<CheckAppUpdateBean> apply(Observable<CheckAppUpdateBean> checkAppUpdateBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).getAppUpdate(requestBody);
                    }
                });
    }

    @Override
    public Observable<LogoutAccountResultBean> submitLogoutAccount(LogoutAccountPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(UserService.class).submitLogoutAccount(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<LogoutAccountResultBean>, ObservableSource<LogoutAccountResultBean>>() {
                    @Override
                    public ObservableSource<LogoutAccountResultBean> apply(Observable<LogoutAccountResultBean> logoutAccountResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(UserService.class).submitLogoutAccount(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<UnbindPhoneResultBean> submitUnbindPhone(UnbindPhonePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitUnbindPhone(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<UnbindPhoneResultBean>, ObservableSource<UnbindPhoneResultBean>>() {
                    @Override
                    public ObservableSource<UnbindPhoneResultBean> apply(Observable<UnbindPhoneResultBean> unbindPhoneResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitUnbindPhone(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}
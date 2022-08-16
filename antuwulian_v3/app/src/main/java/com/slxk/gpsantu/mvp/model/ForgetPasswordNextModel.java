package com.slxk.gpsantu.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.slxk.gpsantu.mvp.contract.ForgetPasswordNextContract;
import com.slxk.gpsantu.mvp.model.api.service.PublicService;
import com.slxk.gpsantu.mvp.model.bean.ForgetPasswordResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneAreaResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneCodeResultBean;
import com.slxk.gpsantu.mvp.model.putbean.ForgetPasswordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneAreaPutBean;
import com.slxk.gpsantu.mvp.model.putbean.PhoneCodePutBean;

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
 * Created by MVPArmsTemplate on 12/25/2020 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ForgetPasswordNextModel extends BaseModel implements ForgetPasswordNextContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ForgetPasswordNextModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<PhoneAreaResultBean> getPhoneArea(PhoneAreaPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).getPhoneArea(requestBody))
                .flatMap(new Function<Observable<PhoneAreaResultBean>, ObservableSource<PhoneAreaResultBean>>() {
                    @Override
                    public ObservableSource<PhoneAreaResultBean> apply(Observable<PhoneAreaResultBean> phoneAreaResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).getPhoneArea(requestBody);
                    }
                });
    }

    @Override
    public Observable<PhoneCodeResultBean> getPhoneCode(PhoneCodePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).getPhoneCode(requestBody))
                .flatMap(new Function<Observable<PhoneCodeResultBean>, ObservableSource<PhoneCodeResultBean>>() {
                    @Override
                    public ObservableSource<PhoneCodeResultBean> apply(Observable<PhoneCodeResultBean> phoneCodeResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).getPhoneCode(requestBody);
                    }
                });
    }

    @Override
    public Observable<ForgetPasswordResultBean> submitForgetPassword(ForgetPasswordPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).submitForgetPassword(requestBody))
                .flatMap(new Function<Observable<ForgetPasswordResultBean>, ObservableSource<ForgetPasswordResultBean>>() {
                    @Override
                    public ObservableSource<ForgetPasswordResultBean> apply(Observable<ForgetPasswordResultBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).submitForgetPassword(requestBody);
                    }
                });
    }
}
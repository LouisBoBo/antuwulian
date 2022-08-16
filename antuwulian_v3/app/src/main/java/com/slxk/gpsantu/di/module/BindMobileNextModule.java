package com.slxk.gpsantu.di.module;

import com.slxk.gpsantu.mvp.contract.BindMobileNextContract;
import com.slxk.gpsantu.mvp.model.BindMobileNextModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2021 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class BindMobileNextModule {

    @Binds
    abstract BindMobileNextContract.Model bindBindMobileNextModel(BindMobileNextModel model);
}
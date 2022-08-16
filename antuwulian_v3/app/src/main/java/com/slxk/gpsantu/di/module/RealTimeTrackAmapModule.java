package com.slxk.gpsantu.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.slxk.gpsantu.mvp.contract.RealTimeTrackAmapContract;
import com.slxk.gpsantu.mvp.model.RealTimeTrackAmapModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class RealTimeTrackAmapModule {

    @Binds
    abstract RealTimeTrackAmapContract.Model bindRealTimeTrackAmapModel(RealTimeTrackAmapModel model);
}
package com.slxk.gpsantu.di.module;

import com.slxk.gpsantu.mvp.contract.ForgetPasswordNextContract;
import com.slxk.gpsantu.mvp.model.ForgetPasswordNextModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class ForgetPasswordNextModule {

    @Binds
    abstract ForgetPasswordNextContract.Model bindForgetPasswordNextModel(ForgetPasswordNextModel model);
}
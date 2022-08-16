package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.SimDetailInfoModule;
import com.slxk.gpsantu.mvp.contract.SimDetailInfoContract;

import com.jess.arms.di.scope.ActivityScope;

import com.slxk.gpsantu.mvp.ui.activity.SimDetailInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2021 17:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SimDetailInfoModule.class, dependencies = AppComponent.class)
public interface SimDetailInfoComponent {
    void inject(SimDetailInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SimDetailInfoComponent.Builder view(SimDetailInfoContract.View view);

        SimDetailInfoComponent.Builder appComponent(AppComponent appComponent);

        SimDetailInfoComponent build();
    }
}